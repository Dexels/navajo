package com.dexels.navajo.beheer;


import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.*;
import com.dexels.navajo.server.Parameter;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ParamPanel extends BaseNavajoPanel {
    private JTextField valueField = new JTextField();
    private JLabel nameLabel = new JLabel();
    private JLabel valueLabel = new JLabel();
    private JLabel idLabel = new JLabel();
    private JLabel paramId = new JLabel();
    private JLabel typeLabel = new JLabel();

    private String value = "value";
    private String condition = "condition";
    private int id = 0;
    private String type = "type";
    private String definition = "0";
    // String name ="name";
    private String user = "";

    private JLabel typeField = new JLabel();
    private JTextField conditionField = new JTextField();
    private JLabel valueLabel1 = new JLabel();

    private JPanel centerPanel = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JComboBox paramComboBox = new JComboBox();
    private Vector[] definitionIdAndNameAndType = new Vector[3];
    XYLayout xYLayout1 = new XYLayout();

    public ParamPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ParamPanel(int id1, String definition1, String user1, String value1, String condition1, RootPanel frame) {
        rootPanel = frame;
        id = id1;
        System.err.println("DEBUG ParamPanel ID = " + id);

        if (id == -1) {
            newEntry = true;
        }
        definition = definition1;
        user = user1;
        value = value1;
        condition = condition1;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        title = "Parameter for " + user;
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setText("Parameter name:");
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        valueLabel.setText("Value:");
        idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        idLabel.setText("Parameter ID:");
        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeLabel.setText("Value type:");
        paramId.setToolTipText("");
        paramId.setText("paramID");

        valueField.setText(value);
        conditionField.setText(condition);

        // fill the param name list
        definitionIdAndNameAndType = rootPanel.auth.select(rootPanel.access, new String[] {"parameter_id", "name", "type"}, "definitions", null, null);
        for (int i = 0; i < definitionIdAndNameAndType[1].size(); i++) {
            String name = (String) definitionIdAndNameAndType[1].get(i);

            paramComboBox.addItem(name);
        }

        if (newEntry) {
            paramId.setText("new");
        } else {
            paramId.setText(Integer.toString(id));
            paramComboBox.setSelectedItem(definition);
        }

        valueLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        valueLabel1.setText("Condition:");

        centerPanel.setLayout(xYLayout1);
        centerPanel.setBackground(Color.yellow);
        paramComboBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        paramComboBox_actionPerformed(e);
                    }
                }
                );
        centerPanel.add(valueField, new XYConstraints(136, 129, 150, 17));
        centerPanel.add(conditionField, new XYConstraints(136, 157, 150, 17));
        centerPanel.add(valueLabel1, new XYConstraints(71, 158, -1, -1));
        centerPanel.add(nameLabel, new XYConstraints(19, 55, 107, -1));
        centerPanel.add(valueLabel, new XYConstraints(74, 130, 53, -1));
        centerPanel.add(paramComboBox, new XYConstraints(134, 55, 113, 16));
        centerPanel.add(typeLabel, new XYConstraints(19, 104, 107, -1));
        centerPanel.add(idLabel, new XYConstraints(29, 87, 98, -1));
        centerPanel.add(paramId, new XYConstraints(134, 86, 150, -1));
        centerPanel.add(typeField, new XYConstraints(136, 104, 150, 17));
        this.setLayout(borderLayout1);
        this.add(centerPanel, BorderLayout.CENTER);
        update();
        applyTemplate2();
    }

    void okButton_actionPerformed(ActionEvent e) {
        value = valueField.getText();
        condition = conditionField.getText();
        definition = (String) paramComboBox.getSelectedItem();
        if (value.equals("")) {
            error = true;
            errorField.setText("Please insert a value");
        }
        if (!error) {
            if (newEntry) {
                try {
                    rootPanel.auth.addValue(rootPanel.access, user, definition, value, condition);
                    UserPanel u = new UserPanel(user, rootPanel, UserPanel.PARAM_TAB);

                    rootPanel.changeContentPane(u);
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                    errorWithDb();
                }
            } else {
                try {
                    System.err.println("BEHEER:PARAMPANEL-DEBUG:ID = " + id);
                    rootPanel.auth.update(
                            rootPanel.access, "parameters", new String[] {"value", "condition"},
                            new String[] {value, condition}, new String[] {"id"}, new String[] {Integer.toString(id)}
                            );

                    UserPanel u = new UserPanel(user, rootPanel, UserPanel.PARAM_TAB);

                    rootPanel.changeContentPane(u);
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                    errorWithDb();
                }
            }
        }
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        // UserPanel u = new UserPanel (user_id, rootPanel.auth, rootPanel.access, rootPanel);
        UserPanel u = new UserPanel(user, rootPanel, UserPanel.PARAM_TAB);

        rootPanel.changeContentPane(u);
    }

    void deleteButton_actionPerformed(ActionEvent e) {
        try {
            rootPanel.auth.deleteValue(rootPanel.access, id);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        UserPanel u = new UserPanel(user, rootPanel, UserPanel.PARAM_TAB);

        rootPanel.changeContentPane(u);
    }

    private void paramComboBox_actionPerformed(ActionEvent e) {
        update();
    }

    private void update() {
        int selectedId = paramComboBox.getSelectedIndex();

        typeField.setText((String) definitionIdAndNameAndType[2].get(selectedId));
    }

}
