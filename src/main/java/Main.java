import parser.Parser;
import parser.Tree;

import javax.management.BadStringOperationException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws BadStringOperationException {
        String expression = "var a, b, c: int; d, e, f: double;";
        Tree s;
        try {
            s = new Parser(expression).S();
            System.out.println(toCanonical(expression));
            assert toCanonical(expression).equals(s.toString());
            System.out.println(s.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String toCanonical(String expression) throws BadStringOperationException {
        expression = expression.trim();
        if (!expression.startsWith("var ")) {
            throw new BadStringOperationException("String is wrongly formatted");
        }
        StringBuilder builder = new StringBuilder("var ");
        for (int i = 4; i < expression.length(); i++) {
            if (!Character.isWhitespace(expression.charAt(i))) {
                builder.append(expression.charAt(i));
            }
        }
        return builder.toString();
    }
}
