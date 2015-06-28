package com.dexels.navajo.sharedstore;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSharedStore implements SharedStoreInterface {

    
	private final static Logger logger = LoggerFactory
			.getLogger(AbstractSharedStore.class);
	
    protected String getTenantSpecificName(String tenant, String name) {
    	if(name.startsWith(TENANT_PREFIX) || tenant == null) {
    		return name;
    	}
        String tenantSpecificName = SharedStoreInterface.TENANT_PREFIX + tenant + SharedStoreInterface.TENANT_POSTFIX + name;
		return tenantSpecificName;
    }

    protected String getName(String name) {
        if (name.startsWith(TENANT_PREFIX)) {
            return name.substring(name.indexOf(TENANT_POSTFIX) + TENANT_POSTFIX.length());
        }
        return name;
    }

    @Override
    public void remove(String tenant, String parent, String name) {
//        remove(parent, getTenantSpecificName(tenant, name));
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