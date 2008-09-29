package com.dexels.navajo.server.enterprise.tribe;

import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;

public class DummyTribeManager implements TribeManagerInterface {

	public void terminate() {
	}

	public Navajo forward(Navajo in) throws Exception {
		return null;
	}

	public void broadcast(Navajo in) throws Exception {
		
	}

	public void broadcast(SmokeSignal m) {

	}

	/**
	 * Without a Tribe I am ALWAYS the chief!
	 */
	public boolean getIsChief() {
		return true; //!!
	}

	public Answer askChief(Request q) {
		return q.getAnswer();
	}

	public void tribalAfterWebServiceRequest(String service, Access a,
			HashSet<String> ignoreTaskIds) {
		// TODO Auto-generated method stub
		
	}

	public Navajo tribalBeforeWebServiceRequest(String service, Access a,
			HashSet<String> ignoreList) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getAllMembers() {
		HashSet s =  new HashSet();
		s.add(new TribeMemberImpl());
		return s;
	}
	
	 public Answer askSomebody(Request q, Object address) {
			
			// If it's myself.
			if ( !q.isIgnoreRequestOnSender()) {
				return q.getAnswer();
			} else {
				return null;
			}
	 }

	public TribeMemberInterface getMyMembership() {
		return new TribeMemberImpl();
	}

	public void multicast(Object[] recipients, SmokeSignal m) {
		// TODO Auto-generated method stub
		
	}

	public Answer askAnybody(Request q) {
		// TODO Auto-generated method stub
		return null;
	}

	public Navajo forward(Navajo in, Object address) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void addTribeMember(TribeMemberInterface tm) {
		// TODO Auto-generated method stub
		
	}

	public String getMyName() {
		return DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
	}

}
