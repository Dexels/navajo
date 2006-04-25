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

  //private byte [] data;
  private String mimetype = "";

  private File dataFile = null;

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
//      byte[] buffer = new byte[1024];
//      java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
//      while ( (b = is.read(buffer, 0, buffer.length)) != -1) {
//        bos.write(buffer,0, b);
//      }
//      bos.close();
//      is.close();
//      this.data = bos.toByteArray();
      dataFile = File.createTempFile("binary_object", "navajo");
      dataFile.deleteOnExit();
      //System.err.println("Created temp file: " + dataFile.getAbsolutePath());
      FileOutputStream fos = new FileOutputStream(dataFile);
      byte[] buffer = new byte[1024];
      while ( (b = is.read(buffer, 0, buffer.length)) != -1) {
    	  fos.write(buffer,0, b);
      }
      fos.close();
      is.close();

      this.mimetype = guessContentType();
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
	  //this.data = data;
	  try {
		  dataFile = File.createTempFile("binary_object", "navajo");
		  dataFile.deleteOnExit();
		  System.err.println("Created temp file: " + dataFile.getAbsolutePath());
		  FileOutputStream fos = new FileOutputStream(dataFile);
		  fos.write(data);
		  fos.close();
	  } catch (IOException e) {
		  e.printStackTrace(System.err);
	  }
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
    //this.data = data;
    try {
		  dataFile = File.createTempFile("binary_object", "navajo");
		  dataFile.deleteOnExit();
		  //System.err.println("Created temp file: " + dataFile.getAbsolutePath());
		  FileOutputStream fos = new FileOutputStream(dataFile);
		  fos.write(data);
		  fos.close();
	  } catch (IOException e) {
		  e.printStackTrace(System.err);
	  }

    this.mimetype = getSubType("mime");
    this.mimetype = (mimetype == null || mimetype.equals("") ? guessContentType() : mimetype);

  }

  /**
   * Gues the internal data's mimetype
   * @return String
   */
  protected final String guessContentType()
  {
	  if (mimetype != null && !mimetype.equals("")) {
		  return mimetype;
	  } else {
      metadata.FormatDescription description = metadata.FormatIdentification.identify(dataFile);
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
  }

  /**
   * Get this Binary's data
   * @return byte[]
   */
  public final byte [] getData() {
	  //return this.data;
	  RandomAccessFile in = null;
	  try {
		  if ( dataFile != null ) {
			  in = new RandomAccessFile(dataFile, "r");
			  byte [] data = new byte[(int) dataFile.length()];// + 1 ];
			  in.readFully(data);
			  return data;
		  }
	  } catch (Exception e) {
		  e.printStackTrace(System.err);
	  } finally {
		  try {
			  if ( in != null ) {
				  in.close();
			  }
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	  }
	  return null;
  }

  public final InputStream getDataAsStream() {
	  if ( dataFile != null ) {
		  try {
			  return new FileInputStream(dataFile);
		  } catch (FileNotFoundException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace(System.err);
			  return null;
		  }
	  } else {
		  return null;
	  }
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

  public void finalize() {
	  System.err.println("In finalize Binary()");
	  if ( dataFile != null ) {
		  dataFile.delete();
	  }
  }

  /**
   * Returns base64.
   */
  public final String getBase64() {
	  if (getData() != null) {
	  	sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
		String data = enc.encode(getData());
		data  = data.replaceAll("\n", "\n  ");
		return data;
	  } else {
		  return null;
	  }
  }

}
