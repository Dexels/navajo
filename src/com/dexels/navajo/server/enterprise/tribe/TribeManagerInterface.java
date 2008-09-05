package com.dexels.navajo.server.enterprise.tribe;

import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;

public interface TribeManagerInterface {

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
	public Set getAllMembers();
	
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
	
}
