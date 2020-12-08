/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.terminal.StreamResource.StreamSource;

public class URLInputStreamSource implements StreamSource {

	private static final long serialVersionUID = -3296924859018619919L;
	private final URL url;
	
	private final static Logger logger = LoggerFactory
			.getLogger(URLInputStreamSource.class);
	
	public URLInputStreamSource(URL url) {
		this.url = url;
	}
	
	@Override
	public InputStream getStream() {
		try {
			return url.openStream();
		} catch (IOException e) {
			logger.error("Error: ",e);
			return null;
		}
	}

}
