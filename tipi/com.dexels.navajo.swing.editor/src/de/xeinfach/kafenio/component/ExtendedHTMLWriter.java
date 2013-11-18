package de.xeinfach.kafenio.component;

import java.io.Writer;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLWriter;

/**
 * Special version that does not indent
 * @author Dexels
 *
 */
public class ExtendedHTMLWriter extends HTMLWriter {

    public ExtendedHTMLWriter(Writer w, HTMLDocument doc) {
    	this(w, doc, 0, doc.getLength());
    }
    
	public ExtendedHTMLWriter(Writer w, HTMLDocument doc, int pos, int len) {
		super(w, doc, pos, len);
		setIndentSpace(0);
		setLineSeparator("");
	}
	
}
