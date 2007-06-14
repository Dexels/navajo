package com.dexels.navajo.workflow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.MappingUtils;

public class WorkFlow implements Mappable, Serializable {

	private static final long serialVersionUID = -6582796299941671005L;
	
	private String myId = null;
	private String definition = null;
	private State currentState = null;
	private Navajo localNavajo = null;
	/**
	 * This arraylist contains all the visited states for this workflow.
	 */
	protected final ArrayList historicStates = new ArrayList();
	
	public static WorkFlow getInstance(String definition, String activatedState) {
		WorkFlow wf = new WorkFlow(definition, WorkFlowManager.generateWorkflowId());
		if ( definition.equals("demo") ) {
			try {
				wf.createState("start");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Read from workflow definition file...
		}
		WorkFlowManager.getInstance().addWorkFlow(wf);
		wf.start();
		return wf;
	}
	
	public WorkFlow(String definition, String id) {
		myId = id;
		this.definition = definition;
		// Create local Navajo to store parameters.
		localNavajo = NavajoFactory.getInstance().createNavajo();
		Message params = NavajoFactory.getInstance().createMessage(localNavajo, "__parms__");
		try {
			localNavajo.addMessage(params);
		} catch (NavajoException e) {
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * Merge a foreign Navajo with the parameter in the localNavajo store.
	 * 
	 * @param in
	 */
	protected void mergeWithParmaters(Navajo in) {
		if ( in != null ) {
			Message clone = localNavajo.getMessage("__parms__").copy(in);
			try {
				in.addMessage(clone);
			} catch (NavajoException e) {
				e.printStackTrace(System.err);
			}
		}
	}

	/**
	 * Add a parameter to the localNavajo store.
	 * 
	 * @param name
	 * @param value
	 */
	protected void addParameter(String name, Object value) {
		try {
			String type = (value != null) ? MappingUtils.determineNavajoType(value) : "";
			MappingUtils.setProperty(true, localNavajo.getMessage("__parms__"), name, value, type, "", "in", "", 0, null, localNavajo, false);
			System.err.println(myId +  ": Added parameter " + name + " with value " + value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		} 
	}
	
	/**
	 * This methods creates a new state and its corresponding transitions by reading its definition from the
	 * workflow definition file.
	 * 
	 * @param name
	 * @return
	 */
	protected State createState(String name) {
		try {
			if ( name.equals("start") && definition.equals("demo") ){
				State s1 = new State("start", this);
				currentState = s1;
				try {
					Transition t1 = s1.addTransition("waitforinput", "navajo:InitAap", null);
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
				return s1;
			} else if ( name.equals("waitforinput") && definition.equals("demo")) {
				State s2 = new State("waitforinput", this);
				currentState = s2;
				try {
					// Add a task and two transitions.
					WorkFlowTask wtf = s2.addTask("person/InitInsertPerson", null, null);
					Transition t4 = s2.addTransition(null, "offsettime:2m", null);
					Transition t2 = s2.addTransition("approve", "navajo:ProcessAap", null);
					t2.addParameter("Name", "[/Result/Name]");
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
				return s2;
			} else if ( name.equals("approve") && definition.equals("demo")) {
				State s2 = new State("waitforinput", this);
				currentState = s2;
				try {
					Transition t2 = s2.addTransition(null, "navajo:ProcessApproveAap", "[/Approval/Name] == [/@Name] AND [/Approval/Status] == 'ok'");
					Transition t3 = s2.addTransition("start", "navajo:ProcessApproveAap", "[/Approval/Name] == [/@Name] AND [/Approval/Status] != 'ok'");
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
				return s2;
			}
			else {
				State s = new State(name, this);
				currentState = s;
				return s;
			}
		} finally {
			
		}
	}
	
	public void start() {
		// Find start state.
		if ( currentState != null ) {
			currentState.enter();
		}
	}
	
	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
    }

	public String getDefinition() {
		return definition;
	}

	public String getMyId() {
		return myId;
	}
	
	public void finish() {
		System.err.println("Workflow " + getMyId() + " is finished");
		WorkFlowManager.getInstance().removePersistedWorkFlow(this);
	}
	
	public void revive() {
		if ( currentState != null ) {
			System.err.println("Reviving workflow from state: " + currentState.getId() );
			currentState.enter();
		}
	}

	public static void main(String [] args) throws Exception {
		WorkFlow wf = new WorkFlow("", "");
		wf.addParameter("Name", new Integer(43453));
		wf.addParameter("Name", "Dexels");
		wf.localNavajo.write(System.err);
	}

}
