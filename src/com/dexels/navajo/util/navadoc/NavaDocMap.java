package com.dexels.navajo.util.navadoc;

import java.io.FileNotFoundException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class NavaDocMap implements Mappable{

	public String configUri = null;
	
	public void kill() {
		// TODO Auto-generated method stub
		
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
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

}
