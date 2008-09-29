/**
 * Title:        Navajo<p>
 * Description:  This file is part of the Navajo Service Oriented Application Framework<p>
 * Copyright:    Copyright 2002-2008 (c) Dexels BV<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
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
package com.dexels.navajo.sharedstore;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

/**
 * A LockQuestion is a request for a lock.
 * Also implement LockReleaseRequest...
 * 
 * @author arjen
 *
 */
public class GetLockRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5986028260014271881L;
	
	public String parent;
	public String name;
	public String owner;
	int lockType;
	private boolean block;
	
	private int lockTimeOut = 30000; // in millis
	
	public GetLockRequest(String parent, String name, int lockType, boolean block) {
		super();
		this.parent = parent;
		this.name = name;
		this.lockType = lockType;
		this.block = block;
		this.owner = DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
		//System.err.println("TRYING TO GET LOCK FOR: " + parent + "/" + name + " (type = " + lockType + ", block = " + block + ")");
	}
	
	public Answer getAnswer() {
		
		SharedStoreInterface ssi = SharedStoreFactory.getInstance();
		SharedStoreLock ssl = ssi.lock(parent, name, owner, lockType, block);
		
		return new LockAnswer(this, ssl);
		
	}

	public int getLockTimeOut() {
		return lockTimeOut;
	}

	public void setLockTimeOut(int lockTimeOut) {
		this.lockTimeOut = lockTimeOut;
	}
	
}
