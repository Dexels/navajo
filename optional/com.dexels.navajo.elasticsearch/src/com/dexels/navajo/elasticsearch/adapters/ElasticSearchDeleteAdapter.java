package com.dexels.navajo.elasticsearch.adapters;

import java.io.IOException;

import com.dexels.navajo.elasticsearch.ElasticSearchDeleteFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ElasticSearchDeleteAdapter implements Mappable{
	
	private String id = null;
	
	
	@Override
	public void load(Access access)  {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void getResponse() throws MappableException, IOException{
		try {
			//the response that ES will show about the deleted file
			String es_response = ElasticSearchDeleteFactory.getInstance().delete_id(id);
			System.out.println("Response: " + es_response);
		}catch (IOException e){
			throw new MappableException("error " + e);
		}
	}

}
