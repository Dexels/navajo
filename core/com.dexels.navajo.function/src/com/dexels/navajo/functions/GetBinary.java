package com.dexels.navajo.functions;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

public class GetBinary extends FunctionInterface {

	public final static String PARENT_LOCATION = "__BINARIES__";
	
	private static final Logger logger = LoggerFactory.getLogger(GetBinary.class);
	
	@Override
	public String remarks() {
		return "Retrieves a binary object from the Mongo SharedStore";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		if ( getOperands().size() != 1 ) {
			throw new TMLExpressionException(this, "Invalid number of operands");
		}
		if ( !(getOperand(0) instanceof String) ) {
			throw new TMLExpressionException(this, "Invalid operand: " + getOperand(0));
		}
		
		Binary b = null;
		String id = (String) getOperand(0);
	
		SharedStoreInterface mss = SharedStoreFactory.getInstance();
		try {
			InputStream is = mss.getStream(PARENT_LOCATION, id);
			b = new Binary(is);
			is.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TMLExpressionException(this, e.getMessage());
		}
		return b;
	}
	
}
