package com.dexels.navajo.document.types;

import java.text.NumberFormat;

/**
 * <p>Title: Money objects</p>
 * <p>Description: A class for representing money typed objects</p>
 * <p>Copyright: Copyright (c) 2003-2004</p>
 * <p>Company: Dexels BV</p>
 * @author $author$
 * @version $Id$
 */

public class Percentage implements Comparable {

  private Double value;
  private static NumberFormat nf = NumberFormat.getPercentInstance();

  public Percentage(Double d) {
    value = d;
  }

  public Percentage() {
    value = null;
  }


  public Percentage(Object o) {
    if (o instanceof Double)
       value = (Double) o;
    else if (o instanceof Integer) {
       value = new Double(((Integer) o).intValue());
    } else {
       value = new Double(o+"");
    }
  }

  public Percentage(Integer d) {
    if (d != null) {
      value = new Double(d.intValue());
    }
  }

  public Percentage(int d) {
    value = new Double(d);
  }

  public Percentage(double d) {
    value = new Double(d);
  }

  public Percentage(String d) {
    try {
      if (d != null) {
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
    }
    return value.doubleValue()+"";
  }

  public double doubleValue() {
    if (value == null) {
      return 0;
    }
    return value.doubleValue();
  }

  public int compareTo(Object o) {
    if (!(o instanceof Percentage))
      return 0;
    Percentage other = (Percentage) o;
    if (other.doubleValue() == this.doubleValue())
      return 0;
    if (this.doubleValue() < other.doubleValue())
      return 1;
    return -1;
  }

}
