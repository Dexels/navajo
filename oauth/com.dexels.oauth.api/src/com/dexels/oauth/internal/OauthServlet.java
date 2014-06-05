package com.dexels.oauth.internal;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.dexels.oauth.api.ClientRegistration;
import com.dexels.oauth.api.ClientStore;
import com.dexels.oauth.api.ScopeValidator;
import com.dexels.oauth.api.Token;
import com.dexels.oauth.api.TokenStore;
import com.dexels.oauth.api.UserAuthenticator;
import com.dexels.oauth.api.impl.EqualUserAuthenticator;
import com.dexels.oauth.api.impl.InMemoryClientStore;
import com.dexels.oauth.api.impl.InMemoryTokenStore;
import com.dexels.oauth.api.impl.SimpleScopeValidator;

public class OauthServlet extends HttpServlet {

	private static final long serialVersionUID = -1948354354961917987L;

	private UserAuthenticator userAuthenticator = null;
	private TokenStore tokenStore = null;
	private ClientStore clientStore = null;
	private ScopeValidator scopeValidator;
	
	public void activate() {
//		this.userAuthenticator = new EqualUserAuthenticator();
//		this.tokenStore = new InMemoryTokenStore();
//		this.scopeValidator = new SimpleScopeValidator();
//		this.clientStore = new InMemoryClientStore();
		final String clientId = "5CO5fUgfoGOQMus628uG/MKRkcdu6aIZS6EUSwLaUbkdLVxzaMGYTQg5UJQDhF5dnizrZNEy5KYHMcVb2kzWjLW8V3CprGk5qZA0CYvyw1Q=";
		this.clientStore.registerClient(clientId, new ClientRegistration() {
			
			@Override
			public String getRedirectUriPrefix() {
				return "http://localhost:8080/data/KNVB/wedstrijd-accommodatie";
			}
			
			@Override
			public String getClientId() {
				return clientId;
			}
			
			@Override
			public String getClientDescription() {
				return "Club: BBCY45I";
			}
		});
		this.clientStore.registerClient("123", new ClientRegistration() {
			
			@Override
			public String getRedirectUriPrefix() {
				return "http://localhost";
			}
			
			@Override
			public String getClientId() {
				return "123";
			}

			@Override
			public String getClientDescription() {
				return "De Apenheul";
			}
		});
		
	}
	
	
	public void setTokenStore(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	public void clearTokenStore(TokenStore tokenStore) {
		this.tokenStore = null;
	}

	public void setClientStore(ClientStore clientStore) {
		this.clientStore = clientStore;
	}

	public void clearClientStore(ClientStore clientStore) {
		this.clientStore = null;
	}

	public void setUserAuthenticator(UserAuthenticator userAuthenticator) {
		this.userAuthenticator = userAuthenticator;
	}
	
	public void clearUserAuthenticator(UserAuthenticator userAuthenticator) {
		this.userAuthenticator = null;
	}
	
	public void setScopeValidator(ScopeValidator scopeValidator) {
		this.scopeValidator = scopeValidator;
	}
	
	public void clearScopeValidator(ScopeValidator scopeValidator) {
		this.scopeValidator = null;
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		System.err.println("Pathinfo: "+pathInfo);
		if(pathInfo==null) {
			initialAuth(req,resp);
			return;
		}
		if("/access_token".equals(pathInfo)) {
			accessToken(req,resp);
			return;
		}
		if("/loginstatus".equals(pathInfo)) {
			loginstatus(req, resp);
			return;
		}
		if("/requestedscopes".equals(pathInfo)) {
			requestedscopes(req, resp);
			return;
		}
		if("/authorizeendpoint".equals(pathInfo)) {
			authorizeendpoint(req, resp);
			return;
		}
		if("/loginendpoint".equals(pathInfo)) {
			login(req, resp);
			return;
		}
		if("/scopes".equals(pathInfo)) {
			scopes(req, resp);
			return;
		}
		
		
	}

//	http://localhost:8080/oauth?redirect_uri=http://www.aap.nl&client_id=123&scope=aapje,olifantje&state=456

	private void initialAuth(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		req.getSession().invalidate();
		String client_id = req.getParameter("client_id");
		String[] scope = req.getParameter("scope").split(",");
		String state = req.getParameter("state");
		String responseType = req.getParameter("response_type");
		String redirect_uri = req.getParameter("redirect_uri");
		req.getSession().setAttribute("client_id", client_id);
		req.getSession().setAttribute("scope", scope);
		req.getSession().setAttribute("state", state);
		req.getSession().setAttribute("redirect_uri", redirect_uri);
		
		for (String s : scope) {
			if(!this.scopeValidator.isValidScope(s)) {
				throw new ServletException("Illegal scope: "+s);
			}
		}
		if(!"token".equals(responseType)) {
			throw new ServletException("Only token auth supported for now!");
		}
		String username = (String) req.getAttribute("username");
		if(username==null) {
			// better to check for token presence & validity
			resp.sendRedirect("/ui/login.html");
			return;
		}
		String[] authorizedScopes = getRequestedScopes(req);
		String[] requestedScopes = req.getParameterMap().get("scope");
		boolean scopesPresent = verifyScopes(authorizedScopes,requestedScopes);
		if(scopesPresent) {
			if(clientStore.verifyRedirectURL(client_id,redirect_uri)) {
				Token token =  tokenStore.generateToken(client_id,scope,username,redirect_uri);
				String populatedURI = insertTokenToURI(redirect_uri,token.toString());
				resp.sendRedirect(populatedURI);
				return;
				// all done
			} else {
				// redirect to: illegal redirect url error
				req.getSession().setAttribute("errorheader","Authenticatie probleem");
				req.getSession().setAttribute("error","Redirect URL ongeldig");
				// todo: redirect
			}
		} else {
			resp.sendRedirect("/ui/authorize.html");
		}
	}

	private String insertTokenToURI(String redirect_uri, String token) {
	return redirect_uri+"#"+token;
}


//	/authorizeendpoint
	
	private void authorizeendpoint(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String[] authorizedScopes = req.getParameterValues("scope");
		String[] scopes = (String[])req.getSession().getAttribute("scope");
		
//		String client_id = req.getParameter("client_id");
		String client_id = (String) req.getSession().getAttribute("client_id");

		String redirect_uri = (String) req.getSession().getAttribute("redirect_uri");

		String action = req.getParameter("action");
		
		if(!action.equals("Accept")) {
			resp.sendRedirect(redirect_uri);
			return;
		}

		boolean scopesPresent = verifyScopes(authorizedScopes,scopes);
		
		if(scopesPresent) {
			if(clientStore.verifyRedirectURL(client_id,redirect_uri)) {
				String username = (String) req.getSession().getAttribute("username");

				Token token =  tokenStore.generateToken(client_id,scopes,username,redirect_uri);
				String populatedURI = insertTokenToURI(redirect_uri,"token="+token.toString());
				System.err.println("Redirecting to: "+populatedURI);

				resp.sendRedirect(populatedURI);
				return;
				// all done
			} else {
				// redirect to: illegal redirect url error
				req.getSession().setAttribute("errorheader","Authenticatie probleem");
				req.getSession().setAttribute("error","Redirect URL ongeldig");
				// todo: redirect
				resp.sendRedirect("/ui/error.html");

			}
		} else {
			resp.sendRedirect("/ui/authorize.html");
			return;
		}
		// boolean scopesPresent = verifyScopes(requestedScopes,providedScopes);
		// post token with selected scopes
		return;
	}
	private String[] getRequestedScopes(HttpServletRequest req) {
		String[] scope = (String[]) req.getSession().getAttribute("scope");
		return scope;
	}	
	// for server flow:
	private void accessToken(HttpServletRequest req, HttpServletResponse resp) {
		String client_id = req.getParameter("client_id");
		String client_secret = req.getParameter("client_secret");
		String code = req.getParameter("code");
		String redirect_uri = req.getParameter("redirect_uri");
	}

	

	private void loginstatus(HttpServletRequest req, HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode(); 
		String error = (String) req.getSession().getAttribute("error");
		String errorheader = (String) req.getSession().getAttribute("errorheader");
		String clientId = (String) req.getSession().getAttribute("client_id");
		ClientRegistration cr = this.clientStore.getClient(clientId);
		if(cr==null) {
			throw new IllegalArgumentException("Unknown client id: "+clientId);
		}
		rootNode.put("clientdescription", cr.getClientDescription());
		if(error!=null) {
			rootNode.put("error", error);
		}
		if(errorheader!=null) {
			rootNode.put("errorheader", errorheader);
		}
		resp.setContentType("application/json");
		mapper.writerWithDefaultPrettyPrinter().writeValue(resp.getWriter(), rootNode);
	}
	
	private void requestedscopes(HttpServletRequest req,
			HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException {
		String[] scopes = getRequestedScopes(req);
		String clientId = (String) req.getSession().getAttribute("client_id");
		ClientRegistration cr = this.clientStore.getClient(clientId);
		if(cr==null) {
			throw new IllegalArgumentException("Unknown client id: "+clientId);
		}

//		String[] scopes = scope.split(",");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode(); 
		ArrayNode requestedScopes = mapper.createArrayNode(); 
		for (String s : scopes) {
			ObjectNode element = mapper.createObjectNode(); 
			element.put("name",s);
			element.put("description",this. scopeValidator.getScopeDescription(s));
			requestedScopes.add(element);
		}
		rootNode.put("requestedScopes", requestedScopes);
		rootNode.put("clientdescription", cr.getClientDescription());

		resp.setContentType("application/json");
		mapper.writerWithDefaultPrettyPrinter().writeValue(resp.getWriter(), rootNode);

	}

	private void scopes(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String token = req.getParameter("token");
		Token t= this.tokenStore.getTokenByString(token);
		if(t==null) {
			resp.sendError(500, "Token expired");
			return;
		}
		if(t.isExpired()) {
			resp.sendError(500, "Token expired");
			return;
		}
		System.err.println("Token of: "+t.getUsername()+" has scopes: "+t.scopes()+" will expire in: "+t.getExpirySeconds()+" seconds");
		resp.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode(); 
		rootNode.put("username", t.getUsername());
		rootNode.put("clientId", t.clientId());
		rootNode.put("expirySeconds", t.getExpirySeconds());
		ArrayNode scopes = mapper.createArrayNode();
		for (String sco : t.scopes()) {
			scopes.add(sco);
		}
		
		rootNode.put("scopes", scopes);
		mapper.writerWithDefaultPrettyPrinter().writeValue(resp.getWriter(), rootNode);
	}
	
	private void login(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.err.println("User: "+username);
//		String redirect_url = req.getParameter("redirect_url");
//		String[] providedScopes = req.getParameterMap().get("scope");
		
		String client_id = (String) req.getSession().getAttribute("client_id");
		String client_secret = (String) req.getSession().getAttribute("client_secret");
		String redirect_uri = (String) req.getSession().getAttribute("redirect_uri");
		boolean succeeded = userAuthenticator.authenticateUser(username, password,client_id);
		if(!succeeded) {
//			req.getSession().invalidate();
			req.getSession().setAttribute("error", "Username incorrect");
			req.getSession().setAttribute("errorheader", "Authentication problem");
			resp.sendRedirect("/ui/login.html");
			return;
		} else {
			req.getSession().setAttribute("error", null);
			req.getSession().setAttribute("errorheader", null);
		}
		
		req.getSession().setAttribute("username", username);

		String[] scopes = getRequestedScopes(req);
		String[] authorizedScopes = (String[])req.getSession().getAttribute("authorizedScopes");
		boolean scopesPresent = verifyScopes(authorizedScopes,scopes);
		if(scopesPresent) {
			if(clientStore.verifyRedirectURL(client_id,redirect_uri)) {
				Token token =  tokenStore.generateToken(client_id,scopes,username,redirect_uri);
				String populatedURI = insertTokenToURI(redirect_uri,token.toString());
				resp.sendRedirect(populatedURI);
				return;
				// all done
			} else {
				// redirect to: illegal redirect url error
				req.getSession().setAttribute("errorheader","Authenticatie probleem");
				req.getSession().setAttribute("error","Redirect URL ongeldig");
				// todo: redirect
			}
		} else {
			resp.sendRedirect("/ui/authorize.html");
			return;
		}
	}

	private boolean verifyScopes(String[] authorizedScopes, String[] requestedScopes) {
		Set<String> sessionScopes = new HashSet<String>();
		if(authorizedScopes!=null) {
			for (String e : authorizedScopes) {
				sessionScopes.add(e);
			}
		}
		for (String scope : requestedScopes) {
			if(!sessionScopes.contains(scope)) {
				return false;
			}
		}
		return true;
	}


	
}
