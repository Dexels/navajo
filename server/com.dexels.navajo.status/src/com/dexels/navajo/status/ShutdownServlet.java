package com.dexels.navajo.status;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;

public class ShutdownServlet extends HttpServlet {
    private static final long serialVersionUID = -6253074545952495770L;

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
        String check = req.getParameter("check");
        String res = "failed";
        if (secret.equals(check)) {
            NavajoShutdown shutter = new NavajoShutdown(bundleContext,  dispatcher, tribeManager);
            new Thread(shutter).start();
            res = "ok";
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
