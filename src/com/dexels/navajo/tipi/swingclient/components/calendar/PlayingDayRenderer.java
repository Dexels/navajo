package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.util.*;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class PlayingDayRenderer
    extends JPanel
    implements DayRendererInterface {
  private Day myDay;
  private boolean isSelected = false;
  private boolean isAnchor = false;
  private String myLabel;
  private String catchUpDay, playingDay;
  private int nrOfAttributes = 0;

  public PlayingDayRenderer() {
  }

  public Component getComponent() {
    return this;
  }

  public void setDay(Day d) {
    myDay = d;
    myLabel = d.getDateString();
    Set m = d.getMessages();
    catchUpDay = (String) d.getAttribute("CATCHUPDAY");
    playingDay = (String) d.getAttribute("MATCHDAY");
    nrOfAttributes = d.getAttributes().size();
    Iterator it = m.iterator();
    while (it.hasNext()) {
      Message cur = (Message) it.next();
      if (cur.getProperty("DayCode").getValue().equals("MATCHDAY")) {
        playingDay = "Wd. " + cur.getProperty("DayNumber").getValue();
      }
      if (cur.getProperty("DayCode").getValue().equals("CATCHUPDAY")) {
        catchUpDay = "Id. " + cur.getProperty("DayNumber").getValue();
      }
    }
    if (playingDay == null) {
      playingDay = "";
    }
    if (catchUpDay == null) {
      catchUpDay = "";
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

      if (!"".equals(playingDay)) {
        if (!"".equals(catchUpDay)) {
          // Blauw & Groen
          dayPaint = new GradientPaint(img.getWidth() / 2, 0, new Color(103, 210, 180), img.getWidth() / 2, getHeight(), CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
        }
        else {
          // Blauw
          dayPaint = new GradientPaint(img.getWidth() / 2, 0, new Color(103, 165, 250), img.getWidth() / 2, getHeight(), CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
        }
      }
      else if (!"".equals(catchUpDay)) {
        // Groen
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, new Color(0, 255, 60), img.getWidth() / 2, getHeight(), CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      }
      else {
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, CalendarConstants.getColor(CalendarConstants.DAY_COLOR), img.getWidth() / 2, getHeight(), CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      }
      offScreen.setPaint(dayPaint);
      offScreen.fillRect(0, 0, img.getWidth(), img.getHeight());

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
      offScreen.drawString(myLabel, 5, 15);
      // Mmmm take these out..
      offScreen.drawString(playingDay, 5, 30);
      offScreen.drawString(catchUpDay, 5, 45);
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