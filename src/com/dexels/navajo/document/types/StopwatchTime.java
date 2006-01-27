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

public final class StopwatchTime extends NavajoType implements Comparable {

  //Set the fixed year constants.
  public static int FIXED_YEAR = 1971;
  public static int FIXED_MONTH = 0;
  public static int FIXED_DAY = 1;

  //private Date value;
  private Calendar calValue;
  private static DateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");

  private final void normalize() {
    calValue.set(Calendar.YEAR, FIXED_YEAR);
    calValue.set(Calendar.MONTH, FIXED_MONTH);
    calValue.set(Calendar.DATE, FIXED_DAY);
  }


  /**
   * Create a new ClockTime object from a given Timestamp
   * @param d Timestamp
   */
  public StopwatchTime(Timestamp d) {
    super(Property.STOPWATCHTIME_PROPERTY);
    calValue = Calendar.getInstance();
    calValue.setTimeInMillis(d.getTime());
    normalize();
  }


  /**
   * Create new ClockTime object from a given Date
   * @param d Date
   */
  public StopwatchTime(Date d) {
    super(Property.STOPWATCHTIME_PROPERTY);
    calValue = Calendar.getInstance();
    calValue.setTimeInMillis(d.getTime());
    normalize();
  }


  /**
   * Create a new ClockTime from a given Calendar
   * @param d Calendar
   */
  public StopwatchTime(Calendar d) {
    super(Property.STOPWATCHTIME_PROPERTY);
    calValue = Calendar.getInstance();
    calValue.setTimeInMillis(d.getTimeInMillis());
    normalize();
  }


  /**
   * Create a new ClockTime object from a given String
   * @param value String
   */
  public StopwatchTime(String value) {
    this(value, null);
  }


  /**
   * Create a new ClockTime object from a given String and with a given subtype
   * @param s String
   * @param subtype String
   */
  public StopwatchTime(String s, String subtype) {
    super(Property.STOPWATCHTIME_PROPERTY, subtype);

    Date value = null;
    if(s == null) {
      return;
    }
    try {
      StringTokenizer tokens = new StringTokenizer(s, ":");
      System.err.println("Tokens: " + tokens.countTokens());
      if(tokens.countTokens() == 1) {
        int h = Integer.parseInt(s);
        if(h < 60) {
          s = "00:00:" + s + ":000";
        } else {
          calValue = null;
          return;
        }
      } else if(tokens.countTokens() == 2) {
        int t1 = Integer.parseInt(tokens.nextToken());
        String s2 = tokens.nextToken();
        int t2 = Integer.parseInt(s2);
        if(t2 > 59 || s2.length() == 3) {
          if(t2 < 10){
            s = "00:" + t1 + ":" + t2 + ":00" + t2;
          } else if(t2 < 100) {
            s = "00:00:" + t1 + ":0" + t2;
          } else {
            s = "00:00:" + t1 + ":" + t2;
          }
        } else {
          s = "00:" + t1 + ":" + t2 + ":000";
        }
      } else if(tokens.countTokens() == 3) {
        int t1 = Integer.parseInt(tokens.nextToken());
        int t2 = Integer.parseInt(tokens.nextToken());
        String s3 = tokens.nextToken();
        int t3 = Integer.parseInt(s3);
        if(t3 > 59 || s3.length() == 3) {
          if(t3 < 10){
            s = "00:" + t1 + ":" + t2 + ":00" + t3;
          }else if(t3 < 100) {
            s = "00:" + t1 + ":" + t2 + ":0" + t3;
          } else {
            s = "00:" + t1 + ":" + t2 + ":" + t3;
          }
        } else {
          s = t1 + ":" + t2 + ":" + t3 + ":000";
        }
      }
      try {
        value = df.parse(s);
        calValue = Calendar.getInstance();
        calValue.setTime(value);
        normalize();
      } catch(Exception e) {
        e.printStackTrace();
        //throw new Exception(e);
        calValue = null;
      }
    } catch(Throwable e1) {
      e1.printStackTrace();
      calValue = null;
    }
  }


  /**
   * Clone this Clocktime object
   * @return Object
   */
  public final Object clone() {
    return new StopwatchTime(dateValue());
  }


  /**
   * Get the value of this ClockTime object as a Date
   * @return Date
   */
  public final Date dateValue() {
    if(calValue != null) {
      return calValue.getTime();
    } else {
      return null;
    }
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
    if(calValue != null) {
      return df.format(calValue.getTime());
    } else {
      return null;
    }
  }

  public static void main(String[] args) throws Exception {

    // Tests.

//    StopwatchTime ck = new StopwatchTime("00:01:44:400");
//    System.err.println("ck = " + ck);
    StopwatchTime ck2 = new StopwatchTime("01");
    System.err.println("ck = " + ck2);
    StopwatchTime ck3 = new StopwatchTime("01:999");
    System.err.println("ck = " + ck3);
    StopwatchTime ck4 = new StopwatchTime("01:100");
    System.err.println("ck = " + ck4);
    StopwatchTime ck5 = new StopwatchTime("02:01:100");
    System.err.println("ck = " + ck5);
    StopwatchTime ck6 = new StopwatchTime("02:01:10");
    System.err.println("ck = " + ck6);
  }

  public final int compareTo(Object o) {

    if(!(o instanceof StopwatchTime)) {
      return 0;
    }

    if(((StopwatchTime)o).calValue == null && calValue == null) {
      return 0;
    }

    if(((StopwatchTime)o).calValue != null && calValue == null) {
      return -1;
    }

    if(((StopwatchTime)o).calValue == null && calValue != null) {
      return 1;
    }

    return(int)(calValue.getTimeInMillis() - ((StopwatchTime)o).dateValue().getTime());
  }

  public boolean equals(Object obj) {

    if(calValue == null && obj == null) {
      return true;
    }

    if(calValue == null || obj == null) {
      return false;
    }

    if(obj instanceof StopwatchTime) {
      StopwatchTime m = (StopwatchTime)obj;
      if(m.calValue == null) {
        return false;
      }
      return compareTo(m) == 0;
    } else {
      return false;
    }
  }

}
