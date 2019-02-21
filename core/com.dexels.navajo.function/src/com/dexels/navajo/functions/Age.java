package com.dexels.navajo.functions;

import java.util.Calendar;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class Age
    extends FunctionInterface {

  public Age() {}

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

    final Object o = this.getOperands().get(0);
    if (o == null | ! (o instanceof java.util.Date)) {
      throw new TMLExpressionException(
          "Age: could not return value for input: " + o);
    }

    final java.util.Date datum = (java.util.Date) o;

    final Calendar cal = Calendar.getInstance();

    cal.setTime(datum);

    /* if a second operand is provided, calculate the age from that
        date, otherwise calculate the age as of today */
    Calendar asofCal = Calendar.getInstance();
    asofCal.setTime(new java.util.Date());

    if ( this.getOperands().size() > 1 ) {
      final Object p = this.getOperands().get(1);
      if ( (p != null) && (p instanceof java.util.Date)) {
        asofCal.setTime( (java.util.Date) p);
      }
    }

    int yToday = asofCal.get(Calendar.YEAR);
    int dToday = cal.get(Calendar.YEAR);
    int result = yToday - dToday;

    if ( (cal.get(Calendar.MONTH) > asofCal.get(Calendar.MONTH))) {
      result--;
    }
    else if ( (cal.get(Calendar.MONTH) == asofCal.get(Calendar.MONTH)) &&
             (cal.get(Calendar.DAY_OF_MONTH) >
              asofCal.get(Calendar.DAY_OF_MONTH))) {
      result--;
    }

    return Integer.valueOf(result);
  }

  @Override
public String remarks() {
    return "calculates the age given a birth date.  The calculation is as of the second date parameter, otherwise, if not provided, will be as of today.";
  }

  public static void main(String args[]) throws Exception {
    final Age a = new Age();
    final Calendar c = Calendar.getInstance();
    c.setTime(new java.util.Date());
    final java.util.Date now = c.getTime();

    c.set(1966, 11, 20);
    System.err.println("birth date is " + c.getTime());
    a.reset();
    a.insertDateOperand(c.getTime());
    Integer age = (Integer) a.evaluate();

    System.err.println("age = " + age.intValue() + ", as of " + now);

    final Calendar asof = Calendar.getInstance();
    asof.set(1972, 4, 14);
    a.reset();
    a.insertDateOperand(c.getTime());
    a.insertDateOperand(asof.getTime());
    age = (Integer) a.evaluate();
    System.err.println("age = " + age.intValue() + ", as of " + asof.getTime());

  }
}