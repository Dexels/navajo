package com.dexels.navajo.tipi.components.swingimpl;

import java.lang.reflect.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiSwingDataComponentImpl
    extends TipiDataComponentImpl {
  private int gridsize = 10;
  private Object result = null;
  public void initContainer() {
    if (getContainer() == null) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          setContainer(createContainer());
        }
      });
    }
  }

  public void addToContainer(final Object c, final Object constraints) {
//    runSyncInEventThread(new Runnable() {
//      public void run() {
        System.err.println("Adding to component: "+getClass());
        getSwingContainer().add( (Component) c, constraints);
        getClass().getClassLoader();
//      }
//    });
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().remove( (Component) c);
      }
    });
  }

  public void setContainerLayout(final Object layout) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ( (Container) getContainer()).setLayout( (LayoutManager) layout);
      }
    });
  }

  public void highLight(Component c, Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(3.0f));
    Rectangle r = c.getBounds();
    g2.drawRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2);
    g2.setStroke(new BasicStroke(1.0f));
  }

//
//  public void paintGrid(Component c, Graphics g) {
//    Color old = g.getColor();
//    Rectangle r = c.getBounds();
//    g.setColor(Color.gray);
//    for (int xpos = r.x; xpos <= r.width; xpos += gridsize) {
//      g.drawLine(xpos, r.y, xpos, r.height);
//    }
//    for (int ypos = r.y; ypos <= r.height; ypos += gridsize) {
//      g.drawLine(r.x, ypos, r.width, ypos);
//    }
//    g.setColor(old);
//  }

  public void replaceLayout(TipiLayout tl) {
    super.replaceLayout(tl);
    ( (Container) getContainer()).repaint();
    if (JComponent.class.isInstance(getContainer())) {
      ( (JComponent) getContainer()).revalidate();
    }
  }

  protected void doLayout() {
    if (getContainer() != null) {
//      try {
//        SwingUtilities.invokeAndWait(new Runnable() {
//          public void run() {
      ( (Container) getContainer()).doLayout();
      if (JComponent.class.isInstance(getContainer())) {
        ( (JComponent) getContainer()).revalidate();
        ( (JComponent) getContainer()).repaint();
      }
//          }
//        });
//      }
//      catch (InvocationTargetException ex) {
//        ex.printStackTrace();
//      }
//      catch (InterruptedException ex) {
//        ex.printStackTrace();
//      }
    }
  }

  public Object getContainerLayout() {
    return getSwingContainer().getLayout();
  }

  public Container getSwingContainer() {
    return (Container) getContainer();
  }

  public void refreshLayout() {
    ArrayList elementList = new ArrayList();
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = (TipiComponent) getTipiComponent(i);
      if (current.isVisibleElement()) {
        removeFromContainer(current.getContainer());
      }
      elementList.add(current);
    }
    for (int i = 0; i < elementList.size(); i++) {
      final TipiComponent current = (TipiComponent) elementList.get(i);
      if (current.isVisibleElement()) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            addToContainer(current.getContainer(), current.getConstraints());
          }
        });
      }
    }
  }

  public void runSyncInEventThread(Runnable r) {
    if (SwingUtilities.isEventDispatchThread()) {
      r.run();
    }
    else {
      try {
        SwingUtilities.invokeAndWait(r);
      }
      catch (InvocationTargetException ex) {
        throw new RuntimeException(ex);
      }
      catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  public void runASyncInEventThread(Runnable r) {
    if (SwingUtilities.isEventDispatchThread()) {
      r.run();
    }
    else {
      SwingUtilities.invokeLater(r);
    }
  }
}
