
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
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.XMLDocumentUtils;
import com.dexels.navajo.util.*;

public class Request {

  public ResourceBundle properties = null;

  public Request(ResourceBundle rb) {
    Util.debugLog("In Request(ResourceBundle) constructor:" + rb.toString());
    properties = rb;
  }

  /**
   * This method returns the Navajo message that corresponds to an initial
   * service request. Initial services are always located somewhere on the filesystem.
   */
  public Navajo getInitialNavajoMesssage(String service)
                throws IOException, SAXException, NavajoException {

    FileInputStream input;
    Document d;
    Navajo outMessage = null;
    String fNaam;

    // Read the filename from koopsom properties
    String fileName = properties.getString(service);
    input = new FileInputStream(new File(fileName));

    d = XMLDocumentUtils.createDocument( input, false );
    d.getDocumentElement().normalize();

    outMessage = new Navajo(d);

    return outMessage;
  }

  /**
   * Generate a simple "feedback" message with only 1 property: "resultaat".
   */
  public Navajo getThanksMessage(String what)
            throws IOException, SAXException, NavajoException
  {
      // Create Navajo out message
      Navajo outMessage = new Navajo();

      Message resultMessage = Message.create(outMessage, "feedback");
      outMessage.addMessage(resultMessage);

      Property prop = Property.create(outMessage, "resultaat", Property.STRING_PROPERTY,
                                      what, 1, "", Property.DIR_OUT);
      resultMessage.addProperty(prop);

      return outMessage;
  }
}