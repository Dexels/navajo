/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actionmanager;


import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.actions.TipiActionFactory;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.tipixml.XMLElement;

import navajo.ExtensionDefinition;

public interface IActionManager {

	public abstract void addAction(XMLElement actionDef, ExtensionDefinition ed)
			throws TipiException, ClassNotFoundException;

	public abstract TipiActionFactory getActionFactory(String name)
			throws TipiException;

	public abstract TipiAction instantiateAction(XMLElement instance,
			TipiComponent tc, TipiExecutable parentExe) throws TipiException;

}