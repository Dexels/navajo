package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;

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
    return null;
  }

  public Object getContainer() {
    return getTopLevel();
  }

  public void addToContainer(final Object current, final Object constraints) {
    if (current != null) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          if (Window.class.isInstance(current)) {
            ( (Component) current).setVisible(true);
          }
        }
      });
    }
    else {
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
    }
  }

  public void removeFromContainer(Object c) {
    final Component current = (Component) c;
    if (current != null) {
      if (RootPaneContainer.class.isInstance(current)) {
        runSyncInEventThread(new Runnable() {
          public void run() {
            current.setVisible(false);
          }
        });
      }
      else {
        final JFrame jf = (JFrame) componentMap.get(current);
        if (jf != null) {
          runSyncInEventThread(new Runnable() {
            public void run() {
              jf.getContentPane().remove(current);
              componentMap.remove(current);
              jf.setVisible(false);
            }
          });
        }
      }
    }
  }

  public TipiComponent getTipiComponentByPath(String path) {
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

  public void addComponent(TipiComponent tc, TipiContext context, Object constraints) {
    if (tc == null) {
      return;
    }
    super.addComponent(tc, context, constraints);
  }
}
