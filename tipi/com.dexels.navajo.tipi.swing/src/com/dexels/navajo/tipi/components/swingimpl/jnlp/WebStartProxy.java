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

import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.internal.HttpResourceLoader;
import com.dexels.navajo.tipi.internal.TipiResourceLoader;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;
import com.dexels.navajo.tipi.internal.swing.cache.impl.CachedHttpJnlpResourceLoader;

public class WebStartProxy {

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
			e.printStackTrace();
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
				System.err
						.println("Using cache on file-based webstart. Not all that efficient.");
			}
			if (useCache) {
				try {

					return new CachedHttpJnlpResourceLoader(relativePath,
							codeURL, manager);
				} catch (javax.jnlp.UnavailableServiceException e) {
					System.err
							.println("Cached HTTP/JNLP cacheloader failed. Returning uncached loader.");
					return new HttpResourceLoader(codeURL.toString());
				}
			} else {
				return new HttpResourceLoader(codeURL.toString());

			}
		} catch (javax.jnlp.UnavailableServiceException e) {

			e.printStackTrace();
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
					System.err.println("Parsing muffin: " + muffins[i]);

					FileContents fc = ps.get(new URL(base, muffins[i]));
					System.err.println("Entry: " + muffins[i] + " readable: "
							+ fc.canRead() + " writable: " + fc.canWrite()
							+ " size: " + fc.getLength() + " maxsize: "
							+ fc.getMaxLength());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnavailableServiceException e) {
			e.printStackTrace();
		}
	}

	public static boolean hasJnlpContext() {
		try {
			javax.jnlp.BasicService bs = (javax.jnlp.BasicService) javax.jnlp.ServiceManager
					.lookup("javax.jnlp.BasicService");
			return bs != null;
		} catch (Exception e) {
//			e.printStackTrace();
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
				e.printStackTrace();
			} catch (IOException e) {
				// System.err.println("No arguments. Ok. ");
			}
		} catch (UnavailableServiceException e) {
			// System.err.println("No jnlp detected");
		}
	}
}
