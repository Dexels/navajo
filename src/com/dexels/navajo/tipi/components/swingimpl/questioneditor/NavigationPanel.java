package com.dexels.navajo.tipi.components.swingimpl.questioneditor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NavigationPanel extends JPanel implements ActionListener {
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton leftButton = new JButton();
  JButton upButton = new JButton();
  JButton downButton = new JButton();
  JButton rightButton = new JButton();
  public NavigationPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    upButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_up.gif")));
    downButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_down.gif")));
    leftButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_left.gif")));
    rightButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_right.gif")));
//    upButton.addActionListener();
    upButton.addActionListener(this);
    downButton.addActionListener(this);
    leftButton.addActionListener(this);
    rightButton.addActionListener(this);
    upButton.setActionCommand("up");
    downButton.setActionCommand("down");
    leftButton.setActionCommand("left");
    rightButton.setActionCommand("right");
    upButton.setMargin(new Insets(0,0,0,0));
    downButton.setMargin(new Insets(0,0,0,0));
    leftButton.setMargin(new Insets(0,0,0,0));
    rightButton.setMargin(new Insets(0,0,0,0));
  }
  private final ArrayList myActionListeners = new ArrayList();

  public void addActionListener(ActionListener e) {
   myActionListeners.add(e);
  }

  public void removeActionListener(ActionListener e) {
   myActionListeners.remove(e);
  }

  public void actionPerformed(ActionEvent ae) {
    for (int i = 0; i < myActionListeners.size(); i++) {
      ActionListener current = (ActionListener)myActionListeners.get(i);
      current.actionPerformed(ae);
    }
  }

  public void setEnabled(boolean b) {
    upButton.setEnabled(b);
    downButton.setEnabled(b);
    leftButton.setEnabled(b);
    rightButton.setEnabled(b);
  }

  private void jbInit() throws Exception {
    leftButton.setText("");
    this.setLayout(gridBagLayout1);
    upButton.setText("");
    downButton.setText("");
    rightButton.setText("");
    this.add(leftButton,    new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(upButton,   new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(downButton,   new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(rightButton,   new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
  }

}
