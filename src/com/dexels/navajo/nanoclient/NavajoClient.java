package com.dexels.navajo.nanoclient;

import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.nanoimpl.*;
//import com.dexels.navajo.document.

public class NavajoClient {
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>This client is nearly identical to the original client, only the document
 * classes not based on DOM-nodes.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

  private static ResourceBundle res = ResourceBundle.getBundle("com.dexels.navajo.swingclient.navajo");

  private  String host = "";
  private  String username = "";
  private  String password = "";
  private  Set threadSet = new HashSet();
  private  String district  = "";

  // Beware: The name may actually matter
  private ThreadGroup threadGroup = new ThreadGroup("requests");

  private static NavajoClient instance = null;
//  static {
//    host = res.getString("default_url");
//  }

  public static NavajoClient getInstance() {
    if (instance==null) {
      instance = new NavajoClient();
    }
    return instance;
  }

   public String getUsername() {
    return username;
  }

  public String getPassword() {
//    System.err.println("Getting password: "+password);
    return password;
  }

  public String getDistrict(){
    return district;
  }

  public String getServerUrl() {
    return host;
  }

  public void setUsername(String s) {
    username = s;
  }

  public void setPassword(String s) {
//    System.err.println("Setting password to : "+s);
    password = s;
    StringTokenizer tok = new StringTokenizer(s, ":");
    if(tok.countTokens() == 4){
      tok.nextToken();
      tok.nextToken();
      district = tok.nextToken();
      System.err.println("District set to: " + district);
    }
  }

  public void setServerUrl(String s) {
    host = s;
  }


  private Map cacheMap = new HashMap();

  private synchronized Navajo getCache(int hash) {
    Navajo n = (Navajo)cacheMap.get(new Integer(hash));
    return n;
  }

  private synchronized void putCache(int hash, Navajo n) {
    cacheMap.put(new Integer(hash),n);
  }

  public NavajoClient() {
    host = res.getString("default_url");
  }
    /**
     * Do a transation with the Navajo Server (name) using
     * a Navajo Message Structure (TMS) compliant XML document.
     */

//  public static Navajo doSimpleSend(Navajo doc, String method,String cacheKey) throws NavajoException {
//    return doSimpleSend(doc,getServerUrl(),method,getUsername(),getPassword(),cacheKey);
//  }

  public  void flushCache() {
    cacheMap.clear();
  }

  public Navajo doSimpleSend(Navajo doc,String method) throws NavajoException {
    Navajo n = doSimpleSend(doc,getServerUrl(),method,getUsername(),getPassword());
    //Message error = n.getMessage("error");
    //if(error != null){
    //  String message = (String)error.getProperty("message").getValue();
    //  new ErrorHandler(new NavajoException("Message:\n" + message));
    //}
    return n;
  }

  private Navajo doSimpleSend(Navajo doc, String server, String method, String user, String password) throws NavajoException {
//    System.err.println("In NavajoClient.doSimpleSend: Navajo: "+dc);
//    System.err.println("Server: "+server+" method: "+method+" user: "+user+" password: "+password);
//    NavajoImpl doc = (NavajoImpl)dc;
    boolean caching = false;
    boolean authenticated = SwingClient.getUserInterface().isAuthenticated(method);
    String threadName = Thread.currentThread().getThreadGroup().getName();
    if(doc==null) {
      doc = (NavajoImpl)NavajoFactory.getInstance().createNavajo();
    }
//    doc.prune();
    if (method.startsWith("Init")) {
      caching = true;
      int hash = method.hashCode();
      Navajo rep = (Navajo)getCache(hash);
    }
    doc.addHeader(NavajoFactory.getInstance().createHeader(doc,method,user,password,-1));
    BufferedInputStream bi;
//    System.err.println("doc: " + doc.toXml().toString());
    /*if (!"requests".equals(threadName)) {
      SwingClient.getUserInterface().getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }*/
    try {
      bi = doTransaction(server,doc,false,"","");
    }
    catch (IOException ex) {
      throw NavajoFactory.getInstance().createNavajoException(ex);
    }
    if(bi==null) {
      System.err.println("No connection.");
      throw NavajoFactory.getInstance().createNavajoException("No connection. (No input stream returned)");
    }
    BufferedReader br;
    Navajo reply = NavajoFactory.getInstance().createNavajo(bi);
    if (caching) {
      putCache(method.hashCode(),reply);
    }


    /*if (!"requests".equals(threadName)) {
      // sync:
      SwingClient.getUserInterface().getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }*/

    return reply;
  }

   private static BufferedInputStream doTransaction(String name, Navajo n, boolean secure, String keystore, String passphrase)
	throws IOException
    {
	    URL url;
        try {
        url = new URL("http://"+name);
        URLConnection con = url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setRequestProperty("Content-type", "text/plain");
        con.setRequestProperty("Accept-Encoding", "gzip");
        //con.setRequestProperty("Content-Encoding", "gzip");

        //OutputStreamWriter writer = new OutputStreamWriter(new java.util.zip.GZIPOutputStream(con.getOutputStream()));
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
//        writer.write("<?xml version='1.0'?>");

        n.write(writer);
        writer.close();
        System.out.println("request header = " + con.getContentEncoding());
        BufferedInputStream in = new BufferedInputStream(new java.util.zip.GZIPInputStream(con.getInputStream()));

        //OutputStream o = con.getOutputStream();

        // For DEBUGGING.
        StringWriter debug = new StringWriter();
//        d.write(debug);
        n.write(debug);
        System.out.println("SEND XML : ");
        System.out.println(debug.toString());
        /////

        //PrintWriter writer = new PrintWriter(con.getOutputStream());
        //writer.write("<?xml version='1.0'?>");
        //d.write(writer);

        //writer.close();
        //InputStream i = con.getInputStream();
        //BufferedInputStream in = new BufferedInputStream(i);

          return in;
        } catch (Exception te) {
          te.printStackTrace();
          return null;
        }
    }
// from here changed everything to static
  private synchronized void addRunner(NavajoAsyncRunner nar) {
    threadSet.add(nar);
//    System.err.println("Added thread: now active: "+threadGroup.activeCount());
    nar.start();
  }

//  public static synchronized void doAsyncSend(Navajo msg, String service) {
//    NavajoAsyncRunner nar = new NavajoAsyncRunner(msg,service,threadGroup);
//    addRunner(nar);
//  }
//
//  public static synchronized void doAsyncSend(Navajo msg, String service, ResponseListener rl) {
//    doAsyncSend(msg,service,rl,null);
//  }

  public synchronized void doAsyncSend(Navajo msg, String service, ResponseListener rl, String responseId) {
    NavajoAsyncRunner nar = new NavajoAsyncRunner(msg,service,threadGroup,rl,responseId);
    addRunner(nar);
  }

  public synchronized void removeRunner(NavajoAsyncRunner n) {
    if (threadSet.contains(n)) {
      threadSet.remove(n);
      System.err.println("Now active: "+threadGroup.activeCount());
      /** @todo Remove this reference to Cursor. Not very elegant. This class should not concern UI */
      if (threadGroup.activeCount()>1) {
//         getContentPane().setCursor((threadGroup.activeCount()>1)?Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR):Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      }
    } else {
      System.err.println("Removed runner, but was not there");
    }
  }

}