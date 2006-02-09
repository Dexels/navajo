package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.io.OutputStream;
import java.io.InputStream;

public class DefaultBrowser {

  private static final String WIN_ID = "Windows";
  private static final String WIN_PATH = "rundll32";
  private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
  private static final String UNIX_PATH = "netscape";
  private static final String UNIX_FLAG = "-remote openURL";

  public DefaultBrowser() {
    super();
  }

  public static boolean displayURL(String url) {

    boolean result = true;
    boolean windows = false;
    String os = System.getProperty("os.name").toLowerCase();
    if (os.indexOf("windows") >= 0) {
      windows = true;
    }
    String cmd = null;
    try {
      if (windows) {
        cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
        System.err.println("Executing Windows command: " + cmd);
        Process p = Runtime.getRuntime().exec(cmd);
      }
      else {
        if(url.indexOf(".doc") >= 0 || url.indexOf(".xls") >= 0 || url.indexOf(".ppt") >= 0 || url.indexOf(".txt") >= 0 || url.indexOf(".rtf") >= 0){
          cmd = "ooffice " + url;
        } else if(url.indexOf(".jpg") >= 0 || url.indexOf(".gif") >= 0 || url.indexOf(".png") >= 0){
          cmd = "display " + url;
        } else if(url.indexOf(".pdf") >= 0){
          cmd = "xpdf '" + url + "'";
        }else if(url.indexOf(".jnlp") >= 0){
          cmd = "mozilla "+ url;
        }
        
        else{ // we don't have a clue..
          cmd = "mozilla " + url;
        }
        if(cmd != null){
          Process p = Runtime.getRuntime().exec(cmd);
          
        }
      }
    }
    catch (java.io.IOException ex) {
       result = false;
      System.err.println("Could not invoke browser, command=" + cmd);
      System.err.println("Caught: " + ex);
    }
    return result;
  }
}
