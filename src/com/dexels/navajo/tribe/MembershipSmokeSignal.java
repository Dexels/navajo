package com.dexels.navajo.tribe;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import com.dexels.navajo.scheduler.NavajoEventProxy;
import com.dexels.navajo.server.enterprise.tribe.SmokeSignal;
import com.dexels.navajo.sharedstore.map.IntroductionRequest;
import com.dexels.navajo.sharedstore.map.SharedTribalMap;
import com.dexels.navajo.util.AuditLog;

/**
 * The MembershipSmokeSignal is used by a new Tribal Member to introduce itself.
 * 
 * @author arjen
 *
 */
public class MembershipSmokeSignal extends SmokeSignal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4808643364226896337L;

	public static final String INTRODUCTION = "introduction";
	public static final String REMOVAL = "removal";
	
	public MembershipSmokeSignal(String sender, String key, Object value) {
		super(sender, key, value);
	}

	public void processMessage() {

		AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "MembershipSmokeSignal: PROCESS MESSAGE (" + getKey() + "/" + getValue() + ")");

		if ( getKey().equals(INTRODUCTION)  ) {
			TribeMember tm = (TribeMember) getValue();
			TribeManager.getInstance().addTribeMember(tm);
			
			/**
			 * The Chief is responsible to sharing the active 'tribal maps' by sending an IntroductionRequest
			 * to the new member.
			 * 
			 */
			if ( TribeManager.getInstance().getIsChief() ) {
				//System.err.println("ABOUT TO SHARE GLOBAL STATE............");
				// Share global state.
				Collection<SharedTribalMap> c = SharedTribalMap.getAllTribalMaps();
				Iterator<SharedTribalMap> iter = c.iterator();
				while ( iter.hasNext() ) {
					IntroductionRequest ir = new IntroductionRequest(iter.next());
					//System.err.println("SENDING TRIBALMAP TO NEW MEMBER...." + iter.next().getId());
					TribeManager.getInstance().askSomebody(ir, tm.getAddress());
				}
			}
			
		}
	}

}
