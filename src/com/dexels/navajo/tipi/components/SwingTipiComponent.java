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
  public void removeFromContainer(Component c) {
    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
  }

     public void registerEvents() {
       getEventMapper().registerEvents(this,myEventList);
     }

   public void setComponentValue(String name, Object object) {
     if (!JComponent.class.isInstance(getContainer())) {
       if (name.equals("visible")) {
         getContainer().setVisible(((Boolean)object).booleanValue());
       }
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
       c.setVisible(((Boolean)object).booleanValue());
     }
     if (name.equals("enabled")) {
       c.setEnabled(((Boolean)object).booleanValue());
     }
   }
   public Object getComponentValue(String name) {
     if (!JComponent.class.isInstance(getContainer())) {
       System.err.println("Sorry, only use JComponent decendants. No awt stuff. Ignoring");
       return super.getComponentValue(name);
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
//     if (s==null || "".equals(s)) {
//       return new LineBorder(Color.blue,2);
//     }
     StringTokenizer st = new StringTokenizer(s,",");
//     System.err.println("\n\n\nBORDERDEFINITION" +s + "\n\n");
     String borderName = st.nextToken();
     if ("etched".equals(borderName)) {
       return BorderFactory.createEtchedBorder();
     }
     if ("raised".equals(borderName)) {
       return BorderFactory.createRaisedBevelBorder();
     }
     if ("lowered".equals(borderName)) {
       return BorderFactory.createLoweredBevelBorder();
     }
     if ("titled".equals(borderName)) {
       String title = st.nextToken();
       return BorderFactory.createTitledBorder(title);
     }
     if ("indent".equals(borderName)) {
       try {
         int top = Integer.parseInt(st.nextToken());
         int left = Integer.parseInt(st.nextToken());
         int bottom = Integer.parseInt(st.nextToken());
         int right = Integer.parseInt(st.nextToken());
         return BorderFactory.createEmptyBorder(top,left,bottom,right);
       }
       catch (Exception ex) {
         System.err.println("Error while parsing border");
       }

     }
     return BorderFactory.createEmptyBorder();
   }

   public Color parseColor(String s) {
     if (!s.startsWith("#")) {
       throw new RuntimeException("BAD COLOR: "+s);
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