package com.dexels.navajo.swingclient;

import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.nanoclient.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavajoPanel extends JPanel implements ActionListener {
  JToolBar methodBar = new JToolBar();
  BorderLayout borderLayout1 = new BorderLayout();
  MessagePanel rootPanel = null;
  private Navajo myNavajo = null;

  NavajoClient myClient = NavajoClient.getInstance();

  public NavajoPanel(Navajo n) {
    this();
    setNavajo(n);
  }

  public NavajoPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void init(String host, String username, String password) {
    myClient.setServerUrl(host);
    myClient.setUsername(username);
    myClient.setPassword(password);
  }


  public void setNavajo(Navajo n) {
    myNavajo = n;
    Message m = n.getRootMessage();
    ArrayList al = n.getAllMethods();
    for (int i = 0; i < al.size(); i++) {
      Method method = (Method)al.get(i);
      JButton jb = new JButton(method.getName());
      methodBar.add(jb);
      jb.addActionListener(this);
    }
    rootPanel = new MessagePanel(m);
    add(rootPanel,BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  public void clear() {
    remove(rootPanel);
    methodBar.removeAll();
    JButton jb = new JButton("InitUpdateMember");
    methodBar.add(jb);
    jb.addActionListener(this);
  }


  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(methodBar,  BorderLayout.SOUTH);
  }
  public void actionPerformed(ActionEvent e) {
    System.err.println(">>>>"+e.getActionCommand());
    clear();
    try {
      Navajo x = myClient.doSimpleSend(myNavajo,e.getActionCommand());
      setNavajo(x);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }
}