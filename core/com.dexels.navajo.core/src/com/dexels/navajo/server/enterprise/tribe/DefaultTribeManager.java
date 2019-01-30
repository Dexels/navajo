package com.dexels.navajo.server.enterprise.tribe;

import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.enterprise.tribe.impl.SimpleTribalTopic;
import com.dexels.navajo.server.enterprise.tribe.impl.SimpleTribeMember;

public class DefaultTribeManager implements TribeManagerInterface {

//	private NavajoConfigInterface navajoConfig;

	
	private final static Logger logger = LoggerFactory.getLogger(DefaultTribeManager.class);

	private Map<String,Lock> mLocks = new ConcurrentHashMap<String,Lock>();
	private Map<String,TribalTopic> topics = new ConcurrentHashMap<String,TribalTopic>();
	private Map<String,TribalNumber> counters = new ConcurrentHashMap<String,TribalNumber>();
	private Map<String,Map> distributedMaps = new ConcurrentHashMap<String,Map>();
	private Map<String,Set> distributedSets = new ConcurrentHashMap<String,Set>();
	private ConfigurationAdmin configAdmin;
	private final Map<String, Configuration> resourcePids = new HashMap<String, Configuration>();
	
	@Override
	public void terminate() {
	}

	public DefaultTribeManager() {
//		if(!Version.osgiActive()) {
//			navajoConfig = DispatcherFactory.getInstance().getNavajoConfig();
//		}
	}
	
	public void activate(Map<String,Object> settings) {
		TribeManagerFactory.setInstance(this);
		becomeChief();
	}
	
	public void deactivate() {
		TribeManagerFactory.setInstance(null);
		noLongerChief();
	}
	
	
	public void setConfigAdmin(ConfigurationAdmin ca) {
		this.configAdmin = ca;
	}

	public void clearConfigAdmin(ConfigurationAdmin ca) {
		this.configAdmin = null;
	}

	@Override
	public Navajo forward(Navajo in,String tenant) throws Exception {
		return null;
	}

	@Override
	public void broadcast(Navajo in,String tenant) throws Exception {
		
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
		s.add(new SimpleTribeMember());
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
		return new SimpleTribeMember();
	}

	@Override
	public void multicast(Object[] recipients, SmokeSignal m) {
		
	}

	@Override
	public Answer askAnybody(Request q) {
		return null;
	}

	@Override
	public Navajo forward(Navajo in, Object address,String tenant) throws Exception {
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
			SimpleTribalTopic dt = new SimpleTribalTopic(name);
			topics.put(name, dt);
			return dt;
		}
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

	@Override
	public boolean isActive() {
		return true;
	}

    @Override
    public Object getMember(String id) {
        return new SimpleTribeMember();
    }

    @Override
    public boolean tribeIsSafe() {
        return true;
    }

	private void becomeChief() {
		try {
			Hashtable<String, Object> settings = new Hashtable<String,Object>();
			settings.put("cluster.owner", "local");
			emitFactoryIfChanged("navajo.cluster.chief","(cluster.implementation=local)",settings);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
	
	private void noLongerChief() {
		
		try {
			deleteFactoryPid("navajo.cluster.chief", "(&(service.factoryPid=navajo.cluster.chief)(cluster.implementation=local))");
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
	
	private void emitFactoryIfChanged(String factoryPid, String filter, Dictionary<String, Object> settings)
			throws IOException {
		updateIfChanged(createOrReuseFactoryConfiguration(factoryPid, filter), settings);
	}

	protected Configuration createOrReuseFactoryConfiguration(String factoryPid, final String filter)
			throws IOException {
		Configuration cc = null;
		try {
			Configuration[] c = configAdmin.listConfigurations(filter);
			if (c != null && c.length > 1) {
				logger.warn("Multiple configurations found for filter: {}", filter);
			}
			if (c != null && c.length > 0) {
				cc = c[0];
			}
		} catch (InvalidSyntaxException e) {
			logger.error("Error in filter: {}", filter, e);
		}
		if (cc == null) {
			cc = configAdmin.createFactoryConfiguration(factoryPid, null);
			resourcePids.put(cc.getPid(), cc);
		}
		return cc;
	}

	private void updateIfChanged(Configuration c, Dictionary<String, Object> settings) throws IOException {
		Dictionary<String, Object> old = c.getProperties();
		if (old != null) {
			if (!old.equals(settings)) {
				c.update(settings);
			} else {
				logger.info("Ignoring equal");
			}
		} else {
			// this will make this component 'own' this configuration, unsure if
			// this is desirable.
			resourcePids.put(c.getPid(), c);
			c.update(settings);
		}
	}
	
	private void deleteFactoryPid(String factoryPid, String filter) throws IOException {
//		"navajo.cluster.chief","(cluster.owner=hazelcast)"
		
		try {
			Configuration[] c = configAdmin.listConfigurations(filter);
			if (c == null || c.length == 0) {
				logger.info("No configurations found for filter: {}", filter);
				return;
			}
			for (Configuration configuration : c) {
				configuration.delete();
			}
		} catch (InvalidSyntaxException e) {
			logger.error("Error in filter: {}", filter, e);
		}

	}
}
