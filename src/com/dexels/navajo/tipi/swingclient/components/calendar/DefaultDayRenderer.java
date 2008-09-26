package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.*;
import java.awt.image.*;
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

public class DefaultDayRenderer
    extends JPanel
    implements DayRendererInterface {
//  private Day myDay;
  private boolean isSelected = false;
  private boolean isAnchor = false;
  private String myLabel;
  private String myFromTime, myToTime, myHall;
  private Image myBackgroundImage;
  private int nrOfAttributes = 0;
  private boolean isToday = false;

  public DefaultDayRenderer() {
  }

  public Component getComponent() {
    return this;
  }

  public void setDay(Day d) {
//    myDay = d;
    myLabel = d.getDateString();
    myFromTime = (String) d.getAttribute("from");
    myToTime = (String) d.getAttribute("CATCHUPDAY");
    myHall = (String) d.getAttribute("MATCHDAY");

    if (d.getDate() != null) {

      Calendar todaysCalendar = Calendar.getInstance();
      todaysCalendar.set(Calendar.HOUR, 0);
      todaysCalendar.set(Calendar.MINUTE, 0);
      todaysCalendar.set(Calendar.SECOND, 0);
      todaysCalendar.set(Calendar.MILLISECOND, 0);
      Date today = todaysCalendar.getTime();

      Calendar dayCalendar = Calendar.getInstance();
      dayCalendar.setTime(d.getDate());
      dayCalendar.set(Calendar.HOUR, 0);
      dayCalendar.set(Calendar.MINUTE, 0);
      dayCalendar.set(Calendar.SECOND, 0);
      dayCalendar.set(Calendar.MILLISECOND, 0);
      Date dayDate = dayCalendar.getTime();

      if (dayDate.equals(today)) {
        isToday = true;
      }
      else {
        isToday = false;
      }
    }

    //System.err.println("Setting day in redererederer: " + d.getAttributes().size());
    nrOfAttributes = d.getAttributes().size();
    String img_url = (String) d.getAttribute("image");
    if (img_url != null) {
      myBackgroundImage = (new ImageIcon(DefaultDayRenderer.class.getResource(img_url))).getImage();
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

  @Override
public void paintComponent(Graphics g) {

    BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D offScreen = (Graphics2D) img.getGraphics();
    Composite oldComposite = offScreen.getComposite();

    if (!myLabel.equals("")) {
      // Graphical representation when the day is set.
      GradientPaint dayPaint;

      if (nrOfAttributes > 0) {
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.ATTRIBUTED_COLOR), img.getWidth() / 2, getHeight(), CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      }
      else {
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.DAY_COLOR), img.getWidth() / 2, getHeight(), CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      }
      offScreen.setPaint(dayPaint);
      offScreen.fillRect(0, 0, img.getWidth(), img.getHeight());
      if (myBackgroundImage != null) {
        offScreen.drawImage(myBackgroundImage, img.getWidth() - myBackgroundImage.getWidth(this) - 2, 0, this);
      }

      if(isToday) {
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
        offScreen.setComposite(alpha);
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.ATTRIBUTED_COLOR), img.getWidth() / 2, getHeight(), CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
        offScreen.setPaint(dayPaint);
        offScreen.fillRect(0, 0, img.getWidth(), img.getHeight());
        offScreen.setComposite(oldComposite);
      }

      if (isSelected) {
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
        offScreen.setComposite(alpha);
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.SELECTION_COLOR), img.getWidth() / 2, getHeight(), CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
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
      offScreen.drawString(myLabel, 2, 12);
      // Mmmm take these out..
      offScreen.drawString(myFromTime, 10, 30);
      offScreen.drawString(myToTime, 10, 45);
      offScreen.drawString(myHall, 10, 60);
    }
    else {
      // Graphical representation when the day is not set.
      GradientPaint dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.NONDAY_COLOR), img.getWidth() / 2, getHeight(), CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      offScreen.setPaint(dayPaint);
      offScreen.fillRect(0, 0, img.getWidth(), img.getHeight());
      offScreen.setColor(CalendarConstants.getColor(CalendarConstants.DAY_BORDER_COLOR));
      offScreen.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);
    }

    g.drawImage(img, 0, 0, this);
    offScreen.dispose();
  }
}
