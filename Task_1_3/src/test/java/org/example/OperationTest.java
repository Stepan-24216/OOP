package org.example;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.example.objects.Expression;
import org.example.objects.Number;
import org.example.objects.Variable;
import org.example.operations.Add;
import org.example.operations.Div;
import org.example.operations.Mul;
import org.example.operations.Parser;
import org.example.operations.Sub;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Проверка операций с уравнениями.
 */
public class OperationTest {

    /**
     * Проверка класса число.
     */
    @Test
    void testNumber() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.example.objects.Number x = new org.example.objects.Number(15);
        x.print();
        Expression der = x.derivative("x");
        der.print();

        int evl = x.eval("x = 10");
        assertEquals(15, evl);

        String output = outContent.toString();

        assertTrue(output.contains("15\n"));
        assertTrue(output.contains("0\n"));

        assertEquals("15", x.toString());
    }

    /**
     * Проверка класса переменная.
     */
    @Test
    void testVariable() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Variable x = new Variable("x");
        x.print();
        Expression der1 = x.derivative("x");
        Expression der2 = x.derivative("y");
        der1.print();
        der2.print();

        int evl = x.eval("x = 10");
        assertEquals(10, evl);

        String output = outContent.toString();

        assertTrue(output.contains("x\n"));
        assertTrue(output.contains("1\n"));
        assertTrue(output.contains("0\n"));
        assertEquals("x", x.toString());
    }

    /**
     * Проверка тестов показанных в заданиях.
     */
    @Test
    void testTask() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // TaskTests
        Expression e =
            new Add(new org.example.objects.Number(3), new Mul(new org.example.objects.Number(2),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        Expression ee =
            new Add(new org.example.objects.Number(3), new Mul(new org.example.objects.Number(2),
                new Variable("x"))); // (3+(2*x))
        int result = ee.eval("x = 10; y = 13");
        System.out.println(result);

        String output = outContent.toString();

        assertTrue(output.contains("(3 + (2 * x))\n"));
        assertTrue(output.contains("(0 + (2 * 1))\n"));
        assertTrue(output.contains("23\n"));
    }

    /**
     * Проверка класса для операции деления.
     */
    @Test
    void testDiv() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        Expression dv = new Div(new org.example.objects.Number(1), new Variable("x"));
        Expression di = dv.derivative("x");
        di.print();

        String output = outContent.toString();

        assertTrue(output.contains("(((0 * x) - (1 * 1)) / (x * x))\n"));
    }

    /**
     * Проверка класса для операции умножения.
     */
    @Test
    void testMul() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Expression var = new Mul(new Variable("x"), new Variable("y"));
        var.print();

        int rez = var.eval("x = 10 ; y = 5");
        assertEquals(50, rez);

        Expression mulTwo = new Mul(new Add(new Variable("x"), new org.example.objects.Number(5)),
            new Variable("x"));
        Expression hardDiv = mulTwo.derivative("x");
        hardDiv.print();

        Expression numVar = new Mul(new org.example.objects.Number(5), new Variable("x"));
        Expression numVarDiv = numVar.derivative("x");
        numVarDiv.print();

        Expression varNum = new Mul(new Variable("x"), new org.example.objects.Number(5));
        Expression varNumDiv = varNum.derivative("x");
        varNumDiv.print();

        String output = outContent.toString();

        assertTrue(output.contains("(x * y)\n"));
        assertTrue(output.contains("(((1 + 0) * x) + ((x + 5) * 1))\n"));
        assertTrue(output.contains("(5 * 1)\n"));
        assertTrue(output.contains("(1 * 5)\n"));

    }

    /**
     * Проверка класса для операции вычитания.
     */
    @Test
    void testSub() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Expression twoSub = new Sub(new Variable("x"), new org.example.objects.Number(5));
        Expression twoSubDiv = twoSub.derivative("x");
        twoSubDiv.print();
        int twoSubRez = twoSub.eval("x = 10");
        assertEquals(5, twoSubRez);

        String output = outContent.toString();

        assertTrue(output.contains("1\n"));
    }

    @Test
    void testDerivative() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Expression e = new Variable("x");
        Expression de1 = e.derivative("x"); // 1
        Expression de2 = de1.derivative("x"); // 0
        assertEquals(0, ((org.example.objects.Number) de2).getValue());

        e = new Add(new org.example.objects.Number(3),
            new org.example.objects.Number(5));

        assertEquals(0,
            ((org.example.objects.Number) e.derivative("x").simplification()).getValue());

        assertEquals(0,
            ((org.example.objects.Number) new Variable("x").derivative("y")).getValue());

        assertEquals(0, ((org.example.objects.Number) new org.example.objects.Number(5).derivative(
            "x")).getValue());

        assertEquals(1,
            ((org.example.objects.Number) new Variable("x").derivative("x")).getValue());

        Expression inner = new Add(
            new Mul(new Variable("x"), new Variable("x")),
            new Mul(new org.example.objects.Number(3), new Variable("x"))
        );
        Expression f = new Mul(inner, new Mul(inner, inner));
        (f.derivative("x")).print();

        String output = outContent.toString();

        assertTrue(output.contains(
            "(((((1 * x) + (x * 1)) + (3 * 1)) * (((x * x) + (3 * x)) * ((x * x) + (3 * x)))) + ("
                + "((x * x) + (3 * x)) * (((((1 * x) + (x * 1)) + (3 * 1)) * ((x * x) + (3 * x))) +"
                + " (((x * x) + (3 * x)) * (((1 * x) + (x * 1)) + (3 * 1))))))\n"));
    }

    @Test
    void testEval() {
        Expression var = new Mul(new Variable("x"), new Variable("y"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            var.eval("x=; y=5");
        });
        assertEquals("Отсутствует важная для означивания часть строки", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            var.eval("x 1; y=5");
        });
        assertEquals("Отсутствует важная для означивания часть строки", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            var.eval("x=abc");
        });
        assertEquals("Некорректное числовое значение для переменной", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            var.eval("x=5 y=3");
        });
        assertEquals("Некорректное означивание переменных", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            (new Add(new Add(new Variable("x"), new Variable("y")), new Variable("z"))).eval(
                "x=3; y=4");
        });
        assertEquals("Не найдено значение для переменной: z", exception.getMessage());

        int num = var.eval(" x = 3 ; y = 4 ; ; ");
        assertEquals(12, num);

        assertEquals(103,
            (new Add(new Variable("x"), new Variable("w"))).eval("x=3; y=4; z=10; w=100"));

        assertEquals(5, new org.example.objects.Number(5).eval(""));

        assertEquals(5,
            new Add(new org.example.objects.Number(2), new org.example.objects.Number(3)).eval(""));

        assertEquals(17,
            (new Add(new Add(new Variable("x"), new Variable("y")), new Variable("z"))).eval(
                "z=10; x=3; y=4"));

        Expression e = new Add(
            new Mul(new Variable("x"), new Variable("y")),
            new Div(new Variable("z"), new Number(2))
        );

        int a = e.eval("x=3; y=4; z=10"); // → (34 + 10/2) = 12 + 5 = 17
        int b = e.eval("x=0; y=100; z=8"); // → (0100 + 8/2) = 0 + 4 = 4

        assertEquals(17, a);

        assertEquals(4, b);
    }

    @Test
    void testSimplification() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Parser parser = new Parser();
        Expression f = parser.parse("0-x");
        Expression a = f.simplification();
        a.print();
        f = parser.parse("0-0");
        a = f.simplification();
        a.print();
        f = parser.parse("0/x");
        a = f.simplification();
        a.print();
        f = parser.parse("abc*0");
        a = f.simplification();
        a.print();
        f = parser.parse("0*abc");
        a = f.simplification();
        a.print();
        f = parser.parse("0*abc");
        a = f.simplification();
        a.print();
        f = parser.parse("10-10");
        a = f.simplification();
        a.print();
        f = parser.parse("13-10");
        a = f.simplification();
        a.print();
        f = parser.parse("21/7");
        a = f.simplification();
        a.print();
        f = parser.parse("-5+8");
        a = f.simplification();
        a.print();
        f = parser.parse("1*3");
        a = f.simplification();
        a.print();

        String output = outContent.toString();

        assertTrue(output.contains("0\n"
            + "0\n"
            + "0\n"
            + "0\n"
            + "0\n"
            + "0\n"
            + "3\n"
            + "3\n"
            + "3\n"
            + "3\n"));
    }
}