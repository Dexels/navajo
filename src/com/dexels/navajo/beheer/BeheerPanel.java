package com.dexels.navajo.beheer;


import javax.swing.*;
import java.awt.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

import java.sql.*;
import java.util.ResourceBundle;
// import java.util.Random;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.document.*;
import java.util.Vector;
// import java.util.Hashtable;
import com.dexels.navajo.util.*;
import com.dexels.navajo.server.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class BeheerPanel extends BaseNavajoPanel {

    // here we have 4 vector[] to contain the data from the Navajo database
    // the 1st vector consist the id (in [0]) and name in ([1]) from the tabel users
    // the 2nd vector consist the id (in [0]) and name in ([1]) from the tabel services
    // the 3rd vector consist the id (in [0]) and name in ([1]) definitions
    // the 4th vector consist the id (in [0]) and name in ([1]) servicegroups
    private Vector[] userIdAndName = new Vector[2];
    private Vector[] serviceIdAndName = new Vector[2];
    private Vector[] definitionIdAndName = new Vector[2];
    private Vector[] groupIdAndName = new Vector[2];

    private JComboBox serviceList = new JComboBox();
    private JComboBox userList = new JComboBox();
    private JLabel jLabel8 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JButton editServiceButton = new JButton();
    private JButton createServiceButton = new JButton();
    private JButton createUserButton = new JButton();
    private JButton editUserButton = new JButton();
    private JButton exitButton = new JButton();
    private JComboBox definitionList = new JComboBox();
    private JButton createDefButton = new JButton();
    private JButton editDefButton = new JButton();
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JButton editGroupButton = new JButton();
    private JButton createGroupButton = new JButton();
    private JComboBox groupList = new JComboBox();
    private JPanel centerPanel = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    public BeheerPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BeheerPanel(RootPanel root) {
        rootPanel = root;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        title = "Navajo Maintainance Index";
        // Connection c = null;
        try {
            // get id and name from the tabel users
            userIdAndName = rootPanel.auth.select(rootPanel.access, new String[] {"id", "name"}, "users", null, null);

            // get id and name from the tabel services
            serviceIdAndName = rootPanel.auth.select(rootPanel.access, new String[] {"id", "name"}, "services", null, null);

            // get id and name from the tabel definitions
            definitionIdAndName = rootPanel.auth.select(rootPanel.access, new String[] {"parameter_id", "name"}, "definitions", null, null);

            // get id and name from the tabel service_group
            groupIdAndName = rootPanel.auth.select(rootPanel.access, new String[] {"id", "name"}, "service_group", null, null);

            for (int i = 0; i < serviceIdAndName[1].size(); i++) {
                String service = (String) serviceIdAndName[1].get(i);

                // System.err.println("service: "+service);
                serviceList.addItem(service);
            }
            // Vector b = auth.allUsers(access);

            for (int i = 0; i < userIdAndName[1].size(); i++) {
                String user = (String) userIdAndName[1].get(i);

                // System.err.println("user: "+user);
                userList.addItem(user);
            }

            for (int i = 0; i < definitionIdAndName[1].size(); i++) {
                String definition = (String) definitionIdAndName[1].get(i);

                // System.err.println("definition: "+definition);
                definitionList.addItem(definition);
            }

            for (int i = 0; i < groupIdAndName[1].size(); i++) {
                String group = (String) groupIdAndName[1].get(i);

                // System.err.println("definition: "+definition);
                groupList.addItem(group);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        editUserButton.setText("Edit");
        editUserButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editUserButton_actionPerformed(e);
                    }
                }
                );
        createUserButton.setText("Create new");
        createUserButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        createUserButton_actionPerformed(e);
                    }
                }
                );
        createServiceButton.setText("Create new");
        createServiceButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        createServiceButton_actionPerformed(e);
                    }
                }
                );
        editServiceButton.setText("Edit");
        editServiceButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editServiceButton_actionPerformed(e);
                    }
                }
                );
        jLabel7.setText("Service");
        jLabel8.setText("User");

        exitButton.setFont(new java.awt.Font("Dialog", 1, 18));
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        exitButton_actionPerformed(e);
                    }
                }
                );
        createDefButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        createDefButton_actionPerformed(e);
                    }
                }
                );
        createDefButton.setText("Create new");
        editDefButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editDefButton_actionPerformed(e);
                    }
                }
                );
        editDefButton.setText("Edit");
        jLabel9.setText("Parameter");
        jLabel10.setText("Service group");
        editGroupButton.setText("Edit");
        editGroupButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editGroupButton_actionPerformed(e);
                    }
                }
                );
        createGroupButton.setText("Create new");
        createGroupButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        createGroupButton_actionPerformed(e);
                    }
                }
                );

        this.setLayout(borderLayout1);
        centerPanel.setLayout(gridBagLayout1);
        centerPanel.setBackground(Color.yellow);
        centerPanel.add(jLabel8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(20, 15, 0, 0), 56, 0));
        centerPanel.add(jLabel7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 15, 0, 0), 43, 0));
        centerPanel.add(userList, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 0, 0, 0), 54, -4));
        centerPanel.add(serviceList, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 54, -4));
        centerPanel.add(editUserButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 17, 0, 10), 17, -10));
        centerPanel.add(createUserButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 0, 0, 46), 34, -10));
        centerPanel.add(createServiceButton, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 46), 34, -10));
        centerPanel.add(editServiceButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 17, 0, 10), 17, -10));
        centerPanel.add(exitButton, new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 67, 10, 0), 143, -1));
        centerPanel.add(createGroupButton, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(12, 0, 0, 46), 34, -10));
        centerPanel.add(jLabel10, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 15, 0, 0), 8, 0));
        centerPanel.add(editGroupButton, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(12, 17, 0, 10), 17, -10));
        centerPanel.add(groupList, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(12, 0, 0, 0), 54, -4));
        centerPanel.add(createDefButton, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 46), 34, -10));
        centerPanel.add(jLabel9, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 15, 0, 0), 25, 0));
        centerPanel.add(definitionList, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 54, -4));
        centerPanel.add(editDefButton, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 17, 0, 10), 17, -10));
        this.add(centerPanel, BorderLayout.CENTER);
        applyTemplate1();
    }

    void editUserButton_actionPerformed(ActionEvent e) {
        String selectedName = (String) userList.getSelectedItem();
        UserPanel userPanel = new UserPanel(selectedName, rootPanel, UserPanel.AUTHORISE_TAB);

        rootPanel.changeContentPane(userPanel);
    }

    void createUserButton_actionPerformed(ActionEvent e) {
        UserPanel userPanel = new UserPanel("", rootPanel, UserPanel.AUTHORISE_TAB);

        rootPanel.changeContentPane(userPanel);
    }

    void editServiceButton_actionPerformed(ActionEvent e) {
        int selectedId = Integer.parseInt((String) serviceIdAndName[0].get(serviceList.getSelectedIndex()));
        String selectedName = (String) serviceList.getSelectedItem();
        ServicePanel s = new ServicePanel(selectedId, selectedName, rootPanel);

        rootPanel.changeContentPane(s);
    }

    void createServiceButton_actionPerformed(ActionEvent e) {
        ServicePanel s = new ServicePanel(-1, "new", rootPanel);

        rootPanel.changeContentPane(s);
    }

    void exitButton_actionPerformed(ActionEvent e) {
        LoginPanel lp = new LoginPanel(rootPanel);

        rootPanel.changeContentPane(lp);
        rootPanel.logout();
    }

    void createDefButton_actionPerformed(ActionEvent e) {
        // String selectedId = (String)definitionIdAndName[0].get(definitionList.getSelectedIndex());
        DefinitionPanel d = new DefinitionPanel(-1, "new", rootPanel);

        rootPanel.changeContentPane(d);
    }

    void editDefButton_actionPerformed(ActionEvent e) {
        int selectedId = Integer.parseInt((String) definitionIdAndName[0].get(definitionList.getSelectedIndex()));
        String selectedName = (String) definitionList.getSelectedItem();
        DefinitionPanel d = new DefinitionPanel(selectedId, selectedName, rootPanel);

        rootPanel.changeContentPane(d);
    }

    void editGroupButton_actionPerformed(ActionEvent e) {
        int selectedId = Integer.parseInt((String) groupIdAndName[0].get(groupList.getSelectedIndex()));
        String selectedName = (String) groupList.getSelectedItem();
        ServiceGroupPanel sg = new ServiceGroupPanel(rootPanel, selectedName, selectedId);

        rootPanel.changeContentPane(sg);

    }

    void createGroupButton_actionPerformed(ActionEvent e) {
        ServiceGroupPanel sg = new ServiceGroupPanel(rootPanel, "", -1);

        // -1, "new", rootPanel);
        rootPanel.changeContentPane(sg);
    }

}

