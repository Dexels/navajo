/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swing.substance;

import java.util.*;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SkinInfo;

import com.dexels.navajo.document.*;

public class SubstanceSkinManager {
	private static Property createSkinProperty(Navajo rootDoc) throws NavajoException {
		Property p = NavajoFactory.getInstance().createProperty(rootDoc, "Skins",Property.CARDINALITY_SINGLE, "Known skins", Property.DIR_IN);
		Map<String,SkinInfo> ss =  SubstanceLookAndFeel.getAllSkins();
		for (String name : ss.keySet()) {
			SkinInfo si = ss.get(name);
			Selection s = NavajoFactory.getInstance().createSelection(rootDoc, name, si.getClassName(), false);
			p.addSelection(s);
		}		
		return p;
	}

	public static Navajo createSkinNavajo() throws NavajoException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Skin");
		n.addMessage(m);
		Property p = createSkinProperty(n);
		m.addProperty(p);
		n.write(System.err);
		return n;
	}
	

}
