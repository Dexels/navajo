package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.felix.service.command.CommandSession;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;

public class Authorize extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	
	public void list(CommandSession session ) throws IOException {
		Map<String,Map<String,ApplicationStatus>> wrap = new HashMap<String, Map<String,ApplicationStatus>>();
		wrap.put("applications", applications);
		writeValueToJsonArray(session.getConsole(),wrap);
	}
	

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String code = req.getParameter("code");
		resp.setContentType("application/json");
		Map<String,String> params = new HashMap<String, String>();
		params.put("client_id", applicationManager.getClientId());
		params.put("code", code);
		params.put("client_secret", applicationManager.getClientSecret());

		post("https://github.com/login/oauth/access_token",params,session);
		final String access_token = (String) session.getAttribute("access_token");
		System.err.println("::: https://api.github.com/user?access_token="+access_token);
		JsonNode user = callGithub("/user", access_token);
		final String login = user.get("login").asText();
		System.err.println("username: "+login);
		System.err.println("Name: "+user.get("name").asText());
		String username = user.get("name").asText();
		
		boolean found = false;
		String organization = applicationManager.getOrganization();
		ArrayNode members = (ArrayNode) callGithub("/orgs/"+organization+"/members", access_token);
		for (JsonNode member : members) {
			String currentLogin = member.get("login").asText();
			if(currentLogin.equals(login)) {
				found = true;
			}
		}
		if(found) {
			session.setAttribute("username", username);
			session.setAttribute("authorized", true);
			session.setAttribute("image", user.get("avatar_url").asText());
			session.setAttribute("company", user.get("company").asText());
			session.setAttribute("email", user.get("email").asText());
		}
		resp.sendRedirect("ui/index.html");
	}
	
	
	private JsonNode callGithub(String path, String access_token) throws JsonParseException, IOException {
		String url = "https://api.github.com"+path+"?access_token="+access_token;
		URL u = new URL(url);
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
		JsonParser jp = factory.createJsonParser(u.openStream());
		JsonNode node = mapper.readTree(jp);		
		mapper.writerWithDefaultPrettyPrinter().writeValue(System.err,node);
		return node;
		
		
	}
	private void post(String url, Map<String,String> params, HttpSession session) throws IOException {
		URL u = new URL("https://github.com/login/oauth/access_token");
		HttpURLConnection uc = (HttpURLConnection) u.openConnection();

		uc.setReadTimeout(10000);
		uc.setConnectTimeout(15000);
		uc.setRequestMethod("POST");
		uc.setRequestProperty("Accept", "application/json");
		uc.setDoInput(true);
		uc.setDoOutput(true);
		OutputStream os = uc.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
		        new OutputStreamWriter(os, "UTF-8"));
		writer.write(getPostParamString(params));
		writer.flush();
		writer.close();
		os.close();

		uc.connect();	
		JsonFactory jfactory = new JsonFactory();
		 
		/*** read from file ***/
		JsonParser jp = jfactory.createJsonParser(uc.getInputStream());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(jp);		
		
		String token = node.get("access_token").asText();
		
		session.setAttribute("access_token", token);
		session.setAttribute("authorized", true);
		
	}
	
	private static String getPostParamString(Map<String, String> params) throws UnsupportedEncodingException {
	    if(params.size() == 0)
	        return "";

	    StringBuffer buf = new StringBuffer();
	    Set<String> keys = params.keySet();
	    for (String key : keys) {
	        buf.append(buf.length() == 0 ? "" : "&");
	        buf.append(URLEncoder.encode(key,"UTF-8")).append("=").append(URLEncoder.encode(params.get(key),"UTF-8"));
		}
	    return buf.toString();
	}

	@Override
	public void build(ApplicationStatus a) throws IOException {

	}
}
