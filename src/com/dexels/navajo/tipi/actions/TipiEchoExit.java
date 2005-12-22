package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.components.echoimpl.EchoTipiContext;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

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

public class TipiEchoExit extends TipiAction {
	public TipiEchoExit() {
	}

	public void execute(TipiEvent e) {
	
		((EchoTipiContext) myContext).exit();
	}

}
