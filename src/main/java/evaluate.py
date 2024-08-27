import os

def print_java_files_contents(directory='.'):
    output_lines = []
    total_lines = 0
    total_chars = 0

    for filename in os.listdir(directory):
        if filename.endswith('.java'):
            filepath = os.path.join(directory, filename)
            with open(filepath, 'r', encoding='utf-8') as file:
                contents = file.read()
                lines = contents.splitlines()
                non_empty_lines = [line for line in lines if line.strip()]

                num_lines = len(non_empty_lines)
                num_chars = len(contents)

                total_lines += num_lines
                total_chars += num_chars

                output_lines.append(f"{filename}:")
                output_lines.append(contents)
                output_lines.append('-' * 40)  # Separator for readability
                output_lines.append(f"Number of non-empty lines: {num_lines}")
                output_lines.append(f"Number of characters: {num_chars}")
                output_lines.append('-' * 40)  # Separator for readability

    # Ask user for output file name
    output_filename = input("Enter the name of the output file (e.g., output.txt): ")
    with open(output_filename, 'w', encoding='utf-8') as output_file:
        output_file.write('\n'.join(output_lines))

    print(f"Output written to {output_filename}")
    print(f"Total number of non-empty lines across all files: {total_lines}")
    print(f"Total number of characters across all files: {total_chars}")

    # Wait for user input before exiting
    input("Press Enter to exit...")

if __name__ == "__main__":
    print_java_files_contents()
