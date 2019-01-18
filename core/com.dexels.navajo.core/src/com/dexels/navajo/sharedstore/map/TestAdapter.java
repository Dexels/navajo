package com.dexels.navajo.sharedstore.map;

import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

@SuppressWarnings("rawtypes")

public class TestAdapter implements Mappable {

	public String id;
	public String key;
	public String value;
	public String values;
	public boolean remove;
	public boolean clear;
	
	public static HashSet preventCollection = new HashSet();
	
	private final static Logger logger = LoggerFactory.getLogger(TestAdapter.class);

	@Override
	public void kill() {
	}
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		
	}
	
	@Override
	public void store() throws MappableException, UserException {
		if ( value != null ) {
			SharedTribalMap stm = SharedTribalMap.getMap(id);
			//preventCollection.add(stm);
			
			if ( stm == null ) {
				stm = new SharedTribalMap(id);
				SharedTribalMap.registerMap(stm, true);	
			}
			
			stm.put(key, new RemoteReference( value ) );
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
			RemoteReference rr = (RemoteReference) stm.get(key);
			return (String) rr.getObject();
		} else {
			logger.warn("Could not find sharedtribalmap with id: " + id);
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
	
	public void setRemove(boolean b) {
		if ( b ) {
			SharedTribalMap stm = SharedTribalMap.getMap(id);
			stm.remove(key);
		}
	}
	
	public String getValues() {
		SharedTribalMap stm = SharedTribalMap.getMap(id);
		if ( stm.values() != null ) {
			StringBuffer sb = new StringBuffer();
			Iterator iter = stm.values().iterator();
			while ( iter.hasNext() ) {
				RemoteReference rr = (RemoteReference) iter.next();
				sb.append(rr.getObject() + ",");
			}
			return sb.toString();
		} else {
			return null;
		}
	}
	
	/**
	 * @param b clear 
	 */
	public void setClear(boolean b) {
		SharedTribalMap stm = SharedTribalMap.getMap(id);
		SharedTribalMap.deregisterMap(stm);
	}
	
}
