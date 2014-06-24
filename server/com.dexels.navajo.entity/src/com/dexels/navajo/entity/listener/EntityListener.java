package com.dexels.navajo.entity.listener;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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
	private final static String DEFAULT_OUTPUT_FORMAT = "json";
	private static final Set<String> SUPPORTED_OUTPUT = new HashSet<String>(Arrays.asList("json",
			"xml", "tml"));
	
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
		String username = requestParameters.get("username")[0];
		String password = requestParameters.get("password")[0];

		String entityName = path.substring(1);
		if ( debug ) {
			System.err.println("method: " + method);
			System.err.println("path: " + path);
			System.err.println("entityName: " + entityName);
		}
		Entity e = myManager.getEntity(entityName);
		
		if (e == null) {
			// Requested entity not found
			handleEntityException(new EntityException(EntityException.ENTITY_NOT_FOUND), request, response);
			return;
		}

		Navajo input = null;
		Message entityMessage = e.getMessage();
		
		
		try {
			// Get the input document
			if (method.equals("GET") || method.equals("DELETE")) {
				input = myManager.deriveNavajoFromParameterMap(e, requestParameters);
			} else {
				JSONTML json = JSONTMLFactory.getInstance();
				json.setEntityTemplate(entityMessage.getRootDoc());
				try {
					input = json.parse(request.getInputStream());
				} catch (Exception e1) {
					throw new EntityException(EntityException.BAD_REQUEST);
				}
			}

			if (debug) {
				System.err.println("Received request:");
				input.write(System.err);
			}

			// Create a header from the input
			Header header = NavajoFactory.getInstance().createHeader(input, "", username, password,-1);
			input.addHeader(header);
		
		
			String output = getOutputFormat(request);
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
			System.out.println("ent");
			handleEntityException(e1, request, response);
		} catch (Exception e2) {
			System.out.println("gen");

			handleGenericException(e2, request, response);
		}

	}


	private void handleEntityException(EntityException e1, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		try {
			response.setStatus(e1.getCode());
			
			String output = this.getOutputFormat(request);
			if (output.equals("json")) {
				response.getWriter().println(errorToJson(e1));
			} else if (output.equals("xml")) {
				response.getWriter().println(errorToJson(e1));
			} else {
				response.getWriter().println(e1.getMessage());
			}
		} catch (EntityException ex1) {
			// Exception while trying to handle existing exception...
			throw new ServletException(ex1.getMessage(), ex1);
		} catch (IOException ex1) {
			// Exception while trying to handle existing exception...
			throw new ServletException(ex1.getMessage(), ex1);
		}
	}
	
	private void handleGenericException(Exception e1, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		try {
			response.setStatus(EntityException.SERVER_ERROR);
			
			String output = this.getOutputFormat(request);
			if (output.equals("json")) {
				response.getWriter().println(errorToJson(e1));
			} else if (output.equals("xml")) {
				response.getWriter().println(errorToJson(e1));
			} else {
				response.getWriter().println(e1.getMessage());
			}
		} catch (EntityException ex1) {
			// Exception while trying to handle existing exception...
			// Next statement will throw ServletException
		} catch (IOException e) {
			// Exception while trying to handle existing exception...
			// Next statement will throw ServletException
		}

		//throw new ServletException(e1.getMessage(), e1);
	}
	

	private String errorToJson(Exception e) throws JsonMappingException, IOException{
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errors", e.getMessage());
		if (e instanceof EntityException) {
			result.put("code", ((EntityException) e).getCode());
		}
		return om.writeValueAsString(result);
	}


	private String getOutputFormat(HttpServletRequest request) throws EntityException {
		// TODO: get output from Accept-header
		String output;
		
		if ((output = request.getParameter("output")) == null) {
			output = DEFAULT_OUTPUT_FORMAT;
		}
		if (!SUPPORTED_OUTPUT.contains(output)) {
			throw new EntityException(EntityException.OUTPUT_NOT_ACCEPTABLE);
		}
		return output;
	}

}
