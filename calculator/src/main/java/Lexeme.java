public class Lexeme {
    enum LexemeType {
        PLUS, MINUS, MULTIPLICATION, DIVISION, POWER, NUMBER, OPENING_PARENTHESIS, CLOSING_PARENTHESIS, EOF
    }

    public String data;
    public LexemeType type;
}
