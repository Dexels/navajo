package com.dexels.navajo.status;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;

public class ShutdownServlet extends HttpServlet {
    private static final long serialVersionUID = -6253074545952495770L;
    private final static Logger logger = LoggerFactory.getLogger(ShutdownServlet.class);

    private DispatcherInterface dispatcher;
    private TribeManagerInterface tribeManager;
    private BundleContext bundleContext;
    private String secret = null;

    public void activate(BundleContext bc, Map<String, Object> settings) {
        this.bundleContext = bc;
        this.secret = (String) settings.get("secret");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String res = null;
        try {
            String ip = req.getHeader("X-Forwarded-For");
            if (ip == null || ip.equals("")) {
                ip = req.getRemoteAddr();
            }
            
            logger.info("Received Navajo Shutdown request from {}", ip);
            
            String check = req.getParameter("check");
            String timeout = req.getParameter("timeout");
            String cancel = req.getParameter("cancel");
            
            res = "failed";
            if (secret.equals(check)) {
                if (cancel != null && cancel.equals("true")) {
                    NavajoShutdown.cancelShutdownInProgress();
                } else {
                    NavajoShutdown shutter = new NavajoShutdown(bundleContext,  dispatcher, tribeManager);
                    if (timeout != null && !timeout.equals("")) {
                        shutter.setTimeout(Integer.valueOf(timeout));
                    }
                    new Thread(shutter).start();
                }
                
                res = "ok";
            } else {
                logger.warn("Received unauthorized Navajo Shutdown request!");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
           logger.warn("Exception on scheduling shutdown: ", e);
        }
       
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.write(res);
        writer.close();
    }

    public void setDispatcherInterface(DispatcherInterface dispatcherInterface) {
        this.dispatcher = dispatcherInterface;
    }

    public void clearDispatcherInterface(DispatcherInterface dispatcherInterface) {
        this.dispatcher= null;
    }

    public void setTribeManagerInterface(TribeManagerInterface tribeManagerInterface) {
        this.tribeManager = tribeManagerInterface;
    }

    public void clearTribeManagerInterface(TribeManagerInterface tribeManagerInterface) {
        this.tribeManager = null;
    }

  
}
