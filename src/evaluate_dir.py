import os

def count_lines_and_chars(directory):
    total_lines = 0
    total_chars = 0

    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                with open(file_path, 'r', encoding='utf-8') as f:
                    for line in f:
                        stripped_line = line.strip()
                        if stripped_line:  # Non-empty line
                            total_lines += 1
                            total_chars += len(stripped_line)

    return total_lines, total_chars

if __name__ == "__main__":
    directory = r"C:\Users\Rashi\Documents\Minecraft\mcp-reborn\Just-Learn-Java\src\main\java\Learn"
    lines, chars = count_lines_and_chars(directory)
    print(f"Total Lines: {lines}")
    print(f"Total Characters: {chars}")
    input("Press Enter to exit...")
