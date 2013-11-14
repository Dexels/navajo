package de.xeinfach.kafenio.component;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.Element;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.HTML;

import javax.swing.undo.UndoableEdit;

import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;

import de.xeinfach.kafenio.util.LeanLogger;

import java.util.Enumeration;
/**
 * Description: Adds new Features to the standard Java HTMLDocument class.
 * 
 * @author Howard Kistler, Karsten Pawlik
 */
public class ExtendedHTMLDocument extends HTMLDocument {
	
	private static LeanLogger log = new LeanLogger("ExtendedHTMLDocument.class");
	
	/**
	 * Constructs a new ExtendedHTMLDocument using the given values.
	 */
	public ExtendedHTMLDocument() {
		log.debug("new ExtendedHTMLDocument created.");
	}
	
	/**
	 * Constructs a new ExtendedHTMLDocument using the given values.
	 * @param content document content
	 * @param styles document css-styles
	 */
	public ExtendedHTMLDocument(AbstractDocument.Content content, StyleSheet styles) {
		super(content, styles);
	}
	
	/**
	 * Constructs a new ExtendedHTMLDocument using the given values.
	 * @param styles document css-styles.
	 */
	public ExtendedHTMLDocument(StyleSheet styles) {
		super(styles);
	}
	
	/**
	 * Überschreibt die Attribute des Elements.
	 * @param element Element bei dem die Attribute geändert werden sollen
	 * @param attributes AttributeSet mit den neuen Attributen
	 * @param tag Angabe was für ein Tag das Element ist
	 */
	public void replaceAttributes(Element element, AttributeSet attributes, HTML.Tag tag) {
		if( (element != null) && (attributes != null)) {
			try {
				writeLock();
				int start = element.getStartOffset();

				DefaultDocumentEvent changes = new DefaultDocumentEvent(start,
				element.getEndOffset() - start, DocumentEvent.EventType.CHANGE);

				AttributeSet sCopy = attributes.copyAttributes();
				changes.addEdit(new AttributeUndoableEdit(element, sCopy, false));

				MutableAttributeSet attr = (MutableAttributeSet) element.getAttributes();
				Enumeration aNames = attr.getAttributeNames();
				Object value;
				Object aName;
				while (aNames.hasMoreElements()) {
					aName = aNames.nextElement();
					value = attr.getAttribute(aName);
					if(value != null && !value.toString().equalsIgnoreCase(tag.toString())) {
						attr.removeAttribute(aName);
					}
				}
				attr.addAttributes(attributes);
				changes.end();

				fireChangedUpdate(changes);
				fireUndoableEditUpdate(new UndoableEditEvent(this, changes));
			} finally {
				writeUnlock();
			}
		}
	}
	
	
	/**
	 * removes the given element between index and index+count.
	 * @param element element to delete
	 * @param index text index
	 * @param count character count
	 * @throws BadLocationException if thrown if index or index+count does not exist.
	 */
	public void removeElements(Element element, int index, int count) throws BadLocationException {
		writeLock();
		int start = element.getElement(index).getStartOffset();
		int end = element.getElement(index + count - 1).getEndOffset();
		try {
			Element[] removed = new Element[count];
			Element[] added = new Element[0];
	
			for (int counter = 0; counter < count; counter++) {
				removed[counter] = element.getElement(counter + index);
			}
	
			DefaultDocumentEvent dde = new DefaultDocumentEvent(start, end - start, DocumentEvent.EventType.REMOVE);
			((AbstractDocument.BranchElement)element).replace(index, removed.length,added);
			dde.addEdit(new ElementEdit(element, index, removed, added));
			UndoableEdit u = getContent().remove(start, end - start);
	
			if(u != null) {
				dde.addEdit(u);
			}
	
			postRemoveUpdate(dde);
			dde.end();
			fireRemoveUpdate(dde);
	
			if(u != null) {
				fireUndoableEditUpdate(new UndoableEditEvent(this, dde));
			}
		} finally {
			writeUnlock();
		}
	}
}