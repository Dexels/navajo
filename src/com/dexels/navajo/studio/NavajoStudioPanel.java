package com.dexels.navajo.studio;


import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.VerticalFlowLayout;
import javax.swing.border.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class NavajoStudioPanel extends JPanel {

    private RootStudioPanel rootPanel = new RootStudioPanel();

    BorderLayout  borderLayout1 = new BorderLayout();
    JPanel        jPanel2 = new JPanel();
    JPanel        jPanel5 = new JPanel();
    GridLayout    gridLayout3 = new GridLayout();
    JSplitPane    trees_mainSplitPane = new JSplitPane();
    JSplitPane    main_messageSplitPane = new JSplitPane();
    JScrollPane   infoPane = new JScrollPane();
    JTextPane     jTextPane1 = new JTextPane();
    JPanel        jPanel3 = new JPanel();
    JPanel        jPanel4 = new JPanel();
    JPanel        jPanel6 = new JPanel();
    GridLayout    gridLayout2 = new GridLayout();
    JTree         BPCLTree = new JTree();
    JScrollPane   jScrollPane2 = new JScrollPane();
    JTree         BPFLTree = new JTree();
    JScrollPane   jScrollPane1 = new JScrollPane();
    JTabbedPane   jTabbedPane1 = new JTabbedPane();
    JPanel        westPanel = new JPanel();
    FlowLayout    flowLayout1 = new FlowLayout();

    public NavajoStudioPanel() {
        try {
            init();
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel5.setBackground(Color.yellow);
        jPanel2.setLayout(gridLayout3);
        main_messageSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        main_messageSplitPane.setBackground(Color.yellow);
        main_messageSplitPane.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextPane1.setPreferredSize(new Dimension(1, 1));
        jTextPane1.setText("jTextPane1");
        jTextPane1.setMinimumSize(new Dimension(1, 1));
        jPanel6.setBackground(Color.yellow);
        jPanel3.setBackground(Color.yellow);
        jPanel4.setBackground(Color.yellow);
        infoPane.setMaximumSize(new Dimension(100, 480));
        infoPane.setMinimumSize(new Dimension(1, 1));
        infoPane.setPreferredSize(new Dimension(1, 1));
        westPanel.setPreferredSize(new Dimension(300, 300));
        westPanel.setLayout(gridLayout2);
        rootPanel.setPreferredSize(new Dimension(300, 300));
        rootPanel.setLayout(flowLayout1);
        this.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(main_messageSplitPane, null);
        main_messageSplitPane.add(trees_mainSplitPane, JSplitPane.TOP);
        main_messageSplitPane.add(infoPane, JSplitPane.BOTTOM);
        infoPane.getViewport().add(jTextPane1, null);
        this.add(jPanel5, BorderLayout.EAST);
        this.add(jPanel3, BorderLayout.WEST);
        this.add(jPanel4, BorderLayout.SOUTH);
        this.add(jPanel6, BorderLayout.NORTH);

        trees_mainSplitPane.add(westPanel, JSplitPane.LEFT);
        westPanel.add(jTabbedPane1, null);
        jTabbedPane1.add(jScrollPane1, "jScrollPane1");
        jTabbedPane1.add(jScrollPane2, "jScrollPane2");
        // trees_mainSplitPane.add(rootPanel, JSplitPane.RIGHT);

        trees_mainSplitPane.add(new RootStudioPanel(), JSplitPane.RIGHT);

        jScrollPane2.getViewport().add(BPCLTree, null);
        jScrollPane1.getViewport().add(BPFLTree, null);
        // trees_mainSplitPane.add(treesPanel, JSplitPane.LEFT);
    }

    void init() {
        rootPanel = new RootStudioPanel();
        BPFLTree = rootPanel.getBPFLTree();
        BPCLTree = rootPanel.getBPCLTree();
        // treesPanel = new NavajoStudioTreesPanel();
    }

    /* public BPCLPanel getBpclPanel(){
     return rootPanel.getBpclPanel();
     }*/

}
