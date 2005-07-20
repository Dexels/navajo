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

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoSocketClient extends ClientQueueImpl {
    
    private Socket connection = null;
    
    public BufferedInputStream doTransaction(String name, Navajo d, boolean useCompression) throws IOException, ClientException, NavajoException, javax.net.ssl.SSLHandshakeException {
        if (connection==null) {
            initialize();
        }
        try {
            return doTransaction(connection, name, d, useCompression);
        } catch (Throwable e) {
            System.err.println("// TRANSACTION FAILED. SINGULAR RETRY");
            e.printStackTrace();
            initialize();
            return doTransaction(connection, name, d, useCompression);
        }
        
//        return null;
    }
    
    
    
    public BufferedInputStream doTransaction(Socket con, String name, Navajo d, boolean useCompression) throws IOException, ClientException, NavajoException {
        long timeStamp = System.currentTimeMillis();


        	//TODO HACKCKCKCKC
        useCompression = false;
        
        
        // Verstuur bericht
        if (useCompression) {
               java.util.zip.GZIPOutputStream out = new java.util.zip.GZIPOutputStream(con.getOutputStream());
          d.write(out, condensed, d.getHeader().getRPCName());
//          out.close();
          long tt = System.currentTimeMillis() - timeStamp;
          System.err.println("Sending request took: " + tt + " millisec");
        }
        else {
          try {
            OutputStream out = con.getOutputStream();
            BufferedOutputStream bout = new BufferedOutputStream(out);
            d.write(bout, condensed, d.getHeader().getRPCName());
//            out.write("\n\0".getBytes());
            bout.flush();
//            bout.
            long tt = System.currentTimeMillis() - timeStamp;
            System.err.println("Sending request took: " + tt + " millisec");
            timeStamp = System.currentTimeMillis();
//            out.close();
          }
          catch (java.net.NoRouteToHostException nrthe) {
            throw new ClientException( -1, 20, "Could not connect to URI: " + name + ", check your connection");
          }
//          catch (java.net.SocketException se) {
//            se.printStackTrace();
//            throw new ClientException( -1, 21, "Could not connect to network, check your connection");
//          }
        }
        // Lees bericht
        BufferedInputStream in = null;
         if (useCompression) {
          java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(con.getInputStream());
          in = new BufferedInputStream(unzip);
        }
        else {
          in = new BufferedInputStream(con.getInputStream());
        }
        //long tt = System.currentTimeMillis() - timeStamp;
        //System.err.println("Executing script took: " + tt + " millisec");
        //timeStamp = System.currentTimeMillis();
         connection = null;
        return in;
        
    }

    /**
     * @throws IOException
     * @throws UnknownHostException
     * 
     */
    private void initialize() throws UnknownHostException, IOException {
        StringTokenizer st = new StringTokenizer(getServerUrl(),":");
        String host = st.nextToken();
        int port = Integer.parseInt(st.nextToken());
        System.err.println("Connecting to host: "+host+" at port: "+port);
        connection = new Socket(host,port);
    }
    
    public static void main(String[] args) throws Exception {
        System.setProperty("com.dexels.navajo.DocumentImplementation",
        "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

        NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null);
        
        Navajo n = NavajoFactory.getInstance().createNavajo();
        Message m = NavajoFactory.getInstance().createMessage(n, "AAP");
        Property pp = NavajoFactory.getInstance().createProperty(n, "bladie", Property.STRING_PROPERTY, "aap", 10, "aaaaapie", Property.DIR_IN,null);
        n.addMessage(m);
        m.addProperty(pp);
        NavajoClientFactory.getClient().setServerUrl("bananus:10000");
        NavajoClientFactory.getClient().setUsername("ROOT");
        NavajoClientFactory.getClient().setPassword("grobbebol");
        Navajo res = NavajoClientFactory.getClient().doSimpleSend(n, "club/InitUpdateClub");
        res.write(System.err);
        System.err.println("And again...");
        res = NavajoClientFactory.getClient().doSimpleSend(n, "InitUpdateMember");
        res.write(System.err); 
//        System.err.println("And once more...");
//        res = NavajoClientFactory.getClient().doSimpleSend(n, "InitUpdateMember");
//        res.write(System.err);      
//        System.err.println("Sleeping for 25 sec:");
//        Thread.sleep(25000);
//        System.err.println("And one last time...");
//        res = NavajoClientFactory.getClient().doSimpleSend(n, "InitUpdateMember");
//        res.write(System.err);      
    }
}
