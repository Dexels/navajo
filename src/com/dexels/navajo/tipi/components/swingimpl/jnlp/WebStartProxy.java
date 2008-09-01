package com.dexels.navajo.tipi.components.swingimpl.jnlp;

import java.net.*;
import java.util.*;

import javax.jnlp.*;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class WebStartProxy {

	public static void appendJnlpCodeBase(SwingTipiContext myContext) {
	      try {
			BasicService bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
			System.err.println("BS: "+bs.getCodeBase());
			URL tipiCodeBase = new URL(bs.getCodeBase(),"tipi");
			System.err.println("TipiBS: "+bs.getCodeBase());
			URL resourceCodeBase = new URL(bs.getCodeBase(),"resource");
			
			myContext.setTipiResourceLoader(tipiCodeBase.toString());
			myContext.setGenericResourceLoader(resourceCodeBase.toString());
			System.err.println("WEBSTARTPROXY found: "+tipiCodeBase);
	      } catch (UnavailableServiceException e) {
			System.err.println("Service unavailable");
//			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.err.println("URL trouble");
			e.printStackTrace();
		} 
	      
	}

}
