/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
