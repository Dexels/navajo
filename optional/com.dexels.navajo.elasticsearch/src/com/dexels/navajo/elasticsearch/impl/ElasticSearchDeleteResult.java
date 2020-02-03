package com.dexels.navajo.elasticsearch.impl;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ElasticSearchDeleteResult implements Mappable {
	public final String id;
	public final String resultm;
	
	
	public ElasticSearchDeleteResult (String id, String resultm) {
		this.id = id;
		this.resultm = resultm;
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
	
	public String getId() {
		return id;
	}
	
	public String getResultMessage() {
		return resultm;
	}

}
