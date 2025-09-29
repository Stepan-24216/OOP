package org.example.objects;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация методов для переменных.
 */
public class Variable extends Expression {
    private final String var;

    public Variable(String name) {
        this.var = name;
    }

    /**
     * Преобразование в строку.
     */
    public String toString() {
        return var;
    }

    /**
     * Взятие производной.
     */
    public Expression derivative(String variable) {
        return new Number(this.var.equals(variable) ? 1 : 0);
    }

    /**
     * Упрощение выражения.
     */
    public Expression simplification() {
        return new Variable(var);
    }

    /**
     * Означивание для переменной.
     */
    public int eval(String s) {
        String[] parts = s.split(";");
        Map<String, String> variables = new HashMap<>();

        for (String test : parts) {
            String[] varAndNum = test.split("=");

            if (test == null || test.trim().isEmpty()) {
                continue; // переходим к следующей итерации
            }

            if (varAndNum.length == 2) {
                String variable = varAndNum[0].trim();
                String value = varAndNum[1].trim();
                variables.put(variable, value);
            } else if (varAndNum.length > 2) {
                throw new IllegalArgumentException("Некорректное означивание переменных");
            } else if (varAndNum.length == 1) {
                throw new IllegalArgumentException(
                    "Отсутствует важная для означивания часть строки");
            }
        }

        if (variables.containsKey(var) || variables.containsKey(var.substring(1))) {
            try {
                if (var.charAt(0) == '-') {
                    return -Integer.parseInt(variables.get(var.substring(1)));
                }
                return Integer.parseInt(variables.get(var));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректное числовое значение для переменной");
            }
        }
        throw new IllegalArgumentException("Не найдено значение для переменной: " + var);
    }
}
