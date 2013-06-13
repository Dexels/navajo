package com.dexels.navajo.util.navadoc;

import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.UserException;

public class NavaDocMap implements Mappable{

	public String configUri = null;
	
	public void kill() {
		
	}

	
	public void store() throws MappableException, UserException {
  	System.setProperty("configUri", configUri);
		NavaDoc documenter = null;
		try {
		  documenter = new NavaDoc(); 
		}catch(Exception e){
			throw new MappableException(e.getMessage());
	  } finally {
		  if ( documenter != null && documenter.getTempStyleSheet() != null ) {
			  documenter.getTempStyleSheet().delete();
		  }
	  }
	}
	
	public void setConfigUri(String value){
		this.configUri = value;
	}
	
	public String getConfigUri(){
		return this.configUri;		
	}

	public void load(Access access) throws MappableException, UserException {
		
	}

}
