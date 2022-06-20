/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.io.IOException;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.functions.scale.ImageScaler;

/**
 * @author Erik Versteeg
 */
public class ScaleImageCropped extends FunctionInterface {
	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Scales an image to the specified dimensions (with transparency if needed). Keeps aspect ratio, and crops the image to the new size if necessary";
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "ScaleImageCropped(Binary,int width,int height,String imageType)";
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
	    if (getOperands().size() < 3) {
			throw new TMLExpressionException(this, "Three operands expected. ");
		}
		Binary b = (Binary) getOperand(0);
		Integer width = (Integer) getOperand(1);
		Integer height = (Integer) getOperand(2);
    	String imageType = "png";
        if ( getOperands().size() == 4 && (String)getOperand(3) != null ) {
        	imageType = (String)getOperand(3);
        }

		try {
			Binary res = ImageScaler.scaleCropped(b, width.intValue(), height.intValue(), imageType);
			return res;
		} catch (IOException e) {
			return null;
		}

	}
}
