package com.dexels.navajo.tipi.components.swingimpl.embed;

import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.swingclient.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.studio.*;
import com.dexels.navajo.tipi.tipixml.XMLParseException;
import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EmbeddedContext extends SwingTipiContext {
  TipiStandaloneToplevel top = new TipiStandaloneToplevel();

  public EmbeddedContext(String tipiDefinition[], boolean debugMode, String[] definitionName, List libraries, String resourceBaseDirectory) throws TipiException, IOException {
      this(tipiDefinition,debugMode,definitionName,libraries,resourceBaseDirectory,null);
  }
  
  public EmbeddedContext(String tipiDefinition[], boolean debugMode, String[] definitionName, List libraries, String resourceBaseDirectory, ClassLoader resourceClassLoader) throws TipiException, IOException {
    if ( SwingClient.getUserInterface()==null) {
      SwingTipiUserInterface stui = new SwingTipiUserInterface(this);
      SwingClient.setUserInterface(stui);
    };
    setResourceClassLoader(resourceClassLoader);
     setDefaultTopLevel(top);
     getDefaultTopLevel().setContext(this);
     setDebugMode(debugMode);
//      System.err.println("Opening: " +
//                         context.getResourceURL(args[args.length - 1]));
     if (resourceBaseDirectory!=null) {
         setResourceBaseDirectory(new File(resourceBaseDirectory));
    for (int i = 0; i < libraries.size(); i++) {
       String current = (String)libraries.get(i);
       parseLibraryFromClassPath(current);
     }
         
    }
     for (int i = 0; i < definitionName.length; i++) {
       parseURL(getTipiResourceURL(tipiDefinition[i],getClass().getClassLoader()),false,definitionName[i]);
     }
  }
  public EmbeddedContext(String definitionName, InputStream contents, List libraries, ActivityController al, String resourceBaseDirectory) throws IOException, TipiException {
      this(definitionName,contents,libraries,al,resourceBaseDirectory,null);
  }

  public EmbeddedContext(String definitionName, InputStream contents, List libraries, ActivityController al, String resourceBaseDirectory, ClassLoader cl) throws IOException, TipiException {
      System.err.println("DefinitionName: "+definitionName);
      System.err.println("Res: "+resourceBaseDirectory);
      setResourceClassLoader(cl);
      if ( SwingClient.getUserInterface()==null) {
          SwingTipiUserInterface stui = new SwingTipiUserInterface(this);
          SwingClient.setUserInterface(stui);
        };
        setResourceBaseDirectory(new File(resourceBaseDirectory));

         setDefaultTopLevel(top);
         getDefaultTopLevel().setContext(this);
         setDebugMode(false);
         for (int i = 0; i < libraries.size(); i++) {
             String current = (String)libraries.get(i);
             parseLibraryFromClassPath(current);
           }
          addActivityListener(al);
         parseStream(contents, "aap",definitionName, false);
         
  }
  
  
  public void clearTopScreen() {
    top.removeAllChildren();
  }

  public void exit() {
	  shutdown();
  }

}
