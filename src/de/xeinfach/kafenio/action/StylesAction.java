/*
GNU Lesser General Public License

StylesAction
Copyright (C) 2000-2003 Howard Kistler
changes to StylesAction
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

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for handling CSS style events
 * 
 * @author Karsten Pawlik
 */
public class StylesAction extends StyledEditorKit.StyledTextAction {

	private static LeanLogger log = new LeanLogger("StylesAction.class");
	
	private JComboBox parent;

	/**
	 * constructs a new StylesAction Object.
	 * @param myParent the Combobox to add the styles to.
	 */
	public StylesAction(JComboBox myParent) {
		super("css-style");
		parent = myParent;
		log.debug("created new StylesAction");
	}

	/**
	 * handles the given ActionEvent.
	 * @param e the ActionEvent to handle
	 */
	public void actionPerformed(ActionEvent e) {
		if(!(this.isEnabled())) {
			return;
		}
		JEditorPane editor = getEditor(e);
		if(editor != null) {
			String stylename = (String)(parent.getSelectedItem());

			if(stylename == null || parent.getSelectedIndex() <= 0)	return;
			boolean replace = false;
			MutableAttributeSet	attr = null;
			SimpleAttributeSet cls = new SimpleAttributeSet();
			cls.addAttribute(HTML.Attribute.CLASS, stylename);
			attr = new SimpleAttributeSet();
			attr.addAttribute(HTML.Tag.FONT, cls);

			MutableAttributeSet inattr = 
				((HTMLEditorKit)(editor.getEditorKitForContentType("text/html"))).getInputAttributes();

			inattr.addAttributes(attr);
			setCharacterAttributes(editor, attr, replace);
		}
	}
}
