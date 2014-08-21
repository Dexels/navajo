package com.dexels.navajo.entity.listener;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dexels.utils.Base64;
import org.dexels.utils.Base64.DecodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Set<String> SUPPORTED_OUTPUT = new HashSet<String>(Arrays.asList("json", "xml", "tml"));
	private final static Logger logger = LoggerFactory.getLogger(EntityListener.class);

	private EntityManager myManager;
	private LocalClient myClient;
	private String urlOutput;
	private String username;
	private String password;

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

		resetParameters();
		Navajo result = null;
		String method = request.getMethod();
		String path = request.getPathInfo();
		
		// Check for a .<format> in the URL - can be in RequestURI or QueryString
		String dotString = null; 
		if (request.getRequestURI().contains(".")) {
			dotString = request.getRequestURI();
		} else if (request.getQueryString() != null && request.getQueryString().contains(".")) {
			dotString = request.getQueryString();
		}
		if (dotString != null) {
			// The output format can be set by adding a trailing .<format> to the URL.
			// This overrules accept-encoding
			urlOutput = dotString.substring(dotString.lastIndexOf('.') +1 );
			if (!SUPPORTED_OUTPUT.contains(urlOutput)) {
				// unsupported format
				urlOutput = null;
			}
		}
		String output = getOutputFormat(request);
		// Only end-points are allowed to cache - no servers in between
		response.setHeader("Cache-Control", "private");
		response.setHeader("Content-Type", "application/"+output);
	
		authenticateRequest(request);

		String entityName = path.substring(1);
		if (entityName.indexOf('.') > 0) {
			// Remove .<format> from entityName
			entityName = entityName.substring(0, entityName.indexOf('.'));
		}

		Navajo input = null;
		String etag = null;
		
		logger.info("Incoming entity request from {}: entity={}, user={}, method={}, output={}",
				request.getRemoteAddr(), entityName, username, method, output);
		
		try {
			if (entityName == "") {
				logger.error("No entity name found in request. Request URI: {}", request.getRequestURI());
				throw new EntityException(EntityException.BAD_REQUEST);
			}
			
			Entity e = myManager.getEntity(entityName);
			if (e == null) {
				// Requested entity not found
				logger.error("Requested entity not registred with entityManager!");
				throw new EntityException(EntityException.ENTITY_NOT_FOUND);
			}

			Message entityMessage = e.getMessage();
			
			// Get the input document
			if (method.equals("GET") || method.equals("DELETE")) {
				input = myManager.deriveNavajoFromParameterMap(e, request.getParameterMap());
			} else {
				JSONTML json = JSONTMLFactory.getInstance();
				json.setEntityTemplate(entityMessage.getRootDoc());
				try {
					input = json.parse(request.getInputStream(), entityMessage.getName());
				} catch (Exception e1) {
					logger.error("Error in parsing input JSON");
					throw new EntityException(EntityException.BAD_REQUEST);
				}
			}

			if (input.getMessage(entityMessage.getName()) == null) {
				logger.error("Entity name not found in input - format incorrect or bad request"); 
				throw new EntityException(EntityException.BAD_REQUEST);

			}
			
			etag = request.getHeader("If-Match");
			if (etag == null) {
				etag = request.getHeader("If-None-Match");
			}

			// Create a header from the input
			Header header = NavajoFactory.getInstance().createHeader(input, "", username, password,-1);
			input.addHeader(header);
			input.getMessage(entityMessage.getName()).setEtag(etag);
			
			Operation o = myManager.getOperation(entityName, method);
			logger.debug("Found matching entity operation"); 
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, myClient, o);
			result = seo.perform(input);
			logger.debug("Performed entity operation"); 

			if ( result.getMessage(entityMessage.getName() ) != null ) {
				// Merge with entity template
				EntityHelper.mergeWithEntityTemplate(result.getMessage(entityMessage.getName() ), entityMessage );
				logger.debug("Merging output of Operation and Entity"); 
				if (method.equals("GET")) {
					response.setHeader("Etag", result.getMessage(entityMessage.getName()).getEtag());
					logger.debug("Added Etag header"); 

				}
			}
		} catch (Exception ex) {
			result = handleException(ex, request, response);
		}
		writeOutput(result, response, output);
		resetParameters();
	}

	private void writeOutput(Navajo result, HttpServletResponse response, String output)
			throws IOException, ServletException {
		if (result == null) {
			throw new ServletException("No output found");
		}
		logger.info("Writing entity output");

		if (result.getMessage("errors") != null) {
			String status = result.getMessage("errors").getProperty("Status").toString();
			if (status.equals("304")) {
				// No content
				logger.info("Returning HTTP code 304 - not modified");
				return;
			}
		}
		if ( output.equals("json"))  {
			response.setHeader("content-type", "application/json");
			Writer w = new OutputStreamWriter(response.getOutputStream());
			JSONTML json = JSONTMLFactory.getInstance();
			json.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			try {
				json.format(result, w, true);
			} catch (Exception e) {
				logger.error("Error in writing entity output in JSON!");
				throw new ServletException("Error producing output");
			}
			w.close();
		} else if ( output.equals("xml") ) {
			response.setHeader("content-type", "text/xml");
			NavajoLaszloConverter.writeBirtXml(result, response.getWriter());
		} else {
			response.setHeader("content-type", "text/xml");
			result.write(response.getOutputStream());
		}
	}

	private void resetParameters() {
		// To prevent LocalClient from adding username & password if none are given, 
		// we reset the username and password to a string with one space character
		username = " ";
		password = " ";
		urlOutput = null;
	}

	private void authenticateRequest(HttpServletRequest request) {
		// TODO: better security, such as API keys
		// Furthermore check authorization
		basicAuthentication(request);
		if (username == null || username.trim().equals("")) {
			// TODO: This is very unsafe
			logger.warn("No basic auth - attemping username/password parameter");
			if (request.getParameterMap().containsKey("username")) {
				logger.info("Taking username/password from URI parameters");
				username = request.getParameter("username");
				password = request.getParameter("password");
			}
		}
	}	
	
	private void basicAuthentication(HttpServletRequest req) {
		String authHeader = req.getHeader("Authorization");
		if (authHeader != null) {
			StringTokenizer st = new StringTokenizer(authHeader);
			if (st.hasMoreTokens()) {
				if (st.nextToken().equalsIgnoreCase("Basic")) {
					String credentials;
					try {
						credentials = new String(Base64.decode(st.nextToken()));

						int p = credentials.indexOf(":");
						if (p != -1) {
							username = credentials.substring(0, p).trim();
							password = credentials.substring(p + 1).trim();

						} else {
							logger.error("Invalid authentication token: {}", credentials);

						}
					} catch (DecodingException e) {
						logger.error("DecodingException in attemping to decode http basic authentication header");
					}

				}
			}
		}
	}

	// In case of an exception, we create a Navajo document with some messages describing
	// the error. This allows us to output the exception in the format the user requested
	// (eg.g JSON).
	private Navajo handleException(Exception ex, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		Navajo result = null;
		logger.warn("Exception in handling entity request: {}. Going to try to handle it nicely.", ex);

		result = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(result, "errors");
		result.addMessage(m);
		m.addProperty(NavajoFactory.getInstance().createProperty(result, "Error", "boolean", "true", 1, null, null));
		if (ex instanceof EntityException) {
			response.setStatus(((EntityException) ex).getCode());
			int code = ((EntityException) ex).getCode();
			m.addProperty(NavajoFactory.getInstance().createProperty(result, "Status", "string",
					String.valueOf(code), 1, null, null));
			m.addProperty(NavajoFactory.getInstance().createProperty(result, "Message", "string",
					ex.getMessage(), 1, null, null));

		} else {
			response.setStatus(EntityException.SERVER_ERROR);
			m.addProperty(NavajoFactory.getInstance().createProperty(result, "Status", "string",
					String.valueOf(EntityException.SERVER_ERROR), 1, null, null));
			m.addProperty(NavajoFactory.getInstance().createProperty(result, "Message", "string",
					"Server error (" + ex.toString(), 1, null, null));
		}

		return result;
	}
	
	private String getOutputFormat(HttpServletRequest request)  {
		if (urlOutput != null) {
			// Explicit output in URL gets preference over Accept header
			return urlOutput;
		}
		
		String mimeResult = null;
		String header = request.getHeader("Accept");
		if (header != null) {
			try {
				String reqTypes[] = header.split(",");
				for (String reqType :reqTypes ) {
					String mime = reqType;
					if (reqType.indexOf(';') > 0) {
						mime = reqType.substring(0, reqType.indexOf(';'));
					}
					
					MimeType n = new MimeType(mime);
					mimeResult = n.getSubType();
					if (SUPPORTED_OUTPUT.contains(mimeResult)) {
						// Found a supported type!
						break;
					}
				}
				
			} catch (MimeTypeParseException e) {
				logger.warn("MimeTypeParseException in getting mime-types from Accept header - using default output");
				mimeResult = DEFAULT_OUTPUT_FORMAT;
			}
		}

		if (!SUPPORTED_OUTPUT.contains(mimeResult)) {
			logger.info("No supported output format requested - using default output");
			mimeResult = DEFAULT_OUTPUT_FORMAT;
		}
		return mimeResult;
	}
}
