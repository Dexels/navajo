package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.text.ParseException;


public class ParseDate extends FunctionInterface {

  public String remarks() {
    return "Given a string and a date pattern, a date object is returned.";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    if (this.getOperands().size() != 2)
      throw new TMLExpressionException(this, "usage: ParseDate(string, format)");

    if (this.getOperand(0) == null)
      throw new TMLExpressionException(this,
          "error: null value in string operand of ParseDate(string, format).");

    if (this.getOperand(1) == null)
      throw new TMLExpressionException(this,
          "error: null value in format operand of ParseDate(string, format).");

    String s = this.getOperand(0).toString();
    String format = (String)this.getOperands().get(1);

    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
        format);

    java.util.Date date = null;
    try {
      date = formatter.parse(s);
    }
    catch (ParseException ex) {
      throw new TMLExpressionException(this, "Invalid date format: " + s + " for given date pattern: " + format);
    }

    return date;

  }

  public String usage() {
    return "ParseDate(string representation, pattern)";
  }

  public static void main(String [] args) throws Exception {
    ParseDate pd = new ParseDate();
    pd.reset();
    pd.insertOperand("01-01-1978");
    pd.insertOperand("");
    java.util.Date d = (java.util.Date) pd.evaluate();
    System.err.println(d);
  }
}