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

public class DefaultTipiScreen extends DefaultTipiPanel {
  public DefaultTipiScreen() {
    setContainerLayout(new BorderLayout());
  }

  public void addToContainer(Component c, Object constraints) {
    getContainer().add(c, constraints);
    //getContainer().add(c,BorderLayout.CENTER);
  }
  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    String fullscreen = (String)instance.getAttribute("fullscreen", "false");
    String icon = (String)instance.getAttribute("icon", null);
    if(icon!= null){
      try{
        URL i = new URL(icon);
        JFrame f = new JFrame();
        JFrame top = (JFrame)context.getTopLevel();
        ImageIcon ic = new ImageIcon(i);
        top.setIconImage(ic.getImage());
      }catch(Exception e){
         URL t = MainApplication.class.getResource(icon);
         if(t!=null){
           JFrame topscreen = (JFrame)context.getTopLevel();
           ImageIcon ii = new ImageIcon(t);
           topscreen.setIconImage(ii.getImage());
         }
      }
    }
    Dimension screen = new Dimension(800,600);
    if("true".equals(fullscreen)){
      screen = Toolkit.getDefaultToolkit().getScreenSize();
    }
    ((JFrame)context.getTopLevel()).setSize(screen);
    super.load(definition,instance,context);
  }
}