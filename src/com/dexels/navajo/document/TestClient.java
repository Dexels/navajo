

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document;


import java.util.ArrayList;
import java.io.*;
import java.util.*;

import javax.xml.transform.stream.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;
import com.dexels.navajo.xml.XMLDocumentUtils;


public class TestClient {

    public TestClient() {}

    public static void main(String[] args) {
        TestClient testClient1 = new TestClient();

        InputSource input;

        try {

            Navajo tb = new Navajo();
            // tb.getMessageBuffer().setDoctype("tml", "tml.dtd", "");

            Message message = Message.create(tb, "harm");

            tb.addMessage(message);

            // tb.getMessageBuffer().write(System.out);
            XMLDocumentUtils.toXML(tb.getMessageBuffer(), null, null, new StreamResult(System.out));

            // tb.getMessageBuffer().setDoctype("tml", "/home/arjen/tml.dtd", "");
            // tb.getMessageBuffer().write(System.out);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
