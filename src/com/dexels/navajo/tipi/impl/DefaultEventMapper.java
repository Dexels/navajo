package com.dexels.navajo.tipi.impl;

import java.awt.Component;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultEventMapper implements TipiEventMapper {

  private TipiComponent myComponent = null;

  public DefaultEventMapper() {
  }

  public void registerEvents(TipiComponent tc, ArrayList eventList) {
    myComponent = tc;
    Component c = tc.getContainer();
    for (int i = 0; i < eventList.size(); i++) {
      TipiEvent current = (TipiEvent)eventList.get(i);
      defaultRegisterEvent(c,current);
    }
  }



  private void defaultRegisterEvent(Component c, TipiEvent te) {
//    switch (te.getType()) {
//      case TipiEvent.TYPE_ONACTIONPERFORMED:
    if (te.isTrigger("onActionPerformed")) {
      try{
        java.lang.reflect.Method m = c.getClass().getMethod("addActionListener", new Class[] {ActionListener.class});
          ActionListener bert = new ActionListener(){public void actionPerformed(ActionEvent e) {
            try {
              myComponent.performTipiEvent("onActionPerformed",e);
            }
            catch (TipiException ex) {
              ex.printStackTrace();
            }
          }
        };

        m.invoke(c, new Object[]{bert});
      }catch(Exception exe){
        exe.printStackTrace();
      }
    }

//        break;
//      case TipiEvent.TYPE_ONWINDOWCLOSED:
        if (te.isTrigger("onWindowClosed")) {

        if (JInternalFrame.class.isInstance(c)) {
          JInternalFrame jj = (JInternalFrame) c;
          jj.addInternalFrameListener(new InternalFrameAdapter() {
          public void internalFrameClosing(InternalFrameEvent e) {
            try {
              myComponent.performTipiEvent("onWindowClosed",e);
            }
            catch (TipiException ex) {
              ex.printStackTrace();
            }
          }
          });
        }else if(JFrame.class.isInstance(c)){
          JFrame jj = (JFrame) c;
          jj.addWindowListener(new WindowAdapter(){
            public void windowClosed(WindowEvent e){
              try {
                myComponent.performTipiEvent("onWindowClosed", e);
              }
              catch (TipiException ex) {
                ex.printStackTrace();
              }
            }
          });
        }else{
          throw new RuntimeException("Can not fire onWindowClosed event from class: " + c.getClass());
        }
        }
//        break;
//      case TipiEvent.TYPE_ONMOUSE_ENTERED:
        if (te.isTrigger("onMouseEntered")) {

          c.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
              try {
                myComponent.performTipiEvent("onMouseEntered", e);
              }
              catch (TipiException ex) {
                ex.printStackTrace();
              }
            }
          });
        }
//        break;
//      case TipiEvent.TYPE_ONMOUSE_EXITED:
        if (te.isTrigger("onActionExited")) {

          c.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
              try {
                myComponent.performTipiEvent("onMouseExited", e);
              }
              catch (TipiException ex) {
                ex.printStackTrace();
              }
            }
          });
        }
//        break;
    }
//  }
}