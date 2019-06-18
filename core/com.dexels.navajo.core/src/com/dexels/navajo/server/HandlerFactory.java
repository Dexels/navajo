package com.dexels.navajo.server;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.script.api.Access;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerFactory {
    
	private static final Logger logger = LoggerFactory.getLogger(HandlerFactory.class);
	
	private static final String defaultIdentifier = "default";
	
    private Map<String, ServiceHandler> instances = new HashMap<>();

    public void addHandler(ServiceHandler handler) {
        logger.info("Added servicehandler {}", handler.getIdentifier());
        instances.put(handler.getIdentifier(), handler);
    }

    public void removeHandler(ServiceHandler handler) {
        logger.info("Removed servicehandler {}", handler.getIdentifier());
        instances.remove(handler.getIdentifier());
    }
    
    public ServiceHandler getDefaultServiceHandler()
    {
    	if (instances.containsKey( defaultIdentifier ) )
    	{
    		return instances.get( defaultIdentifier );
    	}
    	else
    	{
    		throw new RuntimeException("Missing default serviceHandler!");
    	}
    }

    public ServiceHandler createHandler(NavajoConfigInterface navajoConfig, Access access, Boolean simulationMode) {
		ServiceHandler sh = null;
	    try {
	    	
		    if (simulationMode) {
		    	sh = instances.getOrDefault( "stress", getDefaultServiceHandler() );
		    } else {
		    	// check if birt is requested
		    	Header h = access.getInDoc().getHeader();
		    	if ( h.getHeaderAttribute("GenerateBirt") != null && h.getHeaderAttribute("GenerateBirt").equals("true") )
		    	{
			    	sh = instances.getOrDefault( "birt", getDefaultServiceHandler() );
		    	}
		    	else
		    	{
			    	sh = instances.getOrDefault( "default", getDefaultServiceHandler() );
		    	}
		    }
	    }
	    catch( Exception e )
	    {
	    	logger.error("Problem finding handler, falling back on default: ", e);
	    	sh = getDefaultServiceHandler();
	    }
	    sh.setNavajoConfig( navajoConfig );
		return sh;
	}
	
}
