package com.dexels.navajo.client.impl.navajos;

import com.dexels.navajo.client.impl.BaseNavajoHandler;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Handler extends BaseNavajoHandler {
  public Handler() {
  }
  protected void parseURL(URL u, String spectotal, int start, int limit) {
    System.err.println("Navajos: ");
    setSecure(true);
    super.parseURL(u,spectotal,start,limit);
  }

}
