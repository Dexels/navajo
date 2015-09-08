package com.dexels.navajo.tester;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.tester.model.NavajoFileSystemEntry;
import com.dexels.navajo.tester.model.NavajoFileSystemFolder;
import com.dexels.navajo.tester.model.NavajoFileSystemScript;

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
}
