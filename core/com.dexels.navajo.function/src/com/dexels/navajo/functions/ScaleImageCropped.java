package com.dexels.navajo.functions;

import java.io.IOException;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.scale.ImageScaler;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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
		return "ScaleImageCropped(Binary,int width,int height)";
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 3) {
			throw new TMLExpressionException(this, "Three operands expected. ");
		}
		Binary b = (Binary) getOperand(0);
		Integer width = (Integer) getOperand(1);
		Integer height = (Integer) getOperand(2);

		try {
			Binary res = ImageScaler.scaleCropped(b, width.intValue(), height.intValue());
			return res;
		} catch (IOException e) {
			return null;
		}

	}
}
