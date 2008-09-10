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
		final String text;

		try {
			text = getText(0, getLength());

//			System.err.println("insertString TextBefore: " + getText(0, getLength()) + " fire:: " + isFireEvents());
			super.insertUpdate(chng, attr);
			if (fireEvents) {

				// SwingUtilities.invokeLater(new Runnable(){
				//
				// public void run() {

				try {
					if (fireEvents) {
						// Thread.dumpStack();
//						System.err.println("insertString Firing refresh: " + getText(0, getLength()));
						myBox.scheduleAjaxRefresh(AjaxEditorDocument.this.getText(0, getLength()), AjaxEditorDocument.this);
					}
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// System.err.println("insertString TextAfter: " + getText(0,
			// getLength()));
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		// System.err.println("TextAfter: "+getText(0, getLength()));

		// }});
		// }
	}

	@Override
	protected void removeUpdate(final DefaultDocumentEvent chng) {
		String text;
//		try {
//			System.err.println("BomRemoni " + chng.getOffset() + " - " + chng.getLength()+" original: "+getText(0, getLength()));
//		} catch (BadLocationException e2) {
			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
		try {
			text = getText(0, getLength());
			StringBuffer sp = new StringBuffer(text);
			sp.delete(chng.getOffset(), chng.getOffset()+ chng.getLength());
			text = sp.toString();
//			System.err.println("removeUpdate TextAfter: " + sp.toString());
			AjaxEditorDocument.super.removeUpdate(chng);
//			Thread.dumpStack();
			// System.err.println("removeUpdate TextAfter: " + getText(0,
			// getLength()));
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		// SwingUtilities.invokeLater(new Runnable(){
		//
		// public void run() {

		try {
			if (fireEvents && !text.equals(getText(0, getLength()))) {
				// Thread.dumpStack();
				System.err.println("removeUpdate Firing refresh: " + getText(0, getLength()) +" == "+text);
				myBox.scheduleAjaxRefresh(text, AjaxEditorDocument.this);
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
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
