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
  public TipiScreen() {
    setId("init");
  }

  public Object createContainer() {
    return null;
  }

  public Object getContainer() {
    return getTopLevel();
  }

  public void addStudio(final Window current, final Object constraints) {
    current.setVisible(true);
  }

  public void addToContainer(final Object c, final Object constraints) {
    final Component current = (Component) c;
    runSyncInEventThread(new Runnable() {
      public void run() {

          if (current != null && Window.class.isInstance(current)) {
//            System.err.println("Not null, and window");
            ( (Component) current).setVisible(true);
          }
          else {
            System.err.println("**************** SHOULD NOT REALLY BE HERE: " +
                               current);
          }
        }
    });

  }

  public void removeFromContainer(Object c) {
     final Component current = (Component) c;
    if (current == null) {
      return;
    }
      if (Window.class.isInstance(current) ||
          JInternalFrame.class.isInstance(current)) {
        current.setVisible(false);
      }
      else {
        System.err.println("NOT studio mode, but not of type window: " +
                           current.getClass());
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

  public void addComponent(TipiComponent tc, TipiContext context,
                           Object constraints) {

    if (tc == null) {
      System.err.println("And I thought that this would never happen. Nice.");
      Thread.dumpStack();
      return;
    }
     super.addComponent(tc, context, constraints);
  }
}
