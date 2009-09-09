/*
GNU Lesser General Public License

FontSelectorDialog
Copyright (C) 2003 Howard Kistler
changes to FontSelectorDialog
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

import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for providing a dialog that lets the user specify values for tag attributes
 * 
 * @author Howard Kistler
 */
public class FontSelectorDialog extends AbstractKafenioDialog implements ItemListener {

	private static LeanLogger log = new LeanLogger("FontSelectorDialog.class");

	private final JComboBox jcmbFontlist;
	private final JTextPane jtpFontPreview;
	private final Object[] panelContents;
	
	private Vector vcFontnames;
	private KafenioPanel parentKafenio;
	private String fontName;
	private String defaultText;
	private String[] fonts;
	
	/**
	 * creates a new FontSelectorDialog using the given values
	 * @param parent a Frame
	 * @param title frame title
	 * @param bModal boolean value
	 * @param attribName an attribute name
	 * @param demoText demo text to insert as default when new dialog window is shown.
	 */
	public FontSelectorDialog(KafenioPanel parent, String title, boolean bModal, String attribName, String demoText) {
		super(parent, title, bModal);
		
		if(demoText != null && demoText.length() > 0) {
			if(demoText.length() > 24) {
				defaultText = demoText.substring(0, 24);
			} else {
				defaultText = demoText;
			}
		} else {
			defaultText = "aAbBcCdDeEfFgGhH,.0123";
		}

		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		vcFontnames = new Vector(fonts.length - 5);
		for(int i = 0; i < fonts.length; i++) {
			if(	!fonts[i].equals("Dialog") 
				&& !fonts[i].equals("DialogInput") 
				&& !fonts[i].equals("Monospaced") 
				&& !fonts[i].equals("SansSerif") 
				&& !fonts[i].equals("Serif")) 
			{
				vcFontnames.add(fonts[i]);
			}
		}
		jcmbFontlist = new JComboBox(vcFontnames);
		jcmbFontlist.addItemListener(this);

		jtpFontPreview = new JTextPane();

		final HTMLEditorKit kitFontPreview = new HTMLEditorKit();
		final HTMLDocument docFontPreview = (HTMLDocument)(kitFontPreview.createDefaultDocument());

		jtpFontPreview.setEditorKit(kitFontPreview);
		jtpFontPreview.setDocument(docFontPreview);
		jtpFontPreview.setMargin(new Insets(4, 4, 4, 4));
		jtpFontPreview.setBounds(0, 0, 120, 18);
		jtpFontPreview.setText(getFontSampleString(defaultText));
		
		panelContents = new Object[] {	
			attribName, 
			jcmbFontlist, 
			parent.getTranslation("FontSample"), 
			jtpFontPreview 
		};
		init(panelContents);
	}

	/**
	 * sets the value based on the PropertyChangeEvent caught by the superclass.
	 * @param value default values to be used in this method.
	 */
	public void setDefaultValues(Object value) {
		if(value.equals(getButtonLabels()[0])) {
			fontName = (String)(jcmbFontlist.getSelectedItem());
			setVisible(false);
		} else {
			fontName = null;
			setVisible(false);
		}
	}

	/**
	 * handles the given ItemEvent
	 * @param event an ItemEvent to handle
	 */
	public void itemStateChanged(ItemEvent event) {
		if(event.getStateChange() == ItemEvent.SELECTED) {
			jtpFontPreview.setText(getFontSampleString(defaultText));
		}
	}

	/**
	 * creates a new FontSelectorDialog Object using the given values.
	 * @param parent parent frame
	 * @param title frame title
	 * @param bModal boolean value
	 * @param attribName an attribute name
	 */
	public FontSelectorDialog(KafenioPanel parent, String title, boolean bModal, String attribName) {
		this(parent, title, bModal, attribName, "");
	}

	/**
	 * @return returns the currently selected font name.
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * returns a sample html-string for the currently selected font.
	 * @param demoText text to return as sample-html
	 * @return returns a sample html-string for the currently selected font.
	 */
	private String getFontSampleString(String demoText) {
		return "<HTML><BODY><FONT FACE=" + '"' 
				+ jcmbFontlist.getSelectedItem() 
				+ '"' 
				+ ">" 
				+ demoText 
				+ "</FONT></BODY></HTML>";
	}
}

