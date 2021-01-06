/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.saximpl.qdxml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.Deque;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quick and Dirty xml parser. This parser is, like the SAX parser, an event
 * based parser, but with much less functionality.
 */
public class QDParser {

	public static final int PUSHBACK_SIZE = 2000;

	private static final Logger logger = LoggerFactory
			.getLogger(QDParser.class);

	public QDParser() {
	}

	private static final int TEXT = 1;
	private static final int ENTITY = 2;
	private static final int OPEN_TAG = 3;
	private static final int CLOSE_TAG = 4;
	private static final int START_TAG = 5;
	private static final int ATTRIBUTE_LVALUE = 6;
	private static final int ATTRIBUTE_EQUAL = 9;
	private static final int ATTRIBUTE_RVALUE = 10;
	private static final int QUOTE = 7;
	private static final int IN_TAG = 8;
	private static final int SINGLE_TAG = 12;
	private static final int COMMENT = 13;
	private static final int DONE = 11; 
	private static final int DOCTYPE = 14;
	private static final int PRE = 15;
	private static final int CDATA = 16;

	int line = 1;
	int col = 0;
	
	private int popMode(Deque<Integer> st) {
		if (!st.isEmpty())
			return st.pop().intValue();
		else
			return PRE;
	}
	
	public void parse(DocHandler doc, Reader re) throws Exception {
		PushbackReader r = new PushbackReader(new BufferedReader(re), PUSHBACK_SIZE);
		StringBuilder attributeBuffer = new StringBuilder(300);
		int depth = 0;
		int mode = PRE;
		int c = 0;
		int quotec = '"';
		depth = 0;
		StringBuilder sb = new StringBuilder();
		StringBuilder etag = new StringBuilder();
		String tagName = null;
		String lvalue = null;
		String rvalue = null;
		Map<String,String> attrs = null;
		Deque<Integer> st = new LinkedList<>();
		doc.startDocument();
		line = 1;
		col = 0;
		boolean eol = false;
		while ((c = r.read()) != -1) {

			// We need to map \r, \r\n, and \n to \n
			// See XML spec section 2.11
			if (c == '\n' && eol) {
				eol = false;
				continue;
			} else if (eol) {
				eol = false;
			} else if (c == '\n') {
				line++;
				col = 0;
			} else if (c == '\r') {
				eol = true;
				c = '\n';
				line++;
				col = 0;
			} else {
				col++;
			}

			if (mode == DONE) {
				doc.endDocument();
				return;

				// We are between tags collecting text.
			} else if (mode == TEXT) {
				if (c == '<') {
					st.push(Integer.valueOf(mode));
					mode = START_TAG;
				} else if (c == '&') {
					st.push(Integer.valueOf(mode));
					mode = ENTITY;
					etag.setLength(0);
				} else
					sb.append((char) c);

				// we are processing a closing tag: e.g. </foo>
			} else if (mode == CLOSE_TAG) {
				if (c == '>') {
					mode = popMode(st);
					tagName = sb.toString();
					sb.setLength(0);
					depth--;
					if (depth == 0)
						mode = DONE;
					doc.endElement(tagName);
				} else {
					sb.append((char) c);
				}

				// we are processing CDATA
			} else if (mode == CDATA) {
				if (c == '>' && sb.toString().endsWith("]]")) {
					sb.setLength(sb.length() - 2);
					doc.addCData(sb.toString());
					sb.setLength(0);
					mode = popMode(st);
				} else
					sb.append((char) c);

				// we are processing a comment. We are inside
				// the <!-- .... --> looking for the -->.
			} else if (mode == COMMENT) {
				if (c == '>' && sb.toString().endsWith("--")) {
					doc.addComment(sb.toString());
					sb.setLength(0);
					mode = popMode(st);
				} else
					sb.append((char) c);

				// We are outside the root tag element
			} else if (mode == PRE) {
				if (c == '<') {
					mode = START_TAG;
				}

				// We are inside one of these <? ... ?>
				// or one of these <!DOCTYPE ... >
			} else if (mode == DOCTYPE) {
				if (c == '>') {
					mode = popMode(st);
					if (mode == TEXT)
						mode = PRE;
				}

				// we have just seen a < and
				// are wondering what we are looking at
				// <foo>, </foo>, <!-- ... --->, etc.
			} else if (mode == START_TAG) {
				mode = popMode(st);
				if (c == '/') {
					st.push(Integer.valueOf(mode));
					mode = CLOSE_TAG;
				} else if (c == '?') {
					mode = DOCTYPE;
				} else {
					st.push(Integer.valueOf(mode));
					mode = OPEN_TAG;
					tagName = null;
					attrs = new HashMap<>();
					sb.append((char) c);
				}

				// we are processing an entity, e.g. &lt;, &#187;, etc.
			} else if (mode == ENTITY) {
				if (c == ';') {
					mode = popMode(st);
					String cent = etag.toString();
					etag.setLength(0);
					if (cent.equals("lt"))
						sb.append('<');
					else if (cent.equals("gt"))
						sb.append('>');
					else if (cent.equals("amp"))
						sb.append('&');
					else if (cent.equals("quot"))
						sb.append('"');
					else if (cent.equals("apos"))
						sb.append('\'');
					// Could parse hex entities if we wanted to
					else if (cent.length() > 0 && cent.charAt(0) == '#')
						sb.append((char) Integer.parseInt(cent.substring(1)));
					// Insert custom entity definitions here
					else
						exc("Unknown entity: &" + cent + ";");
				} else {
					etag.append((char) c);
				}

				// we have just seen something like this:
				// <foo a="b"/
				// and are looking for the final >.
			} else if (mode == SINGLE_TAG) {
				if (tagName == null)
					tagName = sb.toString();
				if (c != '>') {
					exc("Expected > for tag: <" + tagName + "/>. Got c='" + c + "'");
				}
				doc.startElement(tagName, attrs);
				doc.endElement(tagName);
				if (depth == 0) {
					doc.endDocument();
					return;
				}
				sb.setLength(0);
				attrs = new Hashtable<>();
				tagName = null;
				mode = popMode(st);

				// we are processing something
				// like this <foo ... >. It could
				// still be a <!-- ... --> or something.
			} else if (mode == OPEN_TAG) {
				if (c == '>') {
					if (tagName == null)
						tagName = sb.toString();
					sb.setLength(0);
					depth++;
					doc.startElement(tagName, attrs);
					tagName = null;
					attrs = new HashMap<>();
					mode = popMode(st);
					skipWhitespace(r);
					char ccc = nextChar(r);
					if (ccc != '<') {
						doc.text(r);
						r.read();
						popMode(st);
					}
				} else if (c == '/') {
					mode = SINGLE_TAG;
				} else if (c == '-' && sb.toString().equals("!-")) {
					mode = COMMENT;
				} else if (c == '[' && sb.toString().equals("![CDATA")) {
					mode = CDATA;
					sb.setLength(0);
				} else if (c == 'E' && sb.toString().equals("!DOCTYP")) {
					sb.setLength(0);
					mode = DOCTYPE;
				} else if (Character.isWhitespace((char) c)) {
					tagName = sb.toString();
					sb.setLength(0);
					mode = IN_TAG;
				} else {
					sb.append((char) c);
				}

				// We are processing the quoted right-hand side
				// of an element's attribute.
			} else if (mode == QUOTE) {
				if (c == quotec) {
					rvalue = sb.toString();
					sb.setLength(0);
					if (attrs==null) {
						throw new NullPointerException("The XML Compiler is in deep st#@$t");
					} else {
						attrs.put(lvalue, rvalue);
					}
					mode = IN_TAG;
					// See section the XML spec, section 3.3.3
					// on normalization processing.
				} else if (c == '&') {
					st.push(Integer.valueOf(mode));
					mode = ENTITY;
					etag.setLength(0);
				} else {
					sb.append((char) c);
				}

			} else if (mode == ATTRIBUTE_RVALUE) {
				if (c == '"' || c == '\'') {
					quotec = c;
					rvalue = doc.quoteStarted(quotec, r, lvalue, tagName,attributeBuffer);
					if (rvalue != null) {
						if (attrs==null) {
							throw new NullPointerException("Serious parsing problem!");
						} else {
							attrs.put(lvalue, rvalue);
						}
					}
					mode = IN_TAG;

				} else if (!Character.isWhitespace((char) c)) {
					exc("Error in attribute processing");
				}

			} else if (mode == ATTRIBUTE_LVALUE) {
				if (Character.isWhitespace((char) c)) {
					lvalue = sb.toString();
					sb.setLength(0);
					mode = ATTRIBUTE_EQUAL;
				} else if (c == '=') {
					lvalue = sb.toString();
					sb.setLength(0);
					mode = ATTRIBUTE_RVALUE;
				} else {
					sb.append((char) c);
				}

			} else if (mode == ATTRIBUTE_EQUAL) {
				if (c == '=') {
					mode = ATTRIBUTE_RVALUE;
				} else if (!Character.isWhitespace((char) c)) {
					exc("Error in attribute processing, got c='" + c + "'");
				}

			} else if (mode == IN_TAG) {
				if (c == '>') {
					mode = popMode(st);
					doc.startElement(tagName, attrs);
					depth++;
					tagName = null;
					attrs = new HashMap<>();
					skipWhitespace(r);
					if (nextChar(r) != '<') {
						doc.text(r);
					} 

				} else if (c == '/') {
					mode = SINGLE_TAG;
				} else if (!Character.isWhitespace((char) c)) {
					mode = ATTRIBUTE_LVALUE;
					sb.append((char) c);
				}
			}
		}
		if (mode == DONE)
			doc.endDocument();
		else
			exc("missing end tag");
	}

	private  char nextChar(PushbackReader r) throws IOException {
		int c = r.read();
		r.unread(c);
		return (char) c;
	}

	private  void skipWhitespace(PushbackReader r) throws IOException {
		while (Character.isWhitespace(nextChar(r))) {
			r.read();
		}
	}

	private void exc(String s) throws Exception {
		throw new Exception(s + " near line " + line + ", column " + col);
	}

}
