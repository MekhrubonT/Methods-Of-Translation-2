package parser;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Queue;

import static parser.TokenHolder.*;

public class LexicalAnalyzer {
    private final InputStream s;
    private Queue<TokenHolder> queue = new LinkedList<>();

    public int getCurPos() {
        return curPos;
    }

    private int curPos = 0;
    private TokenHolder curToken;

    public LexicalAnalyzer(InputStream s) throws ParseException {
        this.s = s;
        nextToken();
    }

    private int nextChar() throws ParseException {
        try {
            curPos++;
            return s.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    private void flush(StringBuilder builder) {
        if (builder.length() != 0)
            queue.add(builder.toString().equals("var") ? VARHOLDER : new TokenHolder(builder.toString()));
    }

    public Token nextToken() throws ParseException {
        StringBuilder curWord = new StringBuilder();
        while (queue.isEmpty()) {
            int curChar = nextChar();
            switch (curChar) {
                case ',':
                    flush(curWord);
                    queue.add(COMMAHOLDER);
                    break;
                case ':':
                    flush(curWord);
                    queue.add(COLONHOLDER);
                    break;
                case ';':
                    flush(curWord);
                    queue.add(SEMICOLONHOLDER);
                    break;
                case -1:
                    flush(curWord);
                    queue.add(ENDHOLDER);
                    break;
                default:
                    if (Character.isLetter(curChar)) {
                        curWord.append((char) curChar);
                    } else {
                        flush(curWord);
                    }
            }
        }
        curToken = queue.poll();
        return curToken.getType();
    }

    public String getCurChar() {
        return curToken.getWord();
    }

    public Token getCurToken() {
        return curToken.getType();
    }
}
