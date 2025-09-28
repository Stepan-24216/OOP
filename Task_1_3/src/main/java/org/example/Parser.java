package org.example;

import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;


public class Parser {
  private String input;
  private int position;
  private char currentChar;

  public Expression parse(String expression) {
    if (expression == null || expression == "") {
      throw new IllegalArgumentException("Эй тут пусто!!!");
    }
    this.input = expression.replaceAll("\\s+", ""); // Убираем пробелы
    this.position = 0;
    this.currentChar = input.length() > 0 ? input.charAt(0) : '\0';

    Expression result = parseExpression();
    if (position < input.length()) {
      throw new IllegalArgumentException("Непарные скобки или неизвестный символ.");
    }

    return result;
  }

  private void advance() {
    position++;
    if (position < input.length()) {
      currentChar = input.charAt(position);
    } else {
      currentChar = '\0';
    }
  }

  private boolean isVariableChar(char c) {
    return Character.isLetter(c) || c == '_' ||
        (Character.isDigit(c) && position > 0 && Character.isLetter(input.charAt(position - 1)));
  }

  private Expression parseExpression() {
    Expression left = parseTerm();

    while (currentChar == '+' || currentChar == '-') {
      char operator = currentChar;
      advance();
      Expression right = parseTerm();
      if (operator == '+') {
        left = new Add(left, right);
      } else {
        left = new Sub(left, right);
      }
    }

    return left;
  }

  private Expression parseTerm() {
    Expression left = parseFactor();

    while (currentChar == '*' || currentChar == '/') {
      char operator = currentChar;
      advance();
      Expression right = parseFactor();
      if (operator == '*') {
        left = new Mul(left, right);
      } else {
        left = new Div(left, right);
      }
    }

    return left;
  }

  private Expression parseFactor() {

    if (currentChar == '(') {
      advance(); // пропускаем '('
      Expression expr = parseExpression();
      if (currentChar != ')') {
        throw new IllegalArgumentException("Непарные скобки или неизвестный символ.");
      }
      advance(); // пропускаем ')'
      return expr;
    } else if (Character.isDigit(currentChar) || currentChar == '-') {
      if (currentChar == '-') {
        advance();
        return parseNumber(true);
      } else {
        return parseNumber(false);
      }
    } else if (Character.isLetter(currentChar)) {
      return parseVariable();
    } else {
      throw new IllegalArgumentException("Неправильный синтаксис. *_*");
    }
  }

  /**
   * читаем число.
   */
  private Expression parseNumber(boolean flag) {
    StringBuilder sb = new StringBuilder();

    // Читаем только цифры (целые числа)
    while (Character.isDigit(currentChar)) {
      sb.append(currentChar);
      advance();
    }

    try {
      int value = Integer.parseInt(sb.toString());
      if (flag) {
        return new Number(-value);
      } else {
        return new Number(value);
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Некорекнтное число. *_*");
    }
  }

  /**
   * Читаем имя переменной.
   */
  private Expression parseVariable() {
    StringBuilder sb = new StringBuilder();

    while (isVariableChar(currentChar)) {
      sb.append(currentChar);
      advance();
    }

    String varName = sb.toString();

    // Проверяем, что имя переменной не пустое
    if (varName.isEmpty()) {
      throw new IllegalArgumentException("Имя переменной пустое. *_*");
    }

    // Проверяем, что имя начинается с буквы
    if (!Character.isLetter(varName.charAt(0))) {
      throw new IllegalArgumentException("Некоректное имя переменной. *_*");
    }

    return new Variable(varName);
  }

}

