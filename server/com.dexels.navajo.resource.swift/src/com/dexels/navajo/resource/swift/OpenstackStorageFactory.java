package com.dexels.navajo.resource.swift;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenstackStorageFactory {
	
	private static OpenstackStorageFactory instance = null;
	
	private final Map<String,OpenstackStore> defaultStores = new HashMap<>();
	private final Map<String,Map<String,OpenstackStore>> tenantStores = new HashMap<>();
	
	private final static Logger logger = LoggerFactory.getLogger(OpenstackStorageFactory.class);
	
	public void activate() {
		instance = this;
	}

	public void deactivate() {
		instance = null;
	}
	
	public static OpenstackStorageFactory getInstance() {
		return instance;
	}

	public void addOpenstackStore(OpenstackStore store, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		String tenant = (String) settings.get("instance");
		logger.info("Adding Openstack store name: {} for tenant {}",name,tenant);
		logger.info("");
		if(tenant==null) {
			defaultStores.put(name, store);
			return;
		}
		Map<String,OpenstackStore> myTenantStore = tenantStores.get(tenant);
		if(myTenantStore==null) {
			myTenantStore = new HashMap<>();
			tenantStores.put(tenant, myTenantStore);
		}
		myTenantStore.put(name, store);
	}
	
	public void removeOpenstackStore(OpenstackStore store, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		String tenant = (String) settings.get("tenant");
		if(tenant==null) {
			defaultStores.remove(name);
			return;
		}
		Map<String,OpenstackStore> myTenantStore = tenantStores.get(tenant);
		if(myTenantStore==null) {
			myTenantStore = new HashMap<>();
			tenantStores.put(tenant, myTenantStore);
		}
		myTenantStore.remove(name);
	}

	public OpenstackStore getOpenstackStore(String name, String tenant) {
		Map<String,OpenstackStore> myTenantStore = tenantStores.get(tenant);
		if(myTenantStore!=null) {
			OpenstackStore os = myTenantStore.get(name);
			if(os!=null) {
				return os;
			}
		}
		return defaultStores.get(name);
	}

	
	
}

