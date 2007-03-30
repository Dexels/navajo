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
public class TipiSwingPanel
    extends JPanel
    implements TipiDesignable, Scrollable {
  private boolean gridFlag = false;
  private boolean selected = false;
  private ImageIcon myImage = null;
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

  private final TipiSwingDataComponentImpl myComponent;

  public TipiSwingPanel(TipiSwingDataComponentImpl me) {
    this.myComponent = me;
  }

  public TipiSwingPanel() {
      this.myComponent = null;
    }
  
  public String toString() {
	  if(myComponent!=null) {
		  return "Class: "+getClass()+" component: "+myComponent.getClass()+" path: "+myComponent.getPath();
	  } else {
		  return "Unknown tipi component";
	  }
  }
  
  
  private Dimension checkMax(Dimension preferredSize) {
      Dimension maximumSize = getMaximumSize();
      if (maximumSize==null) {
          return preferredSize;
      }
      return new Dimension(Math.min(preferredSize.width, maximumSize.width),Math.min(preferredSize.height, maximumSize.height));
  }
  private Dimension checkMin(Dimension preferredSize) {
      Dimension minimumSize = getMinimumSize();
      if (minimumSize==null) {
          return preferredSize;
      }
      return new Dimension(Math.max(preferredSize.width, minimumSize.width),Math.max(preferredSize.height, minimumSize.height));
  }

  public Dimension checkMaxMin(Dimension d) {
      return checkMin(checkMax(d));
  }
  public Dimension getPreferredSize() {
      return checkMaxMin(super.getPreferredSize());
  }
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (myComponent==null) {
        return;
    }
    TipiGradientPaint myPaint = myComponent.getPaint();
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
        g.drawImage(myImage.getImage(), 0, 0, this);
      }
      if(NORTH.equals(alignment)){
        g.drawImage(myImage.getImage(),( (getWidth()/2) - (myImage.getIconWidth()/2) ), 0, this);
      }
      if(NORTHEAST.equals(alignment)){
        g.drawImage(myImage.getImage(), getWidth() - myImage.getIconWidth(), 0, this);
      }
      if(WEST.equals(alignment)){
        g.drawImage(myImage.getImage(), 0, ( (getHeight()/2) - (myImage.getIconHeight()/2) ), this);
      }
      if(CENTER.equals(alignment)){
        g.drawImage(myImage.getImage(), ( (getWidth()/2) - (myImage.getIconWidth()/2) ), ( (getHeight()/2) - (myImage.getIconHeight()/2) ), this);
      }
      if(EAST.equals(alignment)){
        g.drawImage(myImage.getImage(), getWidth() - myImage.getIconWidth(), ( (getHeight()/2) - (myImage.getIconHeight()/2) ), this);
      }
      if(SOUTHWEST.equals(alignment)){
        g.drawImage(myImage.getImage(), 0, getHeight() - myImage.getIconHeight(), this);
      }
      if(SOUTH.equals(alignment)){
        g.drawImage(myImage.getImage(), ( (getWidth()/2) - (myImage.getIconWidth()/2) ), getHeight() - myImage.getIconHeight(), this);
      }
      if(SOUTHEAST.equals(alignment)){
        g.drawImage(myImage.getImage(), getWidth() - myImage.getIconWidth(), getHeight() - myImage.getIconHeight(), this);
      }
    }
    Color old = g.getColor();
    if (selected) {
      myComponent.highLight(this, g);
    }
    g.setColor(old);
  }

  public void setImage(ImageIcon ic){
    myImage = ic;
  }

  public void setImageAlignment(String alignment){
    if(NORTH.equals(alignment) || EAST.equals(alignment) || SOUTH.equals(alignment) || WEST.equals(alignment)
       || CENTER.equals(alignment) || NORTHEAST.equals(alignment) || SOUTHEAST.equals(alignment) || NORTHWEST.equals(alignment) || SOUTHWEST.equals(alignment)){
      this.alignment = alignment;
    }
  }


  public void printComponent(Graphics g) {
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

public Dimension getPreferredScrollableViewportSize() {
	// TODO Auto-generated method stub
	return getPreferredSize();
}

public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
	// TODO Auto-generated method stub
	return 40;
}

public boolean getScrollableTracksViewportHeight() {
	return false;
}

public boolean getScrollableTracksViewportWidth() {
	return false;
}

public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
	return 20;
}


}
