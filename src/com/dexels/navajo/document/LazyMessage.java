package com.dexels.navajo.document;

import org.w3c.dom.Element;

/**
 * This class implements a so called lazy message.
 * A lazy message contains one or more submessages that can be addressed "lazy" by
 * a client.
 * The laziness implies that not necessarily all submessages are sent back, but a specific
 * subset can be identified to improve client-side performance.
 *
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class LazyMessage extends Message {

  /**
    * Public constants.
    */

  public static String MSG_DEFINITION = "lazymessage";

  private int startIndex;
  private int endIndex;
  private int totalSubMessages;

  public LazyMessage(Element e) {
    super(e);
  }
}