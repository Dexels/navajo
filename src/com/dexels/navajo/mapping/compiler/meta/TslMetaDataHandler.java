/*
 * Created on Jun 14, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.mapping.compiler.meta;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.mapping.compiler.meta.MetaDataListener;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TslMetaDataHandler implements MetaDataListener {

    private final TreeMap callsScriptMap = new TreeMap();
    private final Map calledByScriptMap = new TreeMap();
    private final Map includesScriptMap = new TreeMap();
    private final Map includedByScriptMap = new TreeMap();

    private final Map usesAdapter = new HashMap();
    private final Map adapterIsUsedByScript = new HashMap();
    private final Set scriptList = new TreeSet();
    
    public void scriptCalls(String source, String dest, String[] requires) {
//        System.err.println("Script: "+source +" calls scriptL "+dest);
//        for (int i = 0; i < requires.length; i++) {
//            System.err.println("Required: "+requires[i]);
//        }
        scriptList.add(source);
        addToMap(callsScriptMap,source, dest);
        addToMap(calledByScriptMap, dest, source);
    }

    public void scriptIncludes(String source, String dest) {
//        System.err.println("Script: "+source+" includes: "+dest);
        scriptList.add(source);
        addToMap(includesScriptMap,source, dest);
        addToMap(includedByScriptMap, dest, source);
    }

    public void scriptUsesAdapter(String source, String adapterName) {
//        System.err.println("Script: "+source +" uses adapter: "+adapterName);
        scriptList.add(source);
        addToMap(usesAdapter,source, adapterName);
        addToMap(adapterIsUsedByScript, adapterName, source);
   }

    public void scriptUsesField(String source, String adapterName, String fieldName) {
//        System.err.println("Script: "+source +" with adapter: "+adapterName+" uses field: "+fieldName);
    }

    public void resetMetaData() {
        System.err.println("reset");
    }
    
    private void addToMap(Map m, String key, String value) {
        TreeSet current = (TreeSet)m.get(key);
        if (current==null) {
            current = new TreeSet();
            m.put(key, current);
        }
        current.add(value);
    }

    private void removeFromMap(Map m, String key) {
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
    
    public Set getScriptCallsSet(String name) {
        return (Set)callsScriptMap.get(name);
    }
    public Set getScriptCalledBySet(String name) {
        return (Set)calledByScriptMap.get(name);
    }
    public Set getScriptIncludesSet(String name) {
        return (Set)includesScriptMap.get(name);
    }
    public Set getScriptIncludedBySet(String name) {
        return (Set)includedByScriptMap.get(name);
    }

    public Set getScriptUsesAdaptersSet(String name) {
        return (Set)usesAdapter.get(name);
    }
    public Set getAdaptersUsedByScriptSet(String name) {
        return (Set)adapterIsUsedByScript.get(name);
    }
    
    public void loadScriptData(InputStream in) {
        Reader fr = null;
        try {
            fr = new InputStreamReader(in);
            XMLElement e = new CaseSensitiveXMLElement();
            e.parseFromReader(fr);
            parseMetaData(e);
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (fr!=null) {
                try {
                    fr.close();
                } catch (IOException e1) {
                      e1.printStackTrace();
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
        Vector v = e.getChildren();
        for (Iterator iter = v.iterator(); iter.hasNext();) {
            XMLElement element = (XMLElement) iter.next();
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
        Vector v = e.getChildren();
        for (Iterator iter = v.iterator(); iter.hasNext();) {
            XMLElement element = (XMLElement) iter.next();
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
            e.printStackTrace();
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
        for (Iterator iter = scriptList.iterator(); iter.hasNext();) {
            String element = (String) iter.next();
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
            Set s = (Set)usesAdapter.get(element);
            if (s==null) {
                return;
            }
            for (Iterator iter = s.iterator(); iter.hasNext();) {
                String adapter = (String) iter.next();
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
        Set s = (Set)calledByScriptMap.get(element);
        if (s==null) {
            return;
        }
        for (Iterator iter = s.iterator(); iter.hasNext();) {
            String include = (String) iter.next();
            XMLElement xnincl = new CaseSensitiveXMLElement();
            xnincl.setName("calledby");
            xnincl.setAttribute("name",include);
            xn.addChild(xnincl);
        }
    }

    private void addCalls(XMLElement xn, String element) {
        Set s = (Set)callsScriptMap.get(element);
        if (s==null) {
            return;
        }
        for (Iterator iter = s.iterator(); iter.hasNext();) {
            String include = (String) iter.next();
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
        // TODO Auto-generated method stub
        Set s = (Set)includesScriptMap.get(element);
        if (s==null) {
            return;
        }
        for (Iterator iter = s.iterator(); iter.hasNext();) {
            String include = (String) iter.next();
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
        Set s = (Set)includedByScriptMap.get(element);
        if (s==null) {
            return;
        }
        for (Iterator iter = s.iterator(); iter.hasNext();) {
            String include = (String) iter.next();
            XMLElement xnincl = new CaseSensitiveXMLElement();
            xnincl.setName("includedby");
            xnincl.setAttribute("name",include);
            xn.addChild(xnincl);
            
        }
        
    }

    
    private XMLElement toXml(Map m,String topTagName, String tagname, String elementName, String attribute) {
        XMLElement x = new CaseSensitiveXMLElement();
        x.setName(topTagName);
        Set s = m.keySet();
        for (Iterator iter = s.iterator(); iter.hasNext();) {
            String element = (String) iter.next();
            TreeSet value = (TreeSet)m.get(element);
            XMLElement xc = new CaseSensitiveXMLElement();
            xc.setName(tagname);
            xc.setAttribute("name", element);
            x.addChild(xc);
            for (Iterator iterator = value.iterator(); iterator.hasNext();) {
                String ee = (String) iterator.next();
                XMLElement xce = new CaseSensitiveXMLElement();
                xce.setName(elementName);
                xce.setAttribute(attribute, ee);
                xc.addChild(xce);
            }
        }
        return x;
    }

    public void removeScriptMetadata(String script) {
        removeFromMap(callsScriptMap, script);
        removeFromMap(includedByScriptMap, script);
        removeFromMap(includesScriptMap, script);
        removeFromMap(usesAdapter, script);
    }
}
