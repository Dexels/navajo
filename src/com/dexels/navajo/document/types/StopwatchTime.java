package com.dexels.navajo.document.types;

import java.util.Date;
import java.util.StringTokenizer;
import com.dexels.navajo.document.*;
import java.util.*;

/**
 * <p>Title: ClockTime class </p>
 * <p>Description: Datatype class to represent 24 hour clock times (hours, minutes and seconds) </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class StopwatchTime
    extends NavajoType
    implements Comparable {

  long myMillis = 0;

  private static int HOURS_MILLIS = 3600000;
  private static int MINUTE_MILLIS = 60000;
  private static int SECOND_MILLIS = 1000;
  private String myFormat = "HH:mm:SS:MMM";

  /**
   * Constructor that construct StopwatchTime from millis.
   *
   * @param i
   */
  public StopwatchTime(int i) {
    super(Property.STOPWATCHTIME_PROPERTY);
    myMillis = i;
  }

  public StopwatchTime(long i) {
    super(Property.STOPWATCHTIME_PROPERTY);
    myMillis = i;
  }

  /**
   * Create a new ClockTime object from a given String
   * @param value String
   */
  public StopwatchTime(String value) {
    this(value, null);
  }

  public Calendar calendarValue(){
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(myMillis);
    return c;
  }

  /**
   * Create a new ClockTime object from a given String and with a given subtype
   * @param s String
   * @param subtype String
   */
  public StopwatchTime(String s, String subtype) {
    super(Property.STOPWATCHTIME_PROPERTY, subtype);
    constructCalValue(s);
    setupSubtypes();
  }

  private void setupSubtypes() {
    String format = getSubType("format");
    if (format != null) {
      myFormat = format;

    }
  }


  /**
   * Format 1. HH:mm:SS[:MMM]
   *        2. SS[:MMM]
   *        3. mm:SS[:MMM]
   *
   * @param s
   */
  private void constructCalValue(String s) {

    Date value = null;
    if (s == null) {
      return;
    }
    try {

      if (s.indexOf(",") > 0) {
        int pre = Integer.parseInt(s.substring(0, s.indexOf(",")));
        int post = Integer.parseInt(s.substring(s.indexOf(",") + 1));
        int m_post = 0;
        if (post < 10) {
          m_post = 100 * post;
        }
        if (post > 9 && post < 100) {
          m_post = 10 * post;
        }

        myMillis = 1000 * pre + m_post;
        return;
      }

      StringTokenizer tokens = new StringTokenizer(s, ":");
      if (tokens.countTokens() == 1) { // Format 2a
        int h = Integer.parseInt(s);
        myMillis = SECOND_MILLIS * h;
        return;
      }
      else if (tokens.countTokens() == 2) { // Format 2b, 3a
        int t1 = Integer.parseInt(tokens.nextToken());
        String s2 = tokens.nextToken();
        int t2 = Integer.parseInt(s2);
        if (s2.length() == 3) { // Second and millis
          myMillis = t1 * SECOND_MILLIS + t2;
        }
        else { // Minute and seconds
          myMillis = t1 * MINUTE_MILLIS + t2 * SECOND_MILLIS;
        }
      }
      else if (tokens.countTokens() == 3) { // Format 3b, 1a
        int t1 = Integer.parseInt(tokens.nextToken());
        int t2 = Integer.parseInt(tokens.nextToken());
        String s3 = tokens.nextToken();
        int t3 = Integer.parseInt(s3);
        if (s3.length() == 3) {
          myMillis = t1 * MINUTE_MILLIS + t2 * SECOND_MILLIS + t3;
        }
        else {
          myMillis = t1 * HOURS_MILLIS + t2 * MINUTE_MILLIS +
              t3 * SECOND_MILLIS;
        }
      }
      else if (tokens.countTokens() == 4) { // Format 1b
        int t1 = Integer.parseInt(tokens.nextToken());
        int t2 = Integer.parseInt(tokens.nextToken());
        int t3 = Integer.parseInt(tokens.nextToken());
        int t4 = Integer.parseInt(tokens.nextToken());
        myMillis = t1 * HOURS_MILLIS + t2 * MINUTE_MILLIS + t3 * SECOND_MILLIS +
            t4;
      }

    }
    catch (Throwable e1) {
      e1.printStackTrace();
    }
  }

  /**
   * Get stopwatchtime in millis.
   * @return
   */
  public long getMillis() {
    return myMillis;
  }

  /**
   * Clone this Clocktime object
   * @return Object
   */
  public final Object clone() {
    return new StopwatchTime(myMillis);
  }

  /**
   * Get the String representation of this StopwatchTime object
   * @return String
   */
  public final String toString() {
    long millis = myMillis;
    long hours = millis / HOURS_MILLIS;
    millis = millis % HOURS_MILLIS;
    long minutes = millis / MINUTE_MILLIS;
    millis = millis % MINUTE_MILLIS;
    long seconds = millis / SECOND_MILLIS;
    millis = millis % SECOND_MILLIS;

    String sep = "";
    if(myFormat.indexOf(":") > 0){
      sep = ":";
    }else if(myFormat.indexOf(".") > 0){
      sep = ",";
    }

    String result = "";

    String delim = sep;
    if(",".equals(sep)){
      delim = ".";
    }
    StringTokenizer tok = new StringTokenizer(myFormat, delim);

    System.err.println("tokens: " + tok.countTokens());

    if(",".equals(sep) && tok.countTokens() > 2){
      System.err.println("WARNING: Invalid format subtype!");
    }
    boolean firstToken = true;
    while(tok.hasMoreTokens()){
      String token = tok.nextToken();

      //hours
      if("HH".equals(token)){
        if (hours == 0 && ":".equals(sep)) {
          result += "00"+sep;
        }
        else if (hours < 10 && ":".equals(sep)) {
          result += "0" + hours + sep;
        }
        else if (hours < 10) {
          result += hours + sep;
        }

        else {
          result += hours + sep;
        }
      }

      // minutes
      if("mm".equals(token)){
        if(firstToken){
          minutes = hours*60 + minutes;
        }

        if (minutes == 0 && ":".equals(sep)) {
          result += "00"+sep;
        }
        else if (minutes < 10 && ":".equals(sep)) {
          result += "0" + minutes + sep;
        }
        else if (minutes < 10) {
          result += minutes + sep;
        }

        else {
          result += minutes + sep;
        }
      }

      // seconds
      if("SS".equals(token)){
        if(firstToken){
          seconds = hours*3600 + minutes*60 + seconds;
        }
        if (seconds == 0 && ":".equals(sep)) {
          result += "0"+sep;
        }
        else if (seconds < 10 && ":".equals(sep)) {
          result += "00"+seconds + sep;
        }
        else if (seconds < 10) {
          result += seconds + sep;
        }
        else {
          result += seconds + sep;
        }
      }

      // millis 1
      if("M".equals(token)){
        if (millis == 0) {
          result += "0";
        }
        else if (millis < 10) {
          result += millis;
        }
        else if (millis < 100) {
          result += (int) millis/10;
        }
        else {
          result += (int)millis/100;
        }
      }

      // millis 2
      if("MM".equals(token)){
        if (millis == 0) {
          result += "00";
        }
        else if (millis < 10) {
          result += "0" + millis;
        }
        else if (millis < 100) {
          result += millis;
        }
        else {
          result += (int)millis/10;
        }
      }

      // millis 3
      if("MMM".equals(token)){
        if (millis == 0) {
          result += "000";
        }
        else if (millis < 10) {
          result += "00" + millis;
        }
        else if (millis < 100) {
          result += "0" + millis;
        }
        else {
          result += millis;
        }
      }
      firstToken = false;
    }
    if(result.endsWith(",") || result.endsWith(":")){
      result = result.substring(0,result.length()-1);
    }

//    Previous method before FORMAT was introduced
//    if (hours == 0) {
//      result += "00:";
//    }
//    else if (hours < 10) {
//      result += "0" + hours + ":";
//    }
//    else {
//      result += hours + ":";
//    }
//
//    if (minutes == 0) {
//      result += "00:";
//    }
//    else if (minutes < 10) {
//      result += "0" + minutes + ":";
//    }
//    else {
//      result += minutes + ":";
//    }
//
//    if (seconds == 0) {
//      result += "00:";
//    }
//    else if (seconds < 10) {
//      result += "0" + seconds + ":";
//    }
//    else {
//      result += seconds + ":";
//    }
//
//    if (millis == 0) {
//      result += "000";
//    }
//    else if (millis < 10) {
//      result += "00" + millis;
//    }
//    else if (millis < 100) {
//      result += "0" + millis;
//    }
//    else {
//      result += millis;
//    }
//
    return result;
  }

  public final StopwatchTime subtract(StopwatchTime other) {
    StopwatchTime result = null;

    long myMillis = getMillis();
    long otherMillis = other.getMillis();

    return new StopwatchTime(Math.abs(myMillis - otherMillis));
  }

  public final int compareTo(Object o) {

    if (! (o instanceof StopwatchTime)) {
      return 0;
    }

    StopwatchTime other = (StopwatchTime) o;

    return ( (int) (this.getMillis() - other.getMillis()));
  }

  public boolean equals(Object obj) {

    StopwatchTime other = (StopwatchTime) obj;

    return (other.getMillis() == this.getMillis());
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
    StopwatchTime ck5 = new StopwatchTime("00:67:345", "format=SS.MM");
    System.err.println("ck = " + ck5);
    StopwatchTime ck6 = new StopwatchTime("02:01:10");
    System.err.println("ck = " + ck6);
    StopwatchTime ck7 = new StopwatchTime(1100);
    System.err.println("ck7 = " + ck7);

    StopwatchTime sw1 = new StopwatchTime("12:30:00:000");
    StopwatchTime sw2 = new StopwatchTime("04:45:12:976");

    StopwatchTime result = sw1.subtract(sw2);

    System.err.println("result = " + result);
  }

public boolean isEmpty() {
    
    return myMillis!=0;
}

}
