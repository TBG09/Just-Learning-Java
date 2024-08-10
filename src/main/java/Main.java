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

        Logger.info("Main", "Started! Hello from dev!");


        // Initialize Arguments object
        Arguments arguments = new Arguments();
        if (arguments != null) {
            Logger.info("ObjectInit", "Arguments object successfully initialized!");
        } else {
            Logger.warn("ObjectInit", "Arguments object failed to initialize, skipping argument check.");
            Logger.info("HexadecimalHandler", "HexadecimalHandler loaded!");
            argumentCheck = false;
        }

        // Initialize Window object
        Window window = new Window();
        if (window != null) {
            Logger.info("ObjectInit", "Window object successfully initialized!");
        } else {
            Logger.warn("ObjectInit", "Window object failed to initialize, skipping argument check.");
        }

        // Initialize Scanner object
        Scanner scanner = new Scanner(System.in);
        if (scanner != null) {
            Logger.info("ObjectInit", "Scanner object successfully initialized!");
        } else {
            Logger.fatal("ObjectInit", "Scanner object failed to initialize. :(");
            System.exit(1);
        }

        // Initialize IOHandler object
        IOHandler.IO io = new IOHandler.IO();
        if (io != null) {
            Logger.info("ObjectInit", "IOHandler object successfully initialized!");
        } else {
            Logger.fatal("ObjectInit", "IOHandler object failed to initialize. :(");
            System.exit(1);
        }




        // Get the runtime instance
        Runtime runtime = Runtime.getRuntime();
        if (runtime != null) {
            Logger.info("ObjectInit", "Runtime object successfully initialized!");
        } else {
            Logger.warn("ObjectInit", "Runtime object failed to initialize. :(");
        }

        // Get the total memory allocated to the JVM
        assert runtime != null;
        long totalMemory = runtime.totalMemory();
        long totalMemoryMB = totalMemory / (1024 * 1024);
        if (totalMemory == 0) {
            Logger.warn("Main", "Failed to retrieve allocated memory or is somehow 0 (i have no idea how that is possible).");
        } else {
            Logger.info("Main", "Allocated memory is " + totalMemory + " Bytes or " + totalMemoryMB + " MegaBytes");
        }

        // Check if running on Termux
        boolean runningOnTermux = isRunningOnTermux();
        if (runningOnTermux) {
            Logger.info("Main", "Running on Termux! Skipping window creation.");
        }

        // Check for custom arguments if argumentCheck is true
        if (argumentCheck) {
            Logger.info("Main", "Checking for custom arguments...");

            boolean hasCustomArgument = false;

            // Check for "help" argument
            if (Arguments.argumentCheck("help", args)) {
                System.out.println("Version 2.4.1");
                System.out.println("properties - this shows properties of files, not folders.");
                System.out.println("window - opens the test window.");
                System.out.println("credits - shows credits.");
                System.out.println("changelog - shows the changelog.");
                System.out.println("snake - launches the game snake.");
                System.out.println("That's all lol.");
                System.exit(0);
            } else if (Arguments.argumentCheck("properties", args)) {
                // Check for "properties" argument
                hasCustomArgument = true;
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
            } else if (Arguments.argumentCheck("window", args) && (totalMemoryMB >= 16)) {
                // Check for "window" argument and sufficient memory
                if (!runningOnTermux) {
                    hasCustomArgument = true;
                    Logger.info("Main", "Window Launching!");
                    window.StartWindow("Just Learn java 4.1.1(Test Window)");
                    Logger.info("Main", "Not a termux environment, continuing with window launch.");
                    latch.await(); // Wait until the window is closed
                } else {
                    Logger.error("Main", "Window launch canceled due to being ran in a Termux environment.");
                    System.exit(1);
                }
            } else if (Arguments.argumentCheck("window", args)) {
                Logger.error("Main", "Memory amount does not meet requirements to launch window.");
                System.out.println("ERROR: Memory amount does not meet requirements to launch window.");
                System.out.println("Sorry try launching the window with at least 16 MB of memory to the jar.");
                System.exit(1);
            }
        }
        else {
            Logger.info("Main", "No argumentCheck is needed. Continuing with the application.");
        }

        if (Arguments.argumentCheck("hexadecimal", args)) {
            System.out.println("What file do you want to get the hex data out of?");
            String FileObjectHexData = scanner.nextLine();
            if (io.isFileEmpty(FileObjectHexData)) {
                Logger.error("Main - HexadecimalHandler", "File is empty, unable to retreive any data.");
                System.exit(1);
            } else  {
                if (io.FileExists(FileObjectHexData)) {
                    HexadecimalHandler.handleFile(FileObjectHexData);
                    System.exit(0);
                }
            }

        }

        if (Arguments.argumentCheck("credits", args)) {
            ConsoleManagement.clearConsole();
            Logger.info("Arguments", "Displaying credits!");
            System.out.println("97% coded by Bluelist");
            System.out.println("3% Code given by Chatgpt and thank you to chatgpt for helping me :D");
            System.exit(0);
        }

        if (Arguments.argumentCheck("changelog",args)) {
            System.out.println("Changes in version 4.1.1:");
            System.out.println("Added snake game, to launch use arg snake!");
            System.out.println("Added a blue cube to the window which you can move with arrow keys.");
            System.exit(0);
        }

        if (Arguments.argumentCheck("snake", args)) {
            Logger.info("Main", "Starting method SnakeGame.");
            String[] snakeGameArgs = {"snake"};
            SnakeGame.main(snakeGameArgs);
            latch.await();
        }
        
        // Continue with displaying options and handling user input if not in Termux
        if (!runningOnTermux) {
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
                    Logger.error("Main - IOHandler", "File does not exist: ");
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
                    Logger.error("Main - IOHandler", "File does not exist: ");
                    TimeUnit.SECONDS.sleep((long) 2.5);
                    System.exit(1);
                }




            } else {


                System.out.println("Uhhh that isn't an option...");
                System.exit(1);
            }
        } else {
            Logger.info("Main", "No interactive options available in Termux.");
        }
    }

    private static boolean isRunningOnTermux() {
        File termuxFile = new File("/data/data/com.termux/files/home/.termux");
        return termuxFile.exists() && termuxFile.isDirectory();
    }
}
