import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class ExpressionEvaluator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Введите выражение:");
        String expression = scanner.nextLine();

        VariableExtractor variableExtractor = new VariableExtractor(expression);
        Map<String, Double> variables = variableExtractor.getVariables();

        for (String variable : variables.keySet()) {
            System.out.println("Введите значение для переменной " + variable + ":");
            double value = scanner.nextDouble();
            variables.put(variable, value);
        }

        try {
            ExpressionParser parser = new ExpressionParser(expression, variables);
            double result = parser.evaluate();
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
