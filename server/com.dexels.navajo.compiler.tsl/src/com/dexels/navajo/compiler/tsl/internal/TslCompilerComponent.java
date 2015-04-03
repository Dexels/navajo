package com.dexels.navajo.compiler.tsl.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.compiler.tsl.custom.PackageListener;
import com.dexels.navajo.compiler.tsl.custom.PackageReportingClassLoader;
import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.mapping.compiler.TslCompiler;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslCompilerComponent extends ScriptCompiler {

    private static String SCRIPT_EXTENSION = ".xml";
    
    private ClassLoader classLoader = null;
    private final static Logger logger = LoggerFactory.getLogger(TslCompilerComponent.class);
    private TslCompiler compiler;
    String[] standardPackages = new String[] { "com.dexels.navajo.document", "com.dexels.navajo.document.types",
            "com.dexels.navajo.script.api", "com.dexels.navajo.server", "com.dexels.navajo.mapping",
            "com.dexels.navajo.server.enterprise.tribe", "com.dexels.navajo.mapping.compiler.meta",
            "com.dexels.navajo.parser", "com.dexels.navajo.loader", "org.osgi.framework",
            "com.dexels.navajo.entity;resolution:=optional", "com.dexels.navajo.entity.impl;resolution:=optional",
            "com.dexels.navajo.server.resource;resolution:=optional" };

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.compiler.tsl.ScriptCompiler#compileTsl(java.lang.String
     * )
     */

    protected void compileScript(String scriptPath, String compileDate, String tenant, boolean hasTenantSpecificFile,
            boolean forceTenant) throws Exception {
        String packagePath = null;
        String script = null;
        if (scriptPath.indexOf('/') >= 0) {
            packagePath = scriptPath.substring(0, scriptPath.lastIndexOf('/'));
            script = scriptPath.substring(scriptPath.lastIndexOf('/') + 1);
        } else {
            packagePath = "";
            script = scriptPath;
        }

        for (String pkg : standardPackages) {
            packages.add(pkg);
        }

        PackageReportingClassLoader prc = new PackageReportingClassLoader(classLoader);
        prc.addPackageListener(new PackageListener() {
            @Override
            public void packageFound(String name) {
                packages.add(name);
            }
        });

        compiler.compileToJava(script, navajoIOConfig.getScriptPath(), navajoIOConfig.getCompiledScriptPath(),
                packagePath, prc, navajoIOConfig, dependencies, tenant, hasTenantSpecificFile, forceTenant);
    }

    public void setClassLoader(ClassLoader cls) {
        this.classLoader = cls;
    }

    /**
     * @param cls
     *            the classloader to clear
     */
    public void clearClassLoader(ClassLoader cls) {
        this.classLoader = null;
    }

    public void setIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = config;
    }

    /**
     * @param config
     *            the navajoconfig to clear
     */
    public void clearIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = null;
    }

    public void activate() {
        logger.debug("Activating TSL compiler");
        compiler = new TslCompiler(classLoader, navajoIOConfig);
    }

    public void deactivate() {
        logger.debug("Deactivating TSL compiler");
    }

    void setExpressionEvaluator(ExpressionEvaluator e) {
        this.expressionEvaluator = e;
    }

    void clearExpressionEvaluator(ExpressionEvaluator e) {
        this.expressionEvaluator = null;
    }

    @Override
    public boolean scriptNeedsCompilation() {
        return true;
    }

    @Override
    public String getScriptExtension() {
        return SCRIPT_EXTENSION;
    }

}
