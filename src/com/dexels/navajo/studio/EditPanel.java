
package com.dexels.navajo.studio;


import java.awt.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.tree.*;

import org.xml.sax.*;
import org.w3c.dom.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class EditPanel extends JPanel {

    protected BaseStudioPanel contentPanel = null;
    protected String title = "";
    protected boolean newEntry = false;
    protected JTextField errorField = new JTextField();
    protected JLabel titleField = new JLabel();
    protected NavajoTreeNode selectedNode = new NavajoTreeNode();

    protected JPanel headerPanel = new JPanel();
    protected JPanel footerPanel = new JPanel();

    private String okStatusMsg = "";

    BorderLayout borderLayout1 = new BorderLayout();
    GridLayout gridLayout1 = new GridLayout();
    VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JButton cancelButton = new JButton();
    JButton okButton = new JButton();
    JButton deleteButton = new JButton();
    TitledBorder titledBorder1;

    public EditPanel(BaseStudioPanel b, String titleName) {
        contentPanel = b;
        title = titleName;
        try {
            jbInit();
            after();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void after() {
        titleField.setText(title);
        // setEditOk(true);//default
    }

    private void jbInit() throws Exception {
        titledBorder1 = new TitledBorder("");
        this.setLayout(borderLayout1);

        footerPanel.setMinimumSize(new Dimension(1, 1));
        footerPanel.setPreferredSize(new Dimension(30, 35));
        footerPanel.setLayout(flowLayout2);
        headerPanel.setMinimumSize(new Dimension(1, 1));
        headerPanel.setPreferredSize(new Dimension(30, 30));
        headerPanel.setLayout(verticalFlowLayout2);
        this.setBorder(titledBorder1);
        this.setMinimumSize(new Dimension(1, 1));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cancelButton_actionPerformed(e);
                    }
                }
                );
        cancelButton.setText("Cancel");
        okButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        okButton_actionPerformed(e);
                    }
                }
                );
        okButton.setText("Ok");
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        okButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        okButton_mouseExited(e);
                    }
                }
                );
        deleteButton.setForeground(Color.red);
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        deleteButton_actionPerformed(e);
                    }
                }
                );
        headerPanel.add(titleField, null);

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(footerPanel, BorderLayout.SOUTH);
        footerPanel.add(cancelButton, null);
        footerPanel.add(okButton, null);
        footerPanel.add(deleteButton, null);

        titleField.setHorizontalTextPosition(SwingConstants.CENTER);
        titleField.setFont(new java.awt.Font("SansSerif", 3, 22));
        titleField.setForeground(Color.blue);
        titleField.setText("Title");
        titleField.setHorizontalAlignment(JLabel.CENTER);

        // ok_cancelPanel.setBackground(Color.yellow);

        this.add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * functions that will link to the functions in contentPanel
     */

    void updatePanel(NavajoEvent ne) {
        contentPanel.updatePanel(ne);
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        contentPanel.cancelButton_actionPerformed(e);
    }

    void okButton_actionPerformed(ActionEvent e) {
        contentPanel.okButton_actionPerformed(e);
    }

    void deleteButton_actionPerformed(ActionEvent e) {
        contentPanel.deleteButton_actionPerformed(e);
    }

    public void changeContentPane(BaseStudioPanel b) {
        if (contentPanel != null) {
            this.remove(contentPanel);
        }
        contentPanel = b;
        this.add(contentPanel, BorderLayout.CENTER);
        after();
        this.revalidate();
    }

    public void setEditOk(boolean b) {
        okButton.setEnabled(b);
        System.err.println(b);
    }

    public void setNewEdit(boolean b) {
        newEntry = b;
        if (newEntry) {
            footerPanel.remove(deleteButton);
        } else {
            footerPanel.add(deleteButton);
        }
    }

    public void setTitle(String s) {
        title = s;
        titleField.setText(title);
    }

    void setEditOkStatusMsg(String msg) {
        okStatusMsg = msg;
    }

    void okButton_mouseEntered(MouseEvent e) {
        contentPanel.getRootPanel().showStatus(okStatusMsg);
    }

    void okButton_mouseExited(MouseEvent e) {
        contentPanel.getRootPanel().showStatus("");
    }
}
