import java.io.IOException;
import java.io.Reader;

public class Lexer {
    private Reader reader;
    private int currentCharacter;

    public Lexer(Reader reader) throws IOException {
        this.reader = reader;
        this.currentCharacter = reader.read();
    }

    public Lexeme getLexeme() throws IOException {
        while (Character.isWhitespace(currentCharacter)) {
            currentCharacter = reader.read();
        }

        Lexeme toReturn = new Lexeme();

        if (Character.isDigit(currentCharacter)) {
            StringBuilder number = new StringBuilder();
            while (Character.isDigit(currentCharacter)) {
                number.append((char) currentCharacter);
                currentCharacter = reader.read();
            }

            toReturn.data = number.toString();
            toReturn.type = Lexeme.LexemeType.NUMBER;
            return toReturn;
        }

        switch (currentCharacter) {
            case '+':
                toReturn.data = "+";
                toReturn.type = Lexeme.LexemeType.PLUS;
                break;
            case '-':
                toReturn.data = "-";
                toReturn.type = Lexeme.LexemeType.MINUS;
                break;
            case '*':
                toReturn.data = "*";
                toReturn.type = Lexeme.LexemeType.MULTIPLICATION;
                break;
            case '/':
                toReturn.data = "/";
                toReturn.type = Lexeme.LexemeType.DIVISION;
                break;
            case '^':
                toReturn.data = "^";
                toReturn.type = Lexeme.LexemeType.POWER;
                break;
            case '(':
                toReturn.data = "(";
                toReturn.type = Lexeme.LexemeType.OPENING_PARENTHESIS;
                break;
            case ')':
                toReturn.data = ")";
                toReturn.type = Lexeme.LexemeType.CLOSING_PARENTHESIS;
                break;
            case -1:
                toReturn.data = null;
                toReturn.type = Lexeme.LexemeType.EOF;
                break;
            default:
                throw new IllegalStateException("illegal character in string");
        }
        currentCharacter = reader.read();
        return toReturn;
    }
}
