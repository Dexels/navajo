package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSetHTTPS
    extends TipiAction {
  public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
	  // TODO Add support for multi-servers
	    final Operand passphrase = getEvaluatedParameter("passphrase", event);
	    final Operand keystore = getEvaluatedParameter("keystore", event);
	    try {
	    	myContext.setHTTPS(""+passphrase.value,(Binary)keystore.value);
		} catch (ClientException e) {
			throw new TipiException(e);
		}
  }
}