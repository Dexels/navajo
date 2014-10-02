package com.dexels.navajo.entity;

import java.io.StringReader;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class EntityComponent extends Entity {
	

	public EntityComponent() {

	}

	public EntityComponent(Message msg, EntityManager m) {
		super(msg, m);
	}

	public void activateComponent(Map<String, Object> properties)
			throws Exception {
		
		entityName = (String) properties.get("entity.name");
		messageName = (String) properties.get("entity.message");

		Navajo entityNavajo = entityManager.getEntityNavajo((String) properties.get("service.name"));
		activateMessage(entityNavajo);
		entityManager.registerEntity(this);
	}

	public void deactivateComponent() throws Exception {
		deactivate();
	}

	public static void main(String[] args) {

		String aap = "<tml><message name=\"noot\"></message></tml>";
		Navajo m = NavajoFactory.getInstance().createNavajo(new StringReader(aap));

		m.write(System.err);

	}
	
	// OSGi impl - super entities should be added by OSGi to superEntitiesMap
	@Override
	protected Entity getSuperEntity(String extendedEntity) {
		return superEntitiesMap.get(extendedEntity);
	}
}
