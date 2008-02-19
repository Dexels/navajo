package com.dexels.navajo.tipi.extension;

import java.util.*;

import tipi.*;

import com.dexels.navajo.document.*;

public class ExtensionManager {
	public static void addExtensionMessage(Navajo parent, List<TipiExtension> extensionList, String name) throws NavajoException {
		Message arr = NavajoFactory.getInstance().createMessage(parent, name, Message.MSG_TYPE_ARRAY);
		parent.addMessage(arr);
		for (TipiExtension tipiExtension : extensionList) {
			System.err.println("Adding extension: " + tipiExtension.getDescription());
			createExtension(tipiExtension, arr);
		}
		return;
	}

	private static void createExtension(TipiExtension tipiExtension, Message arr) throws NavajoException {
		Message m = NavajoFactory.getInstance().createMessage(arr.getRootDoc(), arr.getName());
		arr.addMessage(m);
		addProperty("Id", tipiExtension.getId(), Property.STRING_PROPERTY, m);
		addProperty("Description", tipiExtension.getDescription(), Property.STRING_PROPERTY, m);
		addProperty("RequiresMain", tipiExtension.requiresMainImplementation(), Property.STRING_PROPERTY, m);
		addProperty("IsMain", tipiExtension.isMainImplementation(), Property.BOOLEAN_PROPERTY, m);
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

}
