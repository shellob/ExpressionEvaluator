public class FunctionChecker {
    private static final String[] FUNCTIONS = {"sin", "cos", "tan", "log", "sqrt"};

    public static boolean isFunction(String str) {
        for (String function : FUNCTIONS) {
            if (str.equalsIgnoreCase(function)) {
                return true;
            }
        }
        return false;
    }
}
