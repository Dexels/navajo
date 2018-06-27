package com.dexels.navajo.dependency;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.FileInputStreamReader;
import com.dexels.navajo.server.InputStreamReader;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslPreCompiler {
    private static final  Logger logger = LoggerFactory.getLogger(TslPreCompiler.class);
    private NavajoIOConfig navajoIOConfig = null;
    private final InputStreamReader inputStreamReader = new FileInputStreamReader();


    public void setIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = config;
    }

    public void clearIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = null;
    }

    public void getAllDependencies(File script, String scriptFolder, List<Dependency> deps, String scriptTenant) throws XPathExpressionException, UserException {
        Document tslDoc = null;
        InputStream is = null;


        try {
            // Check for metascript.
            if (MapMetaData.isMetaScript(script.getAbsolutePath())) {
                MapMetaData mmd = MapMetaData.getInstance();
                InputStream metais = inputStreamReader.getResource(script.getAbsolutePath());

                String intermed = mmd.parse(script.getAbsolutePath(), metais);
                metais.close();
                is = new ByteArrayInputStream(intermed.getBytes());
            } else {
                is = inputStreamReader.getResource(script.getAbsolutePath());
            }
            tslDoc = XMLDocumentUtils.createDocument(is, false);
        } catch (Exception e) {
            logger.error("Pre-compiler failed!", e);
            throw new UserException(-1, "Exception on pre-compiling script: " + script);
        } finally {
        	if (is != null) {
        		try {
					is.close();
				} catch (IOException e) {
				}
        	}
        	
        }
        

        getAllDependencies(script.getAbsolutePath(), scriptTenant, scriptFolder, deps, tslDoc);
    }

    protected void getAllDependencies(String scriptFile, String scriptTenant, String scriptFolder, List<Dependency> deps, Document tslDoc) throws UserException, XPathExpressionException {
        findIncludeDependencies(scriptFile, scriptTenant, scriptFolder, deps, tslDoc);
        findMapDependencies(scriptFile, scriptTenant, scriptFolder, deps, tslDoc);
        findMethodDependencies(scriptFile, scriptTenant, scriptFolder, deps, tslDoc);
        findEntityDependencies(scriptFile, scriptTenant, scriptFolder, deps, tslDoc);
    }

    protected void findMethodDependencies(String scriptFile, String scriptTenant, String scriptFolder, List<Dependency> deps, Document tslDoc) {
        NodeList methods = tslDoc.getElementsByTagName("method");
        for (int i = 0; i < methods.getLength(); i++) {
            Element n = (Element) methods.item(i);
            String methodScript = n.getAttribute("name");
            if (methodScript == null || methodScript.equals("")) {
                return;
            }

            if (scriptTenant != null) {
                // trying tenant-specific variant first
                String methodScriptFile = scriptFolder + File.separator + methodScript + ".xml";

                // Check if exists
                if (new File(methodScriptFile).exists()) {
                    deps.add(new Dependency(scriptFile, methodScriptFile, Dependency.METHOD_DEPENDENCY, getLineNr(n)));

                    // No need to try any other tenant-specific includes since
                    // we are tenant-specific in the first place
                    // Thus continue with next method
                    continue;
                }
            }

            String methodScriptFile = scriptFolder + File.separator + methodScript + ".xml";

            // Check if exists
            boolean isBroken = false;
            if (!new File(methodScriptFile).exists()) {
                isBroken = true;
            }
            deps.add(new Dependency(scriptFile, methodScriptFile, Dependency.METHOD_DEPENDENCY, getLineNr(n), isBroken));

            // Going to check for tenant-specific include-variants
            File scriptFolderFile = new File(methodScriptFile).getParentFile();
            if (scriptTenant == null && scriptFolderFile.exists() && scriptFolderFile.isDirectory()) {
                AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(methodScript) + "_*.xml");
                Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
                for (File f : files) {
                    deps.add(new Dependency(scriptFile, f.getAbsolutePath(), Dependency.METHOD_DEPENDENCY, getLineNr(n)));
                }
            }
        }
    }

    protected void findEntityDependencies(String scriptFile, String scriptTenant, String scriptFolder, List<Dependency> deps, Document tslDoc) {
        if (scriptFile.indexOf("entity") < 1) {
            return;
        }
        NodeList operations = tslDoc.getElementsByTagName("operation");
        NodeList messages = tslDoc.getElementsByTagName("message");
        for (int i = 0; i < operations.getLength(); i++) {
            Element n = (Element) operations.item(i);

            String operationScript = n.getAttribute("service");
            String operationValidationScript = n.getAttribute("validationService");
            if (operationScript == null || operationScript.equals("")) {
                continue;
            }

            if (scriptTenant != null) {
                // trying tenant-specific variant first
                String operationScriptFile = scriptFolder + File.separator + operationScript + "_" + scriptTenant + ".xml";

                // Check if exists
                if (new File(operationScriptFile).exists()) {
                    deps.add(new Dependency(scriptFile, operationScriptFile, Dependency.ENTITY_DEPENDENCY, getLineNr(n)));

                    // No need to try any other tenant-specific includes since
                    // we are tenant-specific in the first place
                    // Thus continue with next entity
                    continue;
                }
            }

            String operationScriptFile = scriptFolder + File.separator + operationScript + ".xml";
            String operationValidationScriptFile = null;
            if (operationValidationScript != null && !"".equals(operationValidationScript)) {
                operationValidationScriptFile = scriptFolder + File.separator + operationValidationScript + ".xml";
            }
            // Check if exists
            boolean isBroken = false;
            if (!new File(operationScriptFile).exists()) {
                isBroken = true;
            }

            deps.add(new Dependency(scriptFile, operationScriptFile, Dependency.ENTITY_DEPENDENCY, getLineNr(n), isBroken));

            // Going to check for tenant-specific include-variants
            if (scriptTenant == null) {
                File scriptFolderFile = new File(operationScriptFile).getParentFile();
                if (scriptFolderFile.exists() && scriptFolderFile.isDirectory()) {
                    AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(operationScript) + "_*.xml");
                    Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
                    for (File f : files) {
                        deps.add(new Dependency(scriptFile, f.getAbsolutePath(), Dependency.ENTITY_DEPENDENCY, getLineNr(n)));
                    }
                }

            }
            // Handle validation service
            if (operationValidationScriptFile != null) {
                isBroken = false;
                if (!new File(operationValidationScriptFile).exists()) {
                    isBroken = true;
                }

                deps.add(new Dependency(scriptFile, operationValidationScriptFile, Dependency.ENTITY_DEPENDENCY, getLineNr(n), isBroken));

                // Going to check for tenant-specific include-variants
                if (scriptTenant == null) {
                    File scriptFolderFile = new File(operationValidationScriptFile).getParentFile();
                    if (scriptFolderFile.exists() && scriptFolderFile.isDirectory()) {
                        AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(operationValidationScript) + "_*.xml");
                        Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
                        for (File f : files) {
                            deps.add(new Dependency(scriptFile, f.getAbsolutePath(), Dependency.ENTITY_DEPENDENCY, getLineNr(n)));
                        }
                    }

                }
            }

        }

        for (int i = 0; i < messages.getLength(); i++) {
            Element n = (Element) messages.item(i);

            String extendsAttr = n.getAttribute("extends");
            if (extendsAttr == null || extendsAttr.equals("") || !extendsAttr.startsWith("navajo://")) {
                continue;
            }

            String ext = extendsAttr.substring(9);
            // Entity versioning stuff
            String version = "0";
            if (ext.indexOf(".") != -1) {
                version = ext.substring(ext.indexOf(".") + 1, ext.indexOf("?") == -1 ? ext.length() : ext.indexOf("?"));
            }
            String rep = "." + version;
            ext = ext.replace(rep, "");

            String[] superEntities = ext.split(",");
            for (String superEntity : superEntities) {
                if (superEntity.indexOf('?') > 0) {
                    superEntity = superEntity.split("\\?")[0];
                }

                if (scriptTenant != null) {
                    // trying tenant-specific variant first
                    String operationScriptFile = scriptFolder + File.separator + superEntity + "_" + scriptTenant + ".xml";

                    // Check if exists
                    if (new File(operationScriptFile).exists()) {
                        deps.add(new Dependency(scriptFile, operationScriptFile, Dependency.ENTITY_DEPENDENCY, getLineNr(n)));

                        // No need to try any other tenant-specific includes
                        // since we are tenant-specific in the first place
                        // Thus continue with next entity
                        continue;
                    }
                }

                String superScriptFile = scriptFolder + File.separator + "entity" + File.separator + superEntity + ".xml";

                // Check if exists
                boolean isBroken = false;
                if (!new File(superScriptFile).exists()) {
                    isBroken = true;
                }

                deps.add(new Dependency(scriptFile, superScriptFile, Dependency.ENTITY_DEPENDENCY, getLineNr(n), isBroken));

                // Going to check for tenant-specific include-variants
                if (scriptTenant == null) {
                    File scriptFolderFile = new File(superScriptFile).getParentFile();
                    if (scriptFolderFile.exists() && scriptFolderFile.isDirectory()) {
                        AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(superEntity) + "_*.xml");
                        Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
                        for (File f : files) {
                            deps.add(new Dependency(scriptFile, f.getAbsolutePath(), Dependency.ENTITY_DEPENDENCY, getLineNr(n)));
                        }
                    }
                }
            }
        }

    }

    protected void findIncludeDependencies(String fullScriptPath, String scriptTenant, String scriptFolder, List<Dependency> deps, Document tslDoc) throws UserException {
        NodeList includes = tslDoc.getElementsByTagName("include");
        for (int i = 0; i < includes.getLength(); i++) {
            Element n = (Element) includes.item(i);
            String includedScript = n.getAttribute("script");
            if (includedScript == null || includedScript.equals("")) {
                throw new UserException(-1, "No script name found in include tag (missing or empty script attribute): " + n);
            }

            if (scriptTenant != null) {
                // trying tenant-specific variant first
                String includeScriptFile = scriptFolder + File.separator + includedScript + "_" + scriptTenant + ".xml";

                // Check if exists
                if (new File(includeScriptFile).exists()) {
                    deps.add(new Dependency(fullScriptPath, includeScriptFile, Dependency.INCLUDE_DEPENDENCY, getLineNr(n)));

                    // No need to try any other tenant-specific includes since
                    // we are tenant-specific in the first place
                    // Thus continue with next include
                    continue;
                }
            }

            String includeScriptFile = scriptFolder + File.separator + includedScript + ".xml";

            // Check if exists
            boolean isBroken = false;
            if (!new File(includeScriptFile).exists()) {
                isBroken = true;
            }
            deps.add(new Dependency(fullScriptPath, includeScriptFile, Dependency.INCLUDE_DEPENDENCY, getLineNr(n), isBroken));

            // Going to check for tenant-specific include-variants
            if (scriptTenant == null) {
                File scriptFolderFile = new File(includeScriptFile).getParentFile();
                if (scriptFolderFile.exists() && scriptFolderFile.isDirectory()) {
                    AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(includedScript) + "_*.xml");
                    Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
                    for (File f : files) {
                        deps.add(new Dependency(fullScriptPath, f.getAbsolutePath(), Dependency.INCLUDE_DEPENDENCY, getLineNr(n)));
                    }
                }
            }

        }
    }

    protected void findMapDependencies(String scriptFile, String scriptTenant, String scriptFolder, List<Dependency> deps, Document tslDoc) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();

        // Tsl scripts seem to have a slightly different xml model than
        // navascript...

        NodeList nodes = (NodeList) xPath.evaluate("//map[@object='com.dexels.navajo.adapter.NavajoMap']/field[@name='doSend']/expression", tslDoc.getDocumentElement(), XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); ++i) {
            Element expression = (Element) nodes.item(i);
            String navajoScript = expression.getAttribute("value");
            if (navajoScript.equals("")) {
                // Dealing with navascript, wher the value is a sub element
                // instead of an attribute of expression
                Element value = (Element) expression.getElementsByTagName("value").item(0);
                navajoScript = value.getTextContent();
            }
            if (navajoScript.contains("@")) {
                // Going to try to parse param ...
                try {
                    List<String> result = getParamValue(tslDoc, navajoScript);
                    for (String res : result) {
                        addScriptDependency(scriptFile, scriptTenant, deps, res, scriptFolder, getLineNr(expression), Dependency.NAVAJO_DEPENDENCY);
                    }
                } catch (XPathExpressionException e) {
                    // Unable to resolve param
                    logger.debug("Unable to resolve param {} in script {}", navajoScript, scriptFile);
                }
                
               

            } else if (navajoScript.startsWith("[/")) {
                // The navajo script is retrieved from the Indoc or database
                // result - not supported
                deps.add(new Dependency(scriptFile, "scripts/__unknown__.xml", Dependency.UNKNOWN_TYPE, getLineNr(expression)));
            } else {
                addScriptDependency(scriptFile, scriptTenant, deps, navajoScript, scriptFolder, getLineNr(expression), Dependency.NAVAJO_DEPENDENCY);
            }
        }

        // Entity map
        xPath = XPathFactory.newInstance().newXPath();
        // ((NodeList)
        // xPath.evaluate("//map[@object='com.dexels.navajo.entity.adapters.EntityMap']/field[@name='name']/expression",
        // tslDoc.getDocumentElement(), XPathConstants.NODESET)).getLength();

        nodes = (NodeList) xPath.evaluate("//map[@object='com.dexels.navajo.entity.adapters.EntityMap']/field[@name='name']/expression", tslDoc.getDocumentElement(), XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); ++i) {
            Element expression = (Element) nodes.item(i);
            String navajoScript = expression.getAttribute("value");
            if (navajoScript.equals("")) {
                // Dealing with navascript, wher the value is a sub element
                // instead of an attribute of expression
                Element value = (Element) expression.getElementsByTagName("value").item(0);
                navajoScript = value.getTextContent();
            }
            if (navajoScript.contains("@")) {
                // Going to try to parse param ...
                List<String> result = getParamValue(tslDoc, navajoScript);
                for (String res : result) {
                    addScriptDependency(scriptFile, scriptTenant, deps, res, scriptFolder, getLineNr(expression), Dependency.ENTITY_DEPENDENCY);
                }

            } else if (navajoScript.startsWith("[/")) {
                // The navajo script is retrieved from the Indoc or database
                // result - not supported
                deps.add(new Dependency(scriptFile, "scripts/__unknown__.xml", Dependency.UNKNOWN_TYPE, getLineNr(expression)));
            } else {
                addScriptDependency(scriptFile, scriptTenant, deps, navajoScript, scriptFolder, getLineNr(expression), Dependency.ENTITY_DEPENDENCY);
            }
        }

    }

    private List<String> getParamValue(Document tslDoc, String paramString) throws XPathExpressionException {
        String paramName = paramString.split("\\@")[1];
        paramName = paramName.substring(0, paramName.length() - 1);
        List<String> result = new ArrayList<String>();

        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xPath.evaluate("//param[@name='" + paramName + "']/expression", tslDoc.getDocumentElement(), XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); ++i) {
            Element expression = (Element) nodes.item(i);
            String scriptString = expression.getAttribute("value");
            if (scriptString.equals("null")) {
                continue;
            }
            if (scriptString.contains("@")) {
                // Going to try to recursively parse param ...
                if (scriptString.equals(paramString)) {
                    return null;
                }
                result.addAll(getParamValue(tslDoc, scriptString));
            } else {
                String cleanScript = scriptString.replace("'", "");
                result.add(cleanScript);

            }
        }
        return result;
    }

    private void addScriptDependency(String scriptFile, String scriptTenant, List<Dependency> deps, String navajoScript, String scriptFolder, int linenr, int type) {
        boolean isExpression = false;
        if (navajoScript.startsWith("[/")) {
            isExpression = true;
        }
        String cleanScript = navajoScript.replace("'", "");
        if (type == Dependency.ENTITY_DEPENDENCY) {
            cleanScript = "entity/" + cleanScript;
        }
        if (scriptTenant != null) {
            // trying tenant-specific variant first
            String navajoScriptFile = scriptFolder + File.separator + cleanScript + "_" + scriptTenant + ".xml";

            // Check if exists
            if (new File(navajoScriptFile).exists()) {
                deps.add(new Dependency(scriptFile, navajoScriptFile, type, linenr));

                // No need to try any other tenant-specific includes since we
                // are tenant-specific in the first place
                // Thus return
                return;
            }
        }
        
        String navajoScriptFilePartial = scriptFolder + File.separator + cleanScript;

        // Check if exists
        boolean isBroken = true;
        String navajoScriptFile = navajoScriptFilePartial + ".xml";
        if (new File(navajoScriptFile).exists() || isExpression) {
            isBroken = false;
        }
        if (isBroken) {
            // Try scala
            navajoScriptFile = navajoScriptFilePartial + ".scala";
            if (new File(navajoScriptFile).exists()) {
                isBroken = false;
            }
        }

        deps.add(new Dependency(scriptFile, navajoScriptFile, type, linenr, isBroken));

        // Going to check for tenant-specific include-variants
        if (scriptTenant == null && !isExpression) {
            File scriptFolderFile = new File(scriptFolder, cleanScript).getParentFile();
            if (scriptFolderFile.exists() && scriptFolderFile.isDirectory()) {
                AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(cleanScript) + "_*.xml");
                Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
                for (File f : files) {
                    deps.add(new Dependency(scriptFile, f.getAbsolutePath(), type, linenr));
                }
            }

        }

    }

    private int getLineNr(Element n) {
        String linenr = n.getAttribute("linenr");
        if (linenr.equals("")) {
            return -1;
        }
        return Integer.valueOf(linenr) + 1; // For some reason the +1 is needed
                                            // here
    }

}