package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.JCheckBox;
import com.dexels.navajo.document.*;

import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class SelectionCheckBox extends JCheckBox {

  Selection mySelection;
private Property myProperty;

  public SelectionCheckBox() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }


public final void setSelection(Selection s, Property p){
    mySelection = s;
    myProperty = p;
    this.setText(s.getName());
  }
  private final void jbInit() throws Exception {
      this.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.err.println("Bim!");
//	    	System.err.println("Check changed");
			if(mySelection.isSelected()!=isSelected()) {
				try {
					myProperty.setSelected(mySelection, isSelected());
				} catch (NavajoException e) {
					e.printStackTrace();
				}
			}
	    	System.err.println("Bom!");
	    	repaint();
		}});
  }


}

