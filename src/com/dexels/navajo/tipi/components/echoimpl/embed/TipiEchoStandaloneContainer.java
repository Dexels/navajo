package com.dexels.navajo.tipi.components.echoimpl.embed;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.echoimpl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiEchoStandaloneContainer implements TipiStandaloneToplevelContainer  {

  private EchoEmbeddedContext embeddedContext = null;
  private final List<String> libraries = new ArrayList<String>();
  private TipiEchoInstance myInstance;
  public TipiEchoStandaloneContainer(TipiEchoInstance instance, EchoTipiContext parentContext) {
	  System.err.println("Created embedded context");
	  embeddedContext = new EchoEmbeddedContext(instance,parentContext);
	  myInstance = instance;
 }

 
  public void loadDefinition(String tipiPath, String definitionName,String resourceBaseDirectory) throws IOException, TipiException {
	  System.err.println("Loading def: "+definitionName+" tipipath: "+tipiPath+" resbase: "+resourceBaseDirectory);
	  embeddedContext = new EchoEmbeddedContext(myInstance, (EchoTipiContext) getContext(), new String[]{tipiPath},false,new String[]{definitionName},libraries,resourceBaseDirectory);
   
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
