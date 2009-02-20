package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.jnlp.BasicService;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.components.swingimpl.jnlp.WebStartProxy;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiInjectJnlpCache extends TipiAction {
	public TipiInjectJnlpCache() {
	}

	protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
		WebStartProxy.injectJnlpCache();
//		((SwingTipiContext)myContext).
		
	}

	

}
