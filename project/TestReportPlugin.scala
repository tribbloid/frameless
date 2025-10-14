import sbt._
import sbt.Keys._
import java.io.File
import java.nio.file.{ Files, Paths }
import scala.collection.mutable

/**
 * A portable sbt plugin for generating aggregated HTML test reports.
 *
 * This plugin automatically discovers test results from all modules in your project
 * and generates a beautiful, comprehensive HTML report with statistics, timing information,
 * and detailed failure messages.
 *
 * ==Usage==
 *
 * After running your tests, generate the report:
 * {{{
 * sbt test
 * sbt generateTestReport
 * }}}
 *
 * The report will be generated at `target/test-reports/test-report.html`
 *
 * ==Configuration==
 *
 * Optional settings can be configured in your `build.sbt`:
 * {{{
 * // Customize the report title (default: project name)
 * testReportTitle := "My Project"
 *
 * // Customize output directory (default: target/test-reports)
 * testReportOutputDir := target.value / "custom-reports"
 * }}}
 *
 * ==Portability==
 *
 * This plugin is fully portable and can be used in any sbt project:
 * - No hardcoded project names or package names
 * - Automatically discovers all modules and test results
 * - Works with any test framework that generates JUnit XML reports
 *
 * Simply copy this file to your project's `project/` directory and it will auto-enable.
 *
 * ==Features==
 * - Aggregated results across all modules
 * - Pass rate visualization with color-coded indicators
 * - Module-level and suite-level breakdowns
 * - Expandable test case details with timing and error messages
 * - Modern, responsive HTML design
 *
 * @author TestReportPlugin
 * @since 1.0
 */
object TestReportPlugin extends AutoPlugin {

  override def trigger = allRequirements

  object autoImport {

    val generateTestReport =
      taskKey[File]("Generate aggregated HTML test report")

    val testReportOutputDir =
      settingKey[File]("Directory for test report output")
    val testReportTitle = settingKey[String]("Title for the test report")
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    testReportOutputDir := target.value / "test-reports",
    testReportTitle := name.value,
    generateTestReport := {
      val log = streams.value.log
      val outputDir = testReportOutputDir.value
      val baseDir = (ThisBuild / baseDirectory).value
      val reportTitle = testReportTitle.value

      log.info("Generating aggregated test report...")

      // Ensure output directory exists
      IO.createDirectory(outputDir)

      // Find all test result XML files across all modules
      val testResultFiles = findTestResults(baseDir, log)

      if (testResultFiles.isEmpty) {
        log.warn(
          "No test results found. Run 'test' first to generate test reports."
        )
      } else {
        log.info(s"Found ${testResultFiles.length} test result files")
      }

      // Parse test results
      val testResults = testResultFiles.flatMap(parseTestResultXml)

      // Generate HTML report
      val htmlFile = outputDir / "test-report.html"
      val html = generateHtmlReport(testResults, reportTitle)
      IO.write(htmlFile, html)

      log.info(s"Test report generated at: ${htmlFile.absolutePath}")
      htmlFile
    }
  )

  private def findTestResults(baseDir: File, log: Logger): Seq[File] = {
    val targetDirs = (baseDir ** "target").get.filter(_.isDirectory)
    val xmlFiles = targetDirs.flatMap { targetDir =>
      val testReportsDir = targetDir / "test-reports"
      if (testReportsDir.exists()) {
        (testReportsDir ** "*.xml").get
      } else {
        Seq.empty
      }
    }
    xmlFiles
  }

  private case class TestResult(
      moduleName: String,
      suiteName: String,
      testCount: Int,
      failures: Int,
      errors: Int,
      skipped: Int,
      time: Double,
      testCases: Seq[TestCase])

  private case class TestCase(
      name: String,
      className: String,
      time: Double,
      status: String, // "passed", "failed", "error", "skipped"
      message: Option[String] = None)

  private def parseTestResultXml(xmlFile: File): Option[TestResult] = {
    try {
      val xml = scala.xml.XML.loadFile(xmlFile)
      val moduleName = extractModuleName(xmlFile)
      val suiteName = (xml \ "@name").text
      val testCount = (xml \ "@tests").text.toInt
      val failures = (xml \ "@failures").text.toInt
      val errors = (xml \ "@errors").text.toInt
      val skipped = (xml \ "@skipped").text.toInt
      val time =
        try { (xml \ "@time").text.toDouble }
        catch { case _: NumberFormatException => 0.0 }

      val testCases = (xml \ "testcase").map { tc =>
        val name = (tc \ "@name").text
        val className = (tc \ "@classname").text
        val testTime =
          try { (tc \ "@time").text.toDouble }
          catch { case _: NumberFormatException => 0.0 }

        val (status, message) = if ((tc \ "failure").nonEmpty) {
          ("failed", Some((tc \ "failure").text))
        } else if ((tc \ "error").nonEmpty) {
          ("error", Some((tc \ "error").text))
        } else if ((tc \ "skipped").nonEmpty) {
          ("skipped", Some((tc \ "skipped").text))
        } else {
          ("passed", None)
        }

        TestCase(name, className, testTime, status, message)
      }

      Some(
        TestResult(
          moduleName,
          suiteName,
          testCount,
          failures,
          errors,
          skipped,
          time,
          testCases
        )
      )
    } catch {
      case e: Exception =>
        None
    }
  }

  private def extractModuleName(xmlFile: File): String = {
    val pathParts = xmlFile.getPath.split(File.separatorChar)
    val targetIndex = pathParts.lastIndexOf("target")
    if (targetIndex > 0) {
      pathParts(targetIndex - 1)
    } else {
      "unknown"
    }
  }

  private def generateHtmlReport(
      results: Seq[TestResult],
      projectTitle: String
    ): String = {
    val totalTests = results.map(_.testCount).sum
    val totalFailures = results.map(_.failures).sum
    val totalErrors = results.map(_.errors).sum
    val totalSkipped = results.map(_.skipped).sum
    val totalTime = results.map(_.time).sum
    val totalPassed = totalTests - totalFailures - totalErrors - totalSkipped

    val passRate =
      if (totalTests > 0) (totalPassed * 100.0 / totalTests) else 0.0

    val moduleResults = results.groupBy(_.moduleName).toSeq.sortBy(_._1)

    s"""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>$projectTitle Test Report</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { 
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
            background: #f5f5f5;
            padding: 20px;
        }
        .container { max-width: 1400px; margin: 0 auto; }
        h1 { 
            color: #333; 
            margin-bottom: 30px;
            font-size: 2.5em;
            text-align: center;
        }
        .summary {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        .summary-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        .stat-card {
            padding: 20px;
            border-radius: 6px;
            text-align: center;
        }
        .stat-card.total { background: #e3f2fd; border-left: 4px solid #2196F3; }
        .stat-card.passed { background: #e8f5e9; border-left: 4px solid #4CAF50; }
        .stat-card.failed { background: #ffebee; border-left: 4px solid #f44336; }
        .stat-card.error { background: #fff3e0; border-left: 4px solid #ff9800; }
        .stat-card.skipped { background: #f5f5f5; border-left: 4px solid #9e9e9e; }
        .stat-number { font-size: 2.5em; font-weight: bold; margin-bottom: 5px; }
        .stat-label { font-size: 0.9em; color: #666; text-transform: uppercase; }
        .pass-rate {
            font-size: 3em;
            font-weight: bold;
            text-align: center;
            margin: 20px 0;
            color: ${if (passRate >= 90) "#4CAF50"
      else if (passRate >= 70) "#ff9800"
      else "#f44336"};
        }
        .module-section {
            background: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .module-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #eee;
        }
        .module-name {
            font-size: 1.5em;
            font-weight: bold;
            color: #333;
        }
        .module-stats {
            display: flex;
            gap: 20px;
            font-size: 0.9em;
        }
        .suite-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        .suite-table th {
            background: #f5f5f5;
            padding: 12px;
            text-align: left;
            font-weight: 600;
            color: #555;
            border-bottom: 2px solid #ddd;
        }
        .suite-table td {
            padding: 10px 12px;
            border-bottom: 1px solid #eee;
        }
        .suite-table tr:hover {
            background: #f9f9f9;
        }
        .status-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 0.85em;
            font-weight: 600;
        }
        .status-passed { background: #4CAF50; color: white; }
        .status-failed { background: #f44336; color: white; }
        .status-error { background: #ff9800; color: white; }
        .status-skipped { background: #9e9e9e; color: white; }
        .test-details {
            margin-top: 10px;
            padding: 10px;
            background: #f9f9f9;
            border-radius: 4px;
            display: none;
        }
        .expandable { cursor: pointer; }
        .expandable:hover { background: #f5f5f5; }
        .timestamp {
            text-align: center;
            color: #999;
            margin-top: 30px;
            font-size: 0.9em;
        }
    </style>
    <script>
        function toggleDetails(id) {
            const details = document.getElementById(id);
            details.style.display = details.style.display === 'none' ? 'block' : 'none';
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>🧪 $projectTitle Test Report</h1>
        
        <div class="summary">
            <h2>Overall Summary</h2>
            <div class="pass-rate">${f"$passRate%.1f"}% Pass Rate</div>
            <div class="summary-grid">
                <div class="stat-card total">
                    <div class="stat-number">$totalTests</div>
                    <div class="stat-label">Total Tests</div>
                </div>
                <div class="stat-card passed">
                    <div class="stat-number">$totalPassed</div>
                    <div class="stat-label">Passed</div>
                </div>
                <div class="stat-card failed">
                    <div class="stat-number">$totalFailures</div>
                    <div class="stat-label">Failed</div>
                </div>
                <div class="stat-card error">
                    <div class="stat-number">$totalErrors</div>
                    <div class="stat-label">Errors</div>
                </div>
                <div class="stat-card skipped">
                    <div class="stat-number">$totalSkipped</div>
                    <div class="stat-label">Skipped</div>
                </div>
            </div>
            <p style="text-align: center; margin-top: 20px; color: #666;">
                Total Time: ${f"$totalTime%.2f"}s
            </p>
        </div>

        ${moduleResults.map {
        case (moduleName, moduleTests) =>
          val modTests = moduleTests.map(_.testCount).sum
          val modFailures = moduleTests.map(_.failures).sum
          val modErrors = moduleTests.map(_.errors).sum
          val modSkipped = moduleTests.map(_.skipped).sum
          val modPassed = modTests - modFailures - modErrors - modSkipped
          val modTime = moduleTests.map(_.time).sum

          s"""<div class="module-section">
            <div class="module-header">
                <div class="module-name">📦 $moduleName</div>
                <div class="module-stats">
                    <span>✅ $modPassed passed</span>
                    <span>❌ $modFailures failed</span>
                    <span>⚠️ $modErrors errors</span>
                    <span>⏭️ $modSkipped skipped</span>
                    <span>⏱️ ${f"$modTime%.2f"}s</span>
                </div>
            </div>
            <table class="suite-table">
                <thead>
                    <tr>
                        <th>Test Suite</th>
                        <th style="text-align: center;">Tests</th>
                        <th style="text-align: center;">Passed</th>
                        <th style="text-align: center;">Failed</th>
                        <th style="text-align: center;">Errors</th>
                        <th style="text-align: center;">Skipped</th>
                        <th style="text-align: right;">Time (s)</th>
                        <th style="text-align: center;">Status</th>
                    </tr>
                </thead>
                <tbody>
                    ${moduleTests.zipWithIndex.map {
              case (suite, idx) =>
                val passed =
                  suite.testCount - suite.failures - suite.errors - suite.skipped
                val status =
                  if (suite.failures > 0 || suite.errors > 0) "failed"
                  else "passed"
                val statusBadge =
                  if (status == "failed") "status-failed" else "status-passed"
                val detailsId =
                  s"details-${moduleName.replaceAll("[^a-zA-Z0-9]", "_")}-$idx"

                s"""<tr class="expandable" onclick="toggleDetails('$detailsId')">
                        <td><strong>${escapeHtml(suite.suiteName)}</strong></td>
                        <td style="text-align: center;">${suite.testCount}</td>
                        <td style="text-align: center;">$passed</td>
                        <td style="text-align: center;">${suite.failures}</td>
                        <td style="text-align: center;">${suite.errors}</td>
                        <td style="text-align: center;">${suite.skipped}</td>
                        <td style="text-align: right;">${f"${suite.time}%.3f"}</td>
                        <td style="text-align: center;">
                            <span class="status-badge $statusBadge">${status.toUpperCase}</span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8">
                            <div id="$detailsId" class="test-details">
                                <h4>Test Cases:</h4>
                                ${suite.testCases.map { tc =>
                    val statusClass = tc.status match {
                      case "passed"  => "status-passed"
                      case "failed"  => "status-failed"
                      case "error"   => "status-error"
                      case "skipped" => "status-skipped"
                      case _         => ""
                    }
                    s"""<div style="margin: 5px 0; padding: 8px; background: white; border-radius: 4px;">
                                    <span class="status-badge $statusClass">${tc.status.toUpperCase}</span>
                                    <strong>${escapeHtml(tc.name)}</strong> 
                                    <span style="color: #666;">(${f"${tc.time}%.3f"}s)</span>
                                    ${tc.message
                        .map(msg =>
                          s"<pre style='margin-top: 5px; padding: 8px; background: #f5f5f5; overflow-x: auto; font-size: 0.85em;'>${escapeHtml(msg.take(500))}</pre>"
                        )
                        .getOrElse("")}
                                  </div>"""
                  }.mkString}
                            </div>
                        </td>
                    </tr>"""
            }.mkString}
                </tbody>
            </table>
        </div>"""
      }.mkString}

        <div class="timestamp">
            Generated on ${java.time.LocalDateTime.now().toString}
        </div>
    </div>
</body>
</html>"""
  }

  private def escapeHtml(str: String): String = {
    str
      .replaceAll("&", "&amp;")
      .replaceAll("<", "&lt;")
      .replaceAll(">", "&gt;")
      .replaceAll("\"", "&quot;")
      .replaceAll("'", "&#39;")
  }
}
