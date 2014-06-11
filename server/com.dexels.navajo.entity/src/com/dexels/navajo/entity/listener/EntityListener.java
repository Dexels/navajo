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
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.entity.util.EntityHelper;
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

		boolean debug = ( requestParameters.get("debug") != null ? true : false );
		String output = ( requestParameters.get("output") != null ?requestParameters.get("output")[0] : "json" );
		String username = requestParameters.get("username")[0];
		String password = requestParameters.get("password")[0];

		String entityName = path.substring(1);
		if ( debug ) {
			System.err.println("method: " + method);
			System.err.println("path: " + path);
			System.err.println("entityName: " + entityName);
		}
		Entity e = myManager.getEntity(entityName);

		Navajo input = null;
		Message entityMessage = e.getMessage();
		
		// If HEAD, simply print the entityMessage
		if ( method.equals("HEAD") ) {
			Writer w = new OutputStreamWriter(response.getOutputStream());
			JSONTML json = JSONTMLFactory.getInstance();
			try {
				json.format(entityMessage.getRootDoc(), w);
			} catch (Exception e1) {
				throw new ServletException(e1);
			}
			w.close();
			return;
		}
		
		// Get the input document 
		if (method.equals("GET")) {
			input = myManager.deriveNavajoFromParameterMap(e, requestParameters);
		} else {
			JSONTML json = JSONTMLFactory.getInstance();
			json.setEntityTemplate(entityMessage.getRootDoc());
			try {
				input = json.parse(request.getInputStream());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		if ( debug ) {
			System.err.println("Received request:");
			input.write(System.err);
		}
		
		// Create a header from the input
		Header header = NavajoFactory.getInstance().createHeader(input, "", username, password, -1);
		input.addHeader(header);
		
		try {
			Operation o = myManager.getOperation(entityName, method);
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, myClient, o);
			Navajo result = seo.perform(input);
			// Merge with entity template
			if ( result.getMessage(entityMessage.getName() ) != null ) {
				EntityHelper.mergeWithEntityTemplate(result.getMessage(entityMessage.getName() ), entityMessage );
			}
			if ( output.equals("json")) {
				Writer w = new OutputStreamWriter(response.getOutputStream());
				JSONTML json = JSONTMLFactory.getInstance();
				json.format(result, w);
				w.close();
			} else if ( output.equals("xml") ) {
				NavajoLaszloConverter.writeBirtXml(result, response.getWriter());
			} else {
				result.write(response.getOutputStream());
			}
		} catch (EntityException e1) {
			response.sendError(e1.getCode(), e1.getMessage());
			throw new ServletException(e1.getMessage(), e1);
		} catch (Exception e2) {
			throw new ServletException(e2.getMessage(), e2);
		}

	}



}
