package com.dexels.navajo.tipi.studio.tree;

import javax.swing.JFrame;
import java.awt.HeadlessException;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TestFrame extends JFrame {

  public TestFrame() {
    TipiComponentTreeTestPanel tp = new TipiComponentTreeTestPanel();
    this.getContentPane().add(tp);
    this.pack();
    this.addWindowListener(new WindowListener(){
      public void windowClosing(WindowEvent e){
        System.exit(0);
      }
      public void windowDeactivated(WindowEvent e){}
      public void windowActivated(WindowEvent e){}
      public void windowIconified(WindowEvent e){}
      public void windowDeiconified(WindowEvent e){}
      public void windowClosed(WindowEvent e){}
      public void windowOpened(WindowEvent e){}
    });
  }

  public static void main(String args[]){
    TestFrame t = new TestFrame();
    t.setTitle("Tipi component view");
    t.show();
  }

}