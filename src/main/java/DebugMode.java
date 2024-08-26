public class DebugMode {

    private static IOHandler.IO io = new IOHandler.IO(); // Assuming this is static


    // Constructor
    public DebugMode() {
        io = new IOHandler.IO(); // Initialize IOHandler

        // Check if initialization was successful
        if (io != null) {
            Logger.info("ObjectInit - DebugMode", "IOHandler object successfully initialized!");
        } else {
            Logger.fatal("ObjectInit - DebugMode", "IOHandler object failed to initialize. :(");
            System.exit(1); // Exit the application if initialization fails
        }
    }

    public static boolean isDebug() {

        String debugToggle = io.readContents("config.txt");
        if ("debug=true".equals(debugToggle.trim())) {
            Logger.info("DebugMode", "debug verified as true.");
            return true;
        } else {
            Logger.info("DebugMode", "debug verified as true.");
            return false;
        }

    }
}
