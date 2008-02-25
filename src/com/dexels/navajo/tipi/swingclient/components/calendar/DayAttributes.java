package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class DayAttributes {
  private Date myDate = Calendar.getInstance().getTime();
  private ArrayList myInfo = new ArrayList();

  public DayAttributes() {
  }

  public void setDate(Date d) {
    myDate = d;
  }

  public Date getDate() {
    return myDate;
  }

  public ArrayList getExtraInfo() {
    return myInfo;
  }

  public void addExtraInfo(String info) {
    myInfo.add(info);
  }
}