package com.dexels.navajo.tipi.studio.components;

import com.dexels.navajo.tipi.components.SwingTipiComponent;
import java.awt.Container;
import com.dexels.navajo.tipi.*;
import java.util.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ClassSelector extends SwingTipiComponent {
  private JComboBox myBox = null;

  public ClassSelector() {
  }
  public Container createContainer() {
    myBox = new JComboBox(getComponentClasses());
    return myBox;
  }


  private Vector getComponentClasses(){
    Vector v = new Vector();
    Map m = TipiContext.getInstance().getTipiClassDefMap();
    Iterator it = m.keySet().iterator();
    while(it.hasNext()){
      v.addElement(it.next());
    }
    return v;
  }
  public Object getComponentValue(String name) {
    if (name.equals("selected")) {
      return (String)myBox.getSelectedItem().toString();
    }
    return super.getComponentValue(name);
  }

}