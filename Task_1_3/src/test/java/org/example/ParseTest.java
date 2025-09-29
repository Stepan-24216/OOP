package org.example;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.example.objects.Expression;
import org.example.operations.Parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Проверка операций с уравнениями.
 */
public class ParseTest {

    @Test
    void simpleTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Parser parser = new Parser();
        Expression parsed = parser.parse("(((x)))");
        parsed.print();

        parsed = parser.parse("((x)+(y))");
        parsed.print();

        parsed = parser.parse("((a)+((b)*(c)))");
        parsed.print();


        String output = outContent.toString();

        assertTrue(output.contains("x\n"));
        assertTrue(output.contains("(x + y)\n"));
        assertTrue(output.contains("(a + (b * c))\n"));
    }

    @Test
    void moreLetterWord() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Parser parser = new Parser();
        Expression parsed = parser.parse("alpha+beta");
        parsed.print();

        parsed = parser.parse("veryLongVariableName * x");
        parsed.print();

        String output = outContent.toString();

        assertTrue(output.contains("(alpha + beta)\n"));
        assertTrue(output.contains("(veryLongVariableName * x)\n"));
    }

    @Test
    void spaceTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Parser parser = new Parser();
        Expression parsed = parser.parse("( x + y )");
        parsed.print();

        parsed = parser.parse("(x+ y)");
        parsed.print();

        parsed = parser.parse("(x +y)");
        parsed.print();


        String output = outContent.toString();

        assertTrue(output.contains("(x + y)\n"));
        assertTrue(output.contains("(x + y)\n"));
        assertTrue(output.contains("(x + y)\n"));
    }

    @Test
    void negativeNumberTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Parser parser = new Parser();
        Expression parsed = parser.parse("(-5)");
        parsed.print();

        parsed = parser.parse("(x + (-3))");
        parsed.print();

        parsed = parser.parse("((a*b)+((c-d)/(1+2)))");
        parsed.print();


        String output = outContent.toString();

        assertTrue(output.contains("(-5)\n"));
        assertTrue(output.contains("(x + (-3))\n"));
        assertTrue(output.contains("((a * b) + ((c - d) / (1 + 2)))\n"));
    }

    @Test
    void failTest() {
        Parser parser = new Parser();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(null);
        });

        assertEquals("Эй тут пусто!!!", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("(x+y");
        });
        assertEquals("Непарные скобки или неизвестный символ.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("x+y)");
        });
        assertEquals("Непарные скобки или неизвестный символ.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("(2{3)");
        });
        assertEquals("Непарные скобки или неизвестный символ.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("()");
        });
        assertEquals("Неправильный синтаксис. *_*", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("(+)");
        });
        assertEquals("Неправильный синтаксис. *_*", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("(x++)");
        });
        assertEquals("Неправильный синтаксис. *_*", exception.getMessage());
    }
}



