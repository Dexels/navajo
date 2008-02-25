package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class YearOverviewTestDialog
    extends JDialog {
  ControlledCalendarPanel controlledCalendarPanel1 = new ControlledCalendarPanel();

  public static void main(String args[]) {
    YearOverviewTestDialog td = new YearOverviewTestDialog();
    td.setSize(1024, 768);
    td.setVisible(true);
  }

  public YearOverviewTestDialog() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      jbInit();
      SwingUtilities.updateComponentTreeUI(this);
//      System.err.println("LNF setTo: " + UIManager.getLookAndFeel().toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final void jbInit() throws Exception {
    this.getContentPane().add(controlledCalendarPanel1, BorderLayout.CENTER);
  }
}