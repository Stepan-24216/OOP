package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


public class OperationTest {

    @Test
    void TestNumber(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Number x = new Number(15);
        x.print();
        Expression der = x.derivative("x");
        der.print();
        int evl = x.eval("x = 10");

        String output = outContent.toString();

        assertTrue(output.contains("15\n"));
        assertTrue(output.contains("0\n"));
        assertEquals(evl,15);
        assertEquals("15",x.toString());
    }

    @Test
    void TestVariable(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Variable x = new Variable("x");
        x.print();
        Expression der1 = x.derivative("x");
        Expression der2 = x.derivative("y");
        der1.print();
        der2.print();
        int evl = x.eval("x = 10");

        String output = outContent.toString();

        assertTrue(output.contains("x\n"));
        assertTrue(output.contains("1\n"));
        assertTrue(output.contains("0\n"));
        assertEquals(evl,10);
        assertEquals("x",x.toString());
    }

    @Test
    void TestExpression(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        Expression dv = new Div(new Number(1),new Variable("x"));
        Expression di = dv.derivative("y");
        di.print();

        Expression var = new Mul(new Variable("x"), new Variable("y"));

        int rez = var.eval("x = 10 ; y = 5");
        Expression ee = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x"))); // (3+(2*x))
        int result = ee.eval("x = 10; y = 13");

        String output = outContent.toString();

        assertTrue(output.contains("(3 + (2 * x))\n"));
        assertTrue(output.contains("(0 + (2 * 1))\n"));
        assertTrue(output.contains("(((0 * x) - (1 * 0)) / (x * x))\n"));
        assertEquals(rez,50);
        assertEquals(result,23);
    }


}
