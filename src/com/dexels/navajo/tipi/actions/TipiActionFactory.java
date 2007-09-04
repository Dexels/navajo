package com.dexels.navajo.tipi.actions;

import java.util.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/**
 * This class is created for every action definition. It is capable of creating
 * different instances for its action. The different instances can be associated
 * to diffferent events and components. The actual parameters will differ, but
 * the allowed/required ones will be the same for every action this factory
 * instance can create.
 */
public class TipiActionFactory {
	protected String myName = null;
	protected Map myDefinedParams = new HashMap();
	protected Map myParams = new HashMap();
	protected TipiContext myContext = null;
	private Class myActionClass = null;

	public TipiActionFactory() {
	}

	// public Object getActionParameter(String name) {
	// /**@todo Implement this com.dexels.navajo.tipi.Executable method*/
	// throw new java.lang.UnsupportedOperationException("Method
	// getActionParameter() not yet implemented.");
	// }
	public void load(XMLElement actionDef, TipiContext context) throws TipiException {
		if (actionDef == null || !actionDef.getName().equals("tipiaction")) {
			throw new IllegalArgumentException("Can not instantiate tipi action.");
		}
		String pack = (String) actionDef.getAttribute("package");
		myName = (String) actionDef.getAttribute("name");
		String clas = (String) actionDef.getAttribute("class");
		String fullDef = pack + "." + clas;
		context.setSplashInfo("Adding action: " + fullDef);
//		System.err.println("Adding action: " + fullDef);
//		context.setSplashInfo("Adding action: " + fullDef);
		try {
			myActionClass = Class.forName(fullDef, true, getClass().getClassLoader());
		} catch (ClassNotFoundException ex) {
			System.err.println("Trouble loading action class: " + fullDef);
			throw new TipiException("Trouble loading action class: " + fullDef);
		}
		myContext = context;
		Vector children = actionDef.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement currentParam = (XMLElement) children.get(i);
			TipiValue tv = new TipiValue();
			tv.load(currentParam);
			myDefinedParams.put(tv.getName(), tv);
		}
	}

	public TipiAction instantateAction(XMLElement instance, TipiComponent tc) throws TipiException {
		// todo:
		// Instantiate the class.
		// System.err.println("INSTANTIATING ACTION:\n"+instance.toString());
		TipiAction newAction = null;
		try {
			newAction = (TipiAction) myActionClass.newInstance();
		} catch (IllegalAccessException ex) {
			throw new TipiException("Can not instantiate actionclass: " + newAction + " problem: " + ex.getMessage());
		} catch (InstantiationException ex) {
			throw new TipiException("Can not instantiate actionclass: " + newAction + " problem: " + ex.getMessage());
		}
		newAction.setContext(myContext);
		newAction.setComponent(tc);
		newAction.setType(myName);
		// Check presence of supplied parameters in the defined parameters
		Vector c = instance.getChildren();

		// TODO Fix that filthy performTipiMethod action. It messes up
		// everything,

		for (int i = 0; i < c.size(); i++) {
			XMLElement x = (XMLElement) c.get(i);
			TipiValue instanceValue = new TipiValue(x);
			// System.err.println("ADDING INSTANCE: "+x.toString());
			TipiValue defined = (TipiValue) myDefinedParams.get(x.getAttribute("name"));
			String val = (String) x.getAttribute("value");
			if (defined != null) {
				instanceValue.setDefaultValue(defined.getDefaultValue());
				instanceValue.setType(defined.getType());

			}
			if (defined == null) {
				// System.err.println("Parameter: "+x.getAttribute("name")+"
				// unknown in action: "+myName);

				newAction.addParameter(instanceValue);
				continue;
			} else {
				defined.typeCheck(val);
			}

			// if (instanceValue==null) {
			// continue;
			// }
			// if (val==null || defined.getDefaultValue()==null) {
			// continue;
			// }
			// if (val.equals(defined.getDefaultValue())) {
			// System.err.println("Skipping defaultvalue: "+val+" TipiVal:
			// "+defined.getName());
			// continue;
			// }
			newAction.addParameter(instanceValue);
		}

		Enumeration ee = instance.enumerateAttributeNames();
		while (ee.hasMoreElements()) {
			String element = (String) ee.nextElement();
			// System.err.println("Checking inline element: "+element);
			if ("type".equals(element)) {
				continue;
			}
			String name = element;
			String value = instance.getStringAttribute(name, null);
			// System.err.println("ADDING INLINE INSTANCE: "+name+" / "+value);
			TipiValue defined = (TipiValue) myDefinedParams.get(element);
			// String val = (String)instance.getAttribute("value");
			if (value == null) {

			}
			TipiValue instanceValue = new TipiValue();
			instanceValue.setName(element);
			instanceValue.setValue(value);
			if (defined != null) {
				instanceValue.setDefaultValue(defined.getDefaultValue());
				instanceValue.setType(defined.getType());
			}
			if (defined == null) {
				newAction.addParameter(instanceValue);
				continue;
			} else {
				newAction.addParameter(instanceValue);
				defined.typeCheck(value);
			}
		}

		// Check presence of required parameters.
		// Iterator it = myDefinedParams.values().iterator();
		// while (it.hasNext()) {
		// TipiValue current = (TipiValue) it.next();
		// if (current.isRequired()) {
		// if (!newAction.hasParameter(current.getName())) {
		// throw new TipiException("Can not instantiate actionclass: " +
		// newAction + " parameter: " + current.getName() + " missing!");
		// }
		// // Check for non required parameters not in the instance: Add them
		// with the default value
		// }
		// else {
		// if (!newAction.hasParameter(current.getName())) {
		// // REMOBED THE CLONE
		// newAction.addParameter( (TipiValue) current);
		// }
		// }
		// }
		return newAction;
	}

	public TipiValue getActionParam(String name) {
		return (TipiValue) myDefinedParams.get(name);
	}
}
