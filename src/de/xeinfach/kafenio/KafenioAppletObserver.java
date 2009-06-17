package de.xeinfach.kafenio;

import java.util.HashMap;
import java.util.Iterator;

import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: An Observer to enable the content saving 
 * of all running kafenio-applets in a browser. This class is
 * implemented as singleton.
 * 
 * @author Karsten Pawlik
 */
public final class KafenioAppletObserver {
	
	private static LeanLogger log = new LeanLogger("KafenioAppletObserver.class");	

	private static KafenioAppletObserver instance = null;
	private static HashMap applets = new HashMap();

	private KafenioAppletObserver() {
		super();
	}
	
	/**
	 * @return returns a reference to the singleton KafenioAppletObserver instance.
	 */
	public static synchronized KafenioAppletObserver getInstance() {
		if (instance == null) {
			instance = new KafenioAppletObserver();
		}
		return instance;
	}
	
	/**
	 * adds an applet to be notified to the list.
	 * @param applet applet to add to the observer
	 */
	public synchronized void registerNewApplet(KafenioApplet applet) {
		applets.put(applet.getName(), applet);
		log.info("register: current number of applets: " + applets.size());
	}

	/**
	 * removes an applet from the notification list
	 * @param applet applet to remove from the observer
	 */
	public synchronized void unregisterApplet(KafenioApplet applet) {
		applets.remove(applet.getName());
		log.info("unregister: current number of applets: " + applets.size());
	}

	/**
	 * calls the saveContents() method of each registered KafenioApplet
	 */
	public synchronized void saveAllAppletContents() {
		log.debug("Saving Content in " + applets.size() + " applets.");
		
		for (Iterator iter = applets.values().iterator(); iter.hasNext();) {
			KafenioApplet element = (KafenioApplet) iter.next();
			element.saveAppletContents();	
		}
	}
}