package com.dexels.navajo.beheer;


import java.awt.*;
import javax.swing.*;

import java.sql.*;
import java.util.ResourceBundle;
// import java.util.Random;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.document.*;
import java.util.Vector;
// import java.util.Hashtable;
import com.dexels.navajo.util.*;
import com.dexels.navajo.server.*;
import com.borland.jbcl.layout.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class BeheerFrame extends JFrame {

    public BeheerFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        RootPanel root = new RootPanel();

        this.setDefaultCloseOperation(3);
        this.setTitle("Navajo Maintainance ");
        setSize(600, 400);

        this.getContentPane().add(root);// , BorderLayout.NORTH);
        // System.err.println("count: "+  this.getContentPane().getComponentCount());
    }

    public static void main(String args[]) {
        BeheerFrame b = new BeheerFrame();

        b.setVisible(true);
    }

}
