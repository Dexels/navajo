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
  private TipiContext myContext = null;

  private byte[] data = null;

  public TipiResourceReference(TipiContext tc, XMLElement xe) {
//    <tipi-resource id="studioIcon" description="" path="{resource:/com/dexels/navajo/tipi/studio/images/studio-icon.gif}" type="image/gif" local="true"/>
    myContext = tc;
    load(xe);
  }

  private void load(XMLElement xe) {
    id = xe.getStringAttribute("id");
    description = xe.getStringAttribute("description");
    path = xe.getStringAttribute("path");
    type = xe.getStringAttribute("type");
    local = xe.getBooleanAttribute("local","true","false",false);
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


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public InputStream getUncachedStream() throws IOException{
    URL u = myContext.getResourceURL(path);
    return u.openStream();
  }

  public InputStream getCachedStream() throws IOException{
    if (data!=null) {
      return new ByteArrayInputStream(data);
    }
    URL u = myContext.getResourceURL(path);
    ByteArrayOutputStream bao = new ByteArrayOutputStream();
    copyResource(bao,u.openStream());
    data = bao.toByteArray();
    return new ByteArrayInputStream(data);
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
