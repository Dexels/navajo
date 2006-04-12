package com.dexels.navajo.server.listener.http;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.*;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClient;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class LoadBalancer extends HttpServlet {

  private ClientInterface client = null;

  public void init() throws ServletException {
    client = (NavajoClient) NavajoClientFactory.createDefaultClient();
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    try {

      long start = System.currentTimeMillis();

           Navajo in = null;

           String sendEncoding = request.getHeader("Accept-Encoding");
           String recvEncoding = request.getHeader("Content-Encoding");

           boolean useSendCompression = ((sendEncoding != null) && (sendEncoding.indexOf("zip") != -1));
           boolean useRecvCompression = ((recvEncoding != null) && (recvEncoding.indexOf("zip") != -1));

           if (useRecvCompression) {
             java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(request.getInputStream());
             in = NavajoFactory.getInstance().createNavajo(new BufferedInputStream(unzip));
           } else {
             in = NavajoFactory.getInstance().createNavajo(new BufferedInputStream(request.getInputStream()));
           }

           if (in == null) {
             throw new ServletException("Invalid request.");
           }

           Header header = in.getHeader();
           if (header == null) {
             throw new ServletException("Empty Navajo header.");
           }

           System.err.println("PROXY TOOK " + ((System.currentTimeMillis() - start) / 1000.0) + " secs.");

           response.sendRedirect("http://www.dexels.nl/sportlink/knvb/servlet/Postman");
           return;

    } catch (Exception e) {

    }
  }

  //Clean up resources
  public void destroy() {
  }
}