package com.dexels.navajo.studio;


import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import com.dexels.navajo.mapping.*;
import java.lang.reflect.*;
import java.util.*;
import java.beans.*;
import java.io.*;
import javax.swing.event.*;
import javax.swing.border.*;
import com.dexels.navajo.document.AntiMessage;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class BPCLObjectPanel extends BaseStudioPanel {

    JTree fieldTree = new JTree();
    ClassTreeNode node = new ClassTreeNode();
    DefaultTreeModel model = new DefaultTreeModel(node);
    JTree classTree = new JTree();
    JLabel jLabel1 = new JLabel();
    JTextPane jTextPane1 = new JTextPane();

    String pathPrefix = "";
    String localClassPath = "";

    JPanel centerPanel = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel4 = new JPanel();
    JScrollPane jScrollPane3 = new JScrollPane();
    JScrollPane jScrollPane2 = new JScrollPane();
    JSplitPane jSplitPane1 = new JSplitPane();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JPanel jPanel2 = new JPanel();
    JTextField currentLocationLabel = new JTextField();
    JLabel jLabel4 = new JLabel();
    JPanel jPanel3 = new JPanel();
    JLabel jLabel5 = new JLabel();
    // TitledBorder titledBorder1;
    // TitledBorder titledBorder2;
    TitledBorder titledBorder3;
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    Border border1;

    public BPCLObjectPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BPCLObjectPanel(RootStudioPanel rootPanel1, NavajoTreeNode selected, boolean newEntry1) {
        rootPanel = rootPanel1;
        selectedNode = selected;
        newEntry = newEntry1;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        // titledBorder1 = new TitledBorder("");
        // titledBorder2 = new TitledBorder(BorderFactory.createMatteBorder(4,4,4,4,new Color(212, 208, 200)),"");
        titledBorder3 = new TitledBorder("");
        border1 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(148, 145, 140), new Color(103, 101, 98)), BorderFactory.createEmptyBorder(3, 3, 3, 3));
        rootPanel.setEditOk(false);
        title = "Add object";

        this.setLayout(borderLayout1);

        classTree.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        classTree_mousePressed(e);
                    }
                }
                );
        jLabel1.setText("Current object: ");

        centerPanel.setLayout(borderLayout2);
        centerPanel.setMinimumSize(new Dimension(1, 1));
        centerPanel.setPreferredSize(new Dimension(400, 400));
        jPanel1.setLayout(borderLayout3);
        jPanel4.setPreferredSize(new Dimension(400, 400));
        jPanel4.setMinimumSize(new Dimension(1, 1));
        jPanel4.setLayout(borderLayout5);
        jLabel3.setBackground(Color.blue);
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel3.setToolTipText("");
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setText("Object contents:");
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Package");
        jPanel2.setLayout(borderLayout4);
        currentLocationLabel.setBackground(new Color(212, 208, 200));
        currentLocationLabel.setBorder(null);
        currentLocationLabel.setEditable(false);
        jLabel4.setText("Chosen object: ");
        jPanel3.setPreferredSize(new Dimension(10, 50));
        jPanel3.setLayout(verticalFlowLayout1);
        // this.add(jTextPane1, new XYConstraints(364, 12, 97, 42));

        System.err.println("DEBUG: rootPanel.classPath: " + rootPanel.classPath);
        if (rootPanel.classPath != null) {
            if (!rootPanel.classPath.equals("")) {
                pathPrefix = rootPanel.classPath;
                localClassPath = rootPanel.classPath.substring(0, rootPanel.classPath.length() - 1);
                System.err.println("classPath: " + localClassPath);
                buildClassTree(rootPanel.classPath);
                jScrollPane3.getViewport().add(classTree, null);
                jScrollPane2.getViewport().setVisible(false);
                jScrollPane2.getViewport().add(fieldTree, null);

            } else {
                jTextPane1.setForeground(Color.red);
                jTextPane1.setText(" Warning: cannot show package tree - please set location of package: in the menubar -> Configure -> Package location");
                jScrollPane3.getViewport().add(jTextPane1);
                System.err.println("Warning: cannot show package tree - please set location of package");
                // showError(" Warning: cannot show package tree - please set location of package");

            }
        } else {
            System.err.println("WARNING: rootPanel.classPath =" + rootPanel.classPath);
        }

        // if(!newEntry){
        // if(!selectedNode.getAttribute("object").equals("")){
        // currentObjectField.setText(selectedNode.getAttribute("object"));
        // }
        // }
        // else{
        // currentObjectField.setForeground(Color.red);
        // currentObjectField.setText("Not defined");
        // }
        applyTemplate2();
        jLabel5.setBackground(Color.white);
        jLabel5.setMinimumSize(new Dimension(1, 1));
        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setText("Select a mappable object from package:");
        jPanel2.setBorder(border1);
        this.setBorder(titledBorder3);
        jScrollPane3.setBorder(BorderFactory.createLineBorder(Color.black));
        jScrollPane2.setBorder(null);
        borderLayout4.setHgap(4);
        borderLayout4.setVgap(4);
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.add(jPanel4, JSplitPane.LEFT);
        jPanel4.add(jScrollPane3, BorderLayout.CENTER);
        jPanel4.add(jLabel2, BorderLayout.NORTH);
        jSplitPane1.add(jPanel2, JSplitPane.RIGHT);
        jPanel2.add(jScrollPane2, BorderLayout.CENTER);
        jPanel2.add(jLabel3, BorderLayout.NORTH);
        jPanel1.add(jPanel3, BorderLayout.SOUTH);
        jPanel3.add(jLabel4, null);
        jPanel3.add(currentLocationLabel, null);
        centerPanel.add(jLabel5, BorderLayout.NORTH);
        jSplitPane1.setDividerLocation(300);

    }

    void okButton_actionPerformed(ActionEvent e) {

        int pos = localClassPath.lastIndexOf(".class");
        String className = localClassPath.substring(0, pos);

        System.out.println("OK BUTTON CLICKED, className = " + className);

        try {
            if (newEntry) {
                System.err.println("DEBUG: INSERT NODE");
                NavajoTreeNode newNode = new NavajoTreeNode("map");

                newNode.putAttributes("object", className);
                rootPanel.tslModel.insertNodeInto(newNode, selectedNode, 0);
                TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(newNode);
                TreePath path = new TreePath(nodes);

                rootPanel.tslTree.setSelectionPath(path);
            } else {
                System.err.println("DEBUG: REPLACE NODE");
                selectedNode.putAttributes("object", className);
                selectedNode.setUserObject("<map> " + className);
                TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(selectedNode);
                TreePath path = new TreePath(nodes);

                rootPanel.tslTree.setSelectionPath(path);
            }
            // rootPanel.useobject = (Mappable)Class.forName(classNameFull).newInstance();
            rootPanel.useobject = (Mappable) Class.forName(className).newInstance();

            System.out.println("rootPanel.useobject = " + rootPanel.useobject);

            rootPanel.classPath = pathPrefix;
            rootPanel.isModified();

            rootPanel.changeContentPane(rootPanel.BPCLPANEL);

            rootPanel.mainMappableObjectTree = fieldTree;
        } catch (Exception ex) {
            System.err.println("Error in BPCLObjectPanel: trouble in okbutton");
            ex.printStackTrace(System.out);
        }

    }

    void cancelButton_actionPerformed(ActionEvent e) {
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void buildClassTree(String filename) {

        // org.dexels.utils.JarResources jr = new org.dexels.utils.JarResources(filename);

        DefaultMutableTreeNode node = new DefaultMutableTreeNode(".");
        DefaultTreeModel model2 = new DefaultTreeModel(node);

        getFileNodes(model2, node, filename);
        classTree = new JTree(model2);

        classTree.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        classTree_mousePressed(e);
                    }
                }
                );
        classTree.putClientProperty("JTree.lineStyle", "Angled");
        model2.setAsksAllowsChildren(true);

        jScrollPane3.getViewport().removeAll();// remove(classTree);
        jScrollPane3.getViewport().add(classTree, null);

        pathPrefix = filename + "/";
    }

    void getFileNodes(DefaultTreeModel model2, DefaultMutableTreeNode root, String path) {
        String list[] = new File(path).list();

        path = path + "/";
        for (int i = 0; i < list.length; i++) {
            System.err.println("file = " + path + list[i]);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(list[i]);

            model2.insertNodeInto(node, root, root.getChildCount());
            File file = new File(path + list[i]);

            if (file.isDirectory()) {
                node.setAllowsChildren(true);
                // getFileNodes(model2, node, path+list[i]);
            } else {
                node.setAllowsChildren(false);
            }
        }
    }

    void classTree_mousePressed(MouseEvent e) {
        TreePath treepath = classTree.getClosestPathForLocation(e.getX(), e.getY());

        if (treepath != null) {
            classTree.setSelectionPath(treepath);
            classTree.scrollPathToVisible(treepath);
            DefaultMutableTreeNode fileNode = (DefaultMutableTreeNode) treepath.getLastPathComponent();

            TreeNode nodes[] = fileNode.getPath();
            String currentClasspath = "";

            for (int i = 1; i < nodes.length; i++) {
                currentClasspath = currentClasspath + nodes[i];
                if (i < nodes.length - 1)
                    currentClasspath = currentClasspath + ".";
            }
            int classPos = currentClasspath.lastIndexOf(".class");

            if (classPos != -1) {
                System.err.println("DEBUG: " + currentClasspath.substring(0, classPos));
                changeClassView(currentClasspath.substring(0, classPos));
                rootPanel.setEditOk(true);
                currentLocationLabel.setForeground(Color.black);
                currentLocationLabel.setText(currentClasspath.substring(0, classPos));
            } else {
                changeClassView("");
                if (!fileNode.getAllowsChildren()) {
                    currentLocationLabel.setForeground(Color.red);
                    currentLocationLabel.setText("not a mappable object");
                } else {
                    currentLocationLabel.setText("");
                }
                rootPanel.setEditOk(false);

            }

            System.err.println("classpath: " + currentClasspath);
            if (fileNode.getAllowsChildren() && fileNode.getChildCount() == 0) {
                nodes = fileNode.getPath();
                String filepath = "";

                for (int i = 1; i < nodes.length; i++) {
                    filepath = filepath + nodes[i];
                    if (i < nodes.length - 1)
                        filepath = filepath + "/";
                }
                System.err.println("jTree3_mousePressed: classpath= " + currentClasspath);
                System.err.println("jTree3_mousePressed: path= " + pathPrefix + filepath);
                getFileNodes((DefaultTreeModel) classTree.getModel(), fileNode, pathPrefix + filepath);
            }
            localClassPath = currentClasspath;
        }
    }

    void changeClassView(String classPath) {

        System.out.println("in changeClassView(" + classPath + ")");

        if (!classPath.equals("")) {
            try {
                Mappable map = (Mappable) Class.forName(classPath).newInstance();

                System.err.println("class = " + map.getClass().getName());

                node = new ClassTreeNode(map.getClass().getName());
                node = rootPanel.setObjectNode(map.getClass(), node, RootStudioPanel.ALL_METHOD);
                model = new DefaultTreeModel(node);

                fieldTree = new JTree(model);
                jScrollPane2.getViewport().remove(fieldTree);
                jScrollPane2.getViewport().setVisible(true);
                jScrollPane2.getViewport().add(fieldTree, null);

            } catch (Exception ex) {
                showError("Invalid class: \"" + classPath + "\" , please check package location");
                System.err.println("ERROR in BPCLObjectPanel.changeClassView:  unable to view selected class: " + classPath + " , please check classpath");
                ex.printStackTrace(System.out);
            }
        } else {
            jScrollPane2.getViewport().setVisible(false);
        }
    }

    void deleteButton_actionPerformed(ActionEvent e) {
        rootPanel.tslModel.removeNodeFromParent(selectedNode);
        rootPanel.isModified();
        rootPanel.classPath = "";
        rootPanel.useobject = null;
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void updatePanel(NavajoEvent ne) {
        String eventClassPath = ne.getClassPath();

        System.err.println("eventClassPath:" + eventClassPath);
        if (!eventClassPath.equals("")) {
            buildClassTree(eventClassPath);
        }
    }

}
