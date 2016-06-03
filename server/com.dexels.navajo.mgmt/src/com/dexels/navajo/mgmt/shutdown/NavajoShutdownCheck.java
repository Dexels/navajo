package com.dexels.navajo.mgmt.shutdown;

import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.server.mgmt.api.ServerShutdownCheck;

public class NavajoShutdownCheck implements ServerShutdownCheck {

    private DispatcherInterface dispatcherInterface;

    public void setDispatcherInterface(DispatcherInterface dispatcherInterface) {
        this.dispatcherInterface = dispatcherInterface;
    }

    public void clearDispatcherInterface(DispatcherInterface dispatcherInterface) {
        this.dispatcherInterface = null;
    }

    @Override
    public boolean allowShutdown() {
        int users = getUserCount();
        int async = getAsyncCount();

        return users + async == 0;
    }

    private int getUserCount() {
        return dispatcherInterface.getAccessSet().size();
    }

    private int getAsyncCount() {
        return AsyncStore.getInstance().objectStore.size();
    }

}
