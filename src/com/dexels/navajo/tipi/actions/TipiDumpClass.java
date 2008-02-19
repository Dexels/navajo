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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


//	  <tipiaction name="setStorageInstanceId"  class="TipiSetStorageInstanceId" package="com.dexels.navajo.tipi.actions">
//	    <param name="id" type="string" required="true"/>
//	  </tipiaction>

	  private void dumpDef(Message msg, String element, XMLElement def) {
		// TODO Auto-generated method stub
//		Message elt = NavajoFactory.getInstance().createMessage(n, "Class", Message.MSG_TYPE_ARRAY_ELEMENT);
		if(def.getName().equals("tipiaction")) {
			System.err.println("Action: "+def.getStringAttribute("name"));
			
		}
	}

}