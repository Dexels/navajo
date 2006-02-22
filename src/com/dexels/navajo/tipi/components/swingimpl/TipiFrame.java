package com.dexels.navajo.tipi.components.swingimpl;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.embed.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiFrame
    extends TipiSwingDataComponentImpl {
//  private JFrame myFrame = null;
  private boolean fullscreen = false;
  private boolean visible = false;
  private int x = 0, y = 0, w = 0, h = 0;
  private String myMenuBar = "";
  public TipiFrame() {
  }

  public Object createContainer() {
     boolean internal = getContext() instanceof EmbeddedContext;
    if (internal) {
      TipiSwingFrameStudioImpl myFrame;
      myFrame = new TipiSwingFrameStudioImpl(this);
      myFrame.setClosable(true);
//      myFrame.setVisible(true);
      myFrame.setResizable(true);
      myFrame.setIconifiable(true);
      myFrame.setMaximizable(true);
      TipiHelper th = new TipiSwingHelper();
      th.initHelper(this);
      addHelper(th);
      return (Container) myFrame;
    }
    else {
      TipiSwingFrameImpl myFrame;
      myFrame = new TipiSwingFrameImpl(this);
      TipiHelper th = new TipiSwingHelper();
      th.initHelper(this);
      addHelper(th);
//        myContext.setToplevel(myFrame);
      return (Container) myFrame;
    }
  }

  public void addToContainer(final Object c, final Object constraints) {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    if (JMenuBar.class.isInstance(c)) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          myFrame.setJMenuBar( (JMenuBar) c);
        }
      });
    }
    else {
      runSyncInEventThread(new Runnable() {
        public void run() {
          myFrame.getContentPane().add( (Component) c, constraints);
        }
      });
    }
  }

  public void removeFromContainer(final Object c) {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    runSyncInEventThread(new Runnable() {
      public void run() {
        myFrame.getContentPane().remove( (Component) c);
      }
    });
  }

  protected void setBounds(Rectangle r) {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    myFrame.setBounds(r);
  }

  protected Rectangle getBounds() {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    return myFrame.getBounds();
  }

  protected void setIcon(ImageIcon ic) {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    if (ic == null) {
      return;
    }
    myFrame.setIconImage(ic);
  }

  protected void setTitle(String s) {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    myFrame.setTitle(s);
  }

  public void setContainerLayout(Object layout) {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    myFrame.getContentPane().setLayout( (LayoutManager) layout);
  }

  private ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }

  public void setComponentValue(String name, Object object) {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    if (name.equals("fullscreen")) {
      fullscreen = ( (Boolean) object).booleanValue();
    }
    if (name.equals("visible")) {
      visible = ( (Boolean) object).booleanValue();
    }
    if ("icon".equals(name)) {
        if (object instanceof URL) {
            setIcon(getIcon( (URL) object));
        } else {
            System.err.println("Warning setting icon of tipiframe:");
        }
    }
    if ("title".equals(name)) {
      this.setTitle( (String) object);
    }
    if (name.equals("x")) {
      x = ( (Integer) object).intValue();
    }
    if (name.equals("y")) {
      y = ( (Integer) object).intValue();
    }
    if (name.equals("w")) {
      w = ( (Integer) object).intValue();
    }
    if (name.equals("h")) {
      h = ( (Integer) object).intValue();
    }
    if (name.equals("menubar")) {
      throw new UnsupportedOperationException("Dont use the menubar attribute. Just add a menubar to the component");
//      try {
//        if (object == null || object.equals("")) {
//          System.err.println("null menu bar. Not instantiating");
//          return;
//        }
//        myMenuBar = (String) object;
//        XMLElement instance = new CaseSensitiveXMLElement();
//        instance.setName("component-instance");
//        instance.setAttribute("name", (String) object);
//        instance.setAttribute("id", (String) object);
//        TipiComponent tm = this.addComponentInstance(myContext, instance, null);
//        setJMenuBar( (JMenuBar) tm.getContainer());
//      }
//      catch (TipiException ex) {
//        ex.printStackTrace();
//        setJMenuBar(null);
//        myMenuBar = "";
//      }
    }
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    if ("visible".equals(name)) {
      return new Boolean(myFrame.isVisible());
    }
    Rectangle r = myFrame.getBounds();
    if (name.equals("x")) {
      return new Integer(r.x);
    }
    if (name.equals("y")) {
      return new Integer(r.y);
    }
    if (name.equals("w")) {
      return new Integer(r.width);
    }
    if (name.equals("h")) {
      return new Integer(r.height);
    }
    if (name.equals("resizable")) {
      return new Boolean(myFrame.isResizable());
    }

    if (name.equals("fullscreen")) {
      new Boolean(JFrame.MAXIMIZED_BOTH == myFrame.getExtendedState());
    }

    if (name.equals("title")) {
      return myFrame.getTitle();
    }

    return super.getComponentValue(name);
  }

  protected void setJMenuBar(JMenuBar s) {
    final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
    ( (JFrame) myFrame).setJMenuBar(s);
  }

  /**
   * componentInstantiated
   *
   * @todo Implement this com.dexels.navajo.tipi.TipiComponent method
   */
  public void componentInstantiated() {
    runSyncInEventThread(new Runnable() {
      public void run() {
        setBounds(new Rectangle(x, y, w, h));
        if (fullscreen) {
          ( (TipiSwingFrame) getSwingContainer()).setExtendedState(JFrame.
              MAXIMIZED_BOTH);
        }
        getSwingContainer().setVisible(visible);
      }
    });
  }
}
