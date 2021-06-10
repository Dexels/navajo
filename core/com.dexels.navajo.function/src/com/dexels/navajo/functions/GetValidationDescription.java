/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.functions;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.resourcebundle.ResourceBundleStore;

import navajofunctions.Version;

public class GetValidationDescription extends FunctionInterface {

    private final static Logger logger = LoggerFactory.getLogger(GetValidationDescription.class);

    private static final String DEFAULT_LOCALE = "nl";

    @Override
    public String remarks() {
        return "Returns the description of a validation code";
    }

    @Override
    public Object evaluate() throws TMLExpressionException {

        Object o = getOperands().get(0);
        String key = null;
        if (o instanceof Integer) {
            key = ((Integer) o).toString();
        } else if (o instanceof String) {
            key = (String) o;
        } else {
            throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
        }

        String tenant = getAccess().getTenant();
        if (getOperands().size() > 1) {
            Object tenantO = getOperands().get(1);
            if (!(tenantO instanceof String)) {
                throw new TMLExpressionException(this,
                        "Invalid operand: " + tenantO.getClass().getName());

            }

            tenant = (String) tenantO;
        }

        String locale = getDefaultLocale();
        if (getOperands().size() > 2) {
            Object localeO = getOperands().get(2);
            if (!(localeO instanceof String)) {
                throw new TMLExpressionException(this,
                        "Invalid operand: " + localeO.getClass().getName());

            }

            locale = (String) localeO;
        }

        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }

        ResourceBundleStore rb = getResourceBundleService();
        if (rb == null) {
            return null;
        }

        return rb.getValidationDescription(key, tenant, locale);
    }

    private String getDefaultLocale() {
        return getAccess().getInDoc().getHeader().getHeaderAttribute("locale");
    }

    private ResourceBundleStore getResourceBundleService() {

        try {
            BundleContext context = Version.getDefaultBundleContext();
            ServiceReference<ResourceBundleStore> servicereference =
                    context.getServiceReference(ResourceBundleStore.class);

            if (servicereference != null) {
                ResourceBundleStore rbs = context.getService(servicereference);
                if (rbs == null) {
                    logger.warn("ResourceBundleStore found but could not be resolved.");
                    return null;
                }
                return rbs;
            }
        } catch (Throwable t) {
            logger.error("Exception in retrieving ResourceBundleStore!", t);
        }

        return null;
    }

}
