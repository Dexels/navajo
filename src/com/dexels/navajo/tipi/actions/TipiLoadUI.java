package com.dexels.navajo.tipi.actions;

import java.io.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiLoadUI
    extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
//    String file = getParameter("file").getValue();
    Object file = evaluate(getParameter("file").getValue()).value;
    System.err.println("CLASSS: " + file.getClass());
    myContext.closeAll();
    if (file != null) {
//      MainApplication.loadXML(file);
      try {
        File f = new File( (String) file);
        FileInputStream fis = new FileInputStream(f);
        myContext.parseStream(fis, "aap");
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
    }
    else {
      throw new TipiException("File is NULL!");
    }
  }
}