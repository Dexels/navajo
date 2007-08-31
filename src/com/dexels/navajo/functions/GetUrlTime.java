package com.dexels.navajo.functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class GetUrlTime extends GetUrlBase {

	public String remarks() {
		return "Check the date of an url. This is not the modification date, this is the time the header was sent. Effectively the current time of the webserver.";
	}

	public String usage() {		
		return "Check the  date of a webserver.";
	}

	public final Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 1)
            throw new TMLExpressionException("GetUrlTime(String) expected");
        Object a = this.getOperands().get(0);
        if (a==null) {
			return null;
		}
        if (!(a instanceof String))
            throw new TMLExpressionException("GetUrlTime(String) expected");

        URL u;
		try {
			u = new URL((String)a);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new TMLExpressionException("CheckUrl: bad url: "+a);
		}

		return getUrlTime(u);
	}
	

}
