import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_BRIGHT_RED = "\u001B[91m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static boolean ANSIenabled = false;

    // Method to log INFO messages
    public static void info(String source, String message) {
        log(source, "INFO", message, ANSI_RESET);
    }

    public void ToggleANSI(boolean toggleOnOff) {
        ANSIenabled = toggleOnOff;
    }

    // Method to log WARN messages
    public static void warn(String source, String message) {
        log(source, "WARN", message, ANSI_YELLOW);
    }

    // Method to log ERROR messages
    public static void error(String source, String message) {
        log(source, "ERROR", message, ANSI_RED);
    }

    // Method to log FATAL messages
    public static void fatal(String source, String message) {
        log(source, "FATAL", message, ANSI_BRIGHT_RED);
    }

    public static void debug(String source, String message) {
        log(source, "DEBUG", message, ANSI_CYAN);
    }

    // Private method to format and print the log message
    private static void log(String source, String level, String message, String color) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateString = formatter.format(new Date());
        System.out.println("[" + dateString + "] [" + color + source + ANSI_RESET + "/" + color + level + ANSI_RESET + "] " + color + message + ANSI_RESET);
    }
}
