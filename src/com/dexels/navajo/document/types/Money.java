package com.dexels.navajo.document.types;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * <p>Title: Money objects</p>
 * <p>Description: A class for representing money typed objects</p>
 * <p>Copyright: Copyright (c) 2003-2004</p>
 * <p>Company: Dexels BV</p>
 * @author $author$
 * @version $Id$
 */

public final class Money implements Comparable {

  private Double value = null;
  private static DecimalFormat nf = new DecimalFormat("¤ #,##0.00;¤ -#,##0.00");
  private static DecimalFormat number = new DecimalFormat("0.00");

  static {
//    nf.setNegativePrefix("- \u00A4");
//    nf.setNegativeSuffix("\u00A4");
  }



  public Money(Double d) {
    value = d;
  }

  public Money() {
    value = null;
  }


  public Money(Object o) {
    if (o instanceof Money) {
      value = ((Money) o).value;
    } else if (o instanceof Double)
       value = (Double) o;
    else if (o instanceof Integer) {
       value = new Double(((Integer) o).intValue());
    } else if (o instanceof String && !((String) o).trim().equals("")) {
       value = new Double(o+"");
    } else {
      value = null;
    }
  }

  public Money(Integer d) {
    if (d != null) {
      value = new Double(d.intValue());
    }
  }

  public Money(int d) {
    value = new Double(d);
  }

  public Money(double d) {
    value = new Double(d);
  }

  public Money(String d) {
    try {
      if (d != null && !d.trim().equals("")) {
        value = new Double(d);
      }
    } catch (Throwable t) {
      value = null;
    }
  }

  public String formattedString() {
    if (value == null) {
      return "-";
    }
    return nf.format(value);
  }

  public String toString() {
    if (value == null) {
      return "";
    } else {
      return number.format(value).replace(',','.');
    }
  }

  public final double doubleValue() {
    if (value == null) {
      return 0;
    }
    return value.doubleValue();
  }

  public final int compareTo(Object o) {
    if (!(o instanceof Money)) {
//      System.err.println("Comparing money to non-money: "+(o==null?"null":o.getClass().getName()));
      return 0;
    }
    Money other = (Money) o;
    if (other.doubleValue() == this.doubleValue())
      return 0;
//    System.err.println("MONEY DIFFERENCE: "+doubleValue()+" vs. "+other.doubleValue());
    if (this.doubleValue() < other.doubleValue())
      return 1;
    return -1;
  }

  public static void main(String [] args) {
    Locale.setDefault(new Locale("nl", "NL"));
    System.err.println(new Money(45.34324)+"");
  }

  public boolean equals(Object obj) {

    if (value == null && obj == null) {
     return true;
   }

   if (value == null || obj == null) {
     return false;
   }

    if (obj instanceof Money) {
      Money m = (Money)obj;
      if (m.value == null) {
      return false;
    }

      return compareTo(m)==0;
    } else {
      return false;
    }
  }

}
