package com.dexels.navajo.tribe;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

public class ClusterState implements Serializable {

	private static final long serialVersionUID = -6046777687660294098L;

	public String firstMember;
	public HashSet<TribeMember> clusterMembers = new HashSet<TribeMember>();

	/**
	 * Get the least busy tribe member.
	 * 
	 * @return
	 */
	public TribeMember getLeastBusyTribalMember() {

		Iterator<TribeMember> all = clusterMembers.iterator();
		double min = 999999.0;
		TribeMember lbtm = null;
		while ( all.hasNext() ) {
			TribeMember tm = all.next();
			if ( tm.getStatus().getCpuLoad() < min && !tm.getStatus().isBusy() ) {
				lbtm = tm;
			}
		}
		return lbtm;
	}

	
}
