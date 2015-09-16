package com.dexels.navajo.dev.console;

import com.dexels.navajo.scheduler.ListenerStore;

public abstract class ListenerStoreCommand extends ConsoleCommand {
	
    public ListenerStore getListenerStore() {
        return ListenerStore.getInstance();
    }

}
