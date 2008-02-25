package com.dexels.navajo.tipi.swingclient.components.calendar;

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

public class MultipleDayRenderer
    extends JPanel {
  private Day[] myDays;
  private boolean isSelected = false;
  private boolean isAnchor = false;
  private String myLabel = "";
  private String myFromTime, myToTime, myHall;
  private Image myBackgroundImage;
  private int myAttributeCount = 0;

  public MultipleDayRenderer() {
  }

  public void setDays(Day[] d) {
    myDays = d;
    myLabel = myDays[0].getDateString();
//    for(int i=0;i<myDays.length;i++){
//      if(myDays[i].getAttributes().size() > 0){
//        myAttributeCount++;
//      }
//    }
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

    if (!myLabel.equals("")) {
      // Graphical representation when the day is set.
      GradientPaint dayPaint;
      if (isSelected) {
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, Color.decode("#7A9BF8"), img.getWidth() / 2, 70, Color.white);
      }
      else if (myAttributeCount > 1) {
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, Color.decode("#77FF77"), img.getWidth() / 2, 70, Color.white);
      }
      else {
        dayPaint = new GradientPaint(img.getWidth() / 2, 0, Color.decode("#EEEEEE"), img.getWidth() / 2, 70, Color.white);
      }
      offScreen.setPaint(dayPaint);
      offScreen.fillRect(0, 0, img.getWidth(), img.getHeight());

      if (myBackgroundImage != null) {
        offScreen.drawImage(myBackgroundImage, img.getWidth() - myBackgroundImage.getWidth(this) - 2, 0, this);
      }

      offScreen.setColor(Color.black);
      offScreen.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);
      if (isAnchor) {
        offScreen.setColor(Color.red);
        offScreen.drawRect(0, 0, img.getWidth() - 2, img.getHeight() - 2);
      }
      offScreen.drawString(myLabel, 10, 15);
      //offScreen.drawString(myFromTime, 10, 30);
      //offScreen.drawString(myToTime, 10, 45);
      //offScreen.drawString(myHall, 10, 60);
    }
    else {
      // Graphical representation when the day is not set.
      GradientPaint dayPaint = new GradientPaint(img.getWidth() / 2, 0, Color.darkGray, img.getWidth() / 2, 70, Color.black);
      offScreen.setPaint(dayPaint);
      offScreen.fillRect(0, 0, img.getWidth(), img.getHeight());
      offScreen.setColor(Color.black);
      offScreen.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);
    }

    g.drawImage(img, 0, 0, this);
    offScreen.dispose();
  }
}
