package com.dexels.navajo.tipi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
//import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
//import com.dexels.navajo.tipi.tipixml.XMLElement;
//import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiScanner {

	
	private static HashMap results = new HashMap();
	private static HashMap revResults = new HashMap();

	private static HashSet preloadSet = new HashSet();
	private static int conflictcount = 0;

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws Exception {

		bindPreload("cancel", "'Annuleren'");
		bindPreload("cancel", "'annuleren'");
		bindPreload("do_cancel", "'annuleer'");
		bindPreload("close_screen", "'sluit scherm'");
		bindPreload("change", "'wijzigen'");		
		bindPreload("save", "'Opslaan'");
		bindPreload("save", "'opslaan'");
		bindPreload("close", "'sluiten'");
		bindPreload("add", "'voeg toe'");
		bindPreload("search", "'zoek'");
		bindPreload("export", "'exporteren'");
		bindPreload("help", "'Help'");
		bindPreload("dash", "'-'");
		
		bindPreload("ok", "'Ok'");
		bindPreload("ok", "'ok'");
		
		dumpCurrent("c:/tipi_preload","nl",".properties");
		
		preloadSet.addAll(revResults.keySet());

		System.err.println("preload: "+preloadSet);

//		File f = new File("c:/projects/SportlinkClubStudio/tipi/activityFrame.xml");
		File folder = new File("c:/projects/SportlinkClubEcho/tipi");
		scanFolder(folder);
//		scanFile(f);
		
//		for (Iterator iter = results.keySet().iterator(); iter.hasNext();) {
//			String element = (String) iter.next();
//			System.err.println("Result: "+element+" results: "+results.get(element));
//		}
		
		
		
		
		System.err.println("Size: "+results.size()+" conflicts: "+conflictcount);
		dumpCurrent("c:/tipi_lang","nl","");
	}

	private static void dumpCurrent(String filename, String locale, String extension) throws IOException {
		FileWriter fw = new FileWriter(filename+"_"+locale+".properties");
		Set s = results.keySet();
		SortedSet ss = new TreeSet(s);
		for (Iterator iter =  ss.iterator(); iter.hasNext();) {
			
			String element = (String) iter.next();
			fw.write(element+" "+results.get(element)+"\n");
			System.err.println("Result: "+element+" results: "+results.get(element));
		}
		fw.flush();
		fw.close();

		fw = new FileWriter(filename+"_"+locale+".sql");
		for (Iterator iter = ss.iterator(); iter.hasNext();) {
			
			String element = (String) iter.next();
			String line = "INSERT INTO propertydescription (descriptionid,locale,name,objectid,objecttype,description,lastupdate,updateby,context,sublocale) VALUES ( propertydescription_seq.nextval, 'nl', '"+element+"', null, null, "+results.get(element) + ", sysdate, 'SLCASPUSER','tipi','club' );";
			//			System.err.println("Result: "+element+" results: "+results.get(element));
			fw.write(line+"\n");

		}
		fw.flush();
		fw.close();

				
	}

	public static void scanFolder(File dir) throws  IOException, NavajoException {
		File[] contents = dir.listFiles();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].getName().endsWith(".xml")) {
				scanFile(contents[i]);
			}
		}
	}

	private static void scanFile(File file) throws  IOException, NavajoException {
//		XMLElement xe = new CaseSensitiveXMLElement();
		System.err.println("Checking file: "+file);
				FileInputStream fr = new FileInputStream(file);
		
		Document d;
		try {
			d = XMLDocumentUtils.createDocument(fr, false);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return;
		}
//		xe.parseFromReader(fr);
        NodeList nl = d.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (n instanceof Element) {
				scanElement((Element)n,file.getName());
			}
        }
        fr.close();
        File filePtr = new File(file.getAbsolutePath());
		System.err.println("> "+filePtr);
        FileWriter fw = new FileWriter(filePtr,false);
		XMLDocumentUtils.write(d, fw,false);
		fw.flush();
		fw.close();
	}

	private static void scanElement(Element xe,String filename) {
		NamedNodeMap ee = xe.getAttributes();
		for (int i = 0; i < ee.getLength(); i++) {
			String next = ee.item(i).getNodeName();
			String value = xe.getAttribute(next);
			checkElement(filename,xe,next,value);
		}
//		while(ee.hasMoreElements()) {
//			String next = (String)ee.nextElement();
//			String value = xe.getStringAttribute(next);
//			checkElement(filename,xe,next,value);
//		}
		NodeList nl = xe.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i) instanceof Element) {
				scanElement((Element)nl.item(i),filename);
			}
		}
//		Vector v = xe.getChildren();
//		
//		for (int i = 0; i < v.size(); i++) {
//			XMLElement child = (XMLElement)v.get(i);
//			scanElement(child,filename);
//		}
	}

	public static void bindPreload(String key,String value) {
		if (!results.containsKey(key)) {
			results.put(key, value);
		}
		if (!revResults.containsKey(value)) {
			revResults.put(value, key);
		}
	}
	
	private static void checkElement(String filename,Element xe, String key, String value) {
		if (key.equals("text") || key.equals("tooltip")|| key.equals("title") || key.equals("label") ) {

			if (checkValue(value)) {

				if (revResults.containsKey(value) || preloadSet.contains(value)) {
					System.err.println("Already found: "+value);
					xe.setAttribute(key,"{label:/"+revResults.get(value)+"}");
				} else {
					String generatedKey = generateKey(xe,filename,key);
					while(results.containsKey(generatedKey)) {
						conflictcount++;
//						System.err.println("Oh dear: conflict");
						generatedKey = randomizeKey(generatedKey);
					}
					System.err.println("Adding to results: "+generatedKey);
					results.put(generatedKey, value);
					revResults.put(value, generatedKey);
					System.err.println("Setting attribute: "+key+" to value: {label:/"+generatedKey+"} ");
					xe.setAttribute(key,"{label:/"+generatedKey+"}");				}
			}
		}
		
	}

	private static boolean checkValue(String value) {
		System.err.println("Checking value: "+value);
		if (value.contains("{") && value.contains("}")) {
			return false;
		}
		if (!value.contains("'")) {
			return false;
		}
		if (value.equals("''")) {
			return false;
		}
		return true;
	}

	private static String randomizeKey(String key) {
		return key+"*";
	}

	private static String generateKey(Element xe, String filename, String key) {
		StringBuffer sb = new StringBuffer();
		
//		sb.append(filename.substring(0,filename.length()-4)+"_");
		if (xe.getTagName().equals("component") || xe.getTagName().equals("tipi")) {
			if (xe.getAttribute("name")!=null) {
				sb.append(xe.getAttribute("id")+"_");
			}
			if (xe.getAttribute("class")!=null) {
				sb.append(xe.getAttribute("class")+"_");
			}
		}
		if (xe.getTagName().equals("column")) {
			if (xe.getAttribute("label")!=null) {
				sb.append("column_"+xe.getAttribute("name")+"_");
			}
		}
		if (xe.getTagName().equals("component-instance") || xe.getTagName().equals("tipi-instance")) {
			if (xe.getAttribute("id")!=null) {
				sb.append(xe.getAttribute("id")+"_");
			}
			if (xe.getAttribute("class")!=null) {
				sb.append(xe.getAttribute("class")+"_");
			} else {
				if (xe.getAttribute("name")!=null) {
					sb.append(xe.getAttribute("name")+"_");
				}	
			}
			sb.append(key);
			return sb.toString();
		}

		if (xe.getTagName().equals("action") ) {
			sb.append(filename.substring(0,filename.length()-4)+"_");
			if (xe.getAttribute("id")!=null) {
				sb.append(xe.getAttribute("id")+"_");
			}
			sb.append(key);
			return sb.toString();
		}

		
		if (sb.length()==0) {
			System.err.println("HUH: "+xe);
		}
		sb.append(key);
		return sb.toString();
	}

	
	
}
