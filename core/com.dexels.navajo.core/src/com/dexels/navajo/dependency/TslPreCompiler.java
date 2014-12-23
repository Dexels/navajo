package com.dexels.navajo.dependency;


import java.io.ByteArrayInputStream;
import java.io.File;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslPreCompiler {

    private NavajoIOConfig navajoIOConfig = null;
    private final static Logger logger = LoggerFactory.getLogger(TslPreCompiler.class);

    public void setIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = config;
    }

    public void clearIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = null;
    }
    
   

    public void getAllDependencies(String script, String scriptFolder, List<Dependency> deps, String scriptTenant)
            throws XPathExpressionException, UserException {
        final String extension = ".xml";
        String fullScriptPath = null;
        Document tslDoc = null;
        InputStream is = null;

        fullScriptPath = scriptFolder + File.separator + script + ".xml";

        try {
            // Check for metascript.
            if (MapMetaData.isMetaScript(fullScriptPath)) {
                MapMetaData mmd = MapMetaData.getInstance();
                InputStream metais = navajoIOConfig.getScript(script, scriptTenant, extension);

                String intermed = mmd.parse(fullScriptPath, metais);
                metais.close();
                is = new ByteArrayInputStream(intermed.getBytes());
            } else {
                is = navajoIOConfig.getScript(script, scriptTenant, extension);
            }

        } catch (Exception e) {
            logger.error("Pre-compiler failed!", e);
            throw new UserException(-1, "Exception on pre-compiling script: " + script);
        }

        tslDoc = XMLDocumentUtils.createDocument(is, false);
        getAllDependencies(fullScriptPath, scriptFolder, deps, tslDoc);
    }
    
    protected void getAllDependencies(String scriptFile, String scriptFolder, List<Dependency> deps, Document tslDoc)
            throws UserException, XPathExpressionException {
        findIncludeDependencies(scriptFile, scriptFolder, deps, tslDoc);
        findNavajoDependencies(scriptFile, scriptFolder, deps, tslDoc);
        findMethodDependencies(scriptFile, scriptFolder, deps, tslDoc);
        findEntityDependencies(scriptFile, scriptFolder, deps, tslDoc);
        
    }

    protected void findMethodDependencies(String scriptFile, String scriptFolder, List<Dependency> deps, Document tslDoc) {
        NodeList methods = tslDoc.getElementsByTagName("method");
        for (int i = 0; i < methods.getLength(); i++) {
            Node n = methods.item(i);
            String methodScript = ((Element) n).getAttribute("name");
            if (methodScript == null || methodScript.equals("")) {
                return;
            }
            String methodScriptFile = scriptFolder + File.separator + methodScript + ".xml";

            // Check if exists
            if (!new File(methodScriptFile).exists()) {
                deps.add(new Dependency(scriptFile, methodScriptFile, Dependency.BROKEN_DEPENDENCY));
                continue;
            }
            deps.add(new Dependency(scriptFile, methodScriptFile, Dependency.METHOD_DEPENDENCY));

            // Going to check for tenant-specific include-variants
            File scriptFolderFile = new File(methodScriptFile).getParentFile();
            AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(methodScript) + "_*.xml");
            Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
            for (File f : files) {
                deps.add(new Dependency(scriptFile, f.getAbsolutePath(), Dependency.METHOD_DEPENDENCY));
            }
        }
    }
    
    protected void findEntityDependencies(String scriptFile, String scriptFolder, List<Dependency> deps,  Document tslDoc) {
        NodeList operations = tslDoc.getElementsByTagName("operation");
        for (int i = 0; i < operations.getLength(); i++) {
            Node n = operations.item(i);
            String operationScript = ((Element) n).getAttribute("service");
            if (operationScript == null || operationScript.equals("")) {
                return;
            }

            String operationScriptFile = scriptFolder + File.separator + operationScript + ".xml";

            // Check if exists
            if (!new File(operationScriptFile).exists()) {
                deps.add(new Dependency(scriptFile, operationScriptFile, Dependency.BROKEN_DEPENDENCY));
                continue;
            }
            deps.add(new Dependency(scriptFile, operationScriptFile, Dependency.ENTITY_DEPENDENCY));

            // Going to check for tenant-specific include-variants
            File scriptFolderFile = new File(operationScriptFile).getParentFile();
            AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(operationScript) + "_*.xml");
            Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
            for (File f : files) {
                deps.add(new Dependency(scriptFile, f.getAbsolutePath(), Dependency.ENTITY_DEPENDENCY));
            }
        }
    }

    protected void findIncludeDependencies(String fullScriptPath, String scriptFolder, List<Dependency> deps, Document tslDoc)
            throws UserException {
        NodeList includes = tslDoc.getElementsByTagName("include");
        int included = 0;
        for (int i = 0; i < includes.getLength(); i++) {
            Node n = includes.item(i);
            String includedScript = ((Element) n).getAttribute("script");
            if (includedScript == null || includedScript.equals("")) {
                throw new UserException(-1, "No script name found in include tag (missing or empty script attribute): " + n);
            }
            
            String includeScriptFile = scriptFolder + File.separator + includedScript + ".xml";
            
            // Check if exists
            if (! new File(includeScriptFile).exists()) {
                deps.add(new Dependency(fullScriptPath, includeScriptFile, Dependency.BROKEN_DEPENDENCY));
                continue;
            }
            deps.add(new Dependency(fullScriptPath, includeScriptFile, Dependency.INCLUDE_DEPENDENCY));
            

            // Going to check for tenant-specific include-variants
            File scriptFolderFile = new File(includeScriptFile).getParentFile();
            AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(includedScript) + "_*.xml");
            Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
            for (File f : files) {
                deps.add(new Dependency(fullScriptPath, f.getAbsolutePath(), Dependency.INCLUDE_DEPENDENCY));
            }
            included++;

            if (included > 1000) {
                return;
            }
        }
    }



    protected void findNavajoDependencies(String scriptFile, String scriptFolder, List<Dependency> deps, Document tslDoc)
            throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xPath.evaluate(
                "//map[@object='com.dexels.navajo.adapter.NavajoMap']/field[@name='doSend']/expression/value",
                tslDoc.getDocumentElement(), XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); ++i) {
            Element value = (Element) nodes.item(i);
            String navajoScript = value.getTextContent();
            if (navajoScript.contains("@")) {
                // Going to try to parse param ...
                List<String> result = getParamValue(tslDoc, navajoScript);
                for (String res : result) {
                    addNavajoDependency(scriptFile, deps, res, scriptFolder);
                }

            } else {
                addNavajoDependency(scriptFile, deps, navajoScript, scriptFolder);

            }
        }
    }

    private List<String> getParamValue(Document tslDoc, String paramString) throws XPathExpressionException {
        String paramName = paramString.split("\\@")[1];
        paramName = paramName.substring(0, paramName.length() - 1);
        List<String> result = new ArrayList<String>();

        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xPath.evaluate("//param[@name='" + paramName + "']/expression",
                tslDoc.getDocumentElement(), XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); ++i) {
            Element expression = (Element) nodes.item(i);
            String scriptString = expression.getAttribute("value");
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
    
    private void addNavajoDependency(String scriptFile, List<Dependency> deps, String navajoScript, String scriptFolder) {
        String cleanScript = navajoScript.replace("'", "");
        String navajoScriptFile = scriptFolder + File.separator + cleanScript + ".xml";

        // Check if exists
        if (!new File(navajoScriptFile).exists()) {
            deps.add(new Dependency(scriptFile, navajoScriptFile, Dependency.BROKEN_DEPENDENCY));
            return;
        }

        deps.add(new Dependency(scriptFile, navajoScriptFile, Dependency.NAVAJO_DEPENDENCY));

        // Going to check for tenant-specific include-variants
        File scriptFolderFile = new File(scriptFile).getParentFile();
        AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(cleanScript) + "_*.xml");
        Collection<File> files = FileUtils.listFiles(scriptFolderFile, fileFilter, null);
        for (File f : files) {
            deps.add(new Dependency(scriptFile, f.getAbsolutePath(), Dependency.NAVAJO_DEPENDENCY));
        }
    }


}