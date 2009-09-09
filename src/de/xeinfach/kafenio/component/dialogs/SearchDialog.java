/*
GNU Lesser General Public License

SearchDialog
Copyright (C) 2000-2003 Howard Kistler
changes to SearchDialog
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

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for providing a dialog that lets the user specify arguments for
 * the Search Find/Replace functions
 * @author Howard Kistler, Karsten Pawlik
 */
public class SearchDialog extends AbstractKafenioDialog {

	private static LeanLogger log = new LeanLogger("SearchDialog.class");

	private String inputFindTerm    = (String)null;
	private String inputReplaceTerm = (String)null;
	private boolean bCaseSensitive  = false;
	private boolean bStartAtTop     = false;
	private boolean bReplaceAll     = false;

	private final Object[] panelContents;
	private final boolean isReplaceDialog;
	private final JTextField jtxfFindTerm;
	private final JTextField jtxfReplaceTerm;
	private final JCheckBox jchkCase;
	private final JCheckBox jchkTop;
	private final JCheckBox jchkAll;
	
	/**
	 * creates a new SearchDialog using the given values.
	 * @param parent parent frame
	 * @param title frame title
	 * @param bModal boolean value
	 * @param bIsReplace is replace turned on by default?
	 * @param bCaseSetting true for case sensitive search, false for case insensitive.
	 * @param bTopSetting wrap mode on?
	 * @param findText the text to find
	 * @param replaceText the text to replace the found text with
	 */
	public SearchDialog(KafenioPanel parent, 
						String title, 
						boolean bModal, 
						boolean bIsReplace, 
						boolean bCaseSetting, 
						boolean bTopSetting,
						String findText,
						String replaceText)
	{
		super(parent, title, bModal);
		
		isReplaceDialog = bIsReplace;
		jtxfFindTerm = new JTextField(3);
		jtxfReplaceTerm = new JTextField(3);
		jchkCase = new JCheckBox(parent.getTranslation("SearchCaseSensitive"), bCaseSetting);
		jchkTop = new JCheckBox(parent.getTranslation("SearchStartAtTop"), bTopSetting);
		jchkAll = new JCheckBox(parent.getTranslation("SearchReplaceAll"), false);

		if(bIsReplace){
			panelContents = new Object[] {	parent.getTranslation("SearchFind"),
											jtxfFindTerm,
											parent.getTranslation("SearchReplace"),
											jtxfReplaceTerm,
											jchkAll,
											jchkCase,
											jchkTop
			};
		} else {
			panelContents = new Object[] {	parent.getTranslation("SearchFind"),
											jtxfFindTerm,
											jchkCase,
											jchkTop
			};
		}
		if(findText != null){
			jtxfFindTerm.setText(findText);
		}
		if(replaceText != null){
			jtxfReplaceTerm.setText(replaceText);
		}
		init(panelContents);
		jtxfFindTerm.requestFocus();
	}

	/**
	 * @see de.xeinfach.kafenio.component.dialogs.AbstractKafenioDialog#setDefaultValues(java.lang.Object)
	 */
	public void setDefaultValues(Object value) {
		if(value.equals(getButtonLabels()[0])) {
			inputFindTerm = jtxfFindTerm.getText();
			bCaseSensitive = jchkCase.isSelected();
			bStartAtTop = jchkTop.isSelected();
			if(isReplaceDialog) {
				inputReplaceTerm = jtxfReplaceTerm.getText();
				bReplaceAll = jchkAll.isSelected();
			}
			setVisible(false);
		} else {
			inputFindTerm = (String)null;
			inputReplaceTerm = (String)null;
			bCaseSensitive = false;
			bStartAtTop = false;
			bReplaceAll = false;
			setVisible(false);
		}
	}

	/**
	 * @return returns value of inputFindTerm variable
	 */
	public String  getFindTerm() {
		return inputFindTerm;
	}

	/**
	 * @return returns value of inputReplaceTerm variable
	 */
	public String  getReplaceTerm()   {
		return inputReplaceTerm;
	}

	/**
	 * @return returns value of bCaseSensitive variable
	 */
	public boolean getCaseSensitive() {
		return bCaseSensitive;
	}
	
	/**
	 * @return returns value of bStartAtTop variable
	 */
	public boolean getStartAtTop() {
		return bStartAtTop;
	}
	
	/**
	 * @return returns value of bReplaceAll variable
	 */
	public boolean getReplaceAll() {
		return bReplaceAll;
	}
}

