package com.dexels.navajo.server;


import java.util.*;
import com.dexels.navajo.document.*;

import com.dexels.navajo.util.*;

public class MaintainanceRequest extends Request {

    public static final String NAVAJO_VERSION = "Navajo ($Id$)";

    // Navajo method names
    public static final String METHOD_NAVAJO = "navajo";
    public static final String METHOD_NAVAJO_LOGON = METHOD_NAVAJO + "_logon";
    public static final String METHOD_NAVAJO_LOGON_SEND = METHOD_NAVAJO
            + "_logon_send";
    public static final String METHOD_NAVAJO_PING = METHOD_NAVAJO + "_ping";
    public static final String METHOD_NAVAJO_TEST = "navajo_test";
    public static final String METHOD_NAVAJO_HELLO = "navajo_hello";

    private Repository repository = null;

    @SuppressWarnings("unchecked")
	public MaintainanceRequest(HashMap rb, Repository repository) {
        super(rb);
        this.repository = repository;
    }

    public void addServicesToMessage(Access access, Parameters parms, Navajo inMessage, boolean multiple) 
         throws SystemException, UserException, java.io.IOException, NavajoException {

        Message services = inMessage.getMessage("services");

        String all[] = repository.getServices(access);

        if (all != null) {
            String card = (multiple) ? "+" : "1";

            Property serviceprop = NavajoFactory.getInstance().createProperty(inMessage, "all_services", card, "Alle diensten", Property.DIR_IN);

            for (int i = 0; i < all.length; i++) {
                Selection sel = NavajoFactory.getInstance().createSelection(inMessage, all[i], i + "", false);
                serviceprop.addSelection(sel);
            }

            services.addProperty(serviceprop);
        } else {
            // Add free text field for servicename if database not available.
            Property serviceprop = NavajoFactory.getInstance().createProperty(inMessage, "service", Property.STRING_PROPERTY, "", 25, "Requested Navajo service", Property.DIR_IN);
            services.addProperty(serviceprop);
        }
    }

    public Navajo logonSend(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, NavajoException {
    	
        Navajo outMessage = null;

        Message identification = inMessage.getMessage("identification");
        String username = identification.getProperty("username").getValue();
        String password = identification.getProperty("password").getValue();

        Message services = inMessage.getMessage("services");
        Property serviceProp = services.getProperty("all_services");
        String service = "";

        if (serviceProp == null) {
            service = services.getProperty("service").getValue();
        } else {
            ArrayList<Selection> selectedServices = serviceProp.getAllSelectedSelections();

            service = selectedServices.get(0).getName();
        }

        Access newAccess = null;

        try {
          newAccess = repository.authorizeUser(username, password, service, inMessage, null);
        }
        catch (Exception ex) {
          ex.printStackTrace(System.err);
        }

        if (newAccess != null && (newAccess.userID != -1) && (newAccess.serviceID != -1))
            outMessage = getThanksMessage("geauthoriseerd");
        else
            throw new UserException(SystemException.NOT_AUTHORISED, "");

        return outMessage;
    }

}
