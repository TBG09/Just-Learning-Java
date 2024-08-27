package Learn;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HexadecimalHandler {
    // Read file and print its hexadecimal representation
    public static void handleFile(String filePath) {
        Logger.info("Learn.Core.HexadecimalHandler", "Handling file: " + filePath);
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            String hexString = bytesToHex(fileBytes);
            System.out.println(hexString);
        } catch (IOException e) {
            Logger.error("Learn.Core.HexadecimalHandler", "Failed to read file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Convert byte array to hexadecimal string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}