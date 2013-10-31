package com.dexels.navajo.tipi.actions;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.connectors.TipiConnector;
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
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiNewCallService extends TipiAction {

	private static final long serialVersionUID = -6767560777929847564L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiNewCallService.class);
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {

		TipiValue parameter = getParameter("input");
		// String unevaluated = null;

		if (parameter != null) {
			// unevaluated = parameter.getValue();
		}
		Operand serviceOperand = getEvaluatedParameter("service", event);
		Operand inputOperand = getEvaluatedParameter("input", event);
		Operand destination = getEvaluatedParameter("destination", event);
		Operand connector = getEvaluatedParameter("connector", event);
		Object cached = getEvaluatedParameterValue("cached", event);
		// Object silentValue = getEvaluatedParameterValue("silent", event);
		// Boolean silent = (Boolean)silentValue;
		if (connector == null || connector.value == null) {
			oldExecute(event);
			return;
		}
		String destAddress = null;
		if (destination != null) {
			destAddress = (String) destination.value;
		}
		if (serviceOperand == null || serviceOperand.value == null) {
			throw new TipiException(
					"Error in callService action: service parameter missing!");
		}
		String service = (String) serviceOperand.value;

		if (cached != null && cached instanceof Boolean) {
			boolean c = (Boolean) cached;
			if (c) {
				Navajo n = myContext.getNavajo(service);
				if (n != null) {
					logger.info("Returning CACHED service : " + service);
					myContext.loadNavajo(n, service);
					return;
					// return n;
				}
			}
		}

		Navajo input = null;
		if (inputOperand != null) {
			input = (Navajo) inputOperand.value;
		}
		setThreadState("waiting");
		TipiConnector defaultConnector = myContext.getDefaultConnector();
		if (connector.value == null) {
			// long timeStamp = System.currentTimeMillis();
			logger.info("No connector");
			if (defaultConnector == null) {
				throw new IllegalStateException(
						"No default tipi connector found!");
			}
			defaultConnector.doTransaction(input, service);
		} else {
			TipiConnector ttt = myContext
					.getConnector((String) connector.value);
			if (ttt == null) {
				logger.warn("Warning: connector: "
						+ (String) connector.value
						+ " not found, reverting to default connector");

				defaultConnector.doTransaction(input, service, destAddress);
			} else {
				ttt.doTransaction(input, service, destAddress);
			}
		}
		setThreadState("busy");

	}

	public void oldExecute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		TipiValue parameter = getParameter("input");
		String unevaluated = null;
		boolean breakOnError = false;

		if (parameter != null) {
			unevaluated = parameter.getValue();
		}
		Operand serviceOperand = getEvaluatedParameter("service", event);
		Operand inputOperand = getEvaluatedParameter("input", event);
		Operand destinationOperand = getEvaluatedParameter("destination", event);
		Operand brk = getEvaluatedParameter("breakOnError", event);
		if (brk != null) {
			breakOnError = ((Boolean) brk.value).booleanValue();
		}
		if (serviceOperand == null || serviceOperand.value == null) {
			throw new TipiException(
					"Error in callService action: service parameter missing!");
		}
		String service = (String) serviceOperand.value;
		Navajo input = null;
		if (inputOperand != null) {
			input = (Navajo) inputOperand.value;
		}

		if (unevaluated != null && input == null) {
			throw new TipiException(
					"Input navajo not found when calling service: " + service
							+ " supplied input: " + unevaluated);
		}
		if (input == null) {
			input = NavajoFactory.getInstance().createNavajo();
		}
		try {
			Navajo nn = input.copy();
			// nn
			// Don't let NavajoClient touch your original navajo! It will mess
			// things up.
			myContext.fireNavajoSent(input, service);

			Navajo result = myContext.getClient().doSimpleSend(nn, service);

			myContext.addNavajo(service, result);
			// is this correct? It is a bit odd.
			if (result.getHeader() != null) {
				result.getHeader().setHeaderAttribute("sourceScript",
						result.getHeader().getRPCName());
			}
			if (destinationOperand != null) {
				if (destinationOperand.value != null) {
					service = (String) destinationOperand.value;
				}
			}
			myContext.loadNavajo(result, service);
			if (myContext.hasErrors(result)) {
				dumpStack("Server error detected: " + service);
				performTipiEvent("onError", Collections.singletonMap("error", (Object) result), true);
			}

			if (breakOnError) {
				if (myContext.hasErrors(result)) {
					throw new TipiBreakException(TipiBreakException.USER_BREAK);
				}
			}

		} catch (ClientException e) {
			logger.error("Error: ",e);
		}

	}
}
