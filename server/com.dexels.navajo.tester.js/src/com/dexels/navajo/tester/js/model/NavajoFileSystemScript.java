package com.dexels.navajo.tester.js.model;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavajoFileSystemScript implements NavajoFileSystemEntry {
    private final static Logger logger = LoggerFactory.getLogger(NavajoFileSystemScript.class);

	public static String PATH_TSL = "sportlink" + File.separator + "scripts";
	public static String PATH_SCALA = "sportlink" + File.separator + "scala";
	
    private String name;
    private String script;
    private String path;

    public NavajoFileSystemScript(File file) {
       
        this.path = file.getAbsolutePath();
        this.name =  FilenameUtils.getBaseName(path);
        
        if (path.contains(PATH_TSL)) {
        	String pattern = Pattern.quote(PATH_TSL);
        	script = path.split(pattern)[1];
        } else if (path.contains(PATH_SCALA)) {
        	String pattern = Pattern.quote(PATH_SCALA);
        	 script = path.split(pattern)[1];
        } else {
        	logger.warn("Unsupported path: {}", path);
        	script = path;
        }

        script =  script.substring(1, script.lastIndexOf(FilenameUtils.getExtension(path)) -1);
        script = script.replace("\\", "/");
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
