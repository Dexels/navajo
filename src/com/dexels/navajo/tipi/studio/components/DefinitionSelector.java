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

public class DefinitionSelector extends SwingTipiComponent {
  private JComboBox myBox = null;

  public Container createContainer() {
    myBox = new JComboBox(getComponentDefinitions());
    return myBox;
  }

  public DefinitionSelector() {
  }

  private Vector getComponentDefinitions(){
    Vector v = new Vector();
    Map m = TipiContext.getInstance().getTipiDefinitionMap();
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