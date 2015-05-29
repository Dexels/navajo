package com.dexels.navajo.sharedstore;

public class AbstractSharedStore {
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

}