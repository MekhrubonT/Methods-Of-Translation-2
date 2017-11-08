package parser;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final Tree COLONTREE = new Tree(":");
    private static final Tree SEMICOLONTREE = new Tree(";");
    private static final Tree COMMATREE = new Tree(",");

    private final LexicalAnalyzer lex;

    public Parser(String s) throws ParseException {
        lex = new LexicalAnalyzer(new ByteArrayInputStream(s.getBytes()));
    }


    public Tree S() throws ParseException {
        switch (lex.getCurToken()) {
            case VAR:
                Tree v = V();
                Tree varGroups = VARGROUPS();
                return new Tree("S", v, varGroups);
            default:
                throw new ParseException("Var at the beginning expected", lex.getCurPos());
        }
    }

    private Tree V() throws ParseException {
        switch (lex.getCurToken()) {
            case VAR:
                lex.nextToken();
                return new Tree("V", new Tree("var"));
            default:
                throw new ParseException("Var expected", lex.getCurPos());

        }
    }

    private Tree VARGROUPS() throws ParseException {
        switch (lex.getCurToken()) {
            case END:
                lex.nextToken();
                return new Tree("VARGROUPS");
            case WORD:
                Tree varlist = VARLIST();
                if (lex.getCurToken() != Token.COLON) {
                    throw new ParseException("Colon expected after variables", lex.getCurPos());
                }
                lex.nextToken();
                Tree type = WORD();
                if (lex.getCurToken() != Token.SEMICOLON) {
                    throw new ParseException("Semicolon expected after type", lex.getCurPos());
                }
                lex.nextToken();
                Tree vargroups = VARGROUPS();
                return new Tree("VARGROUPS", varlist, COLONTREE, type, SEMICOLONTREE, vargroups);
            default:
                throw new ParseException("VAR Group expected", lex.getCurPos());
        }
    }

    private Tree VARLIST() throws ParseException {
        switch (lex.getCurToken()) {
            case WORD:
                Tree word = WORD();
                Tree varlistprime = VARLISTPRIME();
                return new Tree("VARLIST", word, varlistprime);
            default:
                throw new ParseException("Variables expected", lex.getCurPos());
        }
    }

    private Tree VARLISTPRIME() throws ParseException {
        switch (lex.getCurToken()) {
            case COMMA:
                lex.nextToken();
                Tree word = WORD();
                Tree varlistprime = VARLISTPRIME();
                return new Tree("VARLISTPRIME", COMMATREE, word, varlistprime);
            default:
                return new Tree("VARLISTPRIME");
        }
    }

    private Tree WORD() throws ParseException {
//        System.out.println("parser.WORD " + lex.getCurToken());
        switch (lex.getCurToken()) {
            case WORD:
                List<Tree> letterTree = new ArrayList<>();
                do {
                    letterTree.add(new Tree(String.valueOf(lex.getCurChar())));
                } while (lex.nextToken() == Token.WORD);
                return new Tree("WORD", letterTree.toArray(new Tree[0]));
            default:
                throw new ParseException("type or variable expected", lex.getCurPos());
        }
    }

}
