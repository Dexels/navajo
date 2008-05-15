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
	private final List<TipiExecutable> myExecutables = new ArrayList<TipiExecutable>();
	private String myExpression = "";
	private String myExpressionSource = "";
	// private TipiActionBlock myActionBlockParent = null;
	// private TipiEvent myEvent = null;
	private boolean conditionStyle = false;
	private boolean multithread = false;
	private TipiEvent myEvent = null;

	private final TipiContext myContext;
	private Map<String,String> eventPropertyMap = new HashMap<String, String>();

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
//		 System.err.println("PERFORMING BLOCK with expression "+myExpression);
		myEvent = te;
		boolean evaluated;
		evaluated = checkCondition(te);
		try {
			myContext.performedBlock(myComponent, this, myExpression, myExpressionSource, evaluated, te);
		} catch (BlockActivityException ex1) {
			 System.err.println("Blocked exception");
			return;
		}
		if (!evaluated) {
//			 System.err.println("Expression failed: Not executing children");
			return;
		}
//		 System.err.println("Succeeded.");
//		 System.err.println("MY # of executables: "+myExecutables.size());
		// Allright, now we can execute:
		try {
			if (multithread) {
				for (int i = 0; i < myExecutables.size(); i++) {
					TipiExecutable current = myExecutables.get(i);
					myContext.debugLog("thread", " multithread . Performing now");
					System.err.println("In multithread block enqueueing: " + current.toString());
					myContext.enqueueExecutable(current);
				}
			} else {
				myContext.doActions(te,myComponent,this,myExecutables);
//				
//				for (int i = 0; i < myExecutables.size(); i++) {
//					TipiExecutable current = (TipiExecutable) myExecutables.get(i);
//					current.performAction(te, this, i);
//				}
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

	private boolean evaluateBlock(TipiContext context, Object source, TipiEvent te) throws TipiException {
		boolean valid = false;
		Operand o;
		try {
			if ((TipiComponent) source != null) {
				TipiComponent tc = (TipiComponent) source;
				synchronized (tc) {
					tc.setCurrentEvent(te);
					o = Expression
					.evaluate(myExpression, ((TipiComponent) source).getNearestNavajo(), null, null, null, (TipiComponent) source);
					if(o.value==null) {
						myContext.showInternalError("Block expression failed: "+myExpression);
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


	//
	// public TipiEvent getEvent() {
	// return myEvent;
	// }
	//
	public void load(XMLElement elm, TipiComponent parent) {
		conditionStyle = false;
		myComponent = parent;
		for (Iterator<String> iterator = elm.enumerateAttributeNames(); iterator.hasNext();) {
			String n= iterator.next();
			if(!n.equals("expression")) {
				eventPropertyMap.put(n,elm.getStringAttribute(n));
							
			}
		}
		// myEvent = event;
		if (elm.getName().equals("block")) {
			myExpression = (String) elm.getAttribute("expression");
			myExpressionSource = (String) elm.getAttribute("source");
			String multi = elm.getStringAttribute("multithread");
			if ("true".equals(multi)) {
				System.err.println("Load multithread block!");
				multithread = true;
			}
			List<XMLElement> temp = elm.getChildren();
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
			TipiExecutable current = myExecutables.get(i);
			XMLElement parm = current.store();
			// parm.setName("param");
			cond.addChild(parm);
		}
		return cond;
	}

	public void appendTipiExecutable(TipiExecutable tp) {
//		 System.err.println("ADDING EXECUTABLE. Class: "+tp.getClass());
		//
		myExecutables.add(tp);
		// System.err.println("New count: "+myExecutables.size());
	}

	public boolean checkCondition(TipiEvent te) throws TipiException, TipiBreakException {
		if (myExpression == null || myExpression.equals("")) {
			return true;
		}
		return evaluateBlock(myContext, myComponent, te);
	
	}

	private final void parseActions(List<XMLElement> temp) {
		// TipiActionBlock currentBlock = parentBlock;
		try {
			for (XMLElement current : temp) {
				if (current.getName().equals("block")) {
					TipiActionBlock con = myContext.instantiateTipiActionBlock(current, myComponent);
					appendTipiExecutable(con);
				}else {
					TipiAction action = myContext.instantiateTipiAction(current, myComponent);
					appendTipiExecutable(action);
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
		return myExecutables.get(index);
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

	public String getBlockParam(String key) {
		return eventPropertyMap.get(key);
	}
}
