#!/usr/bin/env python3

import sys
import os
import xml.etree.ElementTree as ET
from collections import defaultdict

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

def parse_all_tests(xml_files):
    """Parse all test cases and organize by suite name"""
    tests_by_suite = defaultdict(lambda: {'module': None, 'tests': set()})
    
    for xml_file in xml_files:
        try:
            tree = ET.parse(xml_file)
            root = tree.getroot()
            
            module_name = extract_module_name(xml_file)
            suite_name = root.get('name', '')
            
            # Normalize module name (remove spark version suffixes for comparison)
            normalized_module = module_name.replace('-spark40', '').replace('-spark34', '').replace('-spark33', '')
            
            for tc in root.findall('testcase'):
                test_name = tc.get('name', '')
                
                # Filter out SuiteSelector entries
                if 'It is not a test it is a sbt.testing.SuiteSelector' in test_name:
                    continue
                
                # Create a unique test identifier
                test_id = f"{suite_name}::{test_name}"
                
                if tests_by_suite[suite_name]['module'] is None:
                    tests_by_suite[suite_name]['module'] = normalized_module
                
                tests_by_suite[suite_name]['tests'].add(test_name)
        
        except Exception as e:
            continue
    
    return tests_by_suite

def generate_missing_tests_report(current_tests, legacy_tests):
    """Generate a report showing missing tests"""
    
    lines = []
    lines.append("# Missing Tests Comparison\n\n")
    lines.append("**Analysis of test differences between Current and Legacy projects**\n\n")
    lines.append("---\n\n")
    
    # Get all suite names
    all_suites = sorted(set(current_tests.keys()) | set(legacy_tests.keys()))
    
    # Collect all differences
    missing_in_current = []
    missing_in_legacy = []
    suite_only_in_current = []
    suite_only_in_legacy = []
    
    for suite_name in all_suites:
        current_suite = current_tests.get(suite_name)
        legacy_suite = legacy_tests.get(suite_name)
        
        if current_suite and legacy_suite:
            # Both have the suite, check for missing tests
            current_test_set = current_suite['tests']
            legacy_test_set = legacy_suite['tests']
            
            # Tests in legacy but not in current
            missing_curr = legacy_test_set - current_test_set
            for test in sorted(missing_curr):
                missing_in_current.append({
                    'suite': suite_name,
                    'test': test,
                    'module': legacy_suite['module']
                })
            
            # Tests in current but not in legacy
            missing_leg = current_test_set - legacy_test_set
            for test in sorted(missing_leg):
                missing_in_legacy.append({
                    'suite': suite_name,
                    'test': test,
                    'module': current_suite['module']
                })
        
        elif current_suite and not legacy_suite:
            # Suite only in current
            for test in sorted(current_suite['tests']):
                suite_only_in_current.append({
                    'suite': suite_name,
                    'test': test,
                    'module': current_suite['module']
                })
        
        elif legacy_suite and not current_suite:
            # Suite only in legacy
            for test in sorted(legacy_suite['tests']):
                suite_only_in_legacy.append({
                    'suite': suite_name,
                    'test': test,
                    'module': legacy_suite['module']
                })
    
    # Summary statistics
    lines.append("## Summary\n\n")
    lines.append(f"- **Tests in Current but missing in Legacy:** {len(missing_in_legacy) + len(suite_only_in_current)}\n")
    lines.append(f"- **Tests in Legacy but missing in Current:** {len(missing_in_current) + len(suite_only_in_legacy)}\n")
    lines.append(f"- **Test suites only in Current:** {len(set(t['suite'] for t in suite_only_in_current))}\n")
    lines.append(f"- **Test suites only in Legacy:** {len(set(t['suite'] for t in suite_only_in_legacy))}\n\n")
    
    # Combined table of all missing tests
    lines.append("## All Missing Tests (Single Table)\n\n")
    lines.append("| Status | Module | Test Suite | Test Name |\n")
    lines.append("|--------|--------|------------|----------|\n")
    
    # Add tests missing in current (present in legacy)
    for item in sorted(missing_in_current, key=lambda x: (x['module'], x['suite'], x['test'])):
        lines.append(f"| ⚠️ **Missing in Current** | `{item['module']}` | `{item['suite']}` | {item['test']} |\n")
    
    # Add entire suites only in legacy
    for item in sorted(suite_only_in_legacy, key=lambda x: (x['module'], x['suite'], x['test'])):
        lines.append(f"| ⚠️ **Missing in Current** | `{item['module']}` | `{item['suite']}` | {item['test']} |\n")
    
    # Add tests only in current (new tests)
    for item in sorted(missing_in_legacy, key=lambda x: (x['module'], x['suite'], x['test'])):
        lines.append(f"| ✅ **New in Current** | `{item['module']}` | `{item['suite']}` | {item['test']} |\n")
    
    # Add entire suites only in current
    for item in sorted(suite_only_in_current, key=lambda x: (x['module'], x['suite'], x['test'])):
        lines.append(f"| ✅ **New in Current** | `{item['module']}` | `{item['suite']}` | {item['test']} |\n")
    
    lines.append("\n")
    
    # Detailed breakdown by category
    if missing_in_current or suite_only_in_legacy:
        lines.append("## Tests Present in Legacy but Missing in Current\n\n")
        lines.append(f"**Total: {len(missing_in_current) + len(suite_only_in_legacy)} tests**\n\n")
        
        if missing_in_current:
            lines.append("### Within Existing Test Suites\n\n")
            current_module = None
            for item in sorted(missing_in_current, key=lambda x: (x['module'], x['suite'], x['test'])):
                if current_module != item['module']:
                    current_module = item['module']
                    lines.append(f"\n#### Module: `{current_module}`\n\n")
                lines.append(f"- **{item['suite']}**: `{item['test']}`\n")
        
        if suite_only_in_legacy:
            lines.append("\n### Entire Test Suites Not in Current\n\n")
            suites_by_module = defaultdict(lambda: defaultdict(list))
            for item in suite_only_in_legacy:
                suites_by_module[item['module']][item['suite']].append(item['test'])
            
            for module in sorted(suites_by_module.keys()):
                lines.append(f"\n#### Module: `{module}`\n\n")
                for suite in sorted(suites_by_module[module].keys()):
                    tests = suites_by_module[module][suite]
                    lines.append(f"- **{suite}** ({len(tests)} tests)\n")
                    for test in sorted(tests):
                        lines.append(f"  - `{test}`\n")
        
        lines.append("\n")
    
    if missing_in_legacy or suite_only_in_current:
        lines.append("## Tests Present in Current but Not in Legacy\n\n")
        lines.append(f"**Total: {len(missing_in_legacy) + len(suite_only_in_current)} tests**\n\n")
        
        if missing_in_legacy:
            lines.append("### Within Existing Test Suites\n\n")
            current_module = None
            for item in sorted(missing_in_legacy, key=lambda x: (x['module'], x['suite'], x['test'])):
                if current_module != item['module']:
                    current_module = item['module']
                    lines.append(f"\n#### Module: `{current_module}`\n\n")
                lines.append(f"- **{item['suite']}**: `{item['test']}`\n")
        
        if suite_only_in_current:
            lines.append("\n### Entire Test Suites Only in Current\n\n")
            suites_by_module = defaultdict(lambda: defaultdict(list))
            for item in suite_only_in_current:
                suites_by_module[item['module']][item['suite']].append(item['test'])
            
            for module in sorted(suites_by_module.keys()):
                lines.append(f"\n#### Module: `{module}`\n\n")
                for suite in sorted(suites_by_module[module].keys()):
                    tests = suites_by_module[module][suite]
                    lines.append(f"- **{suite}** ({len(tests)} tests)\n")
                    for test in sorted(tests):
                        lines.append(f"  - `{test}`\n")
        
        lines.append("\n")
    
    lines.append("---\n\n")
    lines.append("*Generated by Missing Tests Comparison Tool*\n")
    
    return ''.join(lines)

def main():
    if len(sys.argv) < 3:
        print("Usage: python3 compareMissingTests.py <current_project_dir> <legacy_project_dir>")
        sys.exit(1)
    
    current_project_dir = sys.argv[1]
    legacy_project_dir = sys.argv[2]
    
    print("Scanning current project for test results...")
    current_xml_files = find_test_results(current_project_dir)
    print(f"Found {len(current_xml_files)} test result files in current project")
    
    print("Scanning legacy project for test results...")
    legacy_xml_files = find_test_results(legacy_project_dir)
    print(f"Found {len(legacy_xml_files)} test result files in legacy project")
    
    print("Parsing current project tests...")
    current_tests = parse_all_tests(current_xml_files)
    total_current = sum(len(suite['tests']) for suite in current_tests.values())
    print(f"Found {total_current} tests across {len(current_tests)} test suites in current project")
    
    print("Parsing legacy project tests...")
    legacy_tests = parse_all_tests(legacy_xml_files)
    total_legacy = sum(len(suite['tests']) for suite in legacy_tests.values())
    print(f"Found {total_legacy} tests across {len(legacy_tests)} test suites in legacy project")
    
    print("Generating missing tests report...")
    report = generate_missing_tests_report(current_tests, legacy_tests)
    
    output_file = os.path.join(current_project_dir, "MISSING_TESTS_REPORT.md")
    with open(output_file, 'w') as f:
        f.write(report)
    
    print(f"\n✅ Report generated at: {output_file}")

if __name__ == '__main__':
    main()
