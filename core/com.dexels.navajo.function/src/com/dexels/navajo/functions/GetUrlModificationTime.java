package com.dexels.navajo.functions;

import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.parser.TMLExpressionException;

public class GetUrlModificationTime extends GetUrlBase {

	@Override
	public String remarks() {
		return "Check the modification date of an url.";
	}

	@Override
	public String usage() {		
		return "Check the modification date of an url.";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 1)
            throw new TMLExpressionException("GetUrlModificationTime(String) expected");
        Object a = this.getOperands().get(0);
        if (a==null) {
			return null;
		}
        if (!(a instanceof String))
            throw new TMLExpressionException("GetUrlModificationTime(String) expected");

        URL u;
		try {
			u = new URL((String)a);
		} catch (MalformedURLException e) {
			throw new TMLExpressionException("CheckUrl: bad url: "+a);
		}

		return getUrlDate(u);
	}
	
}
