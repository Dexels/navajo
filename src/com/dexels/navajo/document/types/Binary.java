package com.dexels.navajo.document.types;

import java.io.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: Binary</p>
 * <p>Description: Binary datacontainer</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels BV</p>
 * @author aphilip
 * @version 1.0
 */

public final class Binary extends NavajoType {

  private byte [] data;
  private String mimetype = "";

  public final static String MSEXCEL = "application/msexcel";
  public final static String MSWORD = "application/msword";
  public final static String PDF = "application/pdf";
  public final static String GIF = "image/gif";
  public final static String TEXT = "plain/text";

  /**
   * Construct a new Binary object with data from an InputStream
   * @param is InputStream
   */
  public Binary(InputStream is) {
    super(Property.BINARY_PROPERTY);
    try {
      int b = -1;
      byte[] buffer = new byte[1024];
      java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
      while ( (b = is.read(buffer, 0, buffer.length)) != -1) {
        bos.write(buffer,0, b);
      }
      bos.close();
      is.close();
      this.data = bos.toByteArray();
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  /**
   * Construct a new Binary object from a byte array
   * @param data byte[]
   */
  public Binary(byte [] data) {
    super(Property.BINARY_PROPERTY);
    this.data = data;
    if (data != null) {
      this.mimetype = guessContentType();
      System.err.println("** Guessed contenttype: " + mimetype);
    }
  }

  /**
   * Construct a new Binary object from a byte array, with a given subtype
   * @param data byte[]
   * @param subtype String
   */
  public Binary(byte [] data, String subtype) {
    super(Property.BINARY_PROPERTY,subtype);
    this.data = data;
    this.mimetype = getSubType("mime");
    this.mimetype = (mimetype == null || mimetype.equals("") ? guessContentType() : mimetype);
  }

  /**
   * Gues the internal data's mimetype
   * @return String
   */
  protected final String guessContentType()
  {
      metadata.FormatDescription description = metadata.FormatIdentification.identify(data);
      if (description != null) {
        System.err.println("guessContentType() = " + description.getShortName() +
                           ", " + description.getMimeType());
      } else {
        System.err.println("UNKOWN content type");
      }
      if (description == null) {
        return "unknown type";
      } else if (description.getMimeType() != null) {
        return description.getMimeType();
      } else {
        return description.getShortName();
      }


  }

  /**
   * Get this Binary's data
   * @return byte[]
   */
  public final byte [] getData() {
    return this.data;
  }

  /**
   * Get this Binary's mimetype
   * @return String
   */
  public final String getMimeType() {
    return this.mimetype;
  }

  /**
   * Set this Binary's mimetype
   * @param mime String
   */
  public final void setMimeType(String mime) {
    this.mimetype = mime;
  }

  // for sorting. Not really much to sort
  public final int compareTo(Object o) {
     return 0;
  }
  
  /**
   * Returns base64.
   */
  public final String getBase64() {
	  if (getData() != null) {
	  	sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();	
		String data = enc.encode(getData());
		StringBuffer suf = new StringBuffer();
		data  = data.replaceAll("\n", "\n  ");
		return data;
	  } else {
		  return null;
	  }
  }

}
