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
	private final Set<String> tenants = new HashSet<String>();
	private final Set<String> mastertenants = new HashSet<String>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(EnvTenantConfig.class);
	
	@Override
	public boolean isMasterForTenant(String tenant) {
		return mastertenants.contains(tenant);
	}

	@Override
	public Set<String> isMasterForTenants() {
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
		for(File f : settings.listFiles()) {
			if(f.isDirectory()) {
				tenants.add(f.getName());
			}
		}
		String tenantlist = System.getenv("TENANT_MASTER");
		if(tenantlist==null) {
			logger.info("No setup for task tenants, ignoring");
			return;
		}
		String parts[] = tenantlist.split(",");
		for (String tenant : parts) {
			mastertenants.add(tenant);
		}
	}
	
	public void deactivate() {
		logger.info("Deactivate env-based tenant config");
		tenants.clear();
		mastertenants.clear();
		
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
