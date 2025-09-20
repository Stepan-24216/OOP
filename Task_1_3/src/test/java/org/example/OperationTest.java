package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

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

        Number x = new Number(15);
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
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        Expression ee = new Add(new Number(3), new Mul(new Number(2),
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


        Expression dv = new Div(new Number(1), new Variable("x"));
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

        Expression mulTwo = new Mul(new Add(new Variable("x"), new Number(5)), new Variable("x"));
        Expression hardDiv = mulTwo.derivative("x");
        hardDiv.print();

        Expression numVar = new Mul(new Number(5), new Variable("x"));
        Expression numVarDiv = numVar.derivative("x");
        numVarDiv.print();

        Expression varNum = new Mul(new Variable("x"), new Number(5));
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

        Expression twoSub = new Sub(new Variable("x"), new Number(5));
        Expression twoSubDiv = twoSub.derivative("x");
        twoSubDiv.print();
        int twoSubRez = twoSub.eval("x = 10");
        assertEquals(5, twoSubRez);

        String output = outContent.toString();

        assertTrue(output.contains("(1 - 0)\n"));
    }


}
