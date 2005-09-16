package com.dexels.navajo.functions;

import java.io.*;
import java.net.*;
import java.util.*;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class CheckUrl extends FunctionInterface {

	public String remarks() {
		return "This function will check whether it returns a valid stream. It will throw an exception when the the URL is malformed, it returns false when the string is null";
	}

	public String usage() {
		
		return "Used to check if an URL is responding.";
	}

	public Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 1)
            throw new TMLExpressionException("CheckUrl(String) expected");
        Object a = this.getOperands().get(0);
        if (a==null) {
			return new Boolean(false);
		}
        if (!(a instanceof String))
            throw new TMLExpressionException("CheckUrl(String) expected");

        URL u;
		try {
			u = new URL((String)a);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new TMLExpressionException("CheckUrl: bad url: "+a);
		}

        return new Boolean(check(u));
	}
	
    public boolean check(URL u) {
        InputStream os = null;
        try {
            os = u.openConnection().getInputStream();
           return true;
        } catch (IOException e) {
           return false;
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
