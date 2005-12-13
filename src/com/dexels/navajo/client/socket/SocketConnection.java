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
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SocketConnection implements Runnable {
    private final NavajoSocketListener mySocketListener;
    private final Socket mySocket;
//    private final Thread myThread;
    private final ClientInterface myClient;
    private final String myName;
    public SocketConnection(String name, ClientInterface d, Socket s, NavajoSocketListener nsl) {
        mySocket = s;
        mySocketListener = nsl;
        myName = name;
        myClient = d;
//        myThread = new Thread(this,name+"_Thread");
//        myThread.start();
    }

    public void run() {
        InputStream in = null;
        OutputStream out = null;
        Writer outWriter = null;
        try {
            in = mySocket.getInputStream();
            out= mySocket.getOutputStream();
            outWriter = new OutputStreamWriter(out);
        } catch (IOException e1) {
             e1.printStackTrace();
        }
        
        try {
            while (true) {
//                Dispatcher.doClearScriptCache();
                Dispatcher.doClearCache();

                Navajo n = NavajoFactory.getInstance().createNavajo(in);
                String service = n.getHeader().getRPCName();
                String username = n.getHeader().getRPCUser();
                String password = n.getHeader().getRPCPassword();
                Navajo outNavajo = myClient.doSimpleSend(n, null, service, username, password, -1);
                if (n == null) {
                    System.err.println("Read fail or something?");
                    continue;
                }
                outNavajo.write(outWriter);
                outWriter.write("\n");
                outWriter.flush();
//                System.err.println("Socketconnection: " + myName + " not recycling");
                return;
            }
        } catch (Throwable e) {
            System.err.println("boioioing thread dying. Whatever");
            e.printStackTrace();
        } finally {
            try {
                mySocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
