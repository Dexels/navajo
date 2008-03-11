package com.dexels.navajo.tipi.extension;

import java.util.*;

import tipi.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.connectors.*;

public class ExtensionManager {
	public static void addExtensionMessage(TipiContext tc, Navajo parent, List<TipiExtension> extensionList, String name) throws NavajoException {
		Message arr = NavajoFactory.getInstance().createMessage(parent, name, Message.MSG_TYPE_ARRAY);
		parent.addMessage(arr);
		for (TipiExtension tipiExtension : extensionList) {
			System.err.println("Adding extension: " + tipiExtension.getDescription());
			createExtension(tc,tipiExtension, arr);
		}
		return;
	}

	private static void createExtension(TipiContext tc,TipiExtension tipiExtension, Message arr) throws NavajoException {
		Message m = NavajoFactory.getInstance().createMessage(arr.getRootDoc(), arr.getName());
		arr.addMessage(m);
		addProperty("Id", tipiExtension.getId(), Property.STRING_PROPERTY, m);
		addProperty("Description", tipiExtension.getDescription(), Property.STRING_PROPERTY, m);
		addProperty("RequiresMain", tipiExtension.requiresMainImplementation(), Property.STRING_PROPERTY, m);
		addProperty("IsMain", tipiExtension.isMainImplementation(), Property.BOOLEAN_PROPERTY, m);
		addProperty("ConnectorId", tipiExtension.getConnectorId(), Property.BOOLEAN_PROPERTY, m);
		addProperty("IsInstantiated", tc.getConnector(tipiExtension.getConnectorId())!=null, Property.BOOLEAN_PROPERTY, m);
		
			createIncludes(tipiExtension.getIncludes(), m);

	}

	private static void createIncludes(String[] includes, Message m) throws NavajoException {
		Message mm = NavajoFactory.getInstance().createMessage(m.getRootDoc(), "Includes", Message.MSG_TYPE_ARRAY);
		m.addMessage(mm);
		for (int i = 0; i < includes.length; i++) {
			Message elt = NavajoFactory.getInstance().createMessage(m.getRootDoc(), "Includes", Message.MSG_TYPE_ARRAY_ELEMENT);
			mm.addMessage(elt);
				addProperty("Id", includes[i], Property.STRING_PROPERTY, elt);
		}
	}

	private static void addProperty(String name, Object value, String type, Message m) throws NavajoException {
		Property p = NavajoFactory.getInstance().createProperty(m.getRootDoc(), name, type, "", 0, null, Property.DIR_OUT);
		p.setAnyValue(value);
		m.addProperty(p);
	}

	public static void addConnectorMessage(TipiContext tipiContext, Navajo n, Set<String> keySet) {
		try {
			Message mm = NavajoFactory.getInstance().createMessage(n, "Connectors", Message.MSG_TYPE_ARRAY);
			n.addMessage(mm);
			for (String connectorId : keySet) {
				Message elt = NavajoFactory.getInstance().createMessage(mm.getRootDoc(), "Connectors", Message.MSG_TYPE_ARRAY_ELEMENT);
				mm.addMessage(elt);
					addProperty("Id", connectorId, Property.STRING_PROPERTY, elt);
					TipiConnector connector = tipiContext.getConnector(connectorId);
					String defEntry = connector.getDefaultEntryPoint();
					addProperty("EntryPoint", defEntry, Property.STRING_PROPERTY, elt);
					
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		
		
	}

}
