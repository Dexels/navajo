package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.io.*;
import java.net.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiHelp extends DefaultTipi implements HyperlinkListener, Runnable {

  JEditorPane myBrowser;
  private String page = "";
  private Thread myThread = null;

  public DefaultTipiHelp() {
    initContainer();
    myBrowser = new JEditorPane();
    myBrowser.addHyperlinkListener(this);
    myBrowser.setEditable(false);
    ((JScrollPane)getContainer()).getViewport().add(myBrowser);
    try{
      URL test = new URL("http://www.dexels.com");
      myBrowser.setPage(test);
    }catch(Exception e){
      System.err.println("Whoops url not found!");
    }
  }

  public void addToContainer(Component parm1, Object parm2) {
    System.err.println("Cannot add. whooahahahaA!");
  }
  public void performService(Navajo parm1, TipiContext parm2, String parm3) throws com.dexels.navajo.tipi.TipiException {
    System.err.println("Cannot perform service");
  }
  public Container createContainer() {
    return new JScrollPane();
  }

  public void hyperlinkUpdate(HyperlinkEvent event) {
    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//      try {
setAsyncToPage(event.getURL().toString());
//        myBrowser.setPage(event.getURL());
//      } catch(IOException ioe) {
//      }
    }
  }
  public void setComponentValue(String name, Object object) {
    System.err.println("SETTING COMPONENT VALUE OF HELP: "+name+" :: "+object);
      if (name.equals("url")) {
        setAsyncToPage( (String) object);
      }
  }

  public void setAsyncToPage(String p) {
    page = p;
    myThread = new Thread(this);
    myThread.start();
  }

  public void run() {
    try {
      myBrowser.setPage(page);
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

}