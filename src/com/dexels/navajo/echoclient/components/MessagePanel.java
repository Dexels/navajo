package com.dexels.navajo.echoclient.components;

import nextapp.echo.Panel;
import com.dexels.navajo.document.*;
import java.util.*;
import nextapp.echo.*;

public class MessagePanel extends Panel {
  public void setMessage(Message m) {

    if (Message.MSG_TYPE_ARRAY.equals(m.getType())) {
      try {
        createArrayMessage(m);
      }
      catch (Error ex1) {
        ex1.printStackTrace();
      }
    } else {
      Label l = new Label();
      l.setText("Message: "+m.getFullMessageName());
      add(l);
      ArrayList msgs = m.getAllMessages();
      ArrayList props = m.getAllProperties();
      try {
        setSubMessages(msgs);
        setProperties(props);
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
    }

  }

  private void createArrayMessage(Message m) {
    if (m.getArraySize()==0) {
      System.err.println("Empty");
      return;
    }
    Message sub = m.getMessage(0);
    ArrayList al = sub.getAllProperties();
    MessageTable mt = new MessageTable();
    for (int i = 0; i < al.size(); i++) {
      Property p = (Property)al.get(i);
      String descr = p.getDescription();
      if (descr==null) {
        descr = p.getName();
      }
      try {
        System.err.println("Adding column: " + descr);
      }
      catch (Error ex1) {
        ex1.printStackTrace();
      }
      mt.addColumn(p.getName(),descr,false);
    }
    mt.setMessage(m);
    System.err.println("# of properties: "+al.size());
    System.err.println("# of rows: "+m.getArraySize());
    add(mt);

    mt.debugTableModel();
    System.err.println("After debug");
    try {
      mt.getMessageTableModel().fireTableStructureChanged();
    }
    catch (Error ex) {
      ex.printStackTrace();
    }
    mt.createDefaultColumnsFromModel();
    System.err.println("After fire!");
  }

  private void setSubMessages(ArrayList al) {
    for (int i = 0; i < al.size(); i++) {
      Message m = (Message)al.get(i);
      MessagePanel mp = new MessagePanel();
      mp.setMessage(m);
      add(mp);
    }

  }

  private void setProperties(ArrayList al) throws NavajoException {
    for (int i = 0; i < al.size(); i++) {
      Property p = (Property)al.get(i);
      EchoPropertyComponent pc = new EchoPropertyComponent();
      pc.setProperty(p);
      add(pc);
    }
  }
}
