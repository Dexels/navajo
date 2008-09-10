package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BaseButton extends JButton implements Ghostable, ActionListener {
  private boolean ghosted = false;
  private boolean enabled = true;
  private ArrayList myListeners = new ArrayList();
  private Action myAction = null;

  public BaseButton(String name) {
    super(name);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public BaseButton() {
    super();
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  public boolean isGhosted() {
    return ghosted;
  }

  public void setGhosted(boolean g) {
    ghosted = g;
    super.setEnabled(enabled && (!ghosted));
  }

  @Override
public void setEnabled(boolean e) {
    enabled = e;
    super.setEnabled(enabled && (!ghosted));
  }

  @Override
public void addActionListener(ActionListener l) {
    super.addActionListener(this);
    myListeners.add(l);
  }

  private final void jbInit() throws Exception {
    myAction = new AbstractAction("bla") {
      public void actionPerformed(ActionEvent e) {
        buttonActionPerformed(e);
      }
    };

    getActionMap().put("actionPerformed",myAction);

  }

  public void setHotKey(String s) {
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(s),"actionPerformed");
  }

  public void buttonActionPerformed(ActionEvent e) {
    actionPerformed(e);
  }

  public void actionPerformed(ActionEvent e) {
    for (int i = 0; i < myListeners.size(); i++) {
      ActionListener al = (ActionListener)myListeners.get(i);
      al.actionPerformed(e);
    }

  }
}
