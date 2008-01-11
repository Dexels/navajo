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
import com.dexels.navajo.server.Access;

/**
 * Helper class to parse workflow states from definition files.
 * 
 * @author arjen
 *
 */
public final class WorkFlowDefinitionReader {

	/**
	 * PUBLIC CONSTANTS
	 */
	
	public static final String TRANSITION_ELT = "transition";
	public static final String STATE_ELT = "state";
	public static final String PARAM_ELT = "param";
	public static final String EXPRESSION_ELT = "expression";
	public static final String TASK_ELT = "task";
	
	public static final String NEXTSTATE_ATTR = "nextstate";
	public static final String TRIGGER_ATTR = "trigger";
	public static final String CONDITION_ATTR = "condition";
	public static final String SERVICE_ATTR = "service";
	public static final String USERNAME_ATTR = "username";
	public static final String PASSWORD_ATTR = "password";
	public static final String NAVAJO_ATTR = "navajo";
	public static final String NAME_ATTR = "name";
	public static final String VALUE_ATTR = "value";
	
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
	
	@SuppressWarnings("unchecked")
	private static final XMLElement findState(XMLElement xml, String name) {
		Vector v = xml.getElementsByTagName(WorkFlowDefinitionReader.STATE_ELT);
		for (int i = 0; i < v.size(); i++) {
			XMLElement x = (XMLElement) v.get(i);
			if ( x.getAttribute("id").equals(name) ) {
				return x;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static final State parseState(WorkFlow wf, String name, Access a) throws Exception {
		
		State s = new State(name, wf, a);
		String definitionFile = wf.getDefinition() + ".xml";
		XMLElement def = readDefinition(definitionFile);
		XMLElement state = findState(def, name);
		
		if ( state == null ) {
			throw new Exception("Could not find state " + name + " in definition file");
		}
		
		Vector transitions = state.getElementsByTagName(WorkFlowDefinitionReader.TRANSITION_ELT);
		for (int i = 0; i < transitions.size(); i++) {
			XMLElement t = (XMLElement) transitions.get(i);
			String nextState = readAttribute(t, WorkFlowDefinitionReader.NEXTSTATE_ATTR);
			String trigger = readAttribute(t, WorkFlowDefinitionReader.TRIGGER_ATTR);
			String condition = readAttribute(t, WorkFlowDefinitionReader.CONDITION_ATTR);
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
			Vector params = t.getElementsByTagName(WorkFlowDefinitionReader.PARAM_ELT);
			for (int j = 0; j < params.size(); j++) {
				XMLElement p = (XMLElement) params.get(j);
				String paramname = (String) p.getAttribute(WorkFlowDefinitionReader.NAME_ATTR);
				Vector exp = p.getElementsByTagName(WorkFlowDefinitionReader.EXPRESSION_ELT);
				ArrayList<ConditionalExpression> al = new ArrayList<ConditionalExpression>();
				for (int e = 0; e < exp.size(); e++) {
					XMLElement ep = (XMLElement) exp.get(e);
					String expression = (String) ep.getAttribute(WorkFlowDefinitionReader.VALUE_ATTR);
					String cond = (String) ep.getAttribute(WorkFlowDefinitionReader.CONDITION_ATTR);
					String navajoToUse = (String) ep.getAttribute(WorkFlowDefinitionReader.NAVAJO_ATTR); 
					al.add(new ConditionalExpression(cond, expression, navajoToUse));
				}
				trans.addParameter(paramname, al);	
			}
		}
		// Parse tasks.
		Vector tasks = state.getElementsByTagName(WorkFlowDefinitionReader.TASK_ELT);
		for (int i = 0; i < tasks.size(); i++) {
			XMLElement t = (XMLElement) tasks.get(i);
			String service = readAttribute(t, WorkFlowDefinitionReader.SERVICE_ATTR);
			String trigger = readAttribute(t, WorkFlowDefinitionReader.TRIGGER_ATTR);
			String condition = readAttribute(t, WorkFlowDefinitionReader.CONDITION_ATTR);
			// Optional: username/password.
			//String username = readAttribute(t, WorkFlowDefinitionReader.USERNAME_ATTR);
			//String password = readAttribute(t, WorkFlowDefinitionReader.PASSWORD_ATTR);
			//  Optional, specificy whether to use request or response navajo of the current workflow state.
			String navajo = readAttribute(t, WorkFlowDefinitionReader.NAVAJO_ATTR);
			s.addTask(service, trigger, condition, navajo);
		}
		
		return s;
		
	}
	
	@SuppressWarnings("unchecked")
	private static final WorkFlowDefinition createInitState(XMLElement xml, String definition, String filePath) throws WorkFlowDefinitionException { 

		WorkFlowDefinition wfd = new WorkFlowDefinition(definition, filePath);
		XMLElement init = findState(xml, "init");
		if ( init == null ) {
			throw new WorkFlowDefinitionException("Could not find init state for workflow: " + definition);
		}
		Vector transitions = init.getElementsByTagName(WorkFlowDefinitionReader.TRANSITION_ELT);
		for (int i = 0; i < transitions.size(); i++) {
			XMLElement t = (XMLElement) transitions.get(i);
			String nextState = readAttribute(t, WorkFlowDefinitionReader.NEXTSTATE_ATTR);
			String trigger = readAttribute(t, WorkFlowDefinitionReader.TRIGGER_ATTR);
			String condition = readAttribute(t, WorkFlowDefinitionReader.CONDITION_ATTR);
			String user = readAttribute(t, WorkFlowDefinitionReader.USERNAME_ATTR);
			try {
				Transition trans = Transition.createStartTransition(nextState, trigger, condition, definition, user);
				initialTransitions.put(definition, trans);
				wfd.setActivationTrigger(trigger);

				// Parse param children.
				Vector params = t.getElementsByTagName(WorkFlowDefinitionReader.PARAM_ELT);
				for (int j = 0; j < params.size(); j++) {
					XMLElement p = (XMLElement) params.get(j);
					String paramname = (String) p.getAttribute(WorkFlowDefinitionReader.NAME_ATTR);
					Vector exp = p.getElementsByTagName(WorkFlowDefinitionReader.EXPRESSION_ELT);
					ArrayList<ConditionalExpression> al = new ArrayList<ConditionalExpression>();
					for (int e = 0; e < exp.size(); e++) {
						XMLElement ep = (XMLElement) exp.get(e);
						String expression = (String) ep.getAttribute(WorkFlowDefinitionReader.VALUE_ATTR);
						String cond = (String) ep.getAttribute(WorkFlowDefinitionReader.CONDITION_ATTR);
						// source can specify the navajo to use:
						// [wfstate]":"["request"|"response"].
						String navajoToUse = (String) ep.getAttribute(WorkFlowDefinitionReader.NAVAJO_ATTR); 
						al.add(new ConditionalExpression(cond, expression, navajoToUse));
					}
					trans.addParameter(paramname, al);	
				}
			} catch (Exception e) {
				throw new WorkFlowDefinitionException(e.getMessage());
			}
		}
		try {
			StringWriter sw = new StringWriter();
			xml.write(sw);
			sw.close();
			wfd.setDefinition(new Binary(sw.getBuffer().toString().getBytes()));
			return wfd;
		} catch (Exception e) {
			throw new WorkFlowDefinitionException(e.getMessage());
		}	

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
						try {
							WorkFlowDefinition wfd = createInitState(xml, name, f.getAbsolutePath());
							wfd.setLastModified(f.lastModified());
							defs.put(name, wfd);
						} catch (WorkFlowDefinitionException e) {
							System.err.println("Could not initialize workflow " + name + ": " + e.getMessage());
						}
					} else {
						WorkFlowDefinition wfd = defs.get(name);
						if ( wfd.getLastModified() != f.lastModified() ) {
							System.err.println("Workflow " + name + " has been updated");
							Transition trans = initialTransitions.get(name);
							if ( trans != null ) {
								trans.cleanup();
							}
							try {
								wfd = createInitState(xml, name, f.getAbsolutePath());
								wfd.setLastModified(f.lastModified());
								defs.put(name, wfd);	
							} catch (WorkFlowDefinitionException e) {
								System.err.println("Could not initialize workflow " + name + ": " + e.getMessage());
							}
						}
					}
				} catch (Exception e) {
					//e.printStackTrace(System.err);
					System.err.println("Could not initialize flow: " + f.getName());
				}
			}
		}
		// Check for deleted workflows.
		Iterator<String> iter = initialTransitions.keySet().iterator();
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
