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
import java.util.HashSet;
import java.util.Iterator;

import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

/**
 * The ClusterState contains the state of the Tribal Members for a Navajo Instance.
 * 
 * @author arjen
 *
 */
public class ClusterState implements Serializable {

	private static final long serialVersionUID = -6046777687660294098L;

	public String firstMember;
	public HashSet<TribeMember> clusterMembers = new HashSet<TribeMember>();
	
	/**
	 * Get the least busy tribe member.
	 * 
	 * @return
	 */
	public TribeMember getLeastBusyTribalMember() {

		Iterator<TribeMember> all = clusterMembers.iterator();
		double min = 999999.0;
		TribeMember lbtm = null;
		while ( all.hasNext() ) {
			TribeMember tm = all.next();
			if ( tm != null && tm.getStatus() != null && tm.getStatus().getCpuLoad() < min && !tm.getStatus().isBusy() && 
					!tm.getAddress().equals( TribeManagerFactory.getInstance().getMyMembership().getAddress() ) ) {
				lbtm = tm;
			}
		}
		return lbtm;
	}

	

	
}
