package com.dexels.navajo.compiler.scala;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.compiler.tsl.custom.PackageListener;
import com.dexels.navajo.compiler.tsl.custom.PackageReportingClassLoader;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.server.NavajoIOConfig;

import scala.collection.mutable.ListBuffer;
import scala.tools.nsc.Global;
import scala.tools.nsc.Settings;
import scala.tools.nsc.reporters.ConsoleReporter;
import scala.tools.reflect.ReflectGlobal;

public class ScalaCompiler extends ScriptCompiler {
	private static final String SCRIPT_PATH = "scala";
    private static String SCRIPT_EXTENSION = ".scala";
    private ClassLoader navajoScriptClassLoader;
    
    Set<String> whitelist = new HashSet<String>();
    
    String[] standardPackages = new String[] {"com.dexels.navajo.document", "com.dexels.navajo.document.types",
            "com.dexels.navajo.script.api", "com.dexels.navajo.server", "com.dexels.navajo.mapping",
            "com.dexels.navajo.server.enterprise.tribe", "com.dexels.navajo.mapping.compiler.meta",
            "com.dexels.navajo.parser", "com.dexels.navajo.loader", "org.osgi.framework",
            "com.dexels.navajo.entity;resolution:=optional", "com.dexels.navajo.entity.impl;resolution:=optional",
            "com.dexels.navajo.server.resource;resolution:=optional" };
    String[] standardReqBundles = new String[] { "org.scala-lang.scala-library;bundle-version=\"2.11.2\", com.sportlink.adapters"};
    
 
    private final static Logger logger = LoggerFactory.getLogger(ScalaCompiler.class);

    public void activate(Map<String, Object> osgisettings) {
       if (osgisettings.containsKey("whitelist")) {
           
           String whitelistString = (String) osgisettings.get("whitelist");
           String[] splitted  = whitelistString.split(",");
           for (String anImport : splitted) {
               if (anImport.indexOf(';') > 0) {
                   anImport =    anImport.substring(0,anImport.indexOf(';'));
               }
               whitelist.add(anImport);
           }
       }
       
    }
    
    

    @Override
    protected Set<String> compileScript(File scriptFile, String script, String packagePath, List<Dependency> dependencies, String tenant,
            boolean hasTenantSpecificFile, boolean forceTenant) throws Exception {
    	final Set<String> packages = new HashSet<>();
    	
        for (String pkg : standardPackages) {
            packages.add(pkg);
        }
        
        PackageReportingClassLoader prc = new PackageReportingClassLoader(navajoScriptClassLoader);
        prc.addPackageListener(new PackageListener() {
            @Override
            public void packageFound(String name) {
                if (whitelist.contains(name)) {
                    packages.add(name);
                }
            }
        });
        

        File targetDir = new File(navajoIOConfig.getCompiledScriptPath(), packagePath + File.separator + script);
        targetDir.mkdirs();
        
        Settings settings = new Settings();
        settings.outputDirs().setSingleOutput(targetDir.getAbsolutePath()) ;
        ConsoleReporter reporter = new ConsoleReporter(settings);

        ReflectGlobal g = new ReflectGlobal(settings, reporter, prc);
        Global.Run compiler = g.new Run();
        
        ListBuffer<String> files = new ListBuffer<String>();
        String file = scriptFile.getAbsolutePath();

        files.$plus$eq(file);
         
        try {
            compiler.compile(files.toList());
        } catch (Exception e) {
            logger.error("Exception on getting scala code! {}", e);
        }

        
        logger.debug("finished compiling scala for the following files: {}", compiler.compiledFiles());
        return packages;
    }

    @Override
    public boolean scriptNeedsCompilation() {
        return false;
    }

    @Override
    public String getScriptExtension() {
        return SCRIPT_EXTENSION;
    }

    public void setNavajoConfig(NavajoIOConfig config) {
        this.navajoIOConfig = config;
    }

    public void clearNavajoConfig(NavajoIOConfig config) {
        this.navajoIOConfig = null;
    }

    public void setClassLoader(ClassLoader navajoScriptClassLoader) {
        this.navajoScriptClassLoader = navajoScriptClassLoader;
    }

    public void clearClassLoader(ClassLoader navajoScriptClassLoader) {
        this.navajoScriptClassLoader = null;
    }



	@Override
	public Set<String> getRequiredBundles() {
		return new HashSet<String>(Arrays.asList(standardReqBundles));
	}



    @Override
    public String getRelativeScriptPath() {
        return SCRIPT_PATH;
    }


	@Override
	public boolean supportTslDependencies() {
		return false;
	}
}