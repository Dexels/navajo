package com.dexels.navajo.server.enterprise.tribe;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;

public interface TribeManagerInterface {

	public String getTribalId();
	
	/**
	 * Tribal Topic methods.
	 */
	public TribalTopic getTopic(String name);
	
	public Map getDistributedMap(String name);
	
	public Set getDistributedSet(String name);
	
	public TribalNumber getDistributedCounter(String name);
	
	/**
	 * Tribal locking methods.
	 * 
	 * @param name
	 * @return
	 */
	public Lock getLock(String name);
	
	public void releaseLock(Lock lock);
	
	
	/**
	 * Terminate the Tribal Membership.
	 */
	public void terminate();
	
	/**
	 * Forward a Navajo Request to another Tribal Member.
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public Navajo forward(Navajo in) throws Exception;
	
	/**
	 * Forward a Navajo Request to a SPECIFIC other Tribal Member.
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public Navajo forward(Navajo in, Object address) throws Exception;
	
	
	/**
	 * Broadcast a Navajo Request to ALL Tribal Members.
	 * @param in
	 * @throws Exception
	 */
	public void broadcast(Navajo in) throws Exception;
	
	/**
	 * Broadcast a SmokeSignal to ALL Tribal Members.
	 * 
	 * @param m
	 */
	public void broadcast(SmokeSignal m);
	
	/**
	 * Multicast a SmokeSignal to SOME Tribal Members.
	 *  
	 * @param recipients
	 * @param m
	 */
	public void multicast(Object [] recipients, SmokeSignal m);
	
	/**
	 * Returns true if I am the chief.
	 * 
	 * @return
	 */
	public boolean getIsChief();
	
	/**
	 * File a Request for the Chief.
	 * 
	 * @param q
	 * @return
	 */
	public Answer askChief(Request q);
	
	/**
	 * 
	 * @param service
	 * @param a
	 * @param ignoreTaskIds
	 */
	public void tribalAfterWebServiceRequest(String service, Access a, HashSet<String> ignoreTaskIds);
	
	/**
	 * 
	 * @param service
	 * @param a
	 * @param ignoreList
	 * @return
	 */
	public Navajo tribalBeforeWebServiceRequest(String service, Access a, HashSet<String> ignoreList);
	
	/**
	 * 
	 * @return
	 */
	public Set<TribeMemberInterface> getAllMembers();
	
	/**
	 * Ask a SPECIFIC Tribal Member something.
	 * 
	 * @param q
	 * @param address
	 * @return
	 */
    public Answer askSomebody(Request q, Object address);
    
    /**
     * Ask ANYBODY who knows the answer.
     * 
     * @param q
     * @return
     */
    public Answer askAnybody(Request q);
    
    /**
     * Returns my Tribal Characteristics.
     * 
     * @return
     */
    public TribeMemberInterface getMyMembership();
    
    /**
     * Add e new Tribe Member.
     * @param tm
     */
    public void addTribeMember(TribeMemberInterface tm);
	
    public String getMyName();
    
    public String getStatistics();

	public boolean isInitializing();

	public ClusterStateInterface getClusterState();

	public String getChiefName();

	String getMyUniqueId();
    
}
