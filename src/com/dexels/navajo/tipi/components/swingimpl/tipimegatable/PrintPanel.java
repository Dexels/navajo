package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import javax.swing.JPanel;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PrintPanel extends JPanel implements PageBreakable{
  public PrintPanel() {
  }

  protected void printComponent(Graphics g) {
    // Make sure the background will not be printed
  }

  public int getDivisionPoint(int startrange, int endrange) {
    int highestEnd = -1;
    Component lastComponent = null;

//    if (getLayout() instanceof BoxLayout) {
//      BoxLayout bl = (BoxLayout)getLayout();
    System.err.println("Found " + getComponentCount() + " components!");
    System.err.println("Layout: " + getLayout());
    for (int i = 0; i < getComponentCount(); i++) {
      Component current = getComponent(i);
      System.err.println("Checking component: " + current);
      Rectangle rr = current.getBounds();
      System.err.println("Bounds: " + rr);
      if (rr.y >= startrange && rr.y < endrange) {
        if (rr.y > highestEnd) {
          highestEnd = rr.y;
          System.err.println("Found a breakable point @" + rr.y);
          lastComponent = current;
        }
      }
      else {
        if (rr.y < startrange && rr.height + rr.y >= endrange && (current instanceof PageBreakable)) {
          System.err.println("Found a (possibly) breakable subcomponent");
          int divpoint = ( (PageBreakable) current).getDivisionPoint(startrange - rr.y, endrange - rr.y);
          System.err.println("Divpoint: " + divpoint);
          divpoint = divpoint + rr.y;
          System.err.println("Translates to: " + divpoint);
          if (divpoint > highestEnd) {
            highestEnd = divpoint;
            System.err.println("Found a breakable point @" + divpoint);
            lastComponent = current;
          }
        }
      }
    }
    if (lastComponent == null) {
      System.err.println("Nothing found");
      return -1;
        } else {
          System.err.println("Returning breakable point: "+highestEnd);
          return highestEnd;
        }
//      }
//    }
  }
}
