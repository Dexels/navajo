package com.dexels.navajo.tipi.components.swingimpl;

import java.lang.reflect.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.internal.*;
import java.awt.event.*;
import java.awt.print.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiSwingDataComponentImpl
    extends TipiDataComponentImpl implements TipiSwingComponent {
  private int gridsize = 10;
  private Object result = null;
  protected TipiPopupMenu myPopupMenu = null;

  public void initContainer() {
    if (getContainer() == null) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          setContainer(createContainer());
        }
      });
    }
  }
  public void setWaitCursor(boolean b) {
    Container cc =  (Container) getSwingContainer();
    if (cc!=null) {
      (cc).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
    }
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = getTipiComponent(i);
      if (TipiSwingComponent.class.isInstance(current)) {
        ((TipiSwingComponent)current).setWaitCursor(b);

      }
    }
  }

  public void addToContainer(final Object c, final Object constraints) {
        getSwingContainer().add( (Component) c, constraints);
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().remove( (Component) c);
      }
    });
  }

  public void setContainerLayout(final Object layout) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (Container) getContainer()).setLayout( (LayoutManager) layout);
      }
    });
  }

  public void highLight(Component c, Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(3.0f));
    Dimension d = c.getSize();
//    Rectangle r = c.getBounds();
//    g2.drawRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2);
    int inset = 5;
    g2.drawRect(inset, inset, d.width-2*inset, d.height-2*inset);
    g2.setStroke(new BasicStroke(1.0f));
  }

  public void setCursor(Cursor c) {
    if (getSwingContainer()!=null) {
      getSwingContainer().setCursor(c);
    }
  }

  public void print() {
    if (getSwingContainer()!=null) {

      PrinterJob printJob = PrinterJob.getPrinterJob();
       printJob.setPrintable((Printable)getSwingContainer());
       if (printJob.printDialog()) {
           try {
               printJob.print();
           } catch (Exception ex) {
               ex.printStackTrace();
           }
       }

//      PrintJob pj = Toolkit.getDefaultToolkit().getPrintJob((Frame)myContext.getTopLevel(),"aap",null);
      System.err.println("Created job");
//      System.err.println("Dimension: "+ printJob.getPageDimension());
//      System.err.println("Resolution: "+ pj.getPageResolution());
//
            if (printJob.printDialog()) {
                try {
                    printJob.print();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


//      Graphics g = pj.getGraphics();
//      System.err.println("Got graphics");
//      getSwingContainer().print(g);
//      System.err.println("Printed...");
//  //      pj.end();
    }
  }


  public void replaceLayout(TipiLayout tl) {
    super.replaceLayout(tl);
    ( (Container) getContainer()).repaint();
    if (JComponent.class.isInstance(getContainer())) {
      ( (JComponent) getContainer()).revalidate();
    }
  }
  public void showPopup(MouseEvent e) {
  ( (JPopupMenu) myPopupMenu.getSwingContainer()).show(getSwingContainer(), e.getX(), e.getY());
}

  protected void doLayout() {
    getContext().debugLog("data    ","Entering doLayout in tipi: "+getId());
    if (getContainer() != null) {
       if (JComponent.class.isInstance(getContainer())) {
         runASyncInEventThread(new Runnable() {
           public void run() {
             getContext().debugLog("data    ","Entering doLayout in tipi: "+getId());
            ( (JComponent) getContainer()).revalidate();
             ( (JComponent) getContainer()).repaint();
             getContext().debugLog("data    ","Exiting doLayout in tipi: "+getId());
           }
         });
      }
    }
    getContext().debugLog("data    ","Exiting doLayout in tipi: "+getId());
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
        runSyncInEventThread(new Runnable() {
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
        System.err.println("Interrupted");
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
