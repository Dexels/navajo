/*
GNU Lesser General Public License

CustomAction
Copyright (C) 2000-2003 Howard Kistler
changes to CustomAction
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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JColorChooser;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTML;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.component.dialogs.FontSelectorDialog;
import de.xeinfach.kafenio.component.dialogs.HyperlinkDialog;
import de.xeinfach.kafenio.component.dialogs.SimpleInfoDialog;

import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for implementing custom HTML insertion actions
 * 
 * @author Karsten Pawlik
 */
public class CustomAction extends StyledEditorKit.StyledTextAction {
	
	private static LeanLogger log = new LeanLogger("CustomAction.class");
	
	private KafenioPanel parentKafenioPanel;
	private HTML.Tag htmlTag;
	private Hashtable htmlAttribs;

	/**
	 * constructs a new CustomAction Object.
	 * @param kafenio reference to a KafenioPanel instance
	 * @param actionName name of the action
	 * @param inTag HTML tag to support.
	 * @param attribs attributes for this action. used in actionPerform-method.
	 */
	public CustomAction(KafenioPanel kafenio, 
						String actionName, 
						HTML.Tag inTag, 
						Hashtable attribs) 
	{
		super(actionName);
		parentKafenioPanel  = kafenio;
		htmlTag     = inTag;
		htmlAttribs = attribs;
		log.debug("new CustomAction created with name: " + actionName);
	}

	/**
	 * constructs a new CustomAction Object.
	 * @param kafenio reference to a KafenioPanel instance
	 * @param actionName name of the action
	 * @param inTag HTML tag to support.
	 */
	public CustomAction(KafenioPanel kafenio, 
						String actionName, 
						HTML.Tag inTag) 
	{
		this(kafenio, actionName, inTag, new Hashtable());
	}

	/**
	 * handles an action event.
	 * @param ae the ActionEvent to handle
	 */
	public void actionPerformed(ActionEvent ae) {
		Hashtable htmlAttribs2  = new Hashtable();
		JTextPane parentTextPane = parentKafenioPanel.getTextPane();
		String selText = parentTextPane.getSelectedText();
		int textLength = -1;

		if(selText != null) {
			textLength = selText.length();
		}

		if(selText == null || textLength < 1) {
			SimpleInfoDialog sidWarn = 	
				new SimpleInfoDialog(parentKafenioPanel, 
									"", 
									true, 
									getString("ErrorNoTextSelected"), 
									SimpleInfoDialog.ERROR);
		} else {
			int caretOffset = parentTextPane.getSelectionStart();
			int internalTextLength = selText.length();
			String currentLink = "";
			String currentTarget = null;
			// Somewhat ham-fisted code to obtain the first HREF in the selected text,
			// which (if found) is passed to the URL HREF request dialog.
			if(htmlTag.toString().equals(HTML.Tag.A.toString())) {
				SimpleAttributeSet sasText = null;
				for(int i = caretOffset; i < caretOffset + internalTextLength; i++) {
					parentTextPane.select(i, i + 1);
					sasText = new SimpleAttributeSet(parentTextPane.getCharacterAttributes());
					Enumeration attribEntries1 = sasText.getAttributeNames();
					while(attribEntries1.hasMoreElements() && currentLink.equals("")) {
						Object entryKey   = attribEntries1.nextElement();
						Object entryValue = sasText.getAttribute(entryKey);
						if(entryKey.toString().equals(HTML.Tag.A.toString())) {
							if(entryValue instanceof SimpleAttributeSet) {
								Enumeration subAttributes = ((SimpleAttributeSet)entryValue).getAttributeNames();
								while(subAttributes.hasMoreElements()) {
									Object subKey = subAttributes.nextElement();
									if(subKey.toString().toLowerCase().equals("href")) {
										currentLink = 
											((SimpleAttributeSet)entryValue).getAttribute(subKey).toString();
									} else if(subKey.toString().toLowerCase().equals("target")) {
										currentTarget = 
											((SimpleAttributeSet)entryValue).getAttribute(subKey).toString();
									}
									if (!currentLink.equals("") && !subAttributes.hasMoreElements()) break;
								}
							}
						}
					}
					if(!currentLink.equals("")) { 
						break; 
					}
				}
			}

			parentTextPane.select(caretOffset, caretOffset + internalTextLength);

			SimpleAttributeSet sasTag  = new SimpleAttributeSet();
			SimpleAttributeSet sasAttr = new SimpleAttributeSet();

			if(htmlTag.toString().equals(HTML.Tag.A.toString())) {
					HyperlinkDialog hdInput = new HyperlinkDialog(	parentKafenioPanel,
																	currentLink, 
																	currentTarget);
					String newAnchor = hdInput.getUrl();
					String newTarget = hdInput.getTarget();
					int action = hdInput.getAction();
					if(action == HyperlinkDialog.INSERT){
						if(newAnchor != null && !newAnchor.equals("")) {
							htmlAttribs2.put("href", newAnchor);
							if (newTarget != null && !newTarget.equals("")) {
								htmlAttribs2.put("target", newTarget);
							}
						}
					} else if(action == HyperlinkDialog.REMOVE_LINK) {
							clearFormat(parentKafenioPanel,
										selText, 
										caretOffset, 
										new String[] {	HTML.Attribute.HREF.toString(),
														HTML.Attribute.TARGET.toString(),
														HTML.Tag.A.toString()});
							refreshAndSelect(parentTextPane, caretOffset, internalTextLength);
							return;
					} else {
//							refreshAndSelect(parentTextPane, caretOffset, internalTextLength);
							return;
					}
			} else if(htmlTag.toString().equals(HTML.Tag.P.toString())) {
				SimpleAttributeSet sasText = new SimpleAttributeSet(parentTextPane.getCharacterAttributes());
				SimpleAttributeSet sasPara = new SimpleAttributeSet(parentTextPane.getParagraphAttributes());
				boolean alignFound = false;
				for (Enumeration en=sasPara.getAttributeNames(); en.hasMoreElements();) {
					Object elm = en.nextElement();
					log.debug("align? " + elm);
					if (elm.equals(HTML.Attribute.ALIGN)) {
						alignFound = true;
					}
				}

				if (alignFound) {
					sasPara.addAttribute(HTML.Attribute.ALIGN, htmlAttribs.get("align"));
					parentTextPane.select(caretOffset, caretOffset + textLength);
					parentTextPane.setParagraphAttributes(sasPara, true);
				} else {
					sasText.removeAttribute(HTML.Tag.P);
					SimpleAttributeSet attribs = new SimpleAttributeSet();
					attribs.addAttribute(HTML.Attribute.ALIGN, htmlAttribs.get("align").toString());
					sasText.addAttribute(htmlTag, attribs);
					parentTextPane.select(caretOffset, caretOffset + textLength);
					parentTextPane.setCharacterAttributes(sasText, true);
				}

				refreshAndSelect(parentTextPane, caretOffset, internalTextLength);
				return;
			} else if(htmlTag.toString().equals(HTML.Tag.FONT.toString())) {
				if(htmlAttribs.containsKey("face")) {
					FontSelectorDialog fsdInput = 
						new FontSelectorDialog(	parentKafenioPanel, 
												getString("FontDialogTitle"), 
												true, 
												"face", 
												parentTextPane.getSelectedText());

					String newFace = fsdInput.getFontName();
					if(newFace != null) {
						htmlAttribs2.put("face", newFace);
					} else {
//						refreshAndSelect(parentTextPane, caretOffset, internalTextLength);
						return;
					}
				} else if(htmlAttribs.containsKey("size")) {
					htmlAttribs2.put("size", new String((String)htmlAttribs.get("size")));
				} else if(htmlAttribs.containsKey("color")) {
					Color color = 
						JColorChooser.showDialog(parentKafenioPanel.getFrame(),"Choose Text Color",Color.black);
					if(color != null) {
						String redHex = Integer.toHexString(color.getRed());
						if(redHex.length() < 2) {
							redHex = "0" + redHex;
						}
	
						String greenHex = Integer.toHexString(color.getGreen());
						if(greenHex.length() < 2) {
							greenHex = "0" + greenHex;
						}
	
						String blueHex = Integer.toHexString(color.getBlue());
						if(blueHex.length() < 2) {
							blueHex = "0" + blueHex;
						}
	
						htmlAttribs2.put("color", "#" + redHex + greenHex + blueHex);
					} else {
//						refreshAndSelect(parentTextPane, caretOffset, internalTextLength);
						return;
					}
				}
			} else if(htmlTag.toString().equals(new HTML.UnknownTag("").toString())) {
				clearAllFormatsExcept(parentKafenioPanel, selText, caretOffset, null);
				return;
			}

			if(htmlAttribs2.size() > 0) {
				Enumeration attribEntries = htmlAttribs2.keys();
				while(attribEntries.hasMoreElements()) {
					Object entryKey   = attribEntries.nextElement();
					Object entryValue = htmlAttribs2.get(entryKey);
					sasAttr.addAttribute(entryKey, entryValue);
				}
				sasTag.addAttribute(htmlTag, sasAttr);
				parentTextPane.setCharacterAttributes(sasTag, false);
			}
			refreshAndSelect(parentTextPane, caretOffset, internalTextLength);
			parentTextPane.requestFocus();
		}
	}

	private void refreshAndSelect(JTextPane parentTextPane, int caretOffset, int internalTextLength) {
		parentKafenioPanel.refreshOnUpdate();
		parentTextPane.select(caretOffset, caretOffset + internalTextLength);
	}

	/**
	 * removes all formats except the given ones. images attributes 
	 * (src, border, width, height and alt) are never removed.
	 * @param myParentKafenioPanel the text pane to perform the removals on.
	 * @param selText the selected text.
	 * @param caretOffset the caret offset
	 * @param additionalAttributes the list of attributes to remove NOT.
	 */
	public void clearAllFormatsExcept(	KafenioPanel myParentKafenioPanel,
										String selText, 
										int caretOffset, 
										String[] additionalAttributes) 
	{
		JTextPane parentTextPane = myParentKafenioPanel.getTextPane();
		int internalTextLength;
		Vector skipAttributesList = new Vector();
		skipAttributesList.add(HTML.Attribute.SRC.toString());
		skipAttributesList.add(HTML.Attribute.BORDER.toString());
		skipAttributesList.add(HTML.Attribute.WIDTH.toString());
		skipAttributesList.add(HTML.Attribute.HEIGHT.toString());
		skipAttributesList.add(HTML.Attribute.ALT.toString());
		
		// add any HTML.Attributes to skipList...
		if (additionalAttributes != null) {
			for (int i=0; i < additionalAttributes.length; i++) {
				skipAttributesList.add(additionalAttributes[i].toString());
			}
		}

		// clear all paragraph attributes in selection
		SimpleAttributeSet sasText = 
			new SimpleAttributeSet(parentTextPane.getParagraphAttributes());
		for (Enumeration en = sasText.getAttributeNames(); en.hasMoreElements();) {
			Object elm = en.nextElement();
			sasText.removeAttribute(sasText.getAttribute(elm));
		}
		parentTextPane.setParagraphAttributes(sasText, true);

		// clear all character attributes in selection
		sasText = null;
		internalTextLength = selText.length();
		for(int i = caretOffset; i <= caretOffset + internalTextLength; i++) {
			parentTextPane.setCaretPosition(i);
			sasText = new SimpleAttributeSet(parentTextPane.getCharacterAttributes().copyAttributes());
			Enumeration attribEntries1 = sasText.getAttributeNames();
			while(attribEntries1.hasMoreElements()) {
				Object entryKey   = attribEntries1.nextElement();
				Object entryValue = sasText.getAttribute(entryKey);
				log.debug(entryKey + "=" + entryValue);
				try {
					for (int j=0; j < skipAttributesList.size(); j++) {
						if (entryKey.toString().equals(skipAttributesList.get(j))) throw new Exception();
					}
				} catch (Exception e) {
					continue;
				}
				if (!entryKey.toString().equals(HTML.Attribute.NAME.toString())) {
					log.debug("removing: " + entryKey.toString() + "=" + entryValue.toString());
					sasText.removeAttribute(entryKey);
				}
			}
			try {
				parentTextPane.select(i, i+1);
				parentTextPane.setCharacterAttributes(sasText, true);
			} catch (Exception e) {
				log.error("An Error ocurred: " + e.fillInStackTrace());
			}
		}
		log.debug("cleared everything.");
		refreshAndSelect(parentTextPane, caretOffset, internalTextLength);
		return;
	}
	
	/**
	 * clears the given list of HTML.Attributes and HTML.Tags from the selected Text.
	 * @param myParentKafenioPanel the parent KafenioPanel object.
	 * @param selText the selected text.
	 * @param caretOffset the caret offset.
	 * @param attributes the list of attributes to remove from each character.
	 */
	public void clearFormat(	KafenioPanel myParentKafenioPanel,
								String selText, 
								int caretOffset, 
								String[] attributes) 
	{
		JTextPane parentTextPane = myParentKafenioPanel.getTextPane();
		SimpleAttributeSet sasText = null;
		int internalTextLength = selText.length();

		// clear all character attributes in selection
		for(int i = caretOffset; i <= caretOffset + internalTextLength; i++) {
			parentTextPane.setCaretPosition(i);
			sasText = new SimpleAttributeSet(parentTextPane.getCharacterAttributes().copyAttributes());
			Enumeration attribEntries1 = sasText.getAttributeNames();
			while(attribEntries1.hasMoreElements()) {
				Object entryKey   = attribEntries1.nextElement();
				Object entryValue = sasText.getAttribute(entryKey);
				log.debug(entryKey + "=" + entryValue);
				for (int j=0; j < attributes.length; j++) {
					if (!entryKey.toString().equals(HTML.Attribute.NAME.toString())
						&& entryKey.toString().equals(attributes[j])) 
					{
						log.debug("removing: " + entryKey.toString() + "=" + entryValue.toString());
						sasText.removeAttribute(entryKey);
					}
				}
			}
			try {
				parentTextPane.select(i, i+1);
				parentTextPane.setCharacterAttributes(sasText, true);
			} catch (Exception e) {
				log.error("An Error ocurred: " + e.fillInStackTrace());
			}
		}
		log.debug("cleared everything.");
		refreshAndSelect(parentTextPane, caretOffset, internalTextLength);
		return;
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
