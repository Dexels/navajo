package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import nextapp.echo.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiScreen extends TipiEchoDataComponentImpl {
//  private Map componentMap = new HashMap();

  public TipiScreen() {
    setId("init");
  }

  public Object createContainer() {
    myContext.setDefaultTopLevel(this);
    return null;
  }

//  public Object createContainer() {
//    return null;
//  }

  public Object getContainer() {
    return getTopLevel();
  }

  public void addToContainer(final Object current, final Object constraints) {
    if (current != null) {
          if (Window.class.isInstance(current)) {
            ( (Component) current).setVisible(true);
          }
    }
  }

  public void removeFromContainer(Object c) {
    final Component current = (Component) c;
    if (current != null) {
          if (Window.class.isInstance(current)) {
            ( (Component) current).setVisible(false);
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
  public Object getTopLevel() {
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = getTipiComponent(i);
      System.err.println("Checking tipi: "+current.getId());
      if (current.isTopLevel()) {
        return current.getContainer();
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
