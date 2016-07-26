package com.dexels.navajo.tipi.actions;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
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

    private final static Logger logger = LoggerFactory.getLogger(TipiNewCallService.class);

    @Override
    public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

        getContext().getClient().setLocaleCode(getContext().getApplicationInstance().getLocaleCode());
        getContext().getClient().setSubLocaleCode(getContext().getApplicationInstance().getSubLocaleCode());

        String service = (String) getEvaluatedParameterValue("service", event);
        Navajo input = (Navajo) getEvaluatedParameterValue("input", event);

        String destination = (String) getEvaluatedParameterValue("destination", event);
        String connector = (String) getEvaluatedParameterValue("connector", event);
        Object cachedObj = getEvaluatedParameterValue("cached", event);
        boolean cached = false;
        if (cachedObj != null && cachedObj instanceof Boolean) {
            cached = (Boolean) cachedObj;
        }
        Boolean breakOnError = (Boolean) getEvaluatedParameterValue("breakOnError", event);
        if (breakOnError == null) {
            breakOnError = false;
        }
        Integer retries = (Integer) getEvaluatedParameterValue("retries", event);
        if (retries == null) {
            retries = 0;
        }
        
        final TipiConnector defaultConnector = getContext().getDefaultConnector();

        if (connector == null && defaultConnector == null) {
            oldExecute(event);
            return;
        }

        if (service == null) {
            throw new TipiException("Error in callService action: service parameter missing!");
        }

        if (cached) {
            Navajo n = myContext.getCachedNavajo(service, 8);
            if (n != null) {
                logger.info("Returning CACHED service : " + service);
                myContext.loadNavajo(n, service);
                return;
            }

        }

        // input = enrichInput(input, true);

        setThreadState("waiting");
        if (connector == null) {
            // long timeStamp = System.currentTimeMillis();
            logger.debug("No connector");
            if (defaultConnector == null) {
                throw new IllegalStateException("No default tipi connector found!");
            }
            Navajo result = defaultConnector.doTransaction(input, service, retries);
            processResult(breakOnError, destination, service, result, cached);
        } else {
            TipiConnector ttt = myContext.getConnector(connector);
            if (ttt == null) {
                logger.warn("Warning: connector: " + connector + " not found, reverting to default connector");

                Navajo result = defaultConnector.doTransaction(input, service, destination);
                processResult(breakOnError, destination, service, result, cached);
            } else {
                Navajo result = ttt.doTransaction(input, service, destination);
                processResult(breakOnError, destination, service, result, cached);
            }
        }
        setThreadState("busy");

    }

    public void oldExecute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
        TipiValue parameter = getParameter("input");
        String unevaluated = null;
        if (getContext().getClient() == null) {
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
        if (breakOnError == null) {
            breakOnError = false;
        }
        Integer retries = (Integer) getEvaluatedParameterValue("retries", event);

        if (serviceOperand == null || serviceOperand.value == null) {
            throw new TipiException("Error in callService action: service parameter missing!");
        }
        String service = (String) serviceOperand.value;
        Navajo input = null;
        if (inputOperand != null) {
            input = (Navajo) inputOperand.value;
        }

        if (unevaluated != null && input == null) {
            throw new TipiException("Input navajo not found when calling service: " + service + " supplied input: " + unevaluated);
        }
        if (input == null) {
            input = NavajoFactory.getInstance().createNavajo();
        }
        Navajo nn = enrichInput(input.copy(), false);
        // nn
        // Don't let NavajoClient touch your original navajo! It will mess
        // things up.
        myContext.fireNavajoSent(input, service);
        try {
            myContext.getClient().doSimpleSend(nn, service);
            processResult(breakOnError, destination, service, nn, false);
        } catch (ClientException e) {
            logger.error("Error: ", e);
        }

    }

    private Navajo enrichInput(Navajo input, boolean newStyle) {
        // Should this be done? Check a global so it can be set by the tipi
        // code. Slightly hackish ofcourse
        Object enrichInput = this.getContext().getGlobalValue("enrichInputWithDebug");
        if (enrichInput != null && enrichInput instanceof Boolean && (Boolean) enrichInput) {

            Message m = NavajoFactory.getInstance().createMessage(input, "__debug__");

            // the userName of the sportlink user firing this action
            Object value = this.getContext().getGlobalValue("UserName");
            Property p = NavajoFactory.getInstance().createProperty(input, "UserName", "string", String.valueOf(value), 0, "", Property.DIR_OUT);
            m.addProperty(p);

            // the Java object id of the input navajo. Note that oldstyle does a
            // clone just before this call
            value = Integer.toHexString(System.identityHashCode(input));
            p = NavajoFactory.getInstance().createProperty(input, "NavajoInputJavaId", "string", String.valueOf(value), 0, "", Property.DIR_OUT);
            m.addProperty(p);

            // which mode this action worked in
            value = newStyle;
            p = NavajoFactory.getInstance().createProperty(input, "NewStyle", "string", String.valueOf(value), 0, "", Property.DIR_OUT);
            m.addProperty(p);

            // the Java object id of this action
            value = Integer.toHexString(System.identityHashCode(this));
            p = NavajoFactory.getInstance().createProperty(input, "ActionJavaId", "string", String.valueOf(value), 0, "", Property.DIR_OUT);
            m.addProperty(p);

            // the Java object id of the event this action belongs to
            value = Integer.toHexString(System.identityHashCode(this.getEvent()));
            p = NavajoFactory.getInstance().createProperty(input, "EventJavaId", "string", String.valueOf(value), 0, "", Property.DIR_OUT);
            m.addProperty(p);

            // the id of the event this action belongs to
            value = this.getEvent().toString();
            p = NavajoFactory.getInstance().createProperty(input, "EventString", "string", String.valueOf(value), 0, "", Property.DIR_OUT);
            m.addProperty(p);

            // the Java object id of the component the event of this action
            // belongs to
            value = Integer.toHexString(System.identityHashCode(this.getComponent()));
            p = NavajoFactory.getInstance().createProperty(input, "ComponentJavaId", "string", String.valueOf(value), 0, "", Property.DIR_OUT);
            m.addProperty(p);

            // the path of the component the event of this action belongs to
            value = this.getComponent().getPath();
            p = NavajoFactory.getInstance().createProperty(input, "ComponentPath", "string", String.valueOf(value), 0, "", Property.DIR_OUT);
            m.addProperty(p);

            if (input == null) {
                input = NavajoFactory.getInstance().createNavajo();
            }

            if (input.getMessage("__debug__") != null) {
                input.removeMessage("__debug__");
            }
            input.addMessage(m);
        }
        return input;
    }

    private void processResult(boolean breakOnError, String destination, String service, Navajo result, boolean cache) throws TipiException {

        myContext.addNavajo(service, result);
        if (cache) {
            myContext.cacheNavajo(service, result);
        }
        // is this correct? It is a bit odd.
        if (result.getHeader() != null) {
            result.getHeader().setHeaderAttribute("sourceScript", result.getHeader().getRPCName());
        }
        if (destination != null) {
            service = destination;
        }
        try {
            myContext.loadNavajo(result, service, breakOnError);
        } catch (TipiBreakException e) {
            performTipiEvent("onError", Collections.singletonMap("error", (Object) result), true);
            throw e;
        }
        // If breakOnError=false, we might still have errors. In that case, perform an onError event
        if (myContext.hasErrors(result)) {
            performTipiEvent("onError", Collections.singletonMap("error", (Object) result), true);
        }

    }
}
