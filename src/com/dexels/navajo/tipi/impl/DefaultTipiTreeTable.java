package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.treetable.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiTreeTable extends DefaultTipi {
  private MessageTreeTablePanel myTreeTable;
  private String messagePath;
  private ArrayList myColumns = new ArrayList();
  private ArrayList myColumnNames = new ArrayList();

  public DefaultTipiTreeTable() {
    initContainer();
  }

  public void addToContainer(Component parm1, Object parm2) {
    throw new RuntimeException("Adding to DefaultTipiTreeTable?!");
  }

  public Container createContainer() {
    myTreeTable = new MessageTreeTablePanel();
    return myTreeTable;
  }
  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    System.err.println("---------------------------> Load called in treetable!");
    messagePath = (String)elm.getAttribute("messagepath");
    super.load(elm,instance,context);
    myTreeTable.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        messageTreeTableActionPerformed(e);
      }
    });
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        String label = (String)child.getAttribute("label");
        String name = (String)child.getAttribute("name");
//      boolean editable = (((String)child.getAttribute("editable")).equals("true"));
        boolean editable = false;
        myColumns.add(name);
        myColumnNames.add(label);
//        myTreeTable.addColumn(name,label,editable);
      }
    }
  }

  public void messageTreeTableActionPerformed(ActionEvent ae) {
    try {
      performAllEvents(TipiEvent.TYPE_ONACTIONPERFORMED,ae);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
     System.err.println("--------------------------> LoadData in treetable called!");
    try {
      System.err.println("Writing Navajo to sys.err" + n.toString());
      n.write(System.err);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
     if(messagePath != null){
      Message m = n.getMessage(messagePath);
      if (m != null) {
         myTreeTable.setMessage(m,new String[]{"GameTypeCode","GameDayCode"});
        myTreeTable.reset();
      }
    } else throw new RuntimeException("Shit!");
  }

}