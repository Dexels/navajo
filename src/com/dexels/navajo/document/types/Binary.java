package com.dexels.navajo.document.types;

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
  private String mimetype = "unknown";

  public final static String MSEXCEL = "application/msexcel";
  public final static String MSWORD = "application/msword";
  public final static String PDF = "application/pdf";
  public final static String GIF = "image/gif";

  public Binary(byte [] data) {
    this.data = data;
  }

  public Binary(byte [] data, String mimetype) {
    this.data = data;
    this.mimetype = mimetype;
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