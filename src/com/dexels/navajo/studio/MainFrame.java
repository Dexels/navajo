package com.dexels.navajo.studio;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import com.sun.java.swing.plaf.motif.*;

import org.apache.crimson.jaxp.DocumentBuilderFactoryImpl;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class MainFrame extends JFrame {

    public MainFrame() {
        DocumentBuilderFactoryImpl dummy = new DocumentBuilderFactoryImpl();
        try {
            this.jbInit();
        } catch (Exception ex) {
            System.err.println("!!!ERROR!!! in class MainFrame: MainFrame()");
        }
    }

    private void jbInit() throws Exception {
        // initialize the userinterface
        RootStudioPanel studio = new RootStudioPanel();

        // NavajoStudioPanel studio = new NavajoStudioPanel();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Navajo Studio ");
        this.setSize(800, 640);
        // this.getContentPane().add(root);
        this.getContentPane().add(studio);

        // setJMenuBar(root.getMenuBar());
        setJMenuBar(studio.getMenuBar());
    }

    public static void main(String args[]) {
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
            System.err.println("UIManager look&feel: " + UIManager.getSystemLookAndFeelClassName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        MainFrame m = new MainFrame();

        m.setVisible(true);

    }

}
