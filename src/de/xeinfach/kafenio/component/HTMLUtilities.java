/*
GNU Lesser General Public License

HTMLUtilities - Special Utility Functions For Ekit
Copyright (C) 2003 Rafael Cieplinski, modified by Howard Kistler
changes to HTMLUtilities
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
package de.xeinfach.kafenio.component;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: HTMLUtilities - Special Utility Functions For Kafenio 
 * Copyright (C) 2003 Rafael Cieplinski, modified by Howard Kistler
 * @author Copyright (C) 2003 Rafael Cieplinski, modified by Howard Kistler, Karsten Pawlik
 */
public class HTMLUtilities {

	private static LeanLogger log = new LeanLogger("HTMLUtilities.class");
	
	private KafenioPanel parentKafenioPanel;
	private Hashtable tags = new Hashtable();

	/**
	 * creates a new HTMLUtilities Object using the given value.
	 * @param newParent a reference to an instance of the KafenioPanel class.
	 */
	public HTMLUtilities(KafenioPanel newParent) {
		parentKafenioPanel = newParent;
		HTML.Tag[] tagList = HTML.getAllTags();
		for(int i = 0; i < tagList.length; i++) {
			tags.put(tagList[i].toString(), tagList[i]);
		}
		log.debug("new HTMLUtilities created.");
	}

	/** 
	 * This method adds the given content as a list element.
	 * @param content content to add to list
	 */
	public void insertListElement(String content) {
		int pos = parentKafenioPanel.getCaretPosition();
		String source = parentKafenioPanel.getSourcePane().getText();
		boolean hit = false;
		String idString;
		int counter = 0;
		do {
			hit = false;
			idString = "diesisteineidzumsuchenimsource" + counter;
			if(source.indexOf(idString) > -1) {
				counter++;
				hit = true;
				if(counter > 10000) {
					return;
				}
			}
		} while(hit);
		Element element = getListItemParent();
		if(element == null) {
			return;
		}
		SimpleAttributeSet sa = new SimpleAttributeSet(element.getAttributes());
		sa.addAttribute("id", idString);
		
		parentKafenioPanel.getExtendedHtmlDoc().replaceAttributes(element, sa, HTML.Tag.LI);
		parentKafenioPanel.refreshOnUpdate();
		source = parentKafenioPanel.getSourcePane().getText();
		
		StringBuffer newHtmlString = new StringBuffer();
		int[] positions = getPositions(element, source, true, idString);
		newHtmlString.append(source.substring(0, positions[3]));
		newHtmlString.append("<li>");
		newHtmlString.append(content);
		newHtmlString.append("</li>");
		newHtmlString.append(source.substring(positions[3] + 1, source.length()));
		
		parentKafenioPanel.getTextPane().setText(newHtmlString.toString());
		parentKafenioPanel.refreshOnUpdate();
		parentKafenioPanel.setCaretPosition(pos - 1);
		
		element = getListItemParent();
		sa = new SimpleAttributeSet(element.getAttributes());
		sa = removeAttributeByKey(sa, "id");
		
		parentKafenioPanel.getExtendedHtmlDoc().replaceAttributes(element, sa, HTML.Tag.LI);
	}


	/** 
	 * Method to remove the given element using stringmanipulation in the source-pane.<BR>
	 * its an alternative method for removeElement in ExtendedHTMLDocument.<BR>
	 * if closingTag is true, an end-tag for the given element is expected.<BR>
	 * @param element an element
	 * @param closingTag a closing tag
	 */
	public void removeTag(Element element, boolean closingTag) {
		if(element == null) {
			return;
		}
		
		int pos = parentKafenioPanel.getCaretPosition();
		HTML.Tag tag = getHTMLTag(element);

		String source = parentKafenioPanel.getSourcePane().getText();
		boolean hit = false;
		String idString;
		int counter = 0;
		do {
			hit = false;
			idString = "diesisteineidzumsuchenimsource" + counter;
			if(source.indexOf(idString) > -1) {
				counter++;
				hit = true;
				if(counter > 10000) {
					return;
				}
			}
		} while(hit);

		SimpleAttributeSet sa = new SimpleAttributeSet(element.getAttributes());
		sa.addAttribute("id", idString);

		parentKafenioPanel.getExtendedHtmlDoc().replaceAttributes(element, sa, tag);
		parentKafenioPanel.refreshOnUpdate();
		source = parentKafenioPanel.getSourcePane().getText();

		StringBuffer newHtmlString = new StringBuffer();
		int[] position = getPositions(element, source, closingTag, idString);

		if(position == null) {
			return;
		}
		for(int i = 0; i < position.length; i++) {
			if(position[i] < 0) {
				return;
			}
		}

		int beginStartTag = position[0];
		int endStartTag = position[1];
		if(closingTag) {
			int beginEndTag = position[2];
			int endEndTag = position[3];
			newHtmlString.append(source.substring(0, beginStartTag));
			newHtmlString.append(source.substring(endStartTag, beginEndTag));
			newHtmlString.append(source.substring(endEndTag, source.length()));
		} else {
			newHtmlString.append(source.substring(0, beginStartTag));
			newHtmlString.append(source.substring(endStartTag, source.length()));
		}

		parentKafenioPanel.getTextPane().setText(newHtmlString.toString());
		parentKafenioPanel.refreshOnUpdate();
	}

	/** 
	 * Diese Methode gibt jeweils den Start- und Endoffset des Elements
	 * sowie dem entsprechenden schließenden Tag zurück
	 */
	private int[] getPositions(Element element, String source, boolean closingTag, String idString) {
		HTML.Tag tag = getHTMLTag(element);
		int[] position = new int[4];
		for(int i = 0; i < position.length; i++) {
			position[i] = -1;
		}
		
		String searchString = "<" + tag.toString();
		int caret = -1; // aktuelle Position im sourceString

		if((caret = source.indexOf(idString)) != -1) {
			position[0] = source.lastIndexOf("<",caret);
			position[1] = source.indexOf(">",caret)+1;
		}

		if(closingTag) {
			String searchEndTagString = "</" + tag.toString() + ">";
			int hitUp = 0;
			int beginEndTag = -1;
			int endEndTag = -1;
			caret = position[1];
			boolean end = false;

			beginEndTag = source.indexOf(searchEndTagString, caret);
			endEndTag = beginEndTag + searchEndTagString.length();

			int interncaret = position[1];
			do {
				int temphitpoint = -1;
				boolean flaghitup = false;
				hitUp = 0;
				do {
					flaghitup = false;
					temphitpoint = source.indexOf(searchString, interncaret);
					if(temphitpoint > 0 && temphitpoint < beginEndTag) {
						hitUp++;
						flaghitup = true;
						interncaret = temphitpoint + searchString.length();
					}
				} while(flaghitup);

				if(hitUp == 0) {
					end = true;
				} else {
					for(int i = 1; i <= hitUp; i++) {
						caret = endEndTag;
						beginEndTag = source.indexOf(searchEndTagString, caret);
						endEndTag = beginEndTag + searchEndTagString.length();
					}
					end = false;
				}
			} while(!end);

			if(beginEndTag < 0 | endEndTag < 0) {
				return null;
			}

			position[2] = beginEndTag;
			position[3] = endEndTag;
		}
		return position;
	}

	/**
	 * Method to check if the given tag is in the hierarchy above the current position or not.
	 * @param tag HTML.Tag to search for.
	 * @return returns true or false. 
	 */
	public boolean checkParentsTag(HTML.Tag tag) {
		Element e = parentKafenioPanel.getExtendedHtmlDoc().getParagraphElement(parentKafenioPanel.getCaretPosition());
		String tagString = tag.toString();
		if(e.getName().equalsIgnoreCase(tag.toString())) {
			return true;
		}
		do {
			if((e = e.getParentElement()).getName().equalsIgnoreCase(tagString)) {
				return true;
			}
		} while(!(e.getName().equalsIgnoreCase("html")));
		return false;
	}

	/**
	 * @return Diese Methoden geben das erste gefundende dem übergebenen tags entsprechende Element zurück
	 */
	public Element getListItemParent() {
		String listItemTag = HTML.Tag.LI.toString();
		Element elementSearch = 
			parentKafenioPanel.getExtendedHtmlDoc().getCharacterElement(parentKafenioPanel.getCaretPosition());
		do {
			if(listItemTag.equals(elementSearch.getName())) {
				return elementSearch;
			}
			elementSearch = elementSearch.getParentElement();
		} while(elementSearch.getName() != HTML.Tag.HTML.toString());
		return null;
	}

	/**
	 * @param sourceAS source AttributeSet
	 * @param removeKey remove key from attributeset
	 * @return Diese Methode entfernt Attribute aus dem SimpleAttributeSet, gemäß den übergebenen Werten, und
	 * gibt das Ergebnis als SimpleAttributeSet zurück
	 */

	public SimpleAttributeSet removeAttributeByKey(SimpleAttributeSet sourceAS, String removeKey) {
		SimpleAttributeSet temp = new SimpleAttributeSet();
		temp.addAttribute(removeKey, "NULL");
		return removeAttribute(sourceAS, temp);
	}

	/**
	 * 
	 * @param sourceAS source AttributeSet
	 * @param removeAS AttributeSet to remove
	 * @return Diese Methode entfernt Attribute aus dem SimpleAttributeSet, gemäß den übergebenen Werten, und
	 * gibt das Ergebnis als SimpleAttributeSet zurück
	 */
	public SimpleAttributeSet removeAttribute(SimpleAttributeSet sourceAS, SimpleAttributeSet removeAS) {
		try {
			String[] sourceKeys = new String[sourceAS.getAttributeCount()];
			String[] sourceValues = new String[sourceAS.getAttributeCount()];
			Enumeration sourceEn = sourceAS.getAttributeNames();
			int i = 0;

			while(sourceEn.hasMoreElements()) {
				Object temp = new Object();
				temp = sourceEn.nextElement();
				sourceKeys[i] = (String) temp.toString();
				sourceValues[i] = new String();
				sourceValues[i] = (String) sourceAS.getAttribute(temp).toString();
				i++;
			}

			String[] removeKeys = new String[removeAS.getAttributeCount()];
			String[] removeValues = new String[removeAS.getAttributeCount()];
			Enumeration removeEn = removeAS.getAttributeNames();
			int j = 0;
			while(removeEn.hasMoreElements()) {
				removeKeys[j] = (String) removeEn.nextElement().toString();
				removeValues[j] = (String) removeAS.getAttribute(removeKeys[j]).toString();
				j++;
			}

			SimpleAttributeSet result = new SimpleAttributeSet();
			boolean hit = false;
			for(int countSource = 0; countSource < sourceKeys.length; countSource++) {
				hit = false;
				if(sourceKeys[countSource] == "name" | sourceKeys[countSource] == "resolver") {
					hit = true;
				} else {
					for(int countRemove = 0; countRemove < removeKeys.length; countRemove++) {
						if(removeKeys[countRemove] != "NULL") {
							if(sourceKeys[countSource].toString() == removeKeys[countRemove].toString()) {
								if(removeValues[countRemove] != "NULL") {
									if(sourceValues[countSource].toString() == removeValues[countRemove].toString()) {
										hit = true;
									}
								} else if(removeValues[countRemove] == "NULL") {
									hit = true;
								}
							}
						} else if(removeKeys[countRemove] == "NULL") {
							if(sourceValues[countSource].toString() == removeValues[countRemove].toString()) {
								hit = true;
							}
						}
					}
				}
				
				if(!hit) {
					result.addAttribute(sourceKeys[countSource].toString(), sourceValues[countSource].toString());
				}
			}
			return result;
		} catch (ClassCastException cce) {
			return null;
		}
	}

	/**
	 * liefert den entsprechenden HTML.Tag zum Element zurück
	 * 
	 * @param elem element to get html tag for
	 * @return returns the element's HTML tag
	 */
	public HTML.Tag getHTMLTag(Element elem) {
		if(tags.containsKey(elem.getName())) {
			return (HTML.Tag)tags.get(elem.getName());
		} else {
			return null;
		}
	}

	/**
	 * @param strings number of strings to get.
	 * @return returns an array of strings
	 */
	public String[] getUniString(int strings) {
		parentKafenioPanel.refreshOnUpdate();
		String[] result = new String[strings];
		String source = parentKafenioPanel.getSourcePane().getText();
		for(int i=0; i<strings; i++) {
			int start = -1, end = -1;
			boolean hit = false;
			String idString;
			int counter = 0;
			do {
				hit = false;
				idString = "diesisteineidzumsuchen" + counter + "#" + i;
				if(source.indexOf(idString) > -1) {
					counter++;
					hit = true;
					if(counter > 10000) {
						return null;
					}
				}
			} while(hit);
			result[i] = idString;
		}
		return result;
	}

	/**
	 * @throws BadLocationException an exception
	 * @throws IOException an exception
	 */
	public void delete() throws BadLocationException,IOException {
		JTextPane jtpMain = parentKafenioPanel.getTextPane();
		JTextPane jtpSource = parentKafenioPanel.getSourcePane();
		ExtendedHTMLDocument htmlDoc = parentKafenioPanel.getExtendedHtmlDoc();
		int selStart = jtpMain.getSelectionStart();
		int selEnd = jtpMain.getSelectionEnd();
		String[] posStrings = getUniString(2);

		if(posStrings == null) {
			return;
		}

		htmlDoc.insertString(selStart,posStrings[0],null);
		htmlDoc.insertString(selEnd+posStrings[0].length(),posStrings[1],null);
		int start = jtpSource.getText().indexOf(posStrings[0]);
		int end = jtpSource.getText().indexOf(posStrings[1]);

		if(start == -1 || end == -1) {
			return;
		}

		String htmlString = new String();
		htmlString += jtpSource.getText().substring(0,start);
		htmlString += jtpSource.getText().substring(start + posStrings[0].length(), end);
		htmlString += jtpSource.getText().substring(end + posStrings[1].length(), jtpSource.getText().length());
		String source = htmlString;
		end = end - posStrings[0].length();
		htmlString = new String();
		htmlString += source.substring(0,start);
		htmlString += getAllTableTags(source);
		htmlString += source.substring(end, source.length());
		parentKafenioPanel.getTextPane().setText(htmlString);
		parentKafenioPanel.refreshOnUpdate();
	}

	/**
	 * @param source html sourcecode
	 * @return returns all table tags.
	 * @throws BadLocationException
	 * @throws IOException
	 */
	private String getAllTableTags(String source) throws BadLocationException,IOException {
		StringBuffer result = new StringBuffer();
		int caret = -1;
		do {
			caret++;
			int[] tableCarets = new int[6];
			tableCarets[0] = source.indexOf("<table",caret);
			tableCarets[1] = source.indexOf("<tr",caret);
			tableCarets[2] = source.indexOf("<td",caret);
			tableCarets[3] = source.indexOf("</table",caret);
			tableCarets[4] = source.indexOf("</tr",caret);
			tableCarets[5] = source.indexOf("</td",caret);
			java.util.Arrays.sort(tableCarets);
			caret = -1;
			for(int i=0; i<tableCarets.length; i++) {
				if(tableCarets[i] >= 0) {
					caret = tableCarets[i];
					break;
				}
			}
			if(caret != -1) {
				result.append(source.substring(caret,source.indexOf(">",caret)+1));
			}
		} while(caret != -1);

		return result.toString();
	}
	
}