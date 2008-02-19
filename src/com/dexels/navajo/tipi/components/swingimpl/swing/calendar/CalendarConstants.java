package com.dexels.navajo.tipi.components.swingimpl.swing.calendar;

import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */
public class CalendarConstants {
  static int DEFAULT_ROW_HEIGHT = 75;
  static int DEFAULT_COLUMN_WIDTH = 75;
  final static int SELECTION_COLOR = 0;
  final static int DAY_BORDER_COLOR = 1;
  final static int DAY_ANCHOR_COLOR = 2;
  final static int DAY_FONT_COLOR = 3;
  final static int DAY_COLOR = 4;
  final static int NONDAY_COLOR = 5;
  final static int BACKGROUND_COLOR = 6;
  final static int BGFONT_COLOR = 7;
  final static int ATTRIBUTED_COLOR = 8;
  final static String COLORSCHEME_DEFAULT = "Default";
  final static String COLORSCHEME_UNDERWORLD = "Noot";
  final static String COLORSCHEME_CONTRAST = "Aap";
  final static String COLORSCHEME_RETRO = "Matthijs";
  final static Color[] DEFAULT = {
      Color.decode("#7A9BF8"), Color.lightGray, Color.red, Color.black, Color.white, Color.decode("#002266"), Color.decode("#EEEEEE"), Color.black, Color.decode("#77FF77")};
  final static Color[] UNDERWORLD = {
      Color.decode("#0000FF"), Color.lightGray, Color.yellow, Color.white, Color.red, Color.decode("#440000"), Color.decode("#000000"), Color.white, Color.decode("#77FF77")};
  final static Color[] CONTRAST = {
      Color.blue, Color.black, Color.white, Color.black, Color.white, Color.lightGray, Color.white, Color.black, Color.decode("#77FF77")};
  final static Color[] RETRO = {
      Color.decode("#F86300"), Color.black, Color.yellow, Color.black, Color.white, Color.lightGray, Color.lightGray, Color.black, Color.decode("#33aa33")};
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
    else {
      return DEFAULT[colorId];
    }
  }

}
