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
  private boolean dialogShowing = false;

  public SwingTipiContext() {
    setDefaultTopLevel(new TipiScreen());
    getDefaultTopLevel().setContext(this);
  }

  public synchronized void setWaiting(boolean b) {
//    System.err.println(">> SETWAITING: "+b+" <<");
//    System.err.println("Dialog? "+dialogShowing);
//    Thread.dumpStack();
//    System.err.println("# in threadSet: "+threadSet.size());
    if (dialogShowing) {
      b = false;
    }
    for (int i = 0; i < rootPaneList.size(); i++) {
      Object obj = rootPaneList.get(i);
      if (TipiComponent.class.isInstance(obj)) {
        TipiComponent tc = (TipiComponent)obj;
       ( (Container) tc.getContainer()).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
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
//    System.err.println(":::: THREAD START: "+threadSet.size());
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
    setActiveThreads(threadSet.size());
    if (threadSet.isEmpty()) {
      setWaiting(false);
    }
  }

  public void updateWaiting() {
    setWaiting(threadSet!=null && threadSet.size()>0);
  }


  public void dialogShowing(boolean b) {
    dialogShowing = b;
    updateWaiting();
  }
  public void createStartupFile(File startupDir, Set jarSet, XMLElement project) throws IOException {
    createWindowsStartupFile(startupDir, jarSet, project);
    createLinuxStartupFile(startupDir, jarSet, project);
  }

  private void createWindowsStartupFile(File startupDir, Set jarSet,
                                        XMLElement project) throws IOException {
     File runFile = new File(startupDir,"run.bat");
     StringBuffer sb = new StringBuffer();

//    XMLElement project = new CaseSensitiveXMLElement();
//    FileReader fr = new FileReader(projectFile);
//    project.parseFromReader(fr);
//    fr.close();
     String javaParams = project.getStringAttribute("java-params","");
     String applicationParams = project.getStringAttribute("application-params","");

     for (Iterator it = jarSet.iterator(); it.hasNext(); ) {
       String current = (String)it.next();
       sb.append("lib/"+current+(it.hasNext()?";":""));
     }
     sb.append(";"+getProjectResourceDir());
    FileWriter fw = new FileWriter(runFile);
    fw.write("java "+javaParams+" -cp .;"+sb.toString()+" tipi.MainApplication tipi/start.xml "+applicationParams);
    fw.close();
  }


  private void createLinuxStartupFile(File startupDir, Set jarSet,
                                        XMLElement project) throws IOException {
     File runFile = new File(startupDir,"run.sh");
     StringBuffer sb = new StringBuffer();

//    XMLElement project = new CaseSensitiveXMLElement();
//    FileReader fr = new FileReader(projectFile);
//    project.parseFromReader(fr);
//    fr.close();
     String javaParams = project.getStringAttribute("java-params","");
     String applicationParams = project.getStringAttribute("application-params","");

     for (Iterator it = jarSet.iterator(); it.hasNext(); ) {
       String current = (String)it.next();
       sb.append("lib/"+current+(it.hasNext()?":":""));
     }
     sb.append(":"+getProjectResourceDir());
    FileWriter fw = new FileWriter(runFile);
    fw.write("java "+javaParams+" -cp .:"+sb.toString()+" tipi.MainApplication tipi/start.xml "+applicationParams);
    fw.close();
  }


  public String getProjectResourceDir() {
    return "./resource";
  }



  protected void instantiateStudio() throws TipiException {
//    System.err.println("Instantiating COMPONENT\n");
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("tipi-instance");
    xe.setAttribute("name","studio");
    xe.setAttribute("id","studio");
    xe.setAttribute("studioelement","true");
    TipiComponentImpl tc = (TipiComponentImpl)instantiateComponent(xe);

    setStudioScreenPath("/studio/split1/split2/tabs/designer/desktop");
    ( (TipiComponent) getDefaultTopLevel()).addComponent(tc, this, null);
    ( (TipiScreen) getDefaultTopLevel()).addStudio((Window)tc.getContainer(), null);
  }

  public void addTopLevel(Object toplevel) {
    rootPaneList.add(toplevel);
  }

  public void removeTopLevel(Object toplevel) {
    rootPaneList.remove(toplevel);
  }
}
