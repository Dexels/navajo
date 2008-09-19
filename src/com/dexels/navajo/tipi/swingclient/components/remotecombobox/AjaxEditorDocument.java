package com.dexels.navajo.tipi.swingclient.components.remotecombobox;

import javax.swing.text.*;

public class AjaxEditorDocument extends PlainDocument {

	private boolean fireEvents = true;
	private final AjaxComboBox myBox;

	public AjaxEditorDocument(AjaxComboBox m) {
		myBox = m;
	}

	@Override
	protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
//		final String text;

//		try {
//			text = getText(0, getLength());
			super.insertUpdate(chng, attr);
			if (fireEvents) {
				try {
					if (fireEvents) {
						myBox.scheduleAjaxRefresh(AjaxEditorDocument.this.getText(0, getLength()), AjaxEditorDocument.this);
					}
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
//		} catch (BadLocationException e1) {
//			e1.printStackTrace();
//			return;
//		}
	}

	@Override
	protected void removeUpdate(final DefaultDocumentEvent chng) {
		String text;
		try {
			text = getText(0, getLength());
			StringBuffer sp = new StringBuffer(text);
			sp.delete(chng.getOffset(), chng.getOffset()+ chng.getLength());
			text = sp.toString();
			AjaxEditorDocument.super.removeUpdate(chng);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
			return;
		}
		try {
			if (fireEvents && !text.equals(getText(0, getLength()))) {
				System.err.println("removeUpdate Firing refresh: " + getText(0, getLength()) +" == "+text);
				myBox.scheduleAjaxRefresh(text, AjaxEditorDocument.this);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
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
		System.err.println("Fire: " + fireEvents);
		this.fireEvents = fireEvents;
	}

	public static void main(String[] args) {
		String s = "anja,Jelle";
		StringBuffer sp = new StringBuffer(s);
		System.err.println("o: "+sp);
		sp.delete(9, 10);
		System.err.println("p: "+sp);
	}
	
}
