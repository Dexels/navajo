package com.dexels.navajo.entity.listener;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.script.api.LocalClient;

public class EntityListener extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6681359881499760460L;

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
		Navajo input = null;
		if ( method.equals("GET")) {
			input = myManager.deriveNavajoFromParameterMap(e, requestParameters);
		} else {
			JSONTML json = JSONTMLFactory.getInstance();
			json.setEntityTemplate(e.getMessage().getRootDoc());
			try {
				input = json.parse(request.getInputStream());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		//System.err.println("Reveived request:");
		//input.write(System.err);
		// Fetch output method
		String output = ( requestParameters.get("output") != null ?requestParameters.get("output")[0] : "json" );
		// Fetch username/password.
		String username = requestParameters.get("username")[0];
		String password = requestParameters.get("password")[0];
		Header header = NavajoFactory.getInstance().createHeader(input, "", username, password, -1);
		input.addHeader(header);

		try {
			Operation o = myManager.getOperation(entityName, method);
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, myClient, o);
			if ( output.equals("json")) {
				Writer w = new OutputStreamWriter(response.getOutputStream());
				JSONTML json = JSONTMLFactory.getInstance();
				json.format(seo.perform(input), w);
				w.close();
			} else {
				seo.perform(input).write(response.getOutputStream());
			}
		} catch (EntityException e1) {
			response.sendError(e1.getCode(), e1.getMessage());
			throw new ServletException(e1.getMessage(), e1);
		} catch (Exception e2) {
			throw new ServletException(e2.getMessage(), e2);
		}

	}



}
