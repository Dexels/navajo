/*
GNU Lesser General Public License

ListAutomationAction
Copyright (C) 2000-2003 Howard Kistler
changes to ListAutomationAction
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
package de.xeinfach.kafenio.action;

import java.awt.event.ActionEvent;
import java.util.StringTokenizer;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.component.HTMLUtilities;
import de.xeinfach.kafenio.component.dialogs.SimpleInfoDialog;
import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for automatically creating bulleted lists from selected text
 * 
 * @author HowardKistler, Karsten Pawlik
 */
public class ListAutomationAction extends HTMLEditorKit.InsertHTMLTextAction {

	private static LeanLogger log = new LeanLogger("ListAutomationAction.class");
	
	private KafenioPanel parentKafenioPanel;
	private HTML.Tag baseTag;
	private String listType;
	private HTMLUtilities htmlUtilities;

	/**
	 * creates a new ListAutomationAction using the given parameters.
	 * @param kafenio a KafenioPanel instance
	 * @param sLabel a list label
	 * @param newListType type of the list (OL or UL)
	 */
	public ListAutomationAction(KafenioPanel kafenio, String sLabel, HTML.Tag newListType) {
		super(sLabel, "", newListType, HTML.Tag.LI);
		parentKafenioPanel = kafenio;
		baseTag    = newListType;
		htmlUtilities = new HTMLUtilities(kafenio);
		log.debug("created new ListAutomationAction.");
	}

	/**
	 * method that handles the given ActionEvent
	 * @param ae the ActionEvent to handle
	 */
	public void actionPerformed(ActionEvent ae) {
		try {
			JEditorPane jepEditor = (JEditorPane)(parentKafenioPanel.getTextPane());
			String selTextBase = jepEditor.getSelectedText();
			int textLength = -1;
			if(selTextBase != null) {
				textLength = selTextBase.length();
			}
			if(selTextBase == null || textLength < 1) {
				int pos = parentKafenioPanel.getCaretPosition();
				parentKafenioPanel.setCaretPosition(pos);
				if(ae.getActionCommand() != "newListPoint") {
					if(	htmlUtilities.checkParentsTag(HTML.Tag.OL) 
						|| htmlUtilities.checkParentsTag(HTML.Tag.UL)) 
					{
						new SimpleInfoDialog(	parentKafenioPanel, 
												getString("Error"), 
												true, 
												getString("ErrorNestedListsNotSupported"));
						return;
					}
				}

				listType = (baseTag == HTML.Tag.OL ? "ol" : "ul");
				StringBuffer sbNew = new StringBuffer();
				if(htmlUtilities.checkParentsTag(baseTag)) {
					sbNew.append("<li></li>");
					insertHTML(	parentKafenioPanel.getTextPane(), 
								parentKafenioPanel.getExtendedHtmlDoc(), 
								parentKafenioPanel.getTextPane().getCaretPosition(), 
								sbNew.toString(), 
								0, 
								0, 
								HTML.Tag.LI);
				} else {
					sbNew.append("<" + listType + "><li></li></" + listType + ">&nbsp;");
					insertHTML(	parentKafenioPanel.getTextPane(), 
								parentKafenioPanel.getExtendedHtmlDoc(), 
								parentKafenioPanel.getTextPane().getCaretPosition(), 
								sbNew.toString(), 
								0, 
								0, 
								(listType.equals("ol") ? HTML.Tag.OL : HTML.Tag.UL));
				}
				parentKafenioPanel.refreshOnUpdate();
			} else {
				listType = (baseTag == HTML.Tag.OL ? "ol" : "ul");
				HTMLDocument htmlDoc = (HTMLDocument)(jepEditor.getDocument());
				int iStart = jepEditor.getSelectionStart();
				int iEnd   = jepEditor.getSelectionEnd();
				String selText = htmlDoc.getText(iStart, iEnd - iStart);
				StringBuffer sbNew = new StringBuffer();
				String sToken = ((selText.indexOf("\r") > -1) ? "\r" : "\n");
				StringTokenizer stTokenizer = new StringTokenizer(selText, sToken);
				sbNew.append("<" + listType + ">");
				while(stTokenizer.hasMoreTokens()) {
					sbNew.append("<li>");
					sbNew.append(stTokenizer.nextToken());
					sbNew.append("</li>");
				}
				sbNew.append("</" + listType + ">&nbsp;");
				htmlDoc.remove(iStart, iEnd - iStart);
				insertHTML(jepEditor, htmlDoc, iStart, sbNew.toString(), 1, 1, null);
				parentKafenioPanel.refreshOnUpdate();
			}
		} catch (BadLocationException ble) {
			log.error("BadLocationException: " + ble.fillInStackTrace());
		}
		
		parentKafenioPanel.repaint();
	}

	/**
	 * returns a translated representation of the given string.
	 * @param stringToTranslate the string to translate.
	 * @return returns a translated representation of the given string.
	 */
	private String getString(String stringToTranslate) {
		return parentKafenioPanel.getTranslation(stringToTranslate);
	}
	
}