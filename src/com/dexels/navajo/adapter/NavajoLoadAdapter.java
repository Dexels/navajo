package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.document.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NavajoLoadAdapter
    implements Mappable {

  public String pathPrefix;

  public String fileName;

  private Access access = null;

  public NavajoLoadAdapter() {
  }


  public void setPathPrefix(String p) {
    pathPrefix = p;
  }

  public void setFileName(String p) {
    fileName = p;
  }

  public String getPathPrefix() {
    return pathPrefix;
  }

  public String getFileName() {
    return fileName;
  }

  public void kill() {
  }

  public void load(Parameters parms, Navajo inMessage, Access access,
                   NavajoConfig config) throws MappableException, UserException {

//    Property nameProperty = inMessage.getProperty(namePropertyPath);
//    String name = nameProperty.getValue();
    this.access = access;
  }

  public void store() throws MappableException, UserException {
    String totalPath = fileName;
    System.err.println("Total: "+totalPath);
    File f = new File(totalPath);
    if (!f.exists()) {
      throw new MappableException("Could not open file!");
    }
    Navajo n = null;

    FileInputStream fr = null;
    try {
      fr = new FileInputStream(f);
      n =  NavajoFactory.getInstance().createNavajo(fr);;
      access.setOutputDoc(n);
    }

    catch (Exception ex) {
      ex.printStackTrace();
      throw new MappableException("Could not parse file!");
    }
    finally {
      if (fr!=null) {
        try {
          fr.close();
        }
        catch (IOException ex1) {
// whatever
          ex1.printStackTrace();
        }
      }
    }
  }

}
