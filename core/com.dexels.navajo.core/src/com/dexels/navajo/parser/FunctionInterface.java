package com.dexels.navajo.parser;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public abstract class FunctionInterface {

    private ArrayList<Object> operandList = null;
    protected Navajo inMessage = null;
    protected Message currentMessage = null;
    
    public void setCurrentMessage(Message currentMessage) {
		this.currentMessage = currentMessage;
	}

	// Act as if these attributes are final, they can only be set once.
    private static Object semahore = new Object();
    private final static HashSet<Class<? extends FunctionInterface>> initialized = new HashSet<Class<? extends FunctionInterface>>();

    @SuppressWarnings("rawtypes")
	private final static HashMap<Class<? extends FunctionInterface>, Class [][]> types = new HashMap<Class<? extends FunctionInterface>, Class[][]>();
	@SuppressWarnings("rawtypes")
	private final static HashMap<Class<? extends FunctionInterface>, Class []> returnType = new HashMap<Class<? extends FunctionInterface>, Class[]>();
    
    public abstract String remarks();
    
    private final Class [] getMyReturnType() {
    	return returnType.get(this.getClass());
    }
    
    private final Class [][] getMyInputParameters() {
    	return types.get(this.getClass());
    }
    
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
    	
    	sb.append(genPipedParamMsg(getMyReturnType()));
    	sb.append(" " + this.getClass().getSimpleName() + "( ");
    	if ( getMyInputParameters() != null ) {
    		for (int i = 0; i < getMyInputParameters().length; i++) {
    			sb.append(genPipedParamMsg(getMyInputParameters()[i]));
    			if ( i < getMyInputParameters().length - 1 ) {
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
    
    public final void setTypes(String [][] navajotypes, String [] navajoReturnType) {
    	if ( initialized.contains(this.getClass()) ) {
    		return;
    	}
    	synchronized (semahore) {

    		if ( initialized.contains(this.getClass()) ) {
        		return;
        	}
    		
    		NavajoFactory nf = NavajoFactory.getInstance();
    		
    		if ( navajotypes != null ) {
    			
    			// Convert navajo types to Java classes.
        	
        		Class [][] mytypes = new Class[navajotypes.length][];
        		boolean hasEmptyOptions = false;
        		boolean hasMultipleOptions = false;
        		
    			for (int i = 0; i < navajotypes.length; i++) {
    				mytypes[i] = new Class[navajotypes[i].length];
    				boolean emptyOptionSpecified = false;
    				boolean multipleOptionsSpecified = false;
    				for (int j = 0; j < navajotypes[i].length; j++) {

    					if ( navajotypes[i][j] != null && navajotypes[i][j].trim().equals("...") ) {
    						mytypes[i][j] = Set.class; // Use Set class to denote multiple parameter option.
    						multipleOptionsSpecified = true;
    						hasMultipleOptions = true;
    					} else if ( navajotypes[i][j] == null || navajotypes[i][j].trim().equalsIgnoreCase("empty") ) {
    						mytypes[i][j] = null;
    						emptyOptionSpecified = true;
    						hasEmptyOptions = true;
    					} else {
    						mytypes[i][j] = nf.getJavaType(navajotypes[i][j].trim());
    					}
    				}

    				if ( hasMultipleOptions && !multipleOptionsSpecified) {
    					throw new IllegalArgumentException("Multiple parameter options can only be specified in one sequence of last parameters.");
    				}

    				if ( hasEmptyOptions && !emptyOptionSpecified && !hasMultipleOptions ) {
    					throw new IllegalArgumentException("Empty parameter options can only be specified in one sequence of last parameters.");
    				}
    			}
    			types.put(this.getClass(), mytypes);
    		}
    		
    		if ( navajoReturnType != null ) {
    			// Set returntype.
    			Class [] myreturnType = new Class[navajoReturnType.length];
    			for (int i = 0; i < navajoReturnType.length; i++) {
    				if ( navajoReturnType[i] == null || navajoReturnType[i].equalsIgnoreCase("empty") ) {
    					myreturnType[i] = null;
    				} else {
    					myreturnType[i] = nf.getJavaType(navajoReturnType[i]);
    				}
    			}
    			returnType.put(this.getClass(), myreturnType);
    		}
    		
    		initialized.add(this.getClass());
    		
    		
    	}
    }
       
    @SuppressWarnings("unchecked")
	private final void checkReturnType(Object o) throws TMLExpressionException  {
    	
    	Class [] myreturntype = returnType.get(this.getClass());
    	
    	if ( myreturntype == null ) {
    		return;
    	}
    	
    	boolean correct = false;
    	
    	for (int i = 0; i < myreturntype.length; i++) {
    		if ( o != null && myreturntype[i].isAssignableFrom(o.getClass()) ) {
    			correct = true;
    		} else if ( o == null ) {
    			correct = true;
    		}
    	}
    	if ( !correct ) {
    		NavajoFactory nf = NavajoFactory.getInstance();
    		throw new TMLExpressionException("Expected returntype " + genPipedParamMsg(myreturntype) + ", got: " + 
    				( o != null ? nf.getNavajoType(o.getClass()) : " empty" ) );
    	}
    }
    
    @SuppressWarnings("unchecked")
	private final void checkTypes() throws TMLExpressionException {
    	
    	Class [][] mytypes = types.get(this.getClass());
    	
    	if ( mytypes != null ) {
    		StringBuffer msg = new StringBuffer();
    		
    		for (int paramIndex = 0; paramIndex < mytypes.length; paramIndex++) {
    			
    			Class [] possibleParameters = mytypes[paramIndex];
    			
    			boolean correct = false;
    			Class passedParam = null;
    			for (int possParam = 0; possParam < possibleParameters.length; possParam++) {
    				
    				Class p = possibleParameters[possParam];
    				boolean notpresent = false;
    				
    				try {
    					Object o = getOperand(paramIndex);
    					passedParam = ( o != null ? o.getClass() : null);
    				} catch (Exception e) {
    					passedParam = null;
    					notpresent = true;
    				}
    				if ( ( !notpresent && passedParam == null ) || // Passedparam could be present but NULL.
    					 ( notpresent && p == null && passedParam == null ) ||   // Passedparam could not be present but expected could be empty.
    					 ( p != null && p.equals(Set.class) ) || // Expected could not be empty and p could be set (=...)
    					 ( p != null && passedParam != null && p.isAssignableFrom( passedParam ) ) ) { // Expected is assignable from passedparam.
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
    	return types.get(this.getClass());
    }
   
    public final void reset() {
        operandList = new ArrayList<Object>();
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

    protected final ArrayList<?> getOperands() {
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
		return returnType.get(this.getClass());
	}

	public boolean isInitialized() {
		return initialized.contains(this.getClass());
	}

	public void setInMessage(Navajo inMessage) {
		this.inMessage = inMessage;
	}
	
	
}
