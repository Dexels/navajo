/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.article;

import java.io.InputStream;

/**
 * This exception should be thrown when the output cannot be returned
 * in JSON format. It should be caught and result in a different type
 * of output according to its mimetype.
 */
public class NoJSONOutputException extends Exception {

	private static final long serialVersionUID = 476969437269688913L;
	private final transient InputStream stream;
	private final String mimeType;

	public NoJSONOutputException(String mimetype, InputStream is) {
		this.stream = is;
		this.mimeType = mimetype;
	}

	public InputStream getStream() {
		return stream;
	}

	public String getMimeType() {
		return mimeType;
	}
}
