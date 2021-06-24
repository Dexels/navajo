/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PropertyTypeException extends RuntimeException {
	private static final long serialVersionUID = -1565828252903897704L;
public PropertyTypeException(Property p, String message) {
    super("Property type exception: "+message+" property: "+p.getName()+" type: "+p.getType()+" subtype: "+p.getSubType()+" value: "+p.getValue());
  }
  public PropertyTypeException(Throwable cause, Property p, String message) {
    super("Property type exception: "+message+" property: "+p.getName()+" type: "+p.getType()+" subtype: "+p.getSubType()+" value: "+p.getValue(),cause);

  }
}
