package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class Wait extends FunctionInterface {

	public final Object evaluate() throws TMLExpressionException {
		if ( getOperands().size() == 0) {
			throw new TMLExpressionException(this, "Missing argument");
		}
		Object w = getOperand(0);
		if ( w instanceof Integer ) {
			try {
				Thread.sleep(((Integer) w).intValue());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new TMLExpressionException(this, "Expected integer argument");
		}
		return null;
	}

	public String remarks() {
		return "Wait for specified number of milliseconds";
	}

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
