package com.dexels.navajo.server.resource;

import java.util.HashMap;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.NavajoCompileScriptEvent;

/**
 * This class is used to fetch (cached) ResourceChecker objects. The cached objects are invalidated upon NavajoCompileScript
 * events.
 * 
 * @author arjen
 *
 */
public class ResourceCheckerManager implements NavajoListener {

	private static ResourceCheckerManager myResourceCheckerMngr;
	
	static {
		myResourceCheckerMngr = new ResourceCheckerManager();
		NavajoEventRegistry.getInstance().addListener(NavajoCompileScriptEvent.class, myResourceCheckerMngr);
	}

	private HashMap<String, ResourceChecker> checkedService = new HashMap<String, ResourceChecker>();
	
	public static ResourceCheckerManager getInstance() {
		return myResourceCheckerMngr;
	}
	
	public static void clearInstance() {
		if(myResourceCheckerMngr!=null) {
			NavajoEventRegistry.getInstance().removeListener(NavajoCompileScriptEvent.class, myResourceCheckerMngr);
		}
		myResourceCheckerMngr = null;
	}
	
	/**
	 * Get a (cached) ResourceChecker object for the specified web service.
	 * 
	 * @param webservice
	 * @return
	 */
	public synchronized ResourceChecker getResourceChecker(String webservice, Navajo inMessage) {
		ResourceChecker rc = checkedService.get(webservice);
		if ( rc == null ) {
			rc = new ResourceChecker(webservice);
			checkedService.put(webservice, rc);
		}
		rc.setInMessage(inMessage);
		return rc;
	}
	
	/**
	 * For unit testing purposes.
	 * 
	 * @param webservice
	 * @param c
	 */
	public static void addCheckedService(String webservice, ResourceChecker c) {
		myResourceCheckerMngr.checkedService.put(webservice, c);
	}
	
	/**
 	 * Make sure that when compile script events comes along, corresponding script is removed from the checkedService cache.
	 */
	@Override
	public void onNavajoEvent(NavajoEvent ne) {
		if ( ne instanceof NavajoCompileScriptEvent ) {
			NavajoCompileScriptEvent ncse = (NavajoCompileScriptEvent) ne;
			checkedService.remove(ncse.getWebservice());
		}
	}
}
