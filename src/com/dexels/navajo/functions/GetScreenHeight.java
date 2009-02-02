package com.dexels.navajo.functions;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;

/**
 * @author frank
 * 
 */
public class GetScreenHeight extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Returns the height of the screen (using Toolkit)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "GetScreenHeight()";
	}

	// GetComponent({component://init/desktop},{event:/from})
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

}
