package com.dexels.navajo.compiler.scala;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.server.NavajoIOConfig;

import scala.collection.mutable.ListBuffer;
import scala.reflect.internal.util.ScalaClassLoader;
import scala.tools.nsc.*;
import scala.tools.nsc.Global.Run;
import scala.tools.nsc.reporters.ConsoleReporter;
import scala.tools.nsc.settings.MutableSettings;
import scala.tools.nsc.settings.MutableSettings.BooleanSetting;

public class ScalaCompilerOrg extends ScriptCompiler {
    private static String SCRIPT_EXTENSION = ".scala";
    private Run compiler;
    private Global g;
    BundleContext bundleContext;
    

    public void activate(Map<String, Object> osgisettings, BundleContext context) {
        // File libJar = new
        // File("/home/chris/git/navajo/server/com.dexels.navajo.compiler.scala/lib/org.scala-lang.scala-library_2.11.6.jar");
        this.bundleContext = context;
        Settings settings = new Settings();
        
        URL compilerPath = null;
        URL scalaDoc = null;
        URL libPath = null;
        
        try {
            compilerPath = java.lang.Class.forName("scala.tools.nsc.Interpreter").getProtectionDomain().getCodeSource().getLocation();
            scalaDoc = java.lang.Class.forName("com.dexels.navajo.scala.document.NavajoDocument").getProtectionDomain().getCodeSource().getLocation();
            libPath = java.lang.Class.forName("scala.Some").getProtectionDomain().getCodeSource().getLocation();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MutableSettings.PathSetting a = (MutableSettings.PathSetting) settings.bootclasspath();
        
        a.v_$eq(compilerPath + File.pathSeparator + libPath + File.pathSeparator + scalaDoc);
        settings.outputDirs().setSingleOutput("/home/chris/scala/") ;
        ((BooleanSetting) settings.usejavacp()).value_$eq(true);
        ConsoleReporter reporter = new ConsoleReporter(settings);
        g = new Global(settings, reporter);
        compiler = g.new Run();
    }

    @Override
    protected void compileScript(String scriptPath, String compileDate, String tenant, boolean hasTenantSpecificFile,
            boolean forceTenant) throws Exception {
        // final String code =
        // "object MyClass { def execute = Console.println(\"hello\") }";
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

//        g.settings().outputDirs()
//                .setSingleOutput(navajoIOConfig.getCompiledScriptPath() + File.separator + packagePath + "scala") ;
        ListBuffer<String> files = new ListBuffer<String>();
        String file = navajoIOConfig.getScriptPath() + File.separator + scriptPath + ".scala";

        files.$plus$eq(file);

        try {
            // Interpreter interp = new Interpreter(settings);
            // boolean result = interp.compileString(code);
            compiler.compile(files.toList());

        } catch (Exception e) {
            System.err.println("Exception on getting scala code! " + e);
        }
        System.out.println("finished scala!" + compiler.compiledFiles());

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
