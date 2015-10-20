package com.dexels.navajo.tester.js;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.tester.js.model.NavajoFileSystemFolder;
import com.dexels.navajo.tester.js.model.NavajoFileSystemScript;

public class NavajoTesterHelper {
    private final static Logger logger = LoggerFactory.getLogger(NavajoTesterHelper.class);
    private NavajoConfigInterface navajoConfig;
    
    public void setNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = nci;
    }

    public void clearNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = null;
    }
    

    public NavajoFileSystemFolder getAllScripts() {
        File scriptsPath = new File(navajoConfig.getScriptPath());
        NavajoFileSystemFolder result = new NavajoFileSystemFolder(scriptsPath);
        addContentsTo(result);

        return result;
    }
    
    private void addContentsTo(NavajoFileSystemFolder folder) {
        
        File currentPath = new File(folder.getPath());
        Collection<File> files = FileUtils.listFiles(currentPath, null, false);
 
        for (File f : files) {
            
            if (f.isFile()) {                
                folder.addEntry(new NavajoFileSystemScript(f));
            }
        }
        
        String[] directories = currentPath.list( DirectoryFileFilter.INSTANCE );
        for ( int i = 0; i < directories.length; i++ ) {
            NavajoFileSystemFolder subDir = new NavajoFileSystemFolder(new File(currentPath, directories[i]));
            addContentsTo(subDir);
            folder.addEntry(subDir);
        }  
    }

    public String getFileContent(String path) {
        File scriptsPath = new File(navajoConfig.getScriptPath());
        File f = new File(scriptsPath, path + ".xml");
        if (f.exists()) {
            try {
               byte[] bytes =  Files.readAllBytes(f.toPath());
               return new String(bytes, "UTF-8");
            } catch (IOException e) {
                logger.error("Exception on getting file contents: ", e);
            }
        }
        return "";
       
    }
}
