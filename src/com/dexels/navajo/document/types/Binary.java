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

public class Binary {

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

  protected String guessContentType()
  {

      metadata.FormatDescription description = metadata.FormatIdentification.identify(data);
      System.err.println("guessContentType() = " + description.getShortName() + ", " + description.getMimeType());
      if (description.getMimeType() != null) {
        return description.getMimeType();
      } else {
        return description.getShortName();
      }


  }

  public byte [] getData() {
    return this.data;
  }

  public String getMimeType() {
    return this.mimetype;
  }

  public void setMimeType(String mime) {
    this.mimetype = mimetype;
  }

}