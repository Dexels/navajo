package com.dexels.navajo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class Wait extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory.getLogger(Wait.class);
	
	@Override
	public final Object evaluate() throws TMLExpressionException {
		if ( getOperands().size() == 0) {
			throw new TMLExpressionException(this, "Missing argument");
		}
		Object w = getOperand(0);
		if ( w instanceof Integer ) {
			try {
				Thread.sleep(((Integer) w).intValue());
			} catch (InterruptedException e) {
				logger.error("Error: ", e);
			}
		} else {
			throw new TMLExpressionException(this, "Expected integer argument");
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
