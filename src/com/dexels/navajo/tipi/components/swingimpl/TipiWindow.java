package com.dexels.navajo.tipi.components.swingimpl;

import java.beans.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/** @todo Need to refactor menus in internalframes. Now still uses the old mode Frank */
public class TipiWindow
//    extends DefaultTipi {
    extends TipiSwingDataComponentImpl {
  private TipiSwingWindow myWindow;
  private String myMenuBar = "";
  private String myTitle;
  public Object createContainer() {
    myWindow = new TipiSwingWindow(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    myWindow.addInternalFrameListener(new InternalFrameAdapter() {
      public void internalFrameClosing(InternalFrameEvent l) {
        myWindow_internalFrameClosed(l);
      }
    });
    myWindow.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    return myWindow;
  }

  public Object getComponentValue(String name) {
    if ("visible".equals(name)) {
      return new Boolean(myWindow.isVisible());
    }
//    if("title".equals(name)){
//      return myWindow.getTitle();
//    }
    Rectangle r = myWindow.getBounds();
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
    if (name.equals("iconifiable")) {
      return new Boolean(myWindow.isIconifiable());
    }
    if (name.equals("maximizable")) {
      return new Boolean(myWindow.isMaximizable());
    }
    if (name.equals("closable")) {
      return new Boolean(myWindow.isClosable());
    }
    if (name.equals("resizable")) {
      return new Boolean(myWindow.isResizable());
    }
    if (name.equals("title")) {
      return myWindow.getTitle();
     }

    return super.getComponentValue(name);
  }

  private void myWindow_internalFrameClosed(InternalFrameEvent l) {
//    myContext.disposeTipi(this);
  }

  public void addToContainer(final Object c, final Object constraints) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (JInternalFrame) getContainer()).getContentPane().add( (Component) c, constraints);
        ((SwingTipiContext)myContext).addTopLevel(c);
      }
    });
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (JInternalFrame) getContainer()).getContentPane().remove( (Component) c);
        ((SwingTipiContext)myContext).removeTopLevel(c);
      }
    });
  }

  public void setContainerLayout(final Object layout) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (JInternalFrame) getContainer()).getContentPane().setLayout( (LayoutManager) layout);
      }
    });
  }

  public void setComponentValue(final String name, final Object object) {
    super.setComponentValue(name, object);
    final JInternalFrame jj = (JInternalFrame) getContainer();
    runSyncInEventThread(new Runnable() {
      public void run() {
        if (name.equals("iconifiable")) {
          boolean b = ( (Boolean) object).booleanValue();
          jj.setIconifiable(b);
        }
        if (name.equals("background")) {
          jj.setBackground( (Color) object);
        }
        if (name.equals("maximizable")) {
          boolean b = ( (Boolean) object).booleanValue();
          jj.setMaximizable(b);
        }
        if (name.equals("closable")) {
          boolean b = ( (Boolean) object).booleanValue();
          jj.setClosable(b);
        }
        if (name.equals("resizable")) {
          boolean b = ( (Boolean) object).booleanValue();
          jj.setResizable(b);
        }
        if (name.equals("selected")) {
          boolean b = ( (Boolean) object).booleanValue();
          try {
            jj.setSelected(b);
          }
          catch (PropertyVetoException ex) {
            System.err.println("Tried to select a window, but someone did not agree");
            ex.printStackTrace();
          }
          if (name.equals("visible")) {
            jj.invalidate();
            jj.setVisible( ( (Boolean) object).booleanValue());
          }
        }
        final Rectangle r = getBounds();
/*        if (name.equals("menubar")) {
          try {
            if (object == null | object.equals("")) {
              System.err.println("null menu bar. Not instantiating");
              return;
            }
            myMenuBar = (String) object;
            XMLElement instance = new CaseSensitiveXMLElement();
            instance.setName("component-instance");
            instance.setAttribute("name", (String) object);
            instance.setAttribute("id", (String) object);
            //        TipiComponent tm = myContext.instantiateComponent(instance);
            final TipiComponent tm = addComponentInstance(myContext, instance, null);
            setJMenuBar( (JMenuBar) tm.getContainer());
          }
          catch (TipiException ex) {
            ex.printStackTrace();
            setJMenuBar(null);
            myMenuBar = "";
          }
        }
*/
      if (name.equals("x")) {
          r.x = ( (Integer) object).intValue();
        }
        if (name.equals("y")) {
          r.y = ( (Integer) object).intValue();
        }
        if (name.equals("w")) {
          r.width = ( (Integer) object).intValue();
        }
        if (name.equals("h")) {
          r.height = ( (Integer) object).intValue();
        }
        if (name.equals("title")) {
          myTitle = object.toString();
          setTitle(myTitle);
        }
        if (name.equals("icon")) {
          setIcon(getIcon( (URL) object));
        }
//        System.err.println("Setting bounds to: "+r);
        setBounds(r);
      }
    });
  }

  private ImageIcon getIcon(final URL u) {
    return new ImageIcon(u);
  }

  protected void setTitle(final String s) {
    myWindow.setTitle(s);
  }

  protected void setBounds(final Rectangle r) {
    myWindow.setBounds(r);
  }

  protected Rectangle getBounds() {
    return myWindow.getBounds();
  }

  protected void setIcon(final ImageIcon ic) {
    myWindow.setFrameIcon(ic);
  }

  protected void setJMenuBar(JMenuBar ic) {
    myWindow.setJMenuBar(ic);
  }

  private void doPerformMethod(String name, TipiComponentMethod compMeth) {
    if (name.equals("iconify")) {
      try {
//        myWindow.setIcon(true);
        JInternalFrame jj = (JInternalFrame) getContainer();
        TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
        jj.setIcon(true);
        tt.getDesktopManager().iconifyFrame(jj);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if (name.equals("maximize")) {
//            System.err.println("\n\nMaximizing\n\n");
//            Thread.dumpStack();
      JInternalFrame jj = (JInternalFrame) getContainer();
      if (jj.isMaximum()) {
        System.err.println("Ignoring: Nothing to maximize");
        return;
      }
//      jj.setMaximizable(true);
      try {
        TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
        jj.setMaximum(true);
        tt.getDesktopManager().maximizeFrame(jj);
      }
      catch (Error ex1) {
        ex1.printStackTrace();
      }
      catch (Exception ex1) {
        ex1.printStackTrace();
      }
    }
    if (name.equals("restore")) {
      JInternalFrame jj = (JInternalFrame) getContainer();
      if (!jj.isMaximum()) {
        System.err.println("Ignoring: Nothing to restore");
        return;
      }
//      System.err.println("\n\nRestoring\n\n");
      TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
      tt.getDesktopManager().minimizeFrame(jj);
      try {
        jj.setMaximum(false);
//        // This might give an exception.. don't worry.. can't help it.
      }
      catch (PropertyVetoException ex1) {
        ex1.printStackTrace();
      }
    }
    if (name.equals("toFront")) {
      JInternalFrame jj = (JInternalFrame) getContainer();
      TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
      jj.toFront();
      tt.getDesktopManager().activateFrame(jj);
    }
  }

  protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        doPerformMethod(name, compMeth);
      }
    });
  }

  public boolean isReusable() {
    return true;
  }

  public void reUse() {
//    if (myParent!=null) {
//      myParent.addToContainer();
//    }
//    myWindow.setVisible(true);
  }
}
