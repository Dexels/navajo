/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.core.impl.dom;

import java.io.Serializable;
import java.util.Vector;

import org.akrogen.tkui.css.core.exceptions.DOMExceptionImpl;
import org.w3c.css.sac.SACMediaList;
import org.w3c.dom.DOMException;
import org.w3c.dom.stylesheets.MediaList;

/**
 * w3c {@link MediaList} implementation built with {@link SACMediaList}.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class MediaListImpl implements MediaList, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7588400648695694782L;
	private Vector _media = new Vector();

	public MediaListImpl(SACMediaList mediaList) {
		for (int i = 0; i < mediaList.getLength(); i++) {
			_media.addElement(mediaList.item(i));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.stylesheets.MediaList#getMediaText()
	 */
	public String getMediaText() {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < _media.size(); i++) {
			sb.append(_media.elementAt(i).toString());
			if (i < _media.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.stylesheets.MediaList#setMediaText(java.lang.String)
	 */
	public void setMediaText(String mediaText) throws DOMException {
		/*
		 * try { StringReader sr = new StringReader( mediaText ); CSS2Parser
		 * parser = new CSS2Parser( sr ); ASTMediaList ml = parser.mediaList();
		 * _media = ml._media; } catch( ParseException e ) { throw new
		 * DOMExceptionImpl( DOMException.SYNTAX_ERR,
		 * DOMExceptionImpl.SYNTAX_ERROR, e.getMessage() ); }
		 */
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.stylesheets.MediaList#getLength()
	 */
	public int getLength() {
		return _media.size();
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.stylesheets.MediaList#item(int)
	 */
	public String item(int index) {
		return (index < _media.size()) ? (String) _media.elementAt(index)
				: null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.stylesheets.MediaList#deleteMedium(java.lang.String)
	 */
	public void deleteMedium(String oldMedium) throws DOMException {
		for (int i = 0; i < _media.size(); i++) {
			String str = (String) _media.elementAt(i);
			if (str.equalsIgnoreCase(oldMedium)) {
				_media.removeElementAt(i);
				return;
			}
		}
		throw new DOMExceptionImpl(DOMException.NOT_FOUND_ERR,
				DOMExceptionImpl.NOT_FOUND);
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.stylesheets.MediaList#appendMedium(java.lang.String)
	 */
	public void appendMedium(String newMedium) throws DOMException {
		_media.addElement(newMedium);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getMediaText();
	}

}
