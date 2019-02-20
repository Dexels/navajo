/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import com.dexels.navajo.document.BinaryOpenerFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiOpenBrowser extends TipiAction {

	private static final long serialVersionUID = -2652162769648210598L;

	
	@Override
	protected void execute(TipiEvent event) throws TipiBreakException,
			TipiException {
		Operand url = getEvaluatedParameter("url", event);
		String urlVal = (String) url.value;
		BinaryOpenerFactory.getInstance().browse(urlVal);
	}

}
