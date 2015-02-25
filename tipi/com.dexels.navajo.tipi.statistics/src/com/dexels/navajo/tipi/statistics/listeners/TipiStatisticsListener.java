package com.dexels.navajo.tipi.statistics.listeners;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.listener.http.BaseNavajoServlet;
import com.jcraft.jzlib.DeflaterOutputStream;
import com.jcraft.jzlib.InflaterInputStream;

public class TipiStatisticsListener extends HttpServlet {

    private static final long serialVersionUID = -5725939083934432326L;
    private Navajo responseNavajo;
    
    
    public TipiStatisticsListener() {
        responseNavajo = NavajoFactory.getInstance().createNavajo();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // NO GET!

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String sendEncoding = request.getHeader("Accept-Encoding");
        String recvEncoding = request.getHeader("Content-Encoding");

        // Navajo input = TmlHttpServlet.constructFromRequest(request);
        Navajo res = getNavajo(request ,sendEncoding);
        if (res.getMessage("event") != null) {
            System.err.println(res);
        } else if (res.getMessage("instantiate") != null) {
            System.err.println(res);
        } else {
            // ignore
        }
       
        
        response.setContentType("text/xml; charset=UTF-8");
        BufferedWriter out = null;

        if (recvEncoding != null && recvEncoding.equals(BaseNavajoServlet.COMPRESS_JZLIB)) {
            response.setHeader("Content-Encoding", BaseNavajoServlet.COMPRESS_JZLIB);
            out = new BufferedWriter(new OutputStreamWriter(
                    new DeflaterOutputStream(response.getOutputStream()), "UTF-8"));
        } else if (recvEncoding != null
                && recvEncoding.equals(BaseNavajoServlet.COMPRESS_GZIP)) {
            response.setHeader("Content-Encoding", BaseNavajoServlet.COMPRESS_GZIP);
            out = new BufferedWriter(new OutputStreamWriter(
                    new java.util.zip.GZIPOutputStream(
                            response.getOutputStream()), "UTF-8"));
        } else {
            out = new BufferedWriter(response.getWriter());
        }

        responseNavajo.write(out);
        out.flush();
        out.close();

    }

    private Navajo getNavajo(HttpServletRequest request, String sendEncoding) {
        
     
        BufferedReader r = null;
        Navajo in = null;
        try {

            if (sendEncoding != null && sendEncoding.equals(BaseNavajoServlet.COMPRESS_JZLIB)) {
                r = new BufferedReader(new java.io.InputStreamReader(new InflaterInputStream(request.getInputStream()),
                        "UTF-8"));
            } else if (sendEncoding != null && sendEncoding.equals(BaseNavajoServlet.COMPRESS_GZIP)) {
                r = new BufferedReader(new java.io.InputStreamReader(new java.util.zip.GZIPInputStream(
                        request.getInputStream()), "UTF-8"));
            } else {
                r = new BufferedReader(request.getReader());
            }
            in = NavajoFactory.getInstance().createNavajo(r);
            r.close();
            r = null;
        } catch (Exception e) {
            
        } 
        return in;
    }

}
