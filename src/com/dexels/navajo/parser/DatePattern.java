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


public class DatePattern {

    public int year;
    public int month;
    public int day;
    public boolean isOffset;

    public DatePattern(int year, int month, int day, boolean isOffset) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.isOffset = isOffset;
    }

    public DatePattern(DatePattern prev) {
        this.year = prev.year;
        this.month = prev.month;
        this.day = prev.day;
        this.isOffset = prev.isOffset;
    }

    public static DatePattern parseDatePattern(Date datum) {

        // System.out.println("in parseDatePattern(Date)");
        Calendar cal = Calendar.getInstance();

        cal.setTime(datum);
        int yearT = cal.get(Calendar.YEAR);
        int monthT = cal.get(Calendar.MONTH);
        int dayT = cal.get(Calendar.DAY_OF_MONTH);

        // System.out.println("leaving");
        return new DatePattern(yearT, monthT, dayT, false);
    }

    public static DatePattern parseDatePattern(String value) throws NumberFormatException {

        // System.out.println("in parseDatePattern(String): " + value);
        StringTokenizer dString = new StringTokenizer(value, "#");
        String sYear = dString.nextToken();
        String sMonth = dString.nextToken();
        String sDay = dString.nextToken();

        if ((sYear == null) || (sMonth == null) || (sDay == null))
            throw new NumberFormatException("Invalid date pattern specified: " + value);
        // System.out.println("Found date pattern: year: " + sYear + ", month: " + sMonth + ", day: " +sDay);

        try {
            int yearT = Integer.parseInt(sYear);
            int monthT = Integer.parseInt(sMonth);
            int dayT = Integer.parseInt(sDay);

            return new DatePattern(yearT, monthT, dayT, true);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("Invalid date pattern specified: " + value);
        }
    }

    public Date getDate() {
        if (!isOffset) {
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.YEAR, this.year);
            cal.set(Calendar.MONTH, this.month);
            cal.set(Calendar.DAY_OF_MONTH, this.day);
            return cal.getTime();
        } else {
            return null;
        }
    }

    /**
     * Add the date pattern to the specified date and return the result date.
     */
    public void add(DatePattern another) {

        // System.out.println("in DatePattern.add()");
        // System.out.println("another.isOffset: " + another.isOffset);
        // System.out.println("this.isOffset: " + this.isOffset);
        if (!another.isOffset) { // We have a real date.

            Calendar cal = Calendar.getInstance();

            // System.out.println("another: year=" + another.year + ", month=" + another.month + ", day=" + another.day);
            cal.set(Calendar.YEAR, another.year);
            cal.set(Calendar.MONDAY, another.month);
            cal.set(Calendar.DATE, another.day);

            cal.add(Calendar.YEAR, this.year);
            cal.add(Calendar.MONTH, this.month);
            cal.add(Calendar.DAY_OF_MONTH, this.day);

            this.year = cal.get(Calendar.YEAR);
            this.month = cal.get(Calendar.MONTH);
            this.day = cal.get(Calendar.DAY_OF_MONTH);
            this.isOffset = false;
        } else if (!this.isOffset) { // We have a real date.
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.YEAR, this.year);
            cal.set(Calendar.MONDAY, this.month);
            cal.set(Calendar.DATE, this.day);

            cal.add(Calendar.YEAR, another.year);
            cal.add(Calendar.MONTH, another.month);
            cal.add(Calendar.DAY_OF_MONTH, another.day);

            this.year = cal.get(Calendar.YEAR);
            this.month = cal.get(Calendar.MONTH);
            this.day = cal.get(Calendar.DAY_OF_MONTH);
        } else { // We have two offsets.

            this.year += another.year;
            this.month += another.month;
            this.day += another.day;

        }
    }

    /**
     * Subtract the date pattern to the specified date and return the result date.
     */
    public void subtract(DatePattern another) {

        if (!another.isOffset) { // We have a real date.
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.YEAR, another.year);
            cal.set(Calendar.MONDAY, another.month);
            cal.set(Calendar.DATE, another.day);

            cal.add(Calendar.YEAR, this.year);
            cal.add(Calendar.MONTH, this.month);
            cal.add(Calendar.DAY_OF_MONTH, this.day);

            this.year = cal.get(Calendar.YEAR);
            this.month = cal.get(Calendar.MONTH);
            this.day = cal.get(Calendar.DAY_OF_MONTH);
            this.isOffset = false;

        } else if (!this.isOffset) { // We have a real date.
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.YEAR, -this.year);
            cal.set(Calendar.MONDAY, -this.month);
            cal.set(Calendar.DATE, -this.day);

            cal.add(Calendar.YEAR, -another.year);
            cal.add(Calendar.MONTH, -another.month);
            cal.add(Calendar.DAY_OF_MONTH, -another.day);

            this.year = cal.get(Calendar.YEAR);
            this.month = cal.get(Calendar.MONTH);
            this.day = cal.get(Calendar.DAY_OF_MONTH);

        } else { // We have two offsets.

            this.year += another.year;
            this.month += another.month;
            this.day += another.day;

        }
    }

    /**
     * Multiply the date pattern to the specified date and return the result date.
     * Mmmm... this won't occur that much probably.
     */
    public void multiply(DatePattern another) {}
}
