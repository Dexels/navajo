package com.dexels.navajo.document.types;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * <p>Title: ClockTime class </p>
 * <p>Description: Datatype class to represent 24 hour clock times (hours, minutes and seconds) </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ClockTime {

  private Date value;
  private static DateFormat df = SimpleDateFormat.getTimeInstance(2, Locale.GERMAN);

  public ClockTime(Date d) {
    value = d;
  }

  public ClockTime(String s) throws Exception {
    StringTokenizer tokens = new StringTokenizer(s, ":");
    if (tokens.countTokens() == 1) {
      int h = Integer.parseInt(s);
      if (h >= 0 && h <= 9)
        s = "0"+s+":00:00";
      else
        s = s+":00:00";
    } else
    if (tokens.countTokens() == 2) {
      s += ":00";
    }
    try {
      value = df.parse(s);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  public Date dateValue() {
    return value;
  }

  public String toString() {
    return df.format(value);
  }

  public static void main(String [] args) throws Exception {

    // Tests.

    System.out.println("value = " + new ClockTime(new java.util.Date()).toString());
    ClockTime ck = new ClockTime("9");
    System.out.println("ck = " + ck);
    ClockTime ck2 = new ClockTime("10");
    System.out.println("ck2 = " + ck2);
    System.out.println("date ck = " + ck.dateValue().getTime());
    System.out.println("date ck2 = " + ck2.dateValue().getTime());
    System.out.println("ck2 > ck? -> " + ck2.dateValue().after(ck.dateValue()));

  }
}