package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Operand;
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
public final class TipiSetMDC extends TipiAction {

    private static final Logger logger = LoggerFactory.getLogger(TipiSetMDC.class);

    /**
     * 
     */
    private static final long serialVersionUID = 215495425275051153L;

    @Override
    public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

        String key = getParameter("key").getValue();
        String value = getParameter("from").getValue();
        Operand evaluated = evaluate(key, event);
        Operand evaluatedValue = evaluate(value, event);

        if (evaluated == null || evaluated.value == null) {
            logger.error("Error in setMdc: to evaluation failed. Key {} value: {}", key, value);
            return;
        }

        if (evaluatedValue == null) {
            evaluatedValue = new Operand(null, "string", null);
        } else {
            if (!(evaluated.value instanceof String) || !(evaluatedValue.value instanceof String)) {
                logger.error("Unable to set MDC to non-string values! Key {} value: {}", key, value);
                return;
            }
            MDC.put((String) evaluated.value, (String) evaluatedValue.value);
            logger.debug("MDC key '{}' set to '{}'", evaluated.value, evaluatedValue.value);
        }
    }
}
