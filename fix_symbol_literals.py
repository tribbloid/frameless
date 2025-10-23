#!/usr/bin/env python3
import re
import sys

def fix_symbol_literals_in_file(file_path):
    """Fix symbol literals in a Scala file, avoiding string literals and test names."""
    with open(file_path, 'r') as f:
        content = f.read()

    # Pattern to match symbol literals like 'a, '_1, 'name
    # but not inside string literals (lines with "test(" or containing quotes)
    lines = content.split('\n')
    fixed_lines = []

    for line in lines:
        # Skip lines that are test names or contain string literals with quotes
        if ('test("' in line or
            '"""' in line or
            line.strip().startswith('"') or
            ('"' in line and "'" in line and line.count('"') >= 2)):
            fixed_lines.append(line)
            continue

        # Replace symbol literals with Symbol() calls
        # Pattern: 'word where word starts with letter or underscore
        fixed_line = re.sub(r"(?<![\w\"'])'([a-zA-Z_][a-zA-Z0-9_]*)\b", r'Symbol("\1")', line)
        fixed_lines.append(fixed_line)

    fixed_content = '\n'.join(fixed_lines)

    if fixed_content != content:
        with open(file_path, 'w') as f:
            f.write(fixed_content)
        print(f"Fixed symbol literals in {file_path}")
        return True
    else:
        print(f"No changes needed in {file_path}")
        return False

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python3 fix_symbol_literals.py <file_path>")
        sys.exit(1)

    file_path = sys.argv[1]
    fix_symbol_literals_in_file(file_path)