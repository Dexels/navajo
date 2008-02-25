package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.util.*;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class CalendarConstants {
  public static int DEFAULT_ROW_HEIGHT = 75;
  public static int DEFAULT_COLUMN_WIDTH = 75;

  public final static int SELECTION_COLOR = 0;
  public final static int DAY_BORDER_COLOR = 1;
  public final static int DAY_ANCHOR_COLOR = 2;
  public final static int DAY_FONT_COLOR = 3;
  public final static int DAY_COLOR = 4;
  public final static int NONDAY_COLOR = 5;
  public final static int BACKGROUND_COLOR = 6;
  public final static int BGFONT_COLOR = 7;
  public final static int ATTRIBUTED_COLOR = 8;

  public final static String COLORSCHEME_DEFAULT = "Default";
  public final static String COLORSCHEME_UNDERWORLD = "Donker";
  public final static String COLORSCHEME_CONTRAST = "Licht";
  public final static String COLORSCHEME_RETRO = "Unix";
  public final static String COLORSCHEME_SPORTLINK = "Sportlink";

  public final static Color[] DEFAULT = {
      Color.decode("#7A9BF8"), Color.lightGray, Color.red, Color.black, Color.white, Color.decode("#002266"), SystemColor.control, Color.black, Color.decode("#77FF77")};
  public final static Color[] UNDERWORLD = {
      Color.decode("#0000FF"), Color.lightGray, Color.yellow, Color.white, Color.red, Color.decode("#440000"), Color.decode("#000000"), Color.white, Color.decode("#77FF77")};
  public final static Color[] CONTRAST = {
      Color.blue, Color.black, Color.white, Color.black, Color.white, Color.lightGray, Color.white, Color.black, Color.decode("#77FF77")};
  public final static Color[] RETRO = {
      Color.decode("#F86300"), Color.black, Color.yellow, Color.black, Color.white, Color.lightGray, Color.lightGray, Color.black, Color.decode("#33aa33")};
  public final static Color[] SPORTLINK = {
      Color.decode("#F47921"), Color.lightGray, Color.red, Color.black, Color.white, Color.decode("#213075"), Color.white, Color.black, Color.decode("#77FF77")};

  private static String myColorScheme = COLORSCHEME_DEFAULT;

  public CalendarConstants() {
  }

  public static void setColorScheme(String schemeId) {
    myColorScheme = schemeId;
  }

  public static Color getColor(int colorId) {
    if (myColorScheme.equals(COLORSCHEME_UNDERWORLD)) {
      return UNDERWORLD[colorId];
    }
    else if (myColorScheme.equals(COLORSCHEME_CONTRAST)) {
      return CONTRAST[colorId];
    }
    else if (myColorScheme.equals(COLORSCHEME_RETRO)) {
      return RETRO[colorId];
    }
    else if (myColorScheme.equals(COLORSCHEME_SPORTLINK)) {
      return SPORTLINK[colorId];
    }
    else {
      return DEFAULT[colorId];
    }
  }

  public static Vector getColorSchemas() {
    Vector v = new Vector();
    v.addElement(COLORSCHEME_DEFAULT);
    // Add additional schemas below
    v.addElement(COLORSCHEME_CONTRAST);
    v.addElement(COLORSCHEME_UNDERWORLD);
    v.addElement(COLORSCHEME_RETRO);
    v.addElement(COLORSCHEME_SPORTLINK);
    return v;
  }

  public int getColumnWidth() {
    return DEFAULT_COLUMN_WIDTH;
  }

  public int getRowHeight() {
    return DEFAULT_ROW_HEIGHT;
  }

  public void setRowHeight(int h) {
    DEFAULT_ROW_HEIGHT = h;
  }

  public void setColumnWidth(int w) {
    DEFAULT_COLUMN_WIDTH = w;
  }
}
