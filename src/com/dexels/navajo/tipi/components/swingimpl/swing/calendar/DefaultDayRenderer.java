package com.dexels.navajo.tipi.components.swingimpl.swing.calendar;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */
public class DefaultDayRenderer
    extends JPanel {
//  private Day myDay;
  private boolean isSelected = false;
  private boolean isAnchor = false;
  private String myLabel;
  private String myFromTime, myToTime, myHall;
  private Image myBackgroundImage;
  public DefaultDayRenderer() {
  }

  public void setDay(Day d) {
//    myDay = d;
    myLabel = d.getDateString();
    myFromTime = (String) d.getAttribute("from");
    myToTime = (String) d.getAttribute("to");
    myHall = (String) d.getAttribute("hall");
    String img_url = (String) d.getAttribute("image");
    if (img_url != null) {
      myBackgroundImage = (new ImageIcon(this.getClass().getClassLoader().getResource(img_url))).getImage();
    }
    else {
      myBackgroundImage = null;
    }
    if (myFromTime == null) {
      myFromTime = "";
    }
    if (myToTime == null) {
      myToTime = "";
    }
    if (myHall == null) {
      myHall = "";
    }
  }

  public void setSelected(boolean value) {
    isSelected = value;
  }

  public void setAnchor(boolean value) {
    isAnchor = value;
  }

  public void paintComponent(Graphics g) {
    BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D offScreen = (Graphics2D) img.getGraphics();
    Composite oldComposite = offScreen.getComposite();
    if (!myLabel.equals("")) {
      // Graphical representation when the day is set.
      GradientPaint dayPaint;
      if (myFromTime.length() > 1) {
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.ATTRIBUTED_COLOR), img.getWidth() / 2, CalendarConstants.DEFAULT_ROW_HEIGHT, CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      }
      else {
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.DAY_COLOR), img.getWidth() / 2, CalendarConstants.DEFAULT_ROW_HEIGHT, CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      }
      offScreen.setPaint(dayPaint);
      offScreen.fillRect(0, 0, img.getWidth(), img.getHeight());
      if (myBackgroundImage != null) {
        offScreen.drawImage(myBackgroundImage, img.getWidth() - myBackgroundImage.getWidth(this) - 2, 0, this);
      }
      if (isSelected) {
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
        offScreen.setComposite(alpha);
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.SELECTION_COLOR), img.getWidth() / 2, CalendarConstants.DEFAULT_ROW_HEIGHT, CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
        offScreen.setPaint(dayPaint);
        offScreen.fillRect(0, 0, img.getWidth(), img.getHeight());
        offScreen.setComposite(oldComposite);
      }
      offScreen.setColor(CalendarConstants.getColor(CalendarConstants.DAY_BORDER_COLOR));
      offScreen.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);
      if (isAnchor) {
        offScreen.setColor(CalendarConstants.getColor(CalendarConstants.DAY_ANCHOR_COLOR));
        offScreen.drawRect(0, 0, img.getWidth() - 2, img.getHeight() - 2);
      }
      offScreen.setColor(CalendarConstants.getColor(CalendarConstants.DAY_FONT_COLOR));
      offScreen.drawString(myLabel, 10, 15);
      // Mmmm take these out..
      offScreen.drawString(myFromTime, 10, 30);
      offScreen.drawString(myToTime, 10, 45);
      offScreen.drawString(myHall, 10, 60);
    }
    else {
      // Graphical representation when the day is not set.
      GradientPaint dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.NONDAY_COLOR), img.getWidth() / 2, CalendarConstants.DEFAULT_ROW_HEIGHT, CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      offScreen.setPaint(dayPaint);
      offScreen.fillRect(0, 0, img.getWidth(), img.getHeight());
      offScreen.setColor(CalendarConstants.getColor(CalendarConstants.DAY_BORDER_COLOR));
      offScreen.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);
    }
    g.drawImage(img, 0, 0, this);
    offScreen.dispose();
  }
}
