package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
import java.util.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiTabLayer extends TipiMegaTableLayer {

  private int direction = JTabbedPane.TOP;
  private int tabLayout = JTabbedPane.WRAP_TAB_LAYOUT;
  public TipiTabLayer(TipiMegaTable tmt) {
    super(tmt);

  }
  public void loadLayer(XMLElement elt) {
    super.loadLayer(elt);
    /**@todo Implement this com.dexels.navajo.tipi.components.swingimpl.tipimegatable.TipiMegaTableLayer abstract method*/
    messagePath= elt.getStringAttribute("messagePath");
    String direc = elt.getStringAttribute("direction");
    if ("top".equals(direc)) {
      direction = JTabbedPane.TOP;
    }
    if ("left".equals(direc)) {
      direction = JTabbedPane.LEFT;
    }
    if ("right".equals(direc)) {
      direction = JTabbedPane.RIGHT;
    }
    if ("bottom".equals(direc)) {
      direction = JTabbedPane.BOTTOM;
    }
    String layout = elt.getStringAttribute("layout");
    if ("scroll".equals(layout)) {
      tabLayout = JTabbedPane.SCROLL_TAB_LAYOUT;
    }
    if ("wrap".equals(layout)) {
       tabLayout = JTabbedPane.WRAP_TAB_LAYOUT;
     }

  }

  public void loadData(final Navajo n, Message current, Stack layerStack, final JComponent currentPanel) {
Message nextMessage = null;
    if (current == null) {
      nextMessage = n.getMessage(messagePath);
    } else {
      nextMessage = current.getMessage(messagePath);
    }
//    System.err.println("Tab. Loading with nextMessage: "+nextMessage.getName()+" type: "+nextMessage.getType());
//    System.err.println("My messagePatH: "+messagePath);
    if (layerStack.isEmpty()) {
      return;
    }
    final Message msg = nextMessage;
    final Stack newStack = (Stack)layerStack.clone();
    final TipiMegaTableLayer nextLayer = (TipiMegaTableLayer)newStack.pop();
//    System.err.println("Tab. My stack: "+layerStack);
    myTable.runASyncInEventThread(new Runnable() {
              public void run() {
                JTabbedPane jt = new JTabbedPane();
                jt.setTabPlacement(direction);
                jt.setTabLayoutPolicy(tabLayout);
               currentPanel.add(jt,BorderLayout.CENTER);
               if (msg != null) {
               for (int i = 0; i < msg.getArraySize(); i++) {
                 Message cc = msg.getMessage(i);
//      System.err.println("Got message: ");
//      cc.write(System.err);
                 //System.err.println("Looking for property: "+titleProperty);
                 Property titleProp = cc.getProperty(titleProperty);
//                 String title = titleProp.getValue();

                 String title = null;
                 if (titleProp!=null) {
                   System.err.println("*********\nDEPRECATED: You used only a propertyname as title in your scroll layer, in TipiMegaTabel\nYou should just use an expression..\n********");
                   title = titleProp.getValue();
                 } else {
                   Operand titleOperand = myTable.getContext().evaluate(titleProperty,myTable,null,cc.getRootDoc(),cc);
                   if (titleOperand!=null) {
                     title = ""+titleOperand.value;
                   }
                 }


                 JPanel newPanel = new JPanel();
                 newPanel.setLayout(new BorderLayout());
                 jt.addTab(title,newPanel);
                 nextLayer.loadData(n,cc,newStack,newPanel);
               }
               }
              }
            });
  }



  public XMLElement store() {

    XMLElement newElt = super.store();
    newElt.setAttribute("type","tab");
    switch (direction) {
      case JTabbedPane.TOP:
        newElt.setAttribute("direction","top");
        break;
      case JTabbedPane.BOTTOM:
        newElt.setAttribute("direction","bottom");
        break;
      case JTabbedPane.LEFT:
        newElt.setAttribute("direction","left");
        break;
      case JTabbedPane.RIGHT:
        newElt.setAttribute("direction","right");
        break;
    }
    switch (tabLayout) {
      case JTabbedPane.WRAP_TAB_LAYOUT:
        newElt.setAttribute("layout","wrap");
        break;
      case JTabbedPane.SCROLL_TAB_LAYOUT:
        newElt.setAttribute("layout","scroll");
        break;
    }
    return newElt;
  }

}
