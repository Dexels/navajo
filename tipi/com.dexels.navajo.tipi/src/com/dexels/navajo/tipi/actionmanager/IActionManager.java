package com.dexels.navajo.tipi.actionmanager;

import navajo.ExtensionDefinition;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.actions.TipiActionFactory;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.tipixml.XMLElement;

public interface IActionManager {

	public abstract void addAction(XMLElement actionDef, ExtensionDefinition ed)
			throws TipiException, ClassNotFoundException;

	public abstract TipiActionFactory getActionFactory(String name)
			throws TipiException;

	public abstract TipiAction instantiateAction(XMLElement instance,
			TipiComponent tc, TipiExecutable parentExe) throws TipiException;

}