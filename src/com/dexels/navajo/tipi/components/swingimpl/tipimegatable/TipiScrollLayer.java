package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import javax.swing.*;
import com.dexels.navajo.tipi.tipixml.*;
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

public class TipiScrollLayer extends TipiMegaTableLayer {


  private int direction = BoxLayout.Y_AXIS;
  private boolean scroll = false;
  public TipiScrollLayer(TipiMegaTable tmt) {
    super(tmt);

  }
  public void loadLayer(XMLElement elt) {
    super.loadLayer(elt);
    /**@todo Implement this com.dexels.navajo.tipi.components.swingimpl.tipimegatable.TipiMegaTableLayer abstract method*/
    messagePath= elt.getStringAttribute("messagePath");
    String direc = elt.getStringAttribute("direction");
    if ("horizontal".equals(direc)) {
      direction = BoxLayout.X_AXIS;
    }
    if ("vertical".equals(direc)) {
      direction = BoxLayout.Y_AXIS;
    }
    scroll = elt.getBooleanAttribute("scroll","true","false",false);
  }

  public void loadData(final Navajo n, Message current, Stack layerStack, final JComponent currentPanel) {
Message nextMessage = null;
    if (current == null) {
      nextMessage = n.getMessage(messagePath);
    } else {
      nextMessage = current.getMessage(messagePath);
    }
    System.err.println("scroll. Loading with nextMessage: "+nextMessage.getName()+" type: "+nextMessage.getType());
    System.err.println("My messagePatH: "+messagePath);
    if (layerStack.isEmpty()) {
      return;
    }
    final Stack newStack = (Stack)layerStack.clone();
    final TipiMegaTableLayer nextLayer = (TipiMegaTableLayer)newStack.pop();
    final Message msg = nextMessage;
    myTable.runASyncInEventThread(new Runnable() {
              public void run() {

                JPanel jt = new JPanel();
                BoxLayout myLayout = new BoxLayout(jt,direction);
                jt.setLayout(myLayout);
                if (scroll) {
                  JScrollPane jj = new JScrollPane(jt);
                  currentPanel.add(jj,BorderLayout.CENTER);
                }
                else {
                  currentPanel.add(jt,BorderLayout.CENTER);
                }
                for (int i = 0; i < msg.getArraySize(); i++) {
                  Message cc = msg.getMessage(i);
//      System.err.println("Got message: ");
//      cc.write(System.err);
                  System.err.println("Looking for property: "+titleProperty);
                  Property titleProp = cc.getProperty(titleProperty);
                  String title = titleProp.getValue();
                  JPanel newPanel = new JPanel();
                  newPanel.setLayout(new BorderLayout());
                  newPanel.setBorder(BorderFactory.createTitledBorder(title));
                  jt.add(newPanel);
                  nextLayer.loadData(n,cc,newStack,newPanel);
                }

              }
            });
  }



  public XMLElement store() {

    XMLElement newElt = super.store();
    newElt.setAttribute("type","scroll");
    switch (direction) {
      case BoxLayout.X_AXIS:
        newElt.setAttribute("direction","horizontal");
        break;
      case BoxLayout.Y_AXIS:
        newElt.setAttribute("direction","vertical");
        break;
    }
    newElt.setAttribute("scroll",scroll?"true":"false");
    return newElt;
  }

}
