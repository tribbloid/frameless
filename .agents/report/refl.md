# Java/Scala Runtime Reflection Usage Report

**Generated:** 2025-10-17  
**Project:** Frameless  
**Purpose:** Identify all runtime reflection usage for Spark 3.x/4.x cross-version compatibility

---

## Executive Summary

The Frameless project uses **Java reflection extensively** and **Scala reflection sparingly** to maintain compatibility across Spark 3.x and Spark 4.x versions. The reflection is primarily concentrated in:

1. **Spark API compatibility layer** - Detecting and adapting to different Spark internal APIs
2. **Encoder construction** - Building Spark encoders reflectively for cross-version support
3. **Column expression extraction** - Accessing internal Spark Column structures
4. **Test utilities** - Accessing private Spark fields for verification

**Risk Level:** Medium - Reflection is necessary but creates maintenance burden and potential runtime failures across Spark versions.

---

## 1. Java Reflection Usage

### 1.1 Core Compatibility Layer

#### `SparkCompat.scala`
**Location:** `dataset/src/main/scala/frameless/internal/SparkCompat.scala`

| Line | API | Purpose | Risk |
|------|-----|---------|------|
| 23 | `Class.forName()` | Check if class exists | Low |
| 32-35 | `Class.forName()`, `.getField()`, `.getMethod()`, `.invoke()` | Create Spark 3.x Origin | Medium |
| 39-54 | `Class.forName()`, `.getField()`, `.getConstructor()`, `.newInstance()` | Create Spark 4.x Origin (9 None params) | Medium |
| 59-62 | `Class.forName()`, `.getDeclaredConstructor()`, `.newInstance()` | Fallback Origin construction | Medium |
| 73-79 | `.getConstructors()`, `.getParameterTypes()` | Find constructor by signature | Low |
| 83-87 | `.getConstructors()`, `.getParameterTypes()` | Debug constructor signatures | Low |

**Purpose:** Detect Spark version and construct Origin objects that have different APIs between Spark 3.x and 4.x.

---

### 1.2 TypedExpressionEncoder Construction

#### `TypedExpressionEncoder.scala`
**Location:** `dataset/src/main/scala/frameless/TypedExpressionEncoder.scala`

| Line | API | Purpose | Risk |
|------|-----|---------|------|
| 95-98 | `Class.forName("scala.reflect.ClassTag$")`, `.getField("MODULE$")`, `.getMethod("apply")`, `.invoke()` | Build ClassTag reflectively (Spark 4.x) | High |
| 109-115 | `Class.forName("...RowEncoder$")`, `.getField("MODULE$")`, `.getMethod("encoderFor")`, `.invoke()` | Get RowEncoder for schema | High |
| 121-122 | `.getClass()`, `.getMethod("fields")`, `.invoke()` | Extract encoder fields | High |
| 126-130 | `Class.forName("...ProductEncoder")`, `.getConstructors`, `.getField("MODULE$")`, `.newInstance()` | Create ProductEncoder with custom ClassTag | High |
| 135-143 | Same as 109-115 | Fallback RowEncoder creation | High |
| 166 | `.newInstance()` | Instantiate Spark 4.x ExpressionEncoder | High |
| 170-173 | Same as 95-98 | Build ClassTag reflectively (Spark 3.5+) | High |
| 204-205 | `.newInstance()` | Instantiate Spark 3.5+ ExpressionEncoder | High |
| 247-248 | `.newInstance()` | Instantiate Spark 3.3-3.4 ExpressionEncoder | High |
| 1907-1908 | Static method call via TypedExpressionEncoder | Get target struct type (uses reflection internally) | Medium |

**Purpose:** Create Spark ExpressionEncoder instances that have different constructor signatures across Spark 3.3, 3.5, and 4.0 versions. This is the most critical reflection code as it's executed for every typed operation.

**Risk:** **HIGH** - Any changes to Spark's internal encoder APIs will break this code. The multiple fallback strategies mitigate this somewhat.

---

### 1.3 Column Expression Extraction

#### `FramelessInternals.scala`
**Location:** `dataset/src/main/scala/org/apache/spark/sql/FramelessInternals.scala`

| Line | API | Purpose | Risk |
|------|-----|---------|------|
| 35-40 | `Class.forName("...SparkException")`, `.getMethod("internalError")`, `.invoke()` | Throw Spark 4.x exception | Medium |
| 49-50 | `.getConstructor()`, `.newInstance()` | Throw Spark 3.x AnalysisException | Medium |
| 63-64 | `.getMethod("expr")`, `.invoke()` | Extract Expression (Spark 3.x) | High |
| 72-73 | `.getMethod("node")`, `.invoke()` | Get ColumnNode (Spark 4.x) | High |
| 86-88 | `.getMethod("expression")`, `.invoke()` | Extract from ExpressionColumnNode | High |
| 96-98 | `.getMethod("normalized")`, `.invoke()` | Extract normalized Expression | High |
| 126-128 | `.getMethod("toExpression")`, `.invoke()` | Convert node to Expression | High |
| 136-140 | `.getMethod("toAnalysis")`, `.invoke()` | Convert node to Analysis | High |
| 150-157 | `Class.forName("...SparkSession$")`, `.getField("MODULE$")`, `.getMethod("getActiveSession")`, `.invoke()` | Get active SparkSession | Medium |
| 167-170 | `.getMethod("functionName")`, `.getMethod("arguments")`, `.invoke()` | Extract UnresolvedFunction details | High |
| 226-232 | `.getMethod()`, `.invoke()` | Extract function name and arguments (fallback) | High |
| 258-262 | `.getMethod("value")`, `.getMethod("dataType")`, `.invoke()` | Extract literal value | High |
| 320-323 | `.getMethod("expression")`, `.invoke()` | Recursive expression extraction | High |
| 351-353 | `.getConstructors()` | Find UnresolvedFunction constructor | Medium |
| 489-490 | `.getConstructor()`, `.newInstance()` | Create Column (Spark 3.x) | High |
| 525-527 | `.getConstructor()` | Find ExpressionColumnNode constructor | High |
| 533 | `.getConstructor()` | Fallback constructor search | High |
| 549-556 | `.getConstructors()` | Find Column constructor for node | High |

**Purpose:** Extract Catalyst Expression objects from Spark Column objects, which changed significantly between Spark 3.x (direct `expr` field) and Spark 4.x (ColumnNode hierarchy).

**Risk:** **HIGH** - This is executed frequently during query construction. Any changes to Spark's Column internals require updates here.

---

### 1.4 Spark 4.0 Dataset Helper

#### `Spark40DatasetHelper.scala`
**Location:** `dataset/src/main/scala/org/apache/spark/sql/Spark40DatasetHelper.scala`

| Line | API | Purpose | Risk |
|------|-----|---------|------|
| 38-39 | `Class.forName("...Dataset$")`, `.getField("MODULE$")` | Get Dataset companion object | Medium |
| 42-44 | `.getMethods()`, `.getParameterTypes()` | Find apply/ofRows methods | Medium |
| 52-59 | `.invoke()` | Invoke Dataset factory methods | High |
| 72-86 | `.getMethods()`, `.setAccessible(true)`, `.invoke()` | Invoke QueryExecution.toDS | High |
| 103-105 | `.getMethods()`, `.invoke()` | Invoke QueryExecution.toDF | High |
| 135-150 | `.getMethods()`, `.invoke()` | Multiple fallback attempts | High |

**Purpose:** Create DataFrame instances from LogicalPlan without executing queries, preserving plan structure for Spark 4.0.

**Risk:** **HIGH** - Uses `.setAccessible(true)` to bypass access control, which is fragile.

---

### 1.5 Test and Debug Utilities

#### `FramelessLitPushDownTests.scala`
**Location:** `dataset/src/test/spark-3.3+/frameless/sql/rules/FramelessLitPushDownTests.scala`

| Line | API | Purpose | Risk |
|------|-----|---------|------|
| 16-17 | `.getMethod("currentTimestamp")`, `.invoke()` | Get Spark 3.x timestamp | Low |
| 27-28 | `.getMethod("microsToInstant")`, `.invoke()` | Convert micros to Instant | Low |

**Purpose:** Test utilities that work across Spark versions by detecting available methods.

**Risk:** Low - Test code only.

---

#### `DumpEncoderTag.scala`
**Location:** `dataset/src/test/scala/frameless/debug/DumpEncoderTag.scala`

| Line | API | Purpose | Risk |
|------|-----|---------|------|
| 9-11 | `.getMethods()`, `.invoke()`, `.getMethod()` | Debug encoder ClassTag | Low |

**Purpose:** Debug utility to inspect encoder internals.

**Risk:** Low - Debug code only.

---

#### `DumpConstructors.scala`
**Location:** `dataset/src/test/scala/frameless/debug/DumpConstructors.scala`

| Line | API | Purpose | Risk |
|------|-----|---------|------|
| 14 | `Class.forName()` | Load AgnosticEncoder class | Low |
| 21-23 | `Class.forName()`, `.getField("MODULE$")`, `.getDeclaredMethods()` | Inspect AgnosticEncoder module | Low |

**Purpose:** Debug utility to dump class constructors.

**Risk:** Low - Debug code only.

---

## 2. Scala Runtime Reflection Usage

### 2.1 Reflection Package

#### `reflection/package.scala`
**Location:** `dataset/src/main/scala/org/apache/spark/sql/reflection/package.scala`

| Line | API | Purpose | Risk |
|------|-----|---------|------|
| 43-44 | `scala.reflect.runtime.universe` | Access Scala reflection universe | Medium |
| 46 | `import universe._` | Import all universe members | Medium |
| 56 | `TypeTag` (implicit) | Get type information at runtime | Medium |
| 65-68 | `Type.<:<` with synchronization | Thread-safe subtype checking | Medium |
| 71-90 | `Type.dealias`, `definitions.*`, type matching | Convert Scala types to Spark DataTypes | Medium |

**Purpose:** Provide type-level operations for encoding Scala types to Spark SQL types. This is a copy of Spark's pre-3.4 reflection-based encoding.

**Risk:** Medium - Requires `scala-reflect` dependency and is sensitive to Scala version changes. Protected by synchronization lock due to known thread-safety issues in Scala reflection.

---

### 2.2 Test Utilities

#### `SQLRulesSuite.scala`
**Location:** `dataset/src/test/scala/frameless/sql/rules/SQLRulesSuite.scala`

| Line | API | Purpose | Risk |
|------|-----|---------|------|
| 69 | `scala.reflect.runtime.universe` | Import runtime universe | Low |
| 71 | `runtimeMirror()` | Create runtime mirror | Low |
| 72 | `reflect()` | Create instance mirror | Low |
| 73-77 | `typeOf[]`, `.member()`, `.asTerm`, `.getter` | Get field accessor | Low |
| 78-79 | `.reflectMethod()`, `.apply()` | Invoke getter method | Low |

**Purpose:** Access private `pushedDownFilters` field from `FileSourceScanExec` in tests to verify filter pushdown behavior.

**Risk:** Low - Test code only, but demonstrates the need to access Spark internals.

---

## 3. ClassTag and TypeTag Usage

ClassTag and TypeTag are Scala's compile-time type information carriers, but they're often used in contexts that involve runtime type operations:

### Files Using ClassTag/TypeTag (87 occurrences across 20 files)

**Most intensive usage:**
- `TypedExpressionEncoder.scala` (14 uses) - Building encoders
- `SelectTests.scala` (12 uses) - Test utilities
- `ExplodeTests.scala` (10 uses) - Test utilities
- `TypedEncoder.scala` (9 uses) - Core encoding logic
- `implicits.scala` (6 uses) - Cats integration
- `RecordEncoder.scala` (4 uses) - Product type encoding

**Purpose:** These provide type information that Spark needs for schema derivation and serialization. While not strictly "runtime reflection," they capture type information at compile time for runtime use.

**Risk:** Low to Medium - These are part of Scala's standard type class mechanism, but failures manifest at runtime.

---

## 4. Summary and Recommendations

### Reflection Distribution by Risk Level

| Risk Level | Count | Component |
|------------|-------|-----------|
| **High** | ~30 sites | TypedExpressionEncoder, FramelessInternals, Spark40DatasetHelper |
| **Medium** | ~15 sites | SparkCompat, exception handling, scala-reflect usage |
| **Low** | ~10 sites | Test utilities, debug tools |

### Key Risks

1. **Encoder construction breakage** - Changes to Spark's ExpressionEncoder constructor signatures will break TypedExpressionEncoder
2. **Column API changes** - Changes to Spark 4.x ColumnNode hierarchy will break FramelessInternals
3. **Private API access** - Using `.setAccessible(true)` and accessing internal Spark classes
4. **Thread safety** - Scala reflection subtype checking requires synchronization
5. **Maintenance burden** - Each Spark version may require reflection updates

### Mitigation Strategies

1. **Multiple fallback strategies** - Code tries multiple approaches before failing
2. **Version detection** - `SparkCompat.isSpark4` enables conditional logic
3. **Comprehensive error handling** - Try-catch blocks with detailed error messages
4. **Test coverage** - Extensive test suite catches reflection breakage early
5. **Documentation** - Comments explain why reflection is needed

### Recommendations

1. **Monitor Spark changelogs** - Watch for ExpressionEncoder, Column, and RowEncoder API changes
2. **Add integration tests** - Test against Spark milestone releases early
3. **Consider alternatives** - Where possible, use public APIs instead of reflection
4. **Maintain fallbacks** - Keep multiple strategies for critical operations
5. **Document assumptions** - Each reflection site should document what it expects from Spark internals

### Files Requiring Special Attention on Spark Updates

1. `TypedExpressionEncoder.scala` - Lines 85-250
2. `FramelessInternals.scala` - Lines 30-600
3. `SparkCompat.scala` - Entire file
4. `Spark40DatasetHelper.scala` - Entire file
5. `reflection/package.scala` - Lines 43-90

---

## Appendix: Complete File Listing

### Production Code
- `dataset/src/main/scala/frameless/TypedExpressionEncoder.scala`
- `dataset/src/main/scala/frameless/TypedDataset.scala`
- `dataset/src/main/scala/frameless/internal/SparkCompat.scala`
- `dataset/src/main/scala/org/apache/spark/sql/FramelessInternals.scala`
- `dataset/src/main/scala/org/apache/spark/sql/Spark40DatasetHelper.scala`
- `dataset/src/main/scala/org/apache/spark/sql/reflection/package.scala`

### Test Code
- `dataset/src/test/spark-3.3+/frameless/sql/rules/FramelessLitPushDownTests.scala`
- `dataset/src/test/scala/frameless/debug/DumpEncoderTag.scala`
- `dataset/src/test/scala/frameless/debug/DumpConstructors.scala`
- `dataset/src/test/scala/frameless/sql/rules/SQLRulesSuite.scala`

### Files with ClassTag/TypeTag (not detailed here but present)
- All test suites and encoder implementations (87 total occurrences across 20 files)

---

**Report End**
