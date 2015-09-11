package com.dexels.navajo.sharedstore;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;


public abstract class AbstractSharedStore implements SharedStoreInterface {
    private static final String TENANT_PREFIX = "$__";
    private static final String TENANT_POSTFIX = "__$";
    
	@Override
    public String getTenantSpecificName(String tenant, String name) {
    	if(name.startsWith(TENANT_PREFIX) || tenant == null) {
    		return name;
    	}
        String tenantSpecificName = TENANT_PREFIX + tenant + TENANT_POSTFIX + name;
		return tenantSpecificName;
    }

	@Override
    public  String getName(String name) {
        if (name.startsWith(TENANT_PREFIX)) {
            return name.substring(name.indexOf(TENANT_POSTFIX) + TENANT_POSTFIX.length());
        }
        return name;
    }
	
	@Override
    public  String getTenant(String name) {
        if (name.startsWith(TENANT_PREFIX)) {
            return name.substring(TENANT_PREFIX.length(), name.indexOf(TENANT_POSTFIX));
        }
        return null;
    }

    @Override
    public void remove(String tenant, String parent, String name) {
        remove(parent, getTenantSpecificName(tenant, name));
    }

    @Override
    public boolean exists(String tenant, String parent, String name) {
        return exists(parent, getTenantSpecificName(tenant, name));
    }

    @Override
    public InputStream getStream(String tenant, String parent, String name) throws SharedStoreException {
        return getStream(parent, getTenantSpecificName(tenant, name));
    }

    @Override
    public OutputStream getOutputStream(String tenant, String parent, String name, boolean requireLock)
            throws SharedStoreException {
    	return getOutputStream(parent, getTenantSpecificName(tenant, name), requireLock);
    }

    @Override
    public void store(String tenant, String parent, String name, Serializable value, boolean append, boolean requireLock)
            throws SharedStoreException {
        store(parent, getTenantSpecificName(tenant, name), value, append, requireLock);

    }

}