package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * Title:        Navajo
 * Description:  Checks if the given Object is a java.util.Date or can be converted to it
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Erik Versteeg
 * @version $Id$
 */
public class IsDate extends FunctionInterface {
    private final static Logger logger = LoggerFactory.getLogger(IsDate.class);

    public IsDate() {}

    @Override
	public String remarks() {
        return "Checks if a (string) object can be transformed to a date.";
    }

    @Override
	public String usage() {
        return "IsDate(String s|Date d)";
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = this.getOperand(0);
        if (o instanceof java.util.Date) {
            return Boolean.TRUE;
        } else if (o instanceof String) {
            java.util.Date date = null;
            try
            {
                // Going to guess some formats now by using the navajo function ParseDate()
                ParseDate td = new ParseDate();
                td.reset();
                td.insertOperand(o);
                // Check if a format is given
                if (this.getOperands().size() > 1 && this.getOperand(1) != null)
                {
                    td.insertOperand(this.getOperand(1));
                }
                date = (java.util.Date)td.evaluate();
            }
            catch (TMLExpressionException tee)
            {
                logger.debug("Not a date", tee);
                return Boolean.FALSE;
            }
            
            if (date != null) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else {
            // Feel free to implement here
            return Boolean.FALSE;
        }
    }

    public static void main(String args[]) throws Exception {
        List<String> dates = new ArrayList<String>();
        dates.add("1997-02-05");
        dates.add("01-01-2001");
        dates.add("01-02-2007");
        dates.add("11/03/1985");
        dates.add("12/06/84");
        dates.add("06/14/1982");
        dates.add("06-05-87");
        dates.add("06-05-187");
        IsDate id = new IsDate();
        for (String date : dates) {
            id.reset();
            id.insertOperand(date);
            System.err.println("Date (" + date + ") ok = " + id.evaluate());
        }
    }
}
