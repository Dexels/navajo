package tipi;

import java.awt.event.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MainFrame extends JFrame implements TopLevel {

  TipiContext c;
  BorderLayout borderLayout1 = new BorderLayout();

  public MainFrame() {
    try {
      c = TipiContext.getInstance();
      c.setToplevel(this);
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    load();
  }

  private void load() {
    try {
      c.parseURL(MainApplication.class.getResource("simplemember.xml"));
      this.getContentPane().add(c.getTopLevel().getContainer(), BorderLayout.CENTER);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.addWindowListener(new MainFrame_this_windowAdapter(this));
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
  }

  public void setTipiMenubar(TipiMenubar tm){
    setJMenuBar((JMenuBar)tm);
  }
}

class MainFrame_this_windowAdapter extends java.awt.event.WindowAdapter {
  MainFrame adaptee;

  MainFrame_this_windowAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}