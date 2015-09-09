package com.dexels.navajo.elasticsearch.adapters;

import java.io.IOException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.elasticsearch.ElasticSearchFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ElasticSearchAdapter implements Mappable {

	private String name = null;
	private String messagePath = null;
	private Access access = null;
	
	public void setMessagePath(String messagePath) {
		this.messagePath = messagePath;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		this.access = access;
	}

	@Override
	public void store() throws MappableException, UserException {
		Message m = access.getInDoc().getMessage(messagePath);
		try {
			ElasticSearchFactory.getInstance().insert(m);
		} catch (IOException e) {
			throw new UserException("Trouble pushing to elasticsearch", e);
		}
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
}
