package com.dexels.navajo.elasticsearch.adapters;

import java.io.IOException;

import com.dexels.navajo.elasticsearch.ElasticSearchDeleteFactory;
import com.dexels.navajo.elasticsearch.impl.ElasticSearchDeleteResult;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ElasticSearchDeleteAdapter implements Mappable {
	
	private String id = null;
	//public ElasticSearchDeleteResult result;
	public ElasticSearchDeleteResult result;

	@Override
	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() throws MappableException, UserException {
		/*// TODO Auto-generated method stub
		try {
			ElasticSearchDeleteFactory.getInstance().delete(id);
		}catch (IOException e) {
			throw new MappableException("error" + e);
		}
		*/
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public ElasticSearchDeleteResult getEsresult () throws MappableException {
		try {
			result = ElasticSearchDeleteFactory.getInstance().delete(id);
			System.out.println("getRes return");
		} catch (IOException e) {
			throw new MappableException ("error" + e);
		}
		return result;
	}

}
