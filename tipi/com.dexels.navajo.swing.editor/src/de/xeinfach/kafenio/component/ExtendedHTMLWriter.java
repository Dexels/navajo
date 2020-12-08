/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
