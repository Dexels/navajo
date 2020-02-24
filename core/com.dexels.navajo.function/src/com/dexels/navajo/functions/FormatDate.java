package com.dexels.navajo.functions;

import java.util.Locale;

import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * Title: Navajo Description: Copyright: Copyright (c) 2002 Company: Dexels
 * 
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class FormatDate extends FunctionInterface {

    public FormatDate() {

        // super(new Class[][]{ {java.util.Date.class}, {java.lang.Integer.class},
        // {java.lang.String.class,null} });
        // setReturnType(new Class[]{java.lang.String.class});
        // super(new String[][]{ {"date"}, {"string"}, {"string", null}});
        // setReturnType(new String[]{"string"});
    }

    @Override
    public boolean isPure() {
        return true;
    }

    @Override
    public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

        if (this.getOperands().size() < 2 || this.getOperands().size() > 3)
            throw new TMLExpressionException(this.usage());

        if (getOperands().get(0) == null) {
            return "";
        }

        java.util.Date date = null;

        if (getOperands().get(0) instanceof ClockTime) {
            date = ((ClockTime) getOperands().get(0)).dateValue();
        } else if (getOperands().get(0) instanceof java.util.Date) {
            date = (java.util.Date) getOperands().get(0);
        } else {
            throw new TMLExpressionException(this, "Type mismatch");
        }

        String format = (String) this.getOperands().get(1);
        if (format == null)
            throw new TMLExpressionException("FormatDate: format cannot be null");

        java.text.SimpleDateFormat formatter = null;

        if (this.getOperands().size() > 2) {
            final String loc = (String) this.getOperands().get(2);
            if (loc != null && loc.length() > 0) {
                final Locale l = new Locale(loc);
                formatter = new java.text.SimpleDateFormat(format, l);
            }
        } else if (getAccess() != null && getAccess().getInDoc() != null){
            String localeheader = getAccess().getInDoc().getHeader().getHeaderAttribute("locale");
            if (localeheader != null) {
                final Locale l = new Locale(localeheader);
                formatter = new java.text.SimpleDateFormat(format, l);
            }
        }
        
        if (formatter == null) {
            // fallback
            formatter = new java.text.SimpleDateFormat(format);
        }

        return formatter.format(date);
    }

    @Override
    public String remarks() {
        return "formats a date according to a format string plus an optional locale";
    }

    public static void main(String args[]) throws Exception {

        // NavajoFactory nf = NavajoFactory.getInstance();

        FormatDate fd = new FormatDate();
        fd.reset();
        fd.insertDateOperand(new java.util.Date());
        fd.insertStringOperand("yyyy-MM-dd");
        Object o = fd.evaluateWithTypeChecking();
        System.err.println(o);
    }
}
