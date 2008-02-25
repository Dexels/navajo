package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;

//import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class Day {
  private int myDate = -1;
  private int myWeekOfYear = -1;
  private int myWeekOfMonth = -1;
  private Map myAttributes = new HashMap();
  //private JComponent myInfo = new JLabel();
  private Date myRealDate;
  private HashSet myMessages = new HashSet();

  public Day() {
  }

  public Day(int date) {
    myDate = date;
  }

  public void setDate(int date) {
    myDate = date;
  }

  public void setDate(Date d) {
    myRealDate = d;
  }

  public String getDateString() {
    if (myDate < 0) {
      return "";
    }
    else {
      return String.valueOf(myDate);
    }
  }

  public Date getDate() {
    return myRealDate;
  }

  public void setWeekOfYear(int week) {
    myWeekOfYear = week;
  }

  public int getDayOfMonth(){
    Calendar c = Calendar.getInstance();
    c.setTime(myRealDate);
    return c.get(Calendar.DAY_OF_MONTH);
  }

  public int getWeekOfYear() {
    Calendar myCalendar = Calendar.getInstance();
      myCalendar.setFirstDayOfWeek(Calendar.MONDAY);
      myCalendar.setMinimalDaysInFirstWeek(4);
      myCalendar.setTime(getDate());
      return myCalendar.get(Calendar.WEEK_OF_YEAR);
//
//    return myWeekOfYear;
  }

  public void setWeekOfMonth(int week) {
    myWeekOfMonth = week;
  }

  public int getWeekOfMonth() {
    return myWeekOfMonth;
  }

  public void setAttributes(Map attr) {
    myAttributes = attr;
  }

  public void addAttribute(String key, Object value) {
    //System.err.println("Adding attribute: " + key + " : " + value.toString());
    myAttributes.put(key, value);
  }

  public void addMessage(Message m) {
    myMessages.add(m);
  }

  public HashSet getMessages() {
    return myMessages;
  }

  public Object getAttribute(String key) {
    if (myAttributes != null) {
      return myAttributes.get(key);
    }
    else {
      return null;
    }
  }

  public void clearMessages() {
    myMessages.clear();
  }

  public Map getAttributes() {
    return myAttributes;
  }

}
