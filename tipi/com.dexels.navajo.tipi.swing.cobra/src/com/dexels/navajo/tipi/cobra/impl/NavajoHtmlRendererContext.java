package com.dexels.navajo.tipi.cobra.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.w3c.dom.html2.HTMLElement;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.cobra.TipiCobraBrowser;


public class NavajoHtmlRendererContext extends SimpleHtmlRendererContext {
	
	private final TipiCobraBrowser owner;
	
	public NavajoHtmlRendererContext(HtmlPanel contextComponent, UserAgentContext ucontext, TipiCobraBrowser owner) {
		super(contextComponent, ucontext);
		this.owner = owner;
	}

	@Override
	public void alert(String message) {
		// TODO Auto-generated method stub
		super.alert(message);
	}


	

	@Override
	public void error(String message, Throwable throwable) {
		// TODO Auto-generated method stub
		super.error(message, throwable);
	}

	@Override
	public String prompt(String message, String inputDefault) {
		// TODO Auto-generated method stub
		return super.prompt(message, inputDefault);
	}

	@Override
	public void linkClicked(HTMLElement linkNode, URL url, String target) {
		if(owner.allowLinking()) {
			super.linkClicked(linkNode, url, target);
			return;
		}
		System.err.println("Linking to: "+url+" target: "+target);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("url", url.toString());
		try {
			owner.performTipiEvent("onLink", params, false);
		} catch (TipiBreakException e) {
			e.printStackTrace();
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setStatus(String message) {
		System.err.println("New status: "+message);
		super.setStatus(message);
	}
	@Override
	public String getStatus() {
		return (String) owner.getValue("status");
	}
	@Override
	public void warn(String message, Throwable throwable) {
		// TODO Auto-generated method stub
		super.warn(message, throwable);
	}

}
