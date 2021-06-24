/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * Title:        Navajo
 * Description:  Checks if the given Object is a java.util.Date and is in the future
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Erik Versteeg
 * @version $Id$
 */
public class IsFutureDate extends FunctionInterface {
    private final static Logger logger = LoggerFactory.getLogger(IsFutureDate.class);

    public IsFutureDate() {}

    @Override
	public String remarks() {
        return "Checks if a (string) object can be transformed to a date.";
    }

    @Override
	public String usage() {
        return "IsFutureDate(String s|Date d)";
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = this.getOperand(0);
        java.util.Date date = null;
        if (o instanceof java.util.Date) {
            date = (java.util.Date) o;
        } else if (o instanceof String) {
            try
            {
                // Going to guess some formats now by using the navajo function ParseDate()
                ParseDate td = new ParseDate();
                td.reset();
                td.insertStringOperand((String) o);
                // Check if a format is given
                if (this.getOperands().size() > 1 && this.getOperand(1) != null)
                {
                    td.insertDateOperand((Date) this.getOperand(1));
                }
                date = (java.util.Date)td.evaluate();
            }
            catch (TMLExpressionException tee)
            {
                logger.debug("Not a date", tee);
                return Boolean.FALSE;
            }
        } else {
            // Feel free to implement here
        }
        
        if (date != null && date.after(new java.util.Date())) {
        	return Boolean.TRUE;
        } else {
        	return Boolean.FALSE;
        }
    }

    public static void main(String args[]) throws Exception {
        List<String> dates = new ArrayList<String>();
        dates.add("1997-02-05");
        dates.add("01-01-2001");
        dates.add("01-01-2021");
        dates.add("");
        dates.add(null);
        dates.add("01-02-2007");
        dates.add("11/03/1985");
        dates.add("12/06/84");
        dates.add("06/14/1982");
        dates.add("06-05-87");
        dates.add("06-05-187");
        IsFutureDate id = new IsFutureDate();
        for (String date : dates) {
            id.reset();
            id.insertStringOperand(date);
            System.err.println("Date (" + date + ") ok = " + id.evaluate());
        }
    }
}
