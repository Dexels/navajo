package com.dexels.navajo.tipi.components;

import javax.swing.*;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import java.awt.event.*;
import java.util.*;
import com.dexels.navajo.document.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiMenuItem extends JMenuItem {
  private TipiContext myContext = null;
  private ArrayList myEvents = new ArrayList();

  public TipiMenuItem() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void load(XMLElement x, TipiContext context) throws TipiException{
    myContext = context;
    String name = (String)x.getAttribute("name");
    setText(name);
    Vector w = x.getChildren();
    for (int j = 0; j < w.size(); j++) {
      XMLElement current = (XMLElement)w.get(j);
      TipiEvent te = new TipiEvent();
      te.load(current,context);
      addEvent(te);
    }
  }

  public void addEvent(TipiEvent te) {
    myEvents.add(te);
  }
  private void jbInit() throws Exception {
    this.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
  }

  void this_actionPerformed(ActionEvent e) {
    performEvents(TipiEvent.TYPE_ONACTIONPERFORMED);
  }

  private void performEvent(TipiContext context, TipiEvent te, int type) {
    switch(type) {
      case TipiEvent.TYPE_ONACTIONPERFORMED:
        te.performAction(new Navajo(),this,context);
        break;
    }
  }

  private void performEvents(int type) {
    for (int i = 0; i < myEvents.size(); i++) {
      TipiEvent te = (TipiEvent)myEvents.get(i);
      performEvent(myContext,te,type);
    }

  }


}