package com.dexels.navajo.tipi.components.calendar;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class MultipleDayContainer {
  private Day[] days;

  public MultipleDayContainer() {
  }

  public MultipleDayContainer(Day[] days) {
    this.days = days;
  }

  public void setDays(Day[] days){
    this.days = days;
  }

  public Day[] getDays(){
    return days;
  }

}
