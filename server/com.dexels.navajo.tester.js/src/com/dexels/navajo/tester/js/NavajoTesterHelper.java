package com.dexels.navajo.tester.js;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.tester.js.model.NavajoFileSystemFolder;
import com.dexels.navajo.tester.js.model.NavajoFileSystemScript;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class NavajoTesterHelper {
    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList(".xml", ".scala",".rr");

    private static final Logger logger = LoggerFactory.getLogger(NavajoTesterHelper.class);
    private NavajoConfigInterface navajoConfig;
    private ObjectMapper mapper = new ObjectMapper();
    private NavajoTesterApplicationList applicationList;

    public void setNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = nci;
    }

    public void clearNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = null;
    }
    
    public List<String> getSupportedTenants() {
        // A bit ugly - going to navigate to the Settings folder to find out for which
        // tenants we have config to handle their requests
        List<String> result = new ArrayList<>();
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
        
        File root = new File(navajoConfig.getRootPath());
		File reactivePath = new File(root,"reactive");
//		return new File(rootPath,"scripts").getAbsolutePath();

        NavajoFileSystemFolder result = new NavajoFileSystemFolder(scriptsPath);
        NavajoFileSystemFolder reactiveFolder = new NavajoFileSystemFolder(reactivePath);
        addContentsTo(result);
        addContentsTo(reactiveFolder);
        result.addEntry(reactiveFolder);
        return result;
    }
    
    private void addContentsTo(NavajoFileSystemFolder folder) {
        
        File currentPath = new File(folder.getPath());
        if (!currentPath.exists()) {
        	return;
        }
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
               return new String(bytes, StandardCharsets.UTF_8);
            } catch (IOException e) {
                logger.error("Exception on getting file contents: ", e);
            }
        }
        return "";
    }
    

    public void setNavajoTesterApplicationList(NavajoTesterApplicationList testerApplicationList) {
    	this.applicationList = testerApplicationList;
    }

    public void clearNavajoTesterApplicationList(NavajoTesterApplicationList testerApplicationList) {
    	this.applicationList = null;
    }
    
    private Map<String,String> determineApplications() {
    	if(this.applicationList==null) {
    		Map<String,String> result = new HashMap<String, String>();
    		result.put("legacy", "Default");
    		return result;
    	}
    	return this.applicationList.applications();
    }
    
    public ArrayNode getApplicationListContent() {
    	
    	
    	ArrayNode result = mapper.createArrayNode();
    	
    	determineApplications().entrySet()
    		.stream()
    		.map(e->mapper.createObjectNode().put("id", e.getKey()).put("description", e.getValue())
    				
    				)
    		.forEach(e->result.add(e));
    	
    	return result;
    }
//    <select data-placeholder="Select an Application" id="applications">
//    <option value="legacy" selected="1">Oracle</option>
//    <option value="VOETBALNL" >VoetbalNL</option>
//    <option value="KNKV-MEMBERPORTAL" >KNKV</option>
//    <option value="NHV-MEMBERPORTAL" >NHV</option>
//    <option value="KNZB-MEMBERPORTAL" >KNZB</option>
//    <option value="KNBSB-MEMBERPORTAL" >KNBSB</option>                         
//    <option value="NBB-MEMBERPORTAL" >NBB</option>
//    <option value="KBHB-MEMBERPORTAL" >KBHB</option>                                                  
// </select>

   
}
