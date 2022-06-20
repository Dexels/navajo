/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Erik Versteeg
 * @version $Id$
 */

public final class GetImageWidth extends FunctionInterface {

    public GetImageWidth() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    	if(this.getOperands().size() == 0) {
    		return Integer.valueOf(0);
    	}
    	if(this.getOperands().size() == 1 && this.getOperands().get(0) == null) {
    		return Integer.valueOf(0);
    	}    	
    	
    	Object arg = this.getOperands().get(0);

        if (arg == null) {
            throw new TMLExpressionException("Argument expected for GetImageWidth() function.");
        }
        else if (arg instanceof Binary) {
            InputStream is = null;
            ImageInputStream iis = null;
            try {
            	is = ((Binary) arg).getDataAsStream();
            	iis = ImageIO.createImageInputStream(is);
        		BufferedImage img = ImageIO.read(iis);
            	return img.getWidth();
            } catch (IOException e) {
        		return -1;
//            	throw new TMLExpressionException(e.getMessage());
            } finally {
            	if ( is != null ) {
            		try {
            			is.close();
            		} catch (IOException e) {
            			
            		}
            	}
            	if ( iis != null ) {
            		try {
            			iis.close();
            		} catch (IOException e) {
            			
            		}
            	}
            }
        } else {
        	return Integer.valueOf(0);
        }
        
    }

    @Override
	public String usage() {
        return "GetImageWidth(binary)";
    }

    @Override
	public String remarks() {
        return "Gets the width of a binary image";
    }
}
