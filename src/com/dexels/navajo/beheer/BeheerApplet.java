package com.dexels.navajo.beheer;


import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;


public class BeheerApplet extends JApplet {
    boolean isStandalone = false;
    // BeheerFrame beheerFrame = new BeheerFrame();

    RootPanel beheerPanel = new RootPanel();

    /** Get a parameter value*/
    public String getParameter(String key, String def) {
        return isStandalone
                ? System.getProperty(key, def)
                : (getParameter(key) != null ? getParameter(key) : def);
    }

    /** Construct the applet*/
    public BeheerApplet() {}

    /** Initialize the applet*/
    public void init() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Component initialization*/
    private void jbInit() throws Exception {
        this.setSize(new Dimension(400, 300));
        this.getContentPane().add(beheerPanel, BorderLayout.NORTH);
    }

    /** Get Applet information*/
    public String getAppletInfo() {
        return "Applet Information";
    }

    /** Get parameter info*/
    public String[][] getParameterInfo() {
        return null;
    }

    // static initializer for setting look & feel
    static {
        try {// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}
    }
}
