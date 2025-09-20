package org.example;

import java.util.HashMap;
import java.util.Map;

public class Variable extends Expression {
    private final String var;

    public Variable(String name) {
        this.var = name;
    }

    public String toString() {
        return var;
    }

    public Expression derivative(String variable) {
        return new Number(this.var.equals(variable) ? 1 : 0);
    }

    public int eval(String s) {
        String[] parts = s.split(";");
        Map<String, String> variables = new HashMap<>();

        for (String test : parts) {
            String[] varAndNum = test.split("=");
            if (varAndNum.length == 2) {
                String variable = varAndNum[0].trim();
                String value = varAndNum[1].trim();
                variables.put(variable, value);
            }
        }

        if (variables.containsKey(var)) {
            return Integer.parseInt(variables.get(var));
        }
        return 0;
    }
}
