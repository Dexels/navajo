package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
import java.io.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiResourceReference {
  private String id = null;
  private String description = null;
  private String path = null;
  private String type = null;
  private boolean local = false;
  private boolean eager = false;
  private TipiContext myContext = null;

  private byte[] data = null;

  public TipiResourceReference(TipiContext tc, XMLElement xe) throws IOException {
//    <tipi-resource id="studioIcon" description="" path="{resource:/com/dexels/navajo/tipi/studio/images/studio-icon.gif}" type="image/gif" local="true"/>
    myContext = tc;
    load(xe);
  }

  private void load(XMLElement xe) throws IOException {
    id = xe.getStringAttribute("id");
    description = xe.getStringAttribute("description");
    path = xe.getStringAttribute("path");
    type = xe.getStringAttribute("type");
    local = xe.getBooleanAttribute("local","true","false",false);
    eager = xe.getBooleanAttribute("eager","true","false",false);
//    if (eager && local) {
//      getStream();
//    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isLocal() {
    return local;
  }
  public boolean isEager() {
    return eager;
  }


  public void setEager(boolean b) {
    eager = b;
  }

  public void setLocal(boolean b) {
    local = b;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public InputStream getStream(TipiComponent source) throws IOException {
    if (local) {
      return getCachedStream(source);
    } else {
      return getUncachedStream(source);
    }
  }

  public URLConnection getUncachedURLConnection(TipiComponent source) throws IOException{
    System.err.println("Getting url: "+path);
    URL u = (URL)myContext.evaluate(path,source).value;
//    URL u = myContext.getResourceURL(path);
    if (u==null) {
      return null;
    }
    return u.openConnection();
  }

  public URL getURL(TipiComponent source) {
    URL u = (URL)myContext.evaluate(path,source).value;
    return u;
  }


  private InputStream getUncachedStream(TipiComponent source) throws IOException{
    if (data!=null) {
      return new ByteArrayInputStream(data);
    }
    URLConnection uc = getUncachedURLConnection(source);
    String contentType = uc.getContentType();
    InputStream myStream = uc.getInputStream();
    return myStream;
  }

  private InputStream getCachedStream(TipiComponent source) throws IOException{
    if (data!=null) {
      return new ByteArrayInputStream(data);
    }
    URLConnection uc = getUncachedURLConnection(source);
    String contentType = uc.getContentType();
    InputStream myStream = uc.getInputStream();
    if (uc==null) {
      throw new IOException("Unknown resource");
    }
    ByteArrayOutputStream bao = new ByteArrayOutputStream();
    copyResource(bao,myStream);
    data = bao.toByteArray();
    return new ByteArrayInputStream(data);
  }

  public byte[] getData(TipiComponent source) throws IOException {
    if (data==null) {
      getCachedStream(source);
    }
    return data;
  }

  private void copyResource(OutputStream out, InputStream in) throws IOException{
      BufferedInputStream bin = new BufferedInputStream(in);
      BufferedOutputStream bout = new BufferedOutputStream(out);
      byte[] buffer = new byte[1024];
      int read;
      while ((read = bin.read(buffer)) > -1) {
        bout.write(buffer,0,read);
      }
      bin.close();
      bout.flush();
      bout.close();
  }

}
