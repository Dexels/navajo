package com.dexels.navajo.tipi.impl;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import javax.swing.JFrame;

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
    Dimension screen = new Dimension(800,600);
    if("true".equals(fullscreen)){
      screen = Toolkit.getDefaultToolkit().getScreenSize();
    }
    ((JFrame)context.getTopLevel()).setSize(screen);
    super.load(definition,instance,context);
  }
}