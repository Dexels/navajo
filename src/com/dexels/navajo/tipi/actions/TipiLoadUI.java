package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import tipi.*;
import java.net.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.io.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiLoadUI extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
//    String file = getParameter("file").getValue();

    Object file = evaluate(getParameter("file").getValue()).value;
    System.err.println("CLASSS: "+file.getClass());
    TipiContext.getInstance().closeAll();

    if (file != null) {
//      MainApplication.loadXML(file);
      try {
        File f = new File((String)file);
        FileInputStream fis = new FileInputStream(f);
        TipiContext.getInstance().parseStream(fis,"aap");
      }
      catch (TipiException ex) {
        ex.printStackTrace();
      }
      catch (XMLParseException ex) {
        ex.printStackTrace();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
    }else{
      throw new TipiException("File is NULL!");
    }
  }
}