/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;

import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TextPropertyField extends PropertyField {
	private static final long serialVersionUID = -7981567590499362651L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TextPropertyField.class);
	private final BoundedLengthDocument myDocument = new BoundedLengthDocument();
	private String search = "off";
	private ImageIcon searchIcon = new ImageIcon(
			TextPropertyField.class.getResource("view.png"));

	public TextPropertyField() {
		this(true);
	}
	public TextPropertyField(Boolean addFocusLostListener) {
		super(addFocusLostListener);
		setDocument(myDocument);
		if (addFocusLostListener)
		{
			this.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					lostFocus(e);
				}
	
				@Override
				public void focusGained(FocusEvent e) {
					selectAll();
				}
			});
		}
		else
		{
			this.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					selectAll();
				}
			});
			
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// overridden, to fix the klopgeest-aap bug
	}

	private final void lostFocus(FocusEvent e) {
		// logger.info("---------------->> Vuur!");
		try {
			if (getText() == null) {
				logger.info("GetText() null");
				return;
			}

			if (initProperty != null) {
				if (!getText().equals(initProperty.getValue())) {
					initProperty.setValue(getText());
					String s = initProperty.getValue();
					if (!s.equals(getText())) {
						setText(s);
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Error: ", ex);
		}

	}

	@Override
	public final void update() {
		// logger.info("Update in property");
		lostFocus(null);
	}

	@Override
	public void setProperty(Property p) {
		if (p != null) {
			myDocument.setMaxLength(p.getLength());
			if (!(p.getType().equals(Property.STRING_PROPERTY)
					|| p.getType().equals(Property.EXPRESSION_PROPERTY)
					|| p.getType().equals(Property.MEMO_PROPERTY) || p
					.getType().equals(Property.TIPI_PROPERTY))) {
				// Toolkit.getDefaultToolkit().beep();
				try {
					logger.info("PROPERTY: " + p.getFullPropertyName()
							+ " is not of string type! Value: " + p.getValue()
							+ "");
				} catch (NavajoException ex) {
					logger.error("Error: ", ex);
				}
			} else {
				// Toch maar eerst kijken wat nu de waarde is voordat je hem
				// gewoon weer op zn oude (getrimde) waarde zet?
				Object o = p.getTypedValue();
				if (o != null && !(o instanceof String)) {
					logger.info("Problems setting property");
					try {
						logger.info("Name: " + p.getFullPropertyName());
					} catch (NavajoException ex1) {
						logger.error("Error: ", ex1);
					}
				}
				if (o != null) {
					textValue = o.toString();
				} else {
					textValue = "";
				}
				if (p.getLength() >= 1) {
					if (textValue != null) {
						textValue = textValue.trim();
						if (textValue.length() > p.getLength()) {
							textValue = textValue.substring(0, p.getLength());
							p.setValue(textValue);
						}
					} else {

					}
				}

				// Reset selected text, fixing JDK-problem?
				setSelectionStart(0);
				setSelectionEnd(0);
				setCaretPosition(0);

				setText(textValue);

			}
			String caps = p.getSubType("capitalization");
			// logger.info("TextPropField: " + p.getName() +
			// " capitalization: " + caps) ;
			if (caps != null) {
				setCapitalizationMode(caps);
			}

		} else {
			myDocument.setMaxLength(-1);
		}
		super.setProperty(p);
		// update();
	}

	@Override
	// TODO Check for length
	public void setText(String s) {
		try {
			myDocument.remove(0, myDocument.getLength());
			myDocument.insertString(0, s, null);
		} catch (BadLocationException ex) {
			System.err
					.println("Error setting text: " + s + " in propertyfield");
		}
		super.setText(s);
	}

	public final void setCapitalizationMode(String mode) {
		myDocument.setCapitalizationMode(mode);
	}

	public final String getCapitalizationMode() {
		return myDocument.getCapitalizationMode();
	}

	public final void setSearchMode(String mode) {
		this.search = mode;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if ("icon".equals(search)) {
			Graphics2D g2 = (Graphics2D) g;
			Composite old = g2.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.4f);
			g2.setComposite(ac);

			if (getHeight() > 16) {
				int iconHeight = searchIcon.getIconHeight();
				int diff = getHeight() - iconHeight;

				g2.drawImage(searchIcon.getImage(),
						(getWidth() - searchIcon.getIconWidth() - 2),
						(diff / 2), searchIcon.getIconWidth(),
						searchIcon.getIconHeight(), null);
				g2.setComposite(old);
			}

		}

	}

}
