package com.dexels.navajo.tribe;

import java.io.Serializable;
import java.util.HashSet;

public class ClusterState implements Serializable {

	private static final long serialVersionUID = -6046777687660294098L;

	public String firstMember;
	public HashSet<TribeMember> clusterMembers = new HashSet<TribeMember>();


}
