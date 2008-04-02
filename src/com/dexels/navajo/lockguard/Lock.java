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

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public final class Lock implements Mappable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 847273830792579002L;

	public static final String VERSION = "$Id$";
	
	public int lockId;
	public String lockSignature;
	public int instanceCount;
	public String username;
	public String webservice;
	public Navajo request;
	
	public Lock( int id, String lockSignature ) {
		lockId = id;
		this.lockSignature = lockSignature;
	}

	public int getLockId() {
		return lockId;
	}
	
	public int getInstanceCount() {
		return instanceCount;
	}
	
	public String getWebservice() {
		return this.webservice;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getLockSignature() {
		return this.lockSignature;
	}
	
	public Object clone() {
		Lock copy = new Lock( this.lockId, this.lockSignature );
		copy.instanceCount = this.instanceCount;
		copy.username = this.username;
		copy.webservice = this.webservice;
		return copy;
	}
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}
	
	public void store() throws MappableException, UserException {
	}
	
	public void kill() {
	}

}
