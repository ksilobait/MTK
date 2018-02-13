import org.junit.Assert;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

public class ParserTest {

    @org.junit.Test
    public void calculate1() throws IOException {
        Reader reader = new StringReader("5+6");
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Assert.assertEquals(11, parser.calculate());
    }

    @org.junit.Test
    public void calculate2() throws IOException {
        Reader reader = new StringReader("-2^2*10");
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Assert.assertEquals(-40, parser.calculate());
    }

    @org.junit.Test
    public void calculate3() throws IOException {
        Reader reader = new StringReader("(((1 + 2) * 3) + 4) * 5");
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Assert.assertEquals(65, parser.calculate());
    }

    @org.junit.Test(expected = IllegalStateException.class)
    public void exception1() throws IOException {
        Reader reader = new StringReader("((8+4)");
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        double error = parser.calculate();
        assertTrue(false);
    }

    @org.junit.Test(expected = IllegalStateException.class)
    public void exception2() throws IOException {
        Reader reader = new StringReader("(8+4))");
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        double error = parser.calculate();
        assertTrue(false);
    }
}
