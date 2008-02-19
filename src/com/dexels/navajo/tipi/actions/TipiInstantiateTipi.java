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
			Object constraints) throws TipiException {
		TipiInstantiateTipi t = new TipiInstantiateTipi();
		// sort of hackish
		t.setContext(parent.getContext());
		return t.instantiateTipi(false, parent, force, id, null, definitionName, null, constraints);
	}

	protected TipiComponent instantiateTipiByDefinition(TipiComponent parent, boolean force, String id, String className,
			String definitionName, Object constraints) throws TipiException {
		return instantiateTipi(false, parent, force, id, className, definitionName, null, constraints);
	}

	protected TipiComponent instantiateTipiByClass(TipiComponent parent, boolean force, String id, String className, String definitionName,
			Object constraints) throws TipiException {
		return instantiateTipi(true, parent, force, id, className, definitionName, null, constraints);
	}

	protected TipiComponent instantiateTipi(boolean byClass, TipiComponent parent, boolean force, String id, String className,
			String definitionName, Map<String,TipiValue> paramMap, Object constraints) throws TipiException {
		return instantiateTipi(myContext, null, byClass, parent, force, id, className, definitionName, null, constraints);
	}
	
	protected TipiComponent instantiateTipi(TipiContext myContext, TipiComponent myComponent, boolean byClass, TipiComponent parent,
			boolean force, String id, String className, String definitionName, Map<String,TipiValue> paramMap, Object constraints) throws TipiException {

		TipiComponent comp = parent.getTipiComponentByPath(id);

		// ALTERNATIVE: this complains a lot.

		// String componentPath;
		// if (parent != null) {
		// componentPath = parent.getPath("component:/") + "/" + id;
		// }
		// else {
		// componentPath = "component://" + id;
		// }
		// /** @todo Should we allow null events? */
		// Operand op = evaluate("{" + componentPath + "}",null);
		//
		// TipiComponent comp = null;
		// if (op!=null) {
		//        
		// comp = (TipiComponent)op.value;
		// }

		// All this to check for existing components
		if (comp != null) {

			if (force) {
				// System.err.println("Calling dispose from instantiate, with
				// force= true");
				// System.err.println("Component path: "+comp.getPath());
				myContext.disposeTipiComponent(comp);
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
		if(id==null) {
			myContext.generateComponentId(parent);
		}
		xe.setAttribute("id", id);
		if (paramMap != null) {
			Iterator<String> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				try {
					String current = it.next();
					if (!"location".equals(current)) {
						xe.setAttribute(current, evaluate(getParameter(current).getValue(), null).value);
					}
				} catch (Exception ex1) {
					ex1.printStackTrace();
				}
			}
		}
		TipiComponent inst = myContext.instantiateComponent(xe);
		inst.setHomeComponent(true);
		inst.setId(id);
		parent.addComponent(inst, myContext, constraints);

		myContext.fireTipiStructureChanged(inst);
		return inst;
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
			id = (String) evaluate(getParameter("id").getValue(), null).value;
			
			
			Object o = evaluate((getParameter("location").getValue()), null).value;
			if (String.class.isInstance(o)) {
				System.err.println("Location evaluated to a string, trying to get Tipi from that string (" + o.toString() + ")");
				o = evaluate("{" + o.toString() + "}", null).value;
			}
			System.err.println(">>> "+getEvaluatedParameter("location",event).value);
			parent = (TipiComponent) o;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("OOps: " + ex.getMessage());
		}
		if (byClass) {
			instantiateTipi(myContext, myComponent, byClass, parent, force, id, (String) getEvaluatedParameter("class",event).value, null, parameterMap,
					constraints);
		} else {
			String definitionName = null;
			try {
				Operand ooo = getEvaluatedParameter("name", null);
				definitionName = (String) ooo.value;
			} catch (Exception ex1) {
				System.err
						.println("Trouble instantiating from definition. Actually, this probably means that you did not put quotes around the tipidefinition name,\nwhich is required by the new ISO-TIPI-2004 standard.");
				definitionName = getParameter("name").getValue();
			}
			instantiateTipi(myContext, myComponent, byClass, parent, force, id, null, definitionName, parameterMap, constraints);
		}
	}
}
