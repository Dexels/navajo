package com.dexels.navajo.elasticsearch;

import java.io.IOException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ElasticSearchAdapter implements Mappable {

	@Override
	public void load(Access access) throws MappableException, UserException {
		Message m = null;
		try {
			ElasticSearchFactory.getInstance().insert(m);
		} catch (IOException e) {
			throw new UserException("Trouble pushing to elasticsearch", e);
		}
	}

	@Override
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
}
