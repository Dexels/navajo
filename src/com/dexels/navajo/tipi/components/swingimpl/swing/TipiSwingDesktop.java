package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.components.swingimpl.parsers.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingDesktop
    extends JDesktopPane
    implements TipiDesignable {
  private TipiSwingDataComponentImpl me;
  private boolean gridFlag = false;
  private boolean selected = false;
  private Image myImage = null;
  private final String NORTH = "north";
  private final String EAST = "east";
  private final String SOUTH = "south";
  private final String WEST = "west";
  private final String CENTER = "center";
  private final String NORTHEAST = "northeast";
  private final String SOUTHEAST = "southeast";
  private final String NORTHWEST = "northwest";
  private final String SOUTHWEST = "southwest";

  private String alignment = CENTER;

  public TipiSwingDesktop(TipiSwingDataComponentImpl me) {
    this.me = me;
  }

  public TipiSwingDesktop(Image i, TipiSwingDataComponentImpl me) {
    this.me = me;
    myImage = i;
  }

  public void setImage(Image i) {
    myImage = i;
  }

  public void setHighlighted(boolean value) {
    selected = value;
  }

  public boolean isHighlighted() {
    return selected;
  }

  public void showGrid(boolean value) {
    gridFlag = value;
  }

  public boolean isGridShowing() {
    return gridFlag;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    TipiGradientPaint myPaint = me.getPaint();
    if(myPaint != null){
      myPaint.setBounds(this.getBounds());
      Paint p =  myPaint.getPaint();
      Graphics2D g2 = (Graphics2D)g;
      Paint oldPaint = g2.getPaint();
      g2.setPaint(p);
      g2.fillRect(0,0,getWidth(), getHeight());
      g2.setPaint(oldPaint);
    }

    if(myImage != null){
      if(NORTHWEST.equals(alignment)){
        g.drawImage(myImage, 0, 0, this);
      }
      if(NORTH.equals(alignment)){
        g.drawImage(myImage,( (getWidth()/2) - (myImage.getWidth(this)/2) ), 0, this);
      }
      if(NORTHEAST.equals(alignment)){
        g.drawImage(myImage, getWidth() - myImage.getWidth(this), 0, this);
      }
      if(WEST.equals(alignment)){
        g.drawImage(myImage, 0, ( (getHeight()/2) - (myImage.getHeight(this)/2) ), this);
      }
      if(CENTER.equals(alignment)){
        g.drawImage(myImage, ( (getWidth()/2) - (myImage.getWidth(this)/2) ), ( (getHeight()/2) - (myImage.getHeight(this)/2) ), this);
      }
      if(EAST.equals(alignment)){
        g.drawImage(myImage, getWidth() - myImage.getWidth(this), ( (getHeight()/2) - (myImage.getHeight(this)/2) ), this);
      }
      if(SOUTHWEST.equals(alignment)){
        g.drawImage(myImage, 0, getHeight() - myImage.getHeight(this), this);
      }
      if(SOUTH.equals(alignment)){
        g.drawImage(myImage, ( (getWidth()/2) - (myImage.getWidth(this)/2) ), getHeight() - myImage.getHeight(this), this);
      }
      if(SOUTHEAST.equals(alignment)){
        g.drawImage(myImage, getWidth() - myImage.getWidth(this), getHeight() - myImage.getHeight(this), this);
      }
    }
    Color old = g.getColor();
    if (selected) {
      me.highLight(this, g);
    }
    g.setColor(old);
  }

}
