package com.dexels.navajo.tipi.components.swingimpl.embed;

import java.io.*;
import java.io.IOException;

import com.dexels.navajo.tipi.TipiException;

import java.util.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.studio.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.swingclient.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiStandaloneContainer implements TipiStandaloneToplevelContainer  {

  private EmbeddedContext embeddedContext = null;
  private final ArrayList libraries = new ArrayList();
  private UserInterface ui = null;

  public TipiStandaloneContainer() {
	  try {
		embeddedContext = new EmbeddedContext();
	} catch (TipiException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }

  public void setUserInterface(UserInterface u) {
    ui = u;
    if (embeddedContext!=null) {
      embeddedContext.setUserInterface(u);
    }
  }
//  public void setResourceBaseDirectory(File f) {
//      if (embeddedContext!=null) {
//          embeddedContext.setResourceBaseDirectory(f);
//        }
//  }

  public void loadDefinition(String tipiPath, String definitionName,String resourceBaseDirectory) throws IOException, TipiException {
	 // System.err.println("Loading def: "+definitionName+" tipipath: "+tipiPath+" resbase: "+resourceBaseDirectory);
	  embeddedContext = new EmbeddedContext(new String[]{tipiPath},false,new String[]{definitionName},libraries,resourceBaseDirectory);
    if (ui!=null) {
      embeddedContext.setUserInterface(ui);
    }
  }
 
  public ArrayList getListeningServices() {
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
