package com.dexels.navajo.jcr.tipi.urlhandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JcrStreamHandler extends URLStreamHandler {
	
	private final Session session;
	
	private final static Logger logger = LoggerFactory
			.getLogger(JcrStreamHandler.class);
	
	public JcrStreamHandler(Session session) {
		this.session = session;
	}

	@Override
	protected URLConnection openConnection(URL url) throws IOException {
		logger.info("Opening connection with URL: "+url);
		return new URLConnection(url) {
			
			@Override
			public void connect() throws IOException {
				logger.info("Connecting!");
				this.connected = true;
			}

			@Override
			public InputStream getInputStream() throws IOException {
				
				try {
					String file =  url.getFile();
					logger.info("Looking for: "+file);
					Node n = session.getNode(file);
					InputStream content = n.getNode("jcr:content").getProperty("jcr:data").getBinary().getStream();
					return content;
				} catch (PathNotFoundException e) {
					throw new IOException("Error resolving JCR URL: "+url.toString(),e);
				} catch (RepositoryException e) {
					throw new IOException("Error resolving JCR URL: "+url.toString(),e);
				}
				
			}

			
		};
	}

//	private InputStream readFile(Node node) throws RepositoryException {
//		if (node.hasProperty(Property.JCR_DATA)) {
//			Property data = node.getProperty(Property.JCR_DATA);
//			final Binary binary = data.getBinary();
//			return new FilterInputStream(binary.getStream()) {
//				@Override
//				public void close() throws IOException {
//					super.close();
//					binary.dispose();
//				}
//			};
//		} else if (node.hasNode(Node.JCR_CONTENT)) {
//			return readFile(node.getNode(Node.JCR_CONTENT));
//		} else {
//			throw new RepositoryException("Unable to read file node: "
//					+ node.getPath());
//		}
//	}
}
