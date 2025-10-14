# Test Report Plugin

A generic sbt plugin that generates aggregated HTML test reports across all project modules.

## Usage

To generate the test report, run:

```bash
sbt generateTestReport
```

The report will be generated at: `target/test-reports/test-report.html`

## Configuration

The plugin can be customized with the following settings:

```scala
// In build.sbt
testReportTitle := "My Project"  // Default: project name
testReportOutputDir := target.value / "custom-reports"  // Default: target/test-reports
```

## Features

- **Generic and Reusable**: No hardcoded project names or packages - works with any sbt project
- **Aggregated Results**: Automatically discovers and combines test results from all project modules
- **Beautiful HTML Report**: Modern, responsive design with color-coded statistics
- **Pass Rate Visualization**: Prominent display of overall pass rate percentage
- **Module Breakdown**: Shows test statistics per module
- **Suite Details**: Expandable test suite details with individual test case results
- **Time Tracking**: Displays execution time for each test suite and individual test
- **Error Messages**: Shows failure and error messages for failed tests
- **Configurable**: Customize report title and output directory

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
- **Type**: sbt AutoPlugin (automatically enabled for all projects)
- **Input**: XML test result files from `target/test-reports/*.xml` in all modules
- **Output**: Single aggregated HTML file at `target/test-reports/test-report.html`

The plugin automatically scans all module directories for test result XML files and aggregates them into a single, comprehensive HTML report. It extracts module names from the directory structure, making it work with any project layout.

### Portability

This plugin is fully portable and can be copied to any sbt project:

1. Copy `project/TestReportPlugin.scala` to your project's `project/` directory
2. The plugin auto-enables and is ready to use
3. Run `sbt generateTestReport` after running tests
4. Optionally configure `testReportTitle` in your `build.sbt`

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
