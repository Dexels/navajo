package com.dexels.navajo.beheer;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;
import java.beans.*;
import javax.swing.border.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ParameterRowPanel extends BaseNavajoPanel {
    JTextField paramValue = new JTextField();
    JTextField paramName = new JTextField();
    int id = -1;
    // int def_id=-1;
    String name = "name";
    String value = "value";
    String condition = "condition";
    String user = "";
    boolean changed = false;

    JButton editButton = new JButton();
    Border border1;
    Border border2;
    GridLayout gridLayout1 = new GridLayout();

    public ParameterRowPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setValue(String val) {
        value = val;
        paramValue.setText(val);
    }

    public ParameterRowPanel(String name1, String value1, String condition1, int id1, RootPanel frame, String User1) {
        rootPanel = frame;
        name = name1;
        value = value1;
        id = id1;
        user = User1;
        condition = condition1;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        border1 = BorderFactory.createEmptyBorder();
        border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(178, 178, 178), new Color(124, 124, 124));
        paramName.setText(name);
        paramValue.setBackground(SystemColor.window);
        paramValue.setBorder(null);
        // paramValue.setBorder(BorderFactory.createLoweredBevelBorder());
        paramValue.setDisabledTextColor(Color.white);
        paramValue.setEditable(false);
        paramValue.setText(value);

        // System.err.println("!!!debug con: "+ condition);

        this.setLayout(gridLayout1);

        paramName.setBackground(SystemColor.window);
        paramName.setBorder(null);
        // paramName.setBorder(BorderFactory.createLoweredBevelBorder());
        paramName.setToolTipText("");
        paramName.setDisabledTextColor(Color.white);
        paramName.setEditable(false);
        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editButton_actionPerformed(e);
                    }
                }
                );
        this.setBackground(SystemColor.window);
        this.setMinimumSize(new Dimension(455, 30));
        this.setPreferredSize(new Dimension(150, 30));
        this.add(paramName, null);
        this.add(paramValue, null);
        this.add(editButton, null);
    }

    void editButton_actionPerformed(ActionEvent e) {
        ParamPanel paramPanel = new ParamPanel(id, name, user, value, condition, rootPanel);

        rootPanel.changeContentPane(paramPanel);
    }
}
