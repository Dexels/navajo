package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.swingclient.*;

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
  private boolean studioMode = false;
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
  private final void dialog_windowClosing(WindowEvent e) {
    JDialog d = (JDialog) e.getSource();
    try {
      performTipiEvent("onWindowClosed", null, true);
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

  private final void constructDialog() {
    //System.err.println("Constructing: studio? "+isStudioElement());
    if (myContext.isStudioMode() && !isStudioElement()) {
      //
      System.err.println("studio");
      studioMode = true;
    }
    else {
      constructStandardDialog();
      studioMode = false;
    }
  }

  private final void constructStandardDialog() {
    Object rootObject =  getContext().getTopLevel();
    RootPaneContainer r = null;
//    JDialog d = null;
    if (rootObject == null) {
      System.err.println("Null root. Bad, bad, bad.");
      myDialog = new JDialog(new JFrame());
    }
    else {
      if (rootObject instanceof RootPaneContainer ) {
        r = (RootPaneContainer)rootObject;
        if (Frame.class.isInstance(r)) {
//        System.err.println("Creating with frame root");
          myDialog = new JDialog( (Frame) r);
        }
        else {
          System.err.println("Creating with dialog root. This is quite surpising, actually.");
          myDialog = new JDialog( (Dialog) r);
        }
      }
      else {
        System.err.println("R is strange... a: "+rootObject.getClass());
        myDialog = new JDialog( ((SwingTipiContext)myContext).getUserInterface().getMainFrame());
      }
    }
    myDialog.setUndecorated(!decorated);
    createWindowListener(myDialog);
    myDialog.setTitle(title);
    myDialog.toFront();
     if (myBar != null) {
      myDialog.setJMenuBar(myBar);
    }
    myDialog.setModal(modal);
    myDialog.getContentPane().setLayout(new BorderLayout());
    myDialog.getContentPane().add(getSwingContainer(), BorderLayout.CENTER);
    myDialog.pack();
    // always the case
    final Rectangle bnds = getDialogBounds();
    if (bnds != null) {
      myDialog.setBounds(bnds);
      System.err.println("Setting bounds: "+bnds);
    } else {
      System.err.println("Null bounds for dialog.");
    }

    myDialog.setLocationRelativeTo( (Component) myContext.getTopLevel());
  }

  private synchronized Rectangle getDialogBounds() {
      return myBounds;
  }
  protected void helperRegisterEvent(TipiEvent te) {
    if (te != null && te.getEventName().equals("onWindowClosed")) {
      //overridden.. should be ok.
    }
    else {
      super.helperRegisterEvent(te);
    }
  }

  protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
    final TipiComponent me = this;
    final Thread currentThread = Thread.currentThread();
    final boolean amIEventThread = SwingUtilities.isEventDispatchThread();
    super.performComponentMethod(name, compMeth, event);
    if (name.equals("show")) {
      runASyncInEventThread(new Runnable() {
        public void run() {
          if (myDialog == null) {
            constructDialog();
          }
          if (myDialog != null) {
             ( (SwingTipiContext) myContext).addTopLevel(myDialog.getContentPane()); ( (SwingTipiContext) myContext).dialogShowing(true); ( (SwingTipiContext) myContext).
                updateWaiting();
            SwingClient.getUserInterface().showCenteredDialog(myDialog);
            final Rectangle bnds = getDialogBounds();
            if (bnds!=null) {
                // JUST TRY TO SET IT.
                try {
                    myDialog.setSize(bnds.width,bnds.height);
                } catch(Throwable t) {
                    t.printStackTrace();
                }
            }
            //            myDialog.setVisible(true);
            if (myContext != null) {
               ( (SwingTipiContext) myContext).dialogShowing(false); if (myDialog != null) {
                 ( (SwingTipiContext) myContext).removeTopLevel(myDialog.getContentPane()); ( (SwingTipiContext) myContext).updateWaiting();
//              myDialog.toFront();
              }
              else {
                System.err.println("Null DIALOG, in TipiDialog.");
              }
            }
            else {
              System.err.println("Null CONTEXT, in TipiDialog.");
            }
          }
        }
      });
    }
    if (name.equals("hide")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
//          System.err.println("Hiding dialog!!!\n\n\n\n");
          if (myDialog != null) {
            myDialog.setVisible(false);
          }
        }
      });
    }
    if (name.equals("dispose")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
//          System.err.println("Hide dialog: Disposing dialog!");
          if (myDialog != null) {
            myDialog.setVisible(false);
            myDialog = null;
          }
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
