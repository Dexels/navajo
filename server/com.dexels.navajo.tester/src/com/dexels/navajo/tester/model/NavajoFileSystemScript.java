package com.dexels.navajo.tester.model;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class NavajoFileSystemScript implements NavajoFileSystemEntry {
    private String name;
    private String script;
    private String path;

    public NavajoFileSystemScript(File file) {
       
        this.path = file.getAbsolutePath();
        this.name =  FilenameUtils.getBaseName(path);
       
        script = path.split("scripts")[1];
        script =  script.substring(1, script.lastIndexOf(FilenameUtils.getExtension(path)) -1);
        
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
    
    public String getScript() {
        return script;
    }
}
