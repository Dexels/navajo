package com.dexels.navajo.dev.console;


import org.apache.felix.service.command.CommandSession;

public class ListenerStore_isRegisteredService extends ListenerStoreCommand {

	public void isregistered(CommandSession session) {
	    isregistered(session, null);
	}
	
	public void isregistered(CommandSession session, String filter) {
	    if (filter == null || filter.equals("")) {
	        return;
	    }
	    boolean isRegister = getListenerStore().isRegisteredWebservice(filter);
        Integer count = getListenerStore().getRegisteredWebserviceCount(filter);
        session.getConsole().println(filter + ": " + isRegister + " (" + count +")");
	    
	}

	@Override
	public String showUsage() {
		return "navajo:isregistered <webservice>";
	}
}
