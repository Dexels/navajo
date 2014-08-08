package com.dexels.navajo.entity;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;


public class EntityComponent extends Entity {
	public EntityComponent() {
		
	}

	public EntityComponent(Message msg, EntityManager m) {
		super(msg, m);
	}

	
	public void activateComponent(Map<String, Object> parameters) throws Exception {
		entityName = (String) parameters.get("entity.name");
	}
	
	
	public void deactivateComponent() throws Exception {
		deactivate();
	}
	

	public static void main(String [] args) {
		
		String aap = "<tml><message name=\"noot\"></message></tml>";
		Navajo m = NavajoFactory.getInstance().createNavajo(new StringReader(aap));
		
		m.write(System.err);
		
	}
}

