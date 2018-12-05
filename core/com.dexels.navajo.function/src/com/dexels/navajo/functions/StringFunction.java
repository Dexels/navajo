package com.dexels.navajo.functions;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class StringFunction extends FunctionInterface {

//	private final static Logger logger = LoggerFactory.getLogger(StringFunction.class);

	@Override
	public String remarks() {
		return "Perform a Java stringfunction on a given string.";
	}

	/**
	 * NOTE: THIS FUNCTION DOES NOT SUPPORT STRING METHODS THAT CONTAIN
	 * PRIMITIVE TYPE ARGUMENTS LIKE int, boolean float, etc. EXAMPLE OF A NOT
	 * SUPPORTED METHOD IS: substring(int).
	 * 
	 * @return
	 * @throws TMLExpressionException
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final Object evaluate()
			throws com.dexels.navajo.expression.api.TMLExpressionException {
		String methodName = (String) getOperand(0);
		if (methodName == null) {
			throw new TMLExpressionException(
					"Could not evaluate StringFunction because method name is null");
		}

		String object = (String) getOperand(1);
		if (object == null) {
			throw new TMLExpressionException(
					"Could not evaluate StringFunction because string is null");
		}
		ArrayList parameters = new ArrayList();
		for (int i = 2; i < getOperands().size(); i++) {
			parameters.add(getOperand(i));
		}
//		logger.warn("String function: " + methodName + " object: " + object);
//		logger.warn("Params: " + parameters);
		// boolean containsInteger = false;
		Class[] classTypes = null;
		if (parameters.size() > 0) {
			classTypes = new Class[parameters.size()];
			for (int i = 0; i < parameters.size(); i++) {
				Class c = parameters.get(i).getClass();
				// System.err.println(i + " c = " + c.getName());
				if (c.getName().equals("java.lang.Integer")) {
					c = java.lang.Integer.TYPE;
				}
				if (c.getName().equals("java.lang.Character")) {
					c = java.lang.Character.TYPE;
				}
				classTypes[i] = c;
			}
		}

		Object returnValue = null;
		try {
			// fix for contains method (see issue #135 GitHub).
			if ( "contains".equals(methodName) ) { // replace java.lang.String for CharSequence...
				classTypes = new Class[1];
				classTypes[0] = java.lang.CharSequence.class;
			}
			Method m = java.lang.String.class.getMethod(methodName, classTypes);
			if (m == null && classTypes!=null) {
				String parameterList = "";
				if (parameters.size() > 0) {
					parameterList = parameters.get(0) + " (" + classTypes[0]+ ")";
				}
				for (int i = 1; i < parameters.size(); i++) {
					parameterList += ", " + parameters.get(i) + "("+ classTypes[i] + ")";
				}
				throw new TMLExpressionException("Could not evaluate: "
						+ object + "." + methodName + "(" + parameterList + ")");
			}
			if(m==null) {
				throw new TMLExpressionException("No method found: "+methodName);
			}
			if (parameters.size() > 0) {
				returnValue = m.invoke(object, parameters.toArray());
			}
			else {
				returnValue = m.invoke(object, (Object[]) null);
			}
		} catch (Exception e) {
			throw new TMLExpressionException(e.getMessage(), e);
		}

		return returnValue;
	}

	@Override
	public String usage() {
		return "StringFunction(Method, Object, Arguments)";
	}

	public static void main(String[] args) throws Exception {
		StringFunction f = new StringFunction();
		// f.reset();
		// String aap = "Voetbal";
		// f.insertOperand("substring");
		// f.insertOperand(aap);
		// f.insertOperand(new Integer(0));
		// f.insertOperand(new Integer(2));
		// //f.insertOperand(new Integer(4));
		//
		// Object o = f.evaluate();
		// System.out.println("o = " + o + ", type = " +
		// o.getClass().getName());

		String noot = "06 - 2322 7572";
		f.reset();

		f.insertOperand("contains");
		f.insertOperand(noot);
		f.insertOperand("02");
		
		Object o = f.evaluate();
		System.out.println("o = " + o + ", type = " + o.getClass().getName());

		// //String noot = "Secretaris-BBFW63X@aap.nl";
		// f.reset();
		// f.insertOperand("substring");
		// f.insertOperand(noot);
		// f.insertOperand(new Integer(0));
		// f.insertOperand(new Integer(7));
		// o = f.evaluate();
		// System.out.println("o = " + o + ", type = " +
		// o.getClass().getName());
		//
		// f.reset();
		// f.insertOperand("indexOf");
		// f.insertOperand("Navajo");
		// f.insertOperand("ava");
		// o = f.evaluate();
		// System.out.println("o = " + o + ", type = " +
		// o.getClass().getName());

	}

}