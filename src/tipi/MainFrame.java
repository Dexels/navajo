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
  private String myXML = "test.xml";

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

  public MainFrame(String fileName) {
    try {
      myXML = fileName;
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
      //System.err.println("Parsing URL");
      System.err.println("Loading with: " + myXML);
      c.parseURL(MainApplication.class.getResource(myXML));
      this.getContentPane().add(c.getTopScreen().getContainer(), BorderLayout.CENTER);
      //System.err.println("Topscreen added");
      TipiBase tb = c.getTipiComponentByPath("/desktop/memberwindow/member_query/query_button");
      if (tb!=null) {
        tb.setValue("text","hoera");
        System.err.println("YIPEE!");
        System.err.println(tb.getName());
      }
      tb = c.getTipiComponentByPath("/desktop");
      if (tb!=null) {
        System.err.println("YIPEE!");
        System.err.println(tb.getName());
      }
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