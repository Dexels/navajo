package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import tipi.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiWindow
    extends DefaultTipi {

//  private int x, y, w, h;

  public DefaultTipiWindow() {
    initContainer();
   }

  public Container createContainer() {
    JInternalFrame f = new JInternalFrame();
    f.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    return f;
  }

  public void addToContainer(Component c, Object constraints) {
    ((JInternalFrame)getContainer()).getContentPane().add(c,constraints);
  }
  public void setContainerLayout(LayoutManager layout){
    ((JInternalFrame)getContainer()).getContentPane().setLayout(layout);
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    JInternalFrame jj = (JInternalFrame)getContainer();
//    String title = (String)elm.getAttribute("title");

    super.load(elm,instance,context);
//    int x = Integer.parseInt( (String) elm.getAttribute("x", "0"));
//    int y = Integer.parseInt( (String) elm.getAttribute("y", "0"));
//    int w = Integer.parseInt( (String) elm.getAttribute("w", "100"));
//    int h = Integer.parseInt( (String) elm.getAttribute("h", "100"));
//    jj.setTitle(title);
//    jj.setClosable(true);
//    jj.setIconifiable(true);
//    jj.setResizable(true);
//    jj.setBounds(new Rectangle(x, y, w, h));
//    jj.setVisible(true);
  }
  public void setComponentValue(String name, Object object) {
    JInternalFrame jj = (JInternalFrame)getContainer();
    Rectangle r = jj.getBounds();
    if (name.equals("x")) {
      r.x = Integer.parseInt( (String) object);
    }
    if (name.equals("y")) {
     r.y = Integer.parseInt( (String) object);
   }
   if (name.equals("w")) {
     r.width = Integer.parseInt( (String) object);
   }
   if (name.equals("h")) {
     r.height = Integer.parseInt( (String) object);
   }
   if (name.equals("iconifiable")) {
     boolean b = object.equals("true");
     jj.setIconifiable(b);
   }
   if (name.equals("maximizable")) {
     boolean b = object.equals("true");
     jj.setMaximizable(b);
   }
   if (name.equals("closable")) {
     boolean b = object.equals("true");
     jj.setClosable(b);
   }
   if (name.equals("title")) {
    jj.setTitle((String)object);
  }
  if (name.equals("icon")) {
    String icon = (String)object;
//    String icon = (String)instance.getAttribute("icon", null);
//        if(icon!= null){
          try{
            URL i = new URL(icon);
//            JFrame f = new JFrame();
            ImageIcon ic = new ImageIcon(i);
            jj.setFrameIcon(ic);
          }catch(Exception e){
             URL t = MainApplication.class.getResource(icon);
             if(t!=null){
               ImageIcon ii = new ImageIcon(t);
               jj.setFrameIcon(ii);
             }
          }
//        }

  }

   jj.setBounds(r);
  }
}