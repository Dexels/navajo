/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.Iterator;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class SetAllProperties extends FunctionInterface {

	@Override
	public String remarks() {
		return "Loops through an array message, and sets for every message the property with the supplied name to the supplied value";
	}

	@Override
	public String usage() {		
		return "SetAllProperties(Message, String, Object)";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 3)
            throw new TMLExpressionException("SetAllProperties(Message, String, Object) expected");
        Object a = this.getOperands().get(0);
        if (!(a instanceof Message))
            throw new TMLExpressionException("SetAllProperties(Message, String, Object) expected");
        Object b = this.getOperands().get(1);
       if (!(b instanceof String))
            throw new TMLExpressionException("SetAllProperties(Message, String, Object) expected");
       Object c = this.getOperands().get(2);
   
       Message source = (Message)a;
       String propertyName = (String)b;
       for (Iterator<Message> iter = source.getAllMessages().iterator(); iter.hasNext();) {
		Message element = iter.next();
		Property p = element.getProperty(propertyName);
		if (p!=null) {
			p.setAnyValue(c);
		}
	}
		return null;
	}
	
}
