package com.dexels.navajo.workflow;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public final class WorkFlowDefinition implements Mappable {

	public String name = null;
	public String filePath = null;
	public String activationTrigger = null;
	public Binary definition = null;
	public long lastModified = -1;
	public int instances = 0;
	
	public WorkFlowDefinition(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}
	
	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

	public String getName() {
		return name;
	}

	public String getActivationTrigger() {
		return activationTrigger;
	}

	public Binary getDefinition() {
		return definition;
	}

	protected void setActivationTrigger(String trigger) {
		if ( this.activationTrigger == null ) {
			this.activationTrigger = trigger;
		} else {
			this.activationTrigger += ";" + trigger;
		}
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setDefinition(Binary definition) {
		this.definition = definition;
	}

	public long getLastModified() {
		return lastModified;
	}

	protected void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public int getInstances() {
		return instances;
	}
	
}
