package com.dexels.navajo.studio;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class NavajoEvent {


  private String classPath="";
  private boolean incomingBPFLChanged;
  public NavajoEvent() {
  }

  void setClassPath(String string){
    classPath =string;
  }
  String getClassPath(){
    return classPath;
  }
  void setIncomingBPFL(boolean changed){
    incomingBPFLChanged = changed;
  }
  boolean getIncomingBPFL(){
    return incomingBPFLChanged;
  }


}