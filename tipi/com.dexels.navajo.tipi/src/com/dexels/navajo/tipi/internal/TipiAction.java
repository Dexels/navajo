package com.dexels.navajo.tipi.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiSuspendException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.actions.TipiActionFactory;

public abstract class TipiAction extends TipiAbstractExecutable {
	// protected TipiContext myContext;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5471018027422398769L;

	protected TipiActionFactory myActionFactory;

	// protected TipiEvent myEvent;

	protected String myType;
	protected String myTextNode;

	protected Map<String, TipiValue> parameterMap = new HashMap<String, TipiValue>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiAction.class);
	
	// if present, will intercept all parameter evaluations
	protected Map<String, Object> evaluatedMap = null; // = new HashMap<String,
														// TipiValue>();

	protected int counter = 0;

	// private TipiStackElement stackElement = null;

	/**
	 * Not 'really' supported, gets a bit difficult in the xml to distinguish
	 * from other params
	 */
	public String getBlockParam(String key) {
		return null;
	}

	public void loadParameters(Map<String, Object> params) {
		evaluatedMap = new HashMap<String, Object>();
		if (params != null) {
			evaluatedMap.putAll(params);
		}
	}

	protected abstract void execute(TipiEvent event) throws TipiBreakException,
			TipiException,TipiSuspendException;


	public static final boolean INLINE_ACTIONS = true;

	public void addParameter(TipiValue tv) {
		parameterMap.put(tv.getName(), tv);
	}

	protected void setThreadState(String state) {
		myContext.setThreadState(state);
	}

	public void performAction(TipiEvent te, TipiExecutable parent, int index)
			throws TipiBreakException, TipiException, TipiSuspendException {
		myContext.debugLog("action", myType);
		setEvent(te);
		if (getComponent().isDisposed()) {
			logger.error("\n**** BREAKING. COMPONENT DISPOSED: "
					+ getComponent().getPath() + " performing action: "
					+ getClass().getName());
			getStackElement().dumpStack("Component disposed: ");
			throw new TipiBreakException(TipiBreakException.COMPONENT_DISPOSED);
		}
		if (!checkCondition(te)) {
			return;
		}

		try {
			execute(te);
		} catch (TipiSuspendException e) {
			// hide
			throw e;
		} catch (TipiBreakException e) {
			// also hide
			throw e;
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
			logger.error("Uncaught exception: ",e);
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
		if (evaluatedMap != null) {
			Object o = evaluatedMap.get(name);
			TipiValue result = new TipiValue(getComponent());
			result.setName(name);
			result.setValue(o);
			return result;
		}
		return parameterMap.get(name);
	}

	// public ArrayList<TipiValue> getParams() {
	// ArrayList<TipiValue> parms = new
	// ArrayList<TipiValue>(parameterMap.values());
	// return parms;
	// }

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
		if (evaluatedMap != null) {
			Operand result = new Operand(evaluatedMap.get(name), "Object", null);
			return result;
		}

		TipiValue t = getParameter(name);
		if (t == null) {
			return null;
		}
		return evaluate(t.getValue(), event);
	}

	public Object getEvaluatedParameterValue(String name, TipiEvent event) {
		if (evaluatedMap != null) {
			return evaluatedMap.get(name);
		}
		Operand o = getEvaluatedParameter(name, event);
		if (o != null) {
			return o.value;
		}
		return null;
	}

	public void setContext(TipiContext tc) {
		myContext = tc;
	}

	public void setText(String content) {
		myTextNode = content;
	}

	public String getText() {
		return myTextNode;
	}
	
	private void constructStack(Stack<TipiExecutable> tex) {
		
		tex.push(this);
		TipiExecutable par = getParent();
		while(par!=null) {
			tex.push(par);
			par = par.getParent();
		}
	}

	protected Stack<TipiExecutable> constructStack() {
		 Stack<TipiExecutable> tex = new Stack<TipiExecutable>();
		 constructStack(tex);
		 return tex;
	}
	
	protected void suspend() throws TipiSuspendException {
		TipiSuspendException tse = new TipiSuspendException(getEvent(), constructStack());
		throw tse;
	}
}