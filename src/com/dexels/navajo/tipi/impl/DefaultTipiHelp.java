package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.io.*;
import java.net.*;
import calpa.html.*;
//import com.dexels.navajo.document.nanoimpl.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiHelp extends DefaultTipi implements CalHTMLObserver  {

//  JEditorPane myBrowser;
  CalHTMLPane myBrowser;
  private String page = "";
  private Thread myThread = null;

  public DefaultTipiHelp() {
    initContainer();
    CalHTMLPreferences cc = new CalHTMLPreferences();
    cc.setAutomaticallyFollowHyperlinks(true);
    myBrowser = new CalHTMLPane(cc,this,"");
//    myBrowser.setC
//    myBrowser.saddHyperlinkListener(this);
//    myBrowser.add
//    myBrowser.setEditable(false);
    ((JScrollPane)getContainer()).getViewport().add(myBrowser);
//    try{
//      URL test = new URL("http://www.dexels.com");
//      myBrowser.setPage(test);
//    }catch(Exception e){
//      System.err.println("Whoops url not found!");
//    }
  }

  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
  }
  public void removeFromContainer(Component c) {
    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
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
//setAsyncToPage(event.getURL().toString());
//      myBrowser.set
//      myBrowser.showHTMLDocument(event.getURL().toString());
//        myBrowser.setPage(event.getURL());
//      } catch(IOException ioe) {
//      }
    }
  }
  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name,object);
      if (name.equals("url")) {
          myBrowser.showHTMLDocument((URL)object);
      }
  }
  public void formSubmitUpdate(CalHTMLPane parm1, URL parm2, int parm3, String parm4, String parm5) {
  }
  public void historyUpdate(CalHTMLPane parm1, int parm2) {
   }
  public void linkActivatedUpdate(CalHTMLPane parm1, URL parm2, String parm3, String parm4) {
    myBrowser.showHTMLDocument(parm2);
  }
  public void linkFocusedUpdate(CalHTMLPane parm1, URL parm2) {
  }
  public void showNewFrameRequest(CalHTMLPane parm1, String parm2, URL parm3) {
  }
  public void statusUpdate(CalHTMLPane parm1, int parm2, URL parm3, int parm4, String parm5) {
  }

//  public void setAsyncToPage(String p) {
//    page = p;
//    System.err.println("Setting browser URL to: " + p);
//    myThread = new Thread(this);
//    myThread.start();
//  }
//
//  public void run() {
//    try {
//      myBrowser.setPage(page);
//    }
//    catch (IOException ex) {
//      ex.printStackTrace();
//    }
//  }

}