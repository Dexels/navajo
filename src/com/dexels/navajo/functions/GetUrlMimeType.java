package com.dexels.navajo.functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetUrlMimeType extends FunctionInterface {

	public String remarks() {
		return "Check the mime type of an url.";
	}

	public String usage() {		
		return "Check the mime type of an url.";
	}

	public Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 1)
            throw new TMLExpressionException("GetUrlMimeType(String) expected");
        Object a = this.getOperands().get(0);
        if (a==null) {
			return null;
		}
        if (!(a instanceof String))
            throw new TMLExpressionException("GetUrlMimeType(String) expected");

        URL u;
		try {
			u = new URL((String)a);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new TMLExpressionException("CheckUrl: bad url: "+a);
		}

		return getUrlType(u);
	}
	
    private String getUrlType(URL u) {
        InputStream os = null;
        try {
        	URLConnection uc = u.openConnection();
            String type = uc.getContentType();
            os = uc.getInputStream();
           return type;
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
