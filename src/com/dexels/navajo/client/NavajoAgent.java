
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.xml.*;

import org.w3c.dom.Document;

/**
 * The NavajoAgent class represents the client side of the Navajo communication process.
 * It contains one public method:
 *
 * send(String method, Navajo message, boolean useCache)
 *
 * where, - method contains the name of the Navajo service.
 *        - message contains the Navajo document. If message=null an empty document is send to
 *          the Navajo server. A newly received document is appended to the input document (message).
 *        - useCache determines whether the received document should be cached. Note that only
 *          newly received messages and methods are cached. Any subsequent call to the same method
 *          will use the corresponding document that is stored in the cache.
 *          Documents that contain user-specific data should in general NOT be cached, however
 *          the programmer is responsible for this.
 *
 * A property file, "navajoagent", is used to set specific properties:
 * - username/password, to connect to Navajo server.
 * - navajoServer, contains the URL of the Navajo server.
 * - cachePath, contains the path where the cached documents should be stored.
 * - enableCache, specifies whether the cache should be used (yes or no).
 * - ssl, specified whether secure socket communications should be used (yes or no). (NOT YET IMPLEMENTED)
 */

public class NavajoAgent extends NavajoClient {

  private String username = "";
  private String password = "";
  private String navajoServer = "";
  private String cachePath = "";
  private boolean enableCache = false;
  private boolean enableHttps = false;
  private String keystore = "";
  private String passphrase = "";

  private HttpServletRequest request = null;

  private ResourceBundle navajoAgentProperties = null;

  public NavajoAgent(ResourceBundle navajoAgentProperties) {
    super("");

    //navajoAgentProperties = ResourceBundle.getBundle("navajoagent");

    // Username and password could be hardcoded!
    username = navajoAgentProperties.getString("navajo.username");
    password = navajoAgentProperties.getString("navajo.password");
    navajoServer = navajoAgentProperties.getString("navajo.server");
    cachePath = navajoAgentProperties.getString("tml.cache.path");
    enableCache = (navajoAgentProperties.getString("tml.cache.enable").equals("yes"));
    enableHttps = (navajoAgentProperties.getString("navajo.security.https").equals("yes"));
    keystore = navajoAgentProperties.getString("navajo.security.keystore");
    passphrase = navajoAgentProperties.getString("navajo.security.passphrase");

    Util.debugLog("Navajo agent: ");
    Util.debugLog("username: " + username);
    Util.debugLog("password: " + password);
    Util.debugLog("navajoServer: " + navajoServer);
    Util.debugLog("cachePath: " + cachePath);
    Util.debugLog("enableCache: " + enableCache);
    Util.debugLog("secure: " + enableHttps);
    Util.debugLog("keystore: " + keystore);
    Util.debugLog("passphrase: " + passphrase);

  }

  private boolean readFromCache(String method, Navajo message) {

    FileInputStream input = null;
    Document doc = null;

    try {
      input = new FileInputStream( new File(cachePath+method+".xml") );

      doc = XMLDocumentUtils.createDocument( input, false );

      message.appendDocBuffer(doc);
      Util.debugLog("!READ " + method + ".xml FROM CACHE!");
    } catch (Exception ioe) {
      return false;
    }

    return true;
  }

  private boolean writeToCache(String method, Navajo message) throws NavajoException {

    Navajo outMessage = null;

    try {
      // Get all newly received messages and methods.
      ArrayList messages = message.getCurrentMessages();
      ArrayList methods = message.getCurrentActions();

      Util.debugLog("Received messages: " + messages.size());
      Util.debugLog("Received methods:  " + methods.size());

      FileWriter file = new FileWriter(cachePath+method+".xml");

      outMessage = new Navajo();
      System.out.println("New document: " + outMessage.getMessageBuffer().getOwnerDocument());
      System.out.println("Previous  document: " + message.getMessageBuffer().getOwnerDocument());

      // Write all newly received messages.
      for (int i = 0; i < messages.size(); i++) {
        Util.debugLog("Message name: " + ((Message) messages.get(i)).getName());
        System.out.println("Previous Owner document = " + ((Message) messages.get(i)).ref.getOwnerDocument());
        Message msg = message.copyMessage((Message) messages.get(i), outMessage);
        System.out.println("New Owner document = " + msg.ref.getOwnerDocument());
        outMessage.addMessage(msg);
      }

      // Write all newly received methods.
      for (int i = 0; i < methods.size(); i++) {
        Method mthd = message.copyMethod((Method) methods.get(i), outMessage);
        outMessage.addMethod(mthd);
      }

      XMLDocumentUtils.toXML( outMessage.getMessageBuffer(), null, null, new StreamResult(file) );

      Util.debugLog("!WROTE " + method + ".xml TO CACHE!");

    } catch (IOException ioe) {
      Util.debugLog("COULD NOT WRITE TML FILE TO CACHE");
      return false;
    }

    return true;

  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  protected HttpServletRequest getRequest() {
    return this.request;
  }

  /**
   * Call a method a the Navajo server using an input Navajo document message.
   * After the calling the method, the Navajo document message is appended with
   * the received messages and methods. Messages and methods with the same name are
   * overwritten by the new ones.
   * Optionally the document cache can be used (if it is enabled) by setting the
   * useCache flag. Any subsequent access to the same method will use the cached
   * Navajo document instead.
   * The cached Navajo document can be refreshed if useCache is set to false.
   *
   * A ClientException is thrown whenever there went something wrong with the
   * Navajo communication or if illegal or invalid documents were sent.
   */

  /**
   * Default unstripped send.
   */
  public void send(String method, Navajo message, boolean useCache) throws ClientException, NavajoException {
    send(method, message, useCache, false);
  }

  public void send(String method, Navajo message, boolean useCache, boolean stripped)
                    throws ClientException, NavajoException
  {

    boolean foundInCache = false;
    Message error = null;

    useCache = useCache && enableCache;

    if (message == null)
      message = new Navajo();

    // Check for old error messages and remove them.
    //try {
      //Util.debugLog("BEFORE error removal: ");
      //message.getMessageBuffer().write(System.out);
      error = message.getMessage("error");
      if (error != null)
        message.removeMessage("error");
      //Util.debugLog("AFTER error removal: ");
      //message.getMessageBuffer().write(System.out);
    //} catch(IOException ioe) {}

    try {
      // If useCache=false or method definition is not found in cache, go to Navajo server.
      // else use file in cache.
      if (!useCache || !(foundInCache = readFromCache(method, message)))
        doMethod(method, username, password, message, navajoServer, enableHttps, keystore, passphrase, this.request, stripped);

      // Write the newly received messages and methods to the cache if it is enabled
      // and useCache is set to true.
      if (useCache && !foundInCache)
        writeToCache(method, message);

    } catch (NavajoException te) {
      throw new ClientException(2, 1, te.getMessage());
    }

    error = message.getMessage("error");
    if (error != null) {
      int level = Integer.parseInt(error.getProperty("level").getValue());
      int code = Integer.parseInt(error.getProperty("code").getValue());
      String messageString = error.getProperty("message").getValue();
      throw new ClientException(level, code, messageString);
    }

  }
}