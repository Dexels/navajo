package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.components.*;
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

public class TipiButton extends TipiComponent {


  private TipiEvent myEvent =  null;
//  private TipiContainer myParent = null;
  private Navajo myNavajo = null;
  private TipiContext myContext = null;
  private Tipi myTipi = null;
  private JButton myButton;

  public TipiButton() {
    setContainer(createContainer());
  }

  public Container getContainer() {
    return myButton;
  }

  public Container createContainer() {
    myButton = new JButton();
    return myButton;
  }

  public void setValue(String s) {
    myButton.setText(s);
    System.err.println("SETTING VALUE OF BUTTON: "+s);
  }

  public void setTipi(Tipi t) {
    myTipi = t;
  }
//  public void loadData(Navajo n, TipiContext context) {
//    myContext = context;
//    myNavajo = n;
//  }

  public void setText(String s) {
    myButton.setText(s);
  }
  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
   }
   public void setContainerLayout(LayoutManager layout){
     throw new UnsupportedOperationException("Can not set layout of container of class: "+getClass());
   }
  public void addTipiEvent(TipiEvent te, Navajo n) {
    myEvent = te;
    myNavajo = n;
    te.setNavajo(n);
    if (te.getType()==TipiEvent.TYPE_ONACTIONPERFORMED) {
      myButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
          myEvent.performAction(myNavajo,e.getSource(),myContext);
        }
      });
    }
  }

  public void load(XMLElement e, XMLElement instance, TipiContext tc) throws TipiException {
//    myParent = tc;
    super.load(e,instance,tc);
    myContext = tc;
    myButton.setText((String)e.getAttribute("value"));
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
}