package com.dexels.navajo.swingclient;

import javax.swing.*;
import com.dexels.navajo.document.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.*;
import com.dexels.navajo.tipi.*;

public class MessagePanel extends JPanel {
  JScrollPane messageScroller = new JScrollPane();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel messagePanel = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();

  private int subMessageCount = 0;
  private int propertyCount = 0;
  private Message myMessage = null;
  TitledBorder titledBorder1;

  public MessagePanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public MessagePanel(Message m) {
    this();
    setMessage(m);
  }

  public void setMessage(Message m) {
    try {
      myMessage = m;
//    messageNameLabel.setText(m.getName());
      titledBorder1.setTitle(m.getName());
      for (int i = 0; i < m.getArraySize(); i++) {
        Message sm = m.getMessage(i);
        addSubMessage(sm);
      }
      ArrayList al = m.getAllProperties();
      for (int i = 0; i < al.size(); i++) {
        Property p = (Property) al.get(i);
        addProperty(p);
      }
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }

  }

  public void addSubMessage(Message m) {
    MessagePanel mp = new MessagePanel(m);
    messagePanel.add(mp,new GridBagConstraints(0, subMessageCount, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),1, 0));
    subMessageCount++;
  }

  public void addProperty(Property p) {
    //BasePropertyComponent mp = new BasePropertyComponent(p);
//    propertyPanel.add(mp,new GridBagConstraints(0, propertyCount, 1, 1, 1.0, 0.0
//            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1),0, 0));
//    propertyCount++;
    //messagePanel.add(mp,new GridBagConstraints(0, subMessageCount, 1, 1, 1.0, 0.0
    //        ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),1, 0));
    //subMessageCount++;
  }

  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"aap");
    this.setLayout(gridBagLayout1);
    messagePanel.setLayout(gridBagLayout2);
    messagePanel.setBorder(titledBorder1);
    this.add(messageScroller,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 392, 166));
    messageScroller.getViewport().add(messagePanel, null);
  }
}