package com.dexels.navajo.studio;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;

import com.borland.jbcl.layout.*;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.xml.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.util.*;
import com.dexels.navajo.util.*;

import java.beans.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class RootStudioPanel extends JPanel {

    /**
     *
     */
    static String PROPERTYFILE = "properties.xml";

    // panels index
    final static int BPFLPANEL = 1;
    final static int BPCLPANEL = 2;

    // setter or getter
    public final static int SET_METHOD = 1;
    public final static int GET_METHOD = 2;
    public final static int ALL_METHOD = 3;

    // bpfl panels
    final static int BPFLMESSAGE = 11;
    final static int BPFLMETHODS = 12;
    final static int BPFLPROPERTY = 13;

    // bpcl panels
    final static int BPCLEXPRESSION = 21;
    final static int BPCLFIELD = 22;
    final static int BPCLMAP = 23;
    final static int BPCLMESSAGE = 24;
    final static int BPCLOBJECT = 25;
    final static int BPCLPROPERTY = 26;
    final static int BPCLPARAM = 27;
    final static int BPCLTSL = 28;
    // state members


    private DefaultTreeModel tmlModel = new DefaultTreeModel(new NavajoTreeNode("tml"));
    private JTree tmlTree = new JTree(tmlModel);

    public DefaultTreeModel tslModel = new DefaultTreeModel(new NavajoTreeNode("tsl"));
    public JTree tslTree = new JTree(tslModel);
    public DefaultTreeModel BPFL_in_Model = new DefaultTreeModel(new NavajoTreeNode("tml"));
    public JTree BPFL_in_Tree = new JTree(BPFL_in_Model);

    // public DefaultTreeModel mapObjectModel  = new DefaultTreeModel(new NavajoTreeNode("object"));
    // public JTree mapObjectTree            = null;//new JTree(new DefaultTreeModel(new NavajoTreeNode("object")));
    public String BPFLFileName = "";
    public String BPCLFileName = "";
    public String BPFL_in_FileName = "";
    public Mappable useobject = null;

    public Class currentObject = null;
    public JTree mainMappableObjectTree = new JTree(); // Jtree of the main mappable object used in the BPCL doc
    public JTree mainMappableObjectTreeSet = new JTree();
    public JTree currentMappableObjectTree = new JTree();
    public JTree currentMappableObjectTreeSet = new JTree();

    public String classPath = "";
    public String userScriptsPath = "";
    public boolean isModified = false;
    public DefaultTreeModel propertyModel = new DefaultTreeModel(new NavajoTreeNode("package"));
    public JTree propertyTree = new JTree(propertyModel);

    // panel members
    private JMenuBar jMenuBar1 = new JMenuBar();
    private JMenu fileMenu = new JMenu();
    private JMenuItem fileItem1 = new JMenuItem();
    private JMenuItem fileItem2 = new JMenuItem();
    private JMenuItem fileItem3 = new JMenuItem();
    private JMenuItem fileItem4 = new JMenuItem();
    private JFileChooser jFileChooser1 = new JFileChooser();
    private JMenu configure = new JMenu();
    private JMenuItem configureItem1 = new JMenuItem();
    public ActionListener editCopyListener = null;
    public ActionListener editPasteListener = null;
    public ActionListener editDeleteListener = null;
    public ActionListener editChangeListener = null;

    public JButton newFileButton = new JButton();
    public JButton openButton = new JButton();
    public JButton saveButton = new JButton();
    public JButton copyButton = new JButton();
    public JButton pasteButton = new JButton();

    JLabel statusbarLabel = new JLabel();
    private int DividerLocationOfTreePanel = 100;
    private int tabState = 0;
    private int copyState = -100;
    private NavajoTreeNode copiedNode = null;
    //private BPFLPanel bpflPanel;
    private BPCLPanel bpclPanel;

    // icons: Paths absolute, should change into relative paths...


    // new
    ImageIcon newFileButtonIcon = new ImageIcon("images/new_file_off.gif");
    ImageIcon newFileButtonIconDisabled = new ImageIcon("images/new_file_disabled.gif");

    // open
    ImageIcon openButtonIcon = new ImageIcon("images/open_off.gif");
    ImageIcon openButtonIconOn = new ImageIcon("images/open_on.gif");
    ImageIcon openButtonIconDisabled = new ImageIcon("images/open_disabled.gif");
    ImageIcon openButtonIconPressed = new ImageIcon("images/open_pressed.gif");

    // save
    ImageIcon saveButtonIcon = new ImageIcon("images/save_off.gif");
    ImageIcon saveButtonIconOn = new ImageIcon("images/save_on.gif");
    ImageIcon saveButtonIconDisabled = new ImageIcon("images/save_disabled.gif");
    ImageIcon saveButtonIconPressed = new ImageIcon("images/save_pressed.gif");

    // copy
    ImageIcon copyButtonIcon = new ImageIcon("images/copy_off.gif");
    ImageIcon copyButtonIconOn = new ImageIcon("images/copy_on.gif");
    ImageIcon copyButtonIconDisabled = new ImageIcon("images/copy_disabled.gif");
    ImageIcon copyButtonIconPressed = new ImageIcon("images/copy_pressed.gif");

    // paste
    ImageIcon pasteButtonIcon = new ImageIcon("images/paste_off.gif");
    ImageIcon pasteButtonIconOn = new ImageIcon("images/paste_on.gif");
    ImageIcon pasteButtonIconDisabled = new ImageIcon("images/paste_disabled.gif");
    ImageIcon pasteButtonIconPressed = new ImageIcon("images/paste_pressed.gif");

    JSeparator iconSeparator1 = new JSeparator(JSeparator.VERTICAL);

    public void addEditCopyListener(ActionListener action) {
        if (editCopyListener != null)
            editCopy.removeActionListener(editCopyListener);
        editCopyListener = action;
        editCopy.addActionListener(editCopyListener);
    }

    public void addEditPasteListener(ActionListener action) {
        if (editPasteListener != null)
            editPaste.removeActionListener(editPasteListener);
        editPasteListener = action;
        editPaste.addActionListener(editPasteListener);
    }

    public void addEditChangeListener(ActionListener action) {
        if (editChangeListener != null)
            editChange.removeActionListener(editChangeListener);
        editChangeListener = action;
        editChange.addActionListener(editChangeListener);
    }

    JMenuItem configureItem2 = new JMenuItem();
    BorderLayout borderLayout1 = new BorderLayout();
    // JPanel centerPanel = new JPanel();
    JPanel northPanel = new JPanel();
    JMenuItem editDelete = new JMenuItem();
    JMenuItem editChange = new JMenuItem();
    JMenuItem editPaste = new JMenuItem();
    JMenuItem editCopy = new JMenuItem();
    JMenu edit = new JMenu();

    JMenu wizard = new JMenu("Wizard");
    JMenuItem pluggableWizard = new JMenuItem("Pluggable wizard generates script fragments for mappable object, i.e. JDBC connector for SQLMap");

    JToolBar jToolBar1 = new JToolBar();
    GridLayout gridLayout1 = new GridLayout();
    JPanel mainPanel = new JPanel();
    GridLayout gridLayout2 = new GridLayout();
    JSplitPane horizontalSplitPane = new JSplitPane();
    // JScrollPane bpflScrollPane = new JScrollPane();
    JTabbedPane treesTabbedPane = new JTabbedPane();
    JSplitPane verticalSplitPane = new JSplitPane();
    GridLayout gridLayout3 = new GridLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    // JScrollPane bpclScrollPane = new JScrollPane();
    JPanel treesPanel = new JPanel();
    // BaseStudioPanel currentPanel1 = new BaseStudioPanel();

    EditPanel editPanel = new EditPanel(new BaseStudioPanel(), "Edit Panel");

    private int msgSplitDividerHeight = 30;
    JTextArea jTextArea1 = new JTextArea();
    TitledBorder titledBorder1;
    TitledBorder titledBorder2;
    TitledBorder titledBorder3;
    JPanel jPanel1 = new JPanel();
    JTextArea msgTextArea = new JTextArea();
    JScrollPane jScrollPane1 = new JScrollPane();
    BorderLayout borderLayout3 = new BorderLayout();
    TitledBorder titledBorder4;
    TitledBorder titledBorder5;
    JMenuItem fileItem5 = new JMenuItem();

    public RootStudioPanel() {
        try {
            init();
            jbInit();
            after();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void init() {
        BPFL_in_Model = new DefaultTreeModel(new NavajoTreeNode("tml"));
        newFile();

        //bpflPanel = new BPFLPanel(this);
        bpclPanel = new BPCLPanel(this);
    }

    private void after() {
        //treesTabbedPane.add(bpflPanel, "BPFL");
        treesTabbedPane.add(bpclPanel, "BPCL");
        changeContentPane(this.BPCLPANEL);
    }

    private void jbInit() throws Exception {
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder("");
        titledBorder3 = new TitledBorder("");
        titledBorder4 = new TitledBorder("");
        titledBorder5 = new TitledBorder("");
        saveButton.setBorder(null);
        saveButton.setMaximumSize(new Dimension(24, 24));
        saveButton.setMinimumSize(new Dimension(24, 24));
        saveButton.setPreferredSize(new Dimension(24, 24));
        saveButton.setDisabledIcon(saveButtonIconDisabled);
        saveButton.setDisabledSelectedIcon(saveButtonIconOn);
        saveButton.setIcon(saveButtonIcon);
        saveButton.setPressedIcon(saveButtonIconPressed);
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        saveButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        saveButton_mouseExited(e);
                    }
                }
                );
        saveButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveButton_actionPerformed(e);
                    }
                }
                );

        openButton.setBorder(null);
        openButton.setMaximumSize(new Dimension(24, 24));
        openButton.setMinimumSize(new Dimension(24, 24));
        openButton.setPreferredSize(new Dimension(24, 24));
        openButton.setIcon(openButtonIcon);
        openButton.setRolloverIcon(openButtonIconOn);
        openButton.setDisabledIcon(openButtonIconDisabled);
        openButton.setPressedIcon(openButtonIconPressed);
        openButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        openButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        openButton_mouseExited(e);
                    }
                }
                );
        openButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            openButton_actionPerformed(e);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                );

        newFileButton.setBorder(null);
        newFileButton.setMaximumSize(new Dimension(24, 24));
        newFileButton.setMinimumSize(new Dimension(24, 24));
        newFileButton.setPreferredSize(new Dimension(24, 24));
        newFileButton.setBorderPainted(false);
        newFileButton.setDisabledIcon(newFileButtonIconDisabled);
        newFileButton.setIcon(newFileButtonIcon);
        newFileButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        newFileButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        newFileButton_mouseExited(e);
                    }

                    public void mousePressed(MouseEvent e) {
                        newFileButton_mousePressed(e);
                    }
                }
                );
        newFileButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newFileButton_actionPerformed(e);
                    }
                }
                );

        pasteButton.setBorder(null);
        pasteButton.setMaximumSize(new Dimension(24, 24));
        pasteButton.setMinimumSize(new Dimension(24, 24));
        pasteButton.setPreferredSize(new Dimension(24, 24));
        pasteButton.setIcon(pasteButtonIcon);
        pasteButton.setRolloverIcon(pasteButtonIconOn);
        pasteButton.setDisabledIcon(pasteButtonIconDisabled);
        pasteButton.setPressedIcon(pasteButtonIconPressed);
        pasteButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pasteButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        pasteButton_mouseExited(e);
                    }
                }
                );
        pasteButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editPaste_actionPerformed(e);
                    }
                }
                );

        copyButton.setBorder(null);
        copyButton.setMaximumSize(new Dimension(24, 24));
        copyButton.setMinimumSize(new Dimension(24, 24));
        copyButton.setPreferredSize(new Dimension(24, 24));
        copyButton.setIcon(copyButtonIcon);
        copyButton.setRolloverIcon(copyButtonIconOn);
        copyButton.setDisabledIcon(copyButtonIconDisabled);
        copyButton.setPressedIcon(copyButtonIconPressed);
        copyButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        copyButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        copyButton_mouseExited(e);
                    }
                }
                );
        copyButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editCopy_actionPerformed(e);
                    }
                }
                );

        iconSeparator1.setPreferredSize(new Dimension(2, 24));
        iconSeparator1.setMaximumSize(new Dimension(2, 32767));

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);

        mainPanel.setLayout(gridLayout2);
        horizontalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        horizontalSplitPane.setBorder(null);
        treesPanel.setPreferredSize(new Dimension(300, 300));
        treesPanel.setLayout(gridLayout3);

        verticalSplitPane.setBorder(null);
        verticalSplitPane.setPreferredSize(new Dimension(30, 30));

        statusbarLabel.setFont(new java.awt.Font("Dialog", 1, 10));
        statusbarLabel.setForeground(new Color(1, 0, 0));
        statusbarLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusbarLabel.setMaximumSize(new Dimension(3, 20));
        statusbarLabel.setMinimumSize(new Dimension(3, 20));
        statusbarLabel.setPreferredSize(new Dimension(3, 20));
        this.setBorder(titledBorder2);
        jPanel1.setLayout(borderLayout3);
        msgTextArea.setBorder(null);
        jScrollPane1.setViewportBorder(BorderFactory.createEtchedBorder());
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new Dimension(1, 1));
        jScrollPane1.setPreferredSize(new Dimension(100, 100));
        jPanel1.setBackground(Color.white);
        jPanel1.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanel1.setPreferredSize(new Dimension(104, 104));
        treesTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        treesTabbedPane_mouseClicked(e);
                    }
                }
                );
        northPanel.setBorder(BorderFactory.createEtchedBorder());
        editChange.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editChange_actionPerformed(e);
                    }
                }
                );
        editDelete.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editDelete_actionPerformed(e);
                    }
                }
                );
        editPaste.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editPaste_actionPerformed(e);
                    }
                }
                );
        editCopy.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editCopy_actionPerformed(e);
                    }
                }
                );
        fileItem5.setText("Exit");
        fileItem5.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fileItem5_actionPerformed(e);
                    }
                }
                );
        jToolBar1.add(newFileButton, null);
        jToolBar1.add(openButton, null);
        jToolBar1.add(saveButton, null);

        jToolBar1.add(iconSeparator1);
        jToolBar1.add(copyButton, null);
        jToolBar1.add(pasteButton, null);

        loadPropertyFile();

        this.setLayout(borderLayout1);
        IndexPanel indexPanel = new IndexPanel(this);

        fileItem4.setText("New");
        fileItem4.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fileItem4_actionPerformed(e);
                    }
                }
                );
        configure.setText("Configure");
        configureItem1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        configureItem1_actionPerformed(e);
                    }
                }
                );
        configureItem1.setText("Package location");

        fileMenu.setText("File");
        fileItem1.setText("Open");
        fileItem1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            fileItem1_actionPerformed(e);
                        } catch (com.dexels.navajo.document.NavajoException tbe) {
                            tbe.printStackTrace();
                        }
                    }
                }
                );
        fileItem2.setText("Save");
        fileItem2.setEnabled(false);
        fileItem2.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fileItem2_actionPerformed(e);
                    }
                }
                );
        fileItem3.setText("Save as");
        fileItem3.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fileItem3_actionPerformed(e);
                    }
                }
                );

        // setup menubar
        configureItem2.setText("Incomming BPFL");
        configureItem2.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            configureItem2_actionPerformed(e);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                );

        JMenuItem configureItem3 = new JMenuItem("Set up (Navajo server)");

        // Panel.setBackground(Color.yellow);
        northPanel.setLayout(gridLayout1);
        editDelete.setEnabled(false);
        editDelete.setText("Delete");
        editDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        editChange.setEnabled(false);
        editChange.setText("Change");
        editPaste.setEnabled(false);
        editPaste.setText("Paste");
        editPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        edit.setText("Edit");
        editCopy.setEnabled(false);
        editCopy.setText("Copy");
        editCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

        // Construct menu
        jMenuBar1.add(fileMenu);
        jMenuBar1.add(configure);
        jMenuBar1.add(edit);
        jMenuBar1.add(wizard);

        fileMenu.add(fileItem1);
        fileMenu.add(fileItem2);
        fileMenu.add(fileItem3);
        fileMenu.add(fileItem4);
        fileMenu.add(fileItem5);

        configure.add(configureItem1);
        configure.add(configureItem2);
        configure.add(configureItem3);

        this.add(northPanel, BorderLayout.NORTH);
        northPanel.add(jToolBar1, null);
        edit.add(editCopy);
        edit.add(editPaste);
        edit.add(editDelete);
        edit.add(editChange);

        wizard.add(pluggableWizard);

        pluggableWizard.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Wizard selected");
                    }
                }
                );

        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(horizontalSplitPane, null);
        horizontalSplitPane.add(verticalSplitPane, JSplitPane.TOP);
        verticalSplitPane.add(editPanel, JSplitPane.RIGHT);
        // editPanel.add(currentPanel1, "currentPanel1");
        verticalSplitPane.add(treesPanel, JSplitPane.LEFT);
        treesPanel.add(treesTabbedPane, null);
        jPanel1.setVisible(false);
        horizontalSplitPane.setDividerSize(0);
        horizontalSplitPane.add(jPanel1, JSplitPane.BOTTOM);
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        this.add(statusbarLabel, BorderLayout.SOUTH);
        jScrollPane1.getViewport().add(msgTextArea, null);
    }

    public void changeContentPane(int panelIndex) {
        changeContentPane(null, panelIndex, false);
    }

    public void changeContentPane(BaseStudioPanel p, int panelIndex, boolean newEntry) {
        //bpflPanel.refreshTree();
        bpclPanel.refreshTree();
        // showMsg("changeContentPane");

        if (panelIndex > 3) {
            // we have to show an additional panel to edit an element
            editPanel.changeContentPane(p);
            editPanel.setNewEdit(newEntry);
            setEditTitle(panelIndex, newEntry);
            horizontalSplitPane.remove(verticalSplitPane);
            horizontalSplitPane.add(verticalSplitPane, JSplitPane.TOP);
            verticalSplitPane.add(treesPanel, JSplitPane.LEFT);
        } else {
            horizontalSplitPane.remove(verticalSplitPane);
            horizontalSplitPane.add(treesPanel, JSplitPane.TOP);
            //if (panelIndex == this.BPFLPANEL) {
                treesTabbedPane.setSelectedIndex(0);
            //} else {
            //    treesTabbedPane.setSelectedIndex(1);
            //}
        }

        // set X location of horizontal divider
        DividerLocationOfTreePanel = this.getWidth()
                - verticalSplitPane.getDividerLocation();
        verticalSplitPane.setDividerLocation(this.getWidth() - DividerLocationOfTreePanel);// this.getHeight() - msgSplitDividerHeight);

        // set Y location of horizontal divider
        msgSplitDividerHeight = this.getHeight()
                - horizontalSplitPane.getDividerLocation();
        horizontalSplitPane.setDividerLocation(this.getHeight() - msgSplitDividerHeight);

        //bpflPanel.refreshTree();
        bpclPanel.refreshTree();

        this.revalidate();
    }

    public JMenuBar getMenuBar() {
        return jMenuBar1;
    }

    /**
     *
     */
    private void editorItem1_actionPerformed(ActionEvent e) {
        this.changeContentPane(this.BPCLPANEL);
    }

    /**
     *
     */
    void editorItem2_actionPerformed(ActionEvent e) {
        this.changeContentPane(this.BPCLPANEL);
    }

    /**
     *
     */
    void fileItem1_actionPerformed(ActionEvent e) throws NavajoException {
        // load xml
        if (!userScriptsPath.equals("")) {
            File userDir = new File(userScriptsPath);

            jFileChooser1.setCurrentDirectory(userDir);

        }
        jFileChooser1.setDialogTitle("Open file");
        int returnIntval = jFileChooser1.showOpenDialog(this);

        System.err.println("returnIntval = " + returnIntval);
        if (returnIntval == 0) {
            String filename = jFileChooser1.getSelectedFile().toString();
            String file = filename.substring(0, (filename.length() - 4));
            String fileType = filename.substring(filename.length() - 3);

            System.err.println("DEBUG file= " + file);

            // read the tml file and put the elements in nodes
            //Document tmldoc = NavajoIOUtil.readXml(file + ".tml");
            Document tsldoc = NavajoIOUtil.readXml(file + ".xsl");

            if (tsldoc == null) {
                this.showMsg("unable to read: " + file + ".xsl");
            } else {
                // building the tree
                //Node xmlroot = (Node) tmldoc.getDocumentElement();
                //NavajoTreeNode root = createNavajoTreeNode(xmlroot);

                //tmlModel = new DefaultTreeModel(root);
                //tmlTree.setModel(tmlModel);
                Node xmlroot = (Node) tsldoc.getDocumentElement();
                NavajoTreeNode root = createNavajoTreeNode(xmlroot);
                tslModel = new DefaultTreeModel(root);
                tslTree.setModel(tslModel);
                tslTree.setSelectionPath(new TreePath(tslTree.getModel().getRoot()));
                tmlTree.setSelectionPath(new TreePath(tmlTree.getModel().getRoot()));

                //BPFLFileName = file + ".tml";
                BPCLFileName = file + ".xsl";

                System.err.println("BPFLfile: " + this.BPFLFileName);
                System.err.println("BPCLfile: " + this.BPCLFileName);


                    changeContentPane(this.BPCLPANEL);
                    tabState = 1;


                NavajoTreeNode objectNode = root.getFirstChildByTag("map");

                if (objectNode != null) {
                    if (objectNode.getAttribute("object") != null) {
                        String objectName = objectNode.getAttribute("object");

                        try {
                            useobject = (Mappable) Class.forName(objectName).newInstance();
                            // Create class model with getter methods.
                            ClassTreeNode objectRoot = new ClassTreeNode(useobject.getClass().getName());

                            Util.debugLog("in fileItemActionPerformed: before calling setObjectNode  GET");
                            objectRoot = setObjectNode(useobject.getClass(), objectRoot, GET_METHOD); // set mainMappableObjectTree
                            DefaultTreeModel objectModel = new DefaultTreeModel(objectRoot);

                            mainMappableObjectTree.setModel(objectModel);
                            // Create class model with setter methods.
                            ClassTreeNode objectRootSet = new ClassTreeNode(useobject.getClass().getName());

                            Util.debugLog("in fileItemActionPerformed: before calling setObjectNode SET");
                            objectRootSet = setObjectNode(useobject.getClass(), objectRootSet, SET_METHOD);
                            DefaultTreeModel objectModelSet = new DefaultTreeModel(objectRootSet);

                            mainMappableObjectTreeSet.setModel(objectModelSet);
                        } catch (java.lang.ClassNotFoundException ce) {
                            System.err.println("!!!ERROR!!! cannot find: " + objectName);
                            ce.printStackTrace(System.out);
                        } catch (java.lang.IllegalAccessException ie) {
                            System.err.println("!!!ERROR!!! cannot acces: " + objectName);
                            ie.printStackTrace(System.out);
                        } catch (java.lang.InstantiationException ine) {
                            System.err.println("!!!ERROR!!! cannot instantiate: " + objectName);
                            ine.printStackTrace(System.out);
                        }
                    }
                }
                JFrame j = (JFrame) this.getTopLevelAncestor();

                j.setTitle("Navajo Studio - " + file);
            }

        }
    }

    /**
     *
     */
    void fileItem2_actionPerformed(ActionEvent e) {
        // save Xml
        if (BPFLFileName.equals("")) {
            fileItem3_actionPerformed(e);
            fileItem2.setEnabled(false);
            isModified = false;
        } else {
            System.err.println("file: " + BPCLFileName);
            NavajoIOUtil.saveXml(tslTree, BPCLFileName);
            fileItem2.setEnabled(false);
            isModified = false;
        }
    }

    /**
     *
     */
    void fileItem3_actionPerformed(ActionEvent e) {
        // save xml as ...
        jFileChooser1.setDialogTitle("Save as");
        int returnIntval = jFileChooser1.showOpenDialog(this);

        System.err.println("returnIntval = " + returnIntval);
        // FilenameFilter a = new FilenameFilter();
        if (returnIntval == 0) {
            String filename = jFileChooser1.getSelectedFile().toString();
            String fileExtentsion = filename.substring(filename.length() - 4, filename.length());
            String file = filename;

            if (fileExtentsion.equals(".tml") || fileExtentsion.equals(".xsl")) {
                file = filename.substring(0, (filename.length() - 4));
            }
            BPFLFileName = BPFLFileName = file + ".tml";
            System.err.println("file: " + BPFLFileName);
            NavajoIOUtil.saveXml(tmlTree, BPFLFileName);

            BPCLFileName = BPCLFileName = file + ".xsl";
            System.err.println("file: " + BPCLFileName);
            NavajoIOUtil.saveXml(tslTree, BPCLFileName);

            fileItem2.setEnabled(false);
            // fileItem3.setEnabled(false);
            isModified = false;

            JFrame j = (JFrame) this.getTopLevelAncestor();

            j.setTitle("Navajo Studio - " + file);
        }

    }

    /**
     *
     */
    private NavajoTreeNode createNavajoTreeNode(Node node) {
        NavajoTreeNode ntNode = new NavajoTreeNode(node);

        // If there are any children and they are non-null then recurse...
        if (node.hasChildNodes()) {
            NodeList childNodes = node.getChildNodes();
            int size = childNodes.getLength();
            for (int k = 0; k < size; k++) {
                Node child = childNodes.item(k);

                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    ntNode.add(createNavajoTreeNode(child));
                }
            }
        }
        return ntNode;
    }

    /**
     *
     */
    void fileItem4_actionPerformed(ActionEvent e) {
        // new xml

        // first ask for confirmation...

        int selection = JOptionPane.showConfirmDialog(this, "Are you sure you want to create a new file? Current information will be lost...", "New file confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (selection == JOptionPane.OK_OPTION) {
            newFile();

            changeContentPane(this.BPCLPANEL);

            JFrame j = (JFrame) this.getTopLevelAncestor();

            if (j != null) {
                j.setTitle("Navajo Studio ");
            } else {// this object is not yet initialize, probaly called from inside the constructor
            }

            fileItem2.setEnabled(false);
        }

    }

    void newFile() {

        // reset all variables

        tmlModel = new DefaultTreeModel(new NavajoTreeNode("tml"));
        tmlTree.setModel(tmlModel);
        tmlTree.setSelectionPath(new TreePath(tmlTree.getModel().getRoot()));

        NavajoTreeNode ntn = new NavajoTreeNode("tsl");
        ntn.putAttributes("notes", "");
        ntn.putAttributes("author", "");
        ntn.putAttributes("id", "");
        ntn.putAttributes("repository", "");

        tslModel = new DefaultTreeModel(ntn);
        tslTree.setModel(tslModel);
        tslTree.setSelectionPath(new TreePath(tslTree.getModel().getRoot()));

        BPFLFileName = "";
        BPCLFileName = "";
        useobject = null;
        isModified = false;

        System.err.println("NEW");

    }

    /**
     *
     */
    void isModified() {
        isModified = true;
        fileItem2.setEnabled(true);
        saveButton.setEnabled(true);
    }

    /**
     *
     */
    void configureItem1_actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();

        if (classPath != null && !classPath.equals("")) {
            jFileChooser = new JFileChooser(classPath);
            jFileChooser.setName(classPath);
        }
        jFileChooser.setDialogTitle("select path to package");
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnIntval = jFileChooser.showOpenDialog(this);

        System.err.println("returnIntval = " + returnIntval);

        if (returnIntval == 0) {
            String filename = jFileChooser.getSelectedFile().toString();

            System.err.println("DEBUG file= " + filename);
            classPath = filename;
            NavajoEvent ne = new NavajoEvent();

            ne.setClassPath(classPath);
            editPanel.updatePanel(ne);

            NavajoTreeNode rootNode = (NavajoTreeNode) propertyModel.getRoot();

            System.err.println("rootNode.getTag(): " + rootNode.getTag());
            NavajoTreeNode packageNode = rootNode.getFirstChildByTag("package");

            if (packageNode == null) {
                packageNode = new NavajoTreeNode("package");
                packageNode.setValue(classPath);
                propertyModel.insertNodeInto(packageNode, rootNode, rootNode.getChildCount());
            } else {
                packageNode.putAttributes("path", classPath);
            }
            savePropertyFile();
        }
        System.err.println("debug package location: " + "[" + classPath + "]");

    }

    void loadPropertyFile() throws NavajoException {
        // load xml

        String file = PROPERTYFILE;

        System.err.println("DEBUG file= " + file);

        // read the tml file and put the elements in nodes
        Document propertyDoc = NavajoIOUtil.readXml(PROPERTYFILE);

        if (propertyDoc == null) {
            showError("unable to read the property file: " + file);
        } else {
            // building the tree
            Node xmlroot = (Node) propertyDoc.getDocumentElement();

            System.err.println("debug xmlroot.getNodeName(): " + xmlroot.getNodeName());
            NavajoTreeNode root = createNavajoTreeNode(xmlroot);

            System.err.println("debug root.getTag(): " + root.getTag());
            System.err.println("debug root.getUserObject(): " + root.getUserObject());

            propertyModel = new DefaultTreeModel(root);
            System.err.println("debug: propertyModel root: " + ((NavajoTreeNode) propertyModel.getRoot()).getTag());
            propertyTree.setModel(propertyModel);
            NavajoTreeNode node = root.getFirstChildByTag("package");

            if (node != null) {
                classPath = node.getAttribute("path");
            } else {
                System.err.println("packageNode not found!!");
            }
            node = root.getFirstChildByTag("scriptpath");
            if (node != null) {
                userScriptsPath = node.getAttribute("path");
            } else {
                System.err.println("scriptpathNode not found!!");
            }
        }

        System.err.println("debug: propertyModel root: " + ((NavajoTreeNode) propertyModel.getRoot()).getTag());
        System.err.println("debug opened package location: " + "[" + classPath + "]");
    }

    void savePropertyFile() {
        System.err.println("file: " + PROPERTYFILE);
        NavajoIOUtil.saveXml(propertyTree, PROPERTYFILE);
    }

    void configureItem2_actionPerformed(ActionEvent e) throws NavajoException {
        // load xml
        if (!userScriptsPath.equals("")) {
            File userDir = new File(userScriptsPath);

            jFileChooser1.setCurrentDirectory(userDir);

        }
        int returnIntval = jFileChooser1.showOpenDialog(this);

        System.err.println("returnIntval = " + returnIntval);
        jFileChooser1.setDialogTitle("set incoming BPFL document (or give name of webservice)");
        if (returnIntval == 0) {
            String filename = jFileChooser1.getSelectedFile().toString();

            System.err.println("DEBUG file= " + filename);
            // TokenString token = new TokenString(filename,new string[]{"."});

            // read the tml file and put the elements in nodes
            Document BPFL_in_doc = NavajoIOUtil.readXml(filename);

            if (BPFL_in_doc == null) {
                showError("unable to read the file: " + filename);
            } else {
                // building the tree
                Node xmlroot = (Node) BPFL_in_doc.getDocumentElement();
                NavajoTreeNode root = createNavajoTreeNode(xmlroot);

                // DefaultTreeModel BPFL_in_Model       = new DefaultTreeModel(root);
                BPFL_in_Model = new DefaultTreeModel(root);
                BPFL_in_Tree.setModel(BPFL_in_Model);
                BPFL_in_FileName = filename;
                System.err.println("incoming BPFL file: " + this.BPFL_in_FileName);

                JFrame j = (JFrame) this.getTopLevelAncestor();
                // j.setTitle("Navajo Studio - " + file);
            }
        }
        NavajoEvent ne = new NavajoEvent();

        ne.setIncomingBPFL(true);
        editPanel.updatePanel(ne);

    }

    public ClassTreeNode setObjectNode(Class map, ClassTreeNode parent, int methodType) {
      return setObjectNode(map, parent, methodType, 1);
    }

    public ClassTreeNode setObjectNode(Class map, ClassTreeNode parent, int methodType, int childDepth) {

        System.out.println("in setObjectNode(), class = " + map.getName());

        try {
            BeanInfo info = Introspector.getBeanInfo(map);
            PropertyDescriptor[] allProperties = info.getPropertyDescriptors();

            for (int i = 0; i < allProperties.length; i++) {
                PropertyDescriptor pd = (PropertyDescriptor) allProperties[i];
                System.out.println(i + ": name = " + pd.getName() + ", read = " + pd.getReadMethod()+ ", write = " + pd.getWriteMethod());
                ClassTreeNode mappableNode;
                Method readMethod = pd.getReadMethod();
                Method writeMethod = pd.getWriteMethod();

                if ((methodType == ALL_METHOD)
                        || ((methodType == SET_METHOD) && (writeMethod != null))
                        || ((methodType == GET_METHOD) && (readMethod != null))) {

                    Class propertyClass;

                    if (pd.getPropertyType().getName().indexOf("[L") != -1) {
                        propertyClass = Class.forName(pd.getPropertyType().getName().substring(2, (pd.getPropertyType().getName().length() - 1)));
                    } else {
                        propertyClass = pd.getPropertyType();
                    }

                    Class[] interfaces = propertyClass.getInterfaces();
                    boolean isMappable = false;

                    for (int j = 0; j < interfaces.length; j++) {
                        // System.out.println("INTERFACE = " + interfaces[j].getName());
                        if (interfaces[j].getName().equals("com.dexels.navajo.mapping.Mappable")) {
                            isMappable = true;
                            break;
                        }
                    }
                    // System.out.println("Adding property: " + pd.getName() + "type = " + pd.getPropertyType().getName());
                    mappableNode = new ClassTreeNode(pd.getPropertyType().getName(), pd.getName());
                    if (isMappable && (childDepth > 0)) {
                        // System.out.println("is mappable");
                        mappableNode = setObjectNode(pd.getPropertyType(), mappableNode, methodType, (childDepth - 1));
                    }
                    parent.add(mappableNode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parent;

    }

    void openButton_actionPerformed(ActionEvent e) throws NavajoException {
        fileItem1_actionPerformed(e);
    }

    void newFileButton_actionPerformed(ActionEvent e) {
        fileItem4_actionPerformed(e);
    }

    // set statusbar
    void saveButton_mouseEntered(MouseEvent e) {
        statusbarLabel.setText("Save File");
        saveButton.setToolTipText("Save File");
    }

    void saveButton_mouseExited(MouseEvent e) {
        statusbarLabel.setText("");
    }

    void openButton_mouseEntered(MouseEvent e) {
        statusbarLabel.setText("Open File");
        openButton.setToolTipText("Open File");
    }

    void openButton_mouseExited(MouseEvent e) {
        statusbarLabel.setText(" ");
    }

    void newFileButton_mouseEntered(MouseEvent e) {
        statusbarLabel.setText("New File");
        newFileButton.setToolTipText("New File");
        newFileButton.setBorder(BorderFactory.createRaisedBevelBorder());
        newFileButton.setBorderPainted(true);
    }

    void newFileButton_mouseExited(MouseEvent e) {
        statusbarLabel.setText(" ");
        newFileButton.setBorderPainted(false);
    }

    void copyButton_mouseEntered(MouseEvent e) {
        statusbarLabel.setText("Copy selected node");
        copyButton.setToolTipText("Copy");
    }

    void copyButton_mouseExited(MouseEvent e) {
        statusbarLabel.setText(" ");
    }

    void pasteButton_mouseEntered(MouseEvent e) {
        statusbarLabel.setText("Paste copied node");
        pasteButton.setToolTipText("Paste");
    }

    void pasteButton_mouseExited(MouseEvent e) {
        statusbarLabel.setText(" ");
    }

    // copy paste
    void editPaste_actionPerformed(ActionEvent e) {
        //if (tabState == 0) {
        //    bpflPanel.editPaste_actionPerformed(e);
        //} else {
            bpclPanel.editPaste_actionPerformed(e);
        //}
    }

    void editCopy_actionPerformed(ActionEvent e) {
        //if (tabState == 0) {
        //    bpflPanel.editCopy_actionPerformed(e);
        //} else {
            bpclPanel.editCopy_actionPerformed(e);
        //}
        copyState = tabState;
        setEditPasteEnabled(true);
    }

    // coppy paste

    void saveButton_actionPerformed(ActionEvent e) {
        // save Xml
        if (BPFLFileName.equals("")) {
            fileItem3_actionPerformed(e);
            isModified = false;
            saveButton.setEnabled(false);
        } else {
            // tml=root.getChildAt(0).;
            System.err.println("file: " + BPFLFileName);
            NavajoIOUtil.saveXml(tmlTree, BPFLFileName);

            System.err.println("file: " + BPCLFileName);
            NavajoIOUtil.saveXml(tslTree, BPCLFileName);
            isModified = false;
            saveButton.setEnabled(false);
        }
    }

    void newFileButton_mousePressed(MouseEvent e) {
        newFileButton.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public JTree getBPCLTree() {
        return tslTree;
    }

    public JTree getBPFLTree() {
        return tmlTree;
    }

    public JTree getBPFL_in_Tree() {
        return BPFL_in_Tree;
    }

    /**
     * Return tree with set methods.
     */
    public JTree getCurrentMappableObjectTreeSet() {
        return currentMappableObjectTreeSet;
    }

    /**
     * Return tree with get methods.
     */
    public JTree getCurrentMappableObjectTree() {
        return currentMappableObjectTree;
    }

    public DefaultTreeModel getBPFLTreeModel() {
        return (DefaultTreeModel) tmlTree.getModel();
    }

    public DefaultTreeModel getBPCLTreeModel() {
        return (DefaultTreeModel) tslTree.getModel();
    }

    public DefaultTreeModel getBPFL_in_TreeModel() {
        return (DefaultTreeModel) BPFL_in_Tree.getModel();
    }

    /**
     * Return tree with get methods.
     */
    public DefaultTreeModel getCurrentMappableObjectTreeModel() {
        return (DefaultTreeModel) currentMappableObjectTree.getModel();
    }

    /**
     * Return tree with set methods.
     */
    public DefaultTreeModel getCurrentMappableObjectTreeModelSet() {
        return (DefaultTreeModel) currentMappableObjectTreeSet.getModel();
    }

    void showMsg(String s) {
        msgTextArea.append(s + "\n");
    }

    void showError(String s) {
        msgTextArea.append("ERROR: " + s + "\n");
    }

    void showStatus(String s) {
        statusbarLabel.setText(s);
    }

    void setEditOk(boolean b) {
        editPanel.setEditOk(b);
    }

    void setNewEdit(boolean b) {
        // Set wheter it is a new entry or not
        editPanel.setNewEdit(b);
    }

    void treesTabbedPane_mouseClicked(MouseEvent e) {
        if (treesTabbedPane.getSelectedIndex() == 0) {
            tabState = 0;
        } else if (treesTabbedPane.getSelectedIndex() == 1) {
            tabState = 1;
        } else {
            tabState = -1;
            showMsg("not implemented, tab panel can only show either BPCL or BPFL tree ");
        }

        if (tabState != copyState) {
            // tab is changed. Dissable paste to prevent pasting nodes from BPCL to BPFL or the other way around
            setEditPasteEnabled(false);
        } else {
            setEditPasteEnabled(true);
        }

    }

    void setEditTitle(int i, boolean newEntry) {
        String prefix = "";

        if (newEntry) {
            prefix = "Add ";
        } else {
            prefix = "Edit ";
        }

        switch (i) {
        //case BPFLPANEL:
        //    editPanel.setTitle(""); // = 1;

        case BPCLTSL:
            editPanel.setTitle("Edit notes");
            break;

        case BPCLPANEL:
            editPanel.setTitle(""); // = 2;
            // bpfl panels
        case BPFLMESSAGE:
            editPanel.setTitle(prefix + "Message");// = 11;
            break;

        case BPFLMETHODS:
            editPanel.setTitle(prefix + "Methods");// = 12;
            break;

        case BPFLPROPERTY:
            editPanel.setTitle(prefix + "Property");// = 13;
            break;

            // bpcl panels
        case BPCLEXPRESSION:
            editPanel.setTitle(prefix + "Expression");// = 21;
            break;

        case BPCLFIELD:
            editPanel.setTitle(prefix + "Field");      // = 22;
            break;

        case BPCLMAP:
            editPanel.setTitle(prefix + "Map");        // = 23;
            break;

        case BPCLMESSAGE:
            editPanel.setTitle(prefix + "Message");    // = 24;
            break;

        case BPCLOBJECT:
            editPanel.setTitle(prefix + "Object");     // = 25;
            break;

        case BPCLPROPERTY:
            editPanel.setTitle(prefix + "Property");   // = 26;
            break;

        case BPCLPARAM:
            editPanel.setTitle(prefix + "Param");   // = 27;
            break;

        default:
            editPanel.setTitle("????"); // unknown panel
            // case editPanel.setTitle("????"); // unknown panel
        }
    }

    public void setEditCopyEnabled(boolean state) {
        editCopy.setEnabled(state);
        copyButton.setEnabled(state);
    }

    public void setEditPasteEnabled(boolean state) {
        editPaste.setEnabled(state);
        pasteButton.setEnabled(state);
    }

    public void setEditDeleteEnabled(boolean state) {
        editDelete.setEnabled(state);
    }

    public void setEditChangeEnabled(boolean state) {
        editChange.setEnabled(state);
    }

    public void editChange_actionPerformed(ActionEvent e) {
        //if (tabState == 0) {
        //    bpflPanel.editButton_actionPerformed(null);
        //} else {
            bpclPanel.editButton_actionPerformed(null);
        //}
    }

    void editDelete_actionPerformed(ActionEvent e) {
        //if (tabState == 0) {
        //    bpflPanel.removeButton_actionPerformed(null);
        //} else {
            bpclPanel.removeButton_actionPerformed(null);
        //}
    }

    void setEditOkStatusMsg(String msg) {
        editPanel.setEditOkStatusMsg(msg);
    }

    void fileItem5_actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
