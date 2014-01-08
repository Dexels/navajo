package com.dexels.navajo.adapter;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.ConditionErrorException;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

public class RESTAdapter extends NavajoMap {

	public String url;
	public String method;
	public int responseCode;
	public String responseMessage;
	public String topMessage = "Response";
	public boolean removeTopMessage = false;

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

	public void setMethod(String method) {
		this.method = method.trim();
	}

	@Override
	public void setDoSend(String m, Navajo od) throws UserException, ConditionErrorException, SystemException {
		// Prepare JSON content.
		JSONTML json = JSONTMLFactory.getInstance();
		Binary bContent = new Binary();
		try {
			json.format(od, bContent.getOutputStream(), removeTopMessage);
		} catch (Exception e) {
			throw new UserException(e.getMessage(), e);
		}
		HTTPMap http = new HTTPMap();
		try {
			http.load(access);
		} catch (MappableException e) {
			throw new UserException(e.getMessage(), e);
		}
		http.setUrl(url);
		http.setMethod(method);
		http.setContent(bContent);
		http.setContentType("application/json");
		http.setContentLength(bContent.getLength());
		http.trustAll();
		http.setDoSend(true);
		
		Binary result = http.getResult();
		responseCode = http.getResponseCode();
		responseMessage = http.getResponseMessage();
		try {
			if ( http.getResponseCode() < 400 && result != null ) {
				inDoc = json.parse(result.getDataAsStream(), topMessage);
			} else {
				inDoc = NavajoFactory.getInstance().createNavajo();
			}
		} catch (Exception e) {
			throw new UserException(e.getMessage(), e);
		}
	}

}
