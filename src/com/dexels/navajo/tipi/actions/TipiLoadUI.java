package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import tipi.*;


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
    String file = getParameter("file").getValue();
    if (file != null) {
      MainApplication.loadXML(file);
    }else{
      throw new TipiException("File is NULL!");
    }
  }
}