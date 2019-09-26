package com.dexels.navajo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;

public abstract class FileNavajoConfig implements NavajoIOConfig {

    private final InputStreamReader inputStreamReader = new FileInputStreamReader();

	
	private static final Logger logger = LoggerFactory
			.getLogger(FileNavajoConfig.class);
	
	
	@Override
	public final Writer getOutputWriter(String outputPath, String scriptPackage,String scriptName, String extension) throws IOException {
	      File dir = new File(outputPath);
	      if (!dir.exists()) {
	        dir.mkdirs();
	      }
	      File javaFile = new File(dir,scriptPackage+"/"+scriptName+extension);
	      javaFile.getParentFile().mkdirs();
	      FileWriter fo = new FileWriter(javaFile);
		return fo;
	}

	@Override
	public final Reader getOutputReader(String outputPath, String scriptPackage,
			String scriptName, String extension) throws IOException {
	      File dir = new File(outputPath);
	      File javaFile = new File(dir,scriptPackage+"/"+scriptName+extension);
	      FileReader fo = new FileReader(javaFile);
		return fo;
	}

    /**
     * Opens a stream to the named bundle. (this method will add the .properties extension)
     * Don't forget to close the stream when done.
     * @param name
     * @return
     * @throws IOException
     */
	@Override
    public final InputStream getResourceBundle(String name) throws IOException {
   	String adapterPath = getAdapterPath();
   	if(adapterPath==null) {
   		return null;
   	}
	File adPath = new File(adapterPath);
		File bundleFile = new File(adPath,name+".properties");
		if(!bundleFile.exists()) {
			return null;
		}
		FileInputStream fix = new FileInputStream(bundleFile);
		return fix;
    }
	

    @Override
    public final InputStream getScript(String name) throws IOException {
    	return getScript(name, null,".xml");
    }
    
	@Override
    public final InputStream getScript(String name, String tenant, String extension) throws IOException {
    	InputStream input;
		String scriptPath = getScriptPath();
		if(!scriptPath.endsWith("/")) {
			scriptPath = scriptPath+"/";
		}
		String path = null;
		if(hasTenantScriptFile(name, tenant, extension)) {
            path = scriptPath + name + "_" + tenant + extension;
		} else {
			path = scriptPath + name + extension;
		}
		input = inputStreamReader.getResource(path);
		if(input==null) {
    		path = scriptPath + name + ".tsl";
			input = inputStreamReader.getResource(path);
		}
		if(input==null) {
			logger.debug("No resource found");
			File f = new File(getContextRoot(),"scripts/"+name+".xml");
			if(!f.exists()) {
				f = new File(getContextRoot(),"scripts/"+name+".tsl");
				if(f.exists()) {
					logger.error("Using *.tsl extensions is not supported");
				}
			}
			logger.debug("Looking into contextroot: "+f.getAbsolutePath());
			if(f.exists()) {
				return new FileInputStream(f);
			}
		}
		return input;
    }

	@Override
	/**
	 * Name does not include tenant suffix
	 */
	public boolean hasTenantScriptFile(String rpcName, String tenant, String scriptPath) {
		if(tenant==null) {
			return false;
		}
		File qualifiedFile = getTenantSpecificFile(rpcName, tenant, getScriptPath(),null ,true);
		return qualifiedFile!=null;
	}
	

	
	
	private File getTenantSpecificFile(String rpcName, String tenant, String parent, String extension, boolean checkIfExists) {
		String name = rpcName.replaceAll("\\.", "/");
		if(!parent.endsWith("/")) {
			parent = parent+"/";
		}
		String qualifiedPath = parent + name + "_" + tenant + extension;
		File qualifiedFile = new File(qualifiedPath);
		if(!checkIfExists || qualifiedFile.exists()) {
			return qualifiedFile;
		}
		return null;
	}
	
	private File getGenericFile(String rpcName,  String parent, String extension) {
		String name = rpcName.replaceAll("\\.", "/");
		if(!parent.endsWith("/")) {
			parent = parent+"/";
		}
		String qualifiedPath = parent + name + extension;
		File file = new File(qualifiedPath);
		return file;
	}
	
	private File getApplicableFile(String rpcName, String tenant, String parent, String extension, boolean checkIfExists) throws FileNotFoundException {
		String name = rpcName.replaceAll("\\.", "/");
		if(!parent.endsWith("/")) {
			parent = parent+"/";
		}
		String path = parent + name + extension;
		File qualifiedFile = getTenantSpecificFile(rpcName, tenant, parent, extension,checkIfExists);
		File generalFile = new File(path);
		if(qualifiedFile != null) {
			return qualifiedFile;
		}
		if(!generalFile.exists()) {
			throw new FileNotFoundException("Script not found: "+rpcName+" no such file: "+generalFile.getAbsolutePath());
		}
		return generalFile;
	}
	
	@Override
    public final InputStream getConfig(String name) throws IOException {
      InputStream input = inputStreamReader.getResource(getConfigPath() + "/" + name);
      return input;
    }

    @Override
    public final void writeConfig(String name, Navajo conf) throws IOException {
      Writer output = new FileWriter(new File(getConfigPath() + "/" + name));
      try {
        conf.write(output);
      }
      catch (NavajoException ex) {
        throw new IOException(ex.getMessage());
      }
      output.close();
    }

	@Override
	public void writeOutput(String scriptName, String suffix, InputStream is) throws IOException {
	   	File opath = new File(getCompiledScriptPath());
	   	final String path = opath.getAbsolutePath()+"/"+scriptName+suffix;
		File fin = new File(path);
		File parent = fin.getParentFile();
		if(!parent.exists()) {
			parent.mkdirs();
		}
	   	FileOutputStream fos = new FileOutputStream(fin);
	   	IOUtils.copy(is, fos);
	   	fos.close();
	   	is.close();
	}    
    @Override
    public final Navajo readConfig(String name) throws IOException {
    	InputStream is = inputStreamReader.getResource(getConfigPath() + File.separator + name);
    	try {
    		if (is == null) {
    			return null;
    		}
    		return NavajoFactory.getInstance().createNavajo(is);
    	} finally {
    		if ( is != null ) {
    			try {
    				is.close();
    			} catch (Exception e) {
    				// NOT INTERESTED.
    			}
    		}
    	}
    }
    

    @Override
    public String getDeployment() {
        logger.warn("getDeplyoment not implemented in OSGi implementation");
        return null;
    }

	
    
}
