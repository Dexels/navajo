package com.dexels.navajo.tipi.swingclient.components.remotecombobox;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AjaxEditorDocument extends PlainDocument {

	private static final long serialVersionUID = 7442690284070678853L;
	private boolean fireEvents = true;
	private final AjaxComboBox myBox;
	
	private final static Logger logger = LoggerFactory
			.getLogger(AjaxEditorDocument.class);
	
	public AjaxEditorDocument(AjaxComboBox m) {
		myBox = m;
	}

	@Override
	protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
		// final String text;

		// try {
		// text = getText(0, getLength());
		super.insertUpdate(chng, attr);
		if (fireEvents) {
			try {
				myBox.scheduleAjaxRefresh(
						AjaxEditorDocument.this.getText(0, getLength()),
						AjaxEditorDocument.this);
			} catch (BadLocationException e) {
				logger.error("Error: ",e);
			}
		}
		// } catch (BadLocationException e1) {
		// e1.printStackTrace();
		// return;
		// }
	}

	@Override
	protected void removeUpdate(final DefaultDocumentEvent chng) {
		String text;
		try {
			text = getText(0, getLength());
			StringBuffer sp = new StringBuffer(text);
			sp.delete(chng.getOffset(), chng.getOffset() + chng.getLength());
			text = sp.toString();
			AjaxEditorDocument.super.removeUpdate(chng);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
			return;
		}
		try {
			if (fireEvents && !text.equals(getText(0, getLength()))) {
				logger.info("removeUpdate Firing refresh: "
						+ getText(0, getLength()) + " == " + text);
				myBox.scheduleAjaxRefresh(text, AjaxEditorDocument.this);
			}
		} catch (BadLocationException e) {
			logger.error("Error: ",e);
		}
		super.removeUpdate(chng);

		// }
		// });
		//

	}

	public boolean isFireEvents() {
		return fireEvents;
	}

	public void setFireEvents(boolean fireEvents) {
		logger.info("Fire: " + fireEvents);
		this.fireEvents = fireEvents;
	}

	public static void main(String[] args) {
		String s = "anja,Jelle";
		StringBuffer sp = new StringBuffer(s);
		logger.info("o: " + sp);
		sp.delete(9, 10);
		logger.info("p: " + sp);
	}

}
