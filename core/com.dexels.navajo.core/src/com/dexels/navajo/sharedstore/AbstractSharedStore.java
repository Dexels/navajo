package com.dexels.navajo.sharedstore;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public abstract class AbstractSharedStore implements SharedStoreInterface {
    private static final String prefix = "$__";
    private static final String postfix = "__$";

    protected String getTenantSpecificName(String tenant, String name) {
        return prefix + tenant + postfix + name;
    }

    protected String getName(String name) {
        if (name.startsWith(prefix)) {
            return name.substring(name.indexOf(postfix) + postfix.length());
        }
        return name;
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