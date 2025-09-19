package org.example;

public class main {
    public static void main(String[] args){
        Number x = new Number(10);
        x.print();
        Variable y = new Variable("xs");
        Expression z = y.derivative("xs");
        z.print();
        Expression b = new Number(5);
        Expression c = y;
        Expression a = new Add(b,c);
        Expression d = a.derivative("xs");
        d.print();

        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        Expression dv = new Div(new Number(1),new Variable("x"));
        Expression di = dv.derivative("y");
        di.print();
//        Expression ee = new Add(new Number(3), new Mul(new Number(2),
//                new Variable("x"))); // (3+(2*x))
//        int result = ee.eval("x = 10; y = 13");
//        System.out.println(result);
    }
}
