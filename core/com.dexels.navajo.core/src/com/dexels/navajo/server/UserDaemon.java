/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

	private static final Logger logger = LoggerFactory
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

	@Override
	public void kill() {
		super.kill();
		userServices.remove(this.getMyId());
	}

	protected static UserDaemon getInstance(String id) {
		return userServices.get(id);
	}

	protected static void startup() {
		Navajo services = null;
		try {
			services = DispatcherFactory.getInstance().getNavajoConfig()
					.readConfig("daemons.xml");
		} catch (Exception e1) {
			logger.error("No 'daemons.xml' configuration file found, abandoning UserDaemon startup.");
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
					throw new RuntimeException("Property ClassName missing in daemons.xml");
				}
				if (sleepTime == null) {
					throw new RuntimeException("Property SleepTime missing in daemons.xml");
				}
				// Make sure to intialize instance!
				ClassLoader cl = DispatcherFactory.getInstance()
						.getNavajoConfig().getClassloader();
				try {
					Class<?> c = Class.forName(className.getValue(), true, cl);
					UserDaemon ud = (UserDaemon) c.getDeclaredConstructor().newInstance();
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
