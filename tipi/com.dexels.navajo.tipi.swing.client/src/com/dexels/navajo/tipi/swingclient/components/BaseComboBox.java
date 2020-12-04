/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

public abstract class BaseComboBox extends JComboBox {

	private static final long serialVersionUID = 2894805109531627299L;
	DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();

	private final static Logger logger = LoggerFactory
			.getLogger(BaseComboBox.class);

	public BaseComboBox() {
		// this.setPreferredSize(new Dimension(125,
		// ComponentConstants.PREFERRED_HEIGHT));
		this.setModel(defaultComboBoxModel);
		// Install the custom key selection manager
		this.setKeySelectionManager(new MyKeySelectionManager());
	}

	public void loadCombobox(Property p) {
		try {
			defaultComboBoxModel = new DefaultComboBoxModel();
			List<Selection> a = p.getAllSelections();
			Selection selectedSelection = null;
			for (int i = 0; i < a.size(); i++) {
				Selection s = a.get(i);
				defaultComboBoxModel.addElement(s);
				selectedSelection = (s.isSelected() ? s : selectedSelection);
			}
			setModel(defaultComboBoxModel);

			if (selectedSelection != null) {
				setSelectedItem(selectedSelection);
			} else {
				Selection s = (Selection) getSelectedItem();
				if (s != null) {
					s.setSelected(true);
				}
			}
		} catch (NavajoException ex) {
			logger.error("Error: ", ex);
		}
	}

	public void loadCombobox(Message msg, String property) {
		Property p = msg.getProperty(property);
		loadCombobox(p);
	}

	public void setToKey(String key) {
		for (int i = 0; i < defaultComboBoxModel.getSize(); i++) {
			Selection s = (Selection) defaultComboBoxModel.getElementAt(i);
			if (s.getValue().equals(key)) {
				setSelectedItem(s);
			}
		}
	}

	public String getSelectedId() {
		Selection s = (Selection) getSelectedItem();
		if (s != null) {
			return s.getValue();
		} else {
			return null;
		}
	}

	// This key selection manager will handle selections based on multiple keys.
	class MyKeySelectionManager implements JComboBox.KeySelectionManager {
		long lastKeyTime = 0;
		String pattern = "";

		@Override
		public int selectionForKey(char aKey, ComboBoxModel model) {
			int selIx = 01;
			Object sel = model.getSelectedItem();
			if (sel != null) {
				for (int i = 0; i < model.getSize(); i++) {
					if (sel.equals(model.getElementAt(i))) {
						selIx = i;
						break;
					}
				}
			}

			long curTime = System.currentTimeMillis();

			if (curTime - lastKeyTime < 500) {
				pattern += ("" + aKey).toLowerCase();
			} else {
				pattern = ("" + aKey).toLowerCase();
			}

			// Save current time
			lastKeyTime = curTime;

			// Search forward from current selection
			for (int i = selIx + 1; i < model.getSize(); i++) {
				String s = model.getElementAt(i).toString().toLowerCase();
				if (s.startsWith(pattern)) {
					return i;
				}
			}

			// Search from top to current selection
			for (int i = 0; i < selIx; i++) {
				if (model.getElementAt(i) != null) {
					String s = model.getElementAt(i).toString().toLowerCase();
					if (s.startsWith(pattern)) {
						return i;
					}
				}
			}
			return -1;
		}
	}
}
