package com.dexels.navajo.tipi.components.swingimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import tipi.*;

public class TipiDesktop
    extends TipiSwingDataComponentImpl {
  public Container createContainer() {
    TipiSwingDesktop jp = new TipiSwingDesktop(this);
    jp.setDesktopManager(new DefaultDesktopManager());
    jp.setDragMode(TipiSwingDesktop.LIVE_DRAG_MODE);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return jp;
  }

  public void addToContainer(Object c, Object constraints) {
    //System.err.println("ADDING COMPONENT TO DESKTOP");
    getContainer().add((Component)c, 0);
  }

  public void removeFromContainer(Object c) {
    getContainer().remove((Component)c);
    getContainer().repaint();
  }

//  public DefaultTipiDesktop() {
//    initContainer();
//  }
//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    super.load(definition,instance,context);
//  }
  public void setComponentValue(String name, Object value) {
    super.setComponentValue(name, value);
    if ("logo".equals(name)) {
//      System.err.println("Found logo: " + (String)value);
      ImageIcon im = new ImageIcon(MainApplication.class.getResource( (String) value));
      if (im != null) {
        ( (TipiSwingDesktop) getContainer()).setImage(im.getImage());
      }
    }
  }

  protected void addedToParent() {
    ( (TipiSwingDesktop) getContainer()).revalidate();
    ( (TipiSwingDesktop) getContainer()).paintImmediately(0, 0, 100, 100);
    super.addedToParent();
  }
}