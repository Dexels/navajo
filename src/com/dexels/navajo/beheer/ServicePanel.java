package com.dexels.navajo.beheer;


import java.awt.*;
import javax.swing.*;
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

public class ServicePanel extends BaseNavajoPanel {
    private String serviceName = "service name";
    private String oldServiceName = "";
    private String groupName = "";
    private int id = -1;

    // boolean newEntry=false;
    private JTextField nameField = new JTextField();
    private JLabel idField = new JLabel();
    private JComboBox groupBox = new JComboBox();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel26 = new JLabel();

    private JPanel centerPanel = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    public ServicePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ServicePanel(int serviceId, String serviceName1, RootPanel frame) {
        id = serviceId;
        serviceName = serviceName1;
        oldServiceName = serviceName;

        rootPanel = frame;
        if (id == -1) {
            newEntry = true;
        }
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        jLabel26.setText("ServiceID:");
        jLabel2.setText("Name");
        jLabel4.setText("group");
        nameField.setText(serviceName);
        title = "Service";
        if (newEntry) {
            idField.setText("new");
        } else {
            idField.setText(Integer.toString(id));
        }
        Vector groups = rootPanel.auth.allServiceGroups(rootPanel.access);
        String groupId = rootPanel.auth.simpleSelect(rootPanel.access, "group_id", "services", "id", Integer.toString(id));

        groupName = rootPanel.auth.simpleSelect(rootPanel.access, "name", "service_group", "id", groupId);

        for (int i = 0; i < groups.size(); i++) {
            ServiceGroup sg = (ServiceGroup) groups.get(i);

            groupBox.addItem(sg.name);
        }
        groupBox.setSelectedItem(groupName);

        // String bla = rootPanel.auth.getServlet(rootPanel.access, serviceName);


        centerPanel.setLayout(gridBagLayout1);
        centerPanel.setBackground(Color.yellow);
        centerPanel.add(jLabel26, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(69, 17, 0, 0), 6, 7));
        centerPanel.add(nameField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 99), 145, 0));
        centerPanel.add(idField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(69, 0, 0, 265), 57, 16));
        centerPanel.add(groupBox, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(14, 0, 148, 217), -20, 0));
        centerPanel.add(jLabel4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(17, 17, 148, 21), 8, 0));
        centerPanel.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 17, 0, 6), 21, 0));
        this.setLayout(borderLayout1);
        this.add(centerPanel, BorderLayout.CENTER);
        applyTemplate2();
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        rootPanel.changeToBeheerPanel();
    }

    void deleteButton_actionPerformed(ActionEvent e) {
        try {
            rootPanel.auth.deleteService(rootPanel.access, serviceName);
            rootPanel.changeToBeheerPanel();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            errorWithDb();
        }
    }

    void okButton_actionPerformed(ActionEvent e) {
        // String servlet=(String)servletBox.getSelectedItem();
        serviceName = nameField.getText();
        groupName = (String) groupBox.getSelectedItem();

        if (serviceName.equals("")) {
            error = true;
            errorField.setText("please fill name for this service");
        } else if (groupName.equals("")) {
            error = true;
            errorField.setText("please assign this service to a group");
        }
        try {
            Vector[] serviceNames = rootPanel.auth.select(rootPanel.access, "select name from services", 1);

            for (int i = 0; i < serviceNames[0].size() && !error; i++) {
                if (serviceName.equals((String) serviceNames[0].get(i))
                        && !serviceName.equals(oldServiceName)) {
                    error = true;
                    errorField.setText("Service name allready exist");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            errorWithDb();
        }

        if (!error) {
            int groupId = -1;

            try {
                groupId = Integer.parseInt(rootPanel.auth.simpleSelect(rootPanel.access, "id", "service_group", "name", groupName));
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
                errorWithDb();
            }

            if (newEntry) {
                try {
                    rootPanel.auth.addService(rootPanel.access, serviceName, groupId);
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                    errorWithDb();
                }
            } else {
                try {
                    rootPanel.auth.update(rootPanel.access, "services", new String[] {"name", "group_Id"}, new String[] {serviceName, Integer.toString(groupId)}, new String[] {"id"}, new String[] {Integer.toString(id)}
                            );
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                    errorWithDb();
                }
            }
        }

        if (!error) {// if still no error
            rootPanel.changeToBeheerPanel();
        }
        serviceName = oldServiceName;
        error = false;
    }

}
