package com.dexels.navajo.parser;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
import java.util.*;

import com.dexels.navajo.document.*;

public abstract class FunctionInterface {

    private ArrayList operandList = null;
    protected Navajo inMessage = null;
    protected Message currentMessage = null;
    
    // Act as if these attributes are final, they can only be set once.
    private static Object semahore = new Object();
    private static boolean initialized = false;
    private static Class [][] types = null;
    private static Class [] returnType = null;
    
    public abstract String remarks();
    
    private final String genPipedParamMsg(Class [] c) {
    	
    	if ( c == null ) {
    		return "unknown";
    	}
    	NavajoFactory nf = NavajoFactory.getInstance();
    	
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < c.length; i++) {
    		if ( c[i] != null ) {
    			sb.append(nf.getNavajoType(c[i]));
    		} else {
    			sb.append("empty");
    		}
    		if ( i < c.length - 1 ) {
    			sb.append(" | ");
    		}
    	}
    	return sb.toString();
    }
    
    public String usage() {
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append(genPipedParamMsg(returnType));
    	sb.append(" " + this.getClass().getSimpleName() + "( ");
    	if ( types != null ) {
    		for (int i = 0; i < types.length; i++) {
    			sb.append(genPipedParamMsg(types[i]));
    			if ( i < types.length - 1 ) {
    				sb.append(", ");
    			}
    		}
    	} else {
    		sb.append("unknown");
    	}
    	sb.append(")");
    	return sb.toString();
    }

    public FunctionInterface() {
    }
    
    public final void setTypes(String [][] navajotypes) {
    	if ( types != null ) {
    		return;
    	}
    	synchronized (semahore) {

    		if ( types != null ) {
        		return;
        	}
    		
    		// Convert navajo types to Java classes.
    		NavajoFactory nf = NavajoFactory.getInstance();
    		types = new Class[navajotypes.length][];
    		boolean hasEmptyOptions = false;
    		for (int i = 0; i < navajotypes.length; i++) {
    			types[i] = new Class[navajotypes[i].length];
    			boolean emptyOptionSpecified = false;
    			for (int j = 0; j < navajotypes[i].length; j++) {
    				if ( navajotypes[i][j] == null || navajotypes[i][j].equalsIgnoreCase("empty") ) {
    					types[i][j] = null;
    					emptyOptionSpecified = true;
    					hasEmptyOptions = true;
    				} else {
    					types[i][j] = nf.getJavaType(navajotypes[i][j]);
    				}
    			}
    			if ( hasEmptyOptions && !emptyOptionSpecified ) {
    				throw new IllegalArgumentException("Empty parameter options can only be specified in one sequence of last parameters.");
    			}
    		}
    		initialized = true;
    	}
    }
       
	public final void setReturnType(String [] navajoReturnType) {
		if ( this.returnType != null ) {
			return;
		}
		NavajoFactory nf = NavajoFactory.getInstance();
		this.returnType = new Class[navajoReturnType.length];
		for (int i = 0; i < navajoReturnType.length; i++) {
			if ( navajoReturnType[i] == null || navajoReturnType[i].equalsIgnoreCase("empty") ) {
				returnType[i] = null;
			} else {
				returnType[i] = nf.getJavaType(navajoReturnType[i]);
			}
		}
	}
         
    private final void checkReturnType(Object o) throws TMLExpressionException  {
    	if ( returnType == null ) {
    		return;
    	}
    	boolean correct = false;
    	for (int i = 0; i < returnType.length; i++) {
    		if ( o != null && o.getClass().equals(returnType[i])  ) {
    			correct = true;
    		}
    	}
    	if ( !correct ) {
    		NavajoFactory nf = NavajoFactory.getInstance();
    		throw new TMLExpressionException("Expected returntype " + genPipedParamMsg(returnType) + ", got: " + 
    				( o != null ? nf.getNavajoType(o.getClass()) : " empty" ) );
    	}
    }
    
    private final void checkTypes() throws TMLExpressionException {
    	if ( types != null ) {
    		StringBuffer msg = new StringBuffer();
    		
    		for (int paramIndex = 0; paramIndex < types.length; paramIndex++) {
    			
    			Class [] possibleParameters = types[paramIndex];
    			
    			boolean correct = false;
    			Class passedParam = null;
    			for (int possParam = 0; possParam < possibleParameters.length; possParam++) {
    				
    				Class p = possibleParameters[possParam];
    				
    				try {
    					passedParam = getOperand(paramIndex).getClass();
    				} catch (Exception e) {
    					passedParam = null;
    				}
    				if ( ( p == null && passedParam == null ) || ( p != null && passedParam != null && p.isAssignableFrom( passedParam ) ) ) {
    					correct = true;
    				}
    			}
    			if ( !correct ) {
    				NavajoFactory nf = NavajoFactory.getInstance();
    				msg.append("Parameter " + (paramIndex+1) + " has type " + 
    						( passedParam != null ?  nf.getNavajoType(passedParam) : "empty" ) + ", expected: " + 
    						genPipedParamMsg(possibleParameters) + "\n");
    			}
    		}
    		if (!msg.toString().equals("")) {
    			throw new TMLExpressionException(this, msg.toString(), null);
    		}
    	}
    	
    }
    
    public Class [][] getTypes() {
    	return types;
    }
   
    public final void reset() {
        operandList = new ArrayList();
    }
    public final void insertOperand(Object o) {
        operandList.add(o);
    }

    public Object evaluateWithTypeChecking() throws TMLExpressionException {
    	// Check types.
    	checkTypes();
    	Object o = evaluate();
    	checkReturnType(o);
    	return o;
    }
    
    public abstract Object evaluate() throws TMLExpressionException;

    protected final ArrayList getOperands() {
        return operandList;
    }

    protected final Navajo getNavajo() {
      return this.inMessage;
    }

    protected final Message getCurrentMessage() {
      return this.currentMessage;
    }

    protected final Object getOperand(int index) throws TMLExpressionException {
        if (index >= operandList.size())
            throw new TMLExpressionException("Function Exception: Missing operand (index = " + index + ")");
        else
            return operandList.get(index);
    }
	
    public Class [] getReturnType() {
		return returnType;
	}

	public boolean isInitialized() {
		return initialized;
	}
	
}
