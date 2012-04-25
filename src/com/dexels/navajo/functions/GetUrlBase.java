package com.dexels.navajo.functions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.parser.FunctionInterface;

/*
 * Some utility functions for URLs
 */

public abstract class GetUrlBase extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(GetUrlBase.class);
	
    protected static Date getUrlDate(URL u) {
        InputStream os = null;
        try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
        	uc.setConnectTimeout(1000);
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
            	   logger.error("Error: ", e);
               }
           }
        }
    }

    protected static Date getUrlTime(URL u) {
        InputStream os = null;
        try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
        	uc.setConnectTimeout(1000);
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
            	   logger.error("Error: ", e);
               }
           }
        }
    }
	
    protected static int getUrlLength(URL u) {
        InputStream os = null;
        try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
        	uc.setConnectTimeout(1000);
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
            	   logger.info("closing problem",e);
               }
           }
        }
    }
    protected static String getUrlType(URL u) {
		InputStream os = null;
		try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
        	uc.setConnectTimeout(1000);
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
	        	   logger.info("closing problem",e);
				}
			}
		}
	}


}
