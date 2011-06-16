package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public final class SelectionCheckBox extends JCheckBox {

	private static final long serialVersionUID = 3939722308465164825L;
	Selection mySelection;
	private Property myProperty;

	public SelectionCheckBox() {
		this.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.err.println("Bim!");
				// System.err.println("Check changed");
				if (mySelection.isSelected() != isSelected()) {
					try {
						myProperty.setSelected(mySelection, isSelected());
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				}
				System.err.println("Bom!");
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
