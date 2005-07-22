/*
 * Created on Jul 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.client.socket;

import java.io.*;
import java.net.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.client.impl.*;
import com.dexels.navajo.server.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoSocketListener implements Runnable {
    private final int port;
    private final Thread acceptThread = new Thread(this);
    private final ServerSocket myServerSocket;
//    private final Dispatcher myDispatcher;
    private int connectionsCreated = 0;
    private final DirectClientImpl myClient;
    private final SocketThreadPool myThreadPool;
    public NavajoSocketListener(DirectClientImpl ci, int port) throws IOException {
        System.setProperty("com.dexels.navajo.DocumentImplementation",
        "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

        this.port = port;
        myClient = ci;
        myServerSocket = new ServerSocket(port);
        myThreadPool = new SocketThreadPool(this,5);
        acceptThread.start();
    }
     public void run() {
         // Will start with a naive thread-creating mechanism.
         while (true) {
            try {
                Socket s = myServerSocket.accept();
                handle(s);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
     }
      private void handle(Socket s) {
          System.err.println("Creating handler..");
          myClient.clearClassCache();
          SocketConnection sc = new SocketConnection("ConnectionNumber"+(connectionsCreated++), myClient,s,this);
          myThreadPool.enqueueExecutable(sc);
      }
    }

