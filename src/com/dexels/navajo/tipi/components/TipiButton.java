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
  public TipiButton() {
//    setEnabled(false);
  }

  public void addComponent(TipiComponent t, TipiContext context, Map td) {
  }

  public void loadData(Navajo n, TipiContext context) {
    myContext = context;
    myNavajo = n;
  }


  public void addTipiEvent(TipiEvent te) {
    myEvent = te;
    te.setNavajo(myNavajo);
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
    setText((String)e.getAttribute("value"));
    setBackground(Color.pink);
    Vector temp = e.getChildren();
    for(int i=0;i<temp.size();i++){
      XMLElement current = (XMLElement)temp.elementAt(i);
      if(current.getName().equals("event")){
        TipiEvent event = new TipiEvent();
        event.load(current,tc);
        addTipiEvent(event);
        myEvent = event;
      }
    }
  }
  public void addComponent(TipiComponent c, TipiContext context) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent method*/
    throw new java.lang.UnsupportedOperationException("Method addComponent() not yet implemented.");
  }
//  public void addTipi(Tipi t, TipiContext context) {
//    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent method*/
//    throw new java.lang.UnsupportedOperationException("Method addTipi() not yet implemented.");
//  }
//  public void addTipiContainer(TipiContainer t, TipiContext context) {
//    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent method*/
//    throw new java.lang.UnsupportedOperationException("Method addTipiContainer() not yet implemented.");
//  }
}