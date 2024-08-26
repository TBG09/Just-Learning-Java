import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StartupJobs {
    public String osName;
    public String javaVer;
    public String deviceArch;
    public String osVersion;




        public void FirstChecks() {
            // Assign values to instance variables using 'this'
            this.osName = System.getProperty("os.name");
            this.javaVer = System.getProperty("java.version");
            this.deviceArch = System.getProperty("os.arch");
            this.osVersion = System.getProperty("os.version");
        }

    public static void main(String[] args) {
        String directoryPath = "logs"; // Directory to be created


        Path path = Paths.get(directoryPath);

        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path);
                Logger.info("StartupJobs", "Directory created: " + directoryPath);
            } else {
                Logger.info("StartupJobs", "Directory already exists: " + directoryPath);
            }
        } catch (IOException e) {
            Logger.error("StartupJobs", "Failed to create directory: " + e.getMessage());
        }
            LogFile.setupLogging();
        Logger.info("StartupJobs","Started setupLogging");
    }
}