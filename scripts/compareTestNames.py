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

def parse_test_names(xml_file):
    """Extract all test names from a test result XML file."""
    tests = []
    try:
        tree = ET.parse(xml_file)
        root = tree.getroot()
        
        module_name = extract_module_name(xml_file)
        suite_name = root.get('name', '')
        
        for tc in root.findall('testcase'):
            test_name = tc.get('name', '')
            classname = tc.get('classname', '')
            
            # Filter out SuiteSelector entries
            if 'It is not a test it is a sbt.testing.SuiteSelector' in test_name:
                continue
            
            # Create a unique test identifier
            full_test_name = f"{classname}.{test_name}"
            tests.append({
                'module': module_name,
                'suite': suite_name,
                'class': classname,
                'test': test_name,
                'full_name': full_test_name
            })
    except Exception as e:
        pass
    
    return tests

def collect_all_tests(project_dir, project_name):
    """Collect all test names from a project."""
    xml_files = find_test_results(project_dir)
    all_tests = []
    
    for xml_file in xml_files:
        tests = parse_test_names(xml_file)
        all_tests.extend(tests)
    
    # Create a set of unique test identifiers
    test_set = {test['full_name'] for test in all_tests}
    
    # Create a mapping from test name to test info
    test_map = {}
    for test in all_tests:
        if test['full_name'] not in test_map:
            test_map[test['full_name']] = test
    
    return test_set, test_map, all_tests

def generate_comparison_table(current_tests_set, current_tests_map, legacy_tests_set, legacy_tests_map):
    """Generate a markdown table comparing tests."""
    
    # Find missing tests
    only_in_current = current_tests_set - legacy_tests_set
    only_in_legacy = legacy_tests_set - current_tests_set
    in_both = current_tests_set & legacy_tests_set
    
    lines = []
    lines.append("# Detailed Test Comparison: Missing Tests\n\n")
    lines.append(f"**Total tests in Current:** {len(current_tests_set)}\n")
    lines.append(f"**Total tests in Legacy:** {len(legacy_tests_set)}\n")
    lines.append(f"**Tests in both:** {len(in_both)}\n")
    lines.append(f"**Only in Current:** {len(only_in_current)}\n")
    lines.append(f"**Only in Legacy:** {len(only_in_legacy)}\n\n")
    
    lines.append("---\n\n")
    
    # Create a comprehensive comparison table
    lines.append("## Complete Test Comparison Table\n\n")
    lines.append("| Test Name in Legacy | Test Name in Current | Missing In |\n")
    lines.append("|---------------------|----------------------|------------|\n")
    
    # Collect all unique test names
    all_test_names = sorted(current_tests_set | legacy_tests_set)
    
    for test_name in all_test_names:
        in_current = test_name in current_tests_set
        in_legacy = test_name in legacy_tests_set
        
        legacy_name = test_name if in_legacy else "-"
        current_name = test_name if in_current else "-"
        
        if in_current and in_legacy:
            missing = "None (in both)"
        elif in_current and not in_legacy:
            missing = "**Legacy**"
        elif not in_current and in_legacy:
            missing = "**Current**"
        else:
            missing = "ERROR"
        
        lines.append(f"| `{legacy_name}` | `{current_name}` | {missing} |\n")
    
    lines.append("\n---\n\n")
    
    # Detailed breakdown by module
    lines.append("## Tests Only in Current Project (New Tests)\n\n")
    if only_in_current:
        # Group by module
        by_module = defaultdict(list)
        for test_name in sorted(only_in_current):
            test_info = current_tests_map[test_name]
            by_module[test_info['module']].append(test_info)
        
        for module in sorted(by_module.keys()):
            lines.append(f"### Module: `{module}` ({len(by_module[module])} new tests)\n\n")
            lines.append("| Test Class | Test Name |\n")
            lines.append("|------------|----------|\n")
            for test in sorted(by_module[module], key=lambda x: x['full_name']):
                lines.append(f"| `{test['class']}` | `{test['test']}` |\n")
            lines.append("\n")
    else:
        lines.append("*No new tests in current project.*\n\n")
    
    lines.append("---\n\n")
    
    # Tests only in legacy
    lines.append("## Tests Only in Legacy Project (Removed/Missing Tests)\n\n")
    if only_in_legacy:
        # Group by module
        by_module = defaultdict(list)
        for test_name in sorted(only_in_legacy):
            test_info = legacy_tests_map[test_name]
            by_module[test_info['module']].append(test_info)
        
        for module in sorted(by_module.keys()):
            lines.append(f"### Module: `{module}` ({len(by_module[module])} removed tests)\n\n")
            lines.append("| Test Class | Test Name |\n")
            lines.append("|------------|----------|\n")
            for test in sorted(by_module[module], key=lambda x: x['full_name']):
                lines.append(f"| `{test['class']}` | `{test['test']}` |\n")
            lines.append("\n")
    else:
        lines.append("*No tests removed from legacy project.*\n\n")
    
    lines.append("---\n\n")
    
    # Summary statistics by module
    lines.append("## Module-Level Test Count Comparison\n\n")
    
    # Get all modules from both projects
    current_by_module = defaultdict(set)
    legacy_by_module = defaultdict(set)
    
    for test_name in current_tests_set:
        test_info = current_tests_map[test_name]
        current_by_module[test_info['module']].add(test_name)
    
    for test_name in legacy_tests_set:
        test_info = legacy_tests_map[test_name]
        legacy_by_module[test_info['module']].add(test_name)
    
    all_modules = sorted(set(current_by_module.keys()) | set(legacy_by_module.keys()))
    
    lines.append("| Module | Tests in Current | Tests in Legacy | Difference |\n")
    lines.append("|--------|-----------------|-----------------|------------|\n")
    
    for module in all_modules:
        current_count = len(current_by_module.get(module, set()))
        legacy_count = len(legacy_by_module.get(module, set()))
        diff = current_count - legacy_count
        
        lines.append(f"| `{module}` | {current_count} | {legacy_count} | {diff:+d} |\n")
    
    lines.append("\n")
    
    return ''.join(lines)

def main():
    if len(sys.argv) < 3:
        print("Usage: python3 compareTestNames.py <current_project_dir> <legacy_project_dir>")
        sys.exit(1)
    
    current_project_dir = sys.argv[1]
    legacy_project_dir = sys.argv[2]
    
    print("Collecting test names from current project...")
    current_tests_set, current_tests_map, current_tests_list = collect_all_tests(current_project_dir, "Current")
    print(f"Found {len(current_tests_set)} unique tests in current project")
    
    print("Collecting test names from legacy project...")
    legacy_tests_set, legacy_tests_map, legacy_tests_list = collect_all_tests(legacy_project_dir, "Legacy")
    print(f"Found {len(legacy_tests_set)} unique tests in legacy project")
    
    print("\nGenerating detailed comparison table...")
    report = generate_comparison_table(current_tests_set, current_tests_map, legacy_tests_set, legacy_tests_map)
    
    output_file = os.path.join(current_project_dir, "TEST_NAMES_COMPARISON.md")
    with open(output_file, 'w') as f:
        f.write(report)
    
    print(f"\n✅ Detailed comparison report generated at: {output_file}")
    
    # Print summary
    only_in_current = current_tests_set - legacy_tests_set
    only_in_legacy = legacy_tests_set - current_tests_set
    in_both = current_tests_set & legacy_tests_set
    
    print("\nSummary:")
    print(f"  Tests in both projects: {len(in_both)}")
    print(f"  Tests only in Current: {len(only_in_current)}")
    print(f"  Tests only in Legacy: {len(only_in_legacy)}")

if __name__ == '__main__':
    main()
