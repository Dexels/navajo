package com.dexels.navajo.tipi.internal;

import java.util.HashMap;
import java.util.Map;

import navajo.ExtensionDefinition;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.actions.TipiActionFactory;
import com.dexels.navajo.tipi.tipixml.XMLElement;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/**
 * This is essentially a map of ActionFactories. All action definitions will be
 * presented to this manager, and it will create an ActionFactory for each
 * different action type.
 */
public class TipiActionManager {
	private final Map<String, TipiActionFactory> actionFactoryMap = new HashMap<String, TipiActionFactory>();

	public TipiActionManager() {
	}

	public void addAction(XMLElement actionDef, TipiContext context,
			ExtensionDefinition ed) throws TipiException {
		TipiActionFactory taf = new TipiActionFactory();
		taf.load(actionDef, context, ed);
		actionFactoryMap.put(actionDef.getStringAttribute("name"), taf);
	}

	public TipiActionFactory getActionFactory(String name) throws TipiException {
		TipiActionFactory taf = actionFactoryMap.get(name);
		if (taf == null) {
			throw new TipiException("No action defined with name: " + name);
		}
		return taf;
	}

	public TipiAction instantiateAction(XMLElement instance, TipiComponent tc,
			TipiExecutable parentExe) throws TipiException {
		String name = (String) instance.getAttribute("type");
		if (name == null) {
			name = instance.getName();
		}
		TipiActionFactory taf = getActionFactory(name);
		return taf.instantateAction(instance, tc, parentExe);
	}

}
