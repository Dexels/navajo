package com.dexels.navajo.server;


import com.dexels.navajo.document.*;
import java.util.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.xml.*;
import com.dexels.navajo.loader.NavajoClassLoader;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class MaintainanceHandler extends ServiceHandler {

    public MaintainanceHandler() {}

    public Navajo doService()
            throws NavajoException, SystemException, UserException {

        try {
            Navajo outMessage = null;

            Util.debugLog("In MaintainanceHandler doService()");
            MaintainanceRequest maintain = new MaintainanceRequest(properties.getProperties(), properties.getRepository());

            Util.debugLog("After constructor");
            if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_PING)) {
                // return ping message.
                outMessage = new Navajo();
                Message msg = Message.create(outMessage, "ping");

                outMessage.addMessage(msg);
                Property prop = Property.create(outMessage, "version", Property.STRING_PROPERTY, MaintainanceRequest.NAVAJO_VERSION,
                        0, "Navajo versie", Property.DIR_OUT);

                prop = Property.create(outMessage, "author", Property.STRING_PROPERTY, MaintainanceRequest.NAVAJO_VERSION,
                        0, "Dexels BV (www.dexels.com)", Property.DIR_OUT);
                msg.addProperty(prop);
            } else
            if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_SHOWUSERS)) {

                Util.debugLog(2, "MaintainanceServlet servlet: In navajo_showusers");
                Util.debugLog(2, "inMessage: " + this.requestDocument);

                outMessage = maintain.getInitialNavajoMesssage(access.rpcName);
                maintain.addUsersToMessage(access, parms, outMessage, false);
                maintain.addServicesToMessage(access, parms, outMessage, false);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_ADD_USER)) {

                outMessage = maintain.addUser(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_ADD_SERVICE)) {

                outMessage = maintain.addService(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_AUTHORISE)) {

                outMessage = maintain.addAuthorisation(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_SHOW_AUTHORISED)) {

                outMessage = maintain.showAuthorised(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_SHOWPARAMETERS)) {

                outMessage = maintain.getInitialNavajoMesssage(access.rpcName);
                maintain.addUsersToMessage(access, parms, outMessage, false);
                maintain.addDefinitionsToMessage(access, parms, outMessage);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_ADD_DEFINITION)) {

                outMessage = maintain.addDefinition(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_ADD_VALUE)) {

                outMessage = maintain.addValue(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_SHOW_VALUES)) {

                outMessage = maintain.showValues(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_DELETE_USER)) {

                outMessage = maintain.deleteUser(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_DELETE_SERVICE)) {

                outMessage = maintain.deleteService(access, parms, requestDocument);

                /*
                 } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_DELETE_AUTHORISATION)) {

                 outMessage = maintain.deleteAuthorisation(access, parms, inMessage);
                 */
            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_DELETE_DEFINITION)) {

                outMessage = maintain.deleteDefinition(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_EDIT_VALUE)) {

                outMessage = maintain.editValue(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_FIND_VALUE)) {

                outMessage = maintain.getInitialNavajoMesssage(access.rpcName);
                maintain.showValue(access, requestDocument, outMessage);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_DELETE_VALUE)) {

                outMessage = maintain.deleteValue(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_SHOWCONDITIONS)) {

                outMessage = maintain.getInitialNavajoMesssage(access.rpcName);
                maintain.addUsersToMessage(access, parms, outMessage, true);
                maintain.addServicesToMessage(access, parms, outMessage, true);
            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_ADD_CONDITION)) {

                outMessage = maintain.addCondition(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_SHOW_CONDITIONS)) {

                outMessage = maintain.showConditions(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_FIND_CONDITION)) {

                outMessage = maintain.getInitialNavajoMesssage(access.rpcName);
                maintain.showCondition(access, requestDocument, outMessage);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_EDIT_CONDITION)) {

                outMessage = maintain.editCondition(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_DELETE_CONDITION)) {

                outMessage = maintain.deleteCondition(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_SHOWLOG)) {

                outMessage = maintain.getInitialNavajoMesssage(access.rpcName);
                maintain.addUsersToMessage(access, parms, outMessage, true);
                maintain.addServicesToMessage(access, parms, outMessage, true);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_SHOW_VIEW)) {

                outMessage = maintain.showLogView(access, parms, requestDocument);

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON)) {

                Util.debugLog("In Navajo/logon:" + access.rpcName);
                outMessage = maintain.getInitialNavajoMesssage(access.rpcName);
                Util.debugLog("Got initial Navajo message");
                maintain.addServicesToMessage(access, parms, outMessage, false);
                Util.debugLog("Added services to message");

            } else if (access.rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON_SEND)) {

                outMessage = maintain.logonSend(access, parms, requestDocument);

            } else {
                throw new SystemException(SystemException.UNKNOWN_RPC_NAME, "");
            }

            Util.debugLog("Leaving MaintainanceServlet (doAction())");
            return outMessage;
        } catch (org.xml.sax.SAXException saxe) {
            throw new SystemException(-1, saxe.getMessage());
        } catch (java.io.IOException ioe) {
            throw new SystemException(-1, ioe.getMessage());
        } catch (java.sql.SQLException sqle) {
            throw new SystemException(-1, sqle.getMessage());
        }
    }
}
