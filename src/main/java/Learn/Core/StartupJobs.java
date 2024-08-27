package Learn.Core;

import Learn.LogFile;
import Learn.Logger;
import Learn.Networking.UpdateRetriever;
import Learn.Window;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class StartupJobs {





    public static void main(String[] args) {
        String directoryPath = "logs"; // Directory to be created

        Path path = Paths.get(directoryPath);



        Scanner scanner = null;
        try {

            scanner = new Scanner(System.in);
            IOHandler.IO io = new IOHandler.IO(); // Initialize Learn.Core.IOHandler.IO as needed
            Window window = new Window(); // Initialize Learn.Window as needed
            long totalMemoryMB = Runtime.getRuntime().totalMemory() / (1024 * 1024); // Get available memory in MB
            boolean runningOnTermux = false; // Example value; set based on your environment check
            CountDownLatch latch = new CountDownLatch(1); // Initialize CountDownLatch

            if (io.FileExists("config.txt")) {
                io.create("config.txt");
            } else {
                // do nothing xd
            }
            Files.createDirectories(Path.of("updates"));



            Arguments.handleArguments(args, scanner, io, window, totalMemoryMB, runningOnTermux, latch);

            if (Files.notExists(path)) {
                Files.createDirectories(path);
                Logger.info("Learn.Core.StartupJobs", "Directory created: " + directoryPath);
            } else {
                Logger.info("Learn.Core.StartupJobs", "Directory already exists: " + directoryPath);
            }
        } catch (IOException | InterruptedException e) {
            Logger.error("Learn.Core.StartupJobs", "Failed to create directory: " + e.getMessage());
        }
        LogFile.setupLogging();
        Logger.info("Learn.Core.StartupJobs", "Started setupLogging");


// Run UpdateRetriever on startup, cuz well it needs to lol
        if (PublicVariables.isDevVer) {
            System.out.println("Do you want to check for any updates? (Pre-release)");
            String updateUserCheck = scanner.nextLine();
            if (updateUserCheck.equalsIgnoreCase("yes")) {
                UpdateRetriever.fetchPreRelease(args); // Method to fetch the pre-release update
            } else {
                System.out.println("Assuming no.");
            }
        } else {
            System.out.println("Do you want to check for any updates? (Release)");
            String updateUserCheck = scanner.nextLine();
            if (updateUserCheck.equalsIgnoreCase("yes")) {
                UpdateRetriever.fetchRelease(args); // Method to fetch the regular release update
            } else {
                System.out.println("Assuming no.");
            }
        }

    }
}