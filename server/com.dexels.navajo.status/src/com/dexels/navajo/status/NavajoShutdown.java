package com.dexels.navajo.status;

import java.util.Date;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;

public class NavajoShutdown implements Runnable{
    private static boolean shutdownInProgress = false;
    private final static Logger logger = LoggerFactory.getLogger(NavajoShutdown.class);

    private final DispatcherInterface dispatcher;
    private final TribeManagerInterface tribeManagerInterface;
    private BundleContext bundlecontext;
    
    private int timeout = -1;
    private Long startedShutdownAt;
    
    
    public NavajoShutdown(BundleContext bundlecontext, DispatcherInterface dispatcher, TribeManagerInterface tribeInterface) {
        this.bundlecontext = bundlecontext;
        this.dispatcher = dispatcher;
        this.tribeManagerInterface = tribeInterface;
    }
    
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
    public static boolean shutdownInProgress() {
        return shutdownInProgress;
    }
    
    public static void cancelShutdownInProgress() {
        logger.info("Attempting to cancel shutdown");
        shutdownInProgress = false;
    }
    
    @Override
    public void run() {
        NavajoShutdown.shutdownInProgress = true;
    
        logger.warn("Navajo shutdown scheduled!");
        boolean expired = false;
        startedShutdownAt = new Date().getTime();
        // At this point we can assume no new requests will be coming in
        
        while (shutdownInProgress) {
            int users = getUserCount();
            int async = getAsyncCount();
            
            if (timeout > 0) {
                expired = (new Date().getTime() - startedShutdownAt) > timeout;
            }
            
            if (users + async == 0 || expired) {
                 startSystemShutdown();
                 return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.warn("Interrupted exception received while sleeping! Going to cancel shutdown");
                NavajoShutdown.shutdownInProgress = false;
            }
        }
    }

    
    private void startSystemShutdown()  {
        logger.warn("Actual shutdown imminent");
        boolean tribeSafe = tribeManagerInterface.tribeIsSafe() && tribeManagerInterface.getMyMembership().isSafe();
        
        
        while (!tribeSafe && shutdownInProgress) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.warn("Interrupted exception received while waiting on Tribe.isSafe! Going to cancel shutdown");
                NavajoShutdown.shutdownInProgress = false;
            }
            tribeSafe = tribeManagerInterface.tribeIsSafe();
        }
        
        // Last chance to stop
        if (!shutdownInProgress) {
            return;
        }
        try {
            bundlecontext.getBundle(0).stop();
        } catch (BundleException e) {
            logger.error("Bundle 0 ( system-bundle) gave a BundleException: ", e);
        }
        
    }

    private int getUserCount() {
        return dispatcher.getAccessSet().size();
    }
    
    private int getAsyncCount() {
        return AsyncStore.getInstance().objectStore.size();
    }
}
  
