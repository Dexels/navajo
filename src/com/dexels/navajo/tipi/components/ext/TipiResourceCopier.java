package com.dexels.navajo.tipi.components.ext;

import java.io.*;
import java.net.*;
import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiResourceCopier
    extends TipiComponentImpl {
  public TipiResourceCopier() {
  }

  public Container createContainer() {
    return null;
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth) {
    if (name.equals("copy")) {
      TipiValue source = compMeth.getParameter("source");
      TipiValue target = compMeth.getParameter("target");
      String targetFile = target.getValue();
      if (targetFile.startsWith("%user.home%")) {
        String file = targetFile.substring(targetFile.lastIndexOf("/"));
        targetFile = System.getProperty("user.home") + file;
      }
      //System.err.println("TargetFile: " + targetFile);
      URL input = getClass().getClassLoader().getResource(source.getValue());
      try {
        BufferedInputStream bin = new BufferedInputStream(input.openStream());
        File file_out = new File(targetFile);
        if (!file_out.exists()) {
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
        }
        else {
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