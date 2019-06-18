package com.dexels.navajo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.script.api.UserException;

public class StressTestHandler  extends ServiceHandler {
    private static final Logger logger = LoggerFactory.getLogger(StressTestHandler.class);

	@Override
	public String getIdentifier() {
		return "stress";
	}

	@Override
	public void setNavajoConfig(NavajoConfigInterface navajoConfig) {
		// Not interested
		
	}

	@Override
    public Navajo doService( Access a )
            throws NavajoException, UserException, SystemException, AuthorizationException, NavajoDoneException {
        if (a.getInDoc().getMessage("stress") == null || a.getInDoc().getMessage("stress").getProperty("sleep") == null) {
            throw new UserException(-1, "Stresstest mode but no sleep parameter set!");
        }
        Long sleep = (Long) a.getInDoc().getMessage("stress").getProperty("sleep").getTypedValue();
        logger.debug("Running: {}; Going to sleep {}", a.getRpcName(), sleep);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
        }
        
        return NavajoFactory.getInstance().createNavajo();
    }

    @Override
    public boolean needsRecompile( Access a ) throws Exception {
        return false;
    }

}
