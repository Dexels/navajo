package com.dexels.navajo.server.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.global.GlobalManager;

public class GlobalManagerImpl implements GlobalManager {

	private final Map<String,String> settings = new HashMap<String, String>();
	private final static List<String> osgiSettings = Arrays.asList(
			"component.id", "component.name", "service.factoryPid",
			"service.pid");
	
	private final static Logger logger = LoggerFactory.getLogger(GlobalManagerImpl.class);
	
	
	@Override
	public void initGlobals(String method, String username, Navajo inMessage,
			Map<String, String> extraParams) throws NavajoException {
	    
	    Message msg = inMessage.getMessage(GLOBALSMSGNAME);

        Message paramMsg = null;
        if (msg!=null) {
            paramMsg = msg;
        } else {
            paramMsg = NavajoFactory.getInstance().createMessage(inMessage, GLOBALSMSGNAME);
            inMessage.addMessage(paramMsg);
        }

        Property nu = NavajoFactory.getInstance().createProperty(inMessage, "NavajoUser", Property.STRING_PROPERTY, username, 50, "", Property.DIR_OUT);
        paramMsg.addProperty(nu);
        Property nm = NavajoFactory.getInstance().createProperty(inMessage, "NavajoMethod", Property.STRING_PROPERTY, method, 50, "", Property.DIR_OUT);
        paramMsg.addProperty(nm);
        
        appendMapToInput(inMessage, extraParams);
        
	}
	
	public static void appendMapToInput(Navajo inMessage, Map<String, String> extraParams) {
        
        Message msg = inMessage.getMessage(GLOBALSMSGNAME);

        Message paramMsg = null;
        if (msg!=null) {
            paramMsg = msg;
        } else {
            paramMsg = NavajoFactory.getInstance().createMessage(inMessage, GLOBALSMSGNAME);
            inMessage.addMessage(paramMsg);
        }
 
        if (extraParams!=null) {
            for (Iterator<Entry<String,String>> iter = extraParams.entrySet().iterator(); iter.hasNext();) {
                Entry<String,String> e = iter.next();
                String key = e.getKey();
                String value = e.getValue();
                Property p2 = NavajoFactory.getInstance().createProperty(inMessage, key, Property.STRING_PROPERTY,
                        value, 10, "",
                        Property.DIR_OUT);
                paramMsg.addProperty(p2);
            }

        }
    }

	public void activate(Map<String,Object> settings) {
		for (Entry<String,Object> e : settings.entrySet()) {
			if (!osgiSettings.contains(e.getKey())) {
				this.settings.put(e.getKey(), ""+e.getValue());
			}
		}
	}

	public void deactivate() {
		this.settings.clear();
	}

	@Override
	public void initGlobals(Navajo inMessage) throws NavajoException {
		Header h = inMessage.getHeader();
		if(h==null) {
			logger.warn("Can not append globals to input message: No header found.");
			return;
		}
		String rpcName = h.getRPCName();
		String username = h.getRPCUser();
		initGlobals(rpcName, username, inMessage, settings);
		
	}


}
