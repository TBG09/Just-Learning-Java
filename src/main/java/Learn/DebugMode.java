package Learn;

import Learn.Core.IOHandler;

public class DebugMode {

    private static IOHandler.IO io = new IOHandler.IO(); // Assuming this is static


    // Constructor
    public DebugMode() {
        io = new IOHandler.IO(); // Initialize Learn.Core.IOHandler

        // Check if initialization was successful
        if (io != null) {
            Logger.info("ObjectInit - Learn.DebugMode", "Learn.Core.IOHandler object successfully initialized!");
        } else {
            Logger.fatal("ObjectInit - Learn.DebugMode", "Learn.Core.IOHandler object failed to initialize. :(");
            System.exit(1); // Exit the application if initialization fails
        }
    }

    public static boolean isDebug() {

        String debugToggle = io.readContents("config.txt");
        if ("debug=true".equals(debugToggle.trim())) {
            Logger.info("Learn.DebugMode", "debug verified as true.");
            return true;
        } else {
            Logger.info("Learn.DebugMode", "debug verified as false.");
            return false;
        }

    }
}
