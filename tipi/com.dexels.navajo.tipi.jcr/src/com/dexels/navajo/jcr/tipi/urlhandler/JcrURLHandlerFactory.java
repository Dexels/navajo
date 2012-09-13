package com.dexels.navajo.jcr.tipi.urlhandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.jcr.tipi.JcrTipiComponent;

public class JcrURLHandlerFactory implements URLStreamHandlerFactory {

	private static Session session;

	private final JcrTipiComponent jcr;
	
	private final static Logger logger = LoggerFactory.getLogger(JcrURLHandlerFactory.class);
	
	public JcrURLHandlerFactory(JcrTipiComponent jcr) {
		this.jcr = jcr;
	}

	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		logger.info("Getting handler for protocol: {}",protocol);
		if("jcr".equals(protocol)) {
			return new JcrStreamHandler(jcr.getSession());
		}
		return null;
	}
	
	public static URL createURL(String protocol,String host, int port, String file) throws MalformedURLException {
		URL uu = new URL(protocol,host,port,file,new JcrStreamHandler(session));
		return uu;
	}

	public static void instantiate(Session session) {
		JcrURLHandlerFactory.session = session;
//		return null;
	}

}
