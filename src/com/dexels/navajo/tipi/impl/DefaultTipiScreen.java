package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiScreen extends DefaultTipi {
  public DefaultTipiScreen() {
    setId("init");
  }
  public Container createContainer() {
    return null;
  }
  public void addToContainer(Component frame, Object constraints) {
    System.err.println("\n\nAdding frame: "+frame.getClass()+"\n\n");
    frame.setVisible(true);
  }
  public RootPaneContainer getTopLevel() {
    System.err.println("COUNT: "+getChildCount());
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = getTipiComponent(i);
      System.err.println("Examining: "+current.getClass());
      if (current.getContainer()!=null && RootPaneContainer.class.isInstance(current.getContainer())) {
        return (RootPaneContainer)current.getContainer();
      }
    }
    System.err.println("RETURNING NULL. OH DEAR");
    return null;
  }

  String getPath(String typedef) {
    return typedef;
  }


  public void clearTopScreen() {
    for (int i = getTipiCount()-1; i >= 0; i--) {
      TipiComponent current = getTipiComponent(i);
      if (!current.isStudioElement()) {
        removeChild(current);
      }

    }

  }

}