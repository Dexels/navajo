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

       while ((val = inStream.read()) != -1)
         myData.write(val);
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  public void render(OutputStream out) throws java.io.IOException {
    /**@todo Implement this nextapp.echo.StreamImageReference abstract method*/
//    new PipedOutputStream(
    ByteArrayInputStream bai = new ByteArrayInputStream(myData.toByteArray());
    int val;
    while ((val = bai.read()) != -1) {
         out.write(val);
    }
    PipedInputStream s;
  }

  public String getContentType() {
    return myType;
  }

}
