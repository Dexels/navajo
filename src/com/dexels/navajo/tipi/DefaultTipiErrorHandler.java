package com.dexels.navajo.tipi;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.impl.*;
import javax.swing.*;
import tipi.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiErrorHandler extends BaseTipiErrorHandler {

  private TipiContext context;

  public DefaultTipiErrorHandler() {
    //setContainer(createContainer);
  }

  public void showError(){
    TipiContext c = getContext();
    if(c != null){
      JFrame top = (JFrame)c.getTopLevel();
      JOptionPane.showMessageDialog(top, getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }else{
      System.err.println("DefaultTipiErrorHandler, context not set!! ");
    }
  }

  public void showError(String text){
    TipiContext c = getContext();
    if(c != null){
      JFrame top = (JFrame)c.getTopLevel();
      JOptionPane.showMessageDialog(top, text, "Error", JOptionPane.ERROR_MESSAGE);
    }else{
      System.err.println("DefaultTipiErrorHandler, context not set!! ");
    }
  }

  public void showError(Exception e){
    TipiContext c = getContext();
    if(c != null){
      JFrame top = (JFrame)c.getTopLevel();
      JOptionPane.showMessageDialog(top, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }else{
      System.err.println("DefaultTipiErrorHandler, context not set!! ");
    }
  }

  public Object createContainer(){
    return new DefaultTipiErrorPanel();
  }
}