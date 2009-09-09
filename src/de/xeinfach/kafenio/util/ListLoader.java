/*
 * ListLoader.java
 * Copyright (C) 2000-03 Howard Kistler
 * Copyright (C) 2004 Maxym Mykhalchuk
 * part of Kafenio project http://kafenio.org
 */
package de.xeinfach.kafenio.util;

import de.xeinfach.kafenio.KafenioPanelConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a static utility class used to load a list of images or links or whatever from the server.
 * Used in Kafenio Applet for Image-from-Server and HyperLink insertion
 * <p>
 * The Servlet or CGI script should return a list of lines:
 * <ul>
 * <li>The first one is a prefix (or an empty line meaning there's no prefix)
 * <li>The following lines should have a following form: [name/description;;]partial_url
 * </ul>
 * <p>
 * Configuration parameter SERVLETMODE controls how this class reads the lines:
 * <ul>
 * <li>given the parameter value "java" it is done via an ObjectStream
 * <li>and given "cgi" - interating an InputStream.readLine()
 * </ul>
 * <p>
 * Loaded info is parsed into the two lists:
 * <ul>
 * <li>The list of names or in case there're no names - the list of partial URLs
 * <li>The list of full URLs with prefix prepended
 * </ul>
 * The class handles exceptions gracefully, returning empty lists.
 *
 * @author Howard Kistler, Maxym Mykhalchuk
 */
public final class ListLoader {
	
	public static final String NAME_URL_SEPARATOR = ";;";
	
	private static LeanLogger log = new LeanLogger("ListLoader.class");
	
	private ListLoader() {}

	/** Loads the lists
	 * @param config Kafenio configuration
	 * @param params Parameters to pass to the Servlet/CGI script
	 * @param names The returned list of names
	 * @param urls The returned list of Urls
	 */
	private static void loadLists(KafenioPanelConfiguration config, String params, List names, List urls) {
		names.clear();
		urls.clear();
		String servletUrl = config.getServletUrl();
		String servletMode = config.getServletMode();
		try {
			List listNames = new ArrayList(), listUrls = new ArrayList();
			String[] images = null, files = null;
			
			// check if servlet url already contains parameters...
			String joiner = "?";
			if (servletUrl.indexOf("?") >= 0)
				joiner = "&";
			
			URL theServlet = new URL(servletUrl + joiner + params);
			URLConnection conn = theServlet.openConnection();

			String[] strings;
			if (servletMode.equalsIgnoreCase("java")) {
				ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
				strings = (String[]) in.readObject();
				in.close();
			}
			else {
				BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8") );
				String inputLine;
				List stringsList = new ArrayList();
				while ((inputLine = in.readLine()) != null)
					stringsList.add(inputLine);
				in.close();
				strings = (String[])stringsList.toArray(new String[0]);
			}
			String prefix = Utils.ensureHasTrailing('/', strings[0]);
			for (int i=1; i<strings.length; i++) {
				if( strings[i].indexOf(NAME_URL_SEPARATOR) >= 0 ) {
					names.add( strings[i].substring(0, strings[i].indexOf(NAME_URL_SEPARATOR)) );
					urls.add( prefix + Utils.ensureHasNoLeading('/', 
						strings[i].substring(strings[i].indexOf(NAME_URL_SEPARATOR) + NAME_URL_SEPARATOR.length())));
				}
				else {
					names.add( strings[i] );
					urls.add( prefix + Utils.ensureHasNoLeading('/', strings[i]) );
				}
			}
		}
		catch (NullPointerException npe) {
			log.error("NullPointerException in ListLoader: " + npe.fillInStackTrace());
		}
		catch (IndexOutOfBoundsException ioob) {
			log.error("IndexOutOfBoundsException in ListLoader: " + ioob.fillInStackTrace());
		}
		catch (MalformedURLException mue) {
			log.error("MalformedURLException in ListLoader: " + mue.fillInStackTrace());
		}
		catch (UnsupportedEncodingException uee) {
			log.error("UnsupportedEncodingException in ListLoader: " + uee.fillInStackTrace());
		}
		catch (IOException ioe) {
			log.error("IOException in ListLoader: " + ioe.fillInStackTrace());
		}
		catch (ClassNotFoundException cnfe) {
			log.error("ClassNotFoundException in ListLoader: " + cnfe.fillInStackTrace());
		}
	}
	
	/** Loads the lists of images
	 * @param config Kafenio configuration
	 * @param names The returned list of image names
	 * @param urls The returned list of image Urls
	 */
	public static void loadImages(KafenioPanelConfiguration config, List names, List urls) {
		loadLists(config, "GetImages=true", names, urls); 
	}

	/** Loads the lists of predefined links
	 * @param config Kafenio configuration
	 * @param names The returned list of image names
	 * @param urls The returned list of image Urls
	 */
	public static void loadLinks(KafenioPanelConfiguration config, List names, List urls) {
		loadLists(config, "GetFiles=true", names, urls); 
	}
	
}
