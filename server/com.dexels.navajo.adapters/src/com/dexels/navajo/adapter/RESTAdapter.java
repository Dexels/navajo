package com.dexels.navajo.adapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dexels.utils.Base64;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.ConditionErrorException;

public class RESTAdapter extends NavajoMap {

	public String url;
	public String method;
	public int responseCode;
	public String responseMessage;
	public String topMessage = "Response";
	public boolean removeTopMessage = false;
	protected List<String> parameters = new ArrayList<String>();
	protected Map<String, String> headers = new HashMap<String, String>();
	
	public String parameterName = null;
	public String parameterValue = null;
	public String headerKey = null;
	public String headerValue = null;
	
	public void setTopMessage(String topMessage) {
		this.topMessage = topMessage;
	}

	public void setRemoveTopMessage(boolean removeTopMessage) {
		this.removeTopMessage = removeTopMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setUrl(String url) {
		this.url = url.trim();
	}
	
	
	private void addParameter(){
		parameters.add(parameterName + "=" + parameterValue);
		parameterName = null;
		parameterValue = null;
	}
	
	public void setParameterName(String name){
		parameterName = name;
		if( parameterValue != null){
			addParameter();
		}
	}
	
	public void setParameterValue(String value){
		parameterValue = value;
		if( parameterName != null){
			addParameter();
		}
	}
	
	private void addHeader(){
		headers.put(headerKey, headerValue);
		headerKey = null;
		headerValue = null;
	}
	
	public void setHeaderKey(String key){
		headerKey = key;
		if( headerValue != null){
			addHeader();
		}
	}
	
	public void setHeaderValue(String value){
		headerValue = value;
		if( headerKey != null){
			addHeader();
		}
	}

	@Override
	public void setMethod(String method) {
		this.method = method.trim();
	}

	@Override
	public void setDoSend(String m, Navajo od) throws UserException, ConditionErrorException,
			SystemException {
		// Prepare JSON content.
		JSONTML json = JSONTMLFactory.getInstance();
		Binary bContent = new Binary();
		try {
			json.format(od, bContent.getOutputStream(), removeTopMessage);
		} catch (Exception e) {
			throw new UserException(e.getMessage(), e);
		}
		HTTPMap http = new HTTPMap();
		
		setupHttpMap(http, bContent);
		http.setDoSend(true);
		Binary result = http.getResult();
		responseCode = http.getResponseCode();
		responseMessage = http.getResponseMessage();
		try {
			if (result != null) {
				inDoc = json.parse(result.getDataAsStream(), topMessage);
			} else {
				inDoc = NavajoFactory.getInstance().createNavajo();
			}
		} catch (Exception e) {
			throw new UserException(e.getMessage(), e);
		}
	}

	private void setupHttpMap(HTTPMap http, Binary content) throws UserException {
		try {
			http.load(access);
		} catch (MappableException e) {
			throw new UserException(e.getMessage(), e);
		}
		String fullUrl = url;
		for (int i = 0; i < parameters.size(); i++) {
			if (i == 0) {
				fullUrl += "?";
			} else {
				fullUrl += "&";
			}
			fullUrl += parameters.get(i);
		}

		for (String key : headers.keySet()) {
			http.setHeaderKey(key);
			http.setHeaderValue(headers.get(key));
		}

		http.setUrl(fullUrl);
		http.setHeaderKey("Accept");
		http.setHeaderValue("application/json");
		http.setMethod(method);
		
		if (method == "POST" || method == "PUT") {
			http.setContent(content);
			http.setContentType("application/json");
			http.setContentLength(content.getLength());
		}
		http.trustAll();
		if (username != null && password != null) {
			// Use HTTP Basic auth - should only be used over HTTPS!
			String authString = username + ":" + password;
			byte[] bytes = authString.getBytes(Charset.forName("UTF-8"));
			String encoded = Base64.encode(bytes, 0, bytes.length, 0, "");
			http.setHeaderKey("Authorization");
			http.setHeaderValue("Basic " + encoded);
			
		}
	}

	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

}
