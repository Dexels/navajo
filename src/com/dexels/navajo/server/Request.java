

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;


import java.io.*;
import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.util.*;


public class Request {

    public HashMap properties = null;

    public Request(HashMap rb) {
        Util.debugLog("In Request(ResourceBundle) constructor:" + rb.toString());
        properties = rb;
    }

    /**
     * This method returns the Navajo message that corresponds to an initial
     * service request. Initial services are always located somewhere on the filesystem.
     */
    public Navajo getInitialNavajoMesssage(String service)
            throws IOException, NavajoException {

        Navajo outMessage = null;
        String fNaam;

        // Read the filename from koopsom properties
        String fileName = properties.get(service).toString();

        outMessage = NavajoFactory.getInstance().createNavajo(new FileInputStream(new File(fileName)));

        return outMessage;
    }

    /**
     * Generate a simple "feedback" message with only 1 property: "resultaat".
     */
    public Navajo getThanksMessage(String what)
            throws IOException, NavajoException {
        // Create Navajo out message
        Navajo outMessage = NavajoFactory.getInstance().createNavajo();

        Message resultMessage = NavajoFactory.getInstance().createMessage(outMessage, "feedback");

        outMessage.addMessage(resultMessage);

        Property prop = NavajoFactory.getInstance().createProperty(outMessage, "resultaat", Property.STRING_PROPERTY,
                what, 1, "", Property.DIR_OUT);

        resultMessage.addProperty(prop);

        return outMessage;
    }
}
