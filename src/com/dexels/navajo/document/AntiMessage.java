
/**
 * Title:        Navajo (version 2)<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document;

import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.*;

public class AntiMessage extends Message {

  /**
   * Public constants.
   */
  public static final String MSG_DEFINITION = "antimessage";
  public static final String MSG_NAME = "name";

  public AntiMessage(Element e) {
   super(e);
  }

  /**
   * Create an antimessage (override Message.create())
   */
  public static Message create(Navajo tb, String name) {

    Message p = null;

    Document d = tb.getMessageBuffer();
    Element n = (Element) d.createElement(AntiMessage.MSG_DEFINITION);
    p = new Message(n);
    p.setName(name);

    return p;
  }

}