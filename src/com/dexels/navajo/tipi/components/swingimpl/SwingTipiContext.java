package com.dexels.navajo.tipi.components.swingimpl;

import java.lang.reflect.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import java.util.*;
import java.io.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.swingclient.*;
import javax.swing.text.*;
import java.text.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class SwingTipiContext
    extends TipiContext {
  private TipiSwingSplash splash;

  private final Set threadSet = Collections.synchronizedSet(new HashSet());
  private final Set dialogThreadSet = Collections.synchronizedSet(new HashSet());
  private boolean dialogShowing = false;

  private JDialog blockingDialog;

  private final SwingTipiUserInterface myUserInterface;
  private boolean debugMode = false;

  public SwingTipiContext() {
    myUserInterface = new SwingTipiUserInterface(this);
    SwingClient.setUserInterface(myUserInterface);
  }

//  public void setWaitCursor(TipiSwingComponent tc, boolean b) {
//    Container cc =  (Container) tc.getContainer();
//    if (cc!=null) {
//      (cc).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
//    }
//    for (int i = 0; i < tc.getChildCount(); i++) {
//      TipiComponent current = tc.getTipiComponent(i);
//      if (TipiSwingComponent.class.isInstance(current)) {
//        setWaitCursor((TipiSwingComponent)current,b);
//
//      }
//    }
//  }
//

  public synchronized void setWaiting(boolean b) {

    if (dialogShowing) {
      b = false;
    }
//    if (rootPaneList.size()>0) {
//      Object obj = rootPaneList.get(0);
//      if (blockingDialog==null) {
//        TipiSwingComponent tsc = (TipiSwingComponent)obj;
//        Frame f = (Frame)tsc.getContainer();
//        blockingDialog = new JDialog(f,"Please wait...",true);
//      }
//      final boolean f = b;
//      if (blockingDialog!=null) {
//        SwingUtilities.invokeLater(new Runnable() {
//          public void run() {
//            blockingDialog.setVisible(f);
//          }
//        });
//      }
//    }
    for (int i = 0; i < rootPaneList.size(); i++) {
      Object obj = rootPaneList.get(i);
      if (TipiSwingComponent.class.isInstance(obj)) {
        TipiSwingComponent tc = (TipiSwingComponent)obj;
        tc.setWaitCursor(b);
      } else {
        ( (Container) obj).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
      }
    }
    for (int j = 0; j < myActivityListeners.size(); j++) {
      TipiActivityListener tal = (TipiActivityListener) myActivityListeners.get(j);
      tal.setActive(b);
    }
  }

  public void clearTopScreen() {
    ( (TipiScreen) getDefaultTopLevel()).clearTopScreen();
  }

  public void setSplashInfo(final String info) {
//    System.err.println("Setting splash: "+info);
    if (splash != null) {
//      try {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            splash.setInfoText(info);
          }
        });
//      }
//      catch (InvocationTargetException ex) {
//        ex.printStackTrace();
//      }
//      catch (InterruptedException ex) {
//        ex.printStackTrace();
//      }
    }
  }

  public void setSplashVisible(boolean b) {
    if (splash != null) {
      splash.setVisible(b);
    }
  }

  public void setSplash(Object s) {
    splash = (TipiSwingSplash) s;
  }
  public void threadStarted(Thread workThread) {
    if (threadSet==null) {
      return;
    }
    super.threadStarted(workThread);
    threadSet.add(workThread);
//    System.err.println(":::: THREAD START: "+threadSet.size());
    setActiveThreads(threadSet.size());
    if (!threadSet.isEmpty()) {
          setWaiting(true);
    }
  }

  public final void threadEnded(Thread workThread) {
    super.threadEnded(workThread);

    if (threadSet==null) {
      return;
    }
    threadSet.remove(workThread);
    setActiveThreads(threadSet.size());
    if (threadSet.isEmpty()) {
      setWaiting(false);
    }
  }

  public final void updateWaiting() {
    if (threadSet==null || threadSet.size()==0) {
      System.err.println("No waiting threads");
      setWaiting(false);
      return;
    }
    //System.err.println("dialog: "+dialogThreadSet);
    //System.err.println("set: "+threadSet);
    setWaiting(!dialogThreadSet.containsAll(threadSet));
  }


  public void dialogShowing(boolean b) {
    dialogShowing = b;
    if (!SwingUtilities.isEventDispatchThread()) {
      if (b) {
        dialogThreadSet.add(Thread.currentThread());
      }
      else {
        dialogThreadSet.remove(Thread.currentThread());
      }
      dialogShowing = b;
    } else {
      dialogShowing = false;
    }
    updateWaiting();
  }

//  protected void instantiateStudio() throws TipiException {
//    XMLElement xe = new CaseSensitiveXMLElement();
//    xe.setName("tipi-instance");
//    xe.setAttribute("name","studio");
//    xe.setAttribute("id","studio");
//    xe.setAttribute("studioelement","true");
//    TipiComponentImpl tc = (TipiComponentImpl)instantiateComponent(xe);
//
//    setStudioScreenPath("/studio/split1/split2/tabs/designer/desktop");
//    ( (TipiComponent) getDefaultTopLevel()).addComponent(tc, this, null);
//    ( (TipiScreen) getDefaultTopLevel()).addStudio((Window)tc.getContainer(), null);
//  }

  public void addTopLevel(Object toplevel) {
    rootPaneList.add(toplevel);
  }

  public void removeTopLevel(Object toplevel) {
    rootPaneList.remove(toplevel);
  }



  protected void clearLogFile() {
    File f = new File(System.getProperty("user.home") + "/.tipidebug");
    System.err.println("Deleting: "+f.getAbsolutePath());
    f.delete();
  }

  public void debugLog(String category, String event) {
    if (!debugMode) {
      return;
    }
    long stamp = System.currentTimeMillis()-startTime;
    SimpleDateFormat inputFormat1 = new SimpleDateFormat("HH:mm:ss S");

//    Calendar c = Calendar.getInstance();
    Date d = new Date(stamp);
//    c.setTimeInMillis(stamp);
    try {
      FileWriter fw = new FileWriter(System.getProperty("user.home") + "/.tipidebug", true);
      fw.write(category + ", " + inputFormat1.format(d) + ", " + " Thread: " + Thread.currentThread().getName() + "," + event+"\n");
      fw.flush();
      fw.close();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void setDebugMode(boolean b) {
    debugMode = b;
    System.err.println("Debugmode = "+b);
    clearLogFile();
  }

}
