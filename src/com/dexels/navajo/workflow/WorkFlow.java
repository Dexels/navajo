package com.dexels.navajo.workflow;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;


import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.MappingUtils;

public class WorkFlow implements Mappable, Serializable {

	private static final long serialVersionUID = -6582796299941671005L;
	
	public final String myId;
	public final String definition;
	public State currentState = null;
	public final Access initiatingAccess;
	public State [] history = null;
	public boolean kill = false;
	public boolean finished = false;
	public Binary localState = null;
	public boolean transientWorkFlow = false;
	
	/**
	 * The local Navajo state store.
	 */
	private Navajo localNavajo = null;
	
	/**
	 * This arraylist contains all the visited states for this workflow.
	 */
	protected final ArrayList<State> historicStates = new ArrayList<State>();
	
	public static WorkFlow getInstance(String definition, String activatedState, Access a, String username) {
		WorkFlow wf = new WorkFlow(definition, WorkFlowManager.generateWorkflowId(), a, username);
		
		wf.currentState = wf.createState(activatedState, a);
		
		//wf.start();
		return wf;
	}
	
	public WorkFlow(String definition, String id, Access a, String username) {
		if ( a != null ) {
			initiatingAccess = a;
		} else {
			// Create empty access object using specified username for initiating task.
			initiatingAccess = new Access(-1, -1, -1, username, null, null, null, null, false, null);
		}
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
	@SuppressWarnings("unchecked")
	protected void mergeWithParameters(Navajo in) {
		
		if ( in != null && in.getMessage("__parms__") == null ) {
			Message clone = localNavajo.getMessage("__parms__").copy(in);
			try {
				in.addMessage(clone);
			} catch (NavajoException e) {
				e.printStackTrace(System.err);
			}
		} else if ( in != null ) {
			Message orig = in.getMessage("__parms__");
			// Get all properties:
			Message clone = localNavajo.getMessage("__parms__").copy(in);
			ArrayList<Property> props = clone.getAllProperties();
			for (int i = 0; i < props.size(); i++) {
				orig.addProperty(props.get(i));
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
			MappingUtils.setProperty(false, null, "/__parms__/" + name, value, type, "", "in", "", 0, localNavajo, localNavajo, false);
		} catch (Exception e) {
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
	protected State createState(String name, Access a) {
		try {
		
				State s = null;
				try {
					s = WorkFlowDefinitionReader.parseState(this, name, a);
					currentState = s;
				} catch (Exception e) {
					System.err.println("Could not parse workflow state " + name + " of workflowdefinition " + this.getDefinition());
					WorkFlowManager.log(this, null, e.getMessage(), e);
					setKill(true);
				}
				return s;
		} finally {
			
		}
	}
	
	public void start() {
		// Find start state.
		if ( currentState != null ) {
			WorkFlowManager.getInstance().addWorkFlow(this);
			try {
				currentState.enter(true);
			} catch (Throwable t) {
				kill = true;
				finish();
			}
		}
	}
	
	public void setKill(boolean b) {
		if ( b ) {
			kill = true;
			if ( currentState != null ) {
				System.err.println("Workflow " + getMyId() + " got killed!");
				currentState.setKill();
				WorkFlowManager.log(this, null, "killed", null);
			}
			finish();
		}
	}
	
	public boolean isKilled() {
		return kill;
	}
	
	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
    }

	/**
	 * Gets the defining name of this workflow.
	 * 
	 * @return
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * Gets the unique workflow id for this workflow instance.
	 * 
	 * @return
	 */
	public String getMyId() {
		return myId;
	}
	
	public void finish() {
		finished = true;
		WorkFlowManager.log(this, null, "finished " + ( kill ? "(got killed)" : ""), null);
		WorkFlowManager.getInstance().removeWorkFlow(this);
	}
	
	public void revive() {
		if ( currentState != null ) {
			System.err.println("Reviving workflow from state: " + currentState.getId() );
			currentState.enter(false);
		}
	}

	public static void main(String [] args) throws Exception {
//		WorkFlow wf = new WorkFlow("", "", null, "ROOT");
//		wf.addParameter("Name", new Integer(43453));
//		wf.addParameter("Name", "Dexels");
//		wf.localNavajo.write(System.err);
	}

	public State getCurrentState() {
		return currentState;
	}

	public Access getInitiatingAccess() {
		return initiatingAccess;
	}

	public State[] getHistory() {
		if ( historicStates.size() == 0) {
			return null;
		}
		ArrayList<State> copy = new ArrayList<State>(historicStates);
		State [] historyd = new State[copy.size()];
		historyd = copy.toArray(historyd);
		return historyd;
	}

	// Gets an historic state by the name (always returns first instance!).
	public State getHistoricState(String name) {
		System.err.println("IN getHistoricState(" + name + "), number of states: " + historicStates.size());
		if ( historicStates.size() == 0) {
			return null;
		}
		for ( int i = 0; i < historicStates.size(); i++ ) {
			System.err.println("FOUND HISTORIC STATE: " + historicStates.get(i).getId());
			if ( historicStates.get(i).getId().equals(name) ) {
				return historicStates.get(i);
			}
		}
		return null;
	}
	
	public Binary getLocalState() throws UserException {

		try {
			if ( localNavajo != null ) {
				localState = new Binary();
				OutputStream os = localState.getOutputStream();
				localNavajo.write(os);
				os.close();
				return localState;
			}
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
		return null;
	}

	public Navajo getLocalNavajo() {
		return localNavajo;
	}

	public boolean isFinished() {
		return finished;
	}

	
}
