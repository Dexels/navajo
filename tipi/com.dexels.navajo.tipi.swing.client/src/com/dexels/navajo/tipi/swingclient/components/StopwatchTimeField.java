package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.FocusEvent;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.StopwatchTime;

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

public class StopwatchTimeField extends PropertyField {

	private static final long serialVersionUID = 562717952596187335L;
	private Document myDocument = null;

	public StopwatchTimeField() {
		this(true);
	}
	
	public StopwatchTimeField(Boolean addFocusLostListener) {
		super(addFocusLostListener);
		myDocument = new StopwatchTimeDocument();
		setDocument(myDocument);
	}

	@Override
	public final void focusLost(FocusEvent e) {

		try {
			if (getText() == null || "".equals(getText().trim())) {
				getProperty().setValue((StopwatchTime) null);
				return;
			}
			StopwatchTime ct = new StopwatchTime(getText(), getProperty()
					.getSubType());
			StopwatchTime oldTime = (StopwatchTime) getProperty()
					.getTypedValue();
			if (!ct.equals(oldTime)) {
				getProperty().setValue(ct);
			}
			setText(ct.toString());
		} catch (Exception ex) {
			setText("");
			if (getProperty() != null) {
				getProperty().setValue((StopwatchTime) null);
			}
		}
	}

	@Override
	public final void focusGained(FocusEvent e) {
	}

	private final void setFormatText() {
		if (getProperty() == null) {
			setText("");
			return;
		}
		if (getProperty().getTypedValue() == null) {
			setText("");
			return;
		}
		if (getProperty().getTypedValue().toString() == null) {
			setText("");
			return;
		}
		setText(getProperty().getTypedValue().toString());
	}

	@Override
	public final void setProperty(Property p) {
		super.setProperty(p);
		setFormatText();
	}

	@Override
	public final void update() {
		focusLost(null);
	}
}

final class StopwatchTimeDocument extends PlainDocument {

	private static final long serialVersionUID = 7690878176633537384L;

	@Override
	public final void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		char[] source = str.toCharArray();
		char[] result = new char[source.length];
		int j = 0;
		for (int i = 0; i < result.length; i++) {
			if ((Character.isDigit(source[i]) || source[i] == ':')
					|| source[i] == ',' && (getLength() + j) < 12) {
				result[j++] = source[i];
			} else {
			}
		}
		super.insertString(offs, new String(result, 0, j), a);
	}

}
