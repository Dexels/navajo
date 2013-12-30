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

		String service = (String) getEvaluatedParameterValue("service", event);
		Navajo input = (Navajo) getEvaluatedParameterValue("input", event);
//		Operand destination = getEvaluatedParameter("destination", event);
		String destination = (String) getEvaluatedParameterValue("destination", event);
		String connector = (String) getEvaluatedParameterValue("connector", event);
		Object cached = getEvaluatedParameterValue("cached", event);
		Boolean local = (Boolean) getEvaluatedParameterValue("local", event);
		Boolean breakOnError = (Boolean) getEvaluatedParameterValue("breakOnError", event);
		if(breakOnError==null) {
			breakOnError = false;
		}

		// Object silentValue = getEvaluatedParameterValue("silent", event);
		// Boolean silent = (Boolean)silentValue;
		final TipiConnector defaultConnector = getContext().getDefaultConnector();
		
//		if(connector == null && defaultConnector!=null) {
//			Navajo result = defaultConnector.doTransaction(input,service);
//			processResult(breakOnError, destination, service, result);
//			return;
//		}
		if (connector == null &&  defaultConnector==null) {
			oldExecute(event);
			return;
		}
//		String destAddress = null;
//		if (destination != null) {
//			destAddress = (String) destination.value;
//		}
		if (service == null ) {
			throw new TipiException(
					"Error in callService action: service parameter missing!");
		}

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


		setThreadState("waiting");
		if (connector == null) {
			// long timeStamp = System.currentTimeMillis();
			logger.debug("No connector");
			if (defaultConnector == null) {
				throw new IllegalStateException(
						"No default tipi connector found!");
			}
			Navajo result = defaultConnector.doTransaction(input, service);
			processResult(breakOnError, destination, service, result);
		} else {
			TipiConnector ttt = myContext
					.getConnector( connector);
			if (ttt == null) {
				logger.warn("Warning: connector: "
						+ connector
						+ " not found, reverting to default connector");

				Navajo result = defaultConnector.doTransaction(input, service, destination);
				processResult(breakOnError, destination, service, result);
			} else {
				Navajo result = ttt.doTransaction(input, service, destination);
				processResult(breakOnError, destination, service, result);
			}
		}
		setThreadState("busy");

	}

	public void oldExecute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		TipiValue parameter = getParameter("input");
		String unevaluated = null;
		if(getContext().getClient()==null) {
			throw new TipiException("No (HTTP) client configured, call will fail.");
		}
		getContext().getClient().setLocaleCode(getContext().getApplicationInstance().getLocaleCode());
		getContext().getClient().setSubLocaleCode(getContext().getApplicationInstance().getSubLocaleCode());
		

		if (parameter != null) {
			unevaluated = parameter.getValue();
		}
		Operand serviceOperand = getEvaluatedParameter("service", event);
		Operand inputOperand = getEvaluatedParameter("input", event);
		String destination = (String) getEvaluatedParameterValue("destination", event);
		Boolean breakOnError = (Boolean) getEvaluatedParameterValue("breakOnError", event);
		if(breakOnError==null) {
			breakOnError = false;
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
		Navajo nn = input.copy();
		// nn
		// Don't let NavajoClient touch your original navajo! It will mess
		// things up.
		myContext.fireNavajoSent(input, service);
		try {
			Navajo result = myContext.getClient().doSimpleSend(nn, service);
			processResult(breakOnError, destination, service, nn);
	} catch (ClientException e) {
			e.printStackTrace();
		}

	
	}

	private void processResult(boolean breakOnError, String destination,
			String service, Navajo result) throws TipiException {

			myContext.addNavajo(service, result);
			// is this correct? It is a bit odd.
			if (result.getHeader() != null) {
				result.getHeader().setHeaderAttribute("sourceScript",
						result.getHeader().getRPCName());
			}
			if (destination != null) {
					service = destination;
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
	}
}
