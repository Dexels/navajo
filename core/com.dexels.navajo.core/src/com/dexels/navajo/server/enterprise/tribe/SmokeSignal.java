/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

/**
 * A SmokeSignal is use the instantiate a process ( proccesMessage() ) on an member of the Navajo cluster.
 * The process can be parameterized by a key/value pair.
 * 
 * @author arjen
 *
 */
public abstract class SmokeSignal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1159623466118822005L;
	
	public final String sender;
	public final String key;
	public final Serializable value;
	
	public SmokeSignal(String key, Serializable value) {
		this.key = key;
		this.value = value;
		this.sender = TribeManagerFactory.getInstance().getMyUniqueId();
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}
	
	public String getSender() {
		return sender;
	}
	
	public boolean iAmTheSender() {
		return sender.equals(TribeManagerFactory.getInstance().getMyUniqueId());
	}
	
	/**
	 * Process the message.
	 * NOTE: A SmokeSignal message may or may not, depending on its semantics, be processed on the original sender
	 * of the broadcast message.
	 * 
	 * @param m
	 */
	public abstract void processMessage();
		
}
