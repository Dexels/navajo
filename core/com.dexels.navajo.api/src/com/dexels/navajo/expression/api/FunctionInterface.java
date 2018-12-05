package com.dexels.navajo.expression.api;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.Access;

@SuppressWarnings("rawtypes")
public abstract class FunctionInterface {

	private ArrayList<Object> operandList = null;
	protected Navajo inMessage = null;
	protected Message currentMessage = null;
	protected FunctionDefinition myFunctionDefinition = null;

	
	private final static Logger logger = LoggerFactory.getLogger(FunctionInterface.class);

	
	public void setCurrentMessage(Message currentMessage) {
		this.currentMessage = currentMessage;
	}

	// Act as if these attributes are final, they can only be set once.
	private static Object semahore = new Object();
	private final static HashSet<Class<? extends FunctionInterface>> initialized = new HashSet<>();

	private final static Map<Class<? extends FunctionInterface>, Class[][]> types = new HashMap<>();
	private final static Map<Class<? extends FunctionInterface>, String> returnType = new HashMap<>();
	private Class[][] myinputtypes;
	private Optional<String> myreturntypes;

	public abstract String remarks();

	private Access access;
	private Map<String, Object> namedParameters;
//	private Map<String, Object> params;

	private final Optional<String> getMyReturnType() {
		return Optional.ofNullable(returnType.get(this.getClass()));
	}

	private final Class[][] getMyInputParameters() {
		return types.get(this.getClass());
	}

	private final String genPipedParamMsg(Class[] c) {

		if (c == null) {
			return "unknown";
		}
		NavajoFactory nf = NavajoFactory.getInstance();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < c.length; i++) {
			if (c[i] != null) {
				sb.append(nf.getNavajoType(c[i]));
			} else {
				sb.append("empty");
			}
			if (i < c.length - 1) {
				sb.append(" | ");
			}
		}
		return sb.toString();
	}

	public String usage() {
		StringBuffer sb = new StringBuffer();

		sb.append(getMyReturnType());
		sb.append(" " + this.getClass().getSimpleName() + "( ");
		if (getMyInputParameters() != null) {
			for (int i = 0; i < getMyInputParameters().length; i++) {
				sb.append(genPipedParamMsg(getMyInputParameters()[i]));
				if (i < getMyInputParameters().length - 1) {
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

	public boolean isPure() {
		return false;
	}
	
	public void setDefinition(FunctionDefinition fd) {
		myFunctionDefinition = fd;
//		if (fd.getInputParams() != null) {
//			myinputtypes = loadInputTypes(fd.getInputParams());
//		}
//		if (fd.getResultParam() != null) {
//			myreturntypes = loadReturnType(fd.getResultParam());
//		}
	}

	// Legacy
	public final void setTypes(String[][] navajotypes, String[] navajoReturnType) {
		if (initialized.contains(this.getClass())) {
			return;
		}
		synchronized (semahore) {

			if (initialized.contains(this.getClass())) {
				return;
			}

			if (navajotypes != null) {

				Class[][] mytypes = loadInputTypes(navajotypes);
				types.put(this.getClass(), mytypes);
			}

			if (navajoReturnType != null) {
				// Set returntype.
				Optional<String> myreturnType = loadReturnType(navajoReturnType);
				if(myreturnType.isPresent()) {
					returnType.put(this.getClass(), myreturnType.get());
				}
			}

			initialized.add(this.getClass());

		}
	}

	private Optional<String> loadReturnType(String[] navajoReturnType) {
		if(navajoReturnType==null) {
			return Optional.empty(); 
		}
		if(navajoReturnType.length>1 || navajoReturnType.length ==0) {
			return Optional.empty(); 
		}
		return Optional.of(navajoReturnType[0]);
	}

	private Class[][] loadInputTypes(String[][] navajotypes) {
		// Convert navajo types to Java classes.

		NavajoFactory nf = NavajoFactory.getInstance();
		Class[][] mytypes = new Class[navajotypes.length][];
		boolean hasEmptyOptions = false;
		boolean hasMultipleOptions = false;

		for (int i = 0; i < navajotypes.length; i++) {
			mytypes[i] = new Class[navajotypes[i].length];
			boolean emptyOptionSpecified = false;
			boolean multipleOptionsSpecified = false;
			for (int j = 0; j < navajotypes[i].length; j++) {

				if (navajotypes[i][j] != null && navajotypes[i][j].trim().equals("...")) {
					mytypes[i][j] = Set.class; // Use Set class to denote multiple parameter option.
					multipleOptionsSpecified = true;
					hasMultipleOptions = true;
				} else if (navajotypes[i][j] == null || navajotypes[i][j].trim().equalsIgnoreCase("empty")) {
					mytypes[i][j] = null;
					emptyOptionSpecified = true;
					hasEmptyOptions = true;
				} else {
					mytypes[i][j] = nf.getJavaType(navajotypes[i][j].trim());
				}
			}

			if (hasMultipleOptions && !multipleOptionsSpecified) {
				throw new IllegalArgumentException(
						"Multiple parameter options can only be specified in one sequence of last parameters.");
			}

			if (hasEmptyOptions && !emptyOptionSpecified && !hasMultipleOptions) {
				throw new IllegalArgumentException(
						"Empty parameter options can only be specified in one sequence of last parameters.");
			}
		}
		return mytypes;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private final void checkTypes() throws TMLExpressionException {

		Class[][] mytypes = null;
		if (myinputtypes != null) {
			mytypes = myinputtypes;
		} else {
			mytypes = types.get(this.getClass());
		}

		if (mytypes != null) {
			StringBuffer msg = new StringBuffer();

			for (int paramIndex = 0; paramIndex < mytypes.length; paramIndex++) {

				Class[] possibleParameters = mytypes[paramIndex];

				boolean correct = false;
				Class passedParam = null;
				for (int possParam = 0; possParam < possibleParameters.length; possParam++) {

					Class p = possibleParameters[possParam];
					boolean notpresent = false;

					try {
						Object o = getOperand(paramIndex);
						passedParam = (o != null ? o.getClass() : null);
					} catch (Exception e) {
						passedParam = null;
						notpresent = true;
					}
					if ((!notpresent && passedParam == null) || // Passedparam could be present but NULL.
							(notpresent && p == null && passedParam == null) || // Passedparam could not be present but
																				// expected could be empty.
							(p != null && p.equals(Set.class)) || // Expected could not be empty and p could be set
																	// (=...)
							(p != null && passedParam != null && p.isAssignableFrom(passedParam))) { // Expected is
																										// assignable
																										// from
																										// passedparam.
						correct = true;
					}
				}
				if (!correct) {
					NavajoFactory nf = NavajoFactory.getInstance();
					msg.append("Parameter " + (paramIndex + 1) + " has type "
							+ (passedParam != null ? nf.getNavajoType(passedParam) : "empty") + ", expected: "
							+ genPipedParamMsg(possibleParameters) + "\n");
				}
			}
			if (!msg.toString().equals("")) {
				throw new TMLExpressionException(this, msg.toString(), null);
			}
		}

	}

	public Class[][] getTypes() {
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
		// checkTypes();
		Object o = evaluate();
		// checkReturnType(o);
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

	public Optional<String> getReturnType() {
		String type = returnType.get(this.getClass());
		if(type==null || type.equals("any")) {
			return Optional.empty();
		}
		return Optional.ofNullable(type);
	}

	public boolean isInitialized() {
		return initialized.contains(this.getClass());
	}

	public void setInMessage(Navajo inMessage) {
		this.inMessage = inMessage;
	}

	public void setAccess(Access access) {
		this.access = access;
	}
//
//	public void setParams(Map<String, Object> params) {
//		this.params = params;
//	}

	public Access getAccess() {
		return this.access;
	}

	public String getInstance() {
		if (this.access == null) {
			return null;
		} else {
			return this.access.getTenant();
		}
	}

	public List<String> typeCheck(List<ContextExpression> l, String expression) {
		// Disabled for now
		
		if(true) {
			return Collections.emptyList();
		}
		//		Class[][] types = getTypes();
		String[][] types = myFunctionDefinition.getInputParams();
		if(types==null) {
			logger.warn("Can't type check function {} as it does not have defined input types. Expression: {}",myFunctionDefinition.getFunctionClass(),expression);
		} else {
			int i = 0;
			for (String[] classes : types) {
				int j = 0;
				for (String c : classes) {
					logger.info("class: {} type: {} -> {}",i,j,c);
					j++;
				}
				i++;
			}
			int index = 0;
			for (String[] alternatives : types) {
				logger.info("Number of alternatives for arg # {} is :{}",index,alternatives.length);
				List<String> typeCheckProblems = typeCheckOption(alternatives,l,index,expression);
				if(typeCheckProblems.isEmpty()) {
					System.err.println("Found typechecked option!");
					return typeCheckProblems;
				}
				index++;
			}
		}
		return Arrays.asList(new String[] {"Could not find a suitable type solution to this function: "+getClass().getName()+" in expression: "+expression});
	}

	private List<String> typeCheckOption(String[] alternatives, List<ContextExpression> l, int argumentIndex, String expression) {
		int argumentNumber = 0;
		List<String> problems = new ArrayList<>();
		for (ContextExpression contextExpression : l) {
			Optional<String> returnType = contextExpression.returnType();
			if(!returnType.isPresent()) {
				continue;
			}
//			if(argumentNumber > alternatives.length) {
//				return Arrays.asList(new String[] {"Argument number mismatch"});
//			}
			// TODO fix alternatives 
			String rt = returnType.get();
			boolean isCompatible = isCompatible(rt,alternatives[argumentNumber]);
			if(!isCompatible) {
				problems.add("Argument # "+argumentNumber+" of type: "+alternatives[argumentNumber]+" is incompatible with: "+rt);
			}
			argumentNumber++;
			
//			System.err.println("Checking context: "+);
		}
		return problems;
	}

	private boolean isCompatible(String rt, String inputType) {
		System.err.println("Comparing: "+rt+" -> "+inputType);
		if(rt==null || inputType==null) {
			return true;
		}
		return rt.equals(inputType);
	}

	public void setNamedParameter(Map<String, Object> named) {
		this.namedParameters = named;
	}
	
	protected Map<String,Object> getNamedParameters() {
		return Collections.unmodifiableMap(this.namedParameters);
	}
}
