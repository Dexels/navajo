package com.dexels.navajo.tipi;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.*;

public class TipiScanner {

	private static HashMap<String, String> results = new HashMap<String, String>();
	private static HashMap<String, String> revResults = new HashMap<String, String>();
	private static HashMap<String,  List<String>> usageMap = new HashMap<String, List<String>>();
	private static List<String> totalStrings = new LinkedList<String>();

	private static HashSet<String> preloadSet = new HashSet<String>();
	private static int conflictcount = 0;

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws Exception {

//		bindPreload("cancel", "'Annuleren'");
//		bindPreload("cancel", "'annuleren'");
//		bindPreload("do_cancel", "'annuleer'");
//		bindPreload("close_screen", "'sluit scherm'");
//		bindPreload("change", "'wijzigen'");
//		bindPreload("save", "'Opslaan'");
//		bindPreload("save", "'opslaan'");
//		bindPreload("close", "'sluiten'");
//		bindPreload("add", "'voeg toe'");
//		bindPreload("search", "'zoek'");
//		bindPreload("export", "'exporteren'");
//		bindPreload("help", "'Help'");
//		bindPreload("dash", "'-'");
//
//		bindPreload("ok", "'Ok'");
//		bindPreload("ok", "'ok'");

		dumpCurrent("c:/tipi_preload", "nl", ".properties");

//		preloadSet.addAll(revResults.keySet());

		System.err.println("preload: " + preloadSet);

		boolean rewriteSource = false;
		
		// File f = new
		// File("c:/projects/SportlinkClubStudio/tipi/activityFrame.xml");
		File folder = new File("c:/projecten/SportlinkClubStudio/tipi");
		scanFolder(folder,rewriteSource);
		// scanFile(f);

		// for (Iterator iter = results.keySet().iterator(); iter.hasNext();) {
		// String element = (String) iter.next();
		// System.err.println("Result: "+element+" results:
		// "+results.get(element));
		// }

		System.err.println("Size: " + results.size() + " conflicts: " + conflictcount);
		dumpCurrent("c:/projecten/SportlinkClubStudio/lang/tipi_lang", "nl", "");
	}

	private static void dumpCurrent(String filename, String locale, String extension) throws IOException {
		FileWriter fw = new FileWriter(filename + "_" + locale + ".properties");
		Set<String> s = results.keySet();
		SortedSet<String> ss = new TreeSet<String>(s);
		for (Iterator<String> iter = ss.iterator(); iter.hasNext();) {

			String element = iter.next();
			fw.write(element + " " + results.get(element) + "\n");
			System.err.println("Result: " + element + " results: " + results.get(element));
		}
		fw.flush();
		fw.close();

		fw = new FileWriter(filename + "_usage_" + locale + ".csv");
		s = usageMap.keySet();
		ss = new TreeSet<String>(s);
		System.err.println("Writing: "+ss.size());
		for (Iterator<String> iter = ss.iterator(); iter.hasNext();) {

			String element = iter.next();
			String label = revResults.get(element);
			fw.write(label+"\t"+element.substring(1,element.length()-1)+"\t" + writeList(usageMap.get(element)) + "\n");
			System.err.println("Result: " + element + " results: " + results.get(element));
		}
		fw.flush();
		fw.close();

		fw = new FileWriter(filename + "_all_" + locale + ".properties");
//		s = all.keySet();
		Collections.sort(totalStrings);
		for (Iterator<String> iter = totalStrings.iterator(); iter.hasNext();) {

			String element = iter.next();
			// strip quotes
			fw.write(element.substring(1,element.length()-1) +"\n");
		}
		fw.flush();
		fw.close();

		
//		
//		
//		fw = new FileWriter(filename + "_" + locale + ".sql");
//		for (Iterator<String> iter = ss.iterator(); iter.hasNext();) {
//
//			String element = iter.next();
//			String line = "INSERT INTO propertydescription (descriptionid,locale,name,objectid,objecttype,description,lastupdate,updateby,context,sublocale) VALUES ( propertydescription_seq.nextval, 'nl', '"
//					+ element + "', null, null, " + results.get(element) + ", sysdate, 'SLCASPUSER','tipi','club' );";
//			fw.write(line + "\n");
//
//		}
//		fw.flush();
//		fw.close();

	}

	private static String writeList(List<String> list) {
		StringBuffer sb = new StringBuffer();
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String elt = iterator.next();
			sb.append(elt);
			if(iterator.hasNext()) {
				sb.append("\t");
			}
			
		}
		return sb.toString();
	}

	public static void scanFolder(File dir, boolean rewriteSource) throws IOException, NavajoException {
		System.err.println("Scanning folder: "+dir);
		File[] contents = dir.listFiles();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].getName().endsWith(".xml")) {
				scanFile(contents[i],rewriteSource);
			} else {
				if(contents[i].isDirectory()) {
					scanFolder(contents[i],rewriteSource);
				}
			}
		}
	}

	private static void scanFile(File file, boolean rewriteSource) throws IOException, NavajoException {
		System.err.println("Checking file: " + file);
		FileInputStream fr = new FileInputStream(file);
		Document d;
		try {
			d = XMLDocumentUtils.createDocument(fr, false);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return;
		}
		NodeList nl = d.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (n instanceof Element) {
				Element elt = (Element) n;
				scanElement(elt, file.getName());
			}
		}
		fr.close();
		if(rewriteSource) {
			File filePtr = new File(file.getAbsolutePath());
			System.err.println("> " + filePtr);
			FileWriter fw = new FileWriter(filePtr, false);
			XMLDocumentUtils.write(d, fw, false);
			fw.flush();
			fw.close();
		}
	}

	private static void scanElement(Element xe, String filename) {
		NamedNodeMap ee = xe.getAttributes();
		for (int i = 0; i < ee.getLength(); i++) {
			String next = ee.item(i).getNodeName();
			String value = xe.getAttribute(next);
			checkElement(filename, xe, next, value);
		}
		// while(ee.hasMoreElements()) {
		// String next = (String)ee.nextElement();
		// String value = xe.getStringAttribute(next);
		// checkElement(filename,xe,next,value);
		// }
		NodeList nl = xe.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i) instanceof Element) {
				scanElement((Element) nl.item(i), filename);
			}
		}
		// Vector v = xe.getChildren();
		//		
		// for (int i = 0; i < v.size(); i++) {
		// XMLElement child = (XMLElement)v.get(i);
		// scanElement(child,filename);
		// }
	}

	public static void bindPreload(String key, String value) {
		if (!results.containsKey(key)) {
			results.put(key, value);
		}
		if (!revResults.containsKey(value)) {
			revResults.put(value, key);
		}
	}

	private static void checkElement(String filename, Element xe, String key, String value) {
		if (key.equals("text") || key.equals("tooltip") || key.equals("title") || key.equals("label")) {

			if (checkValue(value)) {
				totalStrings.add(value);
				if (revResults.containsKey(value) || preloadSet.contains(value)) {
					System.err.println("Already found: " + value);
					usageMap.get(value).add(filename);
					xe.setAttribute(key, "{label:/" + revResults.get(value) + "}");
				} else {
					ArrayList<String> usageList = new ArrayList<String>();
					usageMap.put(value, usageList);
					usageList.add(filename);
					String generatedKey = generateKey(xe, filename, key);
					while (results.containsKey(generatedKey)) {
						conflictcount++;
						// System.err.println("Oh dear: conflict");
						generatedKey = randomizeKey(generatedKey,new String[]{xe.getAttribute("id"),xe.getAttribute("name")});
					}
					System.err.println("Adding to results: " + generatedKey);
					results.put(generatedKey, value);
					revResults.put(value, generatedKey);
					System.err.println("Setting attribute: " + key + " to value: {label:/" + generatedKey + "} ");
					xe.setAttribute(key, "{label:/" + generatedKey + "}");
				}
			}
		}

	}

	private static boolean checkValue(String value) {
//		System.err.println("Checking value: " + value);
		if (value.contains("{") && value.contains("}")) {
			return false;
		}
		if (!value.contains("'")) {
			return false;
		}
		if (value.equals("''")) {
			return false;
		}
		if (value.contains("[")) {
			return false;
		}
		return true;
	}

	private static String randomizeKey(String key, String[] possibleIds) {
//		return key + "*";
		if(possibleIds!=null) {
			for (int i = 0; i < possibleIds.length; i++) {
				if(possibleIds[i]!=null) {
					if (!results.containsKey(key+"_"+possibleIds[i])) {
						// System.err.println("Oh dear: conflict");
						return key+"_"+possibleIds[i];
					}
					
				}
			}
		}
		conflictcount++;
		
		return key+conflictcount;
	}

	private static String generateKey(Element xe, String filename, String key) {
		StringBuffer sb = new StringBuffer();
		if(xe.getTagName().equals("showInfo")) {
			sb.append("showInfo_");
			sb.append(filename.substring(0, filename.length() - 4));
			return sb.toString();
			}
		if(xe.getTagName().equals("showQuestion")) {
			sb.append("showQuestion_");
			sb.append(filename.substring(0, filename.length() - 4));
			return sb.toString();
			}
		if(xe.getTagName().equals("setValue")) {
			sb.append("constant_");
			sb.append(filename.substring(0, filename.length() - 4));
			return sb.toString();
			}

		
		// sb.append(filename.substring(0,filename.length()-4)+"_");
		if (xe.getTagName().equals("component") || xe.getTagName().equals("tipi")) {
			if (xe.getAttribute("name") != null) {
				sb.append(xe.getAttribute("id") + "_");
			}
			if (xe.getAttribute("class") != null) {
				sb.append(xe.getAttribute("class") + "_");
			}
		}
		if (xe.getTagName().equals("column")) {
			if (xe.getAttribute("label") != null) {
				sb.append("column_" + xe.getAttribute("name") + "_");
			}
		}
		if (xe.getTagName().equals("component-instance") || xe.getTagName().equals("tipi-instance") || xe.getTagName().startsWith("c.")) {
			if (xe.getAttribute("id") != null) {
				sb.append(xe.getAttribute("id") + "_");
			}
			if (xe.getAttribute("class") != null) {
				sb.append(xe.getAttribute("class") + "_");
			} else {
				if (xe.getAttribute("name") != null) {
					sb.append(xe.getAttribute("name") + "_");
				}
			}
			sb.append(key);
			return sb.toString();
		}

		if (xe.getTagName().equals("action")) {
			sb.append(filename.substring(0, filename.length() - 4) + "_");
			if (xe.getAttribute("id") != null) {
				sb.append(xe.getAttribute("id") + "_");
			}
			sb.append(key);
			return sb.toString();
		}

		if (sb.length() == 0) {
			System.err.println("HUH: " + xe);
		}
		sb.append(key);
		return sb.toString();
	}

}
