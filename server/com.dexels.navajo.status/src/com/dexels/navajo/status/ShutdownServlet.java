package com.dexels.navajo.status;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;
import com.dexels.navajo.server.enterprise.workflow.WorkFlowManagerInterface;

public class ShutdownServlet extends HttpServlet {
    private static final long serialVersionUID = -6253074545952495770L;

    private DispatcherInterface dispatcher;
    private TribeManagerInterface tribeManager;
    private BundleContext bundleContext;
    private String secret = null;

    public void Activate(BundleContext bc, Map<String, Object> settings) {
        this.bundleContext = bc;
        this.secret = (String) settings.get("secret");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NavajoShutdown shutter = new NavajoShutdown(bundleContext,  dispatcher, tribeManager);
        new Thread(shutter).start();
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
