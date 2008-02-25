package com.dexels.navajo.tipi.swingclient.components;

import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.dexels.navajo.document.*;

public class EditableComboBoxModel extends AbstractListModel implements ComboBoxModel {

	private final Property myProperty;
	private final List propertyList = new ArrayList();
	private Object currentSelection = null;
	
	public EditableComboBoxModel(Property p) {
		myProperty = p;
	}
	
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return currentSelection;
	}

	public void commit(Object selectedSelection) {
		System.err.println("Committing: "+selectedSelection);
		if(selectedSelection instanceof Selection) {
			if(selectedSelection!=null) {
				currentSelection = selectedSelection;
			}
			return;
		}
		if(propertyList.size()>=1) {
			for (int i = 0; i < propertyList.size(); i++) {
				if(propertyList.get(i) instanceof Selection) {
					currentSelection = propertyList.get(i);
					return;
				}
			}
		}
	}
	
	public void setSelectedItem(Object o) {
		currentSelection = o;
	}

	public Object getElementAt(int c) {
		return propertyList.get(c);
	}

	public int getSize() {
		int size = propertyList.size();
		return size;
	}

	
	
	public void updateVisibleList(final String select) throws NavajoException {
		
		int origSize = propertyList.size();
		propertyList.clear();
		int found = -1;
			ArrayList ll = myProperty.getAllSelections();
			for (int i = 0; i < ll.size(); i++) {
				Selection current = (Selection) ll.get(i);
				if(current==null) {
					// huh
				} else {
					if (select.equals(current.getName())) {
						found = i;
					} else {
						if (current.getName().startsWith(select)) {
							propertyList.add(current);
						}
					}
				}
			}
			if(found==-1) {
				propertyList.add(0, select);
				currentSelection = select;
			}
			
			fireIntervalRemoved(this, 0, origSize);
			fireIntervalAdded(this, 0, propertyList.size());
//		fireContentsChanged(this, 0, Math.max(origSize, propertyList.size()) );
	}

}
