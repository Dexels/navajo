/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import java.io.IOException;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.scale.ImageScaler;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author arjen
 *
 */
public class ScaleImageMax extends FunctionInterface {
	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
        return "Scales an image to the specified dimensions. Keeps aspect ratio, and uses the largest value of the dimensions";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "ScaleImageMax(Binary,int width,int height,String imageType)";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
	    if (getOperands().size() < 3) {
            throw new TMLExpressionException(this, "Three operands expected. ");
        }
        Binary b = (Binary)getOperand(0);
        Integer width = (Integer)getOperand(1);
        Integer height = (Integer)getOperand(2);
    	String imageType = "png";
        if ( getOperands().size() == 4 && (String)getOperand(3) != null ) {
        	imageType = (String)getOperand(3);
        }

        try {
            Binary res = ImageScaler.scaleToMax(b, width.intValue(), height.intValue(), imageType);
            return res;
        } catch (IOException e) {
      	  return null;
        }
      
	}

}
