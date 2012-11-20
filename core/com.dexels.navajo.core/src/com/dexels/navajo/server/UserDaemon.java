package com.dexels.navajo.server;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.util.AuditLog;

/**
 * This class can be used to create user defined services that must be started
 * upon engine start.
 * 
 * @author arjen
 * 
 */
public class UserDaemon extends GenericThread {

	private static HashMap<String, UserDaemon> userServices = new HashMap<String, UserDaemon>();
	// private int sleepTime;

	private final static Logger logger = LoggerFactory
			.getLogger(UserDaemon.class);

	public static void startService(UserDaemon userService) {
		if (!userServices.containsKey(userService.getMyId())) {
			userServices.put(userService.getMyId(), userService);
			userService.startThread(userService);
			AuditLog.log("USERDAEMON",
					"Started user daemon: " + userService.getMyId());
		} else {
			AuditLog.log("USERSERVICES", "Could not register user service: "
					+ userService.getMyId()
					+ ": already got service with this name.");
		}
	}

	protected static UserDaemon getInstance(String id) {
		return userServices.get(id);
	}

	protected static void startup() {
		// Read XML config with user services.
		/*
		 * <message name="daemons"> <message name="daemons"> <property
		 * name="ClassName" type="string"/> <property name="SleepTime"
		 * type="integer"/> </message> </message>
		 */

		// System.err.println("\n\n************************************ IN USER DAEMON STARTUP ************************************");
		// Thread.dumpStack();
		// System.err.println("************************************************************************************************\n\n");
		Navajo services = null;
		try {
			services = DispatcherFactory.getInstance().getNavajoConfig()
					.readConfig("daemons.xml");
		} catch (Exception e1) {
			logger.error("No 'deamons.xml' configuration file found, abandoning UserDaemon startup.");
			return;
		}
		if (services != null && services.getMessage("daemons") != null) {
			List<Message> serviceList = services.getMessage("daemons")
					.getElements();
			for (int i = 0; i < serviceList.size(); i++) {
				Message m = serviceList.get(i);
				Property className = m.getProperty("ClassName");
				Property sleepTime = m.getProperty("SleepTime");
				if (className == null) {
					throw new RuntimeException(
							"Property ClassName missing in daemons.xml");
				}
				if (sleepTime == null) {
					throw new RuntimeException(
							"Property SleepTime missing in daemons.xml");
				}
				// Make sure to intialize instance!
				ClassLoader cl = DispatcherFactory.getInstance()
						.getNavajoConfig().getClassloader();
				try {
					Class<? extends UserDaemon> c = (Class<? extends UserDaemon>) Class
							.forName(className.getValue(), true, cl);
					UserDaemon ud = c.newInstance();
					ud.setSleepTime(Integer.parseInt(sleepTime.getValue()));
					startService(ud);
				} catch (Exception e) {
					AuditLog.log(
							"USERDAEMON",
							"Could not instantiate user daemon class: "
									+ className.getValue() + ", reason: "
									+ e.getMessage());
				}
			}
		}
	}

	public UserDaemon(String id) {
		super(id);
	}

}
