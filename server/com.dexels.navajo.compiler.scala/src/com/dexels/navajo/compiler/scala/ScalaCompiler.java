package com.dexels.navajo.compiler.scala;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.compiler.tsl.custom.PackageListener;
import com.dexels.navajo.compiler.tsl.custom.PackageReportingClassLoader;
import com.dexels.navajo.server.NavajoIOConfig;

import scala.collection.mutable.ListBuffer;
import scala.tools.nsc.*;
import scala.tools.nsc.Global.Run;
import scala.tools.nsc.reporters.ConsoleReporter;
import scala.tools.reflect.ReflectGlobal;

public class ScalaCompiler extends ScriptCompiler {
    private static String SCRIPT_EXTENSION = ".scala";
    private Run compiler;
    private Global g;
    BundleContext bundleContext;
    
    String[] standardPackages = new String[] { "com.dexels.navajo.document", "com.dexels.navajo.document.types",
            "com.dexels.navajo.script.api", "com.dexels.navajo.server", "com.dexels.navajo.mapping",
            "com.dexels.navajo.server.enterprise.tribe", "com.dexels.navajo.mapping.compiler.meta",
            "com.dexels.navajo.parser", "com.dexels.navajo.loader", "org.osgi.framework", "com.dexels.navajo.scala", 
            "com.dexels.navajo.scala.document", "com.dexels.navajo.function.scala.api", "com.dexels.navajo.entity;resolution:=optional",
            "com.dexels.navajo.entity.impl;resolution:=optional", "com.dexels.navajo.server.resource;resolution:=optional" };
    
    String[] standardReqBundles = new String[] { "org.scala-lang.scala-library;bundle-version=\"2.11.2\""};
    
    
    
    private final static Logger logger = LoggerFactory.getLogger(ScalaCompiler.class);

    
    private ServiceReference<ClassLoader> getResourceReference() throws InvalidSyntaxException {
        Collection<ServiceReference<ClassLoader>> dlist = bundleContext.getServiceReferences(ClassLoader.class,"(type=navajoScriptClassLoader)");
        if(dlist.size()!=1) {
            logger.info("Matched: {} classloaders.",dlist.size());
        }
        if(dlist.isEmpty()) {
            logger.error("Can not find classloader: {}", "navajoScriptClassLoader");
        }
        ServiceReference<ClassLoader> dref = dlist.iterator().next();
        return dref;
    }

    public ClassLoader getResourceSource()  {
        ServiceReference<ClassLoader> ss;
        try {
            ss = getResourceReference();
            return bundleContext.getService(ss);
        } catch (InvalidSyntaxException e) {
            logger.error("Exception on getting classloader: {}", e);
            return null;
        }
    }
    

    public void activate(Map<String, Object> osgisettings, BundleContext context) {
        this.bundleContext = context;
        Settings settings = new Settings();
        settings.outputDirs().setSingleOutput("/home/chris/scala/") ;
        ConsoleReporter reporter = new ConsoleReporter(settings);
        g = new ReflectGlobal(settings, reporter, getResourceSource());
        compiler = g.new Run();
    }

    @Override
    protected void compileScript(String scriptPath, String compileDate, String tenant, boolean hasTenantSpecificFile,
            boolean forceTenant) throws Exception {

        for (String pkg : standardPackages) {
            packages.add(pkg);
        }
        
       
        for (String pkg : standardReqBundles) {
            reqBundles.add(pkg);
        }
        
        String packagePath = null;
        String script = null;
        if (scriptPath.indexOf('/') >= 0) {
            packagePath = scriptPath.substring(0, scriptPath.lastIndexOf('/'));
            script = scriptPath.substring(scriptPath.lastIndexOf('/') + 1);
        } else {
            packagePath = "";
            script = scriptPath;
        }

        String tenantScript = script;
        if (forceTenant) {
            tenantScript = script + "_" + tenant;
        }
        
        File targetDir = new File(navajoIOConfig.getCompiledScriptPath(),  script +  File.separator + packagePath);
        targetDir.mkdirs();
        
        Settings settings = new Settings();
        settings.outputDirs().setSingleOutput(targetDir.getAbsolutePath()) ;
        ConsoleReporter reporter = new ConsoleReporter(settings);
        g = new ReflectGlobal(settings, reporter, getResourceSource());
        compiler = g.new Run();
        
        ListBuffer<String> files = new ListBuffer<String>();
        String file = navajoIOConfig.getScriptPath() + File.separator + scriptPath + ".scala";

        files.$plus$eq(file);

        try {
            compiler.compile(files.toList());
        } catch (Exception e) {
            logger.error("Exception on getting scala code! {}", e);
        }
        PackageReportingClassLoader prc = new PackageReportingClassLoader(getResourceSource());
        prc.addPackageListener(new PackageListener() {
            @Override
            public void packageFound(String name) {
                packages.add(name);
            }
        });
        
        
        logger.info("finished scala! {}", compiler.compiledFiles());

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

}
