package com.dexels.navajo.parser;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.util.*;


public final class DatePattern {

    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public int second;
    public boolean isOffset;

    public DatePattern(int year, int month, int day, int hour, int minute, int second, boolean isOffset) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.second = second;
        this.minute = minute;
        this.isOffset = isOffset;
    }

    public DatePattern(DatePattern prev) {
        this.year = prev.year;
        this.month = prev.month;
        this.day = prev.day;
        this.hour = prev.hour;
        this.minute = prev.minute;
        this.second = prev.second;
        this.isOffset = prev.isOffset;
    }

    public final static DatePattern parseDatePattern(Date datum) {

        // System.out.println("in parseDatePattern(Date)");
        Calendar cal = Calendar.getInstance();

        cal.setTime(datum);
        int yearT = cal.get(Calendar.YEAR);
        int monthT = cal.get(Calendar.MONTH);
        int dayT = cal.get(Calendar.DAY_OF_MONTH);
        int hourT = cal.get(Calendar.HOUR_OF_DAY);
        int minT = cal.get(Calendar.MINUTE);
        int secT = cal.get(Calendar.SECOND);


        // System.out.println("leaving");
        return new DatePattern(yearT, monthT, dayT, hourT, minT, secT, false);
    }

    public final static DatePattern parseDatePattern(String value) throws
        NumberFormatException {

      // System.out.println("in parseDatePattern(String): " + value);
      StringTokenizer dString = new StringTokenizer(value, "#");
      String sYear = dString.nextToken();
      String sMonth = dString.nextToken();
      String sDay = dString.nextToken();
      String sHour = dString.nextToken();
      String sMinute = dString.nextToken();
      String sSecond = dString.nextToken();

      if ( (sYear == null) || (sMonth == null) || (sDay == null) || (sHour == null) ||
          (sMinute == null) || (sSecond == null))
        throw new NumberFormatException("Invalid date pattern specified: " +
                                        value);
      // System.out.println("Found date pattern: year: " + sYear + ", month: " + sMonth + ", day: " +sDay);

      try {
        int yearT = Integer.parseInt(sYear);
        int monthT = Integer.parseInt(sMonth);
        int dayT = Integer.parseInt(sDay);
        int hourT = Integer.parseInt(sHour);
        int minT = Integer.parseInt(sMinute);
        int secT = Integer.parseInt(sSecond);

        return new DatePattern(yearT, monthT, dayT, hourT, minT, secT, true);
      }
      catch (NumberFormatException nfe) {
        throw new NumberFormatException("Invalid date pattern specified: " +
                                        value);
      }
    }

    public final Date getDate() {
        if (!isOffset) {
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.YEAR, this.year);
            cal.set(Calendar.MONTH, this.month);
            cal.set(Calendar.DAY_OF_MONTH, this.day);
            cal.set(Calendar.HOUR_OF_DAY, this.hour);
            cal.set(Calendar.MINUTE, this.minute);
            cal.set(Calendar.SECOND, this.second);
            return cal.getTime();
        } else {
            return null;
        }
    }

    /**
     * Add the date pattern to the specified date and return the result date.
     */
    public final void add(DatePattern another) {

      // System.out.println("in DatePattern.add()");
      // System.out.println("another.isOffset: " + another.isOffset);
      // System.out.println("this.isOffset: " + this.isOffset);
      if (!another.isOffset) { // We have a real date.

        Calendar cal = Calendar.getInstance();

        //System.out.println("another: year=" + another.year + ", month=" + another.month + ", day=" + another.day + ", hour=" + another.hour);
        cal.set(Calendar.YEAR, another.year);
        cal.set(Calendar.MONDAY, another.month);
        cal.set(Calendar.DATE, another.day);
        cal.set(Calendar.HOUR_OF_DAY, another.hour);
        cal.set(Calendar.MINUTE, another.minute);
        cal.set(Calendar.SECOND, another.second);

        cal.add(Calendar.YEAR, this.year);
        cal.add(Calendar.MONTH, this.month);
        cal.add(Calendar.DAY_OF_MONTH, this.day);
        cal.add(Calendar.HOUR_OF_DAY, this.hour);
        cal.add(Calendar.MINUTE, this.minute);
        cal.add(Calendar.SECOND, this.second);

        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.second = cal.get(Calendar.SECOND);

        this.isOffset = false;
      }
      else if (!this.isOffset) { // We have a real date.
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, this.year);
        cal.set(Calendar.MONDAY, this.month);
        cal.set(Calendar.DATE, this.day);
        cal.set(Calendar.HOUR_OF_DAY, another.hour);
        cal.set(Calendar.MINUTE, another.minute);
        cal.set(Calendar.SECOND, another.second);

        cal.add(Calendar.YEAR, another.year);
        cal.add(Calendar.MONTH, another.month);
        cal.add(Calendar.DAY_OF_MONTH, another.day);
        cal.add(Calendar.HOUR_OF_DAY, this.hour);
        cal.add(Calendar.MINUTE, this.minute);
        cal.add(Calendar.SECOND, this.second);

        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.second = cal.get(Calendar.SECOND);

      }
      else { // We have two offsets.

        this.year += another.year;
        this.month += another.month;
        this.day += another.day;
        this.hour += another.hour;
        this.minute += another.minute;
        this.second += another.second;
      }
    }

    /**
         * Subtract the date pattern to the specified date and return the result date.
     */
    public final void subtract(DatePattern another) {

      if (!another.isOffset) { // We have a real date.
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, another.year);
        cal.set(Calendar.MONDAY, another.month);
        cal.set(Calendar.DATE, another.day);
        cal.set(Calendar.HOUR_OF_DAY, another.hour);
        cal.set(Calendar.MINUTE, another.minute);
        cal.set(Calendar.SECOND, another.second);

        cal.add(Calendar.YEAR, this.year);
        cal.add(Calendar.MONTH, this.month);
        cal.add(Calendar.DAY_OF_MONTH, this.day);
        cal.add(Calendar.HOUR_OF_DAY, this.hour);
        cal.add(Calendar.MINUTE, this.minute);
        cal.add(Calendar.SECOND, this.second);

        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.second = cal.get(Calendar.SECOND);

        this.isOffset = false;

      }
      else if (!this.isOffset) { // We have a real date.
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, -this.year);
        cal.set(Calendar.MONDAY, -this.month);
        cal.set(Calendar.DATE, -this.day);
        cal.set(Calendar.HOUR_OF_DAY, -this.hour);
        cal.set(Calendar.MINUTE, -this.minute);
        cal.set(Calendar.SECOND, -this.second);

        cal.add(Calendar.YEAR, -another.year);
        cal.add(Calendar.MONTH, -another.month);
        cal.add(Calendar.DAY_OF_MONTH, -another.day);
        cal.add(Calendar.HOUR_OF_DAY, -another.hour);
        cal.add(Calendar.MINUTE, -another.minute);
        cal.add(Calendar.SECOND, -another.second);

        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.second = cal.get(Calendar.SECOND);

      }
      else { // We have two offsets.

        this.year += another.year;
        this.month += another.month;
        this.day += another.day;
        this.hour += another.hour;
        this.minute += another.minute;
        this.second += another.second;

      }
    }

    /**
     * Multiply the date pattern to the specified date and return the result date.
     * Mmmm... this won't occur that much probably.
     */
    public void multiply(DatePattern another) {}
}
