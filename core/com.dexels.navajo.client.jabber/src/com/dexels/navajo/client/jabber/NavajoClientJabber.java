package com.dexels.navajo.client.jabber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.client.push.NavajoPushSession;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class NavajoClientJabber {

	private static NavajoClientJabber instance = null;
	
	private NavajoClientJabber() {
		
	}
	
	public static NavajoClientJabber getInstance() {
		if(instance==null) {
			instance = new NavajoClientJabber();
		}
		return instance;
	}

	private final static Logger logger = LoggerFactory
			.getLogger(NavajoClientJabber.class);
	
	public boolean attemptPushRegistration(String agentId) {
		Navajo init = NavajoFactory.getInstance().createNavajo();
		try {
			Message m = NavajoFactory.getInstance().createMessage(init, "Agent");
			init.addMessage(m);
			Property p = NavajoFactory.getInstance().createProperty(init, "ApplicationId", Property.STRING_PROPERTY, agentId, 0, "aap", Property.DIR_IN);
			m.addProperty(p);
		} catch (NavajoException e1) {
			logger.error("Error: ", e1);
		}
		Navajo n = null;
		try {
			n = NavajoClientFactory.getClient().doSimpleSend(init,"navajo/InitClientSession");
		} catch (ClientException e) {
			return false;
		}
		if(n.getMessage("error")!=null) {
			return false;
		}
		if(n.getMessage("ConditionErrors")!=null) {
			return false;
		}
//		
//		NavajoPushSession nps 
		processPushNavajo(n,agentId);
		return true;
	}

	@SuppressWarnings("unchecked")
	private void processPushNavajo(Navajo n,String agentId) {
		try {
			n.write(System.err);
		} catch (NavajoException e1) {
			logger.error("Error: ", e1);
		}
		String pushImpl = (String) n.getProperty("SessionParameters/PushbackHandler").getTypedValue();
		try {
			Class<? extends NavajoPushSession> c = (Class<? extends NavajoPushSession>) Class.forName(pushImpl);
			NavajoPushSession nps =  c.newInstance();
			nps.load(n,agentId);
		} catch (Exception e) {
			logger.error("Error: ", e);
			logger.info("Error loading push implementation. Disabling push");
		}
		
	}
}
