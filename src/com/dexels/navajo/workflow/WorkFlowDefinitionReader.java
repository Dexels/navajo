package com.dexels.navajo.workflow;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.types.Binary;

/**
 * Helper class to parse workflow states from definition files.
 * 
 * @author arjen
 *
 */
public final class WorkFlowDefinitionReader {

	private static File definitionPath = null;
	private static volatile HashMap<String,Transition> initialTransitions = new HashMap<String,Transition>();
	
	private static final XMLElement readDefinition(String definition) throws Exception {
		return readDefinition(new File(definitionPath, definition));
	}
	
	private static String readAttribute(XMLElement x, String name) {
		Object o = x.getAttribute(name);
		if ( o == null ) {
			return null;
		}
		if ( o.equals("") || o.equals("null") ) {
			return null;
		}
		return (String) o;
	}
	
	private static final XMLElement readDefinition(File definition) throws Exception {
		CaseSensitiveXMLElement xml = new CaseSensitiveXMLElement();
		FileReader fr = new FileReader(definition);
		xml.parseFromReader(fr);
		fr.close();
		return xml;
	}
	
	private static final XMLElement findState(XMLElement xml, String name) {
		Vector v = xml.getElementsByTagName("state");
		for (int i = 0; i < v.size(); i++) {
			XMLElement x = (XMLElement) v.get(i);
			if ( x.getAttribute("id").equals(name) ) {
				return x;
			}
		}
		return null;
	}
	
	public static final State parseState(WorkFlow wf, String name) throws Exception {
		
		State s = new State(name, wf);
		String definitionFile = wf.getDefinition() + ".xml";
		XMLElement def = readDefinition(definitionFile);
		XMLElement state = findState(def, name);
		
		Vector transitions = state.getElementsByTagName("transition");
		for (int i = 0; i < transitions.size(); i++) {
			XMLElement t = (XMLElement) transitions.get(i);
			String nextState = readAttribute(t, "nextstate");
			String trigger = readAttribute(t, "trigger");
			String condition = readAttribute(t,"condition");
			String proxy = readAttribute(t,"proxy");
			String webservice = readAttribute(t,"webservice");
			
			Transition trans = null;
			if ( proxy == null || proxy.equals("") ) {
				trans = s.addTransition(nextState, trigger, condition);
				if ( webservice != null && !webservice.equals("") ) {
					trans.getMyTask().setWebservice(webservice);
				}
			} else {
				trans = s.addTransition(nextState, trigger, condition, proxy);
				trans.getMyTask().setProxy(true);
			}
			// Parse param children.
			Vector params = t.getElementsByTagName("param");
			for (int j = 0; j < params.size(); j++) {
				XMLElement p = (XMLElement) params.get(j);
				String paramname = (String) p.getAttribute("name");
				XMLElement e = p.getElementByTagName("expression");
				String expression = (String) e.getAttribute("value");
				trans.addParameter(paramname, expression);
				
			}
		}
		// Parse tasks.
		Vector tasks = state.getElementsByTagName("task");
		for (int i = 0; i < tasks.size(); i++) {
			XMLElement t = (XMLElement) tasks.get(i);
			String service = readAttribute(t,"service");
			String trigger = readAttribute(t,"trigger");
			String condition = readAttribute(t,"condition");
			WorkFlowTask wtf = s.addTask(service, trigger, condition);
		}
		
		return s;
		
	}
	
	private static final WorkFlowDefinition createInitState(XMLElement xml, String definition) throws Exception { 
		
		WorkFlowDefinition wfd = new WorkFlowDefinition();
		XMLElement init = findState(xml, "init");
		if ( init == null ) {
			throw new Exception("Could not find init state for workflow: " + definition);
		}
		Vector transitions = init.getElementsByTagName("transition");
		for (int i = 0; i < transitions.size(); i++) {
			XMLElement t = (XMLElement) transitions.get(i);
			String nextState = readAttribute(t, "nextstate");
			String trigger = readAttribute(t,"trigger");
			String condition = readAttribute(t,"condition");
			Transition trans = Transition.createStartTransition(nextState, trigger, condition, definition);
			initialTransitions.put(definition, trans);
			wfd.setActivationTrigger(trigger);
			wfd.setName(definition);
		}
		StringWriter sw = new StringWriter();
		xml.write(sw);
		sw.close();
		wfd.setDefinition(new Binary(sw.getBuffer().toString().getBytes()));
		return wfd;
	}
	
	/**
	 * Initializes all workflows defined in workflow definition path.
	 * Returns an ArrayList of WorkFlowDefinitions objects.
	 * 
	 * @param path
	 */
	public static void initialize(File path, HashMap<String,WorkFlowDefinition> defs) {
		
		definitionPath = path;
		File [] files = path.listFiles();

		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if ( f.isFile() ) {
				try {

					XMLElement xml = readDefinition(f);
					String name = f.getName().substring(0, f.getName().length() - 4);
					if ( !defs.containsKey(name) ){
						System.err.println("Found new flow definition: " + name);
						WorkFlowDefinition wfd = createInitState(xml, name);
						wfd.setLastModified(f.lastModified());
						defs.put(name, wfd);
					} else {
						WorkFlowDefinition wfd = defs.get(name);
						if ( wfd.getLastModified() != f.lastModified() ) {
							System.err.println("Workflow " + name + " has been updated");
							Transition trans = initialTransitions.get(name);
							if ( trans != null ) {
								trans.cleanup();
							}
							wfd = createInitState(xml, name);
							wfd.setLastModified(f.lastModified());
							defs.put(name, wfd);	
						}
					}
				} catch (Exception e) {
					//e.printStackTrace(System.err);
					System.err.println("Could not initialize flow: " + f.getName());
				}
			}
		}
		// Check for deleted workflows.
		Iterator iter = initialTransitions.keySet().iterator();
		while ( iter.hasNext() ) {
			String name = (String) iter.next();
			File f = new File(definitionPath, name + ".xml");
			if ( !f.exists() ) {
				System.err.println("Definition " + name + " was obviously deleted, deleting initiating transition");
				Transition trans = initialTransitions.get(name);
				trans.cleanup();
			}
		}
		
	}
}
