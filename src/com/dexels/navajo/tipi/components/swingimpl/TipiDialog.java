package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiDialog
    extends TipiSwingDataComponentImpl {
  private boolean disposed = false;
  private JDialog myDialog = null;
  private boolean modal = false;
  private boolean decorated = true;
  private boolean showing = false;
  private String title = "";
  private JMenuBar myBar = null;
  private Rectangle myBounds = new Rectangle(0, 0, 0, 0);
  public TipiDialog() {
  }

  public Object createContainer() {
    TipiSwingPanel tp = new TipiSwingPanel(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return tp;
  }

//  }
  private void dialog_windowClosing(WindowEvent e) {
    JDialog d = (JDialog) e.getSource();
    try {
      performTipiEvent("onWindowClosed", e, true);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
    myContext.disposeTipiComponent(this);
    disposed = true;
  }

  protected void createWindowListener(JDialog d) {
    d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    d.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dialog_windowClosing(e);
      }

      public void windowClosed(WindowEvent e) {
        dialog_windowClosing(e);
      }
    });
  }

//  public void removeFromContainer(Object c) {
//    getSwingContainer().remove( (Component) c);
//  }
  public void setComponentValue(final String name, final Object object) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        if (name.equals("modal")) {
          modal = ( (Boolean) object).booleanValue();
          return;
        }
        if (name.equals("decorated")) {
          decorated = ( (Boolean) object).booleanValue();
          return;
        }
        if (name.equals("title")) {
          title = object.toString();
          return;
        }
        if (name.equals("x")) {
          myBounds.x = ( (Integer) object).intValue();
          return;
        }
        if (name.equals("y")) {
          myBounds.y = ( (Integer) object).intValue();
          return;
        }
        if (name.equals("w")) {
          myBounds.width = ( (Integer) object).intValue();
          return;
        }
        if (name.equals("h")) {
          myBounds.height = ( (Integer) object).intValue();
          return;
        }
      }
    });
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method*/
    if ("isShowing".equals(name)) {
//      return new Boolean( ( (JDialog) getContainer()).isVisible());
      return new Boolean(showing);
    }
    if ("title".equals(name)) {
//      return ( (JDialog) getContainer()).getTitle();
      return title;
    }
    if (name.equals("x")) {
      return new Integer(myBounds.x);
    }
    if (name.equals("y")) {
      return new Integer(myBounds.y);
    }
    if (name.equals("w")) {
      return new Integer(myBounds.width);
    }
    if (name.equals("h")) {
      return new Integer(myBounds.height);
    }
    return super.getComponentValue(name);
  }
  public void disposeComponent() {
    if (myDialog != null) {
      myDialog.setVisible(false);
    }
    super.disposeComponent();
  }

  private void constructDialog() {
    RootPaneContainer r = (RootPaneContainer) getContext().getTopLevel();
//    JDialog d = null;
    if (r == null) {
      System.err.println("Null root. Bad, bad, bad.");
      myDialog = new JDialog(new JFrame());
    }
    else {
      if (Frame.class.isInstance(r)) {
        System.err.println("Creating with frame root");
        myDialog = new JDialog( (Frame) r);
      }
      else {
        System.err.println("Creating with dialog root. This is quite surpising, actually.");
        myDialog = new JDialog( (Dialog) r);
      }
    }
    myDialog.setUndecorated(!decorated);
    createWindowListener(myDialog);
    myDialog.setTitle(title);
    myDialog.pack();
    if (myBounds != null) {
      myDialog.setBounds(myBounds);
    }
    if (myBar != null) {
      myDialog.setJMenuBar(myBar);
    }
    myDialog.setModal(modal);
    myDialog.getContentPane().setLayout(new BorderLayout());
    myDialog.getContentPane().add(getSwingContainer(), BorderLayout.CENTER);
  }

  protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth) {
    final TipiComponent me = this;
    super.performComponentMethod(name, compMeth);
    if (name.equals("show")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          if (myDialog == null) {
            constructDialog();
          }
          myDialog.setLocationRelativeTo( (Component) myContext.getTopLevel());
        }
      });
      runASyncInEventThread(new Runnable() {
        public void run() {
          myDialog.setVisible(true);
        }
      });

      runASyncInEventThread(new Runnable() {
        public void run() {
          myDialog.toFront();
        }
      });


    }
    if (name.equals("hide")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          System.err.println("Hiding dialog!!!\n\n\n\n");
          myDialog.setVisible(false);
        }
      });
    }
    if (name.equals("dispose")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          System.err.println("Hide dialog: Disposing dialog!");
          myDialog.setVisible(false);
          myContext.disposeTipiComponent(me);
          disposed = true;
        }
      });
    }
  }

//  public void setContainerVisible(boolean b) {
//  }
  public void reUse() {
  }
}
