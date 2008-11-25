package com.dexels.navajo.tipi.actions;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
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
public class TipiInstantiateTipi extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		instantiateTipi(false, event);
	}

	public static TipiComponent instantiateByDefinition(TipiComponent parent, boolean force, String id, String definitionName,
			Object constraints, TipiEvent event) throws TipiException {
		TipiInstantiateTipi t = new TipiInstantiateTipi();
		// sort of hackish
		t.setContext(parent.getContext());
		return t.instantiateTipi(false, parent, force, id, null, definitionName, null, constraints, event);
	}

	protected TipiComponent instantiateTipiByDefinition(TipiComponent parent, boolean force, String id, String className,
			String definitionName, Object constraints, TipiEvent event) throws TipiException {
		return instantiateTipi(false, parent, force, id, className, definitionName, null, constraints, event);
	}

	protected TipiComponent instantiateTipiByClass(TipiComponent parent, boolean force, String id, String className, String definitionName,
			Object constraints, TipiEvent event) throws TipiException {
		return instantiateTipi(true, parent, force, id, className, definitionName, null, constraints, event);
	}

	protected TipiComponent instantiateTipi(boolean byClass, TipiComponent parent, boolean force, String id, String className,
			String definitionName, Map<String, TipiValue> paramMap, Object constraints, TipiEvent event) throws TipiException {
		return instantiateTipi(myContext, null, byClass, parent, force, id, className, definitionName, null, constraints, event);
	}

	protected TipiComponent instantiateTipi(TipiContext context, TipiComponent component, boolean byClass, TipiComponent parent,
			boolean force, String id, String className, String definitionName, Map<String, TipiValue> paramMap, Object constraints,
			TipiEvent event) throws TipiException {

		TipiComponent comp = parent.getTipiComponentByPath(id);

		if (comp != null) {
			// Component exists:
			if (force) {
				
				context.disposeTipiComponent(comp);
			} else {
				comp.performTipiEvent("onInstantiate", null, false);
				comp.reUse();
				return comp;
			}
		}
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.setName("component");
		if (byClass) {
			xe.setAttribute("class", className);
		} else {
			xe.setAttribute("name", definitionName);
		}

		xe.setAttribute("id", id);
		if (paramMap != null) {
			Iterator<String> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				try {
					String current = it.next();
					if (!"location".equals(current)) {
						Object value = evaluate(getParameter(current).getValue(), event).value;
						if ("id".equals(current) || "class".equals(current) || "name".equals(current)) {
							xe.setAttribute(current, value);
						} else {
							// TODO THIS IS FILTHY!!!!
							String vv = getEscapedString(value);
							xe.setAttribute(current, vv);
						}

					}
				} catch (Exception ex1) {
					ex1.printStackTrace();
				}
			}
		}
		System.err.println("XE: "+xe);
		TipiComponent inst = context.instantiateComponent(xe, event, this);
		inst.setHomeComponent(true);
		inst.setId(id);
		parent.addComponent(inst, context, constraints);

		context.fireTipiStructureChanged(inst);
		return inst;
	}

	private String getEscapedString(Object value) {
		String vv = null;
		if (value instanceof String) {
			vv = "'" + (String) value + "'";
		} else {
			vv = "" + value;
		}
		return vv;
	}

	protected void instantiateTipi(boolean byClass, TipiEvent event) throws TipiException {
		String id = null;
		Object constraints = null;
		TipiValue forceVal = getParameter("force");
		String forceString = null;
		if (forceVal == null) {
			forceString = "false";
		} else {
			forceString = "true";
		}
		TipiComponent parent = null;
		boolean force;

		force = forceString.equals("true");
		try {
			constraints = getEvaluatedParameter("constraints", event);
			if (constraints != null) {
				constraints = ((Operand) constraints).value;
			}
			// TODO Do extra type checking
//			Operand expectTypeOperand = getEvaluatedParameter("expectType", event);
//			String expectType = null;
//			if (expectTypeOperand != null) {
//				expectType = (String) expectTypeOperand.value;
//			}

			Object o = evaluate((getParameter("location").getValue()), null).value;
			if (String.class.isInstance(o)) {
				System.err.println("Location evaluated to a string, trying to get Tipi from that string (" + o.toString() + ")");
				o = evaluate("{" + o.toString() + "}", null).value;
			}
			parent = (TipiComponent) o;
			if (parent == null) {
				throw new TipiException("Can not instantiate component. Parent not found: " + getParameter("location").getValue());
			}
			TipiValue suppliedId = getParameter("id");
			String proposedId = null;
			if (suppliedId == null) {
				// using parent here is odd, but it does not really matter
				id = myContext.generateComponentId(parent, parent);
			} else {
				proposedId = suppliedId.getValue();
				id = (String) evaluate(proposedId, event).value;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (byClass) {
			instantiateTipi(myContext, getComponent(), byClass, parent, force, id, (String) getEvaluatedParameter("class", event).value, null,
					parameterMap, constraints, event);
		} else {
			String definitionName = null;
			try {
				Operand ooo = getEvaluatedParameter("name", null);
				definitionName = (String) ooo.value;
			} catch (Exception ex1) {
				myContext.showInternalError("Error loading definition: " + definitionName, ex1);
				definitionName = getParameter("name").getValue();
			}
			try {
				// retry:
				instantiateTipi(myContext, getComponent(), byClass, parent, force, id, null, definitionName, parameterMap, constraints, event);
			} catch (Exception ex1) {
				// still did not work:
				myContext.showInternalError("Error loading definition: " + definitionName, ex1);
				if (ex1 instanceof TipiException) {
					TipiException te = (TipiException) ex1;
					throw te;
				}
			}

		}
	}
}
