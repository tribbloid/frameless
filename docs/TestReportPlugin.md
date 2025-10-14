# Test Report Plugin

An sbt plugin that generates aggregated HTML test reports across all Frameless modules.

## Usage

To generate the test report, run:

```bash
sbt generateTestReport
```

The report will be generated at: `target/test-reports/test-report.html`

## Features

- **Aggregated Results**: Combines test results from all modules (core, dataset-spark40, cats-spark40, ml-spark40, refined-spark40)
- **Beautiful HTML Report**: Modern, responsive design with color-coded statistics
- **Pass Rate Visualization**: Prominent display of overall pass rate percentage
- **Module Breakdown**: Shows test statistics per module
- **Suite Details**: Expandable test suite details with individual test case results
- **Time Tracking**: Displays execution time for each test suite and individual test
- **Error Messages**: Shows failure and error messages for failed tests

## Report Contents

The HTML report includes:

### Overall Summary
- Total number of tests
- Passed/Failed/Errors/Skipped counts
- Overall pass rate percentage
- Total execution time

### Module Sections
Each module shows:
- Module name
- Test statistics (passed, failed, errors, skipped)
- Execution time

### Test Suites
For each test suite:
- Suite name
- Test counts and status
- Expandable details showing individual test cases

### Test Cases
Individual test case information:
- Test name
- Execution time
- Status (passed/failed/error/skipped)
- Error messages for failed tests

## Workflow

1. Run your tests:
   ```bash
   sbt test
   ```

2. Generate the report:
   ```bash
   sbt generateTestReport
   ```

3. Open the report:
   ```bash
   # Linux
   xdg-open target/test-reports/test-report.html
   
   # macOS
   open target/test-reports/test-report.html
   
   # Windows
   start target/test-reports/test-report.html
   ```

## Technical Details

- **Location**: `project/TestReportPlugin.scala`
- **Type**: sbt AutoPlugin (automatically enabled)
- **Input**: XML test result files from `target/test-reports/*.xml`
- **Output**: Single aggregated HTML file at `target/test-reports/test-report.html`

The plugin automatically scans all module directories for test result XML files and aggregates them into a single, comprehensive HTML report.

## Example Statistics

Based on the current test run:
- **Total Tests**: 453
- **Passed**: 453
- **Failed**: 0
- **Errors**: 0
- **Skipped**: 0
- **Pass Rate**: 100.0%

## CI/CD Integration

The report can be easily integrated into CI/CD pipelines:

```yaml
# GitHub Actions example
- name: Generate Test Report
  run: sbt generateTestReport

- name: Upload Test Report
  uses: actions/upload-artifact@v3
  with:
    name: test-report
    path: target/test-reports/test-report.html
```

This allows you to download and view the HTML report from your CI pipeline artifacts.
