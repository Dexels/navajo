package com.dexels.navajo.mapping;

import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import java.util.HashMap;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * AsyncMappable objects seperate the request and response part of the object's attributes explicitly by asynchronous action.
 *
 * Navajo services that access AsyncMappable objects ALWAYS need two service requests:
 * 1. Initiate "Object request" request.
 * 2. Initiate "Object response" request.
 *
 * Though these two requests are encapsulated in a single Navascript in order to unite them.
 *
 * The semantic construct to support this is as follows:
 *
 * <map name="unique name, identifying the object instance" object="com.dexels.navajo.adapter.AsyncClass">
 *   <request>
 *    (Sychronous call to object methods)
 *   </request> (-> the runtime will automatically call the method runThread() )
 *   <response> (-> the runtime will call isFinished(), if isFinished returns true, the methods inside the <response> will be executed, if false
 *               a not_finished notification will be send back. If an Exception occured during thread execution, the exception will be thrown
 *               upon calling isFinished(), the runtime will call, as normally, the method kill() ).
 *   </response)
 * </map> (-> the runtime will automatically call the method store() )
 *
 * If a service contains async object, the server will have to supply the client with proper callback pointers for subsequent requests.
 * These callback pointer will be returned as follows:
 * <header>
 *   <callback>
 *     <object name="unique name" ref="pointer" finished="false"/>
 *     <object name="unique name" ref="pointer" finished="false"/>
 *   </callback>
 * </header>
 *
 * The client will need to send the <callback> back in it's subsequent requests.
 *
 */

public abstract class AsyncMappable implements Mappable {

  private boolean isFinished = false;
  private Exception caught = null;
  private long startTime;
  private long lastAccess;
  private String name;
  private String pointer;
  private boolean running = false;

  class RequestThread extends Thread {

    private AsyncMappable parent;

    public RequestThread(AsyncMappable parent) {
      this.parent = parent;
    }

    public void run() {
      try {
        parent.run();
      } catch (Exception e) {
        parent.setException(e);
      }
      System.out.println("Calling setIsFinished()");
      parent.setIsFinished();
    }
  }

  public abstract void kill();
  public abstract void store() throws MappableException, UserException;
  public abstract void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException;

  protected void setException(Exception e) {
    caught = e;
  }


  public void afterReload(String name, String pointer) {
    this.name = name;
    this.pointer = pointer;
  }

  public void runThread() {
    this.lastAccess = System.currentTimeMillis();
    if (!running) {
      startTime = System.currentTimeMillis();
      System.out.println("STARTED RUNTHREAD ON: " + startTime);
      new RequestThread(this).start();
      running = true;
    }
  }
  /**
   * Run the request thread.
   * This method should implement all the stuff that needs to be done asynchronously.
   */
  public abstract void run() throws UserException;

  public synchronized boolean isFinished(Navajo inMessage, Access access) throws Exception {

    Header h = inMessage.getHeader();
    if (h == null) {
        if (access != null)
          h = NavajoFactory.getInstance().createHeader(inMessage, access.rpcName, access.rpcUser, "", -1);
        else
          h = NavajoFactory.getInstance().createHeader(inMessage, "", "", "", -1);
        inMessage.addHeader(h);
    }
    System.out.println(inMessage.toString());
    if (isFinished) {
      System.out.println("THREAD IS FINISHED...");
      if (caught != null)
        throw caught;
      h.setCallBack(this.name, this.pointer, true);
    } else
      h.setCallBack(this.name, this.pointer, false);

    return isFinished;
  }

  protected void setIsFinished() {
    isFinished = true;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getLastAccess() {
    return this.lastAccess;
  }
}