package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.*;
import nextapp.echoservlet.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class EchoTipiContext extends TipiContext {
  private ServerContext myServerContext;
  public EchoTipiContext() {
  }
  public void setSplashInfo(String s) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiContext abstract method*/
  }
  public void setSplashVisible(boolean b) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiContext abstract method*/
  }
  public void setSplash(Object s) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiContext abstract method*/
  }
  public void clearTopScreen() {
    /**@todo Implement this com.dexels.navajo.tipi.TipiContext abstract method*/
  }

  public void setServerContext(ServerContext sc){
    myServerContext = sc;
  }

  public void exit(){
    myServerContext.exit();
    System.err.println("---------------------------------------------------------------------------------------> EXITED");
  }

}
