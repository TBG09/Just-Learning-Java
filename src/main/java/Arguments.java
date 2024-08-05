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
}