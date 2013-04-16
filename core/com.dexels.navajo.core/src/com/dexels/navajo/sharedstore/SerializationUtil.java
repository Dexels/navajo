package com.dexels.navajo.sharedstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class SerializationUtil {

	private final static Logger logger = LoggerFactory.getLogger(SerializationUtil.class);
	private final static String SHAREDSTORE_PARENT = "clusternavajoobjects";
	
	public static boolean existsNavajo(String name) {
		SharedStoreInterface ssi = SharedStoreFactory.getInstance();
		if ( ssi == null || name == null ) {
			return false;
		}
		return ssi.exists(SHAREDSTORE_PARENT, name);
	}
	
	public static void removeNavajo(String name) {
		logger.warn("In removeNavajo(" + name + ")");
		SharedStoreInterface ssi = SharedStoreFactory.getInstance();
		if ( name == null || ssi == null ) {
			return;
		}
		try {
			ssi.remove(SHAREDSTORE_PARENT, name);
		} catch (Throwable t) { }
	}
	
	public static Navajo deserializeNavajo(String name) {
		SharedStoreInterface ssi = SharedStoreFactory.getInstance();
		if ( name == null || !ssi.exists(SHAREDSTORE_PARENT, name) ) {
			return null;
		}
		InputStream is = null;
		try {
			is = ssi.getStream(SHAREDSTORE_PARENT, name);
			Navajo n = NavajoFactory.getInstance().createNavajo(is);
			return n;
		} catch (SharedStoreException e) {
			logger.error("Could not deserialize Navajo for: " + name, e);
		} finally {
			if ( is != null ) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("Could not close deserialization request...", e);
				}
			}
			ssi.remove(SHAREDSTORE_PARENT, name);
		}
		return null;
	}
	
	public static String serializeNavajo(Navajo n, String name) {
		SharedStoreInterface ssi = SharedStoreFactory.getInstance();
		if ( n == null ) {
			return null;
		}
		OutputStream os = null;
		try {
			os = ssi.getOutputStream(SHAREDSTORE_PARENT, name, false);
			n.write(os);
			return name;
		} catch (Throwable e) {
			logger.error("Could not serialize Navajo for: " + name, e);
		} finally {
			if ( os != null ) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error("Could not close serialization request...", e);
				}
			}
		}
		return null;
	}
	
	public static void removeAllNavajos() {
		logger.warn("In removeAllNavajos()");
		Thread.dumpStack();
		SharedStoreInterface ssi = SharedStoreFactory.getInstance();
		ssi.removeAll(SerializationUtil.SHAREDSTORE_PARENT);
	}
}
