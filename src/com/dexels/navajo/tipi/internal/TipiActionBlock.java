package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.studio.*;
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
	// protected Map myParams = new HashMap();
	private final ArrayList myExecutables = new ArrayList();
	private String myExpression = "";
	private String myExpressionSource = "";
	// private TipiActionBlock myActionBlockParent = null;
	// private TipiEvent myEvent = null;
	private boolean conditionStyle = false;
	private boolean multithread = false;
	private TipiEvent myEvent = null;

	private final TipiContext myContext;

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

	public String getExpressionSource() {
		return myExpressionSource;
	}

	public void setExpression(String ex) {
		myExpression = ex;
	}

	public void setExpressionSource(String exs) {
		myExpressionSource = exs;
	}

	public void performAction(TipiEvent te, TipiExecutable parent, int index) throws TipiBreakException, TipiException {
		// System.err.println("PERFORMING BLOCK with expression "+myExpression);
		myEvent = te;
		boolean evaluated;
		if (te instanceof TipiEvent) {
			evaluated = checkCondition((TipiEvent) te);
		} else {
			evaluated = true;
		}
		try {
			myContext.performedBlock(myComponent, this, myExpression, myExpressionSource, evaluated, te);
		} catch (BlockActivityException ex1) {
			// System.err.println("Blocked exception");
			return;
		}
		if (!evaluated) {
			// System.err.println("Expression failed: Not executing children");
			return;
		}
		// System.err.println("Succeeded.");
		try {
			if (multithread) {
				for (int i = 0; i < myExecutables.size(); i++) {
					TipiExecutable current = (TipiExecutable) myExecutables.get(i);
					myContext.debugLog("thread", " multithread . Performing now");
					System.err.println("In multithread block enqueueing: " + current.toString());
					myContext.enqueueExecutable(current);
				}
			} else {
				for (int i = 0; i < myExecutables.size(); i++) {
					TipiExecutable current = (TipiExecutable) myExecutables.get(i);
					current.performAction(te, this, i);
				}
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

	public void loadConditionStyle(XMLElement elm, TipiComponent parent) {
		conditionStyle = true;
		myComponent = parent;
		if (elm.getName().equals("condition")) {
			Vector temp = elm.getChildren();
			for (int i = 0; i < temp.size(); i++) {
				XMLElement current = (XMLElement) temp.elementAt(i);
				if (current.getName().equals("param")) {
					String name = (String) current.getAttribute("name");
					String value = (String) current.getAttribute("value");
					// myParams.put(name, value);
					if ("tipipath".equals(name)) {
						myExpressionSource = value;
					}
					if ("expression".equals(name)) {
						myExpression = value;
					}
				}
				if ("action".equals(current.getName())) {
					try {
						TipiAction ta = myContext.instantiateTipiAction(current, parent);
						myExecutables.add(ta);
					} catch (TipiException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	// public void setEvent(TipiEvent event) {
	// myEvent = event;
	// }

	private boolean evaluateBlock(TipiContext context, Object source, TipiEvent te) throws TipiException {
		boolean valid = false;
		Operand o;
		try {
			if ((TipiComponent) source != null) {
				((TipiComponent) source).setCurrentEvent(te);
				o = Expression
						.evaluate(myExpression, ((TipiComponent) source).getNearestNavajo(), null, null, null, (TipiComponent) source);
				if (o.value.toString().equals("true")) {
					return true;
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

	/**
	 * @deprecated Also: Does not really support event parameters. Did not think
	 *             it useful to implement anymore Only added the parameter to
	 *             make it compile.
	 */
	private boolean evaluateCondition(TipiContext context, Object source, TipiEvent te) throws TipiException {
		boolean valid = false;
		Operand o;
		TipiPathParser pp = new TipiPathParser((TipiComponent) source, context, myExpressionSource);
		TipiComponent sourceComponent = pp.getComponent();
		// context.setCurrentComponent( (TipiComponent) source);
		if (pp.getPathType() == pp.PATH_TO_TIPI) {
			if (sourceComponent != null) {
				try {
					o = Expression.evaluate(myExpression, sourceComponent.getNearestNavajo(), null, null, null, (TipiComponent) source);
					if (o.value.toString().equals("true")) {
						valid = true;
					}
				} catch (TMLExpressionException ex) {
					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				System.err.println("ERROR: --------------------------> Could not find source tipi, returning FALSE");
				System.err.println("Expression: " + myExpression);
				System.err.println("From path: " + myExpressionSource);
				valid = false;
			}
		} else if (pp.getPathType() == pp.PATH_TO_MESSAGE) {
			Message m = pp.getMessage();
			if (sourceComponent != null && m != null) {
				try {
					// Use a copy of the Message. ArrayMessages remain a little
					// tricky
					Navajo n = NavajoFactory.getInstance().createNavajo();
					Message bert = m.copy(n);
					n.addMessage(bert);
					o = Expression.evaluate(myExpression, n, null, bert, null, (TipiComponent) source);
					if (o.value.toString().equals("true")) {
						valid = true;
					}
				} catch (TMLExpressionException ex) {
					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				System.err.println("ERROR: --------------------------> Could not find source tipi for MESSAGE, ignoring condition");
				valid = true;
			}
		} else {
			throw new TipiException("Cannot put a condition on a component or property source");
		}
		return valid;
	}

	//
	// public TipiEvent getEvent() {
	// return myEvent;
	// }
	//
	public void load(XMLElement elm, TipiComponent parent) {
		conditionStyle = false;
		myComponent = parent;
		// myEvent = event;
		if (elm.getName().equals("block")) {
			myExpression = (String) elm.getAttribute("expression");
			myExpressionSource = (String) elm.getAttribute("source");
			String multi = elm.getStringAttribute("multithread");
			if ("true".equals(multi)) {
				System.err.println("Load multithread block!");
				multithread = true;
			}
			Vector temp = elm.getChildren();
			parseActions(temp);
		}
	}

	// public abstract boolean evaluate(Navajo n, TipiContext context, Object
	// source, Object event) throws TipiBreakException, TipiException;
	public XMLElement store() {
		XMLElement cond = new CaseSensitiveXMLElement();
		cond.setName("block");
		// Iterator it = myParams.keySet().iterator();
		if (myExpression != null && !myExpression.equals("")) {
			cond.setAttribute("expression", myExpression);
		}
		if (myExpressionSource != null && !myExpressionSource.equals("")) {
			cond.setAttribute("source", myExpressionSource);
		}
		if (multithread) {
			cond.setAttribute("multithread", "true");
		}

		for (int i = 0; i < myExecutables.size(); i++) {
			TipiExecutable current = (TipiExecutable) myExecutables.get(i);
			XMLElement parm = current.store();
			// parm.setName("param");
			cond.addChild(parm);
		}
		return cond;
	}

	public void appendTipiExecutable(TipiExecutable tp) {
		// System.err.println("ADDING EXECUTABLE. Class: "+tp.getClass());
		//
		myExecutables.add(tp);
		// System.err.println("New count: "+myExecutables.size());
	}

	public boolean checkCondition(TipiEvent te) throws TipiException, TipiBreakException {
		if (myExpression == null || myExpression.equals("")) {
			return true;
		}
		if (conditionStyle) {
			return evaluateCondition(myContext, myComponent, te);
		} else {
			return evaluateBlock(myContext, myComponent, te);
		}
	}

	private final void parseActions(Vector v) {
		// TipiActionBlock currentBlock = parentBlock;
		try {
			for (int i = 0; i < v.size(); i++) {
				XMLElement current = (XMLElement) v.elementAt(i);
				if (current.getName().equals("action")) {
					// currentBlock.parseActions(v,context,myComponent);
					TipiAction action = myContext.instantiateTipiAction(current, myComponent);
					// action.setActionBlock(this);
					appendTipiExecutable(action);
					// myActions.add(action);
				}
				// if (current.getName().equals("condition")) {
				// TipiCondition con = context.instantiateTipiCondition(current,
				// myComponent, this);
				// parseActions(current.getChildren(), context, con);
				// }
				if (current.getName().equals("block")) {
					TipiActionBlock con = myContext.instantiateTipiActionBlock(current, myComponent);
					// con.parseActions(current.getChildren());
					// con.setTipiActionBlockParent(this);
					appendTipiExecutable(con);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public void appendExecutable(TipiExecutable a) {
	// myExecutables.add(a);
	// }

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

	// public TipiActionBlock getActionBlockParent() {
	// return myActionBlockParent;
	// }

	// public TreeNode getChildAt(int parm1) {
	// return (TreeNode)myExecutables.get(parm1);
	// }
	// public int getChildCount() {
	// return myExecutables.size();
	// }
	// public TreeNode getParent() {
	// if (myActionBlockParent!=null) {
	// return myActionBlockParent;
	// } else {
	// return myEvent;
	// }
	// }
	//
	// public int getIndex(TreeNode parm1) {
	// return -1;
	// }
	// public boolean getAllowsChildren() {
	// return true;
	// }
	// public boolean isLeaf() {
	// return myExecutables.size()==0;
	// }
	// public Enumeration children() {
	// return new Vector(myExecutables).elements();
	// }
	// public void addAction(TipiAction ta) {
	// myActions.add(ta);
	// }
	public int getExecutableChildCount() {
		return myExecutables.size();
	}

	public TipiExecutable getExecutableChild(int index) {
		return (TipiExecutable) myExecutables.get(index);
	}

	/*
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
	 * @see com.dexels.navajo.tipi.TipiExecutable#setEvent(com.dexels.navajo.tipi.internal.TipiEvent)
	 */
	public void setEvent(TipiEvent e) {
		myEvent = e;
	}
}
