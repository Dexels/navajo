package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;
import com.dexels.navajo.tipi.components.core.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiSwingComponentImpl extends TipiComponentImpl {
  private int gridsize = 10;

  public void highLight(Component c, Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(3.0f));
    Rectangle r = c.getBounds();
    g2.drawRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2);
    g2.setStroke(new BasicStroke(1.0f));
  }
//
  public void paintGrid(Component c, Graphics g) {
    Color old = g.getColor();
    Rectangle r = c.getBounds();
    g.setColor(Color.gray);
    for(int xpos = r.x;xpos<=r.width;xpos+=gridsize){
      g.drawLine(xpos,r.y,xpos,r.height);
    }
    for(int ypos = r.y;ypos<=r.height;ypos+=gridsize){
      g.drawLine(r.x,ypos,r.width,ypos);
    }
    g.setColor(old);
  }

}