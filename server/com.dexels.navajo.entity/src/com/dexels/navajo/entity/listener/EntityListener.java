package com.dexels.navajo.entity.listener;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.StringTokenizer;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
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
import com.dexels.sportlink.functions.Base64;

public class EntityListener extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6681359881499760460L;
	private final static String DEFAULT_OUTPUT_FORMAT = "json";
	private static final Set<String> SUPPORTED_OUTPUT = new HashSet<String>(Arrays.asList("json", "xml", "tml"));
	
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
		String query = request.getQueryString();
		
		if (query != null && query.lastIndexOf('.') > 0) {
			urlOutput = query.substring(query.lastIndexOf('.') +1 );
			if (!SUPPORTED_OUTPUT.contains(urlOutput)) {
				// unsupported format
				urlOutput = null;
			}
		}
		String output = getOutputFormat(request);
		// Only end-points are allowed to cache - no servers in between
		response.setHeader("Cache-Control", "private");
		response.setHeader("Content-Type", "application/"+output);

		boolean debug = (request.getParameter("debug") != null ? true : false);
		
		authenticateRequest(request);

		String entityName = path.substring(1);
		if ( debug ) {
			System.err.println("method: " + method);
			System.err.println("path: " + path);
			System.err.println("entityName: " + entityName);
		}
		Entity e = myManager.getEntity(entityName);
		
		if (e == null) {
			// Requested entity not found
			handleException(new EntityException(EntityException.ENTITY_NOT_FOUND), request, response);
			return;
		}

		Navajo input = null;
		Message entityMessage = e.getMessage();
		String etag = null;
		
		try {
			// Get the input document
			if (method.equals("GET") || method.equals("DELETE")) {
				input = myManager.deriveNavajoFromParameterMap(e, request.getParameterMap());
			} else {
				JSONTML json = JSONTMLFactory.getInstance();
				json.setEntityTemplate(entityMessage.getRootDoc());
				try {
					input = json.parse(request.getInputStream());
				} catch (Exception e1) {
					throw new EntityException(EntityException.BAD_REQUEST);
				}
			}
			
			etag = request.getHeader("If-Match");
			if (etag == null) {
				etag = request.getHeader("If-None-Match");
			}

			if (debug) {
				System.err.println("Received request:");
				input.write(System.err);
			}

			// Create a header from the input
			Header header = NavajoFactory.getInstance().createHeader(input, "", username, password,-1);
			input.addHeader(header);
			input.getMessage(entityMessage.getName()).setEtag(etag);
			
			Operation o = myManager.getOperation(entityName, method);
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, myClient, o);
			result = seo.perform(input);
			
			if ( result.getMessage(entityMessage.getName() ) != null ) {
				// Merge with entity template
				EntityHelper.mergeWithEntityTemplate(result.getMessage(entityMessage.getName() ), entityMessage );
				if (method.equals("GET")) {
					response.setHeader("Etag", result.getMessage(entityMessage.getName()).getEtag());
				}
			}
		} catch (Exception ex) {
			result = handleException(ex, request, response);
		}
		writeOutput(result, response, output);
	}

	private void writeOutput(Navajo result, HttpServletResponse response, String output)
			throws IOException, ServletException {
		if (result == null) {
			throw new ServletException("No output found");
		}
		if (result.getMessage("errors") != null) {
			String status = result.getMessage("errors").getProperty("status").toString();
			if (status.equals("304")) {
				// No content
				return;
			}
		}
		if ( output.equals("json"))  {
			Writer w = new OutputStreamWriter(response.getOutputStream());
			JSONTML json = JSONTMLFactory.getInstance();
			try {
				json.format(result, w);
			} catch (Exception e) {
				throw new ServletException("Error producing output");
			}
			w.close();
		} else if ( output.equals("xml") ) {
			NavajoLaszloConverter.writeBirtXml(result, response.getWriter());
		} else {
			result.write(response.getOutputStream());
		}
	}

	private void resetParameters() {
		username = null;
		password = null;
		urlOutput = null;
	}

	private void authenticateRequest(HttpServletRequest request) {
		// TODO: better security, such as API keys
		// Furthermore check authorization
		basicAuthentication(request);
		if (username == null) {
			// TODO: This is very unsafe
			username = request.getParameter("username");
			password = request.getParameter("password");
		}
	}	
	
	private void basicAuthentication(HttpServletRequest req) {
		String authHeader = req.getHeader("Authorization");
		if (authHeader != null) {
			StringTokenizer st = new StringTokenizer(authHeader);
			if (st.hasMoreTokens()) {
				if (st.nextToken().equalsIgnoreCase("Basic")) {
					try {
						String credentials = new String(Base64.decode(st.nextToken()), "UTF-8");
						int p = credentials.indexOf(":");
						if (p != -1) {
							username = credentials.substring(0, p).trim();
							password = credentials.substring(p + 1).trim();

						} else {
							System.err.println("Invalid authentication token");
						}
					} catch (UnsupportedEncodingException e) {
						System.err.println("Couldn't retrieve authentication");
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
		
		result = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(result, "errors");
		result.addMessage(m);
		if (ex instanceof EntityException) {
			response.setStatus(((EntityException) ex).getCode());
			int code = ((EntityException) ex).getCode();
			m.addProperty(NavajoFactory.getInstance().createProperty(result, "status", "string",
					String.valueOf(code), 1, null, null));
			m.addProperty(NavajoFactory.getInstance().createProperty(result, "error", "string",
					ex.getMessage(), 1, null, null));

		} else {
			response.setStatus(EntityException.SERVER_ERROR);
			m.addProperty(NavajoFactory.getInstance().createProperty(result, "status", "string",
					String.valueOf(EntityException.SERVER_ERROR), 1, null, null));
			m.addProperty(NavajoFactory.getInstance().createProperty(result, "error", "string",
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
				mimeResult = DEFAULT_OUTPUT_FORMAT;
			}
		}

		if (!SUPPORTED_OUTPUT.contains(mimeResult)) {
			mimeResult = DEFAULT_OUTPUT_FORMAT;
		}
		return mimeResult;
	}

}
