package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
//import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.document.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiButton extends JButton implements TipiComponent {


  private TipiEvent myEvent =  null;
//  private TipiContainer myParent = null;
  private Navajo myNavajo = null;
  private TipiContext myContext = null;
  private Tipi myTipi = null;
//  public TipiButton() {
//    try {
//      jbInit();
//    }
//    catch(Exception e) {
//      e.printStackTrace();
//    }
//  }

  public void addComponent(TipiComponent t, TipiContext context, Map td) {
  }

  public void setTipi(Tipi t) {
    myTipi = t;
  }
//  public void loadData(Navajo n, TipiContext context) {
//    myContext = context;
//    myNavajo = n;
//  }


  public void addTipiEvent(TipiEvent te, Navajo n) {
    myEvent = te;
    myNavajo = n;
    te.setNavajo(n);
    if (te.getType()==TipiEvent.TYPE_ONACTIONPERFORMED) {
      addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myEvent.performAction(myNavajo,e.getSource(),myContext);
        }
      });
    }
  }

  public void load(XMLElement e, TipiContext tc) {
//    myParent = tc;
    myContext = tc;
    setText((String)e.getAttribute("value"));
    setBackground(Color.pink);
    Vector temp = e.getChildren();
    Navajo n;
    if (myTipi!=null) {
      n = myTipi.getNavajo();
    } else {
     n = new Navajo();
    }

    for(int i=0;i<temp.size();i++){
      XMLElement current = (XMLElement)temp.elementAt(i);
      if(current.getName().equals("event")){
        TipiEvent event = new TipiEvent();
        event.load(current,tc);
        addTipiEvent(event,n);
        myEvent = event;
      }
    }
  }
  public void addComponent(TipiComponent c, TipiContext context) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent method*/
    throw new java.lang.UnsupportedOperationException("Method addComponent() not yet implemented.");
  }
//  private void jbInit() throws Exception {
//    this.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        this_actionPerformed(e);
//      }
//    });
//  }

//  void this_actionPerformed(ActionEvent e) {
//    if (myEvent!=null) {
//      myEvent.performAction(myNavajo,this,myContext);
//    } else {
//      System.err.println("No event attached!");
//    }
//
//  }
//  public void addTipi(Tipi t, TipiContext context) {
//    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent method*/
//    throw new java.lang.UnsupportedOperationException("Method addTipi() not yet implemented.");
//  }
//  public void addTipiContainer(TipiContainer t, TipiContext context) {
//    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent method*/
//    throw new java.lang.UnsupportedOperationException("Method addTipiContainer() not yet implemented.");
//  }
}