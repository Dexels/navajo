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

  long myMillis = -1;

  private static int HOURS_MILLIS = 3600000;
  private static int MINUTE_MILLIS = 60000;
  private static int SECOND_MILLIS = 1000;


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


  /**
   * Create a new ClockTime object from a given String and with a given subtype
   * @param s String
   * @param subtype String
   */
  public StopwatchTime(String s, String subtype) {
    super(Property.STOPWATCHTIME_PROPERTY, subtype);
    constructCalValue(s);
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
	    if(s == null) {
	      return;
	    }
	    try {
	      StringTokenizer tokens = new StringTokenizer(s, ":");
	      System.err.println("Tokens: " + tokens.countTokens());
	      if(tokens.countTokens() == 1) { // Format 2a
	    	  int h = Integer.parseInt(s);
	    	  myMillis = SECOND_MILLIS * h;
	    	  return;
	      } else if(tokens.countTokens() == 2) { // Format 2b, 3a
	        int t1 = Integer.parseInt(tokens.nextToken());
	        String s2 = tokens.nextToken();
	        int t2 = Integer.parseInt(s2);
	        if (s2.length() == 3) { // Second and millis
	        	myMillis = t1 * SECOND_MILLIS + t2;
	        } else { // Minute and seconds
	        	myMillis = t1 * MINUTE_MILLIS + t2 * SECOND_MILLIS;
	        }
	      } else if(tokens.countTokens() == 3) { // Format 3b, 1a
	        int t1 = Integer.parseInt(tokens.nextToken());
	        int t2 = Integer.parseInt(tokens.nextToken());
	        String s3 = tokens.nextToken();
	        int t3 = Integer.parseInt(s3);
	        if ( s3.length() == 3 ) {
	        	myMillis = t1 * MINUTE_MILLIS + t2 * SECOND_MILLIS + t3;
	        } else {
	        	myMillis = t1 * HOURS_MILLIS + t2 * MINUTE_MILLIS + t3 * SECOND_MILLIS;
	        }
	      } else if(tokens.countTokens() == 4) { // Format 1b
	    	 int t1 = Integer.parseInt(tokens.nextToken());
		     int t2 = Integer.parseInt(tokens.nextToken());
		     int t3 = Integer.parseInt(tokens.nextToken());
		     int t4 = Integer.parseInt(tokens.nextToken());
		     myMillis = t1 * HOURS_MILLIS + t2 * MINUTE_MILLIS + t3 * SECOND_MILLIS + t4;
	      }

	    } catch(Throwable e1) {
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
	  long hours = millis/HOURS_MILLIS;
	  millis = millis%HOURS_MILLIS;
	  long minutes = millis/MINUTE_MILLIS;
	  millis = millis%MINUTE_MILLIS;
	  long seconds = millis/SECOND_MILLIS;
	  millis = millis%SECOND_MILLIS;

          System.err.println("Hours: "+ hours);
          System.err.println("Minutes: "+ minutes);
          System.err.println("Seconds: "+ seconds);
          System.err.println("Millis: "+ millis);

	  String result = "";
	  if ( hours == 0 ) {
		  result += "00:";
	  }else if ( hours < 10 ) {
		  result += "0"+hours+":";
	  } else {
		  result += hours + ":";
	  }

	  if ( minutes == 0 ) {
		  result += "00:";
	  } else if ( minutes < 10 ) {
		  result += "0"+minutes+":";
	  } else {
		  result += minutes + ":";
	  }

	  if ( seconds == 0 ) {
		  result += "00:";
	  } else if ( seconds < 10 ) {
		  result += "0"+seconds+":";
	  } else {
		  result += seconds + ":";
	  }

	  if ( millis == 0 ) {
		  result += "000";
	  } else if ( millis < 10 ) {
		  result += "00"+millis;
	  } else if ( millis < 100 ) {
		  result += "0"+millis;
	  } else {
		  result += millis;
	  }

	  return result;
  }

  public final StopwatchTime subtract(StopwatchTime other) {
	  StopwatchTime result = null;

	  long myMillis = getMillis();
	  long otherMillis = other.getMillis();

	  return new StopwatchTime( Math.abs(myMillis - otherMillis) );
  }

  public final int compareTo(Object o) {

    if(!(o instanceof StopwatchTime)) {
      return 0;
    }

    StopwatchTime other = (StopwatchTime) o;

    return ( (int) ( this.getMillis() - other.getMillis() ) );
  }

  public boolean equals(Object obj) {

    StopwatchTime other = (StopwatchTime) obj;

    return (other.getMillis() == this.getMillis());
  }

  public static void main(String [] args) throws Exception {

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
    StopwatchTime ck7 = new StopwatchTime(1100);
    System.err.println("ck7 = " + ck7);

	  StopwatchTime sw1 = new StopwatchTime("12:30:00:000");
	  StopwatchTime sw2 = new StopwatchTime("04:45:12:976");


	  StopwatchTime result = sw1.subtract(sw2);

	  System.err.println("result = " + result);
  }

}
