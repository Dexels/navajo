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

import junit.framework.TestSuite;

public class ScriptTestContext  {

	private static ScriptTestContext instance;

	private ScriptEnumerator myEnumerator = null;
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
		if (myEnumerator == null) {
			throw new AssertionError("Can not enumerate scripts: No enumerator found.");
		}
		return myEnumerator.getQualifiedScriptNames();

	}

	public Navajo callService(String script, Navajo input) throws ClientException {
		ClientInterface c = NavajoClientFactory.getClient();
		setupClient(c);
		Navajo result = c.doSimpleSend(input, script);
		navajoMap.put(script, result);
		return result;
	}

	private void setupClient(ClientInterface c) {
		ResourceBundle bb = ResourceBundle.getBundle("testsuite");
		c.setServerUrl(bb.getString("server"));
			try {
				c.setUsername(bb.getString("username"));
			} catch (MissingResourceException e) {
			}
			try {
				c.setPassword(bb.getString("password"));
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
