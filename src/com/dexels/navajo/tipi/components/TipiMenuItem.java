package com.dexels.navajo.tipi.components;

import javax.swing.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
import java.awt.event.*;
import java.util.*;
import com.dexels.navajo.document.*;
import java.awt.Container;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiMenuItem extends SwingTipiComponent {
  private TipiContext myContext = null;
  private ArrayList myEvents = new ArrayList();
  private JMenuItem myItem;

  public TipiMenuItem() {
    try {
      initContainer();
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void load(XMLElement x, TipiContext context) throws TipiException{
    myContext = context;
    String name = (String)x.getAttribute("name");
    ((JMenuItem)getContainer()).setText(name);
    Vector w = x.getChildren();
    for (int j = 0; j < w.size(); j++) {
      XMLElement current = (XMLElement)w.get(j);
      TipiEvent te = new TipiEvent();
      te.load(null,current,context);
      addEvent(te);
    }
  }

  public void addEvent(TipiEvent te) {
    myEvents.add(te);
  }
  private void jbInit() throws Exception {
    ((JMenuItem)getContainer()).addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
  }

  void this_actionPerformed(ActionEvent e) {
    try {
      performEvents(TipiEvent.TYPE_ONACTIONPERFORMED,e);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  private void performEvent(TipiContext context, TipiEvent te, int type, Object event) throws TipiException{
    switch(type) {
      case TipiEvent.TYPE_ONACTIONPERFORMED:
        te.performAction(NavajoFactory.getInstance().createNavajo(),this,context,event);
        break;
    }
  }

  private void performEvents(int type, Object event)  throws TipiException{
    for (int i = 0; i < myEvents.size(); i++) {
      TipiEvent te = (TipiEvent)myEvents.get(i);
      performEvent(myContext,te,type,event);
    }

  }
  public Container createContainer() {
    myItem = new JMenuItem();
    return myItem;
  }

  public Container getContainer(){
    return myItem;
  }
}