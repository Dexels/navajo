package com.dexels.navajo.serviceplugin;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class JavaPluginTest extends JavaPlugin {

	@Override
	public Navajo process(Navajo in) throws Exception {
		
		Navajo out = NavajoFactory.getInstance().createNavajo();
		addMessage(out, "TestMessage");
		addProperty(out, "/TestMessage/Aap", "Dit is een aap");
		
		return out;
	}

}
