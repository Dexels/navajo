package com.dexels.navajo.server.enterprise.tribe;

import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;

public interface TribeManagerInterface {

	public void terminate();
	public Navajo forward(Navajo in) throws Exception;
	public void broadcast(Navajo in) throws Exception;
	public void broadcast(SmokeSignal m);
	public boolean getIsChief();
	public Answer askChief(Request q);
	public void tribalAfterWebServiceRequest(String service, Access a, HashSet<String> ignoreTaskIds);
	public Navajo tribalBeforeWebServiceRequest(String service, Access a, HashSet<String> ignoreList);
	public Set getAllMembers();
    public Answer askSomebody(Request q, Object address);
    public TribeMemberInterface getMyMembership();
	
}
