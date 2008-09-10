package com.dexels.navajo.tipi.actions;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public final class TipiDumpClass extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			Message mm = NavajoFactory.getInstance().createMessage(n, "Class", Message.MSG_TYPE_ARRAY);
			n.addMessage(mm);
			Map<String, XMLElement> m = myContext.getTipiClassDefMap();
			for (Iterator<String> iter = m.keySet().iterator(); iter.hasNext();) {
				String element = iter.next();
				XMLElement def = m.get(element);
				dumpDef(mm, element, def);
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

	private void dumpDef(Message msg, String element, XMLElement def) {
		if (def.getName().equals("tipiaction")) {
			System.err.println("Action: " + def.getStringAttribute("name"));

		}
	}

}