package com.dexels.navajo.tester.model;

import java.io.File;

public class NavajoFileSystemScript implements NavajoFileSystemEntry {
    private String name;
    private String path;

    public NavajoFileSystemScript(File file) {
        this.name = file.getName();
        this.path = file.getAbsolutePath();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return NavajoFileSystemEntry.TYPE_FILE;
    }

    @Override
    public String getPath() {
        return path;
    }
}
