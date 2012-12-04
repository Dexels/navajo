package com.dexels.navajo.server.enterprise.tribe;

import java.util.HashSet;
import java.util.Set;

import navajocore.Version;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;

public class DummyTribeManager implements TribeManagerInterface {

	private NavajoConfigInterface navajoConfig;

	public void terminate() {
	}

	public DummyTribeManager() {
		if(!Version.osgiActive()) {
			navajoConfig = DispatcherFactory.getInstance().getNavajoConfig();
		}
	}
	
	public Navajo forward(Navajo in) throws Exception {
		return null;
	}

	public void broadcast(Navajo in) throws Exception {
		
	}

	public void broadcast(SmokeSignal m) {

	}

	public void setNavajoConfig(NavajoConfigInterface nc) {
		this.navajoConfig = nc;
	}

	public void clearNavajoConfig(NavajoConfigInterface nc) {
		this.navajoConfig = null;
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

	public Set<TribeMemberInterface> getAllMembers() {
		Set<TribeMemberInterface> s =  new HashSet<TribeMemberInterface>();
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

	public String getStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

}
