package com.dexels.navajo.tester.js;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.tester.js.model.NavajoFileSystemFolder;
import com.dexels.navajo.tester.js.model.NavajoFileSystemScript;

public class NavajoTesterHelper {
    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList(".xml", ".scala");

    private final static Logger logger = LoggerFactory.getLogger(NavajoTesterHelper.class);
    private NavajoConfigInterface navajoConfig;
    
    public void setNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = nci;
    }

    public void clearNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = null;
    }
    
    public List<String> getSupportedTenants() {
        // A bit ugly - going to navigate to the Settings folder to find out for which
        // tenants we have config to handle their requests
        List<String> result = new ArrayList<String>();
        File scriptsPath = new File(navajoConfig.getScriptPath());
        File settingsPath = new File(scriptsPath.getParent(), "settings");
        if (settingsPath.exists()) {
            String[] directories = settingsPath.list(DirectoryFileFilter.INSTANCE);
            for (int i = 0; i < directories.length; i++) {
                result.add(directories[i]);
            }
        }
        Collections.sort(result);
        return result;
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
                String filename = f.getAbsolutePath();
                int dotIdx = filename.lastIndexOf('.');
                if (dotIdx > 0 && SUPPORTED_EXTENSIONS.contains(filename.substring(dotIdx, filename.length()))) {
                    folder.addEntry(new NavajoFileSystemScript(f));
                }
                
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
    
    public String getCompiledScriptContent(String path) {
        File scriptsPath = new File(navajoConfig.getCompiledScriptPath());
        File f = new File(scriptsPath, path + ".java");
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
