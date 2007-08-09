/*
 * Created on Jul 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.client;

import java.io.*;
import java.net.*;
import java.util.*;

import com.dexels.navajo.client.queueimpl.*;
import com.dexels.navajo.document.*;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoSocketClient extends ClientQueueImpl {
    
//    private Socket connection = null;
    private static int inoutCount = 0;

    private static synchronized void in() {
        inoutCount++;
    }
    private static synchronized void out() {
        inoutCount--;
    }

    protected InputStream doTransaction(String name, Navajo d, boolean useCompression, boolean forcePreparseProxy) throws IOException, ClientException, NavajoException, javax.net.ssl.SSLHandshakeException {
        Socket connection = null;
         if (connection==null) {
            connection = initialize();
        }
        InputStream bi = null;
        try {
            bi = doTransaction(connection, name, d, useCompression);
        } catch (Throwable e) {
            System.err.println("// TRANSACTION FAILED. SINGULAR RETRY");
            e.printStackTrace();
            initialize();
            bi = doTransaction(connection, name, d, useCompression);
        }
         return bi;
//        return null;
    }
    
    
    
//    public BufferedInputStream doTransactionOld(Socket con, String name, Navajo d, boolean useCompression) throws IOException, ClientException, NavajoException {
//        if (useCompression) {
//               java.util.zip.GZIPOutputStream out = new java.util.zip.GZIPOutputStream(con.getOutputStream());
//          d.write(out, condensed, d.getHeader().getRPCName());
//        }
//        else {
//          try {
//            OutputStream out = con.getOutputStream();
//            BufferedOutputStream bout = new BufferedOutputStream(out);
//            d.write(bout, condensed, d.getHeader().getRPCName());
//            bout.flush();
//          }
//          catch (java.net.NoRouteToHostException nrthe) {
//            throw new ClientException( -1, 20, "Could not connect to URI: " + name + ", check your connection");
//          }
//        }
//        BufferedInputStream in = null;
//         if (useCompression) {
//          java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(con.getInputStream());
//          in = new BufferedInputStream(unzip);
//        }
//        else {
//          in = new BufferedInputStream(con.getInputStream());
//        }
//        return in;
//        
//    }
    

    public InputStream doTransaction(Socket con, String name, Navajo d, boolean useCompression) throws IOException, ClientException, NavajoException {
    	   // Send message
    
    	BufferedWriter os = null;
    	try {
    		os = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
    		d.write(os, condensed, d.getHeader().getRPCName());    	
    		System.err.println("Wrote socket client request.");
    	} finally {
    		if ( os != null ) {
    			try {
    				os.flush();
//    				os.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    
    // Lees bericht
//    BufferedInputStream in = null;
//    if (useCompression) {
//      in = new BufferedInputStream(new java.util.zip.GZIPInputStream(con.getInputStream()));
//    }
//    else {
//      in = new BufferedInputStream(con.getInputStream());
//    }
    
     	return con.getInputStream();
    
  }    

    /**
     * @throws IOException
     * @throws UnknownHostException
     * 
     */
    private Socket initialize() throws UnknownHostException, IOException {
        StringTokenizer st = new StringTokenizer(getServerUrl(),":");
        String host = st.nextToken();
        int port = Integer.parseInt(st.nextToken());
//        System.err.println("Connecting to host: "+host+" at port: "+port);
        Socket connection = new Socket(host,port);
        return connection;
    }
    
    
    private static ThreadGroup tg = new ThreadGroup("Makaak");
    public static void main(String[] args) throws Exception {
        System.setProperty("com.dexels.navajo.DocumentImplementation",
        "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

        
//        Navajo n = NavajoFactory.getInstance().createNavajo();
//        Message m = NavajoFactory.getInstance().createMessage(n, "AAP");
//        Property pp = NavajoFactory.getInstance().createProperty(n, "bladie", Property.STRING_PROPERTY, "aap", 10, "aaaaapie", Property.DIR_IN,null);
//        n.addMessage(m);
//        m.addProperty(pp);

        NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null,null);
        NavajoClientFactory.getClient().setServerUrl("bananus:10000");
        NavajoClientFactory.getClient().setUsername("ROOT");
        NavajoClientFactory.getClient().setPassword("");
        
//        NavajoClientFactory.getClient().setUsername("ROOT");
//        NavajoClientFactory.getClient().setPassword("");
//        NavajoClientFactory.getClient().setServerUrl("ficus:3000/sportlink/knvb/servlet/Postman");
        
//        Navajo res = NavajoClientFactory.getClient().doSimpleSend(n, "club/InitUpdateClub");
//        res.write(System.err);
//        System.err.println("And again...");
//        res = NavajoClientFactory.getClient().doSimpleSend(n, "InitUpdateMember");
//        res.write(System.err); 

        System.err.println("Starting sequential bombardment...");

        bombardSequential("club/InitUpdateClub", 400);
        bombardSimultaneous("club/InitUpdateClub", 400);
        System.err.println("InoutCount: "+inoutCount);
    }

    private static void bombardSequential(final String script, int count) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
              performScript(script);
        }
        long moreTime = System.currentTimeMillis() - time;
        System.err.println("\n      ************* bombardSequential took: "+moreTime+" millis\n");
    }

    private static void bombardSimultaneous(final String script, int count) {
        final List l = Collections.synchronizedList(new ArrayList());
        for (int i = 0; i < count; i++) {
            Thread t = new Thread(tg,"AAP#"+i) {
                public void run() {
                    in();

                    try {
                         performScript(script);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    } finally {
                       
                        try {
                            l.remove(Thread.currentThread());
                        } catch (Throwable e1) {
                            e1.printStackTrace();
                        }
                        out();
                    }
                 }
            };
            l.add(t);
        }
        long time = System.currentTimeMillis();
        for (int i = l.size()-1; i >=0; i--) {
            Thread t = (Thread)l.get(i);
            t.start();
        }
        int last = Integer.MAX_VALUE;
        while (l.size()>0) {
            System.err.println("Threads remaining: "+l.size()+" size: "+tg.activeCount()+" queue: "+NavajoClientFactory.getClient().getQueueSize());
              try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (last==l.size()) {
                System.err.println("No change! beware!");
                System.err.println("InoutCount: "+inoutCount);
               if (l.size()>0) {
                    Thread t = (Thread)l.get(0);
                    System.err.println("Thread is alive: "+ t.isAlive()+" isInter: "+t.isInterrupted()+" val: "+t.toString());
                }
            }
           last = l.size();
            
        }
        long moreTime = System.currentTimeMillis() - time;
        System.err.println("\n   **********bombardSimultaneous took: "+moreTime +" millis\n");
    }

    private synchronized static void performScript(String script) {
        try {
            Navajo n = NavajoFactory.getInstance().createNavajo();
            NavajoClientFactory.getClient().doSimpleSend(n, script);
            
        } catch (ClientException e) {
             e.printStackTrace();
        }
       
    }
}
