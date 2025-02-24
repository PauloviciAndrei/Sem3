package statementParser;
import model.expressions.*;
import model.values.*;
import java.util.*;
import java.util.regex.*;

import static model.expressions.LogicalOperation.*;

public class ExpressionParser {
    public static IExpression parseExpression(String expr) {
        expr = expr.replaceAll("\\s+", "");

        // Boolean values
        if (expr.equals("true")) return new ValueExpression(new BoolValue(true));
        if (expr.equals("false")) return new ValueExpression(new BoolValue(false));

        // Single integer value
        if (expr.matches("\\d+")) return new ValueExpression(new IntValue(Integer.parseInt(expr)));

        // Variable Expression
        if (expr.matches("[a-zA-Z]\\w*")) return new VariableExpression(expr);

        // Heap Read Expression (ReadHeap(a))
        if (expr.startsWith("ReadHeap(") && expr.endsWith(")")) {
            String heapVar = expr.replace("ReadHeap(", "").replace(")", "").trim();
            return new HeapReadExpression(new VariableExpression(heapVar));
        }

        // Relational Operations: <, >, <=, >=
        if (expr.contains("<") || expr.contains(">") || expr.contains("<=") || expr.contains(">=")) {
            return parseRelationalExpression(expr);
        }

        //String expression
        if (expr.startsWith("\"") && expr.endsWith("\""))
            return new ValueExpression(new StringValue(expr.substring(1, expr.length() - 1)));

        // Arithmetic Expression
        return parseArithmeticExpression(expr);
    }


    private static IExpression parseBooleanExpression(String expr) {
        Pattern pattern = Pattern.compile("(.+)(==|!=|&&|\\|\\|)(.+)");
        Matcher matcher = pattern.matcher(expr);

        if (matcher.matches()) {
            IExpression left = parseExpression(matcher.group(1).trim());
            String operator = matcher.group(2).trim();
            IExpression right = parseExpression(matcher.group(3).trim());

            switch (operator) {
                case "==": return new RelationalExpression(left, right, "==");
                case "!=": return new RelationalExpression(left, right, "!=");
                case "&&": return new LogicalExpression(left, right, AND);
                case "||": return new LogicalExpression(left, right, OR);
                default: throw new IllegalArgumentException("Invalid boolean operator: " + operator);
            }
        }
        throw new IllegalArgumentException("Invalid boolean expression: " + expr);
    }

    private static IExpression parseRelationalExpression(String expr) {
        Pattern pattern = Pattern.compile("(.+)(<=|>=|<|>)(.+)");
        Matcher matcher = pattern.matcher(expr);

        if (matcher.matches()) {
            IExpression left = parseExpression(matcher.group(1).trim());
            String operator = matcher.group(2).trim();
            IExpression right = parseExpression(matcher.group(3).trim());

            return new RelationalExpression(left, right, operator);
        }
        throw new IllegalArgumentException("Invalid relational expression: " + expr);
    }


    private static IExpression parseArithmeticExpression(String expr) {
        Stack<IExpression> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < expr.length()) {
            char ch = expr.charAt(i);

            if (Character.isDigit(ch)) {
                int numStart = i;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    i++;
                }
                values.push(new ValueExpression(new IntValue(Integer.parseInt(expr.substring(numStart, i)))));
                continue;
            }

            if (Character.isLetter(ch)) {
                int varStart = i;
                while (i < expr.length() && Character.isLetterOrDigit(expr.charAt(i))) {
                    i++;
                }
                values.push(new VariableExpression(expr.substring(varStart, i)));
                continue;
            }

            if ("+-*/".indexOf(ch) != -1) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    processTopOperator(values, operators);
                }
                operators.push(ch);
            }

            i++;
        }

        while (!operators.isEmpty()) {
            processTopOperator(values, operators);
        }

        return values.pop();
    }

    private static int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
    }

    private static void processTopOperator(Stack<IExpression> values, Stack<Character> operators) {
        IExpression right = values.pop();
        IExpression left = values.pop();
        values.push(new ArithmeticExpression(operators.pop(), left, right));
    }
}