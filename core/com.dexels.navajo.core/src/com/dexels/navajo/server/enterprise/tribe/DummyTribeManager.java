package com.dexels.navajo.server.enterprise.tribe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.DispatcherFactory;

public class DummyTribeManager implements TribeManagerInterface {

//	private NavajoConfigInterface navajoConfig;

	private Map<String,Lock> mLocks = new ConcurrentHashMap<String,Lock>();
	private Map<String,TribalTopic> topics = new ConcurrentHashMap<String,TribalTopic>();
	private Map<String,TribalNumber> counters = new ConcurrentHashMap<String,TribalNumber>();
	private Map<String,Map> distributedMaps = new ConcurrentHashMap<String,Map>();
	private Map<String,Set> distributedSets = new ConcurrentHashMap<String,Set>();
	
	@Override
	public void terminate() {
	}

	public DummyTribeManager() {
//		if(!Version.osgiActive()) {
//			navajoConfig = DispatcherFactory.getInstance().getNavajoConfig();
//		}
	}
	
	@Override
	public Navajo forward(Navajo in) throws Exception {
		return null;
	}

	@Override
	public void broadcast(Navajo in) throws Exception {
		
	}

	@Override
	public void broadcast(SmokeSignal m) {

	}

	/**
	 * Without a Tribe I am ALWAYS the chief!
	 */
	@Override
	public boolean getIsChief() {
		return true; //!!
	}

	@Override
	public Answer askChief(Request q) {
		return q.getAnswer();
	}

	@Override
	public Set<TribeMemberInterface> getAllMembers() {
		Set<TribeMemberInterface> s =  new HashSet<TribeMemberInterface>();
		s.add(new DummyTribeMemberImpl());
		return s;
	}
	
	 @Override
	public Answer askSomebody(Request q, Object address) {
			
			// If it's myself.
			if ( !q.isIgnoreRequestOnSender()) {
				return q.getAnswer();
			} else {
				return null;
			}
	 }

	@Override
	public TribeMemberInterface getMyMembership() {
		return new DummyTribeMemberImpl();
	}

	@Override
	public void multicast(Object[] recipients, SmokeSignal m) {
		
	}

	@Override
	public Answer askAnybody(Request q) {
		return null;
	}

	@Override
	public Navajo forward(Navajo in, Object address) throws Exception {
		return null;
	}

	@Override
	public void addTribeMember(TribeMemberInterface tm) {
		
	}

	@Override
	public String getMyName() {
		return "dummytribemember";
	}

	@Override
	public String getStatistics() {
		return null;
	}

	@Override
	public boolean isInitializing() {
		return false;
	}

	@Override
	public ClusterStateInterface getClusterState() {
		return null;
	}

	@Override
	public String getChiefName() {
		return "dummytribemember";
	}

	@Override
	public void tribalAfterWebServiceRequest(
			String service, Access a, HashSet<String> ignoreTaskIds) {
		
	}

	@Override
	public Navajo tribalBeforeWebServiceRequest(
			String service, Access a, HashSet<String> ignoreList) {
		return null;
	}

	@Override
	public String getMyUniqueId() {
		return "dummytribemember";
	}

	@Override
	public synchronized Lock getLock(String name) {
		Lock l = mLocks.get(name);
		if (l == null ) {
			l = new ReentrantLock();
			mLocks.put(name, l);
		}
		return l;
	}

	@Override
	public synchronized void releaseLock(Lock lock) {
		if ( lock != null ) {
			lock.unlock();
			Iterator<String> all = mLocks.keySet().iterator();
			String found = null;
			while ( all.hasNext() ) {
				String s = all.next();
				if ( mLocks.get(s).equals(lock) ) {
					found = s;
					break;
				}
			}
			if ( found != null ) {
				mLocks.remove(found);
			}
		}
	}

	@Override
	public synchronized TribalTopic getTopic(String name) {
		if ( topics.containsKey(name) ) {
			return topics.get(name);
		} else {
			DummyTopic dt = new DummyTopic();
			topics.put(name, dt);
			return dt;
		}
	}

	@Override
	public String getTribalId() {
		return DispatcherFactory.getInstance().getNavajoConfig().getInstanceGroup();
	}

	@Override
	public synchronized Map getDistributedMap(String name) {
		if ( distributedMaps.get(name) != null ) {
			return distributedMaps.get(name);
		}
		Map m = new ConcurrentHashMap();
		distributedMaps.put(name, m);
		return m;
	}

	@Override
	public synchronized TribalNumber getDistributedCounter(String name) {
		if ( counters.get(name) != null ) {
			return counters.get(name);
		}
		TribalNumber tn = new DefaultTribalNumber();
		counters.put(name, tn);
		return tn;
	}

	@Override
	public synchronized Set getDistributedSet(String name) {
		if ( distributedSets.get(name) != null ) {
			return distributedSets.get(name);
		}
		Set s = Collections.newSetFromMap(new ConcurrentHashMap());
		distributedSets.put(name, s);
		return s;
	}

	@Override
	public List<TribalNumber> getDistributedCounters() {
		// TODO Auto-generated method stub
		return null;
	}

}
