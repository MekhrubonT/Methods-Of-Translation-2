import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final Tree COLONTREE = new Tree(":");
    private static final Tree SEMICOLONTREE = new Tree(";");
    public static final Tree COMMATREE = new Tree(",");

    LexicalAnalyzer lex;

    Tree S() throws ParseException {
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
        switch (lex.getCurToken()) {
            case V:
                assert lex.nextToken() == Token.A;
                assert lex.nextToken() == Token.R;
                return new Tree("V", new Tree("v"), new Tree("a"), new Tree("r"));
            default:
                throw new AssertionError();

        }
    }

    private Tree VARGROUPS() throws ParseException {
        switch (lex.getCurToken()) {
            case END:
                return new Tree("VARGROUPS");
            case V:
            case A:
            case R:
            case LETTER:
                Tree varlist = VARLIST();
                assert lex.nextToken() == Token.COLON;
                Tree type = WORD();
                assert lex.nextToken() == Token.SEMICOLON;
                Tree vargroups = VARGROUPS();
                return new Tree("VARGROUPS", varlist, COLONTREE, type, SEMICOLONTREE, vargroups);
            default:
                throw new AssertionError();
        }
    }

    private Tree VARLIST() throws ParseException {
        switch (lex.getCurToken()) {
            case LETTER:
                Tree word = WORD();
                Tree varlistprime = VARLISTPRIME();
                return new Tree("VARLIST", word, varlistprime);
            default:
                throw new AssertionError();
        }
    }

    private Tree VARLISTPRIME() throws ParseException {
        switch (lex.getCurToken()) {
            case SEMICOLON:
                lex.nextToken();
                return new Tree("VARLISTPRIME", SEMICOLONTREE);
            case COMMA:
                lex.nextToken();
                Tree word = WORD();
                Tree varlistprime = VARLISTPRIME();
                return new Tree("COMMA", COMMATREE, word, varlistprime);
            default:
                throw new AssertionError();
        }
    }

    private Tree WORD() throws ParseException {
        switch (lex.getCurToken()) {
            case LETTER:
                List<Tree> letterTree = new ArrayList<>();
                do {
                    letterTree.add(new Tree(String.valueOf(lex.getCurChar())));
                } while (lex.nextToken() == Token.LETTER);
                return new Tree("WORD", letterTree.toArray(new Tree[0]));
            default:
                throw new AssertionError();
        }
    }

}
