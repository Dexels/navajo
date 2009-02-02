package com.dexels.navajo.functions;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;

/**
 * @author frank
 * 
 */
public class GetScreenWidth extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Returns the width of the screen (using Toolkit)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "GetScreenWidth()";
	}

	// GetComponent({component://init/desktop},{event:/from})
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}

}
