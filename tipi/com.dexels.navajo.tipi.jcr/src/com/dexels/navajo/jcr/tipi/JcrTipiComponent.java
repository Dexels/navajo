package com.dexels.navajo.jcr.tipi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.jcr.tipi.impl.JcrNodeUtils;
import com.dexels.navajo.jcr.tipi.urlhandler.JcrURLHandlerFactory;
import com.dexels.navajo.tipi.internal.TipiResourceLoader;

public class JcrTipiComponent implements TipiResourceLoader{
	
	private Session session;
	
	private final static Logger logger = LoggerFactory
			.getLogger(JcrTipiComponent.class);
	
	public void activate() {
		logger.info("Activating tipi loader. ");
		JcrURLHandlerFactory.instantiate(session);
		logger.info("About to register command.");
		try {
			testJcrURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		Dictionary<String, Object> dict = new Hashtable<String, Object>();
//		dict.put(CommandProcessor.COMMAND_SCOPE, "shell");
//		dict.put(CommandProcessor.COMMAND_FUNCTION, new String[] {"sleep", "grep"});
//		context.registerService(name, service, dict);
	}

	private void testJcrURL() throws MalformedURLException, IOException {
		URL u = JcrURLHandlerFactory.createURL("jcr", "localhost", 0, "/SportlinkClub/tipi/accountWindow.xml");
		InputStream is = u.openStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyResource(baos, is);
		System.err.println("SIZE: "+baos.toByteArray().length);
		logger.info("Content: {}",new String(baos.toByteArray()));
		is.close();
	}
//
//	private void doSanityCheck() throws MalformedURLException, RepositoryException {
//			Node root = session.getRootNode();
//			Workspace sp = session.getWorkspace();
//			logger.info("Doing sanity check on JCR.");
//			URL u = new URL("jcr://"+sp.getName()+root.getName());
//			
//	}

	  private final void copyResource(OutputStream out, InputStream in){
		  BufferedInputStream bin = new BufferedInputStream(in);
		  BufferedOutputStream bout = new BufferedOutputStream(out);
		  byte[] buffer = new byte[1024];
		  int read = -1;
		  boolean ready = false;
		  while (!ready) {
			  try {
				  read = bin.read(buffer);
				  if ( read > -1 ) {
					  bout.write(buffer,0,read);
				  }
			  } catch (IOException e) {
			  }
			  if ( read <= -1) {
				  ready = true;
			  }
		  }
		  try {
			  bin.close();
			  bout.flush();
			  bout.close();
		  } catch (IOException e) {

		  }
	  }
	
	public void deactivate() {
		logger.info("Deactivating tipi loader. ");
		
	}
	
	public void setSession(Session r) {
		System.err.println("Setting repo: "+r);
		this.session = r;
	}

	public void clearSession(Session r) {
		logger.info("Clearing repo: "+r);
		this.session = null;
	}

	@Override
	public URL getResourceURL(String location) throws IOException {
		try {
			Node n = session.getRootNode().getNode(location);
			URL u = JcrURLHandlerFactory.createURL("jcr", "localhost", 0, n.getPath());
			logger.info("Created url for nodepath: "+n.getPath()+" : "+u.toString());
			return u;
		} catch (PathNotFoundException e) {
			throw new IOException("Getting resource " + location+" failed",e);
		} catch (RepositoryException e) {
			throw new IOException("Getting resource " + location+" failed",e);
		}
	}

	@Override
	public InputStream getResourceStream(String location) throws IOException {
		try {
			Node n = session.getRootNode().getNode(location);
			return JcrNodeUtils.readFile(n);
		} catch (PathNotFoundException e) {
			throw new IOException("Getting resource " + location+" failed",e);
		} catch (RepositoryException e) {
			throw new IOException("Getting resource " + location+" failed",e);
		}
	}

	@Override
	public OutputStream writeResource(String resourceName) throws IOException {
		return null;
	}

	@Override
	public List<File> getAllResources() throws IOException {
		return null;
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public void flushCache() throws IOException {
		
	}



	public Session getSession() {
		return session;
	}
}
