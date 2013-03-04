package com.dexels.navajo.server.enterprise.tribe;

import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerRegistryInterface;

public class DummyTribeManager implements TribeManagerInterface {

//	private NavajoConfigInterface navajoConfig;

	public void terminate() {
	}

	public DummyTribeManager() {
//		if(!Version.osgiActive()) {
//			navajoConfig = DispatcherFactory.getInstance().getNavajoConfig();
//		}
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

	public Set<TribeMemberInterface> getAllMembers() {
		Set<TribeMemberInterface> s =  new HashSet<TribeMemberInterface>();
		s.add(new DummyTribeMemberImpl());
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
		return new DummyTribeMemberImpl();
	}

	public void multicast(Object[] recipients, SmokeSignal m) {
		
	}

	public Answer askAnybody(Request q) {
		return null;
	}

	public Navajo forward(Navajo in, Object address) throws Exception {
		return null;
	}

	public void addTribeMember(TribeMemberInterface tm) {
		
	}

	public String getMyName() {
		return "dummytribemember";
	}

	public String getStatistics() {
		return null;
	}

	@Override
	public boolean isInitializing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClusterStateInterface getClusterState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getChiefName() {
		// TODO Auto-generated method stub
		return "dummytribemember";
	}

	@Override
	public void tribalAfterWebServiceRequest(
			String service, Access a, HashSet<String> ignoreTaskIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Navajo tribalBeforeWebServiceRequest(
			String service, Access a, HashSet<String> ignoreList) {
		// TODO Auto-generated method stub
		return null;
	}

}
