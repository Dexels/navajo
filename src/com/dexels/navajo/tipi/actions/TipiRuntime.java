package com.dexels.navajo.tipi.actions;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.Operand;
import java.lang.reflect.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/** @todo Refactor, move to NavajoSwingTipi */
public class TipiRuntime
    extends TipiAction {
  public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    String txt = getParameter("command").getValue();
    Operand o = null;
    try {
      o = evaluate(txt, event);
    }
    catch (Exception ex) {
      System.err.println("Error evaluating[" + txt + "] inserting as plain text only");
      ex.printStackTrace();
    }
    String command = "rundll32 url,FileProtocolHandler ";
    if(o != null){
      command = command + (String)o.value;
    }else{
      command = command + txt;
    }
    try{
      Runtime rt = Runtime.getRuntime();
      Process ps = rt.exec(command);
      System.err.println("Command exited: " + ps.exitValue());
    }catch(IOException ioe){
      System.err.println("Execution failed! stack:");
      ioe.printStackTrace();
    }
  }
}
