package Learn.Networking;

import Learn.Logger;
import Learn.Core.PublicVariables;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class UpdateRetriever {

    private static final String REPO_URL = "https://api.github.com/repos/TBG09/Just-Learning-Java/releases";
    private static final String DOWNLOAD_DIR = "updates/"; // Directory to download the JAR file to

    public static void fetchRelease(String[] args) {
        try {
            // Fetch the latest release information from GitHub
            JSONObject latestRelease = fetchLatestRelease();
            if (latestRelease == null) {
                Logger.warn("Learn.Networking.UpdateRetriever", "Failed to fetch latest release.");
                System.exit(1);
            }

            // Extract JAR download URL and file name
            String jarDownloadURL = getJarDownloadURL(latestRelease);
            if (jarDownloadURL == null) {
                Logger.warn("Learn.Networking.UpdateRetriever", "No JAR file found in the latest release.");
                System.exit(1);
            }

            String latestJarFileName = new File(jarDownloadURL).getName();
            String currentVersion = PublicVariables.VersionNum;

            // Check if the JAR file needs to be updated
            if (latestJarFileName.contains(currentVersion)) {
                Logger.info("Learn.Networking.UpdateRetriever", "No updates detected!");
                System.exit(0);
            }

            // Download the JAR file to the updates directory
            downloadJarFile(jarDownloadURL, DOWNLOAD_DIR + latestJarFileName);
            Logger.info("Learn.Networking.UpdateRetriever", "File " + latestJarFileName + " has been downloaded.");

            // Run the new JAR
            runNewJar(DOWNLOAD_DIR + latestJarFileName);

        } catch (IOException e) {
            Logger.error("Learn.Networking.UpdateRetriever", "An error occurred during update retrieval.");
            e.printStackTrace();
        }
    }

    public static void fetchPreRelease(String[] args) {
        try {
            // Fetch the latest pre-release information from GitHub
            JSONObject latestPreRelease = fetchLatestPreRelease();
            if (latestPreRelease == null) {
                Logger.warn("Learn.Networking.UpdateRetriever", "Failed to fetch latest pre-release.");
                System.exit(1);
            }

            // Extract JAR download URL and file name
            String jarDownloadURL = getJarDownloadURL(latestPreRelease);
            if (jarDownloadURL == null) {
                Logger.warn("Learn.Networking.UpdateRetriever", "No JAR file found in the latest pre-release.");
                System.exit(1);
            }

            String latestJarFileName = new File(jarDownloadURL).getName();
            String currentVersion = PublicVariables.VersionNum;

            // Check if the JAR file needs to be updated
            if (latestJarFileName.contains(currentVersion)) {
                Logger.info("Learn.Networking.UpdateRetriever", "No updates detected!");
                System.exit(0);
            }

            // Download the JAR file to the updates directory
            downloadJarFile(jarDownloadURL, DOWNLOAD_DIR + latestJarFileName);
            Logger.info("Learn.Networking.UpdateRetriever", "File " + latestJarFileName + " has been downloaded.");

            // Run the new JAR
            runNewJar(DOWNLOAD_DIR + latestJarFileName);

        } catch (IOException e) {
            Logger.error("Learn.Networking.UpdateRetriever", "An error occurred during update retrieval.");
            e.printStackTrace();
        }
    }

    private static JSONObject fetchLatestRelease() throws IOException {
        return fetchReleaseData(false); // Fetch the latest stable release
    }

    private static JSONObject fetchLatestPreRelease() throws IOException {
        return fetchReleaseData(true); // Fetch the latest pre-release
    }

    private static JSONObject fetchReleaseData(boolean preRelease) throws IOException {
        URL url = new URL(REPO_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            Logger.warn("Learn.Networking.UpdateRetriever", "Failed to fetch the release data. Response code: " + responseCode);
            return null;
        }

        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            String response = scanner.useDelimiter("\\A").next();
            JSONArray releases = new JSONArray(response);

            for (int i = 0; i < releases.length(); i++) {
                JSONObject release = releases.getJSONObject(i);
                if (preRelease == release.getBoolean("prerelease")) {
                    return release;
                }
            }

            return null; // No suitable release found
        }
    }

    private static String getJarDownloadURL(JSONObject release) {
        JSONArray assets = release.getJSONArray("assets");
        for (int i = 0; i < assets.length(); i++) {
            JSONObject asset = assets.getJSONObject(i);
            String name = asset.getString("name");
            if (name.endsWith(".jar")) {
                return asset.getString("browser_download_url");
            }
        }
        return null;
    }

    private static void downloadJarFile(String downloadURL, String filePath) throws IOException {
        URL url = new URL(downloadURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            Logger.warn("Learn.Networking.UpdateRetriever", "Failed to download the JAR file. Response code: " + responseCode);
            return;
        }

        try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             BufferedOutputStream out = new BufferedOutputStream(fileOutputStream, 1024)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void runNewJar(String jarFilePath) throws IOException {
        String command;
        String os = PublicVariables.osType; // Use osType from PublicVariables

        if (os.contains("win")) {
            command = "java -jar " + jarFilePath + " changelog";
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Print the output of the new JAR
            try (BufferedInputStream in = new BufferedInputStream(process.getInputStream())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    System.out.write(buffer, 0, bytesRead);
                }
            }
        } else {
            command = "java -jar " + jarFilePath + " changelog";
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Print the output of the new JAR
            try (BufferedInputStream in = new BufferedInputStream(process.getInputStream())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    System.out.write(buffer, 0, bytesRead);
                }
            }
        }

        // Exit the current JAR and keep the new one running
        System.exit(0);
    }
}
