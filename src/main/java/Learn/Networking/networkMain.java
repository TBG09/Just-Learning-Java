package Learn.Networking;

import Learn.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class networkMain {
    Logger logger = new Logger();

    public networkMain(String URLstring) {
        try {
            URL url = new URL(URLstring);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();

            // Log the response code
            Logger.info("App.Networking.networkMain", "Response Code: " + responseCode);
            if (responseCode == 200) {
                System.out.println("As said above, the response code is 200, meaning the ping was a success!");
            } else {
                Logger.warn("App.Networking.networkMain", "Response code is not 200, ping might have failed.");
                System.out.println("Response code is " + responseCode);
                System.exit(1);
            }

        } catch (IOException e) {
            Logger.error("App.Networking.networkMain", "An error occurred while trying to ping the URL.");
            e.printStackTrace(); // Print the exception stack trace to help with debugging
        }
    }

    // Method to download a file from a URL
    public void downloadFile(String fileURL, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
             FileOutputStream fos = new FileOutputStream(saveDir)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fos.write(dataBuffer, 0, bytesRead);
            }
        }
        Logger.info("App.Networking.networkMain", "File downloaded: " + saveDir);
    }
}
