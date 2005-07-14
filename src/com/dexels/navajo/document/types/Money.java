package com.dexels.navajo.document.types;

import java.text.DecimalFormat;
import java.util.Locale;
import com.dexels.navajo.document.*;

/**
 * <p>Title: Money objects</p>
 * <p>Description: A class for representing money typed objects</p>
 * <p>Copyright: Copyright (c) 2003-2004</p>
 * <p>Company: Dexels BV</p>
 * @author $Author$
 * @version $Id$
 */

public final class Money
    extends NavajoType
    implements Comparable {

  private Double value = null;
  private static DecimalFormat nf = new DecimalFormat("\u00A4 #,##0.00;\u00A4 -#,##0.00");
  private static DecimalFormat nf_euro = new DecimalFormat("\u20AC #,##0.00;\u20AC -#,##0.00"); //in case of the NOKBUG
  private static DecimalFormat number = new DecimalFormat("0.00");

  private DecimalFormat customFormat = null;

  static {
//    nf.setNegativePrefix("- \u00A4");
//    nf.setNegativeSuffix("\u00A4");
  }

  /**
   * Create a new Money object from a given Double
   * @param d Double
   */
  public Money(Double d) {
    super(Property.MONEY_PROPERTY);
    setupSubtypes();
    value = d;
  }

  /**
   * Create a new Money object from a given Double and with a given subtype
   * @param d Double
   * @param subtype String
   */
  public Money(Double d, String subtype) {
    super(Property.MONEY_PROPERTY, subtype);
    setupSubtypes();
    value = d;
  }

  /**
   * Create a new Money object
   */
  public Money() {
    super(Property.MONEY_PROPERTY);
    setupSubtypes();
    value = null;
  }

  /**
   * Create a new Money object from an arbitrary Object
   * @param o Object
   */
  public Money(Object o) {
    super(Property.MONEY_PROPERTY);
    setupSubtypes();
    if (o instanceof Money) {
      value = ( (Money) o).value;
    }
    else if (o instanceof Double) {
      value = (Double) o;
    }
    else if (o instanceof Integer) {
      value = new Double( ( (Integer) o).intValue());
    }
    else if (o instanceof String && ! ( (String) o).trim().equals("")) {
      value = new Double(o + "");
    }
    else {
      value = null;
    }
  }

  /**
   * Create a new MOney object from an Integer
   * @param d Integer
   */
  public Money(Integer d) {
    super(Property.MONEY_PROPERTY);
    setupSubtypes();
    if (d != null) {
      value = new Double(d.intValue());
    }
  }

  /**
   * Create a new Money object from an int
   * @param d int
   */
  public Money(int d) {
    super(Property.MONEY_PROPERTY);
    setupSubtypes();
    value = new Double(d);
  }

  /**
   * Create a new Money object from a double
   * @param d double
   */
  public Money(double d) {
    super(Property.MONEY_PROPERTY);
    setupSubtypes();
    value = new Double(d);
  }

  /**
   * Create a new Money object from a double and with a given subtype
   * @param d double
   * @param subtype String
   */
  public Money(double d, String subtype) {
    super(Property.MONEY_PROPERTY, subtype);
    setupSubtypes();
    value = new Double(d);
  }

  /**
   * Create e new Money object from a String
   * @param d String
   */
  public Money(String d) {
    super(Property.MONEY_PROPERTY);
    setupSubtypes();
    if (d.indexOf(",") != -1) {
    	d = d.replaceAll("\\.", "");
    	d = d.replace(',', '.');
    }
    try {
      if (d != null && !d.trim().equals("")) {
        value = new Double(d);
      }
    }
    catch (Throwable t) {
      value = null;
    }
  }

  private void setupSubtypes() {
    String format = getSubType("format");
    if (format != null) {
      customFormat = new DecimalFormat(format);
    }
  }

  /**
   * Return formatted representation of this Money object (incl. the currency character)
   * @return String
   */
  public String formattedString() {
    if (value == null) {
      return "-";
    }
    if (customFormat != null) {
      System.err.println("FOrmatting money with customformat: " +
                         customFormat.toPattern());
      return customFormat.format(value);

    }
    return nf.format(value);
  }

  /**
   * Get the String representation of this Money object
   * @return String
   */
  public String toString() {
    if (value == null) {
      return "";
    }
    else {
      return number.format(value).replace(',', '.');
    }
  }

  /**
   * Get this Money object's value as a double
   * @return double
   */
  public final double doubleValue() {
    if (value == null) {
      return 0;
    }
    return value.doubleValue();
  }

  public final int compareTo(Object o) {
    if (! (o instanceof Money)) {
      return 0;
    }
    Money other = (Money) o;
    if (other.doubleValue() == this.doubleValue()) {
      return 0;
    }
    if (this.doubleValue() < other.doubleValue()) {
      return 1;
    }
    return -1;
  }

  public static void main(String[] args) {
     String aap = "10.000,50";
     Money m = new Money(aap);
     System.err.println("m = " + m.formattedString());
  }

  public boolean equals(Object obj) {

    if (value == null && obj == null) {
      return true;
    }

    if (value == null || obj == null) {
      return false;
    }

    if (obj instanceof Money) {
      Money m = (Money) obj;
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
