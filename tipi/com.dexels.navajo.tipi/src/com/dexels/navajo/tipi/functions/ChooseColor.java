package com.dexels.navajo.tipi.functions;

import java.awt.Color;
import java.util.Locale;

import javax.swing.JColorChooser;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class ChooseColor extends FunctionInterface {
	
	String DIALOG_TITLE_NL = "Selecteer kleur";
	String DIALOG_TITLE_EN = "Select color";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Choose a color through JChooseColor";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "ChooseColor()";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		Color c;
        c = JColorChooser.showDialog( null, getDialogTitle(), Color.blue );
        if (c == null) {
        	return null;
        } else {
        	return toHexString(c);
        }
	}

	public final static String toHexString(Color colour) throws NullPointerException {
		  String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
		  if (hexColour.length() < 6) {
		    hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
		  }
		  return "#" + hexColour.toUpperCase();
		}
	
	private String getDialogTitle() {
		Locale locale = null;
	    if (locale == null) {
	        locale = new Locale( System.getProperty("user.language"), System.getProperty("user.country") );
	    }
	    
	 	if ( locale.getDisplayLanguage().toUpperCase() == "EN" ) {
	 		return DIALOG_TITLE_EN;
	 	} else {
	 		return DIALOG_TITLE_NL;
	 	}
	}
}
