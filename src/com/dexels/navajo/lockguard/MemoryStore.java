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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.util.regex.Pattern;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.sharedstore.map.SharedTribalMap;

@SuppressWarnings("unchecked")
public final class MemoryStore extends LockStore {

	public static final String VERSION = "$Id$";
	
	// store contains lock sets per id.
	// 0: l1, l2, l3
	// 1: l1
	// ..
	// TODO: USE TRIBAL MAP TO SUPPORT LOCKING OVER DIFFERENT TRIBE MEMBERS...
	public volatile static SharedTribalMap store;
	
	static {
		store = new SharedTribalMap("webservice-lock-store");
		store = SharedTribalMap.registerMap(store, false);
	}
	
	private void throwLocksExceededException(Access a, LockDefinition ld) throws LocksExceeded {
		VERSION.notifyAll(); // notify other waiting lock threads...
		LocksExceeded le = new LocksExceeded(ld);
		a.setException(le);
		throw le;
	}
	
	/**
	 * Return true is lock is set.
	 * Return false if no relevant lock could be found.
	 * Returns LocksExceeded if no entry allowed due to too many locks of certain type.
	 * 
	 */
	public final Lock addLock(Access a, LockDefinition ld, long waited) throws LocksExceeded {
		
		if ( a.getException() != null && a.getException() instanceof LocksExceeded) {
			throw (LocksExceeded) a.getException();
		}
		
		synchronized (VERSION) {
			Lock hl = getLock(a, ld);
			
			if ( hl != null ) {
				System.err.println("GOT LOCK: " + hl + ", instance count " + hl.instanceCount + ", max: " + ld.maxInstanceCount + ", wait timeout: " + ld.getWaitTimeOut());
				
				if ( hl.instanceCount >= ld.maxInstanceCount ) {
					if ( ld.getWaitTimeOut() != -1 && ( ld.getWaitTimeOut() == 0 || waited >= ld.getWaitTimeOut() ) ) {
						throwLocksExceededException(a, ld);
					} else {
						// Keep waiting...
						System.err.println("Waiting for lock to become available...");
						long startedWaiting = System.currentTimeMillis();
						try {
							if ( ld.getWaitTimeOut() == -1 ) {
								VERSION.wait();
							} else {
								VERSION.wait(ld.getWaitTimeOut());
							}
						} catch (InterruptedException e) {
						}
						waited += ( System.currentTimeMillis() - startedWaiting );
						System.err.println("Retrying lock after having waited for " + waited + " millis..");
						return addLock(a, ld, waited);
					}
				} else {
					hl.instanceCount++;
					// Store, update lock.
					updateLock(hl, a, ld);
					return hl;
				}
			}
		}
		
		return null;
		
	}

	private final Lock getNewLock(Access a, LockDefinition ld) {

		Lock nl = new Lock(ld.id, "(wspatterd="+ ld.webservice+",matchuser=" + ld.matchUsername + ",matchrequest="+ld.matchRequest+",maxinstancecount="+ld.maxInstanceCount+")");
		nl.instanceCount = 0;
		nl.webservice = a.rpcName;
		nl.username =  (ld.matchUsername ? a.rpcUser : null);
		nl.request = (ld.matchRequest ? a.getInDoc() : null);
		return nl;
		
	}
	
	private void updateLock(Lock l, Access a, LockDefinition ld) {
		if ( Pattern.matches( ld.webservice, a.rpcName ) ) {
			Set relevantLocks = (Set) store.get( new Integer(ld.id) );
			relevantLocks.add( l );
			store.put( new Integer(ld.id), relevantLocks );
		}
	}
	
	private final Lock getLock(Access a, LockDefinition ld) {
		
		// TODO: ONLY SUPPORT EXACT MATCH, HENCE 1 LOCK PER WEBSERVICE!!!!!!!!!! ELSE IT WILL BE TOO COMPLICATED..
		if ( Pattern.matches( ld.webservice, a.rpcName ) ) {
			
			Set relevantLocks = (Set) store.get( new Integer(ld.id) );
			
			if ( relevantLocks == null || relevantLocks.size() == 0) { // Create new lock set.
				Lock nl =  getNewLock(a, ld);
				if ( relevantLocks == null ) {
					relevantLocks = new HashSet();
				}
				relevantLocks.add( nl );
				store.put( new Integer(ld.id), relevantLocks );
				return nl;
			}
			
			
			// Username should match.
			Iterator all = relevantLocks.iterator();
			
			while ( all.hasNext() ) {
				
				Lock l = (Lock) all.next();
				
				if ( ld.matchUsername && l.username.equals(a.rpcUser) ) {
									
					if ( !ld.matchRequest ) { // Don't care about matching request.
						return l;
					} else if ( ld.matchRequest && matchRequest( l.request, a.getInDoc(), ld )  ) {
						return l;
					} else {
						// Create new lock for this lock set.
						Lock nl =  getNewLock(a, ld);
						relevantLocks.add( nl );
						return nl;
					}
					
				} else if ( ld.matchUsername && !l.username.equals(a.rpcUser ) ) {
					
					// Create new lock for this lock set.
					Lock nl =  getNewLock(a, ld);
					relevantLocks.add( nl );
					return nl;
					
				} else { // Don't care about matching username.
					
					if ( !ld.matchRequest ) { // Don't care about matching request.
						return l;
					} else if ( ld.matchRequest && matchRequest( l.request, a.getInDoc(), ld )  ) {
						return l;
					} else {
                        // Create new lock for this lock set.
						Lock nl =  getNewLock(a, ld);
						relevantLocks.add( nl );
						return nl;
					}	
				}
			}
		}
		
		return null;
	}

	public void removeAllLocks() {
		store.clear();
	}
	
	public final void removeLock(Access a, Lock l) {
		
		if ( l == null ) {
			return;
		}
		
		synchronized (VERSION) {
			l.instanceCount--;
			Set relevantLocks = (Set) store.get( new Integer(l.lockId) );
			if ( relevantLocks != null && l.instanceCount == 0 ) {
				relevantLocks.remove( l );
				if ( relevantLocks.size() == 0 ) {
					store.remove( new Integer(l.lockId) );
				}
			} else if ( relevantLocks != null  ){
				relevantLocks.add(l);
				store.put( new Integer(l.lockId), relevantLocks);
			}
			
			try {
				VERSION.notifyAll();
			} catch (Throwable t) {}
		}
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
		
		MemoryStore ms = new MemoryStore();
		boolean b = ms.matchRequest(n1, n2, null);
		System.err.println("b = " + b);
	}

	public final void reset() {
		store.clear();
	}

	public final Lock[] getAllLocks() {
		ArrayList lockList = new ArrayList();
		Iterator iter = store.values().iterator();
		while ( iter.hasNext() ) {
			Iterator relevantLocks = ((HashSet) iter.next()).iterator();
			while ( relevantLocks.hasNext() ) {
				Lock l = (Lock) relevantLocks.next();
				lockList.add(l.clone());
			}
		}
		Lock [] arrayList = new Lock[lockList.size()];
		return (Lock []) lockList.toArray(arrayList);
	}

	public int getStoreSize() {
		return store.size();
	}
}
