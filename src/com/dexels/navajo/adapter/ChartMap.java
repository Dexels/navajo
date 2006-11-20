package com.dexels.navajo.adapter;

import com.dexels.navajo.document.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;

public class ChartMap implements Mappable{
	
	public ChartMap(){
		
	}

	public void kill() {

		
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		try{
			inMessage.write(System.err);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}
	
}