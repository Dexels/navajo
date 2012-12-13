package com.dexels.navajo.server.enterprise.tribe;

import java.util.Set;

public interface ClusterStateInterface {

	public TribeMemberInterface getLeastBusyTribalMember();
	public Set<TribeMemberInterface> getClusterMembers();

}