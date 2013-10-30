/*
 * Created on Jun 14, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.mapping.compiler.meta;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TslMetaDataHandler implements MetaDataListener {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TslMetaDataHandler.class);
    private final Map<String,TreeSet<String>> callsScriptMap = new TreeMap<String,TreeSet<String>>();
    private final Map<String,TreeSet<String>> calledByScriptMap = new TreeMap<String,TreeSet<String>>();
    private final Map<String,TreeSet<String>> includesScriptMap = new TreeMap<String,TreeSet<String>>();
    private final Map<String,TreeSet<String>> includedByScriptMap = new TreeMap<String,TreeSet<String>>();

    private final Map<String, TreeSet<String>> usesAdapter = new HashMap<String, TreeSet<String>>();
    private final Map<String, TreeSet<String>> adapterIsUsedByScript = new HashMap<String, TreeSet<String>>();
    private final Set<String> scriptList = new TreeSet<String>();
    
    @Override
	public void scriptCalls(String source, String dest, String[] requires) {
//        System.err.println("Script: "+source +" calls scriptL "+dest);
//        for (int i = 0; i < requires.length; i++) {
//            System.err.println("Required: "+requires[i]);
//        }
        scriptList.add(source);
        addToMap(callsScriptMap,source, dest);
        addToMap(calledByScriptMap, dest, source);
    }

    @Override
	public void scriptIncludes(String source, String dest) {
//        System.err.println("Script: "+source+" includes: "+dest);
        scriptList.add(source);
        addToMap(includesScriptMap,source, dest);
        addToMap(includedByScriptMap, dest, source);
    }

    @Override
	public void scriptUsesAdapter(String source, String adapterName) {
//        System.err.println("Script: "+source +" uses adapter: "+adapterName);
        scriptList.add(source);
        addToMap(usesAdapter,source, adapterName);
        addToMap(adapterIsUsedByScript, adapterName, source);
   }

    @Override
	public void scriptUsesField(String source, String adapterName, String fieldName) {
//        System.err.println("Script: "+source +" with adapter: "+adapterName+" uses field: "+fieldName);
    }

    @Override
	public void resetMetaData() {
        System.err.println("reset");
    }
    
    private void addToMap(Map<String,TreeSet<String>> m, String key, String value) {
        TreeSet<String> current = m.get(key);
        if (current==null) {
            current = new TreeSet<String>();
            m.put(key, current);
        }
        current.add(value);
    }

    private void removeFromMap(Map<String,TreeSet<String>> m, String key) {
        m.remove(key);
    }

    public XMLElement getScriptCalls() {
        return toXml(callsScriptMap, "calls", "script", "method", "name");
    }

    public XMLElement getScriptCalledBy() {
        return toXml(calledByScriptMap, "calls", "script", "method", "name");
    }
    public XMLElement getScriptIncludes() {
        return toXml(includesScriptMap, "include", "script", "include", "name");
    }
    public XMLElement getScriptIncludedBy() {
        return toXml(includedByScriptMap, "include", "include", "includedBy", "name");
    }

    public XMLElement getScriptUsesAdapters() {
        return toXml(usesAdapter, "calls", "script", "adapter", "class");
    }
    public XMLElement getAdaptersUsedByScript() {
        return toXml(adapterIsUsedByScript, "calls", "adapter", "script", "name");
    }
    
    public TreeSet<String> getScriptCallsSet(String name) {
        return callsScriptMap.get(name);
    }
    public TreeSet<String> getScriptCalledBySet(String name) {
        return calledByScriptMap.get(name);
    }
    public TreeSet<String> getScriptIncludesSet(String name) {
        return includesScriptMap.get(name);
    }
    public TreeSet<String> getScriptIncludedBySet(String name) {
        return includedByScriptMap.get(name);
    }

    public Set<String> getScriptUsesAdaptersSet(String name) {
        return usesAdapter.get(name);
    }
    public TreeSet<String> getAdaptersUsedByScriptSet(String name) {
        return adapterIsUsedByScript.get(name);
    }
    
    public void loadScriptData(InputStream in) {
        Reader fr = null;
        try {
            fr = new InputStreamReader(in);
            XMLElement e = new CaseSensitiveXMLElement();
            e.parseFromReader(fr);
            parseMetaData(e);
        } catch (IOException e) {
        	logger.error("Error: ", e);
        }  finally {
            if (fr!=null) {
                try {
                    fr.close();
                } catch (IOException e1) {
                	logger.error("Error: ", e1);
                }
            }
        }
        
    }
    
    /**
     * @param e
     */
    private void parseMetaData(XMLElement e) {
        if (!"metadata".equals(e.getName())) {
           System.err.println("Unknown root tag in metadata: "+e.getName());
           return;
        }
        Vector<XMLElement> v = e.getChildren();
        for (Iterator<XMLElement> iter = v.iterator(); iter.hasNext();) {
            XMLElement element = iter.next();
            if (!"script".equals(element.getName())) {
                System.err.println("Unknown tag within metadata: "+element.getName());
                return;
            }

            String scriptName = element.getStringAttribute("name");
            scriptList.add(scriptName);
            parseScriptData(scriptName, element);

        }
    }

    /**
     * @param element
     */
    private void parseScriptData(String scriptName, XMLElement e) {
        Vector<XMLElement> v = e.getChildren();
        for (Iterator<XMLElement> iter = v.iterator(); iter.hasNext();) {
            XMLElement element =  iter.next();
            if ("adapters".equals(element.getName())) {
                String adapterValue = element.getStringAttribute("name");
                addToMap(usesAdapter,scriptName, adapterValue);
                continue;
            }
            if ("calledby".equals(element.getName())) {
                String adapterValue = element.getStringAttribute("name");
                addToMap(calledByScriptMap,scriptName, adapterValue);
                continue;
            }
            if ("calls".equals(element.getName())) {
                String adapterValue = element.getStringAttribute("name");
                addToMap(callsScriptMap,scriptName, adapterValue);
                continue;
            }
            if ("includes".equals(element.getName())) {
                String adapterValue = element.getStringAttribute("name");
                addToMap(includesScriptMap,scriptName, adapterValue);
                continue;
            }
            if ("includedby".equals(element.getName())) {
                String adapterValue = element.getStringAttribute("name");
                addToMap(includedByScriptMap,scriptName, adapterValue);
                continue;
            }
        }
    }

    public void flushAll() {
        adapterIsUsedByScript.clear();
        calledByScriptMap.clear();
        callsScriptMap.clear();
        includedByScriptMap.clear();
        includesScriptMap.clear();
        usesAdapter.clear();
        scriptList.clear();
    }

    public void parse(File f) {
        flushAll();
        FileReader fr = null;
        XMLElement xe = new CaseSensitiveXMLElement();
        try {
            fr = new FileReader(f);
            xe.parseFromReader(fr);
        } catch (IOException e) {
        	logger.error("Error: ", e);
        } finally {
            if (fr!=null) {
                try {
                    fr.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
    public XMLElement createTotalXML() {
        XMLElement x = new CaseSensitiveXMLElement();
        x.setName("metadata");
        for (Iterator<String> iter = scriptList.iterator(); iter.hasNext();) {
            String element =  iter.next();
            addScriptElement(x,element);
        }
        return x;
    }
    
    public XMLElement getScriptMetadata(String scriptName) {
        XMLElement x = new CaseSensitiveXMLElement();
        x.setName("metadata");
        addScriptElement(x,scriptName);
        return x;
    }
    
    /**
     * @param x
     * @param element
     */
    private void addScriptElement(XMLElement x, String element) {
        XMLElement xn = new CaseSensitiveXMLElement();
        xn.setName("script");
        x.addChild(xn);
        xn.setAttribute("name", element);
        addAdapters(xn,element);
        addCalls(xn,element);
        addCalledBy(xn,element);
              addIncludes(xn,element);
        addIncomingIncludes(xn,element);
    }

    /**
     * @param xn
     * @param element
     */
    private void addAdapters(XMLElement xn, String element) {
            Set<String> s = usesAdapter.get(element);
            if (s==null) {
                return;
            }
            for (Iterator<String> iter = s.iterator(); iter.hasNext();) {
                String adapter = iter.next();
                XMLElement xnincl = new CaseSensitiveXMLElement();
                xnincl.setName("adapters");
                xnincl.setAttribute("name",adapter);
                xn.addChild(xnincl);
            }

    }

    /**
     * @param xn
     * @param element
     */
    private void addCalledBy(XMLElement xn, String element) {
        TreeSet<String> s = calledByScriptMap.get(element);
        if (s==null) {
            return;
        }
        for (Iterator<String> iter = s.iterator(); iter.hasNext();) {
            String include = iter.next();
            XMLElement xnincl = new CaseSensitiveXMLElement();
            xnincl.setName("calledby");
            xnincl.setAttribute("name",include);
            xn.addChild(xnincl);
        }
    }

    private void addCalls(XMLElement xn, String element) {
    	TreeSet<String> s = callsScriptMap.get(element);
        if (s==null) {
            return;
        }
        for (Iterator<String> iter = s.iterator(); iter.hasNext();) {
            String include = iter.next();
            XMLElement xnincl = new CaseSensitiveXMLElement();
            xnincl.setName("calls");
            xnincl.setAttribute("name",include);
            xn.addChild(xnincl);
        }
    }

    
    /**
     * @param xn
     * @param element
     */
    private void addIncludes(XMLElement xn, String element) {
        TreeSet<String> s = includesScriptMap.get(element);
        if (s==null) {
            return;
        }
        for (Iterator<String> iter = s.iterator(); iter.hasNext();) {
            String include = iter.next();
            XMLElement xnincl = new CaseSensitiveXMLElement();
            xnincl.setName("include");
            xnincl.setAttribute("name",include);
            xn.addChild(xnincl);
            
        }
    }

    /**
     * @param xn
     * @param element
     */
    private void addIncomingIncludes(XMLElement xn, String element) {
        TreeSet<String> s = includedByScriptMap.get(element);
        if (s==null) {
            return;
        }
        for (Iterator<String> iter = s.iterator(); iter.hasNext();) {
            String include = iter.next();
            XMLElement xnincl = new CaseSensitiveXMLElement();
            xnincl.setName("includedby");
            xnincl.setAttribute("name",include);
            xn.addChild(xnincl);
            
        }
        
    }

    
    private XMLElement toXml(Map<String,TreeSet<String>> m,String topTagName, String tagname, String elementName, String attribute) {
        XMLElement x = new CaseSensitiveXMLElement();
        x.setName(topTagName);
        Set<Entry<String,TreeSet<String>>> s = m.entrySet();
        for (Iterator<Entry<String,TreeSet<String>>> iter = s.iterator(); iter.hasNext();) {
        	Entry<String,TreeSet<String>> e = iter.next();
            String element = e.getKey();
            TreeSet<String> value = e.getValue();
            XMLElement xc = new CaseSensitiveXMLElement();
            xc.setName(tagname);
            xc.setAttribute("name", element);
            x.addChild(xc);
            for (Iterator<String> iterator = value.iterator(); iterator.hasNext();) {
                String ee = iterator.next();
                XMLElement xce = new CaseSensitiveXMLElement();
                xce.setName(elementName);
                xce.setAttribute(attribute, ee);
                xc.addChild(xce);
            }
        }
        return x;
    }

    @Override
	public void removeScriptMetadata(String script) {
        removeFromMap(callsScriptMap, script);
        removeFromMap(includedByScriptMap, script);
        removeFromMap(includesScriptMap, script);
        removeFromMap(usesAdapter, script);
    }
}
