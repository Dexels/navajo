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

import java.io.Serializable;

public class SharedStoreLock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6982466905008510851L;
	
	private int lockTimeOut = 10000; // in millis
	
	public SharedStoreLock(String name, String parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public String owner;
	public String parent;
	public String name;
	public int lockType;
	
	/**
	 * Returns a string representation of this SharedStoreLock.
	 */
	public String toString() {
		return "(" + owner + "," + parent + "," + name + ")";
	}

	public int getLockTimeOut() {
		return lockTimeOut;
	}

	/**
	 * Specify lock timeout in millis.
	 * Default value is 10000.
	 * 
	 * @param lockTimeOut
	 */
	public void setLockTimeOut(int lockTimeOut) {
		this.lockTimeOut = lockTimeOut;
	}
}
