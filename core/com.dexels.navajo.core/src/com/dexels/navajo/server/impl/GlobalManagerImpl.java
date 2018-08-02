package com.dexels.navajo.server.impl;

import java.util.Arrays;
import java.util.HashMap;
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

    private final Map<String, String> settings = new HashMap<String, String>();
    private final static List<String> osgiSettings = Arrays.asList("component.id", "component.name", "service.factoryPid", "service.pid");

    private final static Logger logger = LoggerFactory.getLogger(GlobalManagerImpl.class);

    public void activate(Map<String, Object> settings) {
        for (Entry<String, Object> e : settings.entrySet()) {
            if (!osgiSettings.contains(e.getKey())) {
                this.settings.put(e.getKey(), "" + e.getValue());
            }
        }
    }

    public void deactivate() {
        this.settings.clear();
    }

    @Override
    public void initGlobals(Navajo inMessage) throws NavajoException {
        Header h = inMessage.getHeader();
        if (h == null) {
            logger.warn("Can not append globals to input message: No header found.");
            return;
        }
        String rpcName = h.getRPCName();
        String username = h.getRPCUser();
        initGlobals(rpcName, username, inMessage, settings);

    }

    @Override
    public void initGlobals(String method, String username, Navajo inMessage, Map<String, String> extraParams) throws NavajoException {

        Message msg = inMessage.getMessage(GLOBALSMSGNAME);

        Message globalsMsg = null;
        if (msg != null) {
            globalsMsg = msg;
        } else {
            globalsMsg = NavajoFactory.getInstance().createMessage(inMessage, GLOBALSMSGNAME);
            inMessage.addMessage(globalsMsg);
        }

        Property nu = NavajoFactory.getInstance().createProperty(inMessage, "NavajoUser", Property.STRING_PROPERTY, username, 50, "", Property.DIR_OUT);
        globalsMsg.addProperty(nu);
        Property nm = NavajoFactory.getInstance().createProperty(inMessage, "NavajoMethod", Property.STRING_PROPERTY, method, 50, "", Property.DIR_OUT);
        globalsMsg.addProperty(nm);
        if (globalsMsg.getProperty("UpdateBy") == null) {
            // ensure it exists
            Property ui = NavajoFactory.getInstance().createProperty(inMessage, "UpdateBy", Property.STRING_PROPERTY, username, 50, "", Property.DIR_OUT);
            globalsMsg.addProperty(ui);
        }
        appendMapToGlobals(inMessage, extraParams);
    }

    public static void appendMapToGlobals(Navajo inMessage, Map<String, String> extraParams) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.putAll(extraParams);
        appendMapToInput(inMessage, newMap, GLOBALSMSGNAME);
    }
    
    public static void appendMapToParms(Navajo inMessage, Map<String, Object> extraParams) {
        appendMapToInput(inMessage, extraParams, Message.MSG_PARAMETERS_BLOCK);
    }
    
    public static void appendMapToAAA(Navajo inMessage, Map<String, Object> extraParams) {
        appendMapToInput(inMessage, extraParams, Message.MSG_AAA_BLOCK);
    }

    private static void appendMapToInput(Navajo inMessage, Map<String, Object> extraParams, String msgName) {
        Message msg = inMessage.getMessage(msgName);

        Message paramMsg = null;
        if (msg != null) {
            paramMsg = msg;
        } else {
            paramMsg = NavajoFactory.getInstance().createMessage(inMessage, msgName);
            inMessage.addMessage(paramMsg);
        }

        if (extraParams != null) {
            for (String key : extraParams.keySet()) {
                Property p2 = NavajoFactory.getInstance().createProperty(inMessage, key, Property.STRING_PROPERTY, "", 10, "", Property.DIR_OUT);
                p2.setAnyValue(extraParams.get(key));
                if (extraParams.get(key) == null) {
                    p2.setType(Property.STRING_PROPERTY);
                }
                paramMsg.addProperty(p2);
            }
        }
    }

}
