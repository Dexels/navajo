package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import java.awt.event.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.treetable.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiTreeTable
    extends TipiDataComponentImpl {
  private MessageTreeTablePanel myTreeTable;
  private String messagePath;
  private ArrayList myColumns = new ArrayList();
  private ArrayList myColumnNames = new ArrayList();
  public Object createContainer() {
    myTreeTable = new MessageTreeTablePanel();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myTreeTable;
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    System.err.println("---------------------------> Load called in treetable!");
    messagePath = (String) elm.getAttribute("messagepath");
    super.load(elm, instance, context);
    myTreeTable.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        messageTreeTableActionPerformed(e);
      }
    });
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        String label = (String) child.getAttribute("label");
        String name = (String) child.getAttribute("name");
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
       Map tempMap = new HashMap();
      tempMap.put("selectedMessage", myTreeTable.getSelectedMessage());
      performTipiEvent("onActionPerformed", tempMap, false);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
//     System.err.println("--------------------------> LoadData in treetable called!");
//    try {
//      System.err.println("Writing Navajo to sys.err" + n.toString());
//      n.write(System.err);
//    }
//    catch (NavajoException ex) {
//      ex.printStackTrace();
//    }
    if (messagePath != null) {
      Message m = n.getMessage(messagePath);
      if (m != null) {
        myTreeTable.setMessage(m, new String[] {"GameTypeCode", "GameDayCode"});
        myTreeTable.reset();
      }
    }
    else {
      throw new RuntimeException("Shit!");
    }
  }
}
