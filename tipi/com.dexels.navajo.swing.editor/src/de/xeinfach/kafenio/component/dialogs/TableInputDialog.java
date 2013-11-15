/*
GNU General Public License

TableInputDialog
Copyright (C) 2000-2003 Howard Kistler
changes to TableInputDialog
Copyright (C) 2003-2004 Karsten Pawlik

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
package de.xeinfach.kafenio.component.dialogs;

import javax.swing.JTextField;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for providing a dialog that lets the user specify values for tag attributes
 * @author Howard Kistler, Karsten Pawlik
 */
public class TableInputDialog extends AbstractKafenioDialog {

	private static LeanLogger log = new LeanLogger("TableInputDialog.class");

	private String inputRows   = new String();
	private String inputCols   = new String();
	private String inputBorder = new String();
	private String inputSpace  = new String();
	private String inputPad    = new String();
	private final JTextField jtxfRows;
	private final JTextField jtxfCols;
	private final JTextField jtxfBorder;
	private final JTextField jtxfSpace;
	private final JTextField jtxfPad;
	private final Object[] panelContents;
	/**
	 * creates a new TableInputDialog using the given values.
	 * @param parent parent frame
	 * @param title frame title
	 * @param bModal boolean value
	 */
	public TableInputDialog(KafenioPanel parent, String title, boolean bModal) {
		super(parent, title, bModal);
		jtxfRows = new JTextField(3);
		jtxfCols = new JTextField(3);
		jtxfBorder = new JTextField(3);
		jtxfSpace = new JTextField(3);
		jtxfPad = new JTextField(3);

		panelContents = new Object[] {
			parent.getTranslation("TableRows"),        jtxfRows,
			parent.getTranslation("TableColumns"),     jtxfCols,
			parent.getTranslation("TableBorder"),      jtxfBorder,
			parent.getTranslation("TableCellSpacing"), jtxfSpace,
			parent.getTranslation("TableCellPadding"), jtxfPad
		};

		init(panelContents);
	}

	/**
	 * sets the value based on the PropertyChangeEvent caught by the superclass.
	 * @param value a value. 
	 */
	public void setDefaultValues(Object value) {
		if(value.equals(getButtonLabels()[0])) {
			inputRows   = jtxfRows.getText();
			inputCols   = jtxfCols.getText();
			inputBorder = jtxfBorder.getText();
			inputSpace  = jtxfSpace.getText();
			inputPad    = jtxfPad.getText();
			setVisible(false);
		} else {
			inputRows   = "";
			inputCols   = "";
			inputBorder = "";
			inputSpace  = "";
			inputPad    = "";
			setVisible(false);
		}
	}

	/**
	 * @return returns the number of rows.
	 */
	public int getRows() {
		try {
			return Integer.parseInt(inputRows);
		} catch(NumberFormatException nfe) {
			log.error("not a number: " + nfe.fillInStackTrace());
			return -1;
		}
	}

	/**
	 * @return returns the number of columns.
	 */
	public int getCols() {
		try {
			return Integer.parseInt(inputCols);
		} catch(NumberFormatException nfe) {
			log.error("not a number: " + nfe.fillInStackTrace());
			return -1;
		}
	}

	/**
	 * @return returns the border width
	 */
	public int getBorder() {
		try {
			return Integer.parseInt(inputBorder);
		} catch(NumberFormatException nfe) {
			log.error("not a number: " + nfe.fillInStackTrace());
			return -1;
		}
	}

	/**
	 * @return returns the spacing as int value.
	 */
	public int getSpacing() {
		try {
			return Integer.parseInt(inputSpace);
		} catch(NumberFormatException nfe) {
			log.error("not a number: " + nfe.fillInStackTrace());
			return -1;
		}
	}

	/**
	 * @return returns the cellpadding as int value.
	 */
	public int getPadding() {
		try {
			return Integer.parseInt(inputPad);
		} catch(NumberFormatException nfe) {
			log.error("not a number: " + nfe.fillInStackTrace());
			return -1;
		}
	}
}
