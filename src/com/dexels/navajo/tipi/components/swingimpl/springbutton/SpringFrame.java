package com.dexels.navajo.tipi.components.swingimpl.springbutton;

import java.awt.*;

import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Frank
 */
public class SpringFrame extends JFrame {
 
    public SpringFrame() {
        super("SpringButton api!");
        getContentPane().setLayout(new FlowLayout());
        //setupGlassPane();
    } 
   
    public static void main(String[] args) {
          SpringFrame sf = new SpringFrame();
          final SpringButton sp = new SpringButton();
          final SpringButton sp2 = new SpringButton();

          sf.getContentPane().add(sp);
          sf.getContentPane().add(sp2);
          sf.setVisible(true);
          sf.setSize(400,250);
    }
    
    
}
