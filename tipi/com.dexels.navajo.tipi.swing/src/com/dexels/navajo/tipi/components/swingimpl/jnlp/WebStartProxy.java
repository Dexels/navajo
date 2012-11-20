package com.dexels.navajo.tipi.components.swingimpl.jnlp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.PropertyResourceBundle;

import javax.jnlp.BasicService;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.internal.HttpResourceLoader;
import com.dexels.navajo.tipi.internal.TipiResourceLoader;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;
import com.dexels.navajo.tipi.internal.swing.cache.impl.CachedHttpJnlpResourceLoader;

public class WebStartProxy {
	
	private final static Logger logger = LoggerFactory
			.getLogger(WebStartProxy.class);
	public static void appendJnlpCodeBase(SwingTipiContext myContext,
			String loaderType) {
		try {
			javax.jnlp.BasicService bs = (javax.jnlp.BasicService) javax.jnlp.ServiceManager
					.lookup("javax.jnlp.BasicService");

			URL tipiCodeBase = new URL(bs.getCodeBase(), loaderType);
			URL resourceCodeBase = new URL(bs.getCodeBase(), "resource");

			myContext.setTipiResourceLoader(tipiCodeBase.toString());
			myContext.setGenericResourceLoader(resourceCodeBase.toString());
		} catch (javax.jnlp.UnavailableServiceException e) {
		} catch (MalformedURLException e) {
			logger.error("Error detected",e);
		}

	}

	public static TipiResourceLoader createDefaultWebstartLoader(
			String relativePath, boolean useCache, CookieManager manager)
			throws IOException {
		javax.jnlp.BasicService bs;
		try {
			bs = (javax.jnlp.BasicService) javax.jnlp.ServiceManager
					.lookup("javax.jnlp.BasicService");
			URL codeURL = new URL(bs.getCodeBase(), relativePath);
			if (codeURL.getProtocol().equals("file") && useCache) {
				logger.info("Using cache on file-based webstart. Not all that efficient.");
			}
			if (useCache) {
				try {

					return new CachedHttpJnlpResourceLoader(relativePath,
							codeURL, manager);
				} catch (javax.jnlp.UnavailableServiceException e) {
					logger.info("Cached HTTP/JNLP cacheloader failed. Returning uncached loader.");
					return new HttpResourceLoader(codeURL.toString());
				}
			} else {
				return new HttpResourceLoader(codeURL.toString());

			}
		} catch (javax.jnlp.UnavailableServiceException e) {

			logger.error("Error detected",e);
			throw new IOException("Error loading basic webstartservice: ", e);
		}

	}

	public static void injectJnlpCache() {
		try {
			BasicService bs = (BasicService) ServiceManager
					.lookup("javax.jnlp.BasicService");
			PersistenceService ps = (PersistenceService) ServiceManager
					.lookup("javax.jnlp.PersistenceService");
			try {
				URL base = bs.getCodeBase();
				String[] muffins = ps.getNames(base);
				for (int i = 0; i < muffins.length; i++) {
					logger.debug("Parsing muffin: " + muffins[i]);

					FileContents fc = ps.get(new URL(base, muffins[i]));
					logger.debug("Entry: " + muffins[i] + " readable: "
							+ fc.canRead() + " writable: " + fc.canWrite()
							+ " size: " + fc.getLength() + " maxsize: "
							+ fc.getMaxLength());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				logger.error("Error detected",e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Error detected",e);
			}
		} catch (UnavailableServiceException e) {
			logger.error("Error detected",e);
		}
	}

	public static boolean hasJnlpContext() {
		try {
			javax.jnlp.BasicService bs = (javax.jnlp.BasicService) javax.jnlp.ServiceManager
					.lookup("javax.jnlp.BasicService");
			return bs != null;
		} catch (Exception e) {
//			logger.error("Error detected",e);
			return false;
		}
	}

	public static void appendJnlpProperties(Map<String, String> properties) {
		try {
			BasicService bs = (BasicService) ServiceManager
					.lookup("javax.jnlp.BasicService");
			try {
				URL base = bs.getCodeBase();
				URL args = new URL(base, "arguments.properties");
				InputStream is = args.openStream();
				PropertyResourceBundle pr = new PropertyResourceBundle(is);
				is.close();
				for (String key : pr.keySet()) {
					properties.put(key, pr.getString(key));
				}
			} catch (MalformedURLException e) {
				logger.error("Error detected",e);
			} catch (IOException e) {
				// logger.debug("No arguments. Ok. ");
			}
		} catch (UnavailableServiceException e) {
			// logger.debug("No jnlp detected");
		}
	}
}
