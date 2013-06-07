package com.dexels.navajo.functions;

import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

public class StoreBinary extends FunctionInterface {

	private static final Logger logger = LoggerFactory.getLogger(StoreBinary.class);
	
	@Override
	public String remarks() {
		return "Stores a binary object in Mongo SharedStore";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		if ( getOperands().size() != 2 ) {
			throw new TMLExpressionException(this, "Invalid number of operands");
		}
		if ( !(getOperand(0) instanceof String) ) {
			throw new TMLExpressionException(this, "Invalid operand: " + getOperand(0));
		}
		if ( !(getOperand(1) instanceof Binary) ) {
			throw new TMLExpressionException(this, "Invalid operand: " + getOperand(1));
		}
		
		String id = (String) getOperand(0);
		Binary n = (Binary) getOperand(1);
		
		SharedStoreInterface mss = SharedStoreFactory.getInstance();
		try {
			OutputStream os = mss.getOutputStream(GetBinary.PARENT_LOCATION, id, false);
			n.write(os);
			os.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TMLExpressionException(this, e.getMessage());
		}
		 
		return id;
	}

}
