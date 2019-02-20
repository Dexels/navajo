package com.dexels.navajo.document.types;

import java.text.NumberFormat;

import com.dexels.navajo.document.Property;

/**
 * <p>Title: Percentage objects</p>
 * <p>Description: A class for representing percentage typed objects</p>
 * <p>Copyright: Copyright (c) 2003-2004</p>
 * <p>Company: Dexels BV</p>
 * @author $author$
 * @version $Id$
 */

public final class Percentage extends NavajoType implements Comparable<Percentage> {

	private static final long serialVersionUID = -4200780883826849390L;
private Double value;
  private static NumberFormat nf = NumberFormat.getPercentInstance();
  static {
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
  }

  /**
   * Create a new Percentage object from a given Double and a given subtype
   * @param d Double
   * @param subtype String
   */
  public Percentage(Double d, String subtype) {
    super(Property.PERCENTAGE_PROPERTY,subtype);
    value = d;
  }

  /**
   * Create a new Percentage object
   */
  public Percentage() {
    super(Property.PERCENTAGE_PROPERTY);
    value = null;
  }

  /**
   * Create a new Percentage object from an arbitrary Object
   * @param o Object
   */
  public Percentage(Object o) {
    super(Property.PERCENTAGE_PROPERTY);
    if (o instanceof Percentage) {
      value = ( (Percentage) o).value;
    }
    else if (o instanceof Double) {
      value = (Double) o;
    }
    else if (o instanceof Integer) {
      value = Double.valueOf( ( (Integer) o).intValue());
    }
    else {
      value = Double.valueOf(o + "");
    }
    if (Double.isNaN(value.doubleValue())) {
      value = null;
    }

  }

  /**
   * Create new Percentage object from a given Integer
   * @param d Integer
   */
  public Percentage(Integer d) {
    super(Property.PERCENTAGE_PROPERTY);
    if (d != null) {
      value = Double.valueOf(d.intValue());
    }
  }

  /**
   * Create a new Percentage object from a given int
   * @param d int
   */
  public Percentage(int d) {
    super(Property.PERCENTAGE_PROPERTY);
    value = Double.valueOf(d);
  }

  /**
   * Create a new Percentage object from a given double
   * @param d double
   */
  public Percentage(double d) {
    super(Property.PERCENTAGE_PROPERTY);
    value = Double.valueOf(d);
  }

  /**
   * Create a new Percentage object from a given double and with a given subtype
   * @param d double
   * @param subtype String
   */
  public Percentage(double d, String subtype) {
    super(Property.PERCENTAGE_PROPERTY,subtype);
    value = Double.valueOf(d);
  }

  /**
   * Create a new PErcentage object from a given String
   * @param d String
   */
  public Percentage(String d) {
    super(Property.PERCENTAGE_PROPERTY);
    try {
      if (d != null) {
        value = Double.valueOf(d);
      }
    }
    catch (Throwable t) {
      value = null;
    }
  }

  /**
   * Get the formatted String representation of this Percentage object (including the % sign)
   * @return String
   */
  public final String formattedString() {
    if (value == null) {
      return "-";
    }
    return nf.format(value);
  }

  /**
   * Get the default String representation of this Percentage object
   * @return String
   */
  @Override
public final String toString() {
    if (value == null) {
      return "";
    }
    return value.doubleValue() + "";
  }

  /**
   * Get the value of this Percentage object as a double
   * @return double
   */
  public final double doubleValue() {
    if (value == null) {
      return 0;
    }
    return value.doubleValue();
  }

  @Override
public final int compareTo(Percentage o) {
    if (o ==null) {
      return 0;
    }
    Percentage other = o;
    if (other.doubleValue() == this.doubleValue()) {
      return 0;
    }
    if (this.doubleValue() < other.doubleValue()) {
      return 1;
    }
    return -1;
  }

  @Override
public int hashCode() {
	if ( value == null ) {
		return 434343;
	}
	return value.hashCode();
  }
  
  @Override
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

@Override
public boolean isEmpty() {
    return value==null;
}

}
