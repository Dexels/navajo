package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMenuItem
    extends TipiSwingComponentImpl {
  private TipiContext myContext = null;
  private ArrayList myEvents = new ArrayList();
  private TipiSwingMenuItem myItem;
  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myItem.getText();
    }
    if (name.equals("icon")) {
      return myItem.getIcon();
    }
    if (name.equals("mnemonic")) {
      return "" + myItem.getMnemonic();
    }
    if (name.equals("icon")) {
      return myItem.getIcon();
    }
    if (name.equals("accelerator")) {
      return myItem.getAccelerator().toString();
    }
    return super.getComponentValue(name);
  }

  public void addEvent(TipiEvent te) {
    myEvents.add(te);
  }

  public Object createContainer() {
    myItem = new TipiSwingMenuItem(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myItem;
  }

  public Object getContainer() {
    return myItem;
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if ("text".equals(name)) {
      myItem.setText( (String) object);
    }
    if ("mnemonic".equals(name)) {
      String ch = (String) object;
      char mn = ch.charAt(0);
      myItem.setMnemonic(mn);
    }
    if ("accelerator".equals(name)) {
      myItem.setAccelerator(KeyStroke.getKeyStroke( (String) object));
    }
    if (name.equals("icon")) {
      myItem.setIcon( (Icon) object);
    }
  }
//  public void load(XMLElement def, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
//    super.load(def, instance, context);
//  }
}