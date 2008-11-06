package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;

public abstract class TipiAbstractExecutable implements TipiExecutable {
	private TipiEvent myEvent = null;
	private TipiComponent myComponent;
	private TipiContext myContext;
	private TipiStackElement stackElement = null;
	private String myCondition = "";
	
	private Map<String, String> eventPropertyMap = new HashMap<String, String>();

	private final List<TipiExecutable> myExecutables = new ArrayList<TipiExecutable>();

	public TipiAbstractExecutable(TipiContext tc) {
		myContext = tc;
	}

	public TipiAbstractExecutable() {
	}
	
	public String getExpression() {
		return myCondition;
	}

	public void setExpression(String ex) {
		myCondition = ex;
	}
	public void removeExecutable(TipiExecutable a) {
		myExecutables.remove(a);
	}

	public TipiContext getContext() {
		return myContext;
	}

	public void setContext(TipiContext tc) {
		myContext = tc;
	}

	public void appendTipiExecutable(TipiExecutable tp) {
		myExecutables.add(tp);
	}

	protected List<TipiExecutable> getExecutables() {
		return myExecutables;
	}

	protected void setExecutables(List<TipiExecutable> executables) {
		myExecutables.clear();
		myExecutables.addAll(executables);
	}


	public TipiComponent getComponent() {
		return myComponent;
	}

	public void setComponent(TipiComponent c) {
		myComponent = c;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.tipi.TipiExecutable#getEvent()
	 */
	public TipiEvent getEvent() {
		return myEvent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.TipiExecutable#setEvent(com.dexels.navajo.tipi
	 * .internal.TipiEvent)
	 */
	public void setEvent(TipiEvent e) {
		myEvent = e;
	}

	public TipiStackElement getStackElement() {
		return stackElement;
	}

	public void setStackElement(TipiStackElement s) {
		stackElement = s;
	}

	public void dumpStack(String message) {
		if (getStackElement() != null) {
			getStackElement().dumpStack(message);
		}
	}

	public String getBlockParam(String key) {
		return eventPropertyMap.get(key);
	}

	public void setBlockParam(String key, String value) {
		eventPropertyMap.put(key,value);
	}
	
	protected void parseActions(TipiContext context, XMLElement current) throws TipiException {
		if (current.getName().indexOf(".") == -1) {
			TipiAction ta = context.instantiateTipiAction(current, getComponent(), this);
			appendTipiExecutable(ta);
		} else {
			StringTokenizer st = new StringTokenizer(current.getName(), ".");
			String classType = st.nextToken();
			String method = st.nextToken();
			if (method.equals("instantiate")) {
				XMLElement newCopy = current.copy();
				newCopy.setName("instantiate");
				newCopy.setAttribute("expectType", "'" + classType + "'");
				TipiAction ta = context.instantiateTipiAction(newCopy, getComponent(), this);
				appendTipiExecutable(ta);
			} else if(method.equals("attribute")) {
				//XMLElement xxx = context.getComponentDefinition(classType);
				// TODO Do an extra check if all attributes exist.
				XMLElement newCopy = current.copy();
				newCopy.setName("attribute");
				TipiAction ta = context.instantiateTipiAction(newCopy, getComponent(), this);
				appendTipiExecutable(ta);
				
			} else{
				//XMLElement xxx = context.getComponentDefinition(classType);
				// TODO Do an extra check if this method exists.
				
				XMLElement newCopy = current.copy();
				newCopy.setName("performTipiMethod");
				newCopy.setAttribute("name", "'" + method + "'");
				TipiAction ta = context.instantiateTipiAction(newCopy, getComponent(), this);
				appendTipiExecutable(ta);
			}
		}
	}


	public boolean checkCondition(TipiEvent te) throws TipiBreakException {
		if (getExpression() == null || getExpression().equals("")) {
			return true;
		}
		return evaluateBlock(getContext(), getComponent(), te);

	}
	
	protected boolean evaluateBlock(TipiContext context, Object source, TipiEvent te) {
		// boolean valid = false;
		Operand o;
		try {
			if ((TipiComponent) source != null) {
				TipiComponent tc = (TipiComponent) source;
				synchronized (tc) {
					tc.setCurrentEvent(te);
					o = Expression.evaluate(getExpression(), ((TipiComponent) source).getNearestNavajo(), null, null, null,
							(TipiComponent) source);
					if (o.value == null) {
						getContext().showInternalError("Block expression failed: " + getExpression());
						return false;
					}
					if (o.value.toString().equals("true")) {
						return true;
					}
				}
			} else {
				o = Expression.evaluate(getExpression(), null, null, null, null, (TipiComponent) source);
				if (o.value.toString().equals("true")) {
					return true;
				}
			}
		} catch (TMLExpressionException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	public int getExecutableChildCount() {
		return myExecutables.size();
	}

	public TipiExecutable getExecutableChild(int index) {
		return myExecutables.get(index);
	}

}
