package tipi;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import nanoxml.*;
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

public class MainApplet extends JApplet {
  private boolean isStandalone = false;
  String tipiFile;
  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public MainApplet() {
//    try {
//      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//    }
  }
  //Initialize the applet
  public void init() {
    try {
      tipiFile = this.getParameter("tipiXML", "test.xml");
      jbInit();
      System.err.println("MY DOCBASE: "+getCodeBase());
//      System.err.println("CLASS: "+get);
      TipiContext.getInstance().setUIMode(TipiContext.UI_MODE_APPLET);
      TipiContext.getInstance().setResourceURL(getCodeBase());
      TipiContext.getInstance().setToplevel(this);
      TipiContext.getInstance().parseURL(new URL(getCodeBase(),tipiFile));
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void jbInit() throws Exception {
//    this.setSize(new Dimension(640,480));
  }
  //Start the applet
  public void start() {
  }
  //Stop the applet
  public void stop() {
  }
  //Destroy the applet
  public void destroy() {
  }
  //Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }
  //Get parameter info
  public String[][] getParameterInfo() {
    String[][] pinfo =
      {
      {"tipiXML", "String", ""},
      };
    return pinfo;
  }

  //static initializer for setting look & feel
  static {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch(Exception e) {
    }
  }
}