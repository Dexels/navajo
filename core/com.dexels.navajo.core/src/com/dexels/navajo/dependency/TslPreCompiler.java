package com.dexels.navajo.dependency;

/**
 * <p>Title: Navajo Product Project</p>"
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$get
 */

/**
 *
 * $columnValue('AAP') -> Object o = contextMap.getColumnValue('AAP') -> symbolTable.put("$columnValue('AAP')", o);
 *laz
 *
 *
 */
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
import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.mapping.compiler.meta.NavajoDependency;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslPreCompiler {

    private NavajoIOConfig navajoIOConfig = null;
    private final static Logger logger = LoggerFactory.getLogger(TslPreCompiler.class);

    public TslPreCompiler(NavajoIOConfig config) {
        navajoIOConfig = config;
    }

    public void setIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = config;
    }

    public void clearIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = null;
    }
    
    public void getAllDependencies(String script, String scriptPath, String workingPath, List<Dependency> deps,
            String scriptTenant) throws Exception {
        final String extension = ".xml";
        String fullScriptPath = null;
        Document tslDoc = null;
        InputStream is = null;

      
        fullScriptPath = scriptPath + "/" + script + extension;
        

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
            throw e;
        }

        tslDoc = XMLDocumentUtils.createDocument(is, false);
        findIncludeDependencies(scriptPath, deps, tslDoc);
        findNavajoDependencies(scriptPath, deps, tslDoc);
        
        
    }

    public void getIncludeDependencies(String script, String scriptPath, String workingPath, List<Dependency> deps,
            String scriptTenant) throws Exception {

        final String extension = ".xml";
        String fullScriptPath = null;
        Document tslDoc = null;
        InputStream is = null;

        fullScriptPath = scriptPath + "/" + script + extension;

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
            throw e;
        }

        tslDoc = XMLDocumentUtils.createDocument(is, false);
        findIncludeDependencies(scriptPath, deps, tslDoc);

    }

    private void findIncludeDependencies(String scriptPath, List<Dependency> deps, Document tslDoc)
            throws UserException {
        NodeList includes = tslDoc.getElementsByTagName("include");
        int included = 0;
        for (int i = 0; i < includes.getLength(); i++) {
            Node n = includes.item(i);
            String script = ((Element) n).getAttribute("script");
            if (script == null || script.equals("")) {
                throw new UserException(-1, "No script name found in include tag (missing or empty script attribute): " + n);
            }
            File scriptPathFile = new File(scriptPath, FilenameUtils.getPath(script));

            // Going to check for tenant-specific include-variants
            AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(script) + "*.xml");
            Collection<File> files = FileUtils.listFiles(scriptPathFile, fileFilter, null);
            for (File f : files) {
                String includeScriptPath = f.getAbsolutePath().substring(scriptPath.length());
                String includeScript = includeScriptPath.substring(1, includeScriptPath.indexOf(".xml"));
                deps.add(new IncludeDependency(IncludeDependency.getScriptTimeStamp(includeScript), script,
                        includeScript));
            }
            included++;

            if (included > 1000) {
                throw new UserException(-1, "Too many included scripts!!!");
            }
        }
    }

    public void getNavajoDependencies(String script, String scriptPath, String workingPath, List<Dependency> deps,
            String scriptTenant) throws Exception {

        final String extension = ".xml";
        String fullScriptPath = null;
        Document tslDoc = null;
        InputStream is = null;

       
        fullScriptPath = scriptPath + "/" + script + extension;
        

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
            throw e;
        }

        tslDoc = XMLDocumentUtils.createDocument(is, false);

        // TransformerFactory tf = TransformerFactory.newInstance();
        // Transformer transformer = tf.newTransformer();
        // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        // StringWriter writer = new StringWriter();
        // transformer.transform(new DOMSource(tslDoc), new
        // StreamResult(writer));
        // String output = writer.getBuffer().toString().replaceAll("\n|\r","");
        //

        findNavajoDependencies(script, deps, tslDoc);

    }

    private void findNavajoDependencies(String script, List<Dependency> deps, Document tslDoc) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xPath.evaluate(
                "//map[@object='com.dexels.navajo.adapter.NavajoMap']/field[@name='doSend']/expression/value",
                tslDoc.getDocumentElement(), XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); ++i) {
            Element value = (Element) nodes.item(i);
            String scriptString = value.getTextContent();
            if (scriptString.contains("@")) {
                // Going to try to parse param ...
                List<String> result = getParamValue(tslDoc, scriptString);
                for (String res : result) {
                    deps.add(new NavajoDependency(NavajoDependency.getScriptTimeStamp(res), script, res));
                }

            } else {
                String cleanScript = scriptString.replace("'", "");
                deps.add(new NavajoDependency(NavajoDependency.getScriptTimeStamp(cleanScript), script, cleanScript));
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
                    logger.warn("Recursive param detected!");
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

}