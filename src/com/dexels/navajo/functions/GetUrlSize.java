package com.dexels.navajo.functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetUrlSize extends FunctionInterface {

	public String remarks() {
		return "Get the length of URL content";
	}

	public String usage() {		
		return "Get the length of URL content";
	}

	public Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 1)
            throw new TMLExpressionException("GetUrlSize(String) expected");
        Object a = this.getOperands().get(0);
        if (a==null) {
			return null;
		}
        if (!(a instanceof String))
            throw new TMLExpressionException("GetUrlSize(String) expected");

        URL u;
		try {
			u = new URL((String)a);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new TMLExpressionException("GetUrlSize: bad url: "+a);
		}

		return new Integer(getUrlLength(u));
	}
	
    private int getUrlLength(URL u) {
        InputStream os = null;
        try {
        	URLConnection uc = u.openConnection();
        	os = uc.getInputStream();
//        	System.err.println(uc.getHeaderFields());
            int d = uc.getContentLength();
           return d;
        } catch (IOException e) {
           return 0;
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
