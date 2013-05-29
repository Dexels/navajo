package com.dexels.navajo.article.command.impl;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class ServiceCommand implements ArticleCommand {

	private String name;
	private LocalClient localClient;

	public ServiceCommand() {
		// default constructor
	}
	
	// for testing, no need to call activate this way
	public ServiceCommand(String name) {
		this.name = name;
	}
	

	public void activate(Map<String,String> settings) {
		this.name = settings.get("command.name");
	}
	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws ArticleException {
		String name = parameters.get("name");
		if(name==null) {
			throw new ArticleException("Command: "+this.getName()+" can't be executed without required parameters: "+name);
		}
		String refresh = parameters.get("refresh");
		if( "false".equals(refresh)) {
			Navajo res = runtime.getNavajo(name);
			if(res!=null) {
				runtime.pushNavajo(name,res);
				return null;
			}
		}
		String input = parameters.get("input");
		Navajo n = null;
		if(input!=null) {
			n = runtime.getNavajo(input);
			if(n==null) {
				throw new ArticleException("Command: "+this.getName()+" supplies an 'input' parameter: "+input+", but that navajo object can not be found"+name);
			}
		}
		if(runtime.getNavajo()!=null) {
			n = runtime.getNavajo();
		} else {
			n = NavajoFactory.getInstance().createNavajo();
		}
		Header h = NavajoFactory.getInstance().createHeader(n, name, runtime.getUsername(), runtime.getPassword(), -1);
		n.addHeader(h);
		final Navajo result = performCall(runtime, name, n);
		runtime.pushNavajo(name, result);
		return null;
	}

	protected Navajo performCall(ArticleRuntime runtime, String name, Navajo n)
			throws ArticleException {
		try {
			Navajo result = localClient.call(n);
			return result;
		} catch (FatalException e) {
			throw new ArticleException("Error calling service: "+name, e);
		}
	}

	
	public void clearLocalClient(LocalClient localClient) {
		this.localClient = null;
	}
	public LocalClient getLocalClient() {
		return localClient;
	}

	public void setLocalClient(LocalClient localClient) {
		this.localClient = localClient;
	}

	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper) {
		return false;
	}
}
