package com.dexels.navajo.tipi.studio.components;

import com.dexels.navajo.tipi.studio.tree.*;
import tipi.MainApplication;
import com.dexels.navajo.tipi.studio.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import nanoxml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MainPanel extends JPanel {
  private StudioMainEditorPanel main;
  private TipiComponentTreeTestPanel tree;

  public MainPanel() {
    this.setLayout(new BorderLayout());
    main = new StudioMainEditorPanel();
    StudioSystemMessagesPanel messages = new StudioSystemMessagesPanel();
    messages.setPreferredSize(new Dimension(800, 200));
    tree = new TipiComponentTreeTestPanel();
    tree.setPreferredSize(new Dimension(200, 800));
    JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tree, main);
    JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitPane, messages);
    this.add(verticalSplitPane, BorderLayout.CENTER);
    System.err.println("Oeps");
    Thread.dumpStack();
  }

  public void setFile(String fileName){
    try{
      System.out.println("Setting file in MainPanel");
      CaseSensitiveXMLElement e = new CaseSensitiveXMLElement();
      URL u = MainApplication.class.getResource(fileName);
      System.out.println("URL: " + u);

      PreviewFrame frame = new PreviewFrame();
      frame.load(u);
      frame.setVisible(true);
      main.getDesktop().add(frame);
      e.parseFromReader(new InputStreamReader(u.openStream()));
      main.setFile(e);
      tree.setElement(e);
    }catch(Exception e){
      e.printStackTrace(System.out);
    }
  }
}