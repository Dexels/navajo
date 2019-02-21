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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.BinaryDigest;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.StopwatchTime;
import com.dexels.navajo.document.types.TypeUtils;
import com.dexels.navajo.script.api.Access;

@SuppressWarnings("rawtypes")
public abstract class FunctionInterface {

	private List<Operand> operandList = null;
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
//	private Optional<String> myreturntypes;

	public abstract String remarks();

	private Access access;
	private Map<String, Operand> namedParameters;
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
		operandList = new ArrayList<Operand>();
	}

	public final void insertOperand(Operand o) {
		if(o==null) {
			throw new NullPointerException("Don't add null operands, you can add Operand.NULL though.");
		}
		operandList.add(o);
	}


	public final void insertStringOperand(String o) {
		insertOperand(Operand.ofString(o));
	}

	public final void insertIntegerOperand(Integer o) {
		operandList.add(Operand.ofInteger(o));
	}

	public final void insertBinaryOperand(Binary o) {
		operandList.add(Operand.ofBinary(o));
	}
	public final void insertFloatOperand(Double o) {
		operandList.add(Operand.ofFloat(o));
	}
//	public final void insertIntegerOperand(Integer o) {
//		operandList.add(Operand.ofInteger(o));
//	}

	public Object evaluateWithTypeChecking() throws TMLExpressionException {
		return evaluate();
	}
	
	public Operand evaluateWithTypeCheckingOperand() throws TMLExpressionException {
		// Check types.
		// checkTypes();
		Object o = evaluate();
		String type = TypeUtils.determineNavajoType(o);
		return new Operand(o,type);
	}

	public abstract Object evaluate() throws TMLExpressionException;

	protected final List<?> getOperands() {
		return operandList.stream().map(e->e.value).collect(Collectors.toList());
	}

	protected final Navajo getNavajo() {
		return this.inMessage;
	}

	protected final Message getCurrentMessage() {
		return this.currentMessage;
	}

	@Deprecated
	protected final Object getOperand(int index) throws TMLExpressionException {
		return operand(index).value;
	}

	public Object operandWithType(int index, String type) {
		Operand d = operand(index);
		if(d.value==null) {
			return null;
		}
		if(d.type.equals(type)) {
			return d.value;
		}
		Object value = d.value;
		Class<?> valueClass = value == null ? null : value.getClass();
		throw new TMLExpressionException("Illegal operand type operand (index = " + index + ") should be of type: "+type+" but was of type: "+d.type+" the value class is: "+valueClass);

	}
	protected Operand operand(int index) {
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

	@SuppressWarnings("unused")
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
		}
		return problems;
	}

	private boolean isCompatible(String rt, String inputType) {
		if(rt==null || inputType==null) {
			return true;
		}
		return rt.equals(inputType);
	}

	public void setNamedParameter(Map<String, Operand> named) {
		this.namedParameters = named;
	}
	
	protected Map<String,Operand> getNamedParameters() {
		return Collections.unmodifiableMap(this.namedParameters);
	}

	public String getStringOperand(int index) {
		return (String) operandWithType(index, "string");
	}
	
	public Integer getIntegerOperand(int index) {
		return (Integer) operandWithType(index, "integer");
	}
	public Boolean getBooleanOperand(int index) {
		return (Boolean) operandWithType(index, "boolean");
	}
	
	public Property getPropertyOperand(int index) {
		return (Property) operandWithType(index, "property");
	}
	
	public Binary getBinaryOperand(int index) {
		return (Binary) operandWithType(index, "binary");
	}
	public BinaryDigest getBinaryDigestOperand(int index) {
		return (BinaryDigest) operandWithType(index, "binary_digest");
		
	}
    public Date getDateOperand(int index) {
		return (Date) operandWithType(index, "date");
	}


	public void insertDateOperand(Date o) {
		insertOperand(Operand.ofDate(o));
	}

	public void insertFloatOperand(Float o) {
		insertOperand(Operand.ofFloat(o.doubleValue()));
		
	}

	public void insertStopwatchOperand(StopwatchTime stopwatchTime) {
		insertOperand(Operand.ofStopwatchTime(stopwatchTime));
	}

	public void insertClockTimeOperand(ClockTime clockTime) {
		insertOperand(Operand.ofClockTime(clockTime));
	}

	public void insertMessageOperand(Message message) {
		insertOperand(Operand.ofMessage(message));
	}

	public void insertBooleanOperand(boolean b) {
		insertOperand(Operand.ofBoolean(b));
	}

	public void insertListOperand(List<? extends Object> list) {
		insertOperand(Operand.ofList(list));
	}

	public void insertNavajoOperand(Navajo createTestNavajo) {
		insertOperand(Operand.ofNavajo(createTestNavajo));
	}

	public void insertSelectionListOperand(List<Selection> allSelectedSelections) {
		insertOperand(Operand.ofSelectionList(allSelectedSelections));
	}

	public void insertPropertyOperand(Property property) {
		insertOperand(Operand.ofProperty(property));
	}

	public void insertMoneyOperand(Money money) {
		insertOperand(Operand.ofMoney(money));
		
	}

	public void insertLongOperand(long value) {
		insertOperand(Operand.ofLong(value));		
	}
	

	public void insertDynamicOperand(Object value) {
		insertOperand(Operand.ofDynamic(value));		
	}
}
