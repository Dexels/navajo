package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiCommitDefinition extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    String definition = (String)evaluate(getParameter("definition").getValue()).value;
    System.err.println("Attempting to commit to definition: "+definition);
    if (definition != null) {
      myContext.commitDefinition(definition);
    }else{
      throw new TipiException("No definition!");
    }
  }
}