package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import java.awt.*;
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
public class TipiScreen
    extends TipiSwingDataComponentImpl {
  private Map componentMap = new HashMap();
  public TipiScreen() {
    setId("init");
  }

  public Object createContainer() {
    System.err.println("");
    return null;
  }

  public Object getContainer() {
    return getTopLevel();
  }

  public void addStudio(final Window current, final Object constraints) {
    current.setVisible(true);
  }
  public void addToContainer(final Object current, final Object constraints) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        if (myContext.isStudioMode()) {

          if (current != null && RootPaneContainer.class.isInstance(current)) {
             System.err.println("Not null, and window");
             ( (Component) current).setVisible(true);
           }
           else {
             TipiFrame tf = new TipiFrame();
             tf.setContext(myContext);
             TipiSwingFrameStudioImpl jf = (TipiSwingFrameStudioImpl)tf.createContainer();
             System.err.println("Creating studio");
//             JFrame jf = new JFrame("Studio dummy");
             componentMap.put(current, jf);
             jf.setSize(400, 300);
             jf.getContentPane().setLayout(new BorderLayout());
             if (JMenuBar.class.isInstance(current)) {
               System.err.println("Setting menu");
               jf.setJMenuBar( (JMenuBar) current);
               jf.setVisible(true);
               myContext.getStudioScreen().addToContainer(jf,null);

               return;
             }
             if (Container.class.isInstance(current)) {
               jf.getContentPane().add( (Component) current, BorderLayout.CENTER);
               jf.setVisible(true);
               return;
             }
   System.err.println("**************** SHOULD NOT REALLY BE HERE: "+current);
           }


//           componentMap.put(current, jf);

          myContext.getStudioScreen().addToContainer(current,constraints);
          return;
       }
       if (current != null && Window.class.isInstance(current)) {
          System.err.println("Not null, and window");
          ( (Component) current).setVisible(true);
        }
        else {
          System.err.println("Creating studio");
          JFrame jf = new JFrame("Studio dummy");
          componentMap.put(current, jf);
          jf.setSize(400, 300);
          jf.getContentPane().setLayout(new BorderLayout());
          if (JMenuBar.class.isInstance(current)) {
            System.err.println("Setting menu");
            jf.setJMenuBar( (JMenuBar) current);
            jf.setVisible(true);
            return;
          }
          if (Container.class.isInstance(current)) {
            jf.getContentPane().add( (Component) current, BorderLayout.CENTER);
            jf.setVisible(true);
            return;
          }
System.err.println("**************** SHOULD NOT REALLY BE HERE: "+current);
        }
      }
    });

  }

  public void removeFromContainer(Object c) {
    System.err.println("TIPISCREEN::::::::::: removing from screen:  "+c.getClass());
    final Component current = (Component) c;
    runSyncInEventThread(new Runnable() {
      public void run() {
        if (current != null) {
          System.err.println("Yes");
          if (Window.class.isInstance(current) ||  JInternalFrame.class.isInstance(current)) {
            current.setVisible(false);

          }
//          else {

            if (myContext.isStudioMode()) {
              System.err.println("no");
              final TipiSwingFrameStudioImpl jf = (TipiSwingFrameStudioImpl) componentMap.get(current);
              if (jf!=null) {
                myContext.getStudioScreen().removeFromContainer(jf);
              }
              myContext.getStudioScreen().removeFromContainer(current);

              componentMap.remove(current);
            } else {
              final JFrame jf = (JFrame) componentMap.get(current);
            if (jf != null) {
              jf.getContentPane().remove(current);
              componentMap.remove(current);
              jf.setVisible(false);
            } else {
              System.err.println("Hmm");
            }
            }
//          }

        }
      }
    });
  }

  public TipiComponent getTipiComponentByPath(String path) {
   // System.err.println("Looking for path: "+path);
    if (path.equals(".")) {
      return this;
    }
    if (path.equals("..")) {
      return null;
    }
    if (path.startsWith("..")) {
      return null;
    }
    if (path.indexOf("/") == 0) {
      path = path.substring(1);
    }
    int s = path.indexOf("/");
    if (s == -1) {
      if (path.equals("")) {
        return (TipiComponent) myContext.getDefaultTopLevel();
      }
      return getTipiComponent(path);
    }
    else {
      String name = path.substring(0, s);
      String rest = path.substring(s);
//      System.err.println("Gettin': "+name);
      TipiComponent t = getTipiComponent(name);
      if (t == null) {
        throw new NullPointerException("Did not find Tipi: " + name);
      }
      return t.getTipiComponentByPath(rest);
    }
  }

  // For now, always return the first frame. Maybe enhance later or something
  public RootPaneContainer getTopLevel() {
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = getTipiComponent(i);
      if (RootPaneContainer.class.isInstance(current.getContainer())) {
        return (RootPaneContainer) current.getContainer();
      }
    }
    System.err.println("RETURNING NULL. OH DEAR");
    return null;
  }

  public String getPath(String typedef) {
    return typedef;
  }

  public String toString() {
    super.toString();
    return "screen";
  }

  public void clearTopScreen() {
    for (int i = getChildCount() - 1; i >= 0; i--) {
      TipiComponent current = getTipiComponent(i);
      if (!current.isStudioElement()) {
        myContext.disposeTipiComponent(current);
      }
    }
  }

  public void addComponent(TipiComponent tc, TipiContext context,
                           Object constraints) {
    if (tc == null) {
      return;
    }
    super.addComponent(tc, context, constraints);
  }
}
