package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
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
  private String myMenuBar = "";
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

  public Object getContainerLayout() {
    /**@todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method*/
    return getSwingContainer().getLayout();
  }

  public void setContainerLayout(Object layout) {
    myDialog.getContentPane().setLayout( (LayoutManager) layout);
  }

  public void addToContainer(Object c, Object constraints) {
    getSwingContainer().add( (Component) c, constraints);
  }

  public void removeFromContainer(Object c) {
    getSwingContainer().remove( (Component) c);
  }

  public void setComponentValue(String name, Object object) {
//    System.err.println("DIALOG SETCALUE: "+name+" value: "+object);
    if (name.equals("modal")) {
//      ( (JDialog) getContainer()).setModal( ( (Boolean) object).booleanValue());
      modal = ( (Boolean) object).booleanValue();
      return;
    }
//    if (name.equals("background")) {
//      ( (JDialog) getContainer()).getContentPane().setBackground( (Color) object);
//    }
    if (name.equals("decorated")) {
//      ( (JDialog) getContainer()).setUndecorated(! ( (Boolean) object).booleanValue());
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
    if (name.equals("menubar")) {
      try {
        if (object == null || object.equals("")) {
          System.err.println("null menu bar. Not instantiating");
          return;
        }
        myMenuBar = (String) object;
        XMLElement instance = new CaseSensitiveXMLElement();
        instance.setName("component-instance");
        instance.setAttribute("name", (String) object);
        instance.setAttribute("id", (String) object);
//        TipiComponent tm = myContext.instantiateComponent(instance);
        TipiComponent tm = addComponentInstance(myContext, instance, null);
        setJMenuBar( (JMenuBar) tm.getContainer());
      }
      catch (TipiException ex) {
        ex.printStackTrace();
        setJMenuBar(null);
        myMenuBar = "";
      }
    }
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

  protected void setJMenuBar(JMenuBar s) {
    myBar = s;
    if (myDialog != null) {
      myDialog.setJMenuBar(s);
    }
  }

  protected void setTitle(String s) {
//    ( (JDialog) getContainer()).setTitle(s);
    title = s;
    if (myDialog != null) {
      myDialog.setTitle(s);
    }
  }

  protected void setIcon(ImageIcon im) {
    System.err.println("setIcon for dialog ignored!");
  }

  protected void setBounds(Rectangle r) {
    myBounds = r;
    if (myDialog != null) {
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
    if (myDialog != null) {
      myDialog.setVisible(false);
    }
    super.disposeComponent();
  }

  private void constructDialog() {
    RootPaneContainer r = getContext().getTopLevel();
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
    super.performComponentMethod(name, compMeth);
    if (name.equals("show")) {
      if (myDialog == null) {
        constructDialog();
      }
      myDialog.setLocationRelativeTo( (Component) myContext.getTopLevel());
      System.err.println("Current bounds :" + myBounds);
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
//       myContext.disposeTipi(this);
    }
    if (name.equals("dispose")) {
      System.err.println("Hide dialog: Disposing dialog!");
      myDialog.setVisible(false);
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