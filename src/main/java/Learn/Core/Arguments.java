package Learn.Core;

import Learn.*;
import Learn.Networking.networkMain;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Arguments {
    // Static method to check if a specific argument is present in the array
    public static boolean argumentCheck(String argument, String[] args) {
        for (String arg : args) {
            if (arg.equals(argument)) {
                return true;
            }
        }
        return false;
    }

    // Method to handle arguments and perform actions
    public static void handleArguments(String[] args, Scanner scanner, IOHandler.IO io, Window window, long totalMemoryMB, boolean runningOnTermux, CountDownLatch latch) throws InterruptedException {
        // Check for multiple arguments
        boolean hasHelp = argumentCheck("help", args);
        boolean hasProperties = argumentCheck("properties", args);
        boolean hasWindow = argumentCheck("window", args);
        boolean hasHexadecimal = argumentCheck("hexadecimal", args);
        boolean hasCredits = argumentCheck("credits", args);
        boolean hasChangelog = argumentCheck("changelog", args);
        boolean hasSnake = argumentCheck("snake", args);
        boolean hasDebug = argumentCheck("debug", args);
        boolean enableANSI = argumentCheck("-ANSI", args);  // Check for ANSI argument
        boolean hasPing = argumentCheck("ping", args);
        
        
        // Initialize Learn.Logger with ANSI support if enabled
        Logger.initialize(enableANSI);

        // Handle arguments
        if (hasHelp) {
            System.out.println("Version 4.2.2");
            System.out.println("properties - this shows properties of files, not folders.");
            System.out.println("window - opens the test window.");
            System.out.println("credits - shows credits.");
            System.out.println("changelog - shows the changelog.");
            System.out.println("snake - launches the game snake.");
            System.out.println("debug - checks if debug is enabled or not");
            System.out.println("That's all lol.");
            System.exit(0);
        }
        
        if (hasPing) {
            System.out.println("What ip do you want to ping, full address please");
            String userURLNAME = scanner.nextLine();
            new networkMain(userURLNAME);
            
        }

        if (hasProperties) {
            System.out.print("Tell me the unit to use as size: ");
            String UserpropertiesFileSize = scanner.nextLine();
            if (!"MB".equals(UserpropertiesFileSize) && !"KB".equals(UserpropertiesFileSize) && !"B".equals(UserpropertiesFileSize)) {
                System.out.println("ERROR: The file unit provided is not a valid unit.");
                System.exit(1);
            }
            System.out.print("Tell me the file you want to get the properties of: ");
            String UserpropertiesFile = scanner.nextLine();
            System.out.println("K got it! Here are the properties");
            System.out.println("Filename " + UserpropertiesFile);
            io.FileSize(UserpropertiesFileSize, UserpropertiesFile);
            System.exit(0);
        }

        if (hasWindow) {
            if (totalMemoryMB >= 16) {
                if (!runningOnTermux) {
                    Logger.info("Learn.Core.Main", "Learn.Window Launching!");
                    window.StartWindow("Just Learn java 4.1.1(Test Learn.Window)");
                    Logger.info("Learn.Core.Main", "Not a termux environment, continuing with window launch.");
                    latch.await(); // Wait until the window is closed
                } else {
                    Logger.error("Learn.Core.Main", "Learn.Window launch canceled due to being run in a Termux environment.");
                    System.exit(1);
                }
            } else {
                Logger.error("Learn.Core.Main", "Memory amount does not meet requirements to launch window.");
                System.out.println("ERROR: Memory amount does not meet requirements to launch window.");
                System.out.println("Sorry try launching the window with at least 16 MB of memory to the jar.");
                System.exit(1);
            }
        }

        if (hasHexadecimal) {
            System.out.println("What file do you want to get the hex data out of?");
            String FileObjectHexData = scanner.nextLine();
            if (io.isFileEmpty(FileObjectHexData)) {
                Logger.error("Learn.Core.Main - Learn.HexadecimalHandler", "File is empty, unable to retrieve any data.");
                System.exit(1);
            } else if (io.FileExists(FileObjectHexData)) {
                HexadecimalHandler.handleFile(FileObjectHexData);
                System.exit(0);
            }
        }

        if (hasCredits) {
            ConsoleManagement.clearConsole();
            Logger.info("Learn.Core.Arguments", "Displaying credits!");
            System.out.println("97% coded by Bluelist");
            System.out.println("3% Code given by ChatGPT and thank you to ChatGPT for helping me :D");
            System.exit(0);
        }

        if (hasChangelog) {
            System.out.println("Changes in version 4.2.2.0084-dev2:");
                System.out.println("Removed ANSI.");
                System.out.println("Added Update checking");
                System.out.println("Added ping option, just make sure to use // when giving it a website, it will also work with an ip.");
                System.out.println("Removed termux check:D (Be free, just note that it will not be able to start a window).");
                System.out.println("Re-organized project structure.");
            System.exit(0);
        }

        if (hasSnake) {
            Logger.info("Learn.Core.Main", "Starting method Learn.SnakeGame.");
            String[] snakeGameArgs = {"snake"};
            SnakeGame.main(snakeGameArgs);
            latch.await();
        }

        if (hasDebug) {
            DebugMode debugMode = new DebugMode(); // Initialize Learn.DebugMode to use in debug check
            if (debugMode.isDebug()) {
                System.out.println("1) Force a random error, or fatal error.");
            } else {
                System.exit(0);
            }
        }
    }
}
