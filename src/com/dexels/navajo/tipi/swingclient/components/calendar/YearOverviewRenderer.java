package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class YearOverviewRenderer
    implements TableCellRenderer {
  private YearOverviewCell myCellJan = new YearOverviewCell();
  private YearOverviewCell myCellFeb = new YearOverviewCell();
  private YearOverviewCell myCellMar = new YearOverviewCell();
  private YearOverviewCell myCellApr = new YearOverviewCell();
  private YearOverviewCell myCellMay = new YearOverviewCell();
  private YearOverviewCell myCellJun = new YearOverviewCell();
  private YearOverviewCell myCellJul = new YearOverviewCell();
  private YearOverviewCell myCellAug = new YearOverviewCell();
  private YearOverviewCell myCellSep = new YearOverviewCell();
  private YearOverviewCell myCellOct = new YearOverviewCell();
  private YearOverviewCell myCellNov = new YearOverviewCell();
  private YearOverviewCell myCellDec = new YearOverviewCell();

  public YearOverviewRenderer() {
    myCellJan.setMonth(0);
    myCellFeb.setMonth(1);
    myCellMar.setMonth(2);
    myCellApr.setMonth(3);
    myCellMay.setMonth(4);
    myCellJun.setMonth(5);
    myCellJul.setMonth(6);
    myCellAug.setMonth(7);
    myCellSep.setMonth(8);
    myCellOct.setMonth(9);
    myCellNov.setMonth(10);
    myCellDec.setMonth(11);
  }

  public Component getTableCellRendererComponent(JTable parm1, Object parm2, boolean isSelected, boolean hasFocus, int row, int column) {
    int month = 4 * row + column;
//    System.err.println("------------------------------------------------>>>>> Month: " + month);
    if (month == 0) {
//      System.err.println("-----------.>>> Returning JANUARI");
      myCellJan.setSelected(isSelected);
      return myCellJan;
    }
    if (month == 1) {
      myCellFeb.setSelected(isSelected);
      return myCellFeb;
    }
    if (month == 2) {
      myCellMar.setSelected(isSelected);
      return myCellMar;
    }
    if (month == 3) {
      myCellApr.setSelected(isSelected);
      return myCellApr;
    }
    if (month == 4) {
      myCellMay.setSelected(isSelected);
      return myCellMay;
    }
    if (month == 5) {
      myCellJun.setSelected(isSelected);
      return myCellJun;
    }
    if (month == 6) {
      myCellJul.setSelected(isSelected);
      return myCellJul;
    }
    if (month == 7) {
      myCellAug.setSelected(isSelected);
      return myCellAug;
    }
    if (month == 8) {
      myCellSep.setSelected(isSelected);
      return myCellSep;
    }
    if (month == 9) {
      myCellOct.setSelected(isSelected);
      return myCellOct;
    }
    if (month == 10) {
      myCellNov.setSelected(isSelected);
      return myCellNov;
    }
    if (month == 11) {
      myCellDec.setSelected(isSelected);
      return myCellDec;
    }

    return null;
  }

}