/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class Request {

    public HashMap<String,String> properties = null;

    public Request(HashMap<String,String> rb) {
        properties = rb;
    }

    /**
     * This method returns the Navajo message that corresponds to an initial
     * service request. Initial services are always located somewhere on the filesystem.
     */
    public Navajo getInitialNavajoMesssage(String service)
            throws IOException, NavajoException {

        Navajo outMessage = null;
    
        // Read the filename from koopsom properties
        String fileName = properties.get(service).toString();

        outMessage = NavajoFactory.getInstance().createNavajo(new FileInputStream(fileName));

        return outMessage;
    }

    /**
     * Generate a simple "feedback" message with only 1 property: "resultaat".
     */
    public Navajo getThanksMessage(String what) throws NavajoException {
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
