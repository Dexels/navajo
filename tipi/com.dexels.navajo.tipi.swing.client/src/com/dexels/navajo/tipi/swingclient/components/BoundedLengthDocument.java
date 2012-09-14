package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

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

class BoundedLengthDocument extends PlainDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2888344441704553296L;
	// private Property myProperty = null;
	private String myCapitalizationMode = "off";
	private boolean check = true;

	private int maxLength = -1;

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		// getLength()
		// logger.info("Insert");
		// int maxlength = myProperty.getLength();
		// Check the length, on output properties length is often not specified,
		// this way the text will still be visible
		if (getMaxLength() <= 0) {
			setMaxLength(255);
		}
		if (str == null) {
			return;
		}
		if (getLength() < getMaxLength()) {
			char[] source = str.toCharArray();
			char[] result = new char[source.length];
			int j = 0;
			for (int i = 0; i < result.length; i++) {
				if (getLength() + i < getMaxLength()) {
					result[j++] = source[i];
				} else {
					// logger.info("Can not add to: "+myProperty.getValue());
					// Toolkit.getDefaultToolkit().beep();
				}
			}
			super.insertString(offs, new String(result, 0, j), a);
			updateCapitalization();
		}
	}

	@Override
	protected void postRemoveUpdate(DefaultDocumentEvent chng) {
		super.postRemoveUpdate(chng);
		updateCapitalization();
	}

	@Override
	protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
		super.insertUpdate(chng, attr);
		updateCapitalization();
	}

	public void setCapitalizationMode(String mode) {
		myCapitalizationMode = mode;
		updateCapitalization();
	}

	public String getCapitalizationMode() {
		return myCapitalizationMode;
	}

	private final void setCheck(boolean b) {
		check = b;
	}

	private synchronized void updateCapitalization() {
		if (!check) {
			return;
		}
		if (getLength() <= 0) {
			return;
		}
		if ("firstupper".equals(myCapitalizationMode)) {
			try {
				String s = getText(0, 1);
				if (!s.toUpperCase().equals(s)) {
					setCheck(false);
					remove(0, 1);
					insertString(0, s.toUpperCase(), null);
					setCheck(true);
				}

			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}
		if ("upper".equals(myCapitalizationMode)) {
			try {
				String s = getText(0, getLength());
				if (!s.toUpperCase().equals(s)) {
					setCheck(false);
					remove(0, getLength());
					insertString(0, s.toUpperCase(), null);
					setCheck(true);
				}
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}
		if ("lower".equals(myCapitalizationMode)) {
			try {
				String s = getText(0, getLength());
				if (!s.toLowerCase().equals(s)) {
					setCheck(false);
					remove(0, getLength());
					insertString(0, s.toLowerCase(), null);
					setCheck(true);
				}
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

	}
}
