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

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;

public abstract class LockStore {

	public static final String VERSION = "$Id$";
	
	private static LockStore instance = null;
	
	public static LockStore getStore() {
		if ( instance == null ) {
			instance = new MemoryStore();
		}
		return instance;
	}
	
	public abstract void reset();
	public abstract Lock addLock(Access a, LockDefinition ld) throws LocksExceeded ;
	public abstract void removeLock(Access a, Lock l);
	public abstract Lock [] getAllLocks();
	
	public boolean matchRequest( Navajo myRequest, Navajo otherRequest, LockDefinition ld ) {
		return matches(myRequest, otherRequest, ld) && matches(otherRequest, myRequest, ld);
	}
	
	private boolean matches( Navajo myRequest, Navajo otherRequest, LockDefinition ld) {
		
		try {
			ArrayList allMessages = myRequest.getAllMessages();
			
			for (int i = 0; i < allMessages.size(); i++) {
				Message m = (Message) allMessages.get(i);
				if ( !( m.getName().equals("__parms__") || 
						m.getName().equals("__globals__")  )) {
					String excludeProperties = null;
					
					if ( ld.excludeProperties.get( m.getName() ) != null ) {
						excludeProperties =  (String) ld.excludeProperties.get( m.getName() );
					}
					System.err.println("excludeProperties = " + excludeProperties);
					
					if ( excludeProperties == null || !excludeProperties.equals("*") ) {
						if ( otherRequest.getMessage( m.getName() ) == null || 
								!m.isEqual( otherRequest.getMessage( m.getName() ), 
										(excludeProperties != null ? excludeProperties : "") ) ) {
							return false;
						}
					}
				}
			}
			return true;
			
		} catch (Exception e) {
			
			return false;
		}
	}
}
