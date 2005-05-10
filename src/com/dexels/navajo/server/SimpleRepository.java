package com.dexels.navajo.server;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import java.util.*;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.NavajoUtils;

public class SimpleRepository implements Repository {

    public NavajoConfig config;
    // name of the message containing globals
    public static final String GLOBALSMSGNAME = "__globals__";

    public SimpleRepository() {}

    public void setNavajoConfig(NavajoConfig config) {
        this.config = config;
    }

    public Access authorizeUser(String username, String password, String service, Navajo inMessage, Object certificate) throws SystemException, AuthorizationException {
        return new Access(1, 1, 1, username, service, "", "", "", certificate);
    }
    /**
     * @param username
     * @param inMessage
     * @param region
     * @param userRoleString
     * @throws NavajoException
     */
    public void initGlobals(String method, String username, Navajo inMessage, Map extraParams) throws NavajoException {
    	// Create RegionOwner "parameter" in inMessage document.
    	Message paramMsg = NavajoFactory.getInstance().createMessage(inMessage, SimpleRepository.GLOBALSMSGNAME);
    	inMessage.addMessage(paramMsg);
//    	Property p = NavajoFactory.getInstance().createProperty(inMessage, "RegionOwner", Property.STRING_PROPERTY, region.toString(), 10, "", Property.DIR_OUT);
//    	paramMsg.addProperty(p);
//    	// Create UserRoles "parameter" in inMessage document.
//    	Property urp = NavajoFactory.getInstance().createProperty(inMessage, "UserRoles", Property.STRING_PROPERTY, userRoleString.toString(), 10, "", Property.DIR_OUT);
//    	paramMsg.addProperty(urp);
 
    	// Create NavajoUser "parameter" in inMessage document.
    	
     	
    	Property nu = NavajoFactory.getInstance().createProperty(inMessage, "NavajoUser", Property.STRING_PROPERTY, username, 50, "", Property.DIR_OUT);
    	paramMsg.addProperty(nu);
       	Property nm = NavajoFactory.getInstance().createProperty(inMessage, "NavajoMethod", Property.STRING_PROPERTY, method, 50, "", Property.DIR_OUT);
    	paramMsg.addProperty(nm);

    	try {
            ResourceBundle rb = ResourceBundle.getBundle("application");
            System.out.println("Checking bundle... "+rb==null);
            // Add application instance, i.e. "Bond" specific parameters from application.properties file.
            Enumeration all = rb.getKeys();
            while (all.hasMoreElements()) {
              String key = (String) all.nextElement();
              Property p2 = NavajoFactory.getInstance().createProperty(inMessage, key, Property.STRING_PROPERTY,
                                                                   rb.getString(key), 10, "",
                                                                   Property.DIR_OUT);
              paramMsg.addProperty(p2);
            }
        } catch (NavajoException e) {
            System.err.println("Can not open resource bundle. No big deal, I guess");
        }
        
       	if (extraParams!=null) {
    	    for (Iterator iter = extraParams.keySet().iterator(); iter.hasNext();) {
                String key = (String) iter.next();
                String value = (String)extraParams.get(key);
                Property p2 = NavajoFactory.getInstance().createProperty(inMessage, key, Property.STRING_PROPERTY,
                        value, 10, "",
                        Property.DIR_OUT);
                paramMsg.addProperty(p2);
            }
            
        }
 }
    
    public ConditionData[] getConditions(Access access) throws SystemException, UserException {
              try {
            //Navajo conditions = NavajoFactory.getInstance().createNavajo(new FileInputStream(config.getRootPath() + "conditions/" + access.rpcName + ".val"));
             Navajo conditions = Dispatcher.getNavajoConfig().getConditions(access.rpcName);
             if (conditions == null) {
                 //System.out.println("No matching conditions found");
                 return null;
             }

             ArrayList list = conditions.getMessage("conditions").getAllMessages();
             ArrayList conditionData = new ArrayList();

             for (int i = 0; i < list.size(); i++) {
                 String expression = NavajoUtils.getPropertyValue((Message) list.get(i), "expression", true);
                 String message = NavajoUtils.getPropertyValue((Message) list.get(i), "message", true);

                 //System.out.println("condition " + i + ": , condition = " + expression + ", message = " + message);<
                 ConditionData cd = new ConditionData();

                 cd.condition = expression;
                 cd.comment = message;
                 cd.userId = access.userID;
                 cd.serviceId = access.serviceID;
                 cd.id = i;
                 conditionData.add(cd);
             }
             if (conditionData.size() == 0)
                 return null;
             ConditionData[] result = new ConditionData[conditionData.size()];

             return (ConditionData[]) conditionData.toArray(result);
         } catch (NavajoException e) {
             e.printStackTrace();
             return null;
         } catch (IOException fnfe) {
             return null;
         }


    }

    public Parameter[] getParameters(Access access) throws SystemException {
        return null;
    }

    public void logTiming(Access access, int part, long timespent) throws SystemException {}

    public void logAction(Access access, int level, String comment) throws SystemException {}

    public String getServlet(Access access) throws SystemException {
        if (access.rpcName.startsWith("navajo"))
            return "com.dexels.navajo.server.MaintainanceHandler";
        else
            return "com.dexels.navajo.server.GenericHandler";
    }

    public String[] getServices(Access access) throws SystemException {
        return null;
    }
}
