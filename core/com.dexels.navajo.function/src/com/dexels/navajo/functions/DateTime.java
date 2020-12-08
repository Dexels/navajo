/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class DateTime extends FunctionInterface {

    @Override
	public String remarks() {
        return "Returns a string containing the current Date Time Stamp. Uses java.time.format.DateTimeFormater and can"
        		 + "hanndle all the patters of that class.";
    }

    @Override
	public String usage() {
        return "DateTime( | String pattern)";
    }

    @Override
	public final String evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

    		String pattern;
    		DateTimeFormatter dtf;

    		if(this.getOperands().isEmpty()){
    			// Getting Default Pattern
    			System.out.println("Getting Default Parser");
    			pattern = "dd-MM-yyyy HH:mm:ss";
    		}else {
    			if(this.getOperands().size() == 1) {
    				pattern = this.getStringOperand(0);
    			}else
    				throw new TMLExpressionException(this, "error: can take 0 or 1 arguments ");
    		}

    		try{
    			dtf = DateTimeFormatter.ofPattern(pattern);
    		}catch(Exception e){
    			// Pattern not found. Setting default Pattern
    			System.out.println("Pattern not found. Setting default pattern : dd-MM-yyyy HH:mm:ss");
    			dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    		}

	    	LocalDateTime now = LocalDateTime.now();
	    	System.out.println(dtf.format(now));
	    	return dtf.format(now);
    }
}