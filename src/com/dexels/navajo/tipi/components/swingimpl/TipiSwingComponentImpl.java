package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.tipi.components.core.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiSwingComponentImpl
    extends TipiComponentImpl {
  private int gridsize = 10;
  protected TipiPopupMenu myPopupMenu = null;
  public void highLight(Component c, Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(3.0f));
    Rectangle r = c.getBounds();
    g2.drawRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2);
    g2.setStroke(new BasicStroke(1.0f));
  }

//
  public void setCursor(int cursorid) {
    if (getContainer() != null) {
      ( (Container) getContainer()).setCursor(Cursor.getPredefinedCursor(cursorid));
    }
  }

  public Container getSwingContainer() {
    return (Container) getContainer();
  }

  public Object getContainerLayout() {
    return getSwingContainer().getLayout();
  }

  public void paintGrid(Component c, Graphics g) {
    Color old = g.getColor();
    Rectangle r = c.getBounds();
    g.setColor(Color.gray);
    for (int xpos = r.x; xpos <= r.width; xpos += gridsize) {
      g.drawLine(xpos, r.y, xpos, r.height);
    }
    for (int ypos = r.y; ypos <= r.height; ypos += gridsize) {
      g.drawLine(r.x, ypos, r.width, ypos);
    }
    g.setColor(old);
  }

  public void showPopup(MouseEvent e) {
    ( (JPopupMenu) myPopupMenu.getSwingContainer()).show(getSwingContainer(), e.getX(), e.getY());
  }

  public void setContainerLayout(Object layout) {
    ( (Container) getContainer()).setLayout( (LayoutManager) layout);
  }
}