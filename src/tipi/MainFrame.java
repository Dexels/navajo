package tipi;

import java.awt.event.*;
import com.dexels.navajo.tipi.*;
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

public class MainFrame extends JFrame {

  TipiContext c;
  BorderLayout borderLayout1 = new BorderLayout();

  public MainFrame() {
    try {
      c = TipiContext.getInstance();
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    c.parseURL(MainApplication.class.getResource("vla.xml"));
    this.getContentPane().setLayout(borderLayout1);
    this.addWindowListener(new MainFrame_this_windowAdapter(this));
    this.getContentPane().add((JComponent)c.getTopLevel(), BorderLayout.CENTER);
    this.setTitle("TIPI Demo");
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
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