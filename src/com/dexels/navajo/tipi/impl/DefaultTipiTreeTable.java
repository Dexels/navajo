package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.treetable.*;
import nanoxml.*;
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

public class DefaultTipiTreeTable
    extends DefaultTipi {
  private MessageTreeTablePanel myTreeTable;
  private String messagePath;
  public DefaultTipiTreeTable() {
    setContainer(createContainer());
  }

  public void addToContainer(Component parm1, Object parm2) {
    throw new RuntimeException("Adding to DefaultTipiTreeTable?!");
  }

  public Container createContainer() {
    myTreeTable = new MessageTreeTablePanel();
    return myTreeTable;
  }
  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
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
        myTreeTable.addColumn(name,label,editable);
      }
    }
  }

  public void messageTreeTableActionPerformed(ActionEvent ae) {
    performAllEvents(TipiEvent.TYPE_ONACTIONPERFORMED);
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
    super.loadData(n,tc);
     if(messagePath != null){
      Message m = n.getByPath(messagePath);
      if (m != null) {
        myTreeTable.setMessage(m);
      }
    }
  }

}