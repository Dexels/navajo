
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;

/**
 * NOTE: ONLY WORKS WITH ALL CLAUSES DELIMITED BY DEFINED DELIMITERS. IF NO DELIMITERS
 * ARE FOUND, THE CLAUSE IS NOT RECOGNIZED AS A CLAUSE.
 */
public class ClauseStream {

  private String input;
  private int startChar = 0;
  private char beginChar = '(';
  private char endChar = ')';
  private String operators = "&|";
  private String unaryOperator = "!";
  private int startCharOp = 0;
  private char unary = ' ';

  public ClauseStream(String s) {
    input = s;
  }

  public ClauseStream(String s, char delimit1, char delimit2, String operatorString) {
    input = s;
    beginChar = delimit1;
    endChar = delimit2;
    operators = operatorString;
  }

  public char isUnary() {
    return unary;
  }

  public boolean containsOperators() {
    for (int i = 0; i < operators.length(); i++) {
      char c = operators.charAt(i);
      if (input.indexOf(c) != -1)
        return true;
    }
    return false;
  }

  public boolean containsClause() {
    // Check if remaining input contains valid clauses.
    // An input string contains valid clauses if either of the following is true:
    // If it contains delimiters and there are operators present.
    // It there are no delimites, but there are operators (undelimited clause).
    if ((input.indexOf(beginChar+"") != -1) && containsOperators())
      return true;
    else if (containsOperators())
      return true;
    else
      return false;
  }

  public char getNextOperator() throws ClauseException {

    char operator = '.';
    char c;

    for (int i = startChar; i < input.length(); i++) {
      c = input.charAt(i);
      if (operators.indexOf(c+"") != -1) {
        startChar = i+1;
        return c;
      }
    }

    return operator;
  }

  public String getNextClause() throws ClauseException {
    StringBuffer value = new StringBuffer();

    char c;
    int start = 0;
    int startUndelimited = 0;
    boolean end = false;
    int prevStartChar = startChar;

    if (startChar >= input.length())
      return "EOC";

    this.unary = ' ';
    for (int i = startChar; i < input.length(); i++) {
      c = input.charAt(i);
      if (startUndelimited == 0) {
        if (c == beginChar) {
          start++;
        } else
        if (c == endChar) {
          start--;
          if (start < 0)
            throw new ClauseException("Syntax error at " + i);
          else
          if (start == 0)
            end = true;
        } else
        if ((unaryOperator.indexOf(c+"") != -1) && (start == 0)) {
          this.unary = c;
        }

        if ((start > 0) && (end == false)) {
          if (!((start == 1) && (c == beginChar))) {
            value.append(c);
          }
        }
        if (end == true) {
          startChar = i+1;
          return value.toString();
        }
      }

      if ((start == 0) && (c != ' ') && (operators.indexOf(c+"") == -1) && (unaryOperator.indexOf(c+"") == -1)) {
        // Found a character other than and prior to delimiters, operators or space:
        // determines begin of undelimited clause (single value expression).
        startUndelimited = 1;
      }

      if (startUndelimited == 1) {
        // No begin delimiter found, but an undelimited clause.
        if (operators.indexOf(c+"") == -1) {
          // Start of clause.
          value.append(c);
        } else {
          // Start of operator determines end of clause.
          startChar = i;
          return value.toString();
        }
      }
    }

    if (startUndelimited == 0)
      throw new ClauseException("Syntax error, unbalanced parentheses: " + input);
    else {
      startChar = input.length();
      return value.toString();
    }
  }
}