package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.swingclient.components.treetable.*;
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
    extends TipiSwingDataComponentImpl {
  private MessageTreeTablePanel myTreeTable;
  private String messagePath;
  private List<String> myColumns = new ArrayList<String>();
  private List<String> myColumnNames = new ArrayList<String>();
  public Object createContainer() {
    myTreeTable = new MessageTreeTablePanel();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myTreeTable;
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
     messagePath = (String) elm.getAttribute("messagepath");
    super.load(elm, instance, context);
    myTreeTable.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        messageTreeTableActionPerformed(e);
      }
    });
    List<XMLElement> children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = children.get(i);
      if (child.getName().equals("column")) {
        String label = (String) child.getAttribute("label");
        String name = (String) child.getAttribute("name");
          myColumns.add(name);
        myColumnNames.add(label);
      }
    }
  }

  public void messageTreeTableActionPerformed(ActionEvent ae) {
    try {
       Map<String,Object> tempMap = new HashMap<String,Object>();
      tempMap.put("selectedMessage", myTreeTable.getSelectedMessage());
      performTipiEvent("onActionPerformed", tempMap, false);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void loadData(Navajo n,  String method) throws TipiException {
	  // NO super?!
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
