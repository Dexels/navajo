package com.dexels.navajo.document.types;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * <p>Title: ClockTime class </p>
 * <p>Description: Datatype class to represent 24 hour clock times (hours, minutes and seconds) </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ClockTime implements Comparable {

  /**
   * Set the fixed year constants.
   */
  public static int FIXED_YEAR = 1900;
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

  public ClockTime(Timestamp d) {
   calValue = Calendar.getInstance();
   calValue.setTimeInMillis(d.getTime());
   normalize();
  }

  public ClockTime(Date d) {
    calValue = Calendar.getInstance();
    calValue.setTimeInMillis(d.getTime());
    normalize();
  }

  public ClockTime(Calendar d) {
    calValue = Calendar.getInstance();
    calValue.setTimeInMillis(d.getTimeInMillis());
    normalize();
  }


  public ClockTime(String s) {


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

  public Object clone() {
    return new ClockTime(dateValue());
  }

  public Date dateValue() {
    if (calValue != null)
      return calValue.getTime();
    else
      return null;
  }

  public Calendar calendarValue() {
    return calValue;
  }

  public String toString() {
    if (calValue != null)
      return df.format(calValue.getTime());
    else
      return null;
  }

  public static void main(String [] args) throws Exception {

    // Tests.

    System.out.println("value = " + new ClockTime(new java.util.Date()).toString());
    ClockTime ck = new ClockTime("945");
    System.out.println("ck = " + ck);
    ClockTime ck2 = new ClockTime("2410");
    System.out.println("ck2 = " + ck2);
    System.out.println("ck2 date = " + ck2.dateValue());
    System.out.println("date ck = " + ck.dateValue().getTime());
    System.out.println("date ck2 = " + ck2.dateValue().getTime());
    System.out.println("ck2 > ck? -> " + ck2.dateValue().after(ck.dateValue()));

  }

  public int compareTo(Object o) {
    if (!(o instanceof ClockTime))
      return 0;

    return (int) (calValue.getTimeInMillis() - ((ClockTime) o).dateValue().getTime());
  }
}