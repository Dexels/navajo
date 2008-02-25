package com.dexels.navajo.tipi.swingclient.components;

import com.dexels.navajo.document.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.sportlink.client.swing.dialogs.*;
import java.io.*;

import com.dexels.navajo.tipi.swingclient.*;
//import com.dexels.navajo.document.nanoimpl.*;
/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class ErrorHandler {

  Message conditionErrors = null;
  Message exceptions = null;
  private boolean errorOccured = false;

  public ErrorHandler() {
  }

  public ErrorHandler(Navajo n){
    conditionErrors = n.getMessage("conditionerrors");
    exceptions = n.getMessage("error");
    if(conditionErrors != null){
      errorOccured = true;
      SwingClient.getUserInterface().addErrorDialog(conditionErrors);
    }else if(exceptions != null){
      errorOccured = true;
      String code = (String)exceptions.getProperty("code").getValue();
      if(code.trim().equals("10")){
//        AuthorisationDialog.
//        AuthorisationDialog.getInstance().showDialog();
      }else{
        SwingClient.getUserInterface().addErrorDialog(exceptions, getStackTrace());
      }
    }
    else{
  //    SwingClient.getUserInterface().setStatusText("");
    }
  }

  private String getStackTrace(){
    try{
      throw (new Exception());
    }catch(Exception e){
      try{
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
      }catch(Exception ex){
        return "No StackTrace available";
      }
    }
  }

  public ErrorHandler(Exception e){
    if(e != null){
      errorOccured = true;
      //SwingClient.getUserInterface().addErrorDialog(e);
    }
  }

//  public boolean errorOccured(){
//    return errorOccured;
//  }
}
