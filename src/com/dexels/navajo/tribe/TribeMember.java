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
package com.dexels.navajo.tribe;

import java.io.Serializable;
import java.util.Date;

import org.jgroups.Address;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.tribe.TribeMemberInterface;

public class TribeMember implements Serializable, Mappable, TribeMemberInterface {

	private static final long serialVersionUID = -1371503985787191894L;
	
	public String memberName;
	private Address address;
	public boolean isChief;
	public Date joinDate;
	public PingAnswer status;
	
	public TribeMember(String s, Address a) {
		this.memberName = s;
		this.address = a;
		this.joinDate = new Date();
	}
	
	public String getMemberName() {
		return memberName;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public boolean getIsChief() {
		return isChief;
	}
	
	public boolean isChief() {
		return isChief;
	}
	
	public void setChief(boolean b) {
		isChief = b;
	}

	public Date getJoinDate() {
		return (Date) joinDate.clone();
	}

	public PingAnswer getStatus() {
		return status;
	}

	public void setStatus(PingAnswer status) {
		this.status = status;
	}

	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

}
