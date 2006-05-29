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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.integrity.Worker;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.util.AuditLog;

public class LockManager implements Runnable {

	public static final String VERSION = "$Id$";
	
	Map lockDefinitions = Collections.synchronizedMap( new HashMap() );
	static LockManager instance = null;
	boolean readingDefinitions = true;
	private long configTimestamp = -1;
	private NavajoConfig myConfig;
	
	private final static String LOCKS_CONFIG = "locks.xml";
	
	private long getConfigTimeStamp() {
		if ( myConfig != null ) {
			java.io.File f = new java.io.File(myConfig.getConfigPath() + "/" + LOCKS_CONFIG);
			if ( f != null && f.exists() ) {
				return f.lastModified();
			}
		} else {
			java.io.File f = new java.io.File("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/config/locks.xml");
			if ( f != null && f.exists() ) {
				return f.lastModified();
			}
		}
		return -1;
	}
	
	private void setConfigTimeStamp() {
		configTimestamp = getConfigTimeStamp();
	}
	
	private boolean isConfigModified() {
		if ( configTimestamp != getConfigTimeStamp() && getConfigTimeStamp() != -1 ) {
			return true;
		} else {
			return false;
		}
	}
	
	private void readDefinitions() {

		try {
			readingDefinitions = true;
			Navajo definition = (myConfig == null ? 
					NavajoFactory.getInstance().createNavajo( new FileInputStream("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/config/locks.xml") )
					:
				    NavajoFactory.getInstance().createNavajo( new FileInputStream(myConfig.getConfigPath() + "/" + LOCKS_CONFIG) )
				    );
					
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
			readingDefinitions = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			setConfigTimeStamp();
			AuditLog.log(AuditLog.AUDIT_MESSAGE_LOCK_MANAGER, "Read new lock definitions");
			
		}
	}
	
	public static LockManager getInstance(NavajoConfig config) {
		if ( instance == null ) {
			instance = new LockManager();
			instance.myConfig = config;
			
			Thread thread = new Thread(instance);
			thread.setDaemon(true);
			thread.start();
			
		}
		return instance;
	}
	
	/**
	 * Return all locks set by this access.
	 * 
	 * @param a
	 * @return
	 */
	public Lock [] grantAccess(Access a) throws LocksExceeded {
		
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
		
		ArrayList lockList = new ArrayList();
		Iterator iter = lockDefinitions.values().iterator();
		
		try {
			while ( iter.hasNext() ) {
				Lock l = LockStore.getStore().addLock(a, (LockDefinition) iter.next() );
				lockList.add(l);
			}
		} catch (LocksExceeded le) {
			// Remove previously set locks and throw exception.
			for (int i = 0; i < lockList.size(); i++) {
				LockStore.getStore().removeLock(a, (Lock) lockList.get(i));
			}
			throw le;
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
		LockManager lm = LockManager.getInstance(null);
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

	public void run() {
		
		readDefinitions();
		AuditLog.log(AuditLog.AUDIT_MESSAGE_LOCK_MANAGER, "Started locking manager $Id$");
		
		while ( true ) {
			try {
				Thread.sleep(200);
				System.err.print(".");
				if ( isConfigModified() ) {
					AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Lock definitions are modified, re-initializing");
					readDefinitions();
				}
				System.err.print(".");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AuditLog.log(AuditLog.AUDIT_MESSAGE_LOCK_MANAGER, "Stopped locking mananger $Id$");
			}
		}
		
		
	}
}
