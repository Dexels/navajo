package com.dexels.navajo.tipi.studio.components;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import javax.swing.text.*;
import com.dexels.navajo.tipi.tipixml.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StudioMainEditorPanel extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane studioTabs = new JTabbedPane();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextPane jEditorPane1 = new JTextPane();
  JDesktopPane jPanel1 = new JDesktopPane();
  public StudioMainEditorPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jEditorPane1.setText("");
    this.add(studioTabs, BorderLayout.CENTER);
    studioTabs.add(jScrollPane1, "Source");
    studioTabs.add(jPanel1, "Preview");
    studioTabs.setTabPlacement(JTabbedPane.BOTTOM);
    jScrollPane1.getViewport().add(jEditorPane1, null);
  }

  public void setFile(XMLElement file){
    System.out.println("SetFile called: " + file);
    try{
      TipiDocumentStyleParser p = new TipiDocumentStyleParser(file);
      jEditorPane1.setDocument(p.getDocument());
    }catch(Exception e){
      System.out.println("Error" + e.toString());
      e.printStackTrace(System.out);
    }
  }

  public JDesktopPane getDesktop(){
    return jPanel1;
  }

}