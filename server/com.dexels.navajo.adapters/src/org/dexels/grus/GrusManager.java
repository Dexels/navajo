package org.dexels.grus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrusManager implements Runnable {

	private final static GrusManager instance; 
	private Thread myThread;
	private final Set<DbConnectionBroker> registeredBrokers = Collections.synchronizedSet(new HashSet<DbConnectionBroker>());
	private boolean shutdown = false;
	
	private final static Logger logger = LoggerFactory
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
		List<DbConnectionBroker> br = new ArrayList<DbConnectionBroker>(registeredBrokers);
		for (DbConnectionBroker db : br) {
			try {
				db.destroy();
			} catch (Throwable t) {
				System.err.println("Problem shutting down broker: ");
				t.printStackTrace();
			}
		}
	}

	public static GrusManager getInstance() {
		return instance;
	}

	public void addBroker(DbConnectionBroker broker) {
		registeredBrokers.add(broker);
	}

	public void removeBroker(DbConnectionBroker broker) {
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
				Iterator<DbConnectionBroker> allBrokers = registeredBrokers.iterator();
				while ( allBrokers.hasNext() ) {
					DbConnectionBroker inspectedBroker = allBrokers.next();
					inspectedBroker.refreshConnections();
				}
			} catch (Throwable t) {
				logger.error("Error: ", t);
			}
		} // while true.
	}
	
	public int getInstances() {
		return registeredBrokers.size();
	}
}
