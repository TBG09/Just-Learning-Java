import os
import base64
import zlib

def compress_and_encode(data):
    """Compress and encode data in Base64."""
    compressed_data = zlib.compress(data.encode())
    encoded_data = base64.b64encode(compressed_data).decode()
    return encoded_data

def process_java_files(directory):
    """Process all Java files in the given directory."""
    encoded_files = []
    
    # Iterate over all files in the directory
    for filename in os.listdir(directory):
        if filename.endswith(".java"):
            file_path = os.path.join(directory, filename)
            try:
                # Read file contents
                with open(file_path, 'r', encoding='utf-8') as file:
                    file_contents = file.read()
                
                # Compress and encode the file contents
                encoded_data = compress_and_encode(file_contents)
                
                # Append the encoded data with the filename as a header
                encoded_files.append(f"--- {filename} ---\n{encoded_data}\n")
            except Exception as e:
                print(f"An error occurred while processing file {filename}: {e}")
    
    return encoded_files

def save_encoded_files(encoded_files, output_file):
    """Save encoded file data to an output file."""
    try:
        with open(output_file, 'w', encoding='utf-8') as file:
            file.write("\n".join(encoded_files))
        print(f"Encoded data saved to {output_file}")
    except Exception as e:
        print(f"An error occurred while saving the file: {e}")

def main():
    directory = input("Enter the directory to scan for Java files: ").strip()
    output_file = input("Enter the path to save the encoded file: ").strip()

    encoded_files = process_java_files(directory)
    save_encoded_files(encoded_files, output_file)

if __name__ == "__main__":
    main()
