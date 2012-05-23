package com.dexels.navajo.swingeditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.KafenioPanelConfiguration;
import de.xeinfach.kafenio.component.ExtendedHTMLDocument;

/**
 * 
 * @author Frank Lyaruu
 * 
 */
public class SwingEditor extends KafenioPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2156410387429367534L;
	private Property myProperty;
	private ArrayList<Binary> attachments = new ArrayList<Binary>();

	public SwingEditor(KafenioPanelConfiguration gpc) {
		super(gpc);

		addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				// System.err.println("Log: " + evt.getPropertyName() + " val: "
				// + evt.getNewValue());
			}
		});
		getSourcePane().getDocument().addDocumentListener(
				new DocumentListener() {

					public void changedUpdate(DocumentEvent e) {
						fireChange("change", e);
					}

					public void insertUpdate(DocumentEvent e) {
						fireChange("insert", e);
					}

					public void removeUpdate(DocumentEvent e) {
						fireChange("remove", e);
					}

					private void fireChange(String changeType, DocumentEvent e) {

					}
				});
	}

	public ArrayList<Binary> getImages() {
		return attachments;
	}

	public void setProperty(Property body) {
		myProperty = body;
		if (!body.getType().equals(Property.BINARY_PROPERTY)) {
			if (body.getValue() != null) {
				setDocumentText(body.getValue());
			}
		} else {
			// read from binary.
		}
	}

	public void insert(String text, int caretPos) {
		// setCaretPosition(caretPos);
		try {
			getExtendedHtmlDoc().insertString(caretPos, text, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public Property getProperty() {
		return myProperty;
	}

	public String getText() {
		return getDocumentText();
	}

	public String getProcessedText() {
		try {
			ArrayList<String> paths = new ArrayList<String>();
			ExtendedHTMLDocument doc = getExtendedHtmlDoc();
			HTMLDocument.Iterator it = doc.getIterator(HTML.getTag("img"));
			AttributeSet attributes = it.getAttributes();
			if (attributes != null) {
				Enumeration<?> names = attributes.getAttributeNames();
				while (names.hasMoreElements()) {
					Object name = names.nextElement();
					Object value = attributes.getAttribute(name);
					System.err.println(name + " is: " + value);

					if ("src".equals(name.toString())) {
						String imgPath = value.toString();
						Binary b = new Binary(new File(imgPath));
						attachments.add(b);
						paths.add(imgPath);
					}
				}
			}

			String text = getDocumentText();
			for (int i = 0; i < paths.size(); i++) {
				text = replaceString(text, paths.get(i), "cid:attach-nr-" + i);
			}
			text = replaceString(text, "&#8224;", " ");
			return text;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String replaceString(String input, String oldValue, String newValue) {
		int index = input.indexOf(oldValue);
		if (index > -1) {
			String head = input.substring(0, index);
			String tail = input.substring(index + oldValue.length());
			return replaceString(head + newValue + tail, oldValue, newValue);
		} else {
			return input;
		}
	}

	public static void main(String[] args) throws BadLocationException {

		KafenioPanelConfiguration gpc = new KafenioPanelConfiguration();
		final SwingEditor kp = new SwingEditor(gpc);
		JFrame jf = new JFrame("aap");
		jf.getContentPane().add(kp, BorderLayout.CENTER);
		jf.setBounds(100, 100, 500, 300);
		jf.setVisible(true);
		JButton jb = new JButton("Teext");
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExtendedHTMLDocument extendedHtmlDoc = kp.getExtendedHtmlDoc();
				String result;
				try {
					result = extendedHtmlDoc.getText(0,
							extendedHtmlDoc.getLength());
					System.err.println("Result: " + kp.getProcessedText());
					System.err.println("Result2: "+result);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

			}
		});
		kp.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				System.err.println("Log: " + evt.getPropertyName() + " val: "
						+ evt.getNewValue());

			}
		});
		jf.getContentPane().add(jb, BorderLayout.SOUTH);
		kp.setDocumentText("Mujaheddin!");
	}

}
