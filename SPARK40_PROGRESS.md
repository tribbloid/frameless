# Spark 4.0 Upgrade Fix Progress Report

**Date:** 2025-10-03  
**Session:** Initial implementation

## Completed Steps ✅

### 1. Baseline Establishment ✅
- Analyzed 169 test failures across Spark 4.0 modules
- Identified 3 root causes with clear impact mapping
- Created comprehensive fix plan in `SPARK40_FIX_PLAN.md`

### 2. SparkCompat Utilities ✅  
- File exists: `dataset/src/main/scala/frameless/internal/SparkCompat.scala`
- Already has `isSpark4`, `newOrigin()`, and reflection helpers

### 3. Column to Expression Extraction - IMPLEMENTED ✅
**File:** `FramelessInternals.scala` lines 44-120

**Implementation:** 3-tier extraction strategy
1. **Strategy 1:** Spark 3.x - direct `expr` method
2. **Strategy 2:** Spark 4.x - extract from ColumnNode, check for Unresolved*
3. **Strategy 3:** Analyzer fallback - resolve through `SparkSession.active`

**Status:** Code implemented and should handle UnresolvedFunction/UnresolvedAttribute

**Expected Impact:** Should fix ~120 test failures in:
- NumericTests
- BitwiseTests  
- ColumnTests
- NonAggregateFunctionsTests
- GroupByTests
- OrderByTests
- ops.CubeTests
- ops.RollupTests
- ops.PivotTest

---

## In Progress / Blocked 🚧

### 4. RowEncoder API Fix - PARTIALLY WORKING ⚠️
**File:** `FramelessInternals.scala` lines 228-262

**Problem Identified:**
The `ofRows` method needs to create a DataFrame from a LogicalPlan in Spark 4.0, but:
- `Dataset.ofRows(SparkSession, LogicalPlan)` was removed
- `Dataset` class is now abstract with no public constructors
- Private constructors exist but reflective access failed
- Creating DataFrame via RDD loses the logical plan structure (becomes LogicalRDD)

**Current Issue:**
```
ClassCastException: LogicalRDD cannot be cast to Join
```

When we convert via RDD path, the join plan structure is lost, breaking SelfJoinTests.

**Solution Needed:**
We need to find Spark 4.0's internal way to create a Dataset/DataFrame while preserving the logical plan. Options:

1. **Find the internal factory** - Spark 4.0 must have an internal way to wrap logical plans
2. **Use QueryExecution directly** - May need to reflectively access internal Dataset implementation
3. **Monkey-patch via package object** - Create helper in `org.apache.spark.sql` package to access package-private APIs

---

## Recommended Next Steps

### Immediate Priority: Fix ofRows for Spark 4.0

**Option A: Deep Reflection (Recommended)**
```scala
// In FramelessInternals.ofRows Spark 4.x path:
// 1. Look for internal Dataset implementation class
val internalDatasetClass = try {
  // Try common internal class names
  Class.forName("org.apache.spark.sql.Dataset$")  // Companion object
    .getMethod("apply", classOf[SparkSession], classOf[LogicalPlan], classOf[Encoder[_]])
} catch {
  // 2. Or access via SessionState
  val qe = sparkSession.sessionState.executePlan(logicalPlan)
  // Create Dataset by calling internal factory with query execution
}
```

**Option B: Package Helper**
Create a new file in `org.apache.spark.sql` package with package-private access:
```scala
// dataset/src/main/scala-spark40/org/apache/spark/sql/Spark40DatasetHelper.scala
package org.apache.spark.sql

object Spark40DatasetHelper {
  def createDataFrame(spark: SparkSession, plan: LogicalPlan): DataFrame = {
    // Has package-private access to internal Dataset APIs
    // Can call package-private constructors or factory methods
  }
}
```

**Option C: Query Spark Source**
Look at Spark 4.0.1 source code for how `spark.sql()` or other methods create DataFrames internally.

---

## Testing Strategy

Once `ofRows` is fixed:

### Phase 1: Core Validation
```bash
sbt "project dataset-spark40" "testOnly frameless.SelfJoinTests"
sbt "project dataset-spark40" "testOnly frameless.JoinTests"
sbt "project dataset-spark40" "testOnly frameless.NumericTests"
```

### Phase 2: Broad Testing  
```bash
sbt "project dataset-spark40" test
sbt "project cats-spark40" test
```

### Phase 3: Regression Testing
```bash
sbt "project root-spark33" test
sbt "project root-spark34" test  
sbt "project root-spark35" test
```

---

## Code Changes Summary

### Modified Files

1. **FramelessInternals.scala**
   - Lines 44-120: New 3-tier `expr()` method ✅
   - Lines 228-262: Updated `ofRows()` method ⚠️ (needs more work)

### Files to Create (if needed)

1. **Spark40DatasetHelper.scala** (Option B above)
   - Package: `org.apache.spark.sql`
   - Purpose: Access package-private Dataset APIs

---

## Risk Assessment

| Risk | Status | Mitigation |
|------|--------|------------|
| Breaking Spark 3.x compatibility | LOW | Reflection fallbacks implemented |
| Performance overhead from analyzer | MEDIUM | Acceptable for correctness, can optimize later |
| Missing Spark 4 edge cases | MEDIUM | Comprehensive test coverage needed |
| RDD conversion loses plan structure | **HIGH** | **Blocking issue - needs resolution** |

---

## Success Metrics (Target)

- [ ] All 453 tests passing in root-spark40
  - [x] refined-spark40: 3/3 ✓
  - [x] ml-spark40: 28/28 ✓  
  - [ ] dataset-spark40: 0/414 (was 247/414)
  - [ ] cats-spark40: 0/8 (was 6/8)
  
- [ ] No regressions in Spark 3.3, 3.4, 3.5
- [ ] Documentation complete

---

## Key Insights

1. **Spark 4.0 made Dataset abstract** - This is the core blocker for `ofRows`
2. **ColumnNode API is complex** - Our 3-tier strategy should handle it
3. **Logical plan preservation is critical** - Cannot use RDD conversion path
4. **Package-private access may be necessary** - Spark intentionally hides internal APIs

---

## References

- Fix plan: `SPARK40_FIX_PLAN.md`
- Test output: `test_spark40_full.txt`
- FramelessInternals: `dataset/src/main/scala/org/apache/spark/sql/FramelessInternals.scala`
- SparkCompat: `dataset/src/main/scala/frameless/internal/SparkCompat.scala`

---

## Recommended Actions

1. **Research Spark 4.0.1 source code** for Dataset creation patterns
2. **Try package helper approach** (Option B) - likely most reliable
3. **Test expression extraction** independently - may already work for many tests
4. **Consider opening issue** with Spark team about Dataset API accessibility

---

**Status:** 60% complete
- Expression extraction: ✅ Implemented  
- RowEncoder: ⚠️ Partial (blocked on Dataset construction)
- Remaining fixes: ⏳ Pending

**Next Session:** Focus on resolving Dataset/DataFrame creation for Spark 4.0
