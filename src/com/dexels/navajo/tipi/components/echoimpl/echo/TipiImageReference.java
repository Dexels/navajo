package com.dexels.navajo.tipi.components.echoimpl.echo;

import nextapp.echo.*;
import java.io.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiImageReference extends StreamImageReference {
      private String myType = "garbage";
      private URL myUrl = null;

  public TipiImageReference(URL resource) {
    myUrl = resource;
    if (myUrl.toString().endsWith("gif")) {
      myType = "image/gif";
    }
    if (myUrl.toString().endsWith("png")) {
      myType = "image/png";
    }
    if (myUrl.toString().endsWith("jpeg")) {
      myType = "image/jpeg";
    }

  }
  public void render(OutputStream parm1) throws java.io.IOException {
    /**@todo Implement this nextapp.echo.StreamImageReference abstract method*/
//    new PipedOutputStream(
    InputStream is = myUrl.openStream();

    PipedInputStream s;
//    s.
  }
  public String getContentType() {
    return myType;
  }

}
