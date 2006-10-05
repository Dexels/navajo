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


	
    protected static Date getUrlDate(URL u) {
        InputStream os = null;
        try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
//        	System.err.println(uc.getHeaderFields());
        	uc.setRequestMethod("HEAD");
            Date d = new Date(uc.getLastModified());
//			flushConnection(uc);
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

    protected static Date getUrlTime(URL u) {
        InputStream os = null;
        try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
//        	System.err.println(uc.getHeaderFields());
        	uc.setRequestMethod("HEAD");
            Date d = new Date(uc.getDate());
//			flushConnection(uc);
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
	
    protected static int getUrlLength(URL u) {
        InputStream os = null;
        try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
//        	System.err.println(uc.getHeaderFields());
        	uc.setRequestMethod("HEAD");
//        	System.err.println(uc.getHeaderFields());
            int d = uc.getContentLength();
//			flushConnection(uc);
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
    protected static String getUrlType(URL u) {
		InputStream os = null;
		try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
//        	System.err.println(uc.getHeaderFields());
        	uc.setRequestMethod("HEAD");
			String type = uc.getContentType();
//			flushConnection(openConnection);

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

//    protected static void flushConnection(URLConnection openConnection)
//			 {
//		InputStream iss;
//		try {
//			iss = openConnection.getInputStream();
//			copyResource(new OutputStream(){
//				public void write(int b) throws IOException {
//					// do absolutely nothing
//				}}, iss);
//			iss.close();
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	}

//    protected static final void copyResource(OutputStream out, InputStream in)
//			throws IOException {
//		BufferedInputStream bin = new BufferedInputStream(in);
//		BufferedOutputStream bout = new BufferedOutputStream(out);
//		byte[] buffer = new byte[1024];
//		int read;
//		while ((read = bin.read(buffer)) > -1) {
//			bout.write(buffer, 0, read);
//		}
//		bin.close();
//		bout.flush();
//		// bout.close();
//	}

}
