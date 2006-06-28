package com.dexels.navajo.document.types;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.sql.Timestamp;
import java.util.Calendar;
import com.dexels.navajo.document.*;

/**
 * <p>Title: ClockTime class </p>
 * <p>Description: Datatype class to represent 24 hour clock times (hours, minutes and seconds) </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class ClockTime extends NavajoType implements Comparable {

  public final static String VERSION = "$Id$";
	
  //Set the fixed year constants.
  public static int FIXED_YEAR = 1971;
  public static int FIXED_MONTH = 0;
  public static int FIXED_DAY = 1;
  //private Date value;
  private Calendar calValue;
  private static DateFormat df = SimpleDateFormat.getTimeInstance(2, Locale.GERMAN);

  private final void normalize() {
    calValue.set(Calendar.MILLISECOND, 0);
    calValue.set(Calendar.SECOND, 0);
    calValue.set(Calendar.YEAR, FIXED_YEAR);
    calValue.set(Calendar.MONTH, FIXED_MONTH);
    calValue.set(Calendar.DATE, FIXED_DAY);
  }

  /**
   * Create a new ClockTime object from a given Timestamp
   * @param d Timestamp
   */
  public ClockTime(Timestamp d) {
    super(Property.CLOCKTIME_PROPERTY);
   calValue = Calendar.getInstance();
   calValue.setTimeInMillis(d.getTime());
   normalize();
  }

  /**
   * Create new ClockTime object from a given Date
   * @param d Date
   */
  public ClockTime(Date d) {
    super(Property.CLOCKTIME_PROPERTY);
    calValue = Calendar.getInstance();
    calValue.setTimeInMillis(d.getTime());
    normalize();
  }

  /**
   * Create a new ClockTime from a given Calendar
   * @param d Calendar
   */
  public ClockTime(Calendar d) {
    super(Property.CLOCKTIME_PROPERTY);
    calValue = Calendar.getInstance();
    calValue.setTimeInMillis(d.getTimeInMillis());
    normalize();
  }

  /**
   * Create a new ClockTime object from a given String
   * @param value String
   */
  public ClockTime(String value) {
    this(value,null);
  }

  /**
   * Create a new ClockTime object from a given String and with a given subtype
   * @param s String
   * @param subtype String
   */
  public ClockTime(String s, String subtype) {
    super(Property.CLOCKTIME_PROPERTY,subtype);


    Date value = null;

    try {
      StringTokenizer tokens = new StringTokenizer(s, ":");
      if (tokens.countTokens() == 1) {
        if (s.startsWith("24")) {
          s = "00" + s.substring(2);
        }
        if (s.startsWith("00")) {
          s = s.substring(2);
          if (s == null || s.equals(""))
            s = "00";
          if (!s.equals("00") && Integer.parseInt(s) >= 60) {
            calValue = null;
            return;
            //throw new Exception("Invalid clocktime: " + s);
          }
          try {
            value = df.parse("00:" + s + ":00");
          }
          catch (Exception e) {
            calValue = null;
            return;
          }
          calValue = Calendar.getInstance();
          calValue.setTime(value);
          normalize();
          return;
        }
        if (s.startsWith("0"))
          s = s.substring(1);
        int h = Integer.parseInt(s);
        if (h >= 0 && h <= 9)
          s = "0" + s + ":00:00";
        else if (h < 24)
          s = s + ":00:00";
        else if (h >= 100 && h <= 959) {
          int hh = Integer.parseInt(s.substring(0, 1));
          int mm = Integer.parseInt(s.substring(1, 3));
          if (mm >= 60) {
            calValue = null;
            return;
            //throw new Exception("Invalid clocktime: " + s);
          }
          s = "0" + hh + ":" + mm + ":00";
        }
        else if (h >= 1000 && h <= 2359) {
          int hh = Integer.parseInt(s.substring(0, 2));
          int mm = Integer.parseInt(s.substring(2, 4));
          if (hh >= 24) {
            calValue = null;
            return;
            //throw new Exception("Invalid clocktime: " + s);
          }
          if (mm >= 60) {
            calValue = null;
            return;
            //throw new Exception("Invalid clocktime: " + s);
          }
          s = hh + ":" + mm + ":00";
        }
        else {
          calValue = null;
          return;
          //throw new Exception("Invalid clocktime: " + s);
        }
      }
      else
      if (tokens.countTokens() == 2) {
        s += ":00";
      }
      try {
        value = df.parse(s);
        calValue = Calendar.getInstance();
        calValue.setTime(value);
        normalize();
      }
      catch (Exception e) {
        //throw new Exception(e);
        calValue = null;
      }
    } catch (Throwable e1) {
      calValue = null;
    }
  }

  /**
   * Clone this Clocktime object
   * @return Object
   */
  public final Object clone() {
    return new ClockTime(dateValue());
  }

  /**
   * Get the value of this ClockTime object as a Date
   * @return Date
   */
  public final Date dateValue() {
    if (calValue != null)
      return calValue.getTime();
    else
      return null;
  }

  /**
   * Get the value of this ClockTime object as a Calendar
   * @return Calendar
   */
  public final Calendar calendarValue() {
    return calValue;
  }

  /**
   * Get the String representation of this ClockTime object
   * @return String
   */
  public final String toString() {
    if (calValue != null)
      return df.format(calValue.getTime());
    else
      return null;
  }

  public static void main(String [] args) throws Exception {

    // Tests.

  ClockTime ck = new ClockTime("12:00:00");
  System.err.println("ck = " + ck);
  }

  public final int compareTo(Object o) {

    if (!(o instanceof ClockTime)) {
      return 0;
    }

    if (((ClockTime) o).calValue == null && calValue == null) {
      return 0;
    }

    if (((ClockTime) o).calValue != null && calValue == null) {
      return -1;
    }

    if (((ClockTime) o).calValue == null && calValue != null) {
      return 1;
    }

    return (int) (calValue.getTimeInMillis() - ((ClockTime) o).dateValue().getTime());
  }

  public boolean equals(Object obj) {

    if (calValue == null && obj == null) {
      return true;
    }

    if (calValue == null || obj == null) {
      return false;
    }

    if (obj instanceof ClockTime) {
      ClockTime m = (ClockTime)obj;
      if (m.calValue == null) {
        return false;
      }
      return compareTo(m)==0;
    } else {
      return false;
    }
  }

public boolean isEmpty() {
    return calValue==null;

}

}
