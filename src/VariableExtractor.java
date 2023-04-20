import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableExtractor {
    private final String expression;

    public VariableExtractor(String expression) {
        this.expression = expression;
    }

    public Map<String, Double> getVariables() {
        Map<String, Double> variables = new HashMap<>();
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            String variable = matcher.group();
            if (!isFunction(variable)) {
                variables.putIfAbsent(variable, 0.0);
            }
        }

        return variables;
    }

    private boolean isFunction(String str) {
        return FunctionChecker.isFunction(str);
    }
}
