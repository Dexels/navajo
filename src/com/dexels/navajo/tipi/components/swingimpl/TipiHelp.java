package com.dexels.navajo.tipi.components.swingimpl;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import calpa.html.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

//import com.dexels.navajo.document.nanoimpl.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiHelp
    extends TipiDataComponentImpl
    implements CalHTMLObserver {
//  JEditorPane myBrowser;
  CalHTMLPane myBrowser;
  private String page = "";
  private Thread myThread = null;
//  public DefaultTipiHelp() {
//    initContainer();
//  }
//  public void addToContainer(Component c, Object constraints) {
//    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
//  }
//  public void removeFromContainer(Component c) {
//    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
//  }
  public void performService(Navajo parm1, TipiContext parm2, String parm3) throws com.dexels.navajo.tipi.TipiException {
    System.err.println("Cannot perform service");
  }

  public Container createContainer() {
    JScrollPane c = new JScrollPane();
    CalHTMLPreferences cc = new CalHTMLPreferences();
    cc.setAutomaticallyFollowHyperlinks(true);
    myBrowser = new CalHTMLPane(cc, this, "");
    c.getViewport().add(myBrowser);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return c;
  }

  public void hyperlinkUpdate(HyperlinkEvent event) {
    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
    }
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("url")) {
      myBrowser.showHTMLDocument( (URL) object);
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