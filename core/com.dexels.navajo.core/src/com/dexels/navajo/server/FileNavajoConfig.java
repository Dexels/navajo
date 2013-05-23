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

	
	private final static Logger logger = LoggerFactory
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
   	File adPath = new File(getAdapterPath());
		File bundleFile = new File(adPath,name+".properties");
		if(!bundleFile.exists()) {
			logger.debug("Bundle: "+name+" not found. Resolved to non-existing file: "+bundleFile.getAbsolutePath());
			return null;
		}
		FileInputStream fix = new FileInputStream(bundleFile);
		return fix;
    }
	
    @Override
    public final InputStream getScript(String name) throws IOException {
    	InputStream input;
		String scriptPath = getScriptPath();
		if(!scriptPath.endsWith("/")) {
			scriptPath = scriptPath+"/";
		}
		String path = scriptPath + name + ".xml";
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
	public Date getScriptModificationDate(String scriptPackage, String tenant) throws FileNotFoundException {
		File scr = getApplicableScriptFile(scriptPackage, tenant);
		return new Date(scr.lastModified());
		
	}
	
	@Override
	public File getApplicableBundleForScript(String rpcName, String tenant) {
		try {
			return getApplicableFile(rpcName, tenant, getCompiledScriptPath(), ".jar");
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	@Override
	public File getApplicableScriptFile(String rpcName, String tenant) throws FileNotFoundException {
		return getApplicableFile(rpcName, tenant, getScriptPath(), ".xml");
	}

	@Override
	public boolean hasTenantScriptFile(String rpcName, String tenant) {
		File qualifiedFile = getTenantSpecificFile(rpcName, tenant, getScriptPath(), ".xml");
		return qualifiedFile!=null;
	}
	
	
	private File getTenantSpecificFile(String rpcName, String tenant, String parent, String extension) {
		String name = rpcName.replaceAll("\\.", "/");
		if(!parent.endsWith("/")) {
			parent = parent+"/";
		}
		String qualifiedPath = parent + name + "_" + tenant + extension;
		File qualifiedFile = new File(qualifiedPath);
		if(qualifiedFile.exists()) {
			return qualifiedFile;
		}
		return null;
	}
	
	private File getApplicableFile(String rpcName, String tenant, String parent, String extension) throws FileNotFoundException {
		String name = rpcName.replaceAll("\\.", "/");
		if(!parent.endsWith("/")) {
			parent = parent+"/";
		}
		String path = parent + name + extension;
		File qualifiedFile = getTenantSpecificFile(rpcName, tenant, parent, extension);
		File generalFile = new File(path);
		if(qualifiedFile != null) {
			return qualifiedFile;
		}
		if(!generalFile.exists()) {
			throw new FileNotFoundException("Script not found: "+path);
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
    	InputStream is = inputStreamReader.getResource(getConfigPath() + "/" + name);
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
    
}
