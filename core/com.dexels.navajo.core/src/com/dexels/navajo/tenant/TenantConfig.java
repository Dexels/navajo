package com.dexels.navajo.tenant;

import java.util.Set;

public interface TenantConfig {

	public boolean isMasterForTenant(String tenant);
	public Set<String> isMasterForTenants();
	public Set<String> getTenants();
}
