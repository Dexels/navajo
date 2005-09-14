package com.dexels.navajo.functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class SetAllProperties extends FunctionInterface {

	public String remarks() {
		return "Loops through an array message, and sets for every message the property with the supplied name to the supplied value";
	}

	public String usage() {		
		return "SetAllProperties(Message, String, Object)";
	}

	public Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 3)
            throw new TMLExpressionException("SetAllProperties(Message, String, Object) expected");
        Object a = this.getOperands().get(0);
        if (!(a instanceof Message))
            throw new TMLExpressionException("SetAllProperties(Message, String, Object) expected");
        Object b = this.getOperands().get(1);
       if (!(a instanceof String))
            throw new TMLExpressionException("SetAllProperties(Message, String, Object) expected");
       Object c = this.getOperands().get(2);
   
       Message source = (Message)a;
       String propertyName = (String)b;
       for (Iterator iter = source.getAllMessages().iterator(); iter.hasNext();) {
		Message element = (Message) iter.next();
		Property p = element.getProperty(propertyName);
		if (p!=null) {
			p.setAnyValue(c);
		}
	}
		return null;
	}
	
}
