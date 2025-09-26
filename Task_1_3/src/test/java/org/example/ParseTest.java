package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


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
    void moreLetterWord(){
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
    void failTest(){
        Parser parser = new Parser();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(null);
        });

        assertEquals("Не опознанный объект. *_*", exception.getMessage());

        Expression parsed = parser.parse("(x+y");
        parsed.print();

        parsed = parser.parse("x+y)");
        parsed.print();

        parsed = parser.parse("(x # y)");
        parsed.print();

        parsed = parser.parse("()");
        parsed.print();

        parsed = parser.parse("(+)");
        parsed.print();

        parsed = parser.parse("(x++)");
        parsed.print();
    }
}

//// Непарные скобки
//        "(x+y" → ParseException
//"x+y)" → ParseException
//
//// Неизвестные символы
//"(x # y)" → ParseException
//
//// Неправильный синтаксис
//"()" → ParseException
//"(+)" → ParseException
//"(x++)" → ParseException

//// Производная константы
//new Number(5).derivative("x") → new Number(0)
//
//// Производная переменной по себе
//new Variable("x").derivative("x") → new Number(1)
//
//// Производная переменной по другой переменной
//new Variable("x").derivative("y") → new Number(0)
//
//// Дифференцирование выражений без переменной
//Expression e = new Add(new Number(3), new Number(5));
//e.derivative("x") → должно быть new Number(0)
//
//// Многократное дифференцирование
//Expression e = new Variable("x");
//Expression de1 = e.derivative("x"); // 1
//Expression de2 = de1.derivative("x"); // 0
//
////f(x) = (x² + 3x)³
//Expression inner = new Add(
//        new Mul(new Variable("x"), new Variable("x")),
//        new Mul(new Number(3), new Variable("x"))
//);
//Expression f = new Mul(inner, new Mul(inner, inner)); - взять производную по x
//// f'(x) = 3*(x² + 3x)² * (2x + 3)
//
//Expression e = new Add(
//        new Mul(new Variable("x"), new Variable("y")),
//        new Div(new Variable("z"), new Number(2))
//);
//e.eval("x=3; y=4; z=10") → (34 + 10/2) = 12 + 5 = 17
//        e.eval("x=0; y=100; z=8") → (0100 + 8/2) = 0 + 4 = 4
//
//// Переменные в разном порядке
//        e.eval("z=10; x=3; y=4") → 17
//// Лишние переменные
//        e.eval("x=3; y=4; z=10; w=100") → 17
//        e.eval("x=3; y=4") // z не задана → ошибка
//new Number(5).eval("") → 5 - пустое означивание для выражений без переменных
//new Add(new Number(2), new Number(3)).eval("") → 5
//
//Некорректный ввод:


//
//// Некорректное означивание переменных
//e.eval("x=; y=5")
//e.eval("x=abc") → не число
//e.eval("x=5 y=3") → нет разделителя
//
//к парсингу
//// Пустые значения
//e.eval("x=; y=5") → должно бросать исключение
//// Лишние пробелы и разделители
//e.eval(" x = 3 ; y = 4 ; ; ") → должно работать
//// Нестандартные разделители
//e.eval("x=3, y=4") → должно бросать исключение

