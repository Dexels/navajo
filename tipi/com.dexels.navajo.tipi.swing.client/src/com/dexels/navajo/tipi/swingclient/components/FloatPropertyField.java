package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.FocusEvent;

import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;

public class FloatPropertyField extends PropertyField implements
		PropertyControlled {
	private static final long serialVersionUID = -315919846250291649L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(FloatPropertyField.class);
	
	protected Document myDocument = null;
	protected boolean readOnly = false;

	public FloatPropertyField() {
		this(true);
		
	}
	public FloatPropertyField(Boolean addFocusLostListener) {
		super(addFocusLostListener);

		try {
			myDocument = new FloatNumberDocument();
			setDocument(myDocument);
			jbInit(addFocusLostListener);
			if (getForcedAlignment() == null) {
				setHorizontalAlignment(SwingConstants.RIGHT);
			}
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	public void setProperty(Property p) {

		initProperty = p;
		if (p == null) {
			return;
		}

		if (!(p.getType().equals(Property.FLOAT_PROPERTY) || p.getType()
				.equals(Property.EXPRESSION_PROPERTY))) {
			// Toolkit.getDefaultToolkit().beep();
		}
		Object val = p.getTypedValue();
		String inText = null;
		if (val == null) {
			inText = "";
		} else {
			inText = val.toString();
		}

		try {
			String text = myDocument.getText(0, myDocument.getLength());
			if (!text.equals("")) {
				double d = Double.parseDouble(text);
				text = "" + d;
			}
			if (text.equals(inText)) {
			} else {
				myDocument.remove(0, myDocument.getLength());
				myDocument.insertString(0, "" + val, null);
			}
		} catch (BadLocationException ex1) {
			logger.error("Error: ", ex1);
		}
		setEditable(p.isDirIn());
		super.setProperty(p);
	}

	protected void jbInit(Boolean addFocusLostListener) throws Exception {
		myDocument
				.addDocumentListener(new javax.swing.event.DocumentListener() {
					@Override
					public void changedUpdate(DocumentEvent e) {
						myDocument_changedUpdate(e);
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						myDocument_insertUpdate(e);
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						myDocument_removeUpdate(e);
					}
				});
		if (addFocusLostListener)
		{
			this.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					moneyFocusLost(e);
				}
	
				@Override
				public void focusGained(FocusEvent e) {
					this_focusGained(e);
				}
			});
		}
		else
		{
			this.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					this_focusGained(e);
				}
			});
		}
	}

	void this_focusGained(FocusEvent e) {
		// aap
	}

	// public Property getProperty(){
	// if(initProperty.getType().equals(Property.EXPRESSION_PROPERTY)){
	// Thread.dumpStack();
	// }
	// return super.getProperty();
	// }

	final void moneyFocusLost(FocusEvent e) {
		try {
			// updateChanged(initProperty);
			if (getText() == null || "".equals(getText())) {
				return;
			}
			double d = Double.parseDouble(getText());
			if (initProperty != null) {
				initProperty.setValue(d);
				// setChanged(true);
			}
		} catch (Exception ex) {
			logger.error("Error: ", ex);
		}
	}

	void myDocument_changedUpdate(DocumentEvent e) {

	}

	void myDocument_insertUpdate(DocumentEvent e) {
		if (initProperty != null) {
			if (!initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
			}
			if (getText() != null && !getText().equals("")) {
				// setValidationState(Validatable.VALID);
			}
		}
	}

	void myDocument_removeUpdate(DocumentEvent e) {
		if (initProperty == null) {
			return;
		}

		if (!initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
			// updateChanged(initProperty);
			// initProperty.setValue(getText());
		}

	}

	public final void setReadOnly(boolean b) {
		readOnly = b;
	}

	@Override
	public void update() {
		// super.update();
		// try {
		// commitEdit();
		// }
		// catch (ParseException ex) {
		// logger.info("Parse problem.");
		// return;
		// }

		if ((initProperty == null) || readOnly) {
			return;
		}
		// this.setFormatterFactory(JFormattedTextField);
		if (!"".equals(getText())) {
			double d = Double.parseDouble(getText());

			// logger.info("Proceeding with update: "+getValue());
			if (initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
				return;
			}
			// updateChanged(initProperty);
			initProperty.setValue(Double.toString(d));
			// if (getText() != null && !getText().equals("")) {
			// setValidationState(Validatable.VALID);
			// }
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// a REAL klopgeest aap bug
	}
}

final class FloatNumberDocument extends PlainDocument {

	private static final long serialVersionUID = 8231480445427602516L;

	@Override
	public final void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		char[] source = str.toCharArray();
		char[] result = new char[source.length];
		int j = 0;
		// boolean dot = false;
		// String oldVal = super.getText(0,getLength());
		// String start = oldVal.substring(0,offs);
		// String end = oldVal.substring(offs,oldVal.length());
		// String total = start+str+end;
		// logger.info("OldVal: "+oldVal);
		// logger.info("Start: "+start);
		// logger.info("End: "+end);
		// logger.info("Total: "+total);
		for (int i = 0; i < result.length; i++) {
			if (Character.isDigit(source[i])) {
				result[j++] = source[i];
			} else if (source[i] == ',' || source[i] == '.') {
				// if (dot == true) {
				// logger.info("Second dot encountered:  " + str);
				// }
				// else {
				// dot = true;
				result[j++] = '.';
				// }
			} else {
				// Toolkit.getDefaultToolkit().beep();
				// logger.info("Invalid char (not numeric) in string:  "+str);
			}
		}
		super.insertString(offs, new String(result, 0, j), a);
	}

	public static void main(String[] args) {
		Double.parseDouble("4,3");
	}

}

// class WholeNumberDocument extends PlainDocument {
// public void insertString(int offs, String str, AttributeSet a) throws
// BadLocationException {
// char[] source = str.toCharArray();
// char[] result = new char[source.length];
// int j = 0;
// for (int i = 0; i < result.length; i++) {
// if (Character.isDigit(source[i]) || source[i]=='.') {
// result[j++] = source[i];
// }
// else {
// Toolkit.getDefaultToolkit().beep();
// logger.info("Invalid char (not numeric) in string:  "+str);
// }
// }
// super.insertString(offs, new String(result, 0, j), a);
// }
// }
