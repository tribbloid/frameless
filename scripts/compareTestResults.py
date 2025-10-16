#!/usr/bin/env python3

import sys
import os
import xml.etree.ElementTree as ET
from collections import defaultdict
from datetime import datetime

class TestCase:
    def __init__(self, name, classname, time, status, message=None):
        self.name = name
        self.classname = classname
        self.time = time
        self.status = status
        self.message = message

class TestResult:
    def __init__(self, module_name, suite_name, test_count, failures, errors, skipped, time, test_cases):
        self.module_name = module_name
        self.suite_name = suite_name
        self.test_count = test_count
        self.failures = failures
        self.errors = errors
        self.skipped = skipped
        self.time = time
        self.test_cases = test_cases

class ModuleStats:
    def __init__(self, tests=0, passed=0, failed=0, errors=0, skipped=0, time=0.0):
        self.tests = tests
        self.passed = passed
        self.failed = failed
        self.errors = errors
        self.skipped = skipped
        self.time = time

class ProjectStats:
    def __init__(self, project_name, total_tests, passed, failed, errors, skipped, total_time, module_results):
        self.project_name = project_name
        self.total_tests = total_tests
        self.passed = passed
        self.failed = failed
        self.errors = errors
        self.skipped = skipped
        self.total_time = total_time
        self.module_results = module_results

def find_test_results(base_dir):
    xml_files = []
    for root, dirs, files in os.walk(base_dir):
        if 'test-reports' in root:
            for file in files:
                if file.endswith('.xml'):
                    xml_files.append(os.path.join(root, file))
    return xml_files

def extract_module_name(xml_file):
    parts = xml_file.split(os.sep)
    try:
        target_idx = parts.index('target')
        if target_idx > 0:
            return parts[target_idx - 1]
    except ValueError:
        pass
    return 'unknown'

def parse_test_result_xml(xml_file):
    try:
        tree = ET.parse(xml_file)
        root = tree.getroot()
        
        module_name = extract_module_name(xml_file)
        suite_name = root.get('name', '')
        time = float(root.get('time', 0))
        
        test_cases = []
        for tc in root.findall('testcase'):
            name = tc.get('name', '')
            classname = tc.get('classname', '')
            
            if 'It is not a test it is a sbt.testing.SuiteSelector' in name:
                continue
            
            test_time = float(tc.get('time', 0))
            
            failure = tc.find('failure')
            error = tc.find('error')
            skipped = tc.find('skipped')
            
            if failure is not None:
                status = 'failed'
                message = failure.text
            elif error is not None:
                status = 'error'
                message = error.text
            elif skipped is not None:
                status = 'skipped'
                message = skipped.text
            else:
                status = 'passed'
                message = None
            
            test_cases.append(TestCase(name, classname, test_time, status, message))
        
        if not test_cases:
            return None
        
        test_count = len(test_cases)
        failures = sum(1 for tc in test_cases if tc.status == 'failed')
        errors = sum(1 for tc in test_cases if tc.status == 'error')
        skipped = sum(1 for tc in test_cases if tc.status == 'skipped')
        
        return TestResult(module_name, suite_name, test_count, failures, errors, skipped, time, test_cases)
    except Exception as e:
        return None

def aggregate_stats(project_name, results):
    total_tests = sum(r.test_count for r in results)
    total_failures = sum(r.failures for r in results)
    total_errors = sum(r.errors for r in results)
    total_skipped = sum(r.skipped for r in results)
    total_passed = total_tests - total_failures - total_errors - total_skipped
    total_time = sum(r.time for r in results)
    
    module_data = defaultdict(lambda: {'tests': 0, 'failures': 0, 'errors': 0, 'skipped': 0, 'time': 0.0})
    
    for r in results:
        module_data[r.module_name]['tests'] += r.test_count
        module_data[r.module_name]['failures'] += r.failures
        module_data[r.module_name]['errors'] += r.errors
        module_data[r.module_name]['skipped'] += r.skipped
        module_data[r.module_name]['time'] += r.time
    
    module_results = {}
    for mod_name, data in module_data.items():
        passed = data['tests'] - data['failures'] - data['errors'] - data['skipped']
        module_results[mod_name] = ModuleStats(
            data['tests'], passed, data['failures'], data['errors'], data['skipped'], data['time']
        )
    
    return ProjectStats(project_name, total_tests, total_passed, total_failures, total_errors, total_skipped, total_time, module_results)

def generate_markdown_report(current, legacy):
    lines = []
    
    lines.append("# Frameless Test Results Comparison\n\n")
    lines.append(f"**Generated:** {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n\n")
    lines.append("---\n\n")
    
    # Summary Section
    lines.append("## Executive Summary\n\n")
    
    current_pass_rate = (current.passed * 100.0 / current.total_tests) if current.total_tests > 0 else 0.0
    legacy_pass_rate = (legacy.passed * 100.0 / legacy.total_tests) if legacy.total_tests > 0 else 0.0
    
    lines.append("| Metric | Current Project | Legacy Project | Difference |\n")
    lines.append("|--------|----------------|----------------|------------|\n")
    lines.append(f"| **Total Tests** | {current.total_tests} | {legacy.total_tests} | {current.total_tests - legacy.total_tests:+d} |\n")
    lines.append(f"| **Passed** | {current.passed} | {legacy.passed} | {current.passed - legacy.passed:+d} |\n")
    lines.append(f"| **Failed** | {current.failed} | {legacy.failed} | {current.failed - legacy.failed:+d} |\n")
    lines.append(f"| **Errors** | {current.errors} | {legacy.errors} | {current.errors - legacy.errors:+d} |\n")
    lines.append(f"| **Skipped** | {current.skipped} | {legacy.skipped} | {current.skipped - legacy.skipped:+d} |\n")
    lines.append(f"| **Pass Rate** | {current_pass_rate:.2f}% | {legacy_pass_rate:.2f}% | {current_pass_rate - legacy_pass_rate:+.2f}% |\n")
    lines.append(f"| **Total Time** | {current.total_time:.2f}s | {legacy.total_time:.2f}s | {current.total_time - legacy.total_time:+.2f}s |\n\n")
    
    # Key Insights
    lines.append("## Key Insights\n\n")
    
    if current.total_tests != legacy.total_tests:
        change = current.total_tests - legacy.total_tests
        lines.append(f"- **Test Coverage Change:** {'+' if change > 0 else ''}{change} tests ({'increased' if change > 0 else 'decreased'} coverage)\n")
    
    if current.failed < legacy.failed:
        lines.append(f"- **Improved Stability:** {legacy.failed - current.failed} fewer test failures\n")
    elif current.failed > legacy.failed:
        lines.append(f"- **Regression Alert:** {current.failed - legacy.failed} additional test failures\n")
    
    if current_pass_rate > legacy_pass_rate:
        lines.append(f"- **Quality Improvement:** Pass rate improved by {current_pass_rate - legacy_pass_rate:.2f}%\n")
    elif current_pass_rate < legacy_pass_rate:
        lines.append(f"- **Quality Concern:** Pass rate decreased by {legacy_pass_rate - current_pass_rate:.2f}%\n")
    
    lines.append("\n")
    
    # Module-by-Module Comparison
    lines.append("## Module-by-Module Comparison\n\n")
    
    all_modules = sorted(set(current.module_results.keys()) | set(legacy.module_results.keys()))
    
    lines.append("| Module | Current Tests | Current Pass Rate | Legacy Tests | Legacy Pass Rate | Change |\n")
    lines.append("|--------|--------------|-------------------|--------------|------------------|--------|\n")
    
    for module in all_modules:
        curr_mod = current.module_results.get(module)
        leg_mod = legacy.module_results.get(module)
        
        if curr_mod and leg_mod:
            c_rate = (curr_mod.passed * 100.0 / curr_mod.tests) if curr_mod.tests > 0 else 0.0
            l_rate = (leg_mod.passed * 100.0 / leg_mod.tests) if leg_mod.tests > 0 else 0.0
            diff = c_rate - l_rate
            diff_str = f"{diff:+.1f}%"
            lines.append(f"| `{module}` | {curr_mod.tests} | {c_rate:.1f}% | {leg_mod.tests} | {l_rate:.1f}% | {diff_str} |\n")
        elif curr_mod:
            c_rate = (curr_mod.passed * 100.0 / curr_mod.tests) if curr_mod.tests > 0 else 0.0
            lines.append(f"| `{module}` | {curr_mod.tests} | {c_rate:.1f}% | - | - | **NEW** |\n")
        elif leg_mod:
            l_rate = (leg_mod.passed * 100.0 / leg_mod.tests) if leg_mod.tests > 0 else 0.0
            lines.append(f"| `{module}` | - | - | {leg_mod.tests} | {l_rate:.1f}% | **REMOVED** |\n")
    
    lines.append("\n")
    
    # Detailed Module Statistics
    lines.append("## Detailed Module Statistics\n\n")
    
    for module in all_modules:
        curr_mod = current.module_results.get(module)
        leg_mod = legacy.module_results.get(module)
        
        if curr_mod or leg_mod:
            lines.append(f"### Module: `{module}`\n\n")
            
            lines.append("| Metric | Current | Legacy | Change |\n")
            lines.append("|--------|---------|--------|--------|\n")
            
            if curr_mod and leg_mod:
                lines.append(f"| Tests | {curr_mod.tests} | {leg_mod.tests} | {curr_mod.tests - leg_mod.tests:+d} |\n")
                lines.append(f"| Passed | {curr_mod.passed} | {leg_mod.passed} | {curr_mod.passed - leg_mod.passed:+d} |\n")
                lines.append(f"| Failed | {curr_mod.failed} | {leg_mod.failed} | {curr_mod.failed - leg_mod.failed:+d} |\n")
                lines.append(f"| Errors | {curr_mod.errors} | {leg_mod.errors} | {curr_mod.errors - leg_mod.errors:+d} |\n")
                lines.append(f"| Skipped | {curr_mod.skipped} | {leg_mod.skipped} | {curr_mod.skipped - leg_mod.skipped:+d} |\n")
                lines.append(f"| Time | {curr_mod.time:.2f}s | {leg_mod.time:.2f}s | {curr_mod.time - leg_mod.time:+.2f}s |\n")
            elif curr_mod:
                lines.append(f"| Tests | {curr_mod.tests} | - | **NEW MODULE** |\n")
                lines.append(f"| Passed | {curr_mod.passed} | - | - |\n")
                lines.append(f"| Failed | {curr_mod.failed} | - | - |\n")
                lines.append(f"| Errors | {curr_mod.errors} | - | - |\n")
                lines.append(f"| Skipped | {curr_mod.skipped} | - | - |\n")
                lines.append(f"| Time | {curr_mod.time:.2f}s | - | - |\n")
            elif leg_mod:
                lines.append(f"| Tests | - | {leg_mod.tests} | **MODULE REMOVED** |\n")
                lines.append(f"| Passed | - | {leg_mod.passed} | - |\n")
                lines.append(f"| Failed | - | {leg_mod.failed} | - |\n")
                lines.append(f"| Errors | - | {leg_mod.errors} | - |\n")
                lines.append(f"| Skipped | - | {leg_mod.skipped} | - |\n")
                lines.append(f"| Time | - | {leg_mod.time:.2f}s | - |\n")
            
            lines.append("\n")
    
    # Recommendations
    lines.append("## Recommendations\n\n")
    
    if current.failed > legacy.failed:
        lines.append("- ⚠️ **High Priority:** Investigate the additional test failures\n")
    
    if current.errors > legacy.errors:
        lines.append("- ⚠️ **Critical:** Address the increase in test errors\n")
    
    if current_pass_rate >= 95:
        lines.append("- ✅ **Good:** Pass rate is excellent (≥95%)\n")
    elif current_pass_rate >= 90:
        lines.append("- ⚠️ **Moderate:** Pass rate is acceptable but could be improved (90-95%)\n")
    else:
        lines.append("- ❌ **Poor:** Pass rate needs immediate attention (<90%)\n")
    
    lines.append("\n---\n\n")
    lines.append("*Report generated by Frameless Test Comparison Tool*\n")
    
    return ''.join(lines)

def main():
    if len(sys.argv) < 3:
        print("Usage: python3 compareTestResults.py <current_project_dir> <legacy_project_dir>")
        sys.exit(1)
    
    current_project_dir = sys.argv[1]
    legacy_project_dir = sys.argv[2]
    
    print("Scanning current project for test results...")
    current_xml_files = find_test_results(current_project_dir)
    print(f"Found {len(current_xml_files)} test result files in current project")
    
    print("Scanning legacy project for test results...")
    legacy_xml_files = find_test_results(legacy_project_dir)
    print(f"Found {len(legacy_xml_files)} test result files in legacy project")
    
    print("Parsing current project results...")
    current_results = [r for r in (parse_test_result_xml(f) for f in current_xml_files) if r is not None]
    print(f"Parsed {len(current_results)} test suites from current project")
    
    print("Parsing legacy project results...")
    legacy_results = [r for r in (parse_test_result_xml(f) for f in legacy_xml_files) if r is not None]
    print(f"Parsed {len(legacy_results)} test suites from legacy project")
    
    print("Generating comparison report...")
    current_stats = aggregate_stats("Current Project", current_results)
    legacy_stats = aggregate_stats("Legacy Project", legacy_results)
    
    report = generate_markdown_report(current_stats, legacy_stats)
    
    output_file = os.path.join(current_project_dir, "TEST_COMPARISON_REPORT.md")
    with open(output_file, 'w') as f:
        f.write(report)
    
    print(f"\n✅ Report generated at: {output_file}")
    print("\nSummary:")
    current_pass_pct = (current_stats.passed * 100.0 / current_stats.total_tests) if current_stats.total_tests > 0 else 0.0
    legacy_pass_pct = (legacy_stats.passed * 100.0 / legacy_stats.total_tests) if legacy_stats.total_tests > 0 else 0.0
    print(f"  Current: {current_stats.total_tests} tests, {current_stats.passed} passed ({current_pass_pct:.1f}%)")
    print(f"  Legacy:  {legacy_stats.total_tests} tests, {legacy_stats.passed} passed ({legacy_pass_pct:.1f}%)")

if __name__ == '__main__':
    main()
