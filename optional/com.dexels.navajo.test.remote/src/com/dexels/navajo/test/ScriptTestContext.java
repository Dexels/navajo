package com.dexels.navajo.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class ScriptTestContext  {

	private static ScriptTestContext instance;

	private ScriptInputLocator myInputLocator = null;
	private final Map<String,Navajo> navajoMap = new HashMap<String,Navajo>();

	public void setInputLocator(ScriptInputLocator myInputLocator) {
		this.myInputLocator = myInputLocator;
	}

	
	private ScriptTestContext() {
		super();
		setInputLocator(new ClasspathInputLocator());

	}

	public Navajo getInput(String scriptName) throws IOException {
		if (myInputLocator == null) {
			return NavajoFactory.getInstance().createNavajo();
		}
		return myInputLocator.getInput(scriptName);
	}

	public List<String> getQualifiedScriptNames() {
		throw new AssertionError("Can not enumerate scripts: No enumerator found.");
	}


	public Navajo callService(String script) throws ClientException {
		return callService(script, null, null);
	}
	
	public Navajo callService(String script, Navajo input) throws ClientException {
		return callService(script, input, null);
	}
	
	public Navajo callService(String script, Navajo input, String altProfile) throws ClientException {
		if(input==null) {
			input = NavajoFactory.getInstance().createNavajo();
		}
		
		ClientInterface c = NavajoClientFactory.getClient();
		setupClient(c, altProfile);			  
	//	Navajo result = c.doSimpleSend(input,"", script,"","",-1,false,false);
		Navajo result = c.doSimpleSend(input,script);
		navajoMap.put(script, result);
		return result;
	}

	private void setupClient(ClientInterface c, String altProfile) {
		ResourceBundle bb = ResourceBundle.getBundle("testsuite");
		c.setServerUrl(bb.getString("server"));
		try {
			if ( altProfile != null ) {
				c.setUsername(bb.getString("username_" + altProfile));
			} else {
				c.setUsername(bb.getString("username"));
			}
		} catch (MissingResourceException e) {
		}
		try {
			if ( altProfile != null ) {
				c.setPassword(bb.getString("password_" + altProfile));
			} else {
				c.setPassword(bb.getString("password"));
			}
		} catch (MissingResourceException e) {
		}

	}

	public static ScriptTestContext getInstance() {
		if (instance == null) {
			instance = new ScriptTestContext();
		}
		return instance;
	}

	public Navajo getScriptResult(String scriptName) {
		return navajoMap.get(scriptName);
	}
	
	
}
