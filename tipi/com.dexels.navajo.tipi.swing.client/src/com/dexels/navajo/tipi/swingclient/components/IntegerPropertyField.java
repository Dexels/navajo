package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.FocusEvent;

import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.PropertyTypeException;

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

public final class IntegerPropertyField extends PropertyField implements
		PropertyControlled {

	private static final long serialVersionUID = -4044131066393441848L;

	// private Property myProperty;
	private WholeNumberDocument myDocument = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(IntegerPropertyField.class);
	private boolean longMode = false;

	private boolean readOnly = false;

	public IntegerPropertyField() {
		this("true");
	}
	public IntegerPropertyField(String addFocusLostListener) {
		super(addFocusLostListener != null && addFocusLostListener.equals("true"));

		try {
			myDocument = new WholeNumberDocument();
			setDocument(myDocument);
			if (addFocusLostListener != null && addFocusLostListener.equals("true"))
			{
				this.addFocusListener(new java.awt.event.FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						this_focusLost(e);
					}
				});
			}
			if (getForcedAlignment() == null) {
				setHorizontalAlignment(SwingConstants.RIGHT);
			}
			// getDocument().addDocumentListener(new MyDocumentListener());
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	public IntegerPropertyField(boolean longMode, String addFocusLostListener) {
		this(addFocusLostListener);
		this.longMode = true;
	}

	public IntegerPropertyField(boolean longMode) {
		this("true");
		this.longMode = true;
	}

	public void setLongMode(boolean longMode) {
		this.longMode = longMode;
	}

	@Override
	public final void setProperty(Property p) {

		if (p == null) {
			return;
		}

		super.setProperty(p);
		// setValue(new Integer(0));
		if (!(p.getType().equals(Property.LONG_PROPERTY)
				|| p.getType().equals(Property.INTEGER_PROPERTY) || p.getType()
				.equals(Property.EXPRESSION_PROPERTY))) {

			// Toolkit.getDefaultToolkit().beep();
		}
		if (p.getLength() > 0) {
			myDocument.setMaxLength(p.getLength());
		}
		Object val = p.getTypedValue();
		if (val == null) {
			setText("");
		} else {
		}
		setText("" + val);
	}

	@Override
	public final void focusLost(FocusEvent e) {
		// No call to super. Good.
	}

	final void this_focusLost(FocusEvent e) {
		// try{
		if (getText() == null || "".equals(getText()) && initProperty != null) {
			initProperty.setValue((String) null);
			return;
		}
		try {
			if (initProperty != null) {
				if (longMode) {
					initProperty
							.setAnyValue(new Long(Long.parseLong(getText())));
					initProperty.setType(Property.LONG_PROPERTY);
				} else {
					initProperty.setAnyValue(new Integer(Integer
							.parseInt(getText())));

				}
			}
		} catch (PropertyTypeException ex1) {
			if (longMode) {
				if(initProperty!=null) {
					initProperty.setValue((Long) null);
				}

			} else {
				if(initProperty!=null) {
					initProperty.setValue((Integer) null);
				}
			}
			setText("");
			ex1.printStackTrace();
		}
	}

	public final void setReadOnly(boolean b) {
		readOnly = b;
	}

	@Override
	public final void update() {
		super.update();
		if ((initProperty == null) || readOnly) {
			return;
		}
		// updateChanged(initProperty);
		try {
			initProperty.setValue(getText());

		} catch (PropertyTypeException ex1) {
			ex1.printStackTrace();
		}

	}

}

final class WholeNumberDocument extends BoundedLengthDocument {
	private static final long serialVersionUID = 805229741318103919L;

	@Override
	public final void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		char[] source = str.toCharArray();
		char[] result = new char[source.length];
		int j = 0;
		for (int i = 0; i < result.length; i++) {
			if (Character.isDigit(source[i]) || source[0] == '-') {
				result[j++] = source[i];
			}
		}
		// logger.info("Aap: "+new String(result));
		super.insertString(offs, new String(result, 0, j), a);
	}
}
