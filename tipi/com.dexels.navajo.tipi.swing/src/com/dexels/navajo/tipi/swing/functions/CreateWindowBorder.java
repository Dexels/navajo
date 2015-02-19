package com.dexels.navajo.tipi.swing.functions;

import java.awt.Color;

import javax.swing.border.Border;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.components.swingimpl.layout.TipiWindowBorder;

/**
 * @author Erik Versteeg
 * 
 */
public class CreateWindowBorder extends FunctionInterface {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Creates a border based on colors";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "CreateWindowBorder(Color startGradientColor, Color endGradientColor, [String gradientLayout])";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		Object color1 = getOperand(0);
		Object color2 = getOperand(1);
		Color startGradientColor = null;
		Color endGradientColor = null;
		
		if ( (color1 instanceof String) ) {
			startGradientColor = Color.decode( "0x" + ((String)color1).substring(1).toString() );
		} else {
			startGradientColor = (Color)color1;
		}
		if ( (color2 instanceof String) ) {
			endGradientColor = Color.decode( "0x" + ((String)color2).substring(1).toString() );
		} else {
			endGradientColor = (Color)color2;
		}

		String gradientLayout = "vertical";
		if (getOperands().size() >= 3) {
			gradientLayout = (String)getOperand(2);
		}
		
		// See what type of border we are going to create
		Border typedBorder = new TipiWindowBorder(startGradientColor, endGradientColor, gradientLayout);
		
		return typedBorder;
	}
}
