package com.dexels.navajo.studio;

import java.awt.*;
import javax.swing.JPanel;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.tree.*;

import org.xml.sax.*;
import org.w3c.dom.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class BaseStudioPanel extends JPanel {

  protected RootStudioPanel rootPanel = null;

//  protected boolean error               = false; // indicated status of the panel. Maybe should be removed

  protected String title                = "";
  protected boolean newEntry            = false;
  protected NavajoTreeNode selectedNode = new NavajoTreeNode();

  BorderLayout borderLayout1 = new BorderLayout();

  public BaseStudioPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
  }


  protected void applyTemplate2(){
    // empty
  }

  /**
   * @param text The string that will be shown on the error line.
   */

  protected void showError(String text){
    rootPanel.showMsg("ERROR: " + text);
  }

  protected void showMsg(String text){
    rootPanel.showMsg(text);
  }
  public RootStudioPanel getRootPanel(){
    return rootPanel;
  }

  /**
   * functions that will be overriden
   */

  void cancelButton_actionPerformed(ActionEvent e){  }
  void okButton_actionPerformed(ActionEvent e){  }
  void deleteButton_actionPerformed(ActionEvent e) {  }
  void updatePanel(NavajoEvent ne){  }

}