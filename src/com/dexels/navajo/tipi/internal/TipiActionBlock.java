package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiActionBlock implements TipiExecutable {
	protected TipiComponent myComponent = null;
	private final List<TipiExecutable> myExecutables = new ArrayList<TipiExecutable>();
	private String myExpression = "";
	private boolean multithread = false;
	private TipiEvent myEvent = null;

	private final TipiContext myContext;
	private Map<String, String> eventPropertyMap = new HashMap<String, String>();
	private TipiStackElement stackElement = null;

	public TipiActionBlock(TipiContext tc) {
		myContext = tc;
	}

	public String getExpression() {
		return myExpression;
	}

	public void setComponent(TipiComponent tc) {
		myComponent = tc;
	}

	public TipiComponent getComponent() {
		return myComponent;
	}


	public void setExpression(String ex) {
		myExpression = ex;
	}


	public void performAction(TipiEvent te, TipiExecutable parent, int index) throws TipiBreakException, TipiException {
		myEvent = te;
		boolean evaluated;
		evaluated = checkCondition(te);

		if (!evaluated) {
			return;
		}
		try {
			if (multithread) {
				for (int i = 0; i < myExecutables.size(); i++) {
					TipiExecutable current = myExecutables.get(i);
					myContext.debugLog("thread", " multithread . Performing now");
					System.err.println("In multithread block enqueueing: " + current.toString());
					myContext.enqueueExecutable(current);
				}
			} else {
				myContext.doActions(te, myComponent, this, myExecutables);
			}
			myEvent = null;
		} catch (TipiBreakException ex) {
			System.err.println("Break encountered!");
			if (TipiBreakException.BREAK_EVENT == ex.getType()) {
				throw ex;
			}
			return;
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private boolean evaluateBlock(TipiContext context, Object source, TipiEvent te) {
		// boolean valid = false;
		Operand o;
		try {
			if ((TipiComponent) source != null) {
				TipiComponent tc = (TipiComponent) source;
				synchronized (tc) {
					tc.setCurrentEvent(te);
					o = Expression.evaluate(myExpression, ((TipiComponent) source).getNearestNavajo(), null, null, null,
							(TipiComponent) source);
					if (o.value == null) {
						myContext.showInternalError("Block expression failed: " + myExpression);
						return false;
					}
					if (o.value.toString().equals("true")) {
						return true;
					}
				}
			} else {
				o = Expression.evaluate(myExpression, null, null, null, null, (TipiComponent) source);
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


	public void load(XMLElement elm, TipiComponent parent, TipiExecutable parentExe) {
		myComponent = parent;
		for (Iterator<String> iterator = elm.enumerateAttributeNames(); iterator.hasNext();) {
			String n = iterator.next();
			if (!n.equals("expression")) {
				eventPropertyMap.put(n, elm.getStringAttribute(n));

			}
		}

		// myEvent = event;

		if (elm.getName().equals("block")) {
			myExpression = (String) elm.getAttribute("expression");
			String multi = elm.getStringAttribute("multithread");
			if ("true".equals(multi)) {
				System.err.println("Load multithread block!");
				multithread = true;
			}
			setStackElement(new TipiStackElement("if: (" + myExpression + ")", elm, parentExe.getStackElement()));

			List<XMLElement> temp = elm.getChildren();
//			for (XMLElement element : temp) {
//				parseActions(myContext, element);
//			}
			parseActions(temp);
		} else {
			System.err.println("WTF?! WHAT IS THIS ELEMENT?!");
		}
	}

	/**
	 * DUPLICATE, copied from TipiEvent
	 * @param context
	 * @param current
	 * @throws TipiException
	 */
	private void parseActions(TipiContext context, XMLElement current) throws TipiException {
		if (current.getName().indexOf(".") == -1) {
			TipiAction ta = context.instantiateTipiAction(current, myComponent, this);
			myExecutables.add(ta);

		} else {
			StringTokenizer st = new StringTokenizer(current.getName(), ".");
			String classType = st.nextToken();
			String method = st.nextToken();
			if (method.equals("instantiate")) {
				XMLElement newCopy = current.copy();
				newCopy.setName("instantiate");
				newCopy.setAttribute("expectType", "'" + classType + "'");
				TipiAction ta = context.instantiateTipiAction(newCopy, myComponent, this);
				myExecutables.add(ta);
			} else if(method.equals("attribute")) {
				//XMLElement xxx = context.getComponentDefinition(classType);
				// TODO Do an extra check if all attributes exist.
				XMLElement newCopy = current.copy();
				newCopy.setName("attribute");
				TipiAction ta = context.instantiateTipiAction(newCopy, myComponent, this);
				myExecutables.add(ta);
				
			} else{
				//XMLElement xxx = context.getComponentDefinition(classType);
				// TODO Do an extra check if this method exists.
				
				XMLElement newCopy = current.copy();
				newCopy.setName("performTipiMethod");
				newCopy.setAttribute("name", "'" + method + "'");
				TipiAction ta = context.instantiateTipiAction(newCopy, myComponent, this);
				myExecutables.add(ta);
			}
		}
	}


	public void appendTipiExecutable(TipiExecutable tp) {
		// System.err.println("ADDING EXECUTABLE. Class: "+tp.getClass());
		//
		myExecutables.add(tp);
		// System.err.println("New count: "+myExecutables.size());
	}

	public boolean checkCondition(TipiEvent te) throws TipiBreakException {
		if (myExpression == null || myExpression.equals("")) {
			return true;
		}
		return evaluateBlock(myContext, myComponent, te);

	}

	private final void parseActions(List<XMLElement> temp) {
		try {
			for (XMLElement current : temp) {
				if (current.getName().equals("block")) {
					TipiActionBlock con = myContext.instantiateTipiActionBlock(current, myComponent, this);

					appendTipiExecutable(con);
				} else {
					parseActions(myContext, current);
					//TipiAction action = myContext.instantiateTipiAction(current, myComponent, this);
					//appendTipiExecutable(action);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeExecutable(TipiExecutable a) {
		myExecutables.remove(a);
	}

	public void moveExecutableUp(TipiAction action) {
		int index_old = myExecutables.indexOf(action);
		if (index_old > 0) {
			myExecutables.remove(action);
			myExecutables.add(index_old - 1, action);
		}
	}

	public void moveExecutableDown(TipiAction action) {
		int index_old = myExecutables.indexOf(action);
		if (index_old < myExecutables.size() - 1) {
			myExecutables.remove(action);
			myExecutables.add(index_old + 1, action);
		}
	}


	public int getExecutableChildCount() {
		return myExecutables.size();
	}

	public TipiExecutable getExecutableChild(int index) {
		return myExecutables.get(index);
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

	public String getBlockParam(String key) {
		return eventPropertyMap.get(key);
	}

	public TipiStackElement getStackElement() {
		return stackElement;
	}

	public void setStackElement(TipiStackElement s) {
		stackElement = s;
	}

	public void dumpStack(String message) {
		getStackElement().dumpStack(message);
	}
}
