package com.dexels.navajo.functions;

import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.parser.TMLExpressionException;

public class GetUrlMimeType extends GetUrlBase {

	@Override
	public String remarks() {
		return "Check the mime type of an url.";
	}

	@Override
	public String usage() {
		return "Check the mime type of an url.";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		// input (ArrayList, Object).
		if (this.getOperands().size() != 1)
			throw new TMLExpressionException("GetUrlMimeType(String) expected");
		Object a = this.getOperands().get(0);
		if (a == null) {
			return null;
		}
		if (!(a instanceof String))
			throw new TMLExpressionException("GetUrlMimeType(String) expected");

		URL u;
		try {
			u = new URL((String) a);
		} catch (MalformedURLException e) {
			throw new TMLExpressionException("CheckUrl: bad url: " + a);
		}

		return getUrlType(u);
	}


}
