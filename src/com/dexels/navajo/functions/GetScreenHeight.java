package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;

public class GetScreenHeight extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		return 768;
	}

	@Override
	public String remarks() {
		return null;
	}

	@Override
	public String usage() {
		return(" ");
	}

}
