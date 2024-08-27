package Learn;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogFile {
    public static final String LOG_DIRECTORY = "logs";
    public static final String LOG_FILE_PREFIX = "log_";
    public static final String LOG_FILE_SUFFIX = ".log";
    public static final long UPDATE_INTERVAL_MS = 300; // 0.3 seconds

    public static PrintStream originalOut;
    public static PrintStream originalErr;
    public static StringBuilder currentInput = new StringBuilder();
    public static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();



    public static void setupLogging() {
        try {
            // Ensure the logs directory exists
            File logDir = new File(LOG_DIRECTORY);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Create a log file with the current date and time
            String logFileName = LOG_FILE_PREFIX + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + LOG_FILE_SUFFIX;
            File logFile = new File(logDir, logFileName);

            // Save the original System.out and System.err
            originalOut = System.out;
            originalErr = System.err;

            // Redirect System.out and System.err to the log file
            PrintStream logStream = new PrintStream(new FileOutputStream(logFile, true), true);
            System.setOut(new PrintStream(new MultiOutputStream(System.out, logStream), true));
            System.setErr(new PrintStream(new MultiOutputStream(System.err, logStream), true));

            Logger.info("Learn.LogFile", "Logging started. Output will be written to " + logFile.getAbsolutePath());

            // Start the periodic update task
            startPeriodicUpdate(logStream);
        } catch (IOException e) {
            Logger.error("Learn.LogFile", "Failed to set up logging: " + e.getMessage());
        }
    }

    public static void startPeriodicUpdate(PrintStream logStream) {
        executor.scheduleAtFixedRate(() -> {
            synchronized (currentInput) {
                if (currentInput.length() > 0) {
                    logStream.println(currentInput.toString());
                    logStream.flush();
                    currentInput.setLength(0); // Clear the input after logging
                }
            }
        }, 0, UPDATE_INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    public static class MultiOutputStream extends OutputStream {
        public final OutputStream[] outputStreams;

        public MultiOutputStream(OutputStream... outputStreams) {
            this.outputStreams = outputStreams;
        }

        @Override
        public void write(int b) throws IOException {
            for (OutputStream os : outputStreams) {
                os.write(b);
            }
        }

        @Override
        public void flush() throws IOException {
            for (OutputStream os : outputStreams) {
                os.flush();
            }
        }

        @Override
        public void close() throws IOException {
            for (OutputStream os : outputStreams) {
                os.close();
            }
        }
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Start typing your input:");
            String line;
            while ((line = reader.readLine()) != null) {
                synchronized (currentInput) {
                    currentInput.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            Logger.error("Learn.LogFile", "Error reading user input: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}