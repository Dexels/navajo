package com.dexels.navajo.tester.js.model;

public interface NavajoFileSystemEntry {
    public static final String TYPE_FILE = "FILE";
    public static final String TYPE_FOLDER = "FOLDER";
    
    public String getName();
    public String getPath();
    public String getType();
}
