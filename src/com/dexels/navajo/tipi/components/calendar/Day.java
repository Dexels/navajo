package com.dexels.navajo.tipi.components.calendar;

import java.util.*;
import javax.swing.*;

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
  private Map myAttributes;
  private JComponent myInfo = new JLabel();
  private Date myRealDate;

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

  public int getWeekOfYear() {
    return myWeekOfYear;
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

  public Object getAttribute(String key) {
    if (myAttributes != null) {
      return myAttributes.get(key);
    }
    else {
      return null;
    }
  }

  public Map getAttributes(){
    return myAttributes;
  }

}
