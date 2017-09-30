
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final Tree COLONTREE = new Tree(":");
    private static final Tree SEMICOLONTREE = new Tree(";");
    public static final Tree COMMATREE = new Tree(",");

    final LexicalAnalyzer lex;

    public Parser(String s) throws ParseException {
        lex = new LexicalAnalyzer(new ByteArrayInputStream(s.getBytes()));
        lex.nextToken();
    }


    public Tree S() throws ParseException {
//        System.out.println("Parser.S " + lex.getCurToken());
        switch (lex.getCurToken()) {
            case V:
                Tree v = V();
                Tree varGroups = VARGROUPS();
                return new Tree("S", v, varGroups);
            default:
                throw new AssertionError();
        }
    }

    private Tree V() throws ParseException {
//        System.out.println("Parser.V " + lex.getCurToken());
        switch (lex.getCurToken()) {
            case V:
                assert lex.nextToken() == Token.A;
                assert lex.nextToken() == Token.R;
                lex.nextToken();
                return new Tree("V", new Tree("v"), new Tree("a"), new Tree("r"));
            default:
                throw new AssertionError();

        }
    }

    private Tree VARGROUPS() throws ParseException {
//        System.out.println("Parser.VARGROUPS " + lex.getCurToken());
        switch (lex.getCurToken()) {
            case END:
                lex.nextToken();
                return new Tree("VARGROUPS");
            case V:
            case A:
            case R:
            case LETTER:
                Tree varlist = VARLIST();
                assert lex.getCurToken() == Token.COLON;
                lex.nextToken();
                Tree type = WORD();
                assert lex.getCurToken() == Token.SEMICOLON;
                lex.nextToken();
                Tree vargroups = VARGROUPS();
                return new Tree("VARGROUPS", varlist, COLONTREE, type, SEMICOLONTREE, vargroups);
            default:
                throw new AssertionError();
        }
    }

    private Tree VARLIST() throws ParseException {
//        System.out.println("Parser.VARLIST " + lex.getCurToken());
        switch (lex.getCurToken()) {
            case V:
            case A:
            case R:
            case LETTER:
                Tree word = WORD();
                Tree varlistprime = VARLISTPRIME();
                return new Tree("VARLIST", word, varlistprime);
            default:
                throw new AssertionError();
        }
    }

    private Tree VARLISTPRIME() throws ParseException {
//        System.out.println("Parser.VARLISTPRIME " + lex.getCurToken());
        switch (lex.getCurToken()) {
            case COMMA:
                lex.nextToken();
                Tree word = WORD();
                Tree varlistprime = VARLISTPRIME();
                return new Tree("VARLISTPRIME", COMMATREE, word, varlistprime);
            default:
                return new Tree("VARLISTPRIME", SEMICOLONTREE);
        }
    }

    private Tree WORD() throws ParseException {
//        System.out.println("Parser.WORD " + lex.getCurToken());
        switch (lex.getCurToken()) {
            case V:
            case A:
            case R:
            case LETTER:
                List<Tree> letterTree = new ArrayList<>();
                do {
                    letterTree.add(new Tree(String.valueOf(lex.getCurChar())));
                } while (isLetter(lex.nextToken()));
//                System.out.println("letter done " + lex.getCurToken());
                return new Tree("WORD", letterTree.toArray(new Tree[0]));
            default:
                throw new AssertionError();
        }
    }

    private boolean isLetter(Token token) {
         return token == Token.LETTER || token == Token.A
                || token == Token.R|| token == Token.V;
    }

}
