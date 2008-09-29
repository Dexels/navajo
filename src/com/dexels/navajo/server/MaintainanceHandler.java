package com.dexels.navajo.server;

import com.dexels.navajo.document.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 *
 * This class handles the login requests from the default HTML client.
 *
 */

public class MaintainanceHandler extends ServiceHandler {

    public Navajo doService()
            throws NavajoException, SystemException, UserException {

        try {
            Navajo outMessage = null;

            
            MaintainanceRequest maintain = new MaintainanceRequest(properties.getProperties(),
            		DispatcherFactory.getInstance().getNavajoConfig().getRepository());

           
            if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_HELLO)) {
          	  outMessage = NavajoFactory.getInstance().createNavajo();
                Message msg = NavajoFactory.getInstance().createMessage(outMessage, "hello");
                outMessage.addMessage(msg);
               String value = access.getInDoc().getProperty("from/name").getValue();
               Property p = NavajoFactory.getInstance().createProperty(outMessage, "world", Property.STRING_PROPERTY, value, -1, "", Property.DIR_OUT);
               msg.addProperty(p);
            } else 
            if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_TEST)) {
            	  outMessage = NavajoFactory.getInstance().createNavajo();
                  Message msg = NavajoFactory.getInstance().createMessage(outMessage, "test");
                  outMessage.addMessage(msg);
            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_PING)) {
                // return ping message.
                outMessage = NavajoFactory.getInstance().createNavajo();
                Message msg = NavajoFactory.getInstance().createMessage(outMessage, "ping");
                outMessage.addMessage(msg);
                Property prop = NavajoFactory.getInstance().createProperty(outMessage, "version", Property.STRING_PROPERTY, MaintainanceRequest.NAVAJO_VERSION,
                        0, "Navajo versie", Property.DIR_OUT);
                prop = NavajoFactory.getInstance().createProperty(outMessage, "author", Property.STRING_PROPERTY, MaintainanceRequest.NAVAJO_VERSION,
                        0, "Dexels BV (www.dexels.com)", Property.DIR_OUT);
                msg.addProperty(prop);
            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON)) {

                outMessage = maintain.getInitialNavajoMesssage(access.rpcName);
                maintain.addServicesToMessage(access, parms, outMessage, false);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON_SEND)) {
                outMessage = maintain.logonSend(access, parms, requestDocument);
            } else {
                throw new SystemException(SystemException.UNKNOWN_RPC_NAME, "", new Exception());
            }
            
            return outMessage;
        } catch (java.io.IOException ioe) {
            throw new SystemException(-1, ioe.getMessage(), ioe);
        } 
    }
}
