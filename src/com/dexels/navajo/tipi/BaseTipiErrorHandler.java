package com.dexels.navajo.tipi;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import java.util.*;

//import com.dexels.navajo.document.nanoimpl.*;
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
  private ResourceBundle res;
  public BaseTipiErrorHandler() {
    try {
      res = ResourceBundle.getBundle("tipi.validation");
    }
    catch (Exception ex) {
      System.err.println("No resource found.");
      res = null;
    }
  }

  public boolean hasErrors(Navajo n){
    if(n != null){
      //System.err.println("Checking for errors");

      Message error = n.getMessage("error");
      Message conditions = n.getMessage("ConditionErrors");
      if (error != null) {
        errorCode = (String) error.getProperty("code").getValue();
        errorMessage = (String) error.getProperty("message").getValue();
        //System.err.println("Found:" + getErrorMessage());
        return true;
      } else if (conditions != null) {
        ArrayList failures = conditions.getAllMessages();
        System.err.println("FOUND CONDITION ERROR");
        errorMessage = "Conditionele fouten:\n";
        for(int i=0;i<failures.size();i++){
          Message current = (Message)failures.get(i);
          String expression  = (String)current.getProperty("FailedExpression").getValue();
          String id = (String)current.getProperty("Id").getValue();
          errorMessage = errorMessage + getConditionErrorDescription(id) +"\n";
        }

        return true;
      }else{
        return false;
      }
    }else{
      return false;
    }
  }

  private String getConditionErrorDescription(String id){
    String description = id;
    try{
      String found = res.getString(id);
      description = found;
    }catch(MissingResourceException ex){
      System.err.println("----> Cannot find reference for condition errorId: " + id);
    }
    return description;
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