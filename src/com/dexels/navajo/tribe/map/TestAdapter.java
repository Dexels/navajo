package com.dexels.navajo.tribe.map;

import java.util.HashSet;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class TestAdapter implements Mappable {

	public String id;
	public String key;
	public String value;
	public String values;
	
	public static HashSet preventCollection = new HashSet();
	
	public void kill() {
	}
	
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		
	}
	
	public void store() throws MappableException, UserException {
		if ( value != null ) {
			SharedTribalMap stm = SharedTribalMap.getMap(id);
			if ( stm == null ) {
				stm = new SharedTribalMap(id);
				preventCollection.add(stm);
			}
			System.err.println("SharedTribalMap( " + id + "), key = " + key + ", value = " + value);
			stm.put(key, value);
		}
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		SharedTribalMap stm = SharedTribalMap.getMap(id);
		if ( stm != null ) {
			return (String) stm.get(key);
		} else {
			System.err.println("Could not find sharedtribalmap with id: " + id);
			return null;
		}
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getValues() {
		SharedTribalMap stm = SharedTribalMap.getMap(id);
		return stm.values().toString();
	}
	
	
}
