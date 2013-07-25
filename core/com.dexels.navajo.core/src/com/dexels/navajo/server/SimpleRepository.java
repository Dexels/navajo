package com.dexels.navajo.server;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.SystemException;

public class SimpleRepository implements Repository, GlobalManager {

	public NavajoConfigInterface config;

	// name of the message containing globals
	public static final String GLOBALSMSGNAME = "__globals__";

	private static final Logger logger = LoggerFactory.getLogger(SimpleRepository.class);
	
	public SimpleRepository() {
	}
	
	public void activate() {
	}

	public void setNavajoConfig(NavajoConfigInterface config) {
		this.config = config;
	}

	/**
	 * @param config the config to remove 
	 */
	public void clearNavajoConfig(NavajoConfigInterface config) {
		this.config = null;
	}

	
	/**
	 * @see Repository
	 */
	
	public boolean useLegacyDateMode() {
		return false;
	}
	
	public Access authorizeUser(String username, String password,
			String service, Navajo inMessage, Object certificate)
			throws SystemException, AuthorizationException {
		try {
			initGlobals(service, username, inMessage, null);
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		return new Access( 1, 1, username, service, "", "", "", certificate);
	}

	/**
	 * @param username
	 * @param inMessage
	 * @param region
	 * @param userRoleString
	 * @throws NavajoException
	 */
	public void initGlobals(String method, String username, Navajo inMessage, Map<String,String> extraParams) throws NavajoException {
//		config.getRootPath();
		// TODO I think this is pretty inefficient. Redo this.
		try {
			ResourceBundle rb = ResourceBundle.getBundle("application");
			parseBundle(method, username, inMessage, extraParams, rb);
			
		} catch (MissingResourceException e) {
			
			InputStream stream = null;
			try {
				stream = config.getResourceBundle("application");
				if(stream==null) {
					logger.debug("Also not found, no globals then.");
					return;
				}
				PropertyResourceBundle prb = new PropertyResourceBundle(stream);
				parseBundle(method, username, inMessage, extraParams, prb);

			} catch (IOException e1) {
				logger.debug("Still can not open resource bundle. Also no big deal, I guess");
				logger.error("Error: ", e1);
			} finally {
				if ( stream != null ) {
					try {
						stream.close();
					} catch (IOException e1) {
					}
				}
			}
		}
	}

	private static void parseBundle(String method, String username,
			Navajo inMessage, Map<String, String> extraParams, ResourceBundle rb) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> all = rb.getKeys();
		while (all.hasMoreElements()) {
			String key = all.nextElement();
			String value = rb.getString(key);
			map.put(key, value);
		}
		parseBundle(method, username, inMessage, extraParams, map);
	}

	public static void parseBundle(String method, String username, Navajo inMessage, Map<String, String> extraParams, Map<String,String> m)
			throws NavajoException {
		Message msg = inMessage.getMessage(GLOBALSMSGNAME);

		Message paramMsg = null;
		if (msg!=null) {
			paramMsg = msg;
		} else {
			paramMsg = NavajoFactory.getInstance().createMessage(inMessage, GLOBALSMSGNAME);
			inMessage.addMessage(paramMsg);
		}

		Property nu = NavajoFactory.getInstance().createProperty(inMessage, "NavajoUser", Property.STRING_PROPERTY, username, 50, "", Property.DIR_OUT);
		paramMsg.addProperty(nu);
		Property nm = NavajoFactory.getInstance().createProperty(inMessage, "NavajoMethod", Property.STRING_PROPERTY, method, 50, "", Property.DIR_OUT);
		paramMsg.addProperty(nm);
		
		appendMapToInput(inMessage, extraParams, m);
	}

	public static void appendMapToInput(Navajo inMessage, Map<String, String> extraParams,
			Map<String, String> m) {
		
		Message msg = inMessage.getMessage(GLOBALSMSGNAME);

		Message paramMsg = null;
		if (msg!=null) {
			paramMsg = msg;
		} else {
			paramMsg = NavajoFactory.getInstance().createMessage(inMessage, GLOBALSMSGNAME);
			inMessage.addMessage(paramMsg);
		}
		
		// Add application instance, i.e. "Bond" specific parameters from
		// application.properties file.
		if(m!=null) {
			for (Entry<String,String> e : m.entrySet()) {
				Property p2 = NavajoFactory.getInstance().createProperty(inMessage, e.getKey(), Property.STRING_PROPERTY,
						e.getValue(), 10, "",
						Property.DIR_OUT);
				paramMsg.addProperty(p2);
			}
		}
		if (extraParams!=null) {
			for (Iterator<Entry<String,String>> iter = extraParams.entrySet().iterator(); iter.hasNext();) {
				Entry<String,String> e = iter.next();
				String key = e.getKey();
				String value = e.getValue();
				Property p2 = NavajoFactory.getInstance().createProperty(inMessage, key, Property.STRING_PROPERTY,
						value, 10, "",
						Property.DIR_OUT);
				paramMsg.addProperty(p2);
			}

		}
	}

	public String getServlet(Access access) throws SystemException {
		String compLanguage = DispatcherFactory.getInstance().getNavajoConfig().getCompilationLanguage();
		if("javascript".equals(compLanguage)) {
			return "com.dexels.navajo.rhino.RhinoHandler";
		}
			return "com.dexels.navajo.server.GenericHandler";
	}

	@Override
	public void initGlobals(Navajo inMessage) throws NavajoException {
		Header h = inMessage.getHeader();
		if(h==null) {
			logger.warn("Can not append globals to input message: No header found.");
			return;
		}
		String rpcName = h.getRPCName();
		String username = h.getRPCUser();
		initGlobals(rpcName, username, inMessage, null);
	}

}
