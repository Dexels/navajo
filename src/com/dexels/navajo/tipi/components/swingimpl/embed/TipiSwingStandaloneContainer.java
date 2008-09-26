package com.dexels.navajo.tipi.components.swingimpl.embed;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.swingclient.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSwingStandaloneContainer implements TipiStandaloneToplevelContainer  {

  private SwingEmbeddedContext embeddedContext = null;
  private final List<String> libraries = new ArrayList<String>();
  private UserInterface ui = null;

  public TipiSwingStandaloneContainer(SwingTipiContext parentContext) {
		embeddedContext = new SwingEmbeddedContext(parentContext);

 }

  public void setUserInterface(UserInterface u) {
    ui = u;
    if (embeddedContext!=null) {
      embeddedContext.setUserInterface(u);
    }
  }


  public void loadDefinition(String tipiPath, String definitionName,String resourceBaseDirectory) throws IOException, TipiException {
	 // System.err.println("Loading def: "+definitionName+" tipipath: "+tipiPath+" resbase: "+resourceBaseDirectory);
	  embeddedContext = new SwingEmbeddedContext((SwingTipiContext) getContext(), new String[]{tipiPath},false,new String[]{definitionName},libraries,resourceBaseDirectory);
    if (ui!=null) {
      embeddedContext.setUserInterface(ui);
    }
  }
 
  public List<String> getListeningServices() {
      if (embeddedContext!=null) {
          return embeddedContext.getListeningServices();
        }
      return null;
  }
  
  public void loadClassPathLib(String location) {
    libraries.add(location);
  }

  public void loadNavajo(Navajo nav,String method)  throws TipiException, TipiBreakException {
    embeddedContext.loadNavajo(nav,method);
  }

  public TipiContext getContext() {
    return embeddedContext;
  }

  public void shutDownTipi() {
    embeddedContext.shutdown();
  }
}
