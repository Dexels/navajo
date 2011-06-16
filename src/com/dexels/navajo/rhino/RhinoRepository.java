package com.dexels.navajo.rhino;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.AuthorizationException;
import com.dexels.navajo.server.SimpleRepository;
import com.dexels.navajo.server.SystemException;

public class RhinoRepository extends SimpleRepository {
	public String getServlet(Access access) throws SystemException {
		try {
			authorizeUser(access.getRpcUser(), "password", access.getRpcName(),
					access.getInDoc(), null);
			initGlobals(access.getRpcName(), access.getRpcUser(),
					access.getInDoc(), null);
		} catch (AuthorizationException e) {
			e.printStackTrace();
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("Rhino activated!");
		return "com.dexels.navajo.rhino.RhinoHandler";
	}
}
