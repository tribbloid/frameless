# TestReportPlugin - Portable sbt Test Report Generator

A standalone sbt plugin for generating beautiful HTML test reports. No dependencies on project structure or naming.

## Quick Start

1. **Copy to your project**:
   ```bash
   cp TestReportPlugin.scala /path/to/your-project/project/
   ```

2. **Run your tests**:
   ```bash
   sbt test
   ```

3. **Generate the report**:
   ```bash
   sbt generateTestReport
   ```

4. **View the report**:
   Open `target/test-reports/test-report.html` in your browser

## Customization (Optional)

Add to your `build.sbt`:

```scala
// Customize the report title (default: project name)
testReportTitle := "My Awesome Project"

// Customize output directory (default: target/test-reports)
testReportOutputDir := target.value / "custom-reports"
```

## What It Does

- Scans all modules for test result XML files
- Aggregates results into a single beautiful HTML report
- Shows pass/fail statistics, execution times, and detailed error messages
- Works with any sbt project using ScalaTest, Specs2, or other JUnit XML-compatible test frameworks

## Requirements

- sbt 1.x
- Test frameworks that generate JUnit XML reports (most Scala test frameworks do)

## Features

✅ Zero configuration required  
✅ Fully portable - works with any sbt project  
✅ No hardcoded names or paths  
✅ Beautiful, responsive HTML design  
✅ Expandable test case details  
✅ Module-level and suite-level breakdowns  
✅ Color-coded pass/fail indicators  

---

For detailed documentation, see: `docs/TestReportPlugin.md`
