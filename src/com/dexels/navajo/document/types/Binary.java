package com.dexels.navajo.document.types;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class Binary {

  private byte [] data;
  private String mimetype = "";

  public final static String MSEXCEL = "application/msexcel";
  public final static String MSWORD = "application/msword";
  public final static String PDF = "application/pdf";
  public final static String GIF = "image/gif";

  public Binary(byte [] data) {
    this.data = data;
    this.mimetype = guessContentType();
  }

  public Binary(byte [] data, String mimetype) {
    this.data = data;
    this.mimetype = (mimetype == null || mimetype.equals("") ? guessContentType() : mimetype);
  }

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

  public final byte [] getData() {
    return this.data;
  }

  public final String getMimeType() {
    return this.mimetype;
  }

  public final void setMimeType(String mime) {
    this.mimetype = mimetype;
  }

}