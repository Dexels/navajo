

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document.jaxpimpl;

import java.util.ArrayList;
import java.io.*;
import java.util.*;

import javax.xml.transform.stream.*;
import com.dexels.navajo.document.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;


public class TestClient {

    public TestClient() {}

    public static void main(String[] args) {
        TestClient testClient1 = new TestClient();

        InputSource input;

        try {

            Navajo tb = NavajoFactory.getInstance().createNavajo();
            // tb.getMessageBuffer().setDoctype("tml", "tml.dtd", "");

            Message message = NavajoFactory.getInstance().createMessage(tb, "harm");

            tb.addMessage(message);

            // tb.getMessageBuffer().write(System.out);
            tb.write(System.out);

            // tb.getMessageBuffer().setDoctype("tml", "/home/arjen/tml.dtd", "");
            // tb.getMessageBuffer().write(System.out);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
