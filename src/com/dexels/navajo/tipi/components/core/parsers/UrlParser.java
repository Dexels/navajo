package com.dexels.navajo.tipi.components.core.parsers;

import java.net.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class UrlParser extends TipiTypeParser {
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		// System.err.println("Parsing url: "+expression);
		return getUrl(expression);
	}

	private URL getUrl(String path) {
		try {
			// int i = path.indexOf(":");
			// String urlPath = path.substring(i + 2);
			return new URL(path);
		} catch (MalformedURLException ex) {
			throw new IllegalArgumentException("supplied url not valid for: " + path);
		}
	}

	public String toString(Object o, TipiComponent source) {
		URL u = (URL) o;
		return u.toString();
	}
}
