package com.dexels.navajo.rhino;

import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.SimpleRepository;
import com.dexels.navajo.server.SystemException;

public class RhinoRepository extends SimpleRepository {
	public String getServlet(Access access) throws SystemException {
		return "com.dexels.navajo.rhino.RhinoHandler";
	}
}
