package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.BasePanel;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.*;
import java.util.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MultipleSelectionPropertyCheckboxGroup extends BasePanel implements ChangeMonitoring, Ghostable, PropertyControlled {
  private Property myProperty;

  public MultipleSelectionPropertyCheckboxGroup() {
    this.setLayout(new GridBagLayout());
    setBackground(Color.green);
  }
  public void setGhosted(boolean b) {
    // dummy
  }
  public boolean isGhosted() {
    return false;
  }
  public Property getProperty() {
    return myProperty;
  }

  public void setProperty(Property p) {
    myProperty = p;
    ArrayList selections = myProperty.getAllSelections();
    if(selections.size() <= 0){
      System.err.println("Watch it! No selection property!");
    }else{
      for(int i=0;i<selections.size();i++){
        Selection current = (Selection)selections.get(i);
        SelectionCheckBox cb = new SelectionCheckBox();
        cb.setBackground(Color.blue);
        cb.setSelection(current);
        cb.setSelected(current.isSelected());
        add(cb, new GridBagConstraints(0,i,1,1,1.0,1.0,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0));
      }
    }
    updateUI();
  }
  public void update() {
    // dummy
  }


}