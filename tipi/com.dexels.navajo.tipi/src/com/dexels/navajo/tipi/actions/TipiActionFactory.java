package com.dexels.navajo.tipi.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import navajo.ExtensionDefinition;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiStackElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
 * to different events and components. The actual parameters will differ, but
 * the allowed/required ones will be the same for every action this factory
 * instance can create.
 */
public class TipiActionFactory implements Serializable {
	
	private static final long serialVersionUID = 6130577853312463090L;
	protected String myName = null;
	protected Map<String, TipiValue> myDefinedParams = new HashMap<String, TipiValue>();
	private Class<?> myActionClass = null;
	private XMLElement xmlElement = null;
	private final List<String> actionEvents = new ArrayList<String>();
	
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
			XMLElement currentChild = children.get(i);
			if ("events".equals(currentChild.getName())) {
				loadEvents(currentChild, actionDef);
			}
			else
			{
				TipiValue tv = new TipiValue(null);
				tv.load(currentChild);
				myDefinedParams.put(tv.getName(), tv);
			}
		}
	}

	/**
	 * Loads all the allowed event from the classdefinition
	 * @param classdef 
	 */
	private final void loadEvents(XMLElement events, XMLElement actionDef) {
		List<XMLElement> children = events.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement xx = children.get(i);
			String eventName = xx.getStringAttribute("name");
			actionEvents.add(eventName);
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
		loadEventsDefinition(tc.getContext(), newAction, instance, xmlElement);

		// Check presence of supplied parameters in the defined parameters
		List<XMLElement> c = instance.getChildren();

		// TODO Fix that filthy performTipiMethod action. It messes up
		// everything,

		// parse children
		for (int i = 0; i < c.size(); i++) {
			XMLElement x = c.get(i);
			if (!actionEvents.contains(x.getName()))
			{
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
		}

		// parse attributes
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
	/**
	 * Loads an event definition from the component definition
	 */

	public void loadEventsDefinition(TipiContext context, TipiAction action,
			XMLElement definition, XMLElement classDef) throws TipiException {
		List<XMLElement> defChildren = definition.getChildren();
		for (int i = 0; i < defChildren.size(); i++) {
			XMLElement xx = defChildren.get(i);
			// String[] s = getCustomChildTags();
			if (!xx.getName().equals("layout")
					&& !xx.getName().equals("tipi-instance")
					&& !xx.getName().equals("component-instance")
					&& !xx.getName().equals("component")
					&& !xx.getName().startsWith("c.")) {
				String type = xx.getStringAttribute("type");
				if (type == null) {
					type = xx.getName();
				}
				if (actionEvents.contains(type)) {
					TipiEvent event = new TipiEvent();
					XMLElement eventDef = getEventDefFromClassDef(classDef,
							type);
					event.init(eventDef);
					event.load(action.getComponent(), xx, context);
					action.addTipiEvent(event);
				}
			}
		}
	}

	private XMLElement getEventDefFromClassDef(XMLElement def, String eventName) {
		List<XMLElement> v = def.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement child = v.get(i);
			if ("events".equals(child.getName())) {
				List<XMLElement> eventChildren = child.getChildren();
				for (int j = 0; j < eventChildren.size(); j++) {
					XMLElement eventChild = eventChildren.get(j);
					String eventChildName = eventChild
							.getStringAttribute("name");
					if (eventChildName == null) {
						eventChildName = eventChild.getName();
					}
					if (eventName.equals(eventChildName)) {
						return eventChild;
					}

				}
			}
		}
		return null;
	}

	
}
