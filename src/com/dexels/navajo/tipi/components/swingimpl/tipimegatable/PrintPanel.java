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
    for (int i = 0; i < getComponentCount(); i++) {
      Component current = getComponent(i);
      Rectangle rr = current.getBounds();
      if (rr.y >= startrange && rr.y < endrange) {
         if (rr.y > highestEnd) {
           highestEnd = rr.y;
           System.err.println("Found a breakable point @" + rr.y+rr.height);
           lastComponent = current;
         }
       }


      if (rr.y+rr.height >= startrange && rr.y+rr.height < endrange) {
        if (rr.y+rr.height > highestEnd) {
          highestEnd = rr.y+rr.height;
          System.err.println("Found a breakable point @" + rr.y+rr.height);
          lastComponent = current;
        }
      }
//      else {
      if (rr.y < startrange && rr.height + rr.y >= endrange && (current instanceof PageBreakable)) {
//
//        if (current instanceof PageBreakable) {
          System.err.println("Found a (possibly) breakable subcomponent. Bounds: "+rr);
          int divpoint = ( (PageBreakable) current).getDivisionPoint(startrange - rr.y, endrange - rr.y);
          System.err.println("Divpoint: " + divpoint);
          divpoint = divpoint + rr.y;
          System.err.println("Translates to: " + divpoint);
          if (divpoint > highestEnd && divpoint > startrange && divpoint< endrange ) {
            highestEnd = divpoint;
            System.err.println("Found a breakable point @" + divpoint);
            lastComponent = current;
          }
        }
//      }
    }
    if (lastComponent == null) {
      System.err.println("Nothing found");
      return -1;
    }
    else {
      System.err.println("Returning breakable point: " + highestEnd);
      return highestEnd;
    }
//      }
//    }
  }
}
