package com.dexels.navajo.tipi.swingclient.components;



import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class MultipleSelectionPropertyCheckboxGroup extends BasePanel implements PropertyControlled, PropertyChangeListener {
  private Property myProperty;
//  private ArrayList mySelectionList = new ArrayList();
  private ArrayList myListeners = new ArrayList();
  private boolean columnMode = false;
  private int columns = 2;

  public MultipleSelectionPropertyCheckboxGroup() {
    this.setLayout(new GridBagLayout());
    setOpaque(false);
  }

  public void addCheckboxListener(ItemListener l){
    myListeners.add(l);
  }

  public void removeCheckboxListener(ItemListener l){
    myListeners.remove(l);
  }

  private void fireListeners(ItemEvent e){
    for(int i=0;i<myListeners.size();i++){
      ItemListener il = (ItemListener)myListeners.get(i);
      il.itemStateChanged(e);
    }
  }

  public void setColumnMode(boolean b){
    columnMode = b;
  }

  public void setColumns(int cols){
    columns = cols;
  }

  public final void setVerticalScrolls(boolean b) {
    // ignore

  }
  public final void setHorizontalScrolls(boolean b) {
    // ignore
  }


  /** @todo Add action / changelisteners to this class */

  public final Property getProperty() {
    return myProperty;
  }

  public final void setProperty(Property p) {
    try {
    	if(myProperty!=null) {
    		myProperty.removePropertyChangeListener(this);
    	}
      myProperty = p;
      ArrayList<Selection> selections = myProperty.getAllSelections();
      
      if (selections.size() <= 0) {
        System.err.println("Watch it! No selection property!");
      }
      else {
        removeAll();
        int col = 0;
        int row = 0;
        for (int i = 0; i < selections.size(); i++) {
          Selection current = selections.get(i);
          SelectionCheckBox cb = new SelectionCheckBox();
          cb.setOpaque(false);
          cb.setSelection(current,myProperty);
          cb.setSelected(current.isSelected());
          if(columnMode){
            int req = (int) Math.ceil(selections.size() / columns)-1; // offset with 1 because gridbag starts at 0
            if(row+1 > req){
              add(cb, new GridBagConstraints(col, row, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
            }else{
              add(cb, new GridBagConstraints(col, row, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
            }
            row++;
            if(row > req){
              row = 0;
              col++;
            }
          }else{
            add(cb, new GridBagConstraints(0, i, 1, 1, 1, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
          }
        }
      }
      myProperty.addPropertyChangeListener(this);
//      updateUI();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
  }
  public final void update() {
    // dummy
  }


  public static void main(String args[]){
    int req = (int) Math.ceil(4 / 2);
    System.err.println("Req: " +req);
 }


public void propertyChange(PropertyChangeEvent e) {
	if(e.getPropertyName().equals("value")) {
		fireListeners(null);
	}
	revalidate();
}


}
