package parser;

public class TokenHolder {
    public static TokenHolder COMMAHOLDER = new TokenHolder(Token.COMMA);
    public static TokenHolder ENDHOLDER = new TokenHolder(Token.END);
    public static TokenHolder SEMICOLONHOLDER = new TokenHolder(Token.SEMICOLON);
    public static TokenHolder COLONHOLDER = new TokenHolder(Token.COLON);
    public static TokenHolder VARHOLDER = new TokenHolder(Token.VAR);

    private final Token type;
    private final String word;

    public TokenHolder(Token type) {
        this.type = type;
        word = "";
    }

    public TokenHolder(String word) {
        if (word.equals("var")) {
            throw new RuntimeException("Var is not allowed");
        }
        this.word = word;
        this.type = Token.WORD;
    }

    public String getWord() {
        return word;
    }

    public Token getType() {
        return type;
    }
}
