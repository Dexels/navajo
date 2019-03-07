package org.dexels.grus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class GrusManager implements Runnable {

	private static final GrusManager instance; 
	private Thread myThread;
	private final Set<LegacyDbConnectionBroker> registeredBrokers = Collections.synchronizedSet(new HashSet<LegacyDbConnectionBroker>());
	private boolean shutdown = false;
	
	private static final Logger logger = LoggerFactory
			.getLogger(GrusManager.class);
	
	static  {
		GrusManager myGrus = new GrusManager();
		myGrus.myThread = new Thread(myGrus, "Grus DbConnection Manager");
		myGrus.myThread.setDaemon(true);
		myGrus.myThread.start();
		instance = myGrus;
	}
	
	public void shutdown() {

		shutdown = true;
		myThread.interrupt();
		List<LegacyDbConnectionBroker> br = new ArrayList<LegacyDbConnectionBroker>(registeredBrokers);
		for (LegacyDbConnectionBroker db : br) {
			try {
				db.destroy();
			} catch (Throwable t) {
				logger.error("Problem shutting down broker:  ", t);
			}
		}
	}

	public static GrusManager getInstance() {
		return instance;
	}

	public void addBroker(LegacyDbConnectionBroker broker) {
		registeredBrokers.add(broker);
	}

	public void removeBroker(LegacyDbConnectionBroker broker) {
		registeredBrokers.remove(broker);
	}

	@Override
	public void run() {
		
		while (!shutdown) {	
			try {
				synchronized (this) {
					wait(60000);
				}
				// Make copy to avoid concurrent modification exception.com
				Iterator<LegacyDbConnectionBroker> allBrokers = registeredBrokers.iterator();
				while ( allBrokers.hasNext() ) {
					LegacyDbConnectionBroker inspectedBroker = allBrokers.next();
					inspectedBroker.refreshConnections();
				}
			} catch (InterruptedException t) {
				// nothing
			} catch (Throwable t) {
				logger.error("Error: ", t);
			}
		} // while true.
	}
	
	public int getInstances() {
		return registeredBrokers.size();
	}
}
