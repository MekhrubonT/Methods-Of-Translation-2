package parser;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class LexicalAnalyzer {
    private final InputStream s;
    private int curChar;

    public int getCurPos() {
        return curPos;
    }

    private int curPos = 0;
    private Token curToken;

    public LexicalAnalyzer(InputStream s) throws ParseException {
        this.s = s;
        nextToken();
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = s.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public Token nextToken() throws ParseException {
        nextChar();
        skip();
        switch (curChar) {
            case ',' : curToken = Token.COMMA;
                break;
            case ':' : curToken = Token.COLON;
                break;
            case ';' : curToken = Token.SEMICOLON;
                break;
            case -1 : curToken = Token.END;
                return curToken;
            case 'v' : curToken = Token.V;
                break;
            case 'a' : curToken = Token.A;
                break;
            case 'r' : curToken = Token.R;
                break;
            default:
                if (Character.isLetter(curChar)) {
                    this.curToken = Token.LETTER;
                } else {
                    throw new ParseException("Illegal character " + curChar, curPos);
                }
        }
        return curToken;
    }

    private void skip() throws ParseException {
        while (Character.isWhitespace(curChar)) {
            nextChar();
        }
    }

    public char getCurChar() {
        return (char) curChar;
    }

    public Token getCurToken() {
        return curToken;
    }
}
