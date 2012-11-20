package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;

public class PropertyTextArea extends JTextArea implements PropertyControlled {
	private static final long serialVersionUID = 6952361729861228490L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(PropertyTextArea.class);
	private String textValue;
	private Property initProperty = null;

	private BoundedLengthDocument myDocument = new BoundedLengthDocument();

	public void setProperty(Property p) {
		myDocument.setMaxLength(p.getLength());
		initProperty = p;
		textValue = (String) p.getTypedValue();
		String currentText = getText();
		updateText(p, currentText);
	}

	private void updateText(Property p, String currentText) {

		setEditable(p.isDirIn());
		if (!currentText.equals(textValue)) {
			setText(textValue);
		}
	}

	public Property getProperty() {
		return initProperty;
	}

	@SuppressWarnings("deprecation")
	public PropertyTextArea() {
		setDocument(myDocument);
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				textValue = getText();
				logger.info("MEMO FIELD: " + textValue);
				if (textValue != null) {
					initProperty.setValue(textValue);
				}
			}
		});

		InputMap im = getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
				InputEvent.SHIFT_DOWN_MASK), "ShiftEnterReleased");
		am.put("ShiftEnterReleased", new KeyEventHandler(this,
				"ShiftEnterReleased"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB,
				InputEvent.SHIFT_DOWN_MASK), "ShiftTabReleased");
		am.put("ShiftTabReleased",
				new KeyEventHandler(this, "ShiftTabReleased"));
	}

	public void update() {
		if (initProperty == null) {
			return;
		}
		textValue = getText();
		if (textValue != null) {
			logger.info("Setting ptopert: " + textValue);
			initProperty.setValue(textValue);
		}
	}



}
