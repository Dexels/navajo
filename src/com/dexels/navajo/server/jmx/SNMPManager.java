package com.dexels.navajo.server.jmx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class SNMPManager implements CompositeData {

	public static final String HOSTNAME = "hostname";
	public static final String PORT = "port";
	public static final String SNMPVERSION = "snmpversion";
	
	public static final String V1 = "V1";
	public static final String V2 = "V2";
	
	@SuppressWarnings("unchecked")
	private HashMap items = new HashMap();
	
	@SuppressWarnings("unchecked")
	public SNMPManager (String value) {

		String host = "localhost";
		String port = "1024";
		String version = "V2";

		StringTokenizer st2 = new StringTokenizer(value, ":");
		if ( st2.hasMoreTokens() ) {
			host = st2.nextToken();
		}
		if ( st2.hasMoreTokens() ) {
			port = st2.nextToken();
		}
		if ( st2.hasMoreTokens() ) {
			version = st2.nextToken();
		}

		items.put(HOSTNAME, host);
		items.put(PORT, new Integer(port));
		items.put(SNMPVERSION, version);
	}
	
	@SuppressWarnings("unchecked")
	public SNMPManager (String host, int port, String version) {
		items.put(HOSTNAME, host);
		items.put(PORT, new Integer(port));
		items.put(SNMPVERSION, version);
	}
	
	public String getHost() {
		return (String) items.get(HOSTNAME);
	}
	
	public int getPort() {
		return ((Integer) items.get(PORT)).intValue();
	}
	
	public String getSnmpVersion() {
		return (String) items.get(SNMPVERSION);
	}
	
	public boolean containsKey(String key) {
		return items.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return items.containsValue(value);
	}

	public Object get(String key) {
		return items.get(key);
	}

	@SuppressWarnings("unchecked")
	public Object[] getAll(String[] keys) {
		ArrayList l = new ArrayList();
		for (int i = 0; i < keys.length; i++) {
			l.add(items.get(keys[i]));
		}
		Object [] o = new Object[l.size()];
		o = l.toArray(o);
		return o;
	}

	public CompositeType getCompositeType() {
		
		CompositeType ct = null;
		try {
			ct = new CompositeType(
					"SNMPMananger",
					"SNMP Manager connection data", 
					new String[]{HOSTNAME, PORT, SNMPVERSION}, 
					new String[]{"SNMP Manager hostname", "SNMP Manager port", "SNMP Version: V1 or V2"},
					new OpenType[]{SimpleType.STRING, SimpleType.INTEGER, SimpleType.STRING}
					);
		} catch (OpenDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ct;
	}

	@SuppressWarnings("unchecked")
	public Collection values() {
		return items.values();
	}
	
}
