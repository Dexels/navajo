package com.dexels.navajo.document;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NavajoFilterProcessor {

  public static Navajo filter(Navajo incoming, Navajo out)   {
    Header h = incoming.getHeader();
    if (h==null) {
      return out;
    }
    return filter(h,out);
  }

  private static Navajo filter(Header header, Navajo out) {
//    Navajo newNavajo = NavajoFactory.getInstance().createNavajo();
//    ArrayList messages = out.getAllMessages();
    return out;
  }
}
