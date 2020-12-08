/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;


public final class SelectionCheckBox extends JCheckBox {

	private static final long serialVersionUID = 3939722308465164825L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(SelectionCheckBox.class);
	
	Selection mySelection;
	private Property myProperty;

	public SelectionCheckBox() {
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Bim!");
				// logger.info("Check changed");
				if (mySelection.isSelected() != isSelected()) {
					try {
						myProperty.setSelected(mySelection, isSelected());
					} catch (NavajoException e) {
						logger.error("Error: ",e);
					}
				}
				logger.info("Bom!");
				repaint();
			}
		});
	}

	public final void setSelection(Selection s, Property p) {
		mySelection = s;
		myProperty = p;
		this.setText(s.getName());
	}

}
