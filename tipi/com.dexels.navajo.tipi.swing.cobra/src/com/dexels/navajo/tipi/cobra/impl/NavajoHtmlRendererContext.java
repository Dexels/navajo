/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.cobra.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.html2.HTMLElement;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.cobra.TipiCobraBrowser;


public class NavajoHtmlRendererContext extends SimpleHtmlRendererContext {
	
	private final TipiCobraBrowser owner;
	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoHtmlRendererContext.class);
	public NavajoHtmlRendererContext(HtmlPanel contextComponent, UserAgentContext ucontext, TipiCobraBrowser owner) {
		super(contextComponent, ucontext);
		this.owner = owner;
	}



	


	@Override
	public String prompt(String message, String inputDefault) {
		return super.prompt(message, inputDefault);
	}

	@Override
	public void linkClicked(HTMLElement linkNode, URL url, String target) {
		if(owner.allowLinking()) {
			super.linkClicked(linkNode, url, target);
			return;
		}
		logger.info("Linking to: "+url+" target: "+target);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("url", url.toString());
		try {
			owner.performTipiEvent("onLink", params, false);
		} catch (TipiBreakException e) {
			logger.debug("Error: ",e);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	public void setStatus(String message) {
		logger.info("New status: "+message);
		super.setStatus(message);
	}
	@Override
	public String getStatus() {
		return (String) owner.getValue("status");
	}

}
