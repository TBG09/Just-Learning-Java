import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StartupJobs {

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
