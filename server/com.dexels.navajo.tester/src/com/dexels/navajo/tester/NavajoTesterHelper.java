package com.dexels.navajo.tester;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.ClientContext;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.tester.model.NavajoFileSystemFolder;
import com.dexels.navajo.tester.model.NavajoFileSystemScript;

public class NavajoTesterHelper {
    private final static Logger logger = LoggerFactory.getLogger(NavajoTesterHelper.class);
    private NavajoConfigInterface navajoConfig;
    private ClientContext context;
    
    public void setNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = nci;
    }

    public void clearNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = null;
    }
    
    public void setNavajoClientContext(ClientContext context) {
        this.context = context;
    }
    public void clearNavajoClientContext(ClientContext context) {
        this.context = null;
    }
    
    public String runScript(String service, String tenant) {
        try {
            context.callService(service, tenant);
        } catch (ClientException e) {
           logger.error("Exception on calling service: {}", e);
        }
        
        Navajo n=  context.getNavajo(service);
        StringWriter outputWriter = new StringWriter();

        n.write(outputWriter);
        return outputWriter.toString();
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
       
    }
}
