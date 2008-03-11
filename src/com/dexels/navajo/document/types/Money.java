package com.dexels.navajo.document.types;

import java.math.*;
import java.text.*;
import java.util.regex.*;

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
    implements Comparable<Money> {

  private static final int INTERNAL_DECIMAL_PLACES = 4;
private Double value = null;
  private static DecimalFormat nf = new DecimalFormat("\u00A4 #,##0.00;\u00A4 -#,##0.00");
  private static DecimalFormat nf_euro = new DecimalFormat("\u20AC #,##0.00;\u20AC -#,##0.00"); //in case of the NOKBUG
  private static DecimalFormat internalTmlFormat = new DecimalFormat("0.00");
  private static NumberFormat editingFormat = NumberFormat.getInstance();

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
    setValue(d);

  }

private void setValue(Double d) {
	value = d;
    roundValue();
}

  /**
   * Create a new Money object from a given Double and with a given subtype
   * @param d Double
   * @param subtype String
   */
  public Money(Double d, String subtype) {
    super(Property.MONEY_PROPERTY, subtype);
    setupSubtypes();
    setValue(d);
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
      setValue((Double) o);
    }
    else if (o instanceof Integer) {
      setValue(new Double( ( (Integer) o).intValue()));
    }
    else if (o instanceof String && ! ( (String) o).trim().equals("")) {
      Pattern p = Pattern.compile("-?[0-9]+[.,]?[0-9]{0,2}");
      Matcher m = p.matcher((String) o);
      boolean isMoneyFormat = m.find();

      if (isMoneyFormat) {
        setValue(new Double(((String) o).substring(m.start(), m.end()) + ""));
      } else {
        setValue(new Double(o + ""));
      }
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
    	setValue( new Double(d.intValue()));
    }
  
  }

  /**
   * Create a new Money object from an int
   * @param d int
   */
  public Money(int d) {
    super(Property.MONEY_PROPERTY);
    setupSubtypes();
    setValue(new Double(d));
 }

  /**
   * Create a new Money object from a double
   * @param d double
   */
  public Money(double d) {
    super(Property.MONEY_PROPERTY);
    setupSubtypes();
    setValue(d);

  }

  /**
   * Create a new Money object from a double and with a given subtype
   * @param d double
   * @param subtype String
   */
  public Money(double d, String subtype) {
    super(Property.MONEY_PROPERTY, subtype);
    setupSubtypes();
    setValue(d);
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
        setValue(new Double(d));
      }
    }
    catch (Throwable t) {
      value = null;
    }
  }

  private void roundValue(){
	  if(value==null) {
		  return;
	  }
	  value = round(value, INTERNAL_DECIMAL_PLACES);
  }
  
  private double round(double d, int decimalPlace){
	    // see the Javadoc about why we use a String in the constructor
	    // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
	    BigDecimal bd = new BigDecimal(Double.toString(d));
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
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
    String formatted = nf.format(value);
     return formatted;
  }

  public String tmlString() {
	    if (value == null) {
	      return "";
	    }
	
	    return internalTmlFormat.format(value);
	  }

public String editingString() {
	    if (value == null) {
	      return "-";
	    }
	    return editingFormat.format(value);
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
      return internalTmlFormat.format(value).replace(',', '.');
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
    double doubleValue = value.doubleValue();
    return doubleValue;
  }

  public final int compareTo(Money o) {
    if (o==null) {
      return 0;
    }
    Money other = o;
    if (other.doubleValue() == this.doubleValue()) {
      return 0;
    }
    if (this.doubleValue() < other.doubleValue()) {
      return 1;
    }
    return -1;
  }

  public static void main(String[] args) {
     String aap = "-225.00";
     Money m = new Money(aap);
     System.err.println("m = " + m.formattedString());
     System.err.println("sa =" + m.doubleValue());
  } 

  /**
   * TODO WTF?
   */
  public int hashCode() {
	  if (value == null) {
		  return 312321;
	  }
	  return value.hashCode();
  }
  

  
  public boolean equals(Object obj) {

    if (value == null && obj == null) {
      return true;
    }
    if(obj==null) {
    	return false;
    }

    if (obj instanceof Money) {
      Money m = (Money) obj;
      if (m.value == null) {
    	if(value==null) {
    		return true;
    	} else {
            return false;
    	}
      } else {
      	if(value==null) {
    		return false;
    	} else {
    	      return compareTo(m) == 0;
    	}
      }

    }
    else {
      return false;
    }
  }

public boolean isEmpty() {
    return value==null;
}

}
