package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.io.*;
import java.net.*;

import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiImageReference
    extends StreamImageReference {
  private String myType = "garbage";
  private URL myUrl = null;
  private ByteArrayOutputStream myData = new ByteArrayOutputStream();

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

    try {
      BufferedInputStream inStream = new BufferedInputStream(myUrl.openStream());
      int val;
      while ( (val = inStream.read()) != -1) {
        myData.write(val);
      }
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void render(OutputStream parm1) throws java.io.IOException {
    InputStream is = myUrl.openStream();

    byte[] buffer = new byte[1024];
    int read = 0;
    while ( (read = is.read(buffer)) > 0) {
      parm1.write(buffer, 0, read);
    }
    parm1.flush();
    is.close();
  }

  public String getContentType() {
    return myType;
  }

}
