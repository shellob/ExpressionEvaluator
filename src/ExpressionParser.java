import java.util.Map;

public class ExpressionParser {
    private final String expression;
    private final Map<String, Double> variables;
    private int pos = -1;
    private int ch;

    public ExpressionParser(String expression, Map<String, Double> variables) {
        this.expression = expression;
        this.variables = variables;
    }

    public double evaluate() {
        nextChar();
        double result = parseExpression();
        if (pos < expression.length()) {
            throw new RuntimeException("Unexpected character: " + (char) ch);
        }
        return result;
    }

    private void nextChar() {
        ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
    }

    private boolean eat(int charToEat) {
        while (ch == ' ') {
            nextChar();
        }
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    private double parseExpression() {
        double result = parseTerm();
        while (true) {
            if (eat('+')) {
                result += parseTerm();
            } else if (eat('-')) {
                result -= parseTerm();
            } else {
                return result;
            }
        }
    }

    private double parseTerm() {
        double result = parseFactor();
        while (true) {
            if (eat('*')) {
                result *= parseFactor();
            } else if (eat('/')) {
                result /= parseFactor();
            } else {
                return result;
            }
        }
    }

    private double parseFactor() {
        if (eat('+')) {
            return parseFactor();
        }
        if (eat('-')) {
            return -parseFactor();
        }

        double result = 0;
        int startPos = this.pos;
        if (eat('(')) {
            result = parseExpression();
            eat(')');
        } else if ((ch >= '0' && ch <= '9') || ch == '.') {
            while ((ch >= '0' && ch <= '9') || ch == '.') {
                nextChar();
            }
            result = Double.parseDouble(expression.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') {
            while (ch >= 'a' && ch <= 'z') {
                nextChar();
            }
            String func = expression.substring(startPos, this.pos);


            if (isFunction(func)) {
                double argument = parseFactor();
                if (func.equals("sqrt")) {
                    result = Math.sqrt(argument);
                } else if (func.equals("sin")) {
                    result = Math.sin(Math.toRadians(argument));
                } else if (func.equals("cos")) {
                    result = Math.cos(Math.toRadians(argument));
                } else if (func.equals("tan")) {
                    result = Math.tan(Math.toRadians(argument));
                } else if (func.equals("log")) {
                    result = Math.log(argument);
                } else {
                    throw new RuntimeException("Unknown function: " + func);
                }
            } else if (variables.containsKey(func)) {
                result = variables.get(func);
                if (eat('^')) {
                    result = Math.pow(result, parseFactor());
                }
            } else {
                throw new RuntimeException("Unknown variable: " + func);
            }
        } else {
            throw new RuntimeException("Unexpected character: " + (char) ch);
        }

        return result;
    }
    private boolean isFunction(String str) {
        return FunctionChecker.isFunction(str);
    }
}


