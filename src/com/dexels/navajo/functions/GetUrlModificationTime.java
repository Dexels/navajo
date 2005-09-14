package com.dexels.navajo.functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetUrlModificationTime extends FunctionInterface {

	public String remarks() {
		return "Check the modification date of an url.";
	}

	public String usage() {		
		return "Check the modification date of an url.";
	}

	public Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 1)
            throw new TMLExpressionException("GetUrlModificationTime(String) expected");
        Object a = this.getOperands().get(0);
        if (!(a instanceof String))
            throw new TMLExpressionException("GetUrlModificationTime(String) expected");

        URL u;
		try {
			u = new URL((String)a);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new TMLExpressionException("CheckUrl: bad url: "+a);
		}

		return getUrlDate(u);
	}
	
    private Date getUrlDate(URL u) {
        InputStream os = null;
        try {
        	URLConnection uc = u.openConnection();
//        	System.err.println(uc.getHeaderFields());
            Date d = new Date(uc.getLastModified());
           return d;
        } catch (IOException e) {
           return null;
        } finally {
            if (os!=null) {
               try {
                   os.close();
               } catch (IOException e) {
                     e.printStackTrace();
               }
           }
        }
    }

}
