# Spark 4.0 Upgrade Fix Plan for Frameless

## Executive Summary

**Date:** 2025-10-03  
**Target:** Fix all test failures caused by Spark 4.0.1 upgrade  
**Status:** 169 tests failing out of 425 total tests in Spark 4.0 modules

### Test Failure Breakdown

| Module | Passed | Failed | Total |
|--------|--------|--------|-------|
| dataset-spark40 | 247 | 167 | 414 |
| cats-spark40 | 6 | 2 | 8 |
| refined-spark40 | 3 | 0 | 3 |
| ml-spark40 | 28 | 0 | 28 |
| **TOTAL** | 284 | 169 | 453 |

---

## Root Cause Analysis

### 1. RowEncoder API Breaking Change
**Error:** `NoSuchMethodException: org.apache.spark.sql.catalyst.encoders.RowEncoder.apply(org.apache.spark.sql.SparkSession)`

**Location:** `FramelessInternals.scala:191` (ofRows method)

**Cause:** Spark 4.0 removed `RowEncoder.apply(SparkSession)` and now requires `RowEncoder.apply(StructType)` or similar schema-based factory methods.

**Impact:** 
- SelfJoinTests (4 failures)
- JoinTests (multiple failures)
- Any code path creating DataFrames from logical plans

---

### 2. Column to Expression Extraction Failure
**Error:** `UnsupportedOperationException: Cannot extract Expression from org.apache.spark.sql.internal.UnresolvedFunction/UnresolvedAttribute`

**Location:** `FramelessInternals.scala:44-74` (expr method)

**Cause:** Spark 4.0 introduced new ColumnNode-based API where Columns wrap UnresolvedFunction and UnresolvedAttribute instead of direct Catalyst Expressions. The current reflection strategy fails to extract Expressions from these new node types.

**Impact (Major):**
- NumericTests (multiply, mod operations)
- BitwiseTests
- ColumnTests
- NonAggregateFunctionsTests
- GroupByTests
- OrderByTests
- ops.CubeTests
- ops.RollupTests
- ops.PivotTest
- sql.rules.FramelessLitPushDownTests

---

### 3. Encoder and Schema API Changes
**Error:** Various reflection failures and schema differences

**Location:** Multiple files in dataset module

**Cause:** Spark 4.0 changed ExpressionEncoder construction and schema representation

**Impact:**
- RecordEncoderTests
- ColumnViaLambdaTests
- SchemaTests
- WriteStreamTests

---

## 12-Step Fix Plan

### Phase 1: Foundation (Steps 1-2)

#### Step 1: Establish Baseline and Triage
**Goal:** Reproduce and document all failures

**Actions:**
```bash
sbt "project root-spark40" test 2>&1 | tee test_spark40_baseline.txt
```

**Deliverables:**
- Baseline log file with all test failures
- Confirmed that refined-spark40 and ml-spark40 are green
- List of all failing test suites

---

#### Step 2: Add Spark Version Utility and Reflection Helpers
**Goal:** Create reusable utilities to reduce reflection boilerplate

**File to create/extend:** `dataset/src/main/scala/frameless/internal/SparkCompat.scala`

**Required utilities:**
```scala
object SparkCompat {
  // Version detection
  def isSpark40Plus: Boolean
  def sparkVersion: String
  def majorMinorPatch: (Int, Int, Int)
  
  // Reflection helpers
  def getField[T](obj: Any, fieldName: String): Option[T]
  def getMethod[T](obj: Any, methodName: String, args: Any*): Option[T]
  def invokeStatic[T](className: String, methodName: String, args: Any*): Option[T]
  
  // Origin helper for Spark 4 ExpressionColumnNode
  def newOrigin(): Any
}
```

**Why:** Avoids repeating try-catch reflection patterns across multiple files

---

### Phase 2: Core Fixes (Steps 3-4)

#### Step 3: Fix RowEncoder API Break
**Priority:** HIGH (blocks 4+ test suites)

**File:** `dataset/src/main/scala/org/apache/spark/sql/FramelessInternals.scala`

**Current broken code (lines 188-196):**
```scala
def ofRows(sparkSession: SparkSession, logicalPlan: LogicalPlan): DataFrame = {
  try {
    // Spark 3.x approach
    val ofRowsMethod = Class.forName("org.apache.spark.sql.Dataset")
      .getMethod("ofRows", classOf[SparkSession], classOf[LogicalPlan])
    ofRowsMethod.invoke(null, sparkSession, logicalPlan).asInstanceOf[DataFrame]
  } catch {
    case _: NoSuchMethodException =>
      // Spark 4.x approach - CURRENTLY BROKEN
      val rowEncoderMethod = Class.forName("org.apache.spark.sql.catalyst.encoders.RowEncoder")
        .getMethod("apply", classOf[SparkSession])  // ❌ THIS SIGNATURE DOESN'T EXIST
      ...
  }
}
```

**Fix strategy:**
```scala
def ofRows(sparkSession: SparkSession, logicalPlan: LogicalPlan): DataFrame = {
  try {
    // Spark 3.x: Dataset.ofRows(sparkSession, logicalPlan)
    val ofRowsMethod = Class.forName("org.apache.spark.sql.Dataset")
      .getMethod("ofRows", classOf[SparkSession], classOf[LogicalPlan])
    ofRowsMethod.invoke(null, sparkSession, logicalPlan).asInstanceOf[DataFrame]
  } catch {
    case _: NoSuchMethodException =>
      // Spark 4.x: new Dataset[Row](sparkSession, logicalPlan, RowEncoder(schema))
      val schema = logicalPlan.schema
      val rowEncoderClass = Class.forName("org.apache.spark.sql.catalyst.encoders.RowEncoder")
      
      // Try RowEncoder.apply(StructType) first
      val encoder = try {
        val applyMethod = rowEncoderClass.getMethod("apply", classOf[StructType])
        applyMethod.invoke(null, schema).asInstanceOf[Encoder[Row]]
      } catch {
        case _: NoSuchMethodException =>
          // Fallback: try encoderFor(schema)
          val encoderForMethod = rowEncoderClass.getMethod("encoderFor", classOf[StructType])
          encoderForMethod.invoke(null, schema).asInstanceOf[Encoder[Row]]
      }
      
      val datasetConstructor = classOf[Dataset[_]]
        .getConstructor(classOf[SparkSession], classOf[LogicalPlan], classOf[Encoder[_]])
      datasetConstructor.newInstance(sparkSession, logicalPlan, encoder).asInstanceOf[DataFrame]
  }
}
```

**Validation:**
```bash
sbt "project dataset-spark40" "testOnly frameless.SelfJoinTests"
sbt "project dataset-spark40" "testOnly frameless.JoinTests"
```

**Expected result:** All RowEncoder-related failures should be resolved

---

#### Step 4: Robust Column to Expression Extraction
**Priority:** CRITICAL (blocks 100+ test failures)

**File:** `dataset/src/main/scala/org/apache/spark/sql/FramelessInternals.scala`

**Current problematic code (lines 44-74):**
The current `expr` method only has two strategies and fails on UnresolvedFunction/UnresolvedAttribute

**Fix strategy - Add 3-tier extraction:**

```scala
def expr(column: Column): Expression = {
  // Strategy 1: Spark 3.x - direct expr field/method
  try {
    val exprMethod = classOf[Column].getMethod("expr")
    return exprMethod.invoke(column).asInstanceOf[Expression]
  } catch {
    case _: NoSuchMethodException => // Continue to Strategy 2
  }
  
  // Strategy 2: Spark 4.x - extract from ColumnNode
  try {
    val nodeMethod = classOf[Column].getMethod("node")
    val columnNode = nodeMethod.invoke(column)
    
    columnNode match {
      case e: Expression => return e  // Direct Expression
      case _ =>
        // Try to get Expression from ExpressionColumnNode wrapper
        try {
          val expressionMethod = columnNode.getClass.getMethod("expression")
          val expr = expressionMethod.invoke(columnNode).asInstanceOf[Expression]
          
          // Check if it's UnresolvedFunction or UnresolvedAttribute
          val className = expr.getClass.getName
          if (className.contains("Unresolved")) {
            // Fall through to Strategy 3 (analyzer)
          } else {
            return expr
          }
        } catch {
          case _: NoSuchMethodException => // Fall through to Strategy 3
        }
    }
  } catch {
    case _: NoSuchMethodException => // Continue to Strategy 3
  }
  
  // Strategy 3: Analyzer fallback - resolve through Spark's analyzer
  try {
    val spark = SparkSession.active
    val dummyDf = spark.range(1).select(column)
    val analyzed = dummyDf.queryExecution.analyzed
    
    analyzed match {
      case Project(projectList, _) if projectList.nonEmpty =>
        projectList.head match {
          case Alias(child, _) => child
          case other => other
        }
      case _ =>
        throw new UnsupportedOperationException(
          s"Cannot extract Expression from Column. Analyzed plan: ${analyzed.getClass.getName}"
        )
    }
  } catch {
    case e: Exception =>
      throw new UnsupportedOperationException(
        s"Cannot extract Expression from Column using any strategy. " +
        s"This may indicate an incompatible Spark version or Column type. Error: ${e.getMessage}",
        e
      )
  }
}
```

**Validation:**
```bash
sbt "project dataset-spark40" "testOnly frameless.NumericTests"
sbt "project dataset-spark40" "testOnly frameless.BitwiseTests"
sbt "project dataset-spark40" "testOnly frameless.ColumnTests"
sbt "project dataset-spark40" "testOnly frameless.NonAggregateFunctionsTests"
sbt "project dataset-spark40" "testOnly frameless.GroupByTests"
```

**Expected result:** ~100+ failures should be resolved

---

### Phase 3: Hardening (Steps 5-7)

#### Step 5: Harden expr Shim for Edge Cases
**Goal:** Optimize and bulletproof the expression extractor

**Actions:**
- Cache reflected method lookups in lazy vals
- Add performance metrics for fallback path usage
- Ensure literal detection works with Spark 4 expression types
- Add defensive null checks

---

#### Step 6: Audit Other Reflection Sites
**Goal:** Find and fix remaining reflection-based incompatibilities

**Files to audit:**
- `FramelessInternals.scala` - all reflection sites
- `TypedDataset.scala` - encoder construction
- `TypedColumn.scala` - column operations

**Search patterns:**
```bash
grep -r "getMethod\|getField\|getConstructor" dataset/src/main/scala/
```

**Validation:**
```bash
sbt "project dataset-spark40" "testOnly frameless.ops.*"
sbt "project dataset-spark40" "testOnly frameless.sql.rules.*"
```

---

#### Step 7: Fix Remaining Encoder Issues
**Goal:** Resolve RecordEncoderTests, SchemaTests, etc.

**Focus areas:**
1. **ExpressionEncoder construction** - Update reflective encoder creation
2. **Schema comparison** - Handle Spark 4 schema representation changes
3. **Lambda column expressions** - Ensure they work through analyzer fallback
4. **DataStreamWriter** - Handle API changes in streaming

**Validation:**
```bash
sbt "project dataset-spark40" "testOnly frameless.RecordEncoderTests"
sbt "project dataset-spark40" "testOnly frameless.SchemaTests"
sbt "project dataset-spark40" "testOnly frameless.ColumnViaLambdaTests"
sbt "project dataset-spark40" "testOnly frameless.WriteStreamTests"
sbt "project dataset-spark40" test
```

---

### Phase 4: Module Completion (Steps 8-10)

#### Step 8: Fix cats-spark40 Module
**Current status:** 2 failures remaining

**Action:**
```bash
sbt "project cats-spark40" "testOnly frameless.cats.*"
```

Most cats failures should be resolved by dataset fixes. Inspect any remaining failures for cats-specific column creation patterns.

---

#### Step 9: Full Integration Test
**Goal:** All Spark 4.0 modules green

```bash
sbt "project root-spark40" test
```

**Success criteria:**
- dataset-spark40: 414/414 passing ✓
- cats-spark40: 8/8 passing ✓
- refined-spark40: 3/3 passing ✓
- ml-spark40: 28/28 passing ✓

---

#### Step 10: Cross-Version Validation
**Goal:** Ensure no regressions in Spark 3.x

```bash
sbt "project root-spark33" test
sbt "project root-spark34" test
sbt "project root-spark35" test
```

**If regressions found:** Add version guards to ensure Spark 3 paths don't invoke Spark 4-only code

---

### Phase 5: Documentation (Steps 11-12)

#### Step 11: Documentation and Comments
**Deliverables:**
1. Inline code comments in FramelessInternals explaining each compatibility strategy
2. README section on Spark 4 compatibility approach
3. Migration guide for users

---

#### Step 12: Cleanup and Optimization
**Optional improvements:**
- Deduplicate reflection utilities
- Cache method handles using MethodHandle API
- Add micro-benchmarks if analyzer fallback shows overhead
- Consider publishing compatibility layer as separate module

---

## Priority Order

### Week 1 (Critical Path)
1. ✅ Step 1: Baseline establishment
2. ✅ Step 2: Utility creation
3. 🔴 Step 3: RowEncoder fix (HIGH priority)
4. 🔴 Step 4: Expression extractor fix (CRITICAL priority)

### Week 2 (Completion)
5. Step 5-7: Hardening and remaining fixes
6. Step 8: cats-spark40
7. Step 9-10: Integration and validation

### Week 3 (Polish)
8. Step 11-12: Documentation and optimization

---

## Test Suite Mapping

### Affected by RowEncoder fix (Step 3):
- ✓ SelfJoinTests
- ✓ JoinTests

### Affected by Expression extractor fix (Step 4):
- ✓ NumericTests
- ✓ BitwiseTests  
- ✓ ColumnTests
- ✓ NonAggregateFunctionsTests
- ✓ GroupByTests
- ✓ OrderByTests
- ✓ ops.CubeTests
- ✓ ops.RollupTests
- ✓ ops.PivotTest
- ✓ sql.rules.FramelessLitPushDownTests

### Affected by encoder fixes (Step 7):
- ✓ RecordEncoderTests
- ✓ ColumnViaLambdaTests
- ✓ SchemaTests
- ✓ WriteStreamTests

### Affected by dataset fixes (Step 8):
- ✓ cats.Test

---

## Risk Assessment

| Risk | Likelihood | Impact | Mitigation |
|------|-----------|--------|------------|
| Analyzer fallback performance overhead | Medium | Low | Add caching, benchmark critical paths |
| New Spark 4 edge cases emerge | Medium | Medium | Comprehensive test coverage, defensive coding |
| Spark 3 regressions | Low | High | Mandatory cross-version testing |
| Undocumented Spark 4 API changes | Low | Medium | Thorough code review of Spark 4 release notes |

---

## Success Metrics

- [ ] All 453 tests passing in root-spark40
- [ ] No regressions in Spark 3.3, 3.4, 3.5 modules  
- [ ] Zero reflection-related exceptions in logs
- [ ] Documentation complete
- [ ] Code review approved

---

## References

- Spark 4.0 Migration Guide: https://spark.apache.org/docs/4.0.0/migration-guide.html
- Current FramelessInternals: `dataset/src/main/scala/org/apache/spark/sql/FramelessInternals.scala`
- Test output: `test_spark40_full.txt`

---

**Generated:** 2025-10-03  
**Author:** AI Assistant  
**Status:** Ready for implementation
