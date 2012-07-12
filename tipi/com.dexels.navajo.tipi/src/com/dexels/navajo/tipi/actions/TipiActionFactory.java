package com.dexels.navajo.tipi.actions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiStackElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.version.ExtensionDefinition;

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
public class TipiActionFactory implements Serializable {
	
	private static final long serialVersionUID = 6130577853312463090L;
	protected String myName = null;
	protected Map<String, TipiValue> myDefinedParams = new HashMap<String, TipiValue>();
	private Class<?> myActionClass = null;
	private XMLElement xmlElement = null;
	
	public XMLElement getXmlElement() {
		return xmlElement;
	}

	public TipiActionFactory() {
	}

	// public Object getActionParameter(String name) {
	// /**@todo Implement this com.dexels.navajo.tipi.Executable method*/
	// throw new java.lang.UnsupportedOperationException("Method
	// getActionParameter() not yet implemented.");
	// }
	public void load(XMLElement actionDef,
			ExtensionDefinition ed) throws ClassNotFoundException {
		
		this.xmlElement = actionDef;
		if (actionDef == null || !actionDef.getName().equals("tipiaction")) {
			throw new IllegalArgumentException(
					"Can not instantiate tipi action.");
		}
		String pack = (String) actionDef.getAttribute("package");
		myName = (String) actionDef.getAttribute("name");
		String clas = (String) actionDef.getAttribute("class");
		String fullDef = pack + "." + clas;
		myActionClass = Class.forName(fullDef, true, ed.getClass().getClassLoader());

		List<XMLElement> children = actionDef.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement currentParam = children.get(i);
			TipiValue tv = new TipiValue(null);
			tv.load(currentParam);
			myDefinedParams.put(tv.getName(), tv);
		}
	}

	/**
	 * 
	 * @param instance
	 *            is the actual action instance in the script
	 * @param tc
	 * @param parentExe
	 * @return
	 * @throws TipiException
	 */
	public TipiAction instantateAction(XMLElement instance, TipiComponent tc,
			TipiExecutable parentExe) throws TipiException {
		// todo:
		// Instantiate the class.
		TipiAction newAction;
		newAction = createAction(tc);

		if (instance.getContent() != null) {
			newAction.setText(instance.getContent());
		}
		String condition = instance.getStringAttribute("condition");
		if (condition != null) {
			newAction.setExpression(condition);
		}

		newAction.setStackElement(new TipiStackElement(myName, instance,
				parentExe.getStackElement()));
		// Check presence of supplied parameters in the defined parameters
		List<XMLElement> c = instance.getChildren();

		// TODO Fix that filthy performTipiMethod action. It messes up
		// everything,

		for (int i = 0; i < c.size(); i++) {
			XMLElement x = c.get(i);
			TipiValue instanceValue = new TipiValue(tc, x);
			TipiValue defined = myDefinedParams.get(x.getAttribute("name"));
			String val = (String) x.getAttribute("value");
			if (defined != null) {
				instanceValue.setDefaultValue(defined.getDefaultValue());
				instanceValue.setType(defined.getType());

			}
			if (defined == null) {
				newAction.addParameter(instanceValue);
				continue;
			}
			defined.typeCheck(val);
			newAction.addParameter(instanceValue);
		}
		for (Iterator<String> iterator = instance.enumerateAttributeNames(); iterator
				.hasNext();) {
			String element = iterator.next();
			if ("type".equals(element)) {
				continue;
			}
			String name = element;
			String value = instance.getStringAttribute(name, null);
			TipiValue defined = myDefinedParams.get(element);
			if (value == null) {

			}
			TipiValue instanceValue = new TipiValue(tc);
			instanceValue.setName(element);
			instanceValue.setValue(value);
			if (defined != null) {
				instanceValue.setDefaultValue(defined.getDefaultValue());
				instanceValue.setType(defined.getType());
			}
			if (defined == null) {
				newAction.addParameter(instanceValue);
				continue;
			}
			newAction.addParameter(instanceValue);
			defined.typeCheck(value);
		}
		return newAction;
	}

	public TipiAction createAction(TipiComponent tc) throws TipiException {
		TipiAction newAction;
		try {
			newAction = (TipiAction) myActionClass.newInstance();
		} catch (IllegalAccessException ex) {
			throw new TipiException("Can not instantiate actionclass. "
					+ " problem: " + ex.getMessage());
		} catch (InstantiationException ex) {
			throw new TipiException("Can not instantiate actionclass. "
					+ " problem: " + ex.getMessage());
		}

		newAction.setContext(tc.getContext());
		newAction.setComponent(tc);
		newAction.setType(myName);
		return newAction;
	}

	public TipiValue getActionParam(String name) {
		return myDefinedParams.get(name);
	}
}
