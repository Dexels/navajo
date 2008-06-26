package com.dexels.navajo.integrity;

import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.sharedstore.SharedStoreException;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterface;
import com.dexels.navajo.sharedstore.SharedStoreLock;
import com.dexels.navajo.sharedstore.SharedStoreObject;
import com.dexels.navajo.sharedstore.map.SharedTribalMap;
import com.dexels.navajo.util.AuditLog;

public class TribalWorker extends GenericThread  implements WorkerInterface {

	/**
	 * Public fields (used as getters for mappable).
	 */
	public int cacheSize;
	public int workSize;
	public int notWrittenSize;
	private int fileCount = 0;
	public int violationCount = 0;
	public String requestId;
	public boolean isRunning;
	
	public static final String VERSION = "$Id$";
	
	private static volatile TribalWorker instance = null;
	
	private static Object semaphore = new Object();
	
	// Worklist containst responses that still need to be written to a file.
	private final SharedTribalMap workList; // = new HashMap<String,Navajo>();
	
	// Integrity cache contains mapping between unique request id and response file.
	private final SharedTribalMap integrityCache;
	
	// Contains all unique request ids that still need to be handled by the worker thread.
	private final SharedTribalMap  notWrittenReponses;
	
	// TODO: Create SharedTribalSet.
	
	// Contains all currently running request ids.
	private final SharedTribalMap runningRequestIds;
	
	private final static String REQUEST_CACHE = "integrity";
	private final static String RESPONSE_PREFIX = "navajoresponse_";
	private final static String myId = "Navajo Integrity Worker (Tribal Aware)";
	
	private int previousWorkListLevelSize = 0;
	private int maxWorkLostLevelSize = 30;
	
	public TribalWorker() {
		super(myId);
		workList = new SharedTribalMap("integrity-worklist");
		SharedTribalMap.registerMap(workList, false);
		
		integrityCache = new SharedTribalMap("integrity-cache");
		SharedTribalMap.registerMap(integrityCache, false);
		//integrityCache.setThreadSafe(true);
		
		runningRequestIds = new SharedTribalMap("integrity-running-request-ids");
		SharedTribalMap.registerMap(runningRequestIds, false);
		//runningRequestIds.setThreadSafe(true);
		
		notWrittenReponses = new SharedTribalMap("integrity-not-written-responses");
		SharedTribalMap.registerMap(notWrittenReponses, false);
		notWrittenReponses.setThreadSafe(true);
		
	}
	
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	public static TribalWorker getInstance() {

		if ( instance != null ) {
			return instance;
		}

		synchronized ( semaphore ) {

			if ( instance != null ) {
				return instance;
			}

			try {
				SharedStoreFactory.getInstance().createParent(REQUEST_CACHE);
			} catch (SharedStoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "getInstance(), Could not Create Integrity Cache in Shared Store");
				return null;
			}

			instance = new TribalWorker();
			JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, myId);
			instance.setSleepTime(1000);
			instance.startThread(instance);

			String [] allResponses = SharedStoreFactory.getInstance().getObjects(REQUEST_CACHE);
			for (int i = 0; i < allResponses.length; i++) {
				SharedStoreFactory.getInstance().remove(REQUEST_CACHE, allResponses[i]);
			}

			AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Started integrity worker $Id$");
		}
		return instance;
	}

	/**
	 * Writes a file with a response to a unique file and couple it to it's unique id
	 * in the integrityCache. The unique id is removed from the notWrittenReponses set to
	 * indicate that it's ready to be read.
	 * 
	 * @param id
	 * @param response
	 */
	private void writeFile(String id, Navajo response) {
		
		SharedStoreObject sso = null;

		try {

			if ( integrityCache.containsKey(id) ) {
				AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Response id " + id + " already cached!");
				return;
			}

			fileCount++;

			sso = new SharedStoreObject(REQUEST_CACHE, RESPONSE_PREFIX + id + ".xml");

			if ( sso == null ) {
				AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Could not create temp file");
				return;
			}

			response.write(sso.getOutputStream());

			integrityCache.put(id, sso );


		} catch (Exception e) {
			// TODO Auto-generated catch block
			if ( sso != null ) {
				sso.remove();
			}
			fileCount--;
			integrityCache.remove(id);
			e.printStackTrace();
		} finally {
			notWrittenReponses.remove( id );
		}
	}
	
	public final void worker() {
		
		int level = workList.size();
		if ( ( level !=  previousWorkListLevelSize) && workList.size() > maxWorkLostLevelSize ) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "WARNING: Integrity Worker TODO list size:  " + workList.size());
			sendHealthCheck(level, maxWorkLostLevelSize, "WARNING", "Integrity worker TODO list size is rather large");
		}
		previousWorkListLevelSize = level;

		// Check for new access objects in workList.
		HashMap<String,Navajo> copyOfWorkList = null;
		Set<String> s = null;

		// Lock worklist. Wait until available...
		synchronized (workList) {
			SharedStoreLock ssl = SharedStoreFactory.getInstance().lock(REQUEST_CACHE, "worklist", SharedStoreInterface.READ_WRITE_LOCK, true);
			if ( ssl == null ) {
				//System.err.println("Could not get lock on worklist...");
				return;
			}
			try {
				copyOfWorkList = new HashMap<String,Navajo>(workList);
				workList.clear();
				//System.err.println(Dispatcher.getInstance().getApplicationId() + ": cleared worklist");
			} finally {
				// Release lock after copy of worklist has been made.
				SharedStoreFactory.getInstance().release(ssl);
			}
		}

		s = new HashSet<String>(copyOfWorkList.keySet());
		Iterator<String> iter = s.iterator();
		while (iter.hasNext()) {
			String id = iter.next();
			if ( id != null && !id.trim().equals("") ) {
				writeFile( id, (Navajo) copyOfWorkList.get(id) );
			}
			if ( id != null ) {
				notWrittenReponses.remove( id );
			}
		}

		// Remove 'old' responses.
		Set<String> s2 = new HashSet<String>(integrityCache.keySet());
		Iterator<String> i = s2.iterator();
		long now = System.currentTimeMillis();
		while ( i.hasNext() ) {
			String id = i.next();
			SharedStoreObject f = (SharedStoreObject) integrityCache.get(id);
			if ( f != null ) {
				long birth = f.getModificationTime();
				if ( now - birth > 60000 ) {
					// remove file reference from integrity cache.
					f.remove();
					f = null;
					fileCount--;
					integrityCache.remove(id);
				}
			} else {
				fileCount--;
				integrityCache.remove(id);
			}
		}
	
	}
	
	/**
	 * Clears the entire integrity cache.
	 */
	private void clearCache() {

		SharedStoreLock ssl = SharedStoreFactory.getInstance().lock(REQUEST_CACHE, "integrityCache", SharedStoreInterface.READ_WRITE_LOCK, true);

		try {
			Set<String> s = new HashSet<String>(integrityCache.keySet());
			Iterator<String> i = s.iterator();
			while ( i.hasNext() ) {
				String id = i.next();
				SharedStoreObject f = (SharedStoreObject) integrityCache.get(id);
				// remove file reference from integrity cache.
				if ( f != null ) {
					f.remove();
					fileCount--;
					integrityCache.remove(id);
				}
				// remove file itself.
			}
		} finally {
			if ( ssl != null ) {
				SharedStoreFactory.getInstance().release(ssl);	
			}
		}
	}
	
	/**
	 * Read a file fileName containing a response XML and returns the corresponding Navajo object.
	 * 
	 * @param fileName
	 * @return
	 */
	private Navajo readFile(SharedStoreObject f) {
		InputStream fs = null;
		try {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Integrity violation detected, returning previous response from: " + f.getName());
			violationCount++;
			// Set modification date to time of last read.
			f.setModificationTime( System.currentTimeMillis() );
			fs = f.getInputStream();
			Navajo n = NavajoFactory.getInstance().createNavajo(fs);
			return n;
		} catch (SharedStoreException sse) {
			sse.printStackTrace(System.err);
			return null;
		} finally {
			if ( fs != null ) {
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Check if there is a running request id.
	 * If so, return Access object of already running request.
	 * 
	 * @param a
	 * @param request
	 * @return
	 */
	private final boolean waitedForRunningRequest(Access a, Navajo request, String requestId) {
		
		Access r = (Access) runningRequestIds.get(requestId);
		//System.err.println("Size of runningRequestIds: " + runningRequestIds.size());
		
		if ( r == null ) {
			//System.err.println("Did not find request id " + requestId + " in running request list");
			runningRequestIds.put(requestId, a);
			return false;
		} else {
			
			try {
				// Wait until running request is ready.
				// System.err.println("DID find request id " + id + " in running request list, wait until ready...");
				a.setWaitingForPreviousResponse(r.accessID);
				while ( !r.isFinished() ) {
					try {
						Thread.sleep(2000);
						//System.err.println(a.accessID + ": Waiting for currently running access set to become ready.");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} finally {
				runningRequestIds.remove(requestId);
			}
			
			a.setOutputDoc(r.getOutputDoc());
		}
		
		return true;
		
	}
	
	/**
	 * Get a possibly stored response from a previously received request.
	 * 
	 * @param request
	 * @return
	 */
	public Navajo getResponse(Access a, Navajo request) {

		/**
		 * In case there is no request id supplied, always return empty navajo.
		 */
		String requestId  = request.getHeader().getRequestId();
		if ( requestId == null || requestId.trim().equals("") ) {
			return null;
		}

		// Check for running request first.
		if ( !waitedForRunningRequest(a, request, requestId) ) {
			// Check if request id is in worklist (still) or integreatyCache (already).
			if ( notWrittenReponses.containsKey( requestId ) ||  workList.containsKey( requestId ) || integrityCache.containsKey( requestId ) ) {
				// Response file write could be still pending, due to a large workList size and/or large responses.
				while ( notWrittenReponses.containsKey( requestId ) ) {
					//AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER,"Integrity violation detected: waiting for response file " + fileName + " to become written...");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return readFile( (SharedStoreObject) integrityCache.get( requestId ) );
			} else {
				return null;
			}
		} else {
			// Return response from previously running request.
			return a.getOutputDoc();
		}
	}
	
	/**
	 * Adds a task to the workList. The response file is written in the worker thread.
	 * The request is immediately recorded as being not written.
	 * 
	 * @param request
	 * @param response
	 */
	public void setResponse(Navajo request, final Navajo response) {
		
		
		final String id  = request.getHeader().getRequestId();
		//System.err.println(Dispatcher.getInstance().getApplicationId() + ": writing response for " + id);
		// Immediately add request id to notWrittenResponses.
		notWrittenReponses.put( id, id );
		
		new Thread() {
			
			public void run() {
				try {

					if ( id != null && !id.trim().equals("") ) {
						
						//  Add response to workList.
						SharedStoreLock ssl = null;
						//System.err.println(Dispatcher.getInstance().getApplicationId() + ": Wait for worklist lock...");

						synchronized (workList) {
							try {
								// Lock worklist. Wait until available...
								ssl = SharedStoreFactory.getInstance().lock(REQUEST_CACHE, "worklist", SharedStoreInterface.READ_WRITE_LOCK, true);
								//System.err.println(Dispatcher.getInstance().getApplicationId() + ": Got worklist lock..." + ssl.parent + "/" + ssl.name );
								workList.put( id, response );
							} catch (Throwable t) {
								notWrittenReponses.remove( id );
								//System.err.println("COULD NOT ADD TO WORKLIST");
								t.printStackTrace(System.err);	
							} finally {
								if ( ssl != null ) {
									//System.err.println(Dispatcher.getInstance().getApplicationId() + ": about to release lock " + ssl.parent + "/" + ssl.name );
									SharedStoreFactory.getInstance().release(ssl);
								}
							}
						}
					}
				} finally {
					// Return from running request id set.
					runningRequestIds.remove(id);
				}
			}
		}.start();
		
	}
	
	/**
	 * Explicitly remove access object from running requests list based upon client request id.
	 * 
	 * @param request
	 */
	public void removeFromRunningRequestsList(Navajo request) {
		String id  = request.getHeader().getRequestId();
		if ( id != null ) {
			runningRequestIds.remove(id);
		}
	}
	
	public int getViolationCount() {
		if ( instance == null ) {
			return 0;
		}
		return instance.violationCount;
	}
	
	public int getCacheSize() {
		if ( instance == null ) {
			return 0;
		}
		return instance.integrityCache.size();
	}
	
	public int getWorkSize() {
		if ( instance == null ) {
			return 0;
		}
		return instance.workList.size();
	}
	
	public int getNotWrittenSize() {
		if ( instance == null ) {
			return 0;
		}
		return instance.notWrittenReponses.size();
	}
	
	public String getVERSION() {
		return VERSION;
	}
	
	public int getFileCount() {
		if ( instance == null ) {
			return 0;
		}
		return instance.fileCount;
	}
	
	public void terminate() {
		if ( instance != null ) {
			
			instance.workList.clear();
			instance.clearCache();
			instance.notWrittenReponses.clear();
			instance = null;
			try {
				JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, myId);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Killed");
		}
	}

	/**
	 * This method is used to introspect a running request.
	 * 
	 * @param requestId
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public boolean getIsRunning() {
		if ( getInstance().runningRequestIds.containsKey(requestId) ) {
			return true;
		} else {
			return false;
		}
	}

	public int getWarningLevelWorkList() {
		return maxWorkLostLevelSize;
	}

	public void setWarningLevelWorkList(int i) {
		maxWorkLostLevelSize = i;
	}

	public static void main(String [] args) throws Exception {
		ClientInterface client = NavajoClientFactory.getClient();
		client.setServers(new String[]{"localhost:8080/NavajoServer/Postman"});
		client.setUsername("ROOT");
		client.setPassword("");
		
		Navajo r = client.doSimpleSend("InitAap");
		
		r.write(System.err);
		
	}
}
