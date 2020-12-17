/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tester.js.model;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavajoFileSystemScript implements NavajoFileSystemEntry {
    private final static Logger logger = LoggerFactory.getLogger(NavajoFileSystemScript.class);

	public static String PATH_TSL = File.separator + "scripts" + File.separator;
	public static String PATH_REACTIVE = File.separator + "reactive" + File.separator;
	public static String PATH_SCALA = File.separator + "scala" + File.separator;
	
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
		} else if (path.contains(PATH_REACTIVE)) {
			String pattern = Pattern.quote(PATH_REACTIVE);
			script = path.split(pattern)[1];
		} else {
			logger.warn("Unsupported path: {}", path);
			script = path;
		}

        script =  script.substring(0, script.lastIndexOf(FilenameUtils.getExtension(path)) -1);
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
