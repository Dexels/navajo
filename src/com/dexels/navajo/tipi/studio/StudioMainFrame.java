package com.dexels.navajo.tipi.studio;

import com.dexels.navajo.tipi.studio.components.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StudioMainFrame extends JFrame {
  JMenuBar studioMenuBar = new JMenuBar();
  JMenu fileMenu = new JMenu();
  JMenuItem newFile = new JMenuItem();
  JMenuItem openFile = new JMenuItem();
  JMenuItem exitFile = new JMenuItem();
  JMenu editMenu = new JMenu();
  JMenuItem jMenuItem4 = new JMenuItem();
  JMenuItem jMenuItem5 = new JMenuItem();
  JMenuItem jMenuItem6 = new JMenuItem();
  MainPanel main = new MainPanel();

  public StudioMainFrame() {
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
    this.getContentPane().add(main);

    this.setTitle("TIPI Studio");
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    fileMenu.setText("File");
    newFile.setText("New");
    openFile.setText("Open");
    openFile.addActionListener(new StudioMainFrame_openFile_actionAdapter(this));
    exitFile.setText("Exit");
    editMenu.setText("Edit");
    jMenuItem4.setText("Copy");
    jMenuItem5.setText("Paste");
    jMenuItem6.setText("Preferences");
    this.setJMenuBar(studioMenuBar);
    studioMenuBar.add(fileMenu);
    studioMenuBar.add(editMenu);
    fileMenu.add(newFile);
    fileMenu.add(openFile);
    fileMenu.add(exitFile);
    editMenu.add(jMenuItem4);
    editMenu.add(jMenuItem5);
    editMenu.add(jMenuItem6);
  }

  void openFile_actionPerformed(ActionEvent e) {
    FileDialog fd = new FileDialog(this, "Open file", FileDialog.LOAD);
    fd.show();
    String file = fd.getFile();
    setTitle("TIPI  Studio - " + file);
    main.setFile(file);
  }


}

class StudioMainFrame_openFile_actionAdapter implements java.awt.event.ActionListener {
  StudioMainFrame adaptee;

  StudioMainFrame_openFile_actionAdapter(StudioMainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.openFile_actionPerformed(e);
  }
}