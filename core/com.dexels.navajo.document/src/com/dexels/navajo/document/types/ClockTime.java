/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.types;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;

/**
 * <p>Title: ClockTime class </p>
 * <p>Description: Datatype class to represent 24 hour clock times (hours, minutes and seconds) </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class ClockTime extends NavajoType implements Comparable<ClockTime> {

  /**
	 * 
	 */
	private static final long serialVersionUID = -1867359996556685730L;
    private static final Logger logger = LoggerFactory.getLogger(ClockTime.class);

	public static final String VERSION = "$Id$";

	//Set the fixed year constants.
	public static final int FIXED_YEAR = 1971;
	public static final int FIXED_MONTH = 0;
	public static final int FIXED_DAY = 1;
	//private Date value;
	private Calendar calValue;

	private boolean shortFormat = false;
    
	private static final ThreadLocal<DateFormat> dfMedium = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue()
		{
			return SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.GERMAN);
		}
	}; 
	private static final ThreadLocal<DateFormat> dfShort = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue()
		{
			return SimpleDateFormat.getTimeInstance(DateFormat.SHORT, Locale.GERMAN);
		}
	}; 

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
            value = dfMedium.get().parse("00:" + s + ":00");
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
        if (s.length() > 0 && s.charAt(0) == '0')
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
        value = dfMedium.get().parse(s);
        calValue = Calendar.getInstance();
        calValue.setTime(value);
        normalize();
        if (getSubType("showseconds") != null && getSubType("showseconds").equalsIgnoreCase("false")) {
        	setShortFormat(true);
        }
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
  @Override
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
  
  public void setShortFormat(boolean b) {
	  shortFormat = b;
  }

  /**
   * Get the String representation of this ClockTime object
   * @return String
   */
  @Override
public final String toString() {
	  if(shortFormat) {
		  return toShortString();
	  }
    if (calValue != null) {
      return dfMedium.get().format(calValue.getTime());
    }
    else {
      return "";
    }
  }
  public final String toShortString() {
    if (calValue != null) {
    	return dfShort.get().format(calValue.getTime());
    } else {
    	return null;
    }
  }
  
  

  @Override
public final int compareTo(ClockTime o) {

    if (o == null) {
      return 0;
    }

    if (o.calValue == null && calValue == null) {
      return 0;
    }

    if (o.calValue != null && calValue == null) {
      return -1;
    }

    if (o.calValue == null && calValue != null) {
      return 1;
    }

    return (int) (calValue.getTimeInMillis() - o.dateValue().getTime());
  }

  @Override
public int hashCode() {
	  if ( calValue == null ) {
		  return 434343;
	  }
	  return calValue.toString().hashCode();
  }
  
  @Override
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
  
  public int getHours() {
	  return calValue.get(Calendar.HOUR_OF_DAY);
  }

  public int getMinutes() {
	  return calValue.get(Calendar.MINUTE);
  }

  public int getSeconds() {
	  return calValue.get(Calendar.SECOND);
  }


@Override
public boolean isEmpty() {
    return calValue==null;
  } 
  
  public static void main(String [] args) {
	  long l = System.currentTimeMillis();
	  for ( int i = 0; i < 1000000; i++ ) {
		  DateFormat df = SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.GERMAN);
		  df.format(new java.util.Date());
	  }
	  logger.info("Per call (1): " + ( System.currentTimeMillis() - l ) );
	  
	  l = System.currentTimeMillis();
	  ThreadLocal<DateFormat> a = new ThreadLocal<DateFormat>() {
		  @Override
		  protected DateFormat initialValue()
		  {
			  return SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.GERMAN);
		  } 
	  };
	  for ( int i = 0; i < 1000000; i++ ) {
		  a.get().format(new java.util.Date());
	  }
	  logger.info("Per call (2): " + ( System.currentTimeMillis() - l ) );
	  
  }

}
