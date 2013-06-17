package com.dexels.navajo.entity.listener;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.script.api.LocalClient;

public class EntityListener extends HttpServlet {

	EntityManager myManager;
	LocalClient myClient;
	
	public void setClient(LocalClient client) {
		this.myClient = client;
	}

	public void clearClient(LocalClient client) {
		this.myClient = null;
	}
	public void setEntityManager(EntityManager em) {
		myManager = em;
	}

	public void clearEntityManager(EntityManager em) {
		myManager = null;
	}

	/**
	 *  entity/Match?Match/MatchId=2312321
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getMethod();
		String path = request.getPathInfo();
		Map<String,String []> requestParameters = request.getParameterMap();
		System.err.println("method: " + method);
		System.err.println("path: " + path);
		String entityName = path.substring(1);
		Entity e = myManager.getEntity(entityName);
		Navajo input = myManager.deriveNavajoFromParameterMap(e, requestParameters);
		// Fetch username/password.
		String username = requestParameters.get("username")[0];
		String password = requestParameters.get("password")[0];
		Header header = NavajoFactory.getInstance().createHeader(input, "", username, password, -1);
		input.addHeader(header);
		try {
			Operation o = myManager.getOperation(entityName, method);
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, myClient);
			seo.perform(input, o).write(response.getOutputStream());
			 
		} catch (EntityException e1) {
			throw new ServletException(e1.getMessage(), e1);
		}

	}



}
