/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class Wait extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory.getLogger(Wait.class);
    @Override
	public boolean isPure() {
    		return false;
    }

	@Override
	public final Object evaluate() throws TMLExpressionException {
		if ( getOperands().size() == 0) {
			throw new TMLExpressionException(this, "Missing argument");
		}
		int w = getIntegerOperand(0);
		try {
			Thread.sleep(((Integer) w).intValue());
		} catch (InterruptedException e) {
			logger.error("Error: ", e);
		}
		return null;
	}

	@Override
	public String remarks() {
		return "Wait for specified number of milliseconds";
	}

	@Override
	public String usage() {
		return "Wait(integer)";
	}

	public static void main(String [] args) throws Exception {
		Wait w = new Wait();
		w.reset();
		//w.insertOperand("aa");
		System.err.print("Start wait...");
		w.evaluate();
		System.err.println("end wait.");
	}
}
