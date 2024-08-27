package Learn.Core;

import java.io.File;

public class PublicVariables {

    public static String VersionNum = "4.2.2.0084";
    public static String osType = System.getProperty("os.name").toLowerCase();
    public static String javaVer = System.getProperty("java.version");
    public static String deviceArch = System.getProperty("os.arch");
    public static String osVersion = System.getProperty("os.version");
    public static boolean isDevVer = true;
    boolean runningOnTermux = isRunningOnTermux();

    private static boolean isRunningOnTermux() {
        File termuxFile = new File("/data/data/com.termux/files/home/.termux");
        return termuxFile.exists() && termuxFile.isDirectory();
    }
}
