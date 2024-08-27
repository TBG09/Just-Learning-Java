package Learn.Core;

import Learn.DebugMode;
import Learn.Logger;
import Learn.Window;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        Boolean argumentCheck = true;
        Boolean onlineMode = true;

        StartupJobs.main(args);

        Logger.info("Learn.Core.Main", "Started! Hello from dev!");

        DebugMode debugMode = new DebugMode();
        if (debugMode != null) {
            Logger.info("ObjectInit", "Learn.DebugMode object successfully initialized!");
        } else {
            Logger.warn("ObjectInit", "Learn.DebugMode object failed to initialize :(");
        }

        StartupJobs startupJobs = new StartupJobs();

// Log debug information if debug mode is enabled
        if (DebugMode.isDebug()) {
            Logger.debug("Learn.Core.Main", "Running build 0084 on version 4.2.2");
            if (PublicVariables.isDevVer) {
                Logger.debug("Learn.Core.Main", "Is a dev build?: yes");
            } else {
                Logger.debug("Learn.Core.Main", "Is a dev build?: nope");
            }


            // Log system information with null checks
            Logger.debug("Learn.Core.Main", "Running on " +
                    (PublicVariables.osType != null ? PublicVariables.osType : "unknown OS") + " " +
                    (PublicVariables.osVersion != null ? PublicVariables.osVersion : "unknown version") + " on an " +
                    (PublicVariables.deviceArch  != null ? PublicVariables.deviceArch : "unknown architecture") + ".");
            if (PublicVariables.osVersion != null && PublicVariables.osVersion != null && PublicVariables.deviceArch != null ) {

            }   else {
                Logger.debug("Learn.Core.Main","Ok what? How are you even... forget it...");
            }


        } else {
            // Honestly, idk what to do here yet xd
        }


        // Initialize Learn.Core.Arguments object
        Arguments arguments = new Arguments();
        if (arguments != null) {
            Logger.info("ObjectInit", "Learn.Core.Arguments object successfully initialized!");
        } else {
            Logger.warn("ObjectInit", "Learn.Core.Arguments object failed to initialize, skipping argument check.");
            Logger.info("Learn.HexadecimalHandler", "Learn.HexadecimalHandler loaded!");
            argumentCheck = false;
        }

        // Initialize Learn.Window object
        Window window = new Window();
            Logger.info("ObjectInit", "Learn.Window object successfully initialized!");

        // Initialize Scanner object
        Scanner scanner = new Scanner(System.in);
            Logger.info("ObjectInit", "Scanner object successfully initialized!");

        // Initialize Learn.Core.IOHandler object
        IOHandler.IO io = new IOHandler.IO();
            Logger.info("ObjectInit", "Learn.Core.IOHandler object successfully initialized!");


        // Get the runtime instance
        Runtime runtime = Runtime.getRuntime();
            Logger.info("ObjectInit", "Runtime object successfully initialized!");

        // Get the total memory allocated to the JVM
        assert runtime != null;
        long totalMemory = runtime.totalMemory();
        long totalMemoryMB = totalMemory / (1024 * 1024);
        if (totalMemory == 0) {
            Logger.warn("Learn.Core.Main", "Failed to retrieve allocated memory or is somehow 0 (i have no idea how that is possible).");
        } else {
            Logger.info("Learn.Core.Main", "Allocated memory is " + totalMemory + " Bytes or " + totalMemoryMB + " MegaBytes");
        }

        // Check if running on Termux





            System.out.println("What do you want to do?");
            TimeUnit.SECONDS.sleep((long) 1.5); // Sleep for 1.5 seconds
            System.out.println("1) Create a file");
            System.out.println("2) Write data to a file");
            System.out.println("3) Delete a file");
            System.out.println("4) Rename a file");
            System.out.println("5) Set read-only property to true or false");
            Integer UserOption = Integer.valueOf(scanner.nextLine());

            // Handle user option for creating a file
            if (UserOption.equals(1)) {
                System.out.println("You want to create a file? Alright fine by me.");
                System.out.print("Tell me the file you want to create, you can put the path to it as well: ");
                String UserFileName = scanner.nextLine();
                io.create(UserFileName);
                System.out.println("Alright I made your file! " + UserFileName);
                System.out.println("Enjoy your new file!");
                System.exit(0);
            } else if (UserOption.equals(2)) {
                // Handle user option for writing data to a file
                System.out.println("Alright sure lets write something!");
                System.out.print("Tell what file you want to write to: ");
                String UserFileNameData = scanner.nextLine();
                if (io.FileExists(UserFileNameData)) {
                    System.out.print("Alright, now tell me what you want to write to it: ");
                    String UserFileData = scanner.nextLine();
                    io.write(UserFileNameData, UserFileData);
                    System.out.println("Alright I have added the data to file " + UserFileNameData + ", Enjoy!");
                    System.exit(0);
                } else {
                    TimeUnit.SECONDS.sleep(2); // Sleep for 2 seconds
                    System.exit(1);
                }
            } else if (UserOption.equals(3)) {
                // Handle user option for deleting a file
                System.out.println("Alright, just don't delete system files.");
                System.out.print("What file do you want to delete? Provide a path or just the file: ");
                String UserFileDelete = scanner.nextLine();
                if (io.FileExists(UserFileDelete)) {
                    System.out.println("The file has been deleted!");
                    io.delete(UserFileDelete);
                    TimeUnit.SECONDS.sleep(2); // Sleep for 2 seconds
                    System.exit(0);
                } else {
                    io.delete(UserFileDelete);
                    TimeUnit.SECONDS.sleep(2); // Sleep for 2 seconds
                    System.exit(1);
                }


            } else if (UserOption.equals(4)) {

                System.out.println("What file do you want to rename?");
                String UserRenameFile = scanner.nextLine();
                if (io.FileExists(UserRenameFile)) {
                    System.out.println("And rename what to?");
                    String UserRenameFileResult = scanner.nextLine();
                    io.renameFile(UserRenameFile, UserRenameFileResult);
                } else {
                    Logger.error("Learn.Core.Main - Learn.Core.IOHandler", "File does not exist: ");
                            TimeUnit.SECONDS.sleep((long) 2.5);
                    System.exit(1);
                }
            } else if (UserOption.equals(5)) {
                System.out.println("What file do you want to change the property on?");
                String UserReadOnlyFile = scanner.nextLine();
                if (io.FileExists(UserReadOnlyFile)) {
                    System.out.println("Now do you want to turn it off or on? (false being on and true being off)");
                    Boolean userReadOnlyPropertyAnswer = Boolean.valueOf(scanner.nextLine());
                    if (userReadOnlyPropertyAnswer.equals("true")) {
                        io.ReadOnlyChange(UserReadOnlyFile, userReadOnlyPropertyAnswer);
                    } else {
                        io.ReadOnlyChange(UserReadOnlyFile, userReadOnlyPropertyAnswer);
                    }

                } else {
                    Logger.error("Learn.Core.Main - Learn.Core.IOHandler", "File does not exist: ");
                    TimeUnit.SECONDS.sleep((long) 2.5);
                    System.exit(1);
                }




            } else {


                System.out.println("Uhhh that isn't an option...");
                System.exit(1);
            }
    }


}