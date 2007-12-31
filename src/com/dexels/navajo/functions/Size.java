package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;
import java.util.ArrayList;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.Expression;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class Size extends FunctionInterface {

    public Size() {}

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    	if(this.getOperands().size()==0) {
    		return new Integer(0);
    	}
    	if(this.getOperands().size()==1 && this.getOperands().get(0)==null) {
    		return new Integer(0);
    	}    	
    	
    	Object arg = this.getOperands().get(0);

        //System.out.println("IN SIZE(), ARG = " + arg);
        if (arg == null) {
            throw new TMLExpressionException("Argument expected for Size() function.");
        }
        else if (arg instanceof java.lang.String) {
            return new Integer(((String) arg).length());
        }
        else if (arg instanceof Binary) {
        	return new Integer( (int) ((Binary) arg).getLength());
        } 
        else if (arg instanceof Message) {
        	return new Integer( (int) ((Message) arg).getArraySize());
        } 
        
        else if (!(arg instanceof ArrayList)) {
            throw new TMLExpressionException("Expected list argument for size() function.");
        }
        
        ArrayList list = (ArrayList) arg;

        return new Integer(list.size());
    }

    public String usage() {
        return "Size(list | arraymessage)";
    }

    public String remarks() {
        return "This function return the size of a list argument, or the size of an array message.";
    }

    public static void main(String [] args) throws Exception {
      Navajo n = NavajoFactory.getInstance().createNavajo();
      Message m = NavajoFactory.getInstance().createMessage(n, "Aap");
      n.addMessage(m);
      Property p = NavajoFactory.getInstance().createProperty(n, "Selection", "+", "", "out");
      m.addProperty(p);
      Selection s = NavajoFactory.getInstance().createSelection(n, "Aap", "0", false);
      p.addSelection(s);
      n.write(System.err);
      Operand o = Expression.evaluate("GetPropertyType('/Aap/Selection') == 'selection' AND Size([Aap/Selection]) == 0", n);
      System.err.println("o " + o.value + ", type: " + o.type);
    }
}
