package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.components.*;
import javax.swing.event.*;
import java.awt.event.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DefaultTipiDialog
    extends DefaultTipiRootPane {
  private boolean disposed = false;
  private JDialog myDialog = null;

  private boolean modal = false;
  private boolean decorated = true;
  private boolean showing = false;
  private String title = "";
  private JMenuBar myBar = null;
  private Rectangle myBounds = new Rectangle(0,0,0,0);

  public DefaultTipiDialog() {
  }

  public Container createContainer() {
    TipiSwingPanel tp = new TipiSwingPanel(this);
    return tp;
  }

//  }
  private void dialog_windowClosing(WindowEvent e) {
    System.err.println("Window closing called!");
    JDialog d = (JDialog) e.getSource();
    try {
      performTipiEvent("onWindowClosed", e);
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

  public LayoutManager getContainerLayout() {
    /**@todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method*/
    return getContainer().getLayout();
  }

  public void setContainerLayout(LayoutManager layout) {
    getContainer().setLayout(layout);
  }

  public void addToContainer(Component c, Object constraints) {
       getContainer().add(c, constraints);
  }

  public void removeFromContainer(Component c) {
      getContainer().remove(c);
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("modal")) {
//      ( (JDialog) getContainer()).setModal( ( (Boolean) object).booleanValue());
      modal = ( (Boolean) object).booleanValue();
    }
//    if (name.equals("background")) {
//      ( (JDialog) getContainer()).getContentPane().setBackground( (Color) object);
//    }
    if (name.equals("decorated")) {
//      ( (JDialog) getContainer()).setUndecorated(! ( (Boolean) object).booleanValue());
      decorated = ( (Boolean) object).booleanValue();
    }
    if (name.equals("title")) {
      title = object.toString();
    }

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
    return super.getComponentValue(name);
  }

  protected void setJMenuBar(JMenuBar s) {
    myBar = s;
    if (myDialog!=null) {
      myDialog.setJMenuBar(s);
    }
    }

  protected void setTitle(String s) {
//    ( (JDialog) getContainer()).setTitle(s);
    title = s;
    if (myDialog!=null) {
      myDialog.setTitle(s);
    }

  }

  protected void setIcon(ImageIcon im) {
    System.err.println("setIcon for dialog ignored!");
  }

  protected void setBounds(Rectangle r) {
    myBounds = r;
     if (myDialog!=null) {
       myDialog.setBounds(r);
     }
  }

  protected Rectangle getBounds() {
    return myBounds;
  }

  public void setVisible(boolean b) {
    if (b) {
      ( (JDialog) getContainer()).setVisible(b);
    }
//    ((JDialog)getContainer()).set
  }

  public void disposeComponent() {
    getContainer().setVisible(false);
    super.disposeComponent();
  }

  private void constructDialog() {
    RootPaneContainer r = getContext().getTopLevel();
//    JDialog d = null;
    if (r==null) {
      System.err.println("Null root. Bad, bad, bad.");
      myDialog = new JDialog(new JFrame());
    } else {

      if (Frame.class.isInstance(r)) {
        System.err.println("Creating with frame root");
        myDialog = new JDialog( (Frame) r);
      }
      else {
        System.err.println("Creating with dialog root. This is quite surpising, actually.");
        myDialog = new JDialog( (Dialog) r);
      }
    }
    createWindowListener(myDialog);
    myDialog.setTitle(title);
    if (myBounds!=null) {
      myDialog.setBounds(myBounds);
    }
    if (myBar!=null) {
      myDialog.setJMenuBar(myBar);
    }
    myDialog.setModal(modal);
    myDialog.setUndecorated(!decorated);
    myDialog.getContentPane().setLayout(new BorderLayout());
    myDialog.getContentPane().add(getContainer(),BorderLayout.CENTER);
  }

  protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth) {
    super.performComponentMethod(name, compMeth);
    if (name.equals("show")) {
      // If modal IT WILL BLOCK HERE
      if (myDialog==null) {
        constructDialog();
      }

     myDialog.setLocationRelativeTo( (Component) myContext.getTopLevel());
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          myDialog.setVisible(true);
        }
      });
      // Any code beyond this point will be executed after the dialog has been closed.
    }
    if (name.equals("hide")) {
      myDialog.setVisible(false);
//      System.err.println("Hide dialog: Disposing dialog!");
//       disposeComponent();
//       TipiContext.getInstance().disposeTipi(this);
    }
    if (name.equals("dispose")) {
      System.err.println("Hide dialog: Disposing dialog!");
      myContext.disposeTipiComponent(this);
      disposed = true;
    }
  }

  public void setContainerVisible(boolean b) {
    // do nothing
//    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
//    super.setContainerVisible(b);
  }
//  public void loadData(Navajo n, TipiContext tc) throws com.dexels.navajo.tipi.TipiException {
//    /**@todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method*/
//    super.loadData(n,tc);
//    ((JDialog)getContainer()).pack();
//  }
}