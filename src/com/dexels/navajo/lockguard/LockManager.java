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
import com.dexels.navajo.server.Access;

public class LockManager {

	public static final String VERSION = "$Id$";
	
	static Map lockDefinitions = Collections.synchronizedMap( new HashMap() );
	static LockManager instance = null;
	
	private void readDefinitions() {
		LockDefinition ld = new LockDefinition(".*", false, false, null, 0, 75);
		lockDefinitions.put( new Integer(ld.id), ld);
		HashMap excludeMessages = new HashMap();
		excludeMessages.put("Aap", "Mies");
		LockDefinition ld2 = new LockDefinition("ProcessWhatever", false, true, excludeMessages, 0, 1);
		lockDefinitions.put( new Integer(ld2.id), ld2);
	}
	
	public static LockManager getInstance() {
		if ( instance == null ) {
			instance = new LockManager();
			instance.readDefinitions();
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
		Access a2 = new Access(2, 2, 2, "pipo", "ProcessWhatever", "", "", "", null);
		a2.setInDoc(n2);
		Lock [] locks2 = lm.grantAccess( a2 );
		
		if ( locks2 != null ) {
			for (int i = 0 ; i < locks2.length; i++ ) {
				System.err.println(locks2[i]);
			}
		}
	}
}
