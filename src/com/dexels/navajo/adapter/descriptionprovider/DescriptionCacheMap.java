package com.dexels.navajo.adapter.descriptionprovider;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class DescriptionCacheMap implements Mappable {

	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
		try {
			access.getOutputDoc().addMessage(config.getDescriptionProvider().dumpCacheMessage(access.getOutputDoc()));
			
		} catch (NavajoException e) {
			e.printStackTrace();
		}

	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

}
