package com.dexels.navajo.server;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import java.util.*;
import java.util.Map.Entry;

import com.dexels.navajo.document.*;

public class SimpleRepository implements Repository {

	public NavajoConfig config;

	// name of the message containing globals
	public static final String GLOBALSMSGNAME = "__globals__";

	public SimpleRepository() {
	}

	public void setNavajoConfig(NavajoConfig config) {
		this.config = config;
	}

	public Access authorizeUser(String username, String password,
			String service, Navajo inMessage, Object certificate)
			throws SystemException, AuthorizationException {
		return new Access(1, 1, 1, username, service, "", "", "", certificate);
	}

	/**
	 * @param username
	 * @param inMessage
	 * @param region
	 * @param userRoleString
	 * @throws NavajoException
	 */
	public void initGlobals(String method, String username, Navajo inMessage, Map<String,String> extraParams) throws NavajoException {

		try {
			
			ResourceBundle rb = ResourceBundle.getBundle("application");
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

			// Add application instance, i.e. "Bond" specific parameters from
			// application.properties file.
			Enumeration<String> all = rb.getKeys();
			while (all.hasMoreElements()) {
				String key = all.nextElement();
				Property p2 = NavajoFactory.getInstance().createProperty(inMessage, key, Property.STRING_PROPERTY,
						rb.getString(key), 10, "",
						Property.DIR_OUT);
				paramMsg.addProperty(p2);
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

		} catch (MissingResourceException e) {
//			e.printStackTrace();
			System.err.println("Can not open resource bundle. No big deal, I guess");
		}

	}

	public Parameter[] getParameters(Access access) throws SystemException {
		return null;
	}

	public void logTiming(Access access, int part, long timespent)
			throws SystemException {
	}

	public void logAction(Access access, int level, String comment)
			throws SystemException {
	}

	public String getServlet(Access access) throws SystemException {
		if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON) || 
				access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON_SEND) ||
				access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_PING)  
		)
			return "com.dexels.navajo.server.MaintainanceHandler";
		else
			return "com.dexels.navajo.server.GenericHandler";
	}

	public String[] getServices(Access access) throws SystemException {
		return null;
	}
}
