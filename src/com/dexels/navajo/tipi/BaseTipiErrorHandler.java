package com.dexels.navajo.tipi;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class BaseTipiErrorHandler implements TipiErrorHandler{

  private String errorCode, errorMessage;
  private TipiContext context;

  public BaseTipiErrorHandler() {
  }

  public boolean hasErrors(Navajo n){
    if(n != null){
      System.err.println("Checking for errors");
      Message error = n.getMessage("error");
      if (error != null) {
        errorCode = (String) error.getProperty("code").getValue();
        errorMessage = (String) error.getProperty("message").getValue();
        System.err.println("Found:" + getErrorMessage());
        return true;
      }
      else {
        return false;
      }
    }else{
      return false;
    }
  }

  public void setContext(TipiContext c){
    context = c;
  }

  public TipiContext getContext(){
    return context;
  }

  public String getErrorMessage(){
    return errorMessage;
  }

  public String getErrorCode(){
    return errorCode;
  }
}