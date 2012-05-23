package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.components.swingimpl.formatters.ColorFormatter;

@Deprecated
public class TipiSwingColorButton extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8938738809662470207L;
	private ColorFormatter myFormatter = new ColorFormatter();
	// private ColorParser cp = new ColorParser();
	private Property myProperty;
	private TipiComponent myComponent;

	public TipiSwingColorButton(TipiComponent tc) {
		myComponent = tc;
		getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent arg0) {
				updateMe(getText());
			}

			public void insertUpdate(DocumentEvent arg0) {
				updateMe(getText());
			}

			public void removeUpdate(DocumentEvent arg0) {
				updateMe(getText());
			}
		});
		// addPropertyChangeListener("text",new PropertyChangeListener(){
		//
		// public void propertyChange(PropertyChangeEvent e) {
		//
		// }});
	}

	public void updateMe(String text) {
		if (myProperty == null) {
			return;
		}
		if (text == null || "".equals(text) || !text.startsWith("{color:/")) {
			myProperty.setAnyValue(null);
			return;
		}
		Color c;
		try {
			c = (Color) myComponent.getContext().evaluateExpression(text,
					myComponent, null);
			setBackground(c);
			myProperty.setAnyValue(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setProperty(Property p) {
		myProperty = p;
		Object o = p.getTypedValue();
		if (o == null) {
			setText("unknown color");
			return;
		}
		Color cc = (Color) o;
		setBackground(cc);
		setText(myFormatter.format(cc));
	}

}
