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

public final class Percentage
    implements Comparable {

  private Double value;
  private static NumberFormat nf = NumberFormat.getPercentInstance();
  static {
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
  }

  public Percentage(Double d) {
    value = d;
  }

  public Percentage() {
    value = null;
  }

  public Percentage(Object o) {
    if (o instanceof Percentage) {
      value = ( (Percentage) o).value;
    }
    else if (o instanceof Double) {
      value = (Double) o;
    }
    else if (o instanceof Integer) {
      value = new Double( ( (Integer) o).intValue());
    }
    else {
      value = new Double(o + "");
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
    }
    catch (Throwable t) {
      value = null;
    }
  }

  public final String formattedString() {
    if (value == null) {
      return "-";
    }
    return nf.format(value);
  }

  public final String toString() {
    if (value == null) {
      return "";
    }
    return value.doubleValue() + "";
  }

  public final double doubleValue() {
    if (value == null) {
      return 0;
    }
    return value.doubleValue();
  }

  public final int compareTo(Object o) {
    if (! (o instanceof Percentage)) {
      return 0;
    }
    Percentage other = (Percentage) o;
    if (other.doubleValue() == this.doubleValue()) {
      return 0;
    }
    if (this.doubleValue() < other.doubleValue()) {
      return 1;
    }
    return -1;
  }

  public boolean equals(Object obj) {

    if (value == null && obj == null) {
      return true;
    }

    if (value == null || obj == null) {
      return false;
    }

    if (obj instanceof Percentage) {
      Percentage m = (Percentage) obj;
      if (m.value == null) {
        return false;
      }

      return compareTo(m) == 0;
    }
    else {
      return false;
    }
  }

}