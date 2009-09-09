/*
GNU Lesser General Public License

SimpleInfoDialog
Copyright (C) 2000-2003 Howard Kistler
changes to SimpleInfoDialog
Copyright (C) 2003-2004 Karsten Pawlik

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package de.xeinfach.kafenio.component.dialogs;

import javax.swing.JOptionPane;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for providing a dialog that lets the user specify values for tag attributes.
 * @author Howard Kistler, Karsten Pawlik
 */
public class SimpleInfoDialog extends AbstractKafenioDialog {

	private static LeanLogger log = new LeanLogger("SimpleInfoDialog.class");

	public static final int ERROR = JOptionPane.ERROR_MESSAGE;
	public static final int INFO = JOptionPane.INFORMATION_MESSAGE;
	public static final int WARNING = JOptionPane.WARNING_MESSAGE;
	public static final int QUESTION = JOptionPane.QUESTION_MESSAGE;
	public static final int PLAIN = JOptionPane.PLAIN_MESSAGE;

	private KafenioPanel parent;
	private String[] buttonLabels;
	private Object[] panelContents;
	private int optionType;
	private Integer buttonState = new Integer(JOptionPane.CLOSED_OPTION);

	/**
	 * creates a new SimpleInfoDialog Object using the given values.
	 * @param newParent parent frame
	 * @param title frame title
	 * @param bModal boolean value
	 * @param message message text
	 * @param type type of the SimpleInfoDialog
	 */
	public SimpleInfoDialog(KafenioPanel newParent, String title, boolean bModal, String message, int type) {
		super(newParent, title, bModal);

		parent = newParent;
		panelContents = new Object[] {message};

		if(type == QUESTION) {
			init(panelContents);
		} else {
			buttonLabels = new String[]{parent.getTranslation("DialogClose") };
			optionType = JOptionPane.DEFAULT_OPTION;
			init(panelContents, buttonLabels, new Integer(optionType), new Integer(type));
		}
		
		int centerX = (int)(((parent.getWidth() / 2) + newParent.getX()) - (this.getWidth()  / 2));
		if(centerX < 0) centerX = 0;

		int centerY = (int)(((parent.getHeight() / 2) + newParent.getY()) - (this.getHeight() / 2));
		if(centerY < 0) centerY = 0;
		
		this.setLocation(centerX, centerY);
	}

	/**
	 * @see de.xeinfach.kafenio.component.dialogs.AbstractKafenioDialog#setDefaultValues(java.lang.Object)
	 */
	public void setDefaultValues(Object value) {
		setVisible(false);
	}

	/**
	 * creates a new SimpleInfoDialog using the given values
	 * @param newParent parent frame
	 * @param title frame title
	 * @param bModal boolean value
	 * @param message message text
	 */
	public SimpleInfoDialog(KafenioPanel newParent, String title, boolean bModal, String message) {
		this(newParent, title, bModal, message, WARNING);
	}

	/**
	 * @return returns the value of the clicked button as string
	 */
	public String getDecisionValue() {
		return getJOptionPane().getValue().toString();
	}
}
