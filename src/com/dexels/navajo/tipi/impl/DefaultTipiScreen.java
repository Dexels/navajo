package com.dexels.navajo.tipi.impl;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiScreen extends DefaultTipiRootPane {
  private JFrame myFrame = null;

  public DefaultTipiScreen() {
    System.err.println("CREATING SCREEN!");
  }

  public Container createContainer() {
    myFrame = new JFrame();
    myFrame.setVisible(true);
    return myFrame;
  }

  public void addToContainer(Component c, Object constraints) {
//    getContainer().add(c, constraints);
    myFrame.getContentPane().add(c,constraints);
    //getContainer().add(c,BorderLayout.CENTER);
  }
//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    System.err.println("LOADING SCREEN!");
//    setContainerLayout(new BorderLayout());
//    String fullscreen = (String)instance.getAttribute("fullscreen", "false");
//    String title = (String) instance.getAttribute("title", "no title");
//    String icon = (String)instance.getAttribute("icon", null);
//    if(icon!= null){
//      try{
//        URL i = new URL(icon);
//        JFrame f = new JFrame();
//        JFrame top = (JFrame)context.getTopLevel();
//        ImageIcon ic = new ImageIcon(i);
//        top.setIconImage(ic.getImage());
//      }catch(Exception e){
//         URL t = MainApplication.class.getResource(icon);
//         if(t!=null){
//           JFrame topscreen = (JFrame)context.getTopLevel();
//           ImageIcon ii = new ImageIcon(t);
//           topscreen.setIconImage(ii.getImage());
//         }
//      }
//    }
//    Dimension screen = new Dimension(800,600);
//    if("true".equals(fullscreen)){
//      screen = Toolkit.getDefaultToolkit().getScreenSize();
//    }
//    ((JFrame)context.getTopLevel()).setSize(screen);
//    ((JFrame)context.getTopLevel()).setTitle(title);
//    super.load(definition,instance,context);
//  }
//  public Container createContainer() {
//    return super.createContainer();
//  }

//  public void setComponentValue(String name, Object object) {
//    super.setComponentValue(name, object);
//  }
//
//  public Object getComponentValue(String name) {
//    return super.getComponentValue(name);
//  }
  protected void setBounds(Rectangle r) {
    myFrame.setBounds(r);
//    System.err.println("FrameSize: "+r);
//    myFrame.setSize(r.getSize());
  }
  protected Rectangle getBounds() {
     return myFrame.getBounds();
   }

 protected void setIcon(ImageIcon ic) {
   myFrame.setIconImage(ic.getImage());
 }

 protected void setTitle(String s) {
   myFrame.setTitle(s);
 }
  public void setContainerLayout(LayoutManager layout) {
    myFrame.getContentPane().setLayout(layout);
  }
  public void setComponentValue(String name, Object object) {
    if (name.equals("fullscreen") && "true".equals(object)) {
      myFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    }
    super.setComponentValue(name,object);
  }

  protected void setJMenuBar(JMenuBar s) {
    System.err.println("MYBAR: "+s);
    myFrame.getRootPane().setJMenuBar(s);
  }

}