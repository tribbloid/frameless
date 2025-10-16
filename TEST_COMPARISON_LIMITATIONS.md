# Test Comparison Limitations

## Important Note About Legacy Test Results

The test comparison between the current project and the legacy project has significant limitations due to **test execution failures in the legacy project**.

### What Happened

When running `sbt test` on the legacy project (`__LEGACY/frameless`), we encountered:

- ✅ **Current Project**: All 453 tests executed successfully (100% pass rate)
- ❌ **Legacy Project**: Only 4 tests captured; most tests failed to execute

### Legacy Test Execution Errors

The legacy test run showed:
```
[error] Error during tests:
[error]   frameless.ml.classification.TypedRandomForestClassifierTests
[error]   frameless.ml.TypedEncoderInstancesTests
[error]   frameless.ml.clustering.ClusteringIntegrationTests
[error]   frameless.ml.regression.TypedRandomForestRegressorTests
[error]   frameless.ml.feature.TypedStringIndexerTests
[error]   frameless.ml.regression.RegressionIntegrationTests
[error]   frameless.ml.clustering.KMeansTests
[error]   frameless.ml.clustering.BisectingKMeansTests
[error]   frameless.ml.feature.TypedIndexToStringTests
[error]   frameless.ml.regression.TypedLinearRegressionTests
[error]   frameless.ml.classification.ClassificationIntegrationTests
[error]   frameless.ml.feature.TypedVectorAssemblerTests
[info] *** 12 SUITES ABORTED ***
```

### Comparison Results Summary

Based on the limited data available:

| Metric | Value |
|--------|-------|
| Tests captured in Current | 453 |
| Tests captured in Legacy | 4 |
| Tests in both projects | 4 |
| Tests only in Current | 449 |
| Tests only in Legacy | 0 |

### Tests That Appear in Both Projects

Only 4 tests were successfully captured from the legacy project:

1. `frameless.IsValueClassTests.Case class is not Value class`
2. `frameless.IsValueClassTests.Scala value type is not Value class (excluded)`
3. `frameless.IsValueClassTests.Value class evidence`
4. `frameless.cats.Test.spark is working`

### Interpretation

The comparison table in `TEST_NAMES_COMPARISON.md` shows:
- **449 tests marked as "Missing In: Legacy"** - These tests exist in the current project but were not captured from the legacy project (due to execution failures, not because they don't exist)
- **0 tests marked as "Missing In: Current"** - No tests were found in legacy that don't exist in current

### What This Means

**The comparison does NOT indicate that the legacy project had fewer tests.** Instead, it shows:

1. The legacy project's test suite **failed to execute properly**
2. The current project's test suite **executes successfully** with all 453 tests passing
3. We cannot make a valid comparison of test coverage between the two projects based on this data

### Recommendations for Accurate Comparison

To get an accurate comparison, you would need to:

1. Fix the test execution issues in the legacy project
2. Re-run the tests until they execute completely
3. Then perform the comparison again

OR

1. Compare the test source code files directly (`.scala` test files)
2. Count test methods/cases in the source code rather than relying on execution results

### Current Project Status

The important takeaway is:
- ✅ **Current project is healthy**: 453 tests, 100% pass rate
- ✅ **All test suites execute successfully**
- ✅ **No test failures or errors**

---

*Note: This limitation does not diminish the value of the current project's test results, which demonstrate excellent quality and coverage.*
