package com.dexels.navajo.tipi.components.swingimpl;

import java.lang.reflect.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.parsers.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiSwingComponentImpl
    extends TipiComponentImpl implements TipiSwingComponent {
  private int gridsize = 10;
  protected TipiGradientPaint myPaint;
  protected TipiPopupMenu myPopupMenu = null;
  private boolean committedInUI;
  protected SwingTipiContext mySwingTipiContext;

  public void showPopup(MouseEvent e) {
  ( (JPopupMenu) myPopupMenu.getSwingContainer()).show(getSwingContainer(), e.getX(), e.getY());
  }

  public void highLight(Component c, Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(3.0f));
    Rectangle r = c.getBounds();
    g2.drawRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2);
    g2.setStroke(new BasicStroke(1.0f));
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

  public void setPaint(Paint p){
    this.myPaint = (TipiGradientPaint)p;
  }

  public TipiGradientPaint getPaint(){
    return myPaint;
  }


  public void setCursor(int cursorid) {
    if (getContainer() != null) {
      ( (Container) getContainer()).setCursor(Cursor.getPredefinedCursor(cursorid));
    }
  }
  public void setCursor(Cursor c) {
    if (getSwingContainer()!=null) {
      getSwingContainer().setCursor(c);
    }
  }

  public void initContainer() {
	  mySwingTipiContext = (SwingTipiContext)myContext;
    if (getContainer() == null) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          setContainer(createContainer());
        }
      });
    }
  }

  public Container getSwingContainer() {
    return (Container) getContainer();
  }

  public Object getContainerLayout() {
    return getSwingContainer().getLayout();
  }


  public void setContainerLayout(Object layout) {
    ( (Container) getContainer()).setLayout( (LayoutManager) layout);
  }

  public void addToContainer(final Object c, final Object constraints) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().add( (Component) c, constraints);
      }
    });
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().remove( (Component) c);
      }
    });
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
//        System.err.println("Interrupted");
      }
    }
  }

  public void runASyncInEventThread(Runnable r) {
    if (SwingUtilities.isEventDispatchThread() || !committedInUI) {
      r.run();
    }
    else {
      SwingUtilities.invokeLater(r);
    }
  }

  public void print() {
    if (getSwingContainer()!=null) {
      PrintJob pj = Toolkit.getDefaultToolkit().getPrintJob((Frame)myContext.getTopLevel(),"aap",null);
      Graphics g = pj.getGraphics();
      getSwingContainer().print(g);
    }
  }
  
  public void commitToUi() {
      super.commitToUi();
      committedInUI = true;
  }

}
