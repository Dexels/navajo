package com.dexels.navajo.tipi.components;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.awt.*;
import java.lang.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ResourceCopier extends com.dexels.navajo.tipi.TipiComponent{
  public ResourceCopier() {
  }

  public void addToContainer(Component c, Object constraints) {
    throw new java.lang.UnsupportedOperationException("Method createContainer() not yet implemented.");
  }
  public void registerEvents() {
    // Unsupported
  }
  public Container createContainer() {
    return null;
  }

  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
    return super.getComponentValue(name);
  }

  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {
    if (name.equals("copy")) {
      TipiMethodParameter source = compMeth.getParameter("source");
      TipiMethodParameter target = compMeth.getParameter("target");
      String targetFile = target.getValue();
      if(targetFile.startsWith("%user.home%")){
        String file = targetFile.substring(targetFile.lastIndexOf("/"));
        targetFile = System.getProperty("user.home") + file;
      }
      //System.err.println("TargetFile: " + targetFile);
      URL input = getClass().getClassLoader().getResource(source.getValue());
      try {
        BufferedInputStream bin = new BufferedInputStream(input.openStream());
        File file_out = new File(targetFile);
        if(!file_out.exists()){
          file_out.getParentFile().mkdirs();
          FileOutputStream fout = new FileOutputStream(file_out);
          byte[] buffer = new byte[1024];
          while (bin.read(buffer) >= 0) {
            fout.write(buffer);
          }
          fout.flush();
          bin.close();
          fout.close();
          //System.err.println("Resource copied!");
        }else{
          System.err.println("File not copied, it was already there.");
        }
      }
      catch (IOException ex) {
        System.err.println("Error reading from resource");
        ex.printStackTrace();
      }
    }
  }


}