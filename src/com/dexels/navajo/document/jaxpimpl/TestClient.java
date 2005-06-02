

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document.jaxpimpl;

import java.io.*;
import org.xml.sax.InputSource;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class TestClient {

    public TestClient() {}

    public static void main(String[] args) {
        TestClient testClient1 = new TestClient();

        InputSource input;

        try {

          FileInputStream fis = new FileInputStream(new File("c:/vladb/clubforms/FormNumber2.xml"));

            Navajo tb = NavajoFactory.getInstance().createNavajo(fis);
            // tb.getMessageBuffer().setDoctype("tml", "tml.dtd", "");

//            Message message = NavajoFactory.getInstance().createMessage(tb, "harm");
//
//            tb.addMessage(message);

            // tb.getMessageBuffer().write(System.out);
            tb.write(System.out);

            // tb.getMessageBuffer().setDoctype("tml", "/home/arjen/tml.dtd", "");
            // tb.getMessageBuffer().write(System.out);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
