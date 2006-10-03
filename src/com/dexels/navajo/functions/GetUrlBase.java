package com.dexels.navajo.functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/*
 * Some utility functions for URLs
 */

public abstract class GetUrlBase extends FunctionInterface {

	
    protected Date getUrlDate(URL u) {
        InputStream os = null;
        try {
        	URLConnection uc = u.openConnection();
//        	System.err.println(uc.getHeaderFields());
            Date d = new Date(uc.getLastModified());
			flushConnection(uc);
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

	
    protected int getUrlLength(URL u) {
        InputStream os = null;
        try {
        	URLConnection uc = u.openConnection();
//        	System.err.println(uc.getHeaderFields());
            int d = uc.getContentLength();
			flushConnection(uc);
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
    protected String getUrlType(URL u) {
		InputStream os = null;
		try {
			URLConnection openConnection = u.openConnection();
			String type = openConnection.getContentType();
			flushConnection(openConnection);

			return type;
		} catch (IOException e) {
			return null;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

    protected void flushConnection(URLConnection openConnection)
			throws IOException {
		InputStream iss = openConnection.getInputStream();
		copyResource(new OutputStream(){
			public void write(int b) throws IOException {
				// do absolutely nothing
			}}, iss);
		iss.close();
	}

    protected static final void copyResource(OutputStream out, InputStream in)
			throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		// bout.close();
	}

}
