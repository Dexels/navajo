package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 * @deprecated
 * 
 */
@Deprecated
public class TipiPerformMethod extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		Operand hostUrlValue = null;
		boolean breakOnError = false;
		long expirationInterval = -1;
		String hostUrl = null;
		String username = null;
		String password = null;
		String keystore = null;
		String keypass = null;
		boolean condenseCheck = true;
		Operand brk = getEvaluatedParameter("breakOnError", event);
		if (brk != null) {
			breakOnError = ((Boolean) brk.value).booleanValue();
		}
		hostUrlValue = getEvaluatedParameter("hostUrl", event);
		Operand usernameValue = getEvaluatedParameter("username", event);
		Operand passwordValue = getEvaluatedParameter("password", event);
		Operand keyStoreOperand = getEvaluatedParameter("keystore", event);
		Operand keyPassOperand = getEvaluatedParameter("keypass", event);

		if (hostUrlValue != null) {

			Object o = hostUrlValue.value;
			hostUrl = o == null ? null : o.toString();
		}

		if (usernameValue != null) {
			Object o = usernameValue.value;
			username = o == null ? null : o.toString();
		}
		if (passwordValue != null) {
			Object o = passwordValue.value;
			password = o == null ? null : o.toString();
		}

		if (keyStoreOperand != null) {
			Object o = keyStoreOperand.value;
			keystore = o == null ? null : o.toString();
		}
		if (keyPassOperand != null) {
			Object o = keyPassOperand.value;
			keypass = o == null ? null : o.toString();
		}

		String destination = "*";
		TipiValue destVal = getParameter("destination");
		if (destVal != null) {
			destination = destVal.getValue();
			if (destination == null) {
				destination = "*";
			}

		}

		Operand expiration = getEvaluatedParameter("expirationInterval", event);
		if (expiration != null) {
			expirationInterval = ((Integer) expiration.value).intValue();
		}

		Operand condense = getEvaluatedParameter("condense", event);
		if (condense != null) {
			condenseCheck = ((Boolean) condense.value).booleanValue();
		}

		// /** @todo REWRITE THIS STRANGE CONSTRUCTION. LOOKS OLD. SHOULD BE
		// MUCH EASIER NOW */
		// TipiValue sourceTipi = getParameter("tipipath");

		Operand method = getEvaluatedParameter("method", event);
		TipiDataComponent evalTipi = null;
		Operand tipi = getEvaluatedParameter("tipipath", event);
		if (tipi != null) {
			evalTipi = (TipiDataComponent) tipi.value;
		}
		NavajoClientFactory.getClient().setCondensed(condenseCheck);
		if (method == null) {
			throw new IllegalArgumentException("Error performing method. Method evaluated to null.");
		}
		setThreadState("waiting");

		if (evalTipi == null) {
			if (myComponent.getNearestNavajo() != null) {
				Navajo n = myComponent.getNearestNavajo();
				myContext.performTipiMethod(null, n, destination, method.value.toString(), breakOnError, event, expirationInterval,
						hostUrl, username, password, keystore, keypass);
			} else {
				myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(), destination, method.value.toString(),
						breakOnError, event, expirationInterval, hostUrl, username, password, keystore, keypass);
			}
			return;
		}
		setThreadState("busy");

		evalTipi.performService(myContext, destination, method.value.toString(), breakOnError, event, expirationInterval, hostUrl,
				username, password, keystore, keypass);

	}
}
