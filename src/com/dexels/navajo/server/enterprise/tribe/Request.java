package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;

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
public abstract class Request implements Serializable {

	public String owner = null;
	public Answer predefined = null;
	private String guid = null;
	protected boolean blocking = true;
	protected long timeout = -1;
	// ignoreRequestOnSender specifies whether the request should NEVER be performed on the server from  which the request originated.
	private boolean ignoreRequestOnSender = false;
	private Object recipient;
	
	public Request() {
		owner = DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
		guid = hashCode() + "-" + System.currentTimeMillis();
	}
	
	/**
	 * Gets the original sender/creator of this request.
	 * 
	 * @return
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * This method is used by the Tribal Manager to inject the received answer of the recipient in
	 * the original request.
	 * 
	 * @param a
	 */
	public void setPredefinedAnswer(Answer a) {
		predefined = a;
	}
	
	/**
	 * The sender of the request can use this method to get the supplied answer of the recipient.
	 * 
	 * @return
	 */
	public Answer getPredefinedAnswer() {
		return predefined;
	}
	
	/**
	 * This method MUST BE called by the recipient of this request.
	 * 
	 * @return
	 */
	public abstract Answer getAnswer();

	/**
	 * Get the unique GUID associated with this request.
	 * 
	 * @return
	 */
	public String getGuid() {
		return guid;
	}
	
	/**
	 * Specifies whether this request is either blocking or non-blocking.
	 * 
	 * @return
	 */
	public boolean isBlocking() {
		return blocking;
	}

	/**
	 * Use to set request either in blocking or non-blocking mode. Blocking means wait for the answer.
	 * 
	 * @param blocking
	 */
	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	/**
	 * Specifies whether or not to ignore processing this request on the originator.
	 * 
	 * @return
	 */
	public boolean isIgnoreRequestOnSender() {
		return ignoreRequestOnSender;
	}

	/**
	 * Specify whether or not to ignore processing this request on the originator.
	 * 
	 * @param ignoreRequestOnSender
	 */
	public void setIgnoreRequestOnSender(boolean ignoreRequestOnSender) {
		this.ignoreRequestOnSender = ignoreRequestOnSender;
	}

	/**
	 * Gets the address of the recipient.
	 * 
	 * @return
	 */
	public Object getRecipient() {
		return recipient;
	}

	/**
	 * Sets the address of the recipient of this request.
	 * 
	 * @param recipient
	 */
	public void setRecipient(Object recipient) {
		this.recipient = recipient;
	}

	/**
	 * Get the timeout in millis that specifies the maximum amount of
	 * time the request should wait for the answer (assuming blocking = true!).
	 * 
	 * @param timeout
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * Set the timeout in millis to specify the maximum amount of
	 * time the request should wait for the answer (assuming blocking = true!).
	 * 
	 * @param timeout
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
