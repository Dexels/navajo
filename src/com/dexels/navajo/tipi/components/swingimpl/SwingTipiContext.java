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

  public SwingTipiContext() {
    setDefaultTopLevel(new TipiScreen());
    getDefaultTopLevel().setContext(this);
  }

  public synchronized void setWaiting(boolean b) {
    System.err.println(">> SETWAITING: "+b+" <<");
//    Thread.dumpStack();
    for (int i = 0; i < rootPaneList.size(); i++) {
      TipiComponent tc = (TipiComponent) rootPaneList.get(i);
      ( (Container) tc.getContainer()).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
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
    if (splash != null) {
      try {
        SwingUtilities.invokeAndWait(new Runnable() {
          public void run() {
            splash.setInfoText(info);
          }
        });
      }
      catch (InvocationTargetException ex) {
        ex.printStackTrace();
      }
      catch (InterruptedException ex) {
        ex.printStackTrace();
      }
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
    System.err.println(":::: THREAD START: "+threadSet.size());
    setActiveThreads(threadSet.size());
    if (!threadSet.isEmpty()) {
          setWaiting(true);
    }
  }

  public void threadEnded(Thread workThread) {
    super.threadEnded(workThread);

    if (threadSet==null) {
      return;
    }
    threadSet.remove(workThread);
    System.err.println(":::: THREAD ENDED: "+threadSet.size());
    setActiveThreads(threadSet.size());
    if (threadSet.isEmpty()) {
      setWaiting(false);
    }
  }
  public void createStartupFile(File startupDir, ArrayList jarList) throws IOException {
    File runFile = new File(startupDir,"run.bat");
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < jarList.size(); i++) {
      String current = (String)jarList.get(i);
      sb.append("lib/"+current+";");
    }
   FileWriter fw = new FileWriter(runFile);
   fw.write("java -cp "+sb.toString()+" tipi.MainApplication tipi/start.xml");
   fw.close();
  }

  protected void instantiateStudio() throws TipiException {
//    System.err.println("Instantiating COMPONENT\n");
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("tipi-instance");
    xe.setAttribute("name","studio");
    xe.setAttribute("studioelement","true");
    TipiComponentImpl tc = (TipiComponentImpl)instantiateComponent(xe);

    setStudioScreenPath("/studio/split1/split2/tabs/designer/desktop");

//    tc.replaceContainer(null);
//    tc.setStudioElement(true);
//    System.err.println("Reiniting");
//    tc.initContainer();
    ( (TipiComponent) getDefaultTopLevel()).addComponent(tc, this, null);
    ( (TipiScreen) getDefaultTopLevel()).addStudio((Window)tc.getContainer(), null);
  }


}
