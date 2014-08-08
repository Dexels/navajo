package com.dexels.navajo.entity;

import java.io.StringReader;
import java.util.Map;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class EntityComponent extends Entity {
	public EntityComponent() {

	}

	public EntityComponent(Message msg, EntityManager m) {
		super(msg, m);
	}

	public void activateComponent(BundleContext bundleContext, Map<String, Object> parameters)
			throws Exception {
		entityName = (String) parameters.get("entity.name");
		this.bundleContext = bundleContext;
	}

	public void deactivateComponent() throws Exception {
		deactivate();
	}

	public static void main(String[] args) {

		String aap = "<tml><message name=\"noot\"></message></tml>";
		Navajo m = NavajoFactory.getInstance().createNavajo(new StringReader(aap));

		m.write(System.err);

	}
}
