package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DefaultTipiScreen
    extends DefaultTipi {

  private Map componentMap = new HashMap();

  public DefaultTipiScreen() {
    setId("system");
  }

  public Container createContainer() {
    return null;
  }

  public void addToContainer(Component current, Object constraints) {

    if (current != null) {
      if (RootPaneContainer.class.isInstance(current)) {
        current.setVisible(true);
      }
      else {
        JFrame jf = new JFrame("Studio dummy");
        componentMap.put(current,jf);
        jf.setSize(400,300);
        jf.getContentPane().setLayout(new BorderLayout());
        if (JMenuBar.class.isInstance(current)) {
          System.err.println("Setting menu");
          jf.setJMenuBar( (JMenuBar) current);
          jf.setVisible(true);
          return;
        }
        if (Container.class.isInstance(current)) {
          jf.getContentPane().add(current, BorderLayout.CENTER);
          jf.setVisible(true);
          return;
        }
      }
    }

  }

  public void removeFromContainer(Component current) {
    if (current != null) {
      if (RootPaneContainer.class.isInstance(current)) {
        current.setVisible(false);
      }
      else {
        JFrame jf = (JFrame)componentMap.get(current);
        if (jf!=null) {
          jf.getContentPane().remove(current);
          componentMap.remove(current);
          jf.setVisible(false);
        }
       }
    }
  }

  public RootPaneContainer getTopLevel() {
//    System.err.println("COUNT: " + getChildCount());
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = getTipiComponent(i);
      System.err.println("Examining: " + current.getClass());
      if (RootPaneContainer.class.isInstance(current.getContainer())) {
        return (RootPaneContainer)current.getContainer();
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
        TipiContext.getInstance().disposeTipiComponent(current);
      }
    }
  }
}