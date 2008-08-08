package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

import com.dexels.navajo.server.Dispatcher;

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
	// ignoreRequestOnSender specifies whether the request should NEVER be performed on the server from  which the request originated.
	private boolean ignoreRequestOnSender = false;
	private Object recipient;
	
	public Request() {
		owner = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
		guid = hashCode() + "-" + System.currentTimeMillis();
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setPredefinedAnswer(Answer a) {
		predefined = a;
	}
	
	public Answer getPredefinedAnswer() {
		return predefined;
	}
	
	public abstract Answer getAnswer();

	public String getGuid() {
		return guid;
	}
	
	public boolean isBlocking() {
		return blocking;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	public boolean isIgnoreRequestOnSender() {
		return ignoreRequestOnSender;
	}

	public void setIgnoreRequestOnSender(boolean ignoreRequestOnSender) {
		this.ignoreRequestOnSender = ignoreRequestOnSender;
	}

	public Object getRecipient() {
		return recipient;
	}

	public void setRecipient(Object recipient) {
		this.recipient = recipient;
	}
}
