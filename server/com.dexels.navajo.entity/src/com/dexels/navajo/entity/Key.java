package com.dexels.navajo.entity;

import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class Key {

	private Set<Property> myKey = new HashSet<Property>();
	private final Entity myEntity;
	private final String id;
	
	public Key(String id, Entity e) {
		this.id = id;
		myEntity = e;
	}
	
	protected void addProperty(Property p) {
		myKey.add(p);
	}
	
	public String getId() {
		return id;
	}
	
	public Navajo generateRequestMessage() {
		return generateRequestMessage(null);
	}
	
	/**
	 * Generate a request message based upon a Navajo input.
	 * The request message will only containing the defined properties in the Key with filled in values from the input Navajo.
	 * 
	 * @param input
	 * @return
	 */
	public Navajo generateRequestMessage(Navajo input) {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, myEntity.getName());
		n.addMessage(m);
		// Copy properties.
		for ( Property p : myKey ) {
			Property copy = p.copy(n);
			m.addProperty(copy);
			if ( input != null ) {
				Property ip = input.getProperty(p.getFullPropertyName());
				if ( ip != null ) {
					copy.setUnCheckedStringAsValue(ip.getValue());
				}
			}
		}
		return n;
	}
	
	private boolean propertyMatch(Property p1, Property p2) {
	
		if ( p1.getName().equals(p2.getName()) ) {
			if ( p1.getType().equals(p2.getType() ) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean keyMatch(Set<Property> input) {
		
		for ( Property p : myKey ) {
			if ( p.getKey().indexOf("optional") == -1 ) {
				// Find property in input.
				boolean foundProp = false;
				for ( Property ip : input ) {
					if ( propertyMatch(p, ip) ) {
						foundProp = true;
						break;
					}
				}
				if (!foundProp) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Set<Property> getKeyProperties() {
		return myKey;
	}
	
	public static boolean isAutoKey(String key) {
		return ( key != null && key.indexOf("auto") != -1 );
	}
	
	public static boolean isOptionalKey(String key) {
		return ( key != null && key.indexOf("optional") != -1 );
	}
	
	public static String getKeyId(String key) {
		String [] keyValues = key.split(",");
		for ( int i = 0; i < keyValues.length; i++ ) {
			if ( keyValues[i].indexOf("id=") != -1 ) {
				String keyId = keyValues[i].split("=")[1];
				return keyId;
			}
		}
		return null;
	}
}
