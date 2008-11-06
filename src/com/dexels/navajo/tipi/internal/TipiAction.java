package com.dexels.navajo.tipi.internal;

import java.util.*;

import org.omg.CosNaming.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.actions.*;

public abstract class TipiAction extends TipiAbstractExecutable  {
	protected TipiContext myContext;

	protected TipiActionFactory myActionFactory;

	// protected TipiEvent myEvent;

	protected String myType;

	protected Map<String, TipiValue> parameterMap = new HashMap<String, TipiValue>();

	protected int counter = 0;

	private TipiStackElement stackElement = null;

	/**
	 * Not 'really' supported, gets a bit difficult in the xml to distinguish
	 * from other params
	 */
	public String getBlockParam(String key) {
		return null;
	}

	// protected TipiCondition myCondition;
	protected abstract void execute(TipiEvent event) throws TipiBreakException, TipiException;

	// protected TipiActionBlock myActionBlock;

	public static final boolean INLINE_ACTIONS = true;

	public void addParameter(TipiValue tv) {
		// System.err.println("ADDING PARAMETER:\n"+tv.toString());
		// Thread.dumpStack();
		parameterMap.put(tv.getName(), tv);
	}

	public void setThreadState(String state) {
		myContext.setThreadState(state);
	}

	public void performAction(TipiEvent te, TipiExecutable parent, int index) throws TipiBreakException, TipiException {
		myContext.debugLog("action", myType);
		setEvent(te);
		if (getComponent().isDisposed()) {
			System.err.println("\n**** BREAKING. COMPONENT DISPOSED: " + getComponent().getPath() + " performing action: "
					+ getClass().getName());
			// Thread.dumpStack();
			getStackElement().dumpStack("Component disposed: ");
			throw new TipiBreakException(TipiBreakException.COMPONENT_DISPOSED);
		}
		if(!checkCondition(te)) {
			return;
		}

		try {
			execute(te);
		} catch (Throwable e) {
			dumpStack(e.getMessage());
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			if (e instanceof TipiException) {
				throw (TipiException) e;
			}
			if (e instanceof TipiBreakException) {
				getStackElement().dumpStack("Break detected in action");
				throw (TipiBreakException) e;
			}
			System.err.println("Uncaught exception: ");
			e.printStackTrace();
		}
		setEvent(null);
	}



	public void setType(String type) {
		myType = type;
	}

	public String getType() {
		return myType;
	}

	public boolean hasParameter(String name) {
		return parameterMap.containsKey(name);
	}

	public TipiValue getParameter(String name) {
		return parameterMap.get(name);
	}

	public ArrayList<TipiValue> getParams() {
		ArrayList<TipiValue> parms = new ArrayList<TipiValue>(parameterMap.values());
		return parms;
	}

	public Set<String> getParameterNames() {
		return parameterMap.keySet();
	}

	public Operand evaluate(String expr, TipiEvent event) {

		Message m = null;
		Navajo n = null;
		if (getComponent() != null) {
			n = getComponent().getNearestNavajo();
		}

		return myContext.evaluate(expr, getComponent(), event, n, m);
	}

	public Operand getEvaluatedParameter(String name, TipiEvent event) {
		TipiValue t = getParameter(name);
		if (t == null) {
			return null;
		}
		return evaluate(t.getValue(), event);
	}

	public Object getEvaluatedParameterValue(String name, TipiEvent event) {
		Operand o = getEvaluatedParameter(name, event);
		if (o != null) {
			return o.value;
		}
		return null;
	}

	public void setContext(TipiContext tc) {
		myContext = tc;
	}


}