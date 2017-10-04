package com.dexels.navajo.compiler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.ExtendDependency;
import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;
import com.dexels.navajo.script.api.CompiledScriptFactory;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.server.NavajoIOConfig;

/**
 * Interface for TSL compiling service
 * 
 * @author cbrouwer
 *
 */
public abstract class ScriptCompiler {
    private final static Logger logger = LoggerFactory.getLogger(ScriptCompiler.class);
    
    protected ExpressionEvaluator expressionEvaluator;
    protected NavajoIOConfig navajoIOConfig = null;

    public void compile(File scriptPath, String tenant, boolean hasTenantSpecificFile, boolean forceTenant)
            throws Exception {
        List<Dependency> dependencies = new ArrayList<>();
        File scriptsMap = new File(navajoIOConfig.getRootPath(), getRelativeScriptPath());
        String script = getRelative(scriptsMap, scriptPath);
        script = script.substring(0, script.indexOf(getScriptExtension()));
        String scriptName = null;
        String packagePath = null;
        if (script.indexOf('/') >= 0) {
            packagePath = script.substring(0, script.lastIndexOf('/'));
            scriptName = script.substring(script.lastIndexOf('/') + 1);
        } else {
            packagePath = "";
            scriptName = script;
        }
        
        Set<String> packages;
        try {
            packages = compileScript(scriptPath, scriptName, packagePath, dependencies, tenant, hasTenantSpecificFile, forceTenant);
        } catch (Throwable t) {
            logger.error("Exception on compiling script {}!", scriptName, t);
            return;
        }
        
        // Before generating OSGi stuff, check forceTenant
        if (forceTenant) {
            scriptName += "_" + tenant;
        }
        
        Set<String> dependentResources = processDependencies(dependencies);
        generateFactoryClass(scriptName, packagePath, dependentResources);

        generateManifest(script, "1.0.0", packagePath, scriptName, packages);
        String compiledDate = generateDs(packagePath, scriptName, dependencies, dependentResources);
        if (packagePath.startsWith("entity")) {
            generateEntityDs(packagePath, scriptName, compiledDate, dependencies, dependentResources);
        }

    }

    private Set<String> processDependencies(List<Dependency> dependencies) {
        Set<String> dependentResources = new HashSet<String>();

        for (Dependency d : dependencies) {
            if ("resource".equals(d.getType())) {
                if (d instanceof AdapterFieldDependency) {
                    final AdapterFieldDependency adapterFieldDep = (AdapterFieldDependency) d;
                    logger.info("ID: {}. It's an aadapter field. with multiple: {} type {}", adapterFieldDep.getId(),
                            adapterFieldDep.hasMultipleDependencies(), adapterFieldDep.getClass());
                    
                    Operand op = expressionEvaluator.evaluate(adapterFieldDep.getId(), null,Optional.empty());
                    if (op != null && op.value instanceof String) {
                        logger.debug("Succeeded evaluation of id: " + ((String) op.value));
                        dependentResources.add((String) op.value);
                    }
                    logger.info("Resource dependency detected: {} type: {} dependency id: ", d.getClass().getName(),
                            d.getType(), d.getId());

                    Dependency[] subs = adapterFieldDep.getMultipleDependencies();
                    if (subs != null) {
                        for (Dependency dependency : subs) {
                            logger.info("Nested dependency detected: {}, type {}", dependency.getClass().getName(), dependency.getType());
                        }
                    }
                }
            }
        }
        return dependentResources;
    }
    
    private void generateFactoryClass(String script, String packagePath, Set<String> resources) throws IOException {

        String javaPackagePath = packagePath.replaceAll("/", ".");
        PrintWriter w = new PrintWriter(
                navajoIOConfig.getOutputWriter(navajoIOConfig.getCompiledScriptPath(), packagePath, script + "Factory", ".java"));
        if ("".equals(packagePath)) {
            w.println("package defaultPackage;");
        } else {
            w.println("package " + javaPackagePath + ";");
        }

        w.println("import com.dexels.navajo.server.*;");
        w.println("import com.dexels.navajo.mapping.*;");
        w.println("import com.dexels.navajo.script.api.*;");
        w.println();
        w.println("public class " + script + "Factory extends CompiledScriptFactory {");
        w.println(" protected String getScriptName() {");
        if ("".equals(packagePath)) {
            w.println("     return \"defaultPackage." + script + "\";");
        } else {
            w.println("     return \"" + javaPackagePath + "." + script + "\";");
        }
        w.println(" }");
        for (String res : resources) {
            addResourceField(res, w);
        }

        w.println("public CompiledScript getCompiledScript() throws InstantiationException, IllegalAccessException, ClassNotFoundException {");
        w.println(" Class<? extends CompiledScript> c;");
        w.println(" c = (Class<? extends CompiledScript>) Class.forName(getScriptName());");
        w.println(" CompiledScript instance = c.newInstance();");
        w.println(" super.initialize(instance);");
        w.println(" return instance;");
        w.println('}');
        w.println("");
        for (String res : resources) {
            addResourceDependency(res, w, "set");
            addResourceDependency(res, w, "clear");
        }
        w.println("");

        w.println('}');
        w.flush();
        w.close();

    }
    
    private void generateManifest(String fullScript, String version, String packagePath, String script, Set<String> packages) throws IOException {
    	String description = fullScript.replace('/',  '.');
    	String symbolicName = "navajo.script." + description;
        PrintWriter w = new PrintWriter(
                navajoIOConfig.getOutputWriter(navajoIOConfig.getCompiledScriptPath(), packagePath, script, ".MF"));
        
        // properties.getCompiledScriptPath(), pathPrefix, serviceName, ".java"
        w.print("Manifest-Version: 1.0\r\n");
        w.print("Bundle-SymbolicName: " + symbolicName + "\r\n");
        w.print("Bundle-Version: " + version + "." + formatCompilationDate() + "\r\n");
        w.print("Bundle-Name: " + description + "\r\n");
        w.print("Bundle-RequiredExecutionEnvironment: JavaSE-1.6\r\n");
        w.print("Bundle-ManifestVersion: 2\r\n");
        w.print("Bundle-ClassPath: .\r\n");

        StringBuffer sb = new StringBuffer();
        Iterator<String> it = packages.iterator();
        boolean first = true;
        while (it.hasNext()) {
            if (!first) {
                sb.append(' ');
            }
            first = false;
            String pck = it.next();
            sb.append(pck);
            if (it.hasNext()) {
                sb.append(",\r\n");
            }

        }
        w.print("Import-Package: " + sb.toString() + "\r\n");

        sb = new StringBuffer();
        it = getRequiredBundles().iterator();
        first = true;
        while (it.hasNext()) {
            if (!first) {
                sb.append(' ');
            }
            first = false;
            String pck = it.next();
            sb.append(pck);
            if (it.hasNext()) {
                sb.append(",\r\n");
            }

        }
        if (sb.toString().length() > 0) {
            w.print("Require-Bundle: " + sb.toString() + "\r\n");
        }

        w.print("Service-Component: OSGI-INF/*.xml\r\n");
        w.print("\r\n");
        w.flush();
        w.close();
    }
    
    private String getRelative(File base, File path) {
        String relative = base.toURI().relativize(path.toURI()).getPath();
        return relative;
    }
    
    private String formatCompilationDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatted = df.format(new Date());
        return formatted;
    }

    private String generateDs(String packagePath, String script, List<Dependency> dependencies, Set<String> dependentResources)
            throws IOException {
        String fullName;
        String compiledDate = "" + new Long(new Date().getTime());
        if (packagePath.equals("")) {
            fullName = script;
        } else {
            fullName = packagePath + "/" + script;

        }
        String javaPackagePath;
        if ("".equals(packagePath)) {
            javaPackagePath = "defaultPackage";
        } else {
            javaPackagePath = packagePath.replaceAll("/", ".");
        }

        String tenant = tenantFromScriptPath(fullName);
        if (tenant == null) {
            tenant = getTentantSpecificDependency(dependencies);
        }
        String symbolicName = rpcNameFromScriptPath(fullName).replaceAll("/", ".");
        // symbolicName = fullName.replaceAll("/", ".");
        boolean hasTenantSpecificFile = tenant != null;

        XMLElement xe = new CaseSensitiveXMLElement("scr:component");
        xe.setAttribute("xmlns:scr", "http://www.osgi.org/xmlns/scr/v1.1.0");
        xe.setAttribute("immediate", "true");
        if (tenant != null) {
            xe.setAttribute("name", symbolicName + "_" + tenant);
        } else {
            xe.setAttribute("name", symbolicName);
        }
        xe.setAttribute("activate", "activate");
        xe.setAttribute("deactivate", "deactivate");
        xe.setAttribute("configuration-policy", "optional");
        XMLElement implementation = new CaseSensitiveXMLElement("implementation");
        xe.addChild(implementation);
        implementation.setAttribute("class", javaPackagePath + "." + script + "Factory");
        XMLElement service = new CaseSensitiveXMLElement("service");
        xe.addChild(service);
        XMLElement provide = new CaseSensitiveXMLElement("provide");
        service.addChild(provide);
        provide.setAttribute("interface", CompiledScriptFactory.class.getName());
        

        addProperty("navajo.scriptName", "String", symbolicName, xe);
        addProperty("navajo.compileddate", "String",  compiledDate, xe);
        if (hasTenantSpecificFile) {
            addProperty("navajo.tenant", "String", tenant, xe);
            addProperty("service.ranking", "Integer", "1000", xe);
        } else {
            addProperty("service.ranking", "Integer", "0", xe);
            addProperty("navajo.tenant", "String", "default", xe);
        }

        for (String resource : dependentResources) {
            XMLElement dep = new CaseSensitiveXMLElement("reference");
            dep.setAttribute("bind", "set" + resource);
            dep.setAttribute("unbind", "clear" + resource);
            dep.setAttribute("policy", "static");
            dep.setAttribute("cardinality", "1..1");
            dep.setAttribute("interface", "javax.sql.DataSource");
            dep.setAttribute("target", "(navajo.resource.name=" + resource + ")");
            xe.addChild(dep);
        }
        PrintWriter w = new PrintWriter(
                navajoIOConfig.getOutputWriter(navajoIOConfig.getCompiledScriptPath(), packagePath, script, ".xml"));
        w.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xe.write(w);
        w.flush();
        w.close();
        
        return compiledDate;
    }
    
    private void addResourceField(String res, PrintWriter ww) {
        ww.println("private Object _" + res + ";");
    }

    private void addResourceDependency(String res, PrintWriter w, String prefix) {
        w.println("void " + prefix + res + "(Object resource) {");
        w.println("  this._" + res + " = resource;");
        w.println("  " + prefix + "Resource(\"" + res + "\",resource); ");
        w.println("}\n");
    }

    private String tenantFromScriptPath(String scriptPath) {
        int scoreIndex = scriptPath.lastIndexOf("_");
        int slashIndex = scriptPath.lastIndexOf("/");
        if (scoreIndex >= 0 && slashIndex < scoreIndex) {
            return scriptPath.substring(scoreIndex + 1, scriptPath.length());
        } else {
            return null;
        }
    }
    
    private String getTentantSpecificDependency(List<Dependency> dependencies) {
        for (Dependency d : dependencies) {
            if (d instanceof IncludeDependency) {
                IncludeDependency incDep = (IncludeDependency) d;
                if (incDep.isTentantSpecificInclude()) {
                    return incDep.getTentant();
                }
            }
        }
        return null;
    }

    private void generateEntityDs(String packagePath, String script,  String compiledDate, List<Dependency> dependencies,
            Set<String> dependentResources) throws IOException {
        String fullName;

        if (packagePath.equals("")) {
            fullName = script;
        } else {
            fullName = packagePath + "/" + script;
        }
        String entityName = fullName.substring(fullName.indexOf("/") + 1).replaceAll("/", ".");

        String symbolicName = rpcNameFromScriptPath(fullName).replaceAll("/", ".");

        XMLElement xe = new CaseSensitiveXMLElement("scr:component");
        xe.setAttribute("xmlns:scr", "http://www.osgi.org/xmlns/scr/v1.1.0");
        xe.setAttribute("immediate", "true");
        xe.setAttribute("name", "navajo.entities." + symbolicName);
        xe.setAttribute("activate", "activate");
        xe.setAttribute("deactivate", "deactivate");
        xe.setAttribute("enabled", "true");

        XMLElement implementation = new CaseSensitiveXMLElement("implementation");
        xe.addChild(implementation);
        implementation.setAttribute("class", "com.dexels.navajo.entity.Entity");
        XMLElement service = new CaseSensitiveXMLElement("service");
        xe.addChild(service);
        XMLElement provide = new CaseSensitiveXMLElement("provide");
        service.addChild(provide);
        provide.setAttribute("interface", "com.dexels.navajo.entity.Entity");

        addProperty("entity.name", "String", entityName, xe);
        addProperty("service.name", "String", fullName, xe);
        addProperty("entity.message", "String", script, xe);

        XMLElement refMan = new CaseSensitiveXMLElement("reference");
        refMan.setAttribute("bind", "setEntityManager");
        refMan.setAttribute("unbind", "clearEntityManager");
        refMan.setAttribute("policy", "dynamic");
        refMan.setAttribute("cardinality", "1..1");
        refMan.setAttribute("interface", "com.dexels.navajo.entity.EntityManager");
        refMan.setAttribute("name", "EntityManager");
        xe.addChild(refMan);

        XMLElement refScript = new CaseSensitiveXMLElement("reference");
        refScript.setAttribute("cardinality", "1..1");
        refScript.setAttribute("bind", "setCompiledScript");
        refScript.setAttribute("unbind", "clearCompiledScript");
        refScript.setAttribute("interface", "com.dexels.navajo.script.api.CompiledScriptFactory");
        refScript.setAttribute("name", "CompiledScript");
        String target1 = "component.name=" + symbolicName.replace("/", ".");
        String target2 = "navajo.compileddate=" + compiledDate;
        refScript.setAttribute("target", "(&("+target1+")("+target2 +"))");
        xe.addChild(refScript);

        for (int i = 0; i < dependencies.size(); i++) {
            Dependency d = dependencies.get(i);
            if (d instanceof ExtendDependency) {
                String extendedEntity = d.getId().replaceAll("/", ".");
                XMLElement depref = new CaseSensitiveXMLElement("reference");
                depref.setAttribute("name", "SuperEntity" + i);
                depref.setAttribute("policy", "static");
                depref.setAttribute("cardinality", "1..1");
                depref.setAttribute("interface", "com.dexels.navajo.entity.Entity");
                depref.setAttribute("target", "(entity.name=" + extendedEntity + ")");
                depref.setAttribute("bind", "addSuperEntity");
                depref.setAttribute("unbind", "removeSuperEntity");
                xe.addChild(depref);
            }
        }

        for (String resource : dependentResources) {
            XMLElement dep = new CaseSensitiveXMLElement("reference");
            dep.setAttribute("bind", "set" + resource);
            dep.setAttribute("unbind", "clear" + resource);
            dep.setAttribute("policy", "static");
            dep.setAttribute("cardinality", "1..1");
            dep.setAttribute("interface", "javax.sql.DataSource");
            dep.setAttribute("target", "(navajo.resource.name=" + resource + ")");
            xe.addChild(dep);
        }

        PrintWriter w = new PrintWriter(
                navajoIOConfig.getOutputWriter(navajoIOConfig.getCompiledScriptPath(), packagePath, "entity", ".xml"));
        w.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xe.write(w);
        w.flush();
        w.close();
    }


    private String rpcNameFromScriptPath(String scriptPath) {
        int scoreIndex = scriptPath.lastIndexOf("_");
        int slashIndex = scriptPath.lastIndexOf("/");
        if (scoreIndex >= 0 && slashIndex < scoreIndex) {
            return scriptPath.substring(0, scoreIndex);
        } else {
            return scriptPath;
        }
    }

    protected void addProperty(final String key, final String type, final String value, final XMLElement xe) {
        XMLElement property = new CaseSensitiveXMLElement("property");
        xe.addChild(property);
        property.setAttribute("name", key);
        property.setAttribute("type", type);
        property.setAttribute("value", value);
    }


    /* Abstract methods */

    /**
     * Takes care of any script-language specific compilation. Returns a set of required packages
     * @param scriptPath TODO
     */
    protected abstract Set<String> compileScript(File scriptPath, String script, String packagePath, List<Dependency> dependencies,
            String tenant, boolean hasTenantSpecificFile, boolean forceTenant) throws Exception;

    /**
     * @return A boolean to indicate whether the script has been compiled to a
     *         .java file or straight to java byte code.
     */
    public abstract boolean scriptNeedsCompilation();

    /**
     * @return Returns the script extension (INCLUDING the dot) this
     *         ScriptCompiler can handle
     */
    public abstract String getScriptExtension();
    
    /**
     * @return Returns the relative script path, i.e. "scripts" or "scalascripts"
     */
    public abstract String getRelativeScriptPath();
    
    /**
     * @return Returns the required OSGi bundles
     */
    public abstract Set<String> getRequiredBundles();
    
    /**
     * @return Returns whether this compiler supports TSL style depdenencies
     */
    public abstract boolean supportTslDependencies();

}