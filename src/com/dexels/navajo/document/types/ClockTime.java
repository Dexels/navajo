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

public class ClockTime implements Comparable {

  private Date value;
  private static DateFormat df = SimpleDateFormat.getTimeInstance(2, Locale.GERMAN);

  public ClockTime(Date d) {
    value = d;
  }

  public ClockTime(String s) throws Exception {
    StringTokenizer tokens = new StringTokenizer(s, ":");
    if (tokens.countTokens() == 1) {
      if (s.startsWith("00")) {
        s = s.substring(2);
        if (Integer.parseInt(s) >= 60)
          throw new Exception("Invalid clocktime: " + s);
        value = df.parse("00:"+s+":00");
        return;
      }
      if (s.startsWith("0"))
        s = s.substring(1);
      int h = Integer.parseInt(s);
      if (h >= 0 && h <= 9)
        s = "0"+s+":00:00";
      else if (h < 24)
        s = s+":00:00";
      else if (h >= 100 && h <= 959) {
        int hh = Integer.parseInt( s.substring(0, 1) );
        int mm = Integer.parseInt( s.substring(1, 3) );
        if (mm >= 60)
          throw new Exception("Invalid clocktime: " + s);
        s = "0" + hh + ":" + mm + ":00";
      } else if (h >= 1000 && h <= 2359) {
        int hh = Integer.parseInt( s.substring(0, 2) );
        int mm = Integer.parseInt( s.substring(2, 4) );
        if (hh >= 24)
          throw new Exception("Invalid clocktime: " + s);
        if (mm >= 60)
          throw new Exception("Invalid clocktime: " + s);
        s = hh + ":" + mm + ":00";
      } else {
        throw new Exception("Invalid clocktime: " + s);
      }
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
    ClockTime ck = new ClockTime("945");
    System.out.println("ck = " + ck);
    ClockTime ck2 = new ClockTime("0055");
    System.out.println("ck2 = " + ck2);
    System.out.println("date ck = " + ck.dateValue().getTime());
    System.out.println("date ck2 = " + ck2.dateValue().getTime());
    System.out.println("ck2 > ck? -> " + ck2.dateValue().after(ck.dateValue()));

  }

  public int compareTo(Object o) {
    if (!(o instanceof ClockTime))
      return 0;

    return (int) (value.getTime() - ((ClockTime) o).dateValue().getTime());
  }
}