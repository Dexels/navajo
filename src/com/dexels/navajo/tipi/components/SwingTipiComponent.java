package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class SwingTipiComponent extends TipiComponent {

  public void SwingTipiComponent() {
    initContainer();
    setEventMapper(new DefaultEventMapper());
  }
  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
   }

     public void registerEvents() {
       getEventMapper().registerEvents(this,myEventList);
     }

   public void setComponentValue(String name, Object object) {
     if (!JComponent.class.isInstance(getContainer())) {
       return;
     }
     JComponent c = (JComponent)getContainer();

     if (name.equals("background")) {
       c.setBackground(parseColor((String)object));
     }
     if (name.equals("foreground")) {
       c.setForeground(parseColor((String)object));
     }
     if (name.equals("font")) {
       c.setFont(parseFont((String)object));
     }
     if (name.equals("tooltip")) {
       c.setToolTipText((String)object);
     }
     if (name.equals("border")) {
       c.setBorder(parseBorder((String)object));
     }
     if (name.equals("visible")) {
       c.setVisible(parseBoolean((String)object));
     }
     if (name.equals("enabled")) {
       c.setEnabled(parseBoolean((String)object));
     }
   }
   public Object getComponentValue(String name) {
     if (!JComponent.class.isInstance(getContainer())) {
       System.err.println("Sorry, only use JComponent decendants. No awt stuff. Ignoring");
       return null;
     }
     JComponent c = (JComponent)getContainer();
     if (name.equals("tooltip")) {
       return c.getToolTipText();
     }
     return super.getComponentValue(name);
   }

   public boolean parseBoolean(String s) {
     return s.equals("true");
   }

   public Border parseBorder(String s) {
     /** @todo Implement properly */
     return new LineBorder(Color.blue,2);
   }

   public Color parseColor(String s) {
     if (!s.startsWith("#")) {
       throw new RuntimeException("BAD COLOR!");
     }
     String st = s.substring(1);
     int in = Integer.parseInt(st,16);
     return new Color(in);

   }

   public Font parseFont(String s) {
//     String[] ss = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//     for (int i = 0; i < ss.length; i++) {
//       System.err.println(ss[i]);
//
//     }

     StringTokenizer str = new StringTokenizer(s,",");
     String name = str.nextToken();
     int size = Integer.parseInt(str.nextToken());
     int style = Integer.parseInt(str.nextToken());
     //System.err.println("Name: "+name);
     Font f = new Font(name,style,size);
     return f;
   }


}