package com.dexels.navajo.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.util.AuditLog;

/**
 * This class can be used to create user defined services that must be started upon engine start.
 * 
 * @author arjen
 *
 */
public class UserDaemon extends GenericThread {

	private static HashMap<String, UserDaemon> userServices = new HashMap<String,UserDaemon>();
//	private int sleepTime;
	
	public static void startService(UserDaemon userService) {
		if (!userServices.containsKey(userService.getMyId()) ) {
			userServices.put(userService.getMyId(), userService);
			userService.startThread(userService);
			AuditLog.log("USERDAEMON", "Started user daemon: " + userService.getMyId());
		} else {
			AuditLog.log("USERSERVICES", "Could not register user service: " + userService.getMyId() + ": already got service with this name.");
		}
	}
	
	
	protected static UserDaemon getInstance(String id) {
		return userServices.get(id);
	}
	
	@SuppressWarnings("unchecked")
	protected static void startup() {
		// Read XML config with user services.
	    /*
	     * <message name="daemons">
	     *    <message name="daemons">
	     *         <property name="ClassName" type="string"/>
	     *         <property name="SleepTime" type="integer"/>
	     *    </message>
	     * </message>
	     */
		
		System.err.println("\n\n************************************ IN USER DAEMON STARTUP ************************************");
		//Thread.dumpStack();
		//System.err.println("************************************************************************************************\n\n");
		try {
			Navajo services = DispatcherFactory.getInstance().getNavajoConfig().readConfig("daemons.xml");
			if ( services != null && services.getMessage("daemons") != null ) {
				List<Message> serviceList = services.getMessage("daemons").getElements();
				for ( int i = 0; i < serviceList.size(); i++ ) {
					Message m = serviceList.get(i);
					Property className = m.getProperty("ClassName");
					Property sleepTime = m.getProperty("SleepTime");
					if ( className == null ) {
						throw new RuntimeException("Property ClassName missing in daemons.xml");
					}
					if ( sleepTime == null ) {
						throw new RuntimeException("Property SleepTime missing in daemons.xml");
					}
					// Make sure to intialize instance!
					ClassLoader cl = DispatcherFactory.getInstance().getNavajoConfig().getClassloader();
					try {
						Class<? extends UserDaemon> c = (Class<? extends UserDaemon>) Class.forName(className.getValue(), true,cl);
						UserDaemon ud = (UserDaemon) c.newInstance();
						ud.setSleepTime(Integer.parseInt(sleepTime.getValue()));
						startService(ud);
					} catch (Exception e) {
						AuditLog.log("USERDAEMON", "Could not instantiate user daemon class: " + className.getValue() + ", reason: " + e.getMessage());
					} 
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UserDaemon(String id) {
		super(id);
	}

}
