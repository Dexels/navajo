package com.dexels.navajo.tipi.actionmanager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.actions.TipiActionFactory;
import com.dexels.navajo.tipi.internal.TipiAction;
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
public class TipiActionManager implements Serializable, IActionManager {
	
	private static final long serialVersionUID = -1280464136835192795L;
	private final Map<String, TipiActionFactory> actionFactoryMap = new HashMap<String, TipiActionFactory>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiActionManager.class);
	
	public TipiActionManager() {
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.actionmanager.IActionManager#addAction(com.dexels.navajo.tipi.tipixml.XMLElement, navajo.ExtensionDefinition)
	 */
	@Override
	public void addAction(XMLElement actionDef,ExtensionDefinition ed) throws ClassNotFoundException {
		TipiActionFactory taf = new TipiActionFactory();
		taf.load(actionDef, ed);
		actionFactoryMap.put(actionDef.getStringAttribute("name"), taf);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.actionmanager.IActionManager#getActionFactory(java.lang.String)
	 */
	@Override
	public TipiActionFactory getActionFactory(String name) throws TipiException {
		TipiActionFactory taf = actionFactoryMap.get(name);
		if (taf == null) {
			throw new TipiException("No action defined with name: " + name);
		}
		return taf;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.actionmanager.IActionManager#instantiateAction(com.dexels.navajo.tipi.tipixml.XMLElement, com.dexels.navajo.tipi.TipiComponent, com.dexels.navajo.tipi.TipiExecutable)
	 */
	@Override
	public TipiAction instantiateAction(XMLElement instance, TipiComponent tc,
			TipiExecutable parentExe) throws TipiException {
		String name = (String) instance.getAttribute("type");
		if (name == null) {
			name = instance.getName();
		}
		if (name == null) {
			throw new TipiException("Undefined action type in: "
					+ instance.toString());
		}
		TipiActionFactory taf = getActionFactory(name);
		if(taf==null) {
			logger.warn("Missing action factory for action: {}",name);
			return null;
		}
		return taf.instantateAction(instance, tc, parentExe);
	}

}
