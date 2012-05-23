/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package com.dexels.navajo.lockguard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

class TotalDefinitions {
	int count;
}

public final class LockManager extends GenericThread implements LockManagerMXBean {

	public LockDefinition [] definitions;
	public Lock [] locks;
	public int lockCount;
	
	public static final String VERSION = "$Id$";
	
	final Map<Integer,LockDefinition> lockDefinitions = Collections.synchronizedMap( new HashMap<Integer,LockDefinition>() );
	static volatile LockManager instance = null;
	boolean readingDefinitions = true;
	private long configTimestamp = -1;
	
	private final static String id = "Navajo LockManager";
	
	private static Object semaphore = new Object();
	
	public LockManager() {
		super(id);
	}
	
	private final static String LOCKS_CONFIG = "locks.xml";

	private final long getConfigTimeStamp() {
		if (DispatcherFactory.getInstance().getNavajoConfig() != null ) {
			java.io.File f = new java.io.File(DispatcherFactory.getInstance().getNavajoConfig() + "/" + LOCKS_CONFIG);
			if ( f != null && f.exists() ) {
				return f.lastModified();
			} else {
				return 0;
			}
		} else {
			return -1;
		}
		
	}
	
	private final void setConfigTimeStamp() {
		configTimestamp = getConfigTimeStamp();
	}
	
	private final boolean isConfigModified() {
		if ( configTimestamp != getConfigTimeStamp() && getConfigTimeStamp() != -1 ) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	private final void readDefinitions() {

		FileInputStream in = null;
		
		try {
			readingDefinitions = true;
			lockDefinitions.clear();
			
			in = (DispatcherFactory.getInstance().getNavajoConfig() == null ? new FileInputStream("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/config/locks.xml")
					: new FileInputStream(DispatcherFactory.getInstance().getNavajoConfig().getConfigPath() + "/" + LOCKS_CONFIG));
			
			Navajo definition = NavajoFactory.getInstance().createNavajo( in  );
								
			ArrayList all = definition.getMessage("Locks").getAllMessages();
			for ( int i = 0; i < all.size(); i++ ) {
				Message lock = (Message) all.get(i);
				String ws = lock.getProperty("WebservicePattern").getValue();
				boolean matchUsername = ((Boolean) lock.getProperty("MatchUsername").getTypedValue()).booleanValue();
				boolean matchRequest = ((Boolean) lock.getProperty("MatchRequest").getTypedValue()).booleanValue();
				int maxInstance = ((Integer) lock.getProperty("MaxInstanceCount").getTypedValue()).intValue();
				int timeOut = ((Integer) lock.getProperty("TimeOut").getTypedValue()).intValue();
				HashMap excludeMessages = new HashMap();
				if ( matchRequest && lock.getMessage("ExcludedMessages") != null ) {
					ArrayList excludedMessagesList = lock.getMessage("ExcludedMessages").getAllMessages();
					for (int j = 0; j < excludedMessagesList.size(); j++) {
						Message ex = (Message) excludedMessagesList.get(j);
						String mn = ex.getProperty("MessageName").getValue();
						String exprops = ex.getProperty("ExcludedProperties").getValue();
						excludeMessages.put(mn, exprops);
					}
				}
				LockDefinition ld = new LockDefinition(ws, matchUsername, matchRequest, excludeMessages, timeOut, maxInstance);
				lockDefinitions.put( new Integer(ld.id), ld );
			}
			
		} catch (FileNotFoundException e) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_LOCK_MANAGER, "Could not read config file");
		} finally {
			readingDefinitions = false;
			LockStore.getStore().reset();
			setConfigTimeStamp();
			AuditLog.log(AuditLog.AUDIT_MESSAGE_LOCK_MANAGER, "Read new lock definitions");
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// NOT INTERESTED.
				}
			}
		}
	}
	
	public static LockManager getInstance() {
		
		if ( instance != null ) {
			return instance;
		}
		
		synchronized ( semaphore ) {
			if ( instance == null ) {
				instance = new LockManager();
				instance.startThread(instance);
				try {
					JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
				} catch (Throwable t) {
					t.printStackTrace(System.err);
				} 
			}
		}
		return instance;
	}
	
	public void removeLocks(Access a, Lock [] allLocks) {
		for (int i = 0; i < allLocks.length; i++ ) {
			LockStore.getStore().removeLock(a, allLocks[i]);
		}
	}
	
	/**
	 * Return all locks set by this access.
	 * 
	 * @param a
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unchecked" })
	public final Lock [] grantAccess(final Access a) throws LocksExceeded {
		
		while ( readingDefinitions ) {
			// Wait
			try {
				Thread.sleep(100);
				System.err.println("Waiting while definitions are read");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		final ArrayList lockList = new ArrayList();
		final Iterator iter = lockDefinitions.values().iterator();
		
		// Try ty acquire locks in parallel.
		final TotalDefinitions totalDefinitions = new TotalDefinitions();
		totalDefinitions.count = lockDefinitions.size();
		//System.err.println("TOTAL NUMBER OF LOCK DEFINITIONS: " + totalDefinitions.count);
		
		// Fork for each lockdefinition....
		while ( iter.hasNext() ) {
			final LockDefinition ld = (LockDefinition) iter.next();
			new Thread() {
				public void run() {
					Lock l;
					try {
						//System.err.println("Trying to acquire lock for: " + ld);
						l = LockStore.getStore().addLock(a, ld, 0 );
						if ( l != null && !( a.getException() != null && a.getException() instanceof LocksExceeded )) {
							lockList.add(l);
							//System.err.println(">>>> Got lock for: " + ld);
						} else {
							//System.err.println("Not lock for: " + ld);
						}
					} catch (LocksExceeded e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						//System.err.println(">>>> Locks exceeded lock for: " + ld);
					} finally {
						synchronized (totalDefinitions) {
							totalDefinitions.count--;
							//System.err.println("DECREASED totalDefinitions, count = " + totalDefinitions.count );
							totalDefinitions.notifyAll();
						}	
					}
				}
			}.start();
		}
		
		// Lockdefinition barrier.
		//System.err.println("ABOUT TO WAIT FOR ALL LOCKS.....");
		while ( totalDefinitions.count > 0 ) { // barrier condition.
			synchronized (totalDefinitions) {
				try {
					totalDefinitions.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//System.err.println("GOT 'M ALL... OR NOT: " + a.getException());
		
		// If a LocksExceeded exception was thrown by one of the lock threads, clean up locks and throw exception...
		if ( a.getException() != null && a.getException() instanceof LocksExceeded ) {
			// Remove previously set locks and throw exception.
			for (int i = 0; i < lockList.size(); i++) {
				LockStore.getStore().removeLock(a, (Lock) lockList.get(i));
			}
			throw (LocksExceeded) a.getException();
		}
		
		if ( lockList.size() == 0) {
			return null;
		}
		
		Lock [] locks = new Lock[lockList.size()];
		return (Lock []) lockList.toArray(locks);
	}
	
	public static void main(String [] args) throws Exception {
		Navajo n1 = NavajoFactory.getInstance().createNavajo();
		Navajo n2 = NavajoFactory.getInstance().createNavajo();
		
		Message m1 = NavajoFactory.getInstance().createMessage(n1, "Aap");
		Message m2 = NavajoFactory.getInstance().createMessage(n2, "Aap");
		
		n1.addMessage(m1);
		n2.addMessage(m2);
		
		Property p1 = NavajoFactory.getInstance().createProperty(n1, "Noot", Property.STRING_PROPERTY, "Harrie", 0, "", Property.DIR_OUT);
		Property p2 = NavajoFactory.getInstance().createProperty(n2, "Noot", Property.STRING_PROPERTY, "Harrie K.", 0, "", Property.DIR_OUT);
		
		m1.addProperty(p1);
		m2.addProperty(p2);
		
		// Test.
		LockManager lm = LockManager.getInstance();
		Access a = new Access(1, 1, 2, "arjen", "ProcessWhatever", "", "", "", null);
		a.setInDoc(n1);
		
		Lock [] locks = lm.grantAccess( a );
		
		if ( locks != null ) {
			for (int i = 0 ; i < locks.length; i++ ) {
				System.err.println(locks[i]);
			}
		}
		
		// Simulate another access.
		System.err.println("ANOTHER REQUEST: \n");
		Access a2 = new Access(2, 2, 2, "arjen", "ProcessWhatever", "", "", "", null);
		a2.setInDoc(n2);
		Lock [] locks2 = lm.grantAccess( a2 );
		
		if ( locks2 != null ) {
			for (int i = 0 ; i < locks2.length; i++ ) {
				System.err.println(locks2[i]);
			}
		}
		
		while ( true ) {
			Thread.sleep(10000);
		}
	}

	public void worker() {
		
		if ( isConfigModified() ) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_LOCK_MANAGER, "Lock definitions are modified, re-initializing");
			readDefinitions();
		}
		
	}
	
	public int getLockCount() {
		if ( LockStore.getStore() != null ) {
			return LockStore.getStore().getStoreSize();
		} else {
			return 0;
		}
	}
	
	public Lock [] getLocks() {
		return LockStore.getStore().getAllLocks();
	}
	
	public LockDefinition [] getDefinitions() {
		definitions = new LockDefinition[instance.lockDefinitions.size()];
		return (LockDefinition []) instance.lockDefinitions.values().toArray(definitions);
	}
	
	public String getVERSION() {
		return VERSION;
	}

	private final static void resetInstance() {
		instance = null;
	}
	
	public void terminate() {
		LockStore.getStore().reset();
		lockDefinitions.clear();
		resetInstance();
		AuditLog.log(AuditLog.AUDIT_MESSAGE_LOCK_MANAGER, "Killed");	
	}

	public void clearAllLocks() {
		LockStore.getStore().removeAllLocks();
	}
}
