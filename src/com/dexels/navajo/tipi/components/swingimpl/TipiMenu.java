package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMenu
    extends TipiSwingComponentImpl {
  private TipiSwingMenu myMenu = null;
  public Object createContainer() {
    myMenu = new TipiSwingMenu(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myMenu;
  }

  public void addToContainer(final Object menu, final Object item) {
    if (TipiSwingMenuItem.class.isInstance(menu)) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          myMenu.add( (TipiSwingMenuItem) menu);
        }
      });
    }
    if (TipiSwingMenu.class.isInstance(menu)) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          myMenu.add( (TipiSwingMenu) menu);
        }
      });
    }
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        myMenu.remove( (Component) c);
      }
    });
  }
//
  public void load(XMLElement def, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    super.load(def, instance, context);
    Vector v = def.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement) v.get(i);
      TipiComponent tc = context.instantiateComponent(current);
      addComponent(tc, context, null);
    }
  }

  public void setComponentValue(String name, Object object) {
    if ("text".equals(name)) {
      myMenu.setText( (String) object);
      return;
    }
    if ("mnemonic".equals(name)) {
      String ch = (String) object;
      char mn = ch.charAt(0);
      myMenu.setMnemonic(mn);
      return;
    }
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myMenu.getText();
    }
    if (name.equals("icon")) {
      return myMenu.getIcon();
    }
    if (name.equals("mnemonic")) {
      return "" + myMenu.getMnemonic();
    }
    return super.getComponentValue(name);
  }
}
