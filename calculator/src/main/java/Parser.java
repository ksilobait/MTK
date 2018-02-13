import java.io.IOException;

public class Parser {
    private Lexer lexer;
    private Lexeme currentLexeme;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        this.currentLexeme = lexer.getLexeme();
    }

    public int calculate() throws IOException {
        int result = parseExpr();
        if (currentLexeme.type != Lexeme.LexemeType.EOF) {
            throw new IllegalStateException("unexpected ending of the string");
        }

        return result;
    }

    private int parseExpr() throws IOException {
        int result = parseAdditiveTerm();

        while (currentLexeme.type == Lexeme.LexemeType.PLUS || currentLexeme.type == Lexeme.LexemeType.MINUS) {
            int sign = 1;
            if (currentLexeme.type == Lexeme.LexemeType.MINUS) {
                sign = -1;
            }

            currentLexeme = lexer.getLexeme();

            int nextValue = parseAdditiveTerm();
            result += sign * nextValue;
        }

        return result;
    }

    private int parseAdditiveTerm() throws IOException {
        int result = parseMultiplicativeTerm();

        while (currentLexeme.type == Lexeme.LexemeType.MULTIPLICATION || currentLexeme.type == Lexeme.LexemeType.DIVISION) {
            int type = 1;
            if (currentLexeme.type == Lexeme.LexemeType.DIVISION) {
                type = -1;
            }

            currentLexeme = lexer.getLexeme();

            int nextValue = parseMultiplicativeTerm();
            if (type == 1) {
                result *= nextValue;
            } else {
                result /= nextValue;
            }
        }

        return result;
    }

    private int parseMultiplicativeTerm() throws IOException {
        int base = parsePowerBase();

        if (currentLexeme.type == Lexeme.LexemeType.POWER) {
            currentLexeme = lexer.getLexeme();
            int power = parseMultiplicativeTerm();

            int result = 1;
            for (int i = 0; i < power; i++) {
                result *= base;
            }
            return result;
        }

        return base;
    }

    private int parsePowerBase() throws IOException {
        if (currentLexeme.type == Lexeme.LexemeType.MINUS) {
            currentLexeme = lexer.getLexeme();
            return -1 * parseMultiplicativeTerm();
        }

        return parseAtom();
    }

    private int parseAtom() throws IOException {
        if (currentLexeme.type == Lexeme.LexemeType.NUMBER) {
            String s = currentLexeme.data;
            int result = Integer.parseInt(s);
            currentLexeme = lexer.getLexeme();
            return result;
        }

        if (currentLexeme.type == Lexeme.LexemeType.OPENING_PARENTHESIS) {
            currentLexeme = lexer.getLexeme();
            int result = parseExpr();
            if (currentLexeme.type != Lexeme.LexemeType.CLOSING_PARENTHESIS) {
                throw new IllegalStateException("expected closing parenthesis");
            }
            currentLexeme = lexer.getLexeme();
            return result;
        }

        throw new IllegalStateException("unknown rule for atom");
    }
}
