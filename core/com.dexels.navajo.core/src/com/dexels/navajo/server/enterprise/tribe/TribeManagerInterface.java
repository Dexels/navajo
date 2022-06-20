/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.tribe;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;

@SuppressWarnings("rawtypes")
public interface TribeManagerInterface {
    public static final String MEMORY_MAP_KEY = "navajo_memory_map";
    public static final String MEMORY_MAP_EXP_KEY = "navajo_memory_map_exp";

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
	public Navajo forward(Navajo in, String tenant) throws Exception;
	
	/**
	 * Forward a Navajo Request to a SPECIFIC other Tribal Member.
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public Navajo forward(Navajo in, Object address, String tenant) throws Exception;
	
	
	/**
	 * Broadcast a Navajo Request to ALL Tribal Members.
	 * @param in
	 * @throws Exception
	 */
	public void broadcast(Navajo in, String tenant) throws Exception;
	
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
	public void tribalAfterWebServiceRequest(String service, Access a, Set<String> ignoreTaskIds);
	
	/**
	 * 
	 * @param service
	 * @param a
	 * @param ignoreList
	 * @return
	 */
	public Navajo tribalBeforeWebServiceRequest(String service, Access a, Set<String> ignoreList);
	
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
	
	public Object getMember(String id);

	List<TribalNumber> getDistributedCounters();
    
	public boolean isActive();
	
	/** 
	 * Indicates whether all data is properly migrated and backup up amongst the members
	 */
	public boolean tribeIsSafe();
}
