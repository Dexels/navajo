package com.dexels.navajo.tenant.impl;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.tenant.TenantConfig;

public class EnvTenantConfig implements TenantConfig {

	private NavajoIOConfig config;
	private final Set<String> tenants = new HashSet<>();
	private final Set<String> mastertenants = new HashSet<>();
	
	private boolean wildcard = false;
	
	private static final Logger logger = LoggerFactory
			.getLogger(EnvTenantConfig.class);
	
	@Override
	public boolean isMasterForTenant(String tenant) {
		if(wildcard) {
			return true;
		}
		return mastertenants.contains(tenant);
	}

	@Override
	public Set<String> isMasterForTenants() {
		if(wildcard) {
			return Collections.unmodifiableSet(tenants);
		}
		return Collections.unmodifiableSet(mastertenants);
	}

	@Override
	public Set<String> getTenants() {
		return Collections.unmodifiableSet(tenants);
	}
	
	public void activate() {
		logger.info("Activate env-based tenant config");
		File root = new File(config.getRootPath());
		File settings = new File(root,"settings");
		if(!settings.exists()) {
			logger.warn("No tenant settings found, not injecting tenant config");
			return;
		}
		for(File f : settings.listFiles()) {
			if(f.isDirectory()) {
				tenants.add(f.getName());
			}
		}
		mastertenants.clear();
		mastertenants.addAll(parseTenantMasters());
	}

	private Set<String> parseTenantMasters() {
		Set<String> masters = new HashSet<>();
		String tenantlist = System.getenv("TENANT_MASTER");
		if (tenantlist == null) {
		    logger.warn("Master of no tenant!");
		    return Collections.emptySet();
		}
		if("wildcard".equals(tenantlist)) {
			logger.warn("No setup for task tenants, setting wildcard");
			this.wildcard = true;
		    return Collections.emptySet();
		}
		if(tenantlist.equals("-") || tenantlist.equals("")) {
			logger.warn("Tenant master operations blocked. No tasks/workflows will be activated");
		    return Collections.emptySet();
		}
		String[] parts = tenantlist.split(",");
		for (String tenant : parts) {
			masters.add(tenant);
		}
		return masters;
	}
	
	public void deactivate() {
		logger.info("Deactivate env-based tenant config");
		tenants.clear();
		mastertenants.clear();
		wildcard = false;
		
	}

	public NavajoIOConfig getConfig() {
		return config;
	}

	public void setConfig(NavajoIOConfig ioConfig) {
		this.config = ioConfig;
	}

	public void clearConfig(NavajoIOConfig ioConfig) {
		this.config = null;
	}
}
