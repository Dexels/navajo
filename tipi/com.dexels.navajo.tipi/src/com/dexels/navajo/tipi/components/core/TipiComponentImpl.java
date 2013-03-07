package com.dexels.navajo.tipi.components.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiExtension;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.notifier.SerializablePropertyChangeListener;
import com.dexels.navajo.tipi.PropertyHandler;
import com.dexels.navajo.tipi.PropertyLinkRequest;
import com.dexels.navajo.tipi.PropertyValidatable;
import com.dexels.navajo.tipi.ScopeLimit;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiDataComponent;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.TipiSuspendException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.adapter.BaseAdapter;
import com.dexels.navajo.tipi.internal.AttributeRef;
import com.dexels.navajo.tipi.internal.MessageComponent;
import com.dexels.navajo.tipi.internal.PropertyComponent;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiLayout;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipilink.TipiLink;

public abstract class TipiComponentImpl implements TipiEventListener,
		TipiComponent, TipiLink, Serializable {
	private static final long serialVersionUID = -4241997420431509085L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiComponentImpl.class);
	public abstract Object createContainer();

	private Object myContainer = null;
	private Object myParentContainer = null;
	private Object myConstraints;
	protected TipiContext myContext = null;
	protected Navajo myNavajo = null;
	protected String myName;
	protected String myId;
	protected TipiComponent myParent = null;
	protected boolean isHomeComponent = false;
	
	
	// Maps a propertyPath onto a set of attributes
	private Map<String, Set<PropertyLinkRequest>> linkMap = new HashMap<String, Set<PropertyLinkRequest>>();
	private Set<String> allLinks = new HashSet<String>();

	private final Map<String, TipiComponent> tipiComponentMap = new HashMap<String, TipiComponent>();
	private final Map<String, XMLElement> localMethodsMap = new HashMap<String, XMLElement>();
	private final Map<String, Object> localValuesMap = new HashMap<String, Object>();

	protected final List<PropertyComponent> properties = new ArrayList<PropertyComponent>();
	protected final List<MessageComponent> messages = new ArrayList<MessageComponent>();

	protected final List<TipiEvent> myEventList = new ArrayList<TipiEvent>();
	private final List<TipiComponent> tipiComponentList = new ArrayList<TipiComponent>();
	// private final Map<String, String> detectedExpressions = new
	// HashMap<String, String>();
	private final List<String> componentEvents = new ArrayList<String>();
	private final Map<String, TipiValue> componentValues = new HashMap<String, TipiValue>();
	private final Map<String, TipiComponentMethod> componentMethods = new HashMap<String, TipiComponentMethod>();
	private final List<TipiHelper> myHelpers = new ArrayList<TipiHelper>();
	protected final Map<String, String> aliasMap = new HashMap<String, String>();

	private boolean hadConditionErrors = false;
	private boolean isVisibleElement = false;
	private boolean isToplevel = false;
	private TipiLayout currentLayout = null;
	private boolean isPropertyComponent = false;
	private boolean isDisposed = false;
	public boolean isTransient = false;
	private final Map<String, Property> componentAttributes = new HashMap<String, Property>();
	protected Message stateMessage = null;
	private final List<PropertyChangeListener> myContainerListeners = new LinkedList<PropertyChangeListener>();
	private final Map<String, PropertyChangeListener> myDataListeners = new HashMap<String, PropertyChangeListener>();
	private String extension;
	private String componentType;

	private TipiExtension myParentExtension;

	public TipiExtension getParentExtension() {
		return myParentExtension;
	}

	public void setParentExtension(TipiExtension myParentExtension) {
		this.myParentExtension = myParentExtension;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getExtension() {
		return extension;
	}

	public void removeFromContainer(Object c) {
		logger.debug("REMOVE FROM CONTAINER IGNORED: NOT IMPLEMENTED. CLASS: "
						+ getClass());
	}

	public boolean isServiceRoot() {
		return false;
	}

	public TipiDataComponent getServiceRoot() {
		if (isServiceRoot()) {
			return (TipiDataComponent) this;
		}
		if (getTipiParent() == null) {
			return null;
		}
		return getTipiParent().getServiceRoot();
	}

	public void addToContainer(Object c, Object constraints) {
		throw new UnsupportedOperationException(
				"Can not add to container of class: " + getClass());
	}

	public Object getContainerLayout() {
		// override to be useful
		return null;
	}

	public void clearConditionErrors() {
		// throw new UnsupportedOperationException("clearConditionErrors: Dont
		// know what to do");
	}

	public void setContainerLayout(Object layout) {
		// override to be useful
	}

	public TipiContext getContext() {
		if (myContext == null) {
			throw new RuntimeException(
					"TipiComponent without context. This is not allowed");
		}
		return myContext;
	}

	public void initContainer() {
		if (getContainer() == null) {
			setContainer(createContainer());
		}
	}

	public void deregisterEvent(TipiEvent e) {
		this.removeTipiEvent(e);
		helperDeregisterEvent(e);
	}

	public void setName(String name) {
		myName = name;
	}

	public void setContext(TipiContext tc) {
		myContext = tc;
	}

	// public void setTransient(boolean b) {
	// isTransient = b;
	// }
	//
	// public boolean isTransient() {
	// return isTransient;
	// }

	public boolean isPropertyComponent() {
		return isPropertyComponent;
	}

	// public boolean isValueSet(String name) {
	// return detectedExpressions.containsKey(name);
	// }

	// public void unSetValue(String name) {
	// setValue(name, getTipiValue(name).getDefaultValue(), this, true, null);
	// detectedExpressions.remove(name);
	// }

	public void setPropertyComponent(boolean b) {
		isPropertyComponent = b;
	}

	public void setValue(String name, Object value) {
		setValue(name, value, this, false, null);
	}

	public void animateValue(String name, Object value) {
		getContext().animateProperty(getAttributeProperty(name), 1000, value);
		setValue(name, value, this, false, null);
	}

	private final void setValue(String name, Object value,
			TipiComponent source, boolean defaultValue, TipiEvent event) {
		setValue(name, "" + value, value);
	}

	private final void setValue(String name, String expression, Object value
			) {
		if (value instanceof PropertyLinkRequest) {
			PropertyLinkRequest linkRequest = (PropertyLinkRequest) value;
			linkRequest.setAttributeName(name);
			addLinkRequest(linkRequest);
			return;
		}

		Property tv = getAttributeProperty(name);

		if (tv == null) {
			throw new UnsupportedOperationException("Setting value: " + name
					+ " in: " + getClass() + " is not supported!");
		}
		if ("out".equals(tv.getDirection())) {
			throw new UnsupportedOperationException("Setting value: " + name
					+ " in: " + getClass() + " is has out direction!");
		}
		String type = tv.getType();

		if ((myContext.getClassManager().isValidType(type))) {
			try {
				// if (!defaultValue) {
				// detectedExpressions.put(name, expression);
				// }
				if ("selection".equals(type)) {
					Selection ss = tv.getSelection((String) value);
					if (ss == null) {
						throw new RuntimeException(this.getName()
								+ ": Invalid selection value [" + value
								+ "] for attribute " + name
								+ ", valid values are: "
								+ tv.getAllSelections());
					} else {
						setComponentValue(name, value);
						tv.setAnyValue(value);
						return;
					}
				} else {
					setComponentValue(name, value);
					tv.setAnyValue(value);

				}
				return;
			} catch (Throwable e) {
				myContext.showInternalError("Error in setter: " + name
						+ " for component: " + getPath() + " proposed value: "
						+ value + "\nFaillure: " + e.getMessage());
				logger.error("Error: ",e);
			}
		} else {
			setComponentValue(name, value);
			throw new RuntimeException(
					"Attribute type not specified in CLASSDEF: " + type+" name: "+name+" value: "+value);
		}
	}

	public Set<PropertyLinkRequest> getLinkAttributes(String propertyPath) {
		return linkMap.get(propertyPath);
	}

	// public Set<String> getLinkAttributes() {
	// return allLinks;
	// }

	// TODO FIXME ETC: Store and release PropertyHandlers

	public void loadPropertiesFromNavajo(Navajo n) {
		for (String currentPropertyPath : allLinks) {
			Property p = n.getProperty(currentPropertyPath);
			Set<PropertyLinkRequest> attributes = linkMap
					.get(currentPropertyPath);
			// PropertyHandler ph =
			new PropertyHandler(this, p, attributes);
		}
	}

	public void loadPropertiesFromMessage(Message m) {
		for (String currentPropertyPath : allLinks) {
			Property p = m.getProperty(currentPropertyPath);
			Set<PropertyLinkRequest> attributes = linkMap
					.get(currentPropertyPath);
			// PropertyHandler ph =
			new PropertyHandler(this, p, attributes);
		}
	}

	private void addLinkRequest(PropertyLinkRequest value) {
		String propertyPath = value.getPropertyName();
		Set<PropertyLinkRequest> registered = linkMap.get(propertyPath);
		if (registered == null) {
			registered = new HashSet<PropertyLinkRequest>();
			linkMap.put(propertyPath, registered);
		}
		registered.add(value);
		allLinks.add(propertyPath);
		TipiDataComponent td = getServiceRoot();
		if (td != null) {
			td.registerPropertyChild(this);
		}
	}

	public Object getValue(String name) {

		TipiValue tv = componentValues.get(name);
		if (tv == null) {
			throw new UnsupportedOperationException("Getting value: " + name
					+ " in: " + getClass() + " is not supported!");
		}
		Object propval = getAttributeProperty(name).getTypedValue();
		Object oo = getComponentValue(name);
		if (oo == null) {
		} else {
			if (!oo.equals(propval)) {
				getAttributeProperty(name).setAnyValue(oo);
			}
		}
		/**
		 * I should do an extra typecheck here, to check the supplied type
		 * against the TipiValue.
		 */
		return getAttributeProperty(name).getTypedValue();
	}

	public Property getAttributeProperty(String name) {
		return componentAttributes.get(name);
	}

	private Property createAttributeProperty(TipiValue tv) {
		return tv.createProperty();
	}

	/**
	 * Loads an event definition from the component definition
	 */
	public void loadEventsDefinition(TipiContext context,
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
				if (componentEvents.contains(type)) {
					TipiEvent event = new TipiEvent();
					XMLElement eventDef = getEventDefFromClassDef(classDef,
							type);
					event.init(eventDef);
					event.load(this, xx, context);
					addTipiEvent(event);
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

	public void loadMethodDefinitions(TipiContext context,
			XMLElement definition, XMLElement classDef) throws TipiException {
		List<XMLElement> defChildren = definition.getChildren();
		for (int i = 0; i < defChildren.size(); i++) {
			XMLElement xx = defChildren.get(i);
			if (xx.getName().equals("method")) {
				// String type = xx.getStringAttribute("type");
				TipiComponentMethod tcm = new TipiComponentMethod(this);
				tcm.load(xx);
			}
		}
	}

	public void load(XMLElement definition, XMLElement instance,
			TipiContext context) throws TipiException {
		setContext(context);
		String id = instance.getStringAttribute("id");
		String name = instance.getStringAttribute("name");
		setName(name);
		if (id == null || id.equals("")) {
			myId = name;
		} else {
			myId = id;
		}
		String constraint = (String) instance.getAttribute("constraint");
		if (constraint == null) {
			constraint = (String) definition.getAttribute("constraint");
		}
		if (constraint != null) {
			setConstraints(constraint);
		}
		List<XMLElement> children = null;
		if (instance.getAttribute("class") != null
				|| instance.getAttribute("type") != null) {
			children = instance.getChildren();
		} else {
			children = definition.getChildren();
		}
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			if (child.getName().equals("layout")
					|| child.getName().startsWith("l.")) {
				instantiateWithLayout(child);
			} else if (child.getName().equals("globalmethod"))
			{
				myContext.addGlobalMethod(child);
			} else if (child.getName().equals("localmethod"))
			{
				this.getScopeHomeComponent().addLocalMethod(child);
			} else {
				if (child.getName().equals("tipi-instance")
						|| child.getName().equals("component-instance")
						|| child.getName().equals("component")
						|| child.getName().startsWith("c.")) {
					addComponentInstance(myContext, child,
							child.getAttribute("constraint"));
				}
			}
		}

	}

	private final void instantiateWithLayout(XMLElement x) throws TipiException {
		TipiLayout tl = myContext.instantiateLayout(x, this);
		if (tl == null) {
			throw new RuntimeException(
					"Trying to instantiate with layout, but the layout == null");
		}
		tl.setComponent(this);
		setLayout(tl);
		tl.createLayout();
		tl.initializeLayout(x);
		if (getContainer() != null) {
			setContainerLayout(tl.getLayout());
		}
		tl.loadLayout(this);
		tl.commitLayout();
	}

	public void setId(String id) {
		myId = id;
	}

	private final void removeChildComponent(TipiComponent tc) {
		if (!tipiComponentMap.containsValue(tc)) {
			logger.warn("!!!!!!   Can not removeChildComponent: Component not found.");
		}
		if (!tipiComponentList.contains(tc)) {
			logger.warn("!!!!!!   Can not removeChildComponent : Component not found in list.");
		}
		if (!tipiComponentMap.containsKey(tc.getId())) {
			logger.warn("!!!!!!   Can not removeChildComponent: Component not found in map.");
		}
		tipiComponentList.remove(tc);
		tipiComponentMap.remove(tc.getId());
	}

	private final void addChildComponent(TipiComponent tc) {
		if (tipiComponentList.contains(tc)) {
			logger.warn("!!!!!!   Can not add child with id: "
					+ tc.getId() + " component already in list, removing old reference");
			tipiComponentList.remove(tc);
		}
		if (tipiComponentMap.containsValue(tc)) {
			logger.warn("!!!!!!   Can not add child with id:"
					+ tc.getId() + " component already in map, removing old reference");
			tipiComponentMap.remove(tc.getId());
		}
		tipiComponentMap.put(tc.getId(), tc);
		tipiComponentList.add(tc);
	}

	public final void clearAllComponents() {
		tipiComponentMap.clear();
		tipiComponentList.clear();
	}

	public final boolean isVisibleElement() {
		return isVisibleElement;
	}

	public final boolean isTopLevel() {
		return isToplevel;
	}

	public void instantiateComponent(XMLElement instance, XMLElement classdef)
			throws TipiException {
		String id = (String) instance.getAttribute("id");
		String defname = (String) instance.getAttribute("name");
		isVisibleElement = classdef.getStringAttribute("addtocontainer",
				"false").equals("true");
		isToplevel = classdef.getStringAttribute("toplevel", "false").equals(
				"true");

		if (id == null || "".equals(id)) {
			// pretty deprecated. I don't like it.
			// if(defname!=null) {
			// id = defname;
			// }
			id = myContext.generateComponentId(null, this);
		}
		setExtension(classdef.getStringAttribute("extension"));
		setComponentType(classdef.getStringAttribute("name"));
		// stateMessage = getStateMessage();//
		// NavajoFactory.getInstance().createMessage(myContext.getStateNavajo(),
		// id!=null?id:"Unknown");
		XMLElement definitionXml = null;
		if (defname != null && !instance.getName().startsWith("c.")) {
			definitionXml = myContext.getComponentDefinition(defname);
		}

		myId = id;
		List<XMLElement> children = classdef.getChildren();
		initBeforeBuildingChildren(instance, classdef, definitionXml);
		for (int i = 0; i < children.size(); i++) {
			XMLElement xx = children.get(i);
			if ("events".equals(xx.getName())) {
				loadEvents(xx, classdef);
			}
			if ("values".equals(xx.getName())) {
				loadValues(xx, null);
			}
			if ("methods".equals(xx.getName())) {
				loadMethods(xx);
			}
		}

	}

	private void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * If you want to do something (retrieve some data from this instance, or
	 * whatever) BEFORE the kids are created. I created this construction to
	 * accommodate the TipiGridPanel, where I needed to find the width of the
	 * grid before parsing all the children. So if that is the case, override
	 * this function. Normally it does nothing.
	 * 
	 * @param instance
	 * @param classdef
	 */
	public void initBeforeBuildingChildren(XMLElement instance,
			XMLElement classdef, XMLElement definition) {
	}

	public void loadStartValues(XMLElement element, TipiEvent event) {
		Iterator<String> it = componentValues.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			TipiValue tv = componentValues.get(key);
			String value = element.getStringAttribute(key);
			if (value != null) {
				if (tv.getType().equals("out")) {
					throw new RuntimeException(
							"You cannot pass the value of an 'out' direction value in to an instance or definition in the script");
				}
				Operand o = evaluate(value.toString(), this, event);
				if (o != null && o.value != null) {
					setValue(key, value, o.value);
				} else {
					logger.error("Evaluation error: " + value);
					setValue(key, value);
				}
			}
		}
	}

	/**
	 * Loads all the allowed event from the classdefinition
	 * @param classdef 
	 */
	private final void loadEvents(XMLElement events, XMLElement classdef) {
		List<XMLElement> children = events.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement xx = children.get(i);
			String eventName = xx.getStringAttribute("name");
			componentEvents.add(eventName);
		}
	}

	/**
	 * Loads all the allowed methods from the classdefinition
	 */
	private final void loadMethods(XMLElement events) {
		List<XMLElement> children = events.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement xx = children.get(i);
			String methodName = xx.getStringAttribute("name");
			TipiComponentMethod tcm = new TipiComponentMethod(this);
			tcm.load(xx);
			componentMethods.put(methodName, tcm);
		}
	}

	public boolean isReusable() {
		return false;
	}

	public void reUse() {
		// no action
	}

	public void bind(final Object c, final String propertyName,
			final String containerPropertyName) throws TipiException {
		final Property p = getAttributeProperty(propertyName);
		PropertyChangeListener pcl = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if (containerPropertyName.equals(e.getPropertyName())) {
					p.setAnyValue(e.getNewValue());
				}

			}
		};
		myContainerListeners.add(pcl);
		Method m;
		try {
			m = c.getClass().getMethod("addPropertyChangeListener",
					new Class[] { PropertyChangeListener.class });
			m.invoke(c, new Object[] { pcl });
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			throw new TipiException("Trouble binding: " + this
					+ " conainerProperty: " + containerPropertyName, e);
		}

		PropertyChangeListener propListener = new SerializablePropertyChangeListener() {

			private static final long serialVersionUID = -506166614251050065L;

			public void propertyChange(PropertyChangeEvent e) {
				Object value = e.getNewValue();
				if (value != null) {
					try {
						doCallSetter(c, containerPropertyName, value);
					} catch (Throwable ee) {
						logger.error("Error: ",e);
					}
				}
			}

			public String toString() {
				try {
					return "TipiComponentBoundProp: " + p.getFullPropertyName()
							+ ":" + p.hashCode();
				} catch (NavajoException e) {
					logger.error("Error: ",e);
				}
				return "TipiComponentBoundPropKaboem";
			}
		};
		p.addPropertyChangeListener(propListener);
	}

	private String getSetterName(String propertyName) {
		return "set" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1, propertyName.length());
	}

	protected void doCallSetter(Object component, String propertyName,
			Object param) {
		try {
			callSetter(component, propertyName, param);
		} catch (Throwable e) {
			logger.error("Error: ",e);
			myContext.showInternalError("Error setting value: " + propertyName
					+ " to: " + param, e);
		}
	}

	private void callSetter(Object component, String propertyName, Object param)
			throws SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		String b = getSetterName(propertyName);
		Class<?> paramType = param.getClass();

		// Isn't there some clever autoboxing to this kind of sh!t?
		if (paramType.equals(Integer.class)) {
			paramType = int.class;
		}
		if (paramType.equals(Float.class)) {
			paramType = float.class;
		}
		if (paramType.equals(Double.class)) {
			paramType = double.class;
		}
		if (paramType.equals(Long.class)) {
			paramType = long.class;
		}
		if (paramType.equals(Boolean.class)) {
			paramType = boolean.class;
		}
		Method m;
		try {
			m = component.getClass().getMethod(b, new Class[] { paramType });
			m.invoke(component, new Object[] { param });
		} catch (NoSuchMethodException e) {
			Method[] mm = component.getClass().getMethods();
			for (int i = 0; i < mm.length; i++) {
				if (mm[i].getName().equals(b)) {
					Class<?>[] c = mm[i].getParameterTypes();
					if (c.length == 1) {
						if (c[0].isAssignableFrom(paramType)) {
							mm[i].invoke(component, new Object[] { param });
							return;
						}
					}
				}
			}
		}
	}

	/**
	 * Loads the attributes of an instance into a component. You may need to
	 * override it if your instance
	 * 
	 * @param values
	 * @param event
	 * @throws TipiException
	 */
	protected void loadValues(XMLElement values, TipiEvent event)
			throws TipiException {
		List<XMLElement> children = values.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement xx = children.get(i);

			String valueName = xx.getStringAttribute("name");
			TipiValue tv = new TipiValue(this);
			tv.load(xx);
			componentValues.put(valueName, tv);
			Property attrProp = createAttributeProperty(tv);
			componentAttributes.put(valueName, attrProp);
			getStateMessage().addProperty(attrProp);
			if (tv.getValue() != null && !"".equals(tv.getValue())) {
				try {
					setValue(tv.getName(),
							evaluate(tv.getValue(), this, event).value, this,
							true, event);
				} catch (Throwable ex) {
					setValue(tv.getName(), tv.getValue(), this, true, event);
				}
			}
			String containerProperty = xx.getStringAttribute("property");
			if (containerProperty != null) {
				bind(getActualComponent(), valueName, containerProperty);

			}
			attrProp.forcePropertyChange();
		}
	}

	public final String getId() {
		if (myId == null) {
			myId = myContext.generateComponentId(getTipiParent(), this);
		}
		return myId;
	}

	public final Message getStateMessage() {
		if (stateMessage != null) {
			return stateMessage;
		}
		Navajo stateNavajo = myContext.getStateNavajo();
		stateMessage = NavajoFactory.getInstance().createMessage(stateNavajo,
				getId());
		if (getTipiParent() != null) {
			getTipiParent().getStateMessage().addMessage(stateMessage);
		}
		return stateMessage;
	}

	public final void performMethod(String methodName,
			Map<String, Object> params, TipiAction invocation, TipiEvent event) {
		TipiComponentMethod tcm = componentMethods.get(methodName);
		if (tcm == null) {
			logger.error("Could not find component method: " + methodName
					+ " component: " + getPath() + " class: " + getClass()+" ignoring call.");
		} else {
			tcm.loadInstance(invocation);
			tcm.loadParams(params);
			performComponentMethod(methodName, tcm, event);
		}
	}

	public final void performMethod(String methodName, TipiAction invocation,
			TipiEvent event) throws TipiBreakException {
		TipiComponentMethod tcm = componentMethods.get(methodName);
		if (tcm == null) {
			logger.error("Could not find component method: " + methodName
					+ " component: " + getPath() + " class: " + getClass()+" ignoring call.");
		} else {
			tcm.loadInstance(invocation);
			performComponentMethod(methodName, tcm, event);
		}
	}

	public final TipiComponentMethod getTipiComponentMethod(String methodName) {
		TipiComponentMethod tcm = componentMethods.get(methodName);
		return tcm;
	}

	/**
	 * @param name  
	 * @param compMeth 
	 * @param event 
	 */
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
	}

	/**
	 * beware, leading slashes will be stripped. To do a global lookup, use the
	 * TipiContext
	 */
	public TipiComponent getTipiComponentByPath(String path) {
		if (path.equals(".")) {
			return this;
		}
		if (path.equals("..")) {
			return myParent;
		}
		if (path.startsWith("./")) {
			return getTipiComponentByPath(path.substring(2));
		}
		if (path.startsWith("../")) {
			return myParent.getTipiComponentByPath(path.substring(3));
		}
		if (path.indexOf("/") == 0) {
			path = path.substring(1);
		}
		int s = path.indexOf("/");
		// HUH?
		if (s == -1) {
			if (path.equals("")) {
				return myContext.getDefaultTopLevel();
			}
			return getTipiComponent(path);
		} else {
			String name = path.substring(0, s);
			String rest = path.substring(s);
			TipiComponent t = getTipiComponent(name);
			if (t == null) {
				throw new NullPointerException("Did not find Tipi: " + name);
			}
			return t.getTipiComponentByPath(rest);
		}
	}

	public void setLayout(TipiLayout tl) {
		currentLayout = tl;
	}

	public final TipiLayout getLayout() {
		return currentLayout;
	}

	public final TipiComponent getTipiComponent(String s) {
		return tipiComponentMap.get(s);
	}

	public final TipiComponent getTipiComponent(int i) {
		// if (tipiComponentMap.size()!=tipiComponentList.size()) {
		// }
		return tipiComponentList.get(i);
	}

	public Iterable<TipiComponent> getChildren() {
		return new ArrayList<TipiComponent>(tipiComponentList);
	}

	protected final TipiComponent getChildByContainer(Object container) {
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent child = getTipiComponent(i);
			if (child != null) {
				if (container == child.getContainer()) {
					return child;
				}
			}
		}
		return null;
	}

	/**
	 * Disposes this component: Beware: Should not be called directly. To
	 * programatically dispose a component, call
	 * TipiContext.disposeComponent(component)
	 */
	public void disposeComponent() {
		if (getContainer() != null) {
			for (PropertyChangeListener p : myContainerListeners) {
				try {
					Object c = getContainer();
					Method m = c.getClass().getMethod(
							"removePropertyChangeListener",
							new Class[] { PropertyChangeListener.class });
					if (m == null) {
						continue;
					}
					m.invoke(c, new Object[] { p });
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				} catch (Exception e) {
					logger.error("Error: ",e);
				}
			}
		}
		for (String ss : myDataListeners.keySet()) {
			Property p = getAttributeProperty(ss);
			if (p != null) {
				p.removePropertyChangeListener(myDataListeners.get(ss));
			}
		}
		// try {
		// myContext.unlink(getStateMessage());
		// } catch (NavajoException e) {
		// logger.error("Error: ",e);
		// }
		removeAllChildren();
		clearAllComponents();
		helperDispose();
		isDisposed = true;
		myContainerListeners.clear();

	}

	public final boolean isDisposed() {
		return isDisposed;
	}

	public final void removeAllChildren() {
		ArrayList<TipiComponent> backup = new ArrayList<TipiComponent>(
				tipiComponentList);
		for (int i = 0; i < backup.size(); i++) {
			TipiComponent current = backup.get(i);
			// myContext.disposeTipiComponent(current);
			current.disposeComponent();
		}
		clearAllComponents();
	}

	public List<PropertyComponent> getRecursiveProperties() {
		List<PropertyComponent> al = new ArrayList<PropertyComponent>();
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent tc = getTipiComponent(i);
			al.addAll(tc.getRecursiveProperties());
		}
		al.addAll(properties);
		return al;
	}

	public void removeChild(TipiComponent child) {
		if (child == null) {
			logger.warn("Null child... Can not proceed with deleting.");
			return;
		}
		if (!tipiComponentMap.containsValue(child)) {
			logger.warn("Can not dispose! No such component. I am "
					+ getId() + " my class: " + getClass() + " child id: "
					+ child.getId());
			return;
		}
		Object c = child.getContainer();
		if (c != null) {
			removeFromContainer(c);
		}
		if (PropertyComponent.class.isInstance(child)) {
			logger.debug("Removing property");
			properties.remove(child);
		}
		if (MessageComponent.class.isInstance(child)) {
			messages.remove(child);
		}
		removeChildComponent(child);
	}

	public void setParent(TipiComponent tc) {
		myParent = tc;
		if (myParent != null) {
			tc.getStateMessage().addMessage(getStateMessage());
		}
	}

	public TipiComponent getTipiParent() {
		return myParent;
	}

	public void addComponent(TipiComponent c, TipiContext context, Object td) {
		addComponent(c, -1, context, td);
	}

	public void addComponent(final TipiComponent c, int index,
			TipiContext context, Object td) {
		if (td == null && getLayout() != null) {
			td = getLayout().createDefaultConstraint(tipiComponentList.size());
			if (td != null) {
				c.setConstraints(td);
			}
		}
		if (c.getId() == null) {
			logger.warn("Warning: null id.");
		}
		/**
		 * @TODO Fix following scenario: What should happen when a component is
		 *       added with the same id?
		 */
		if (tipiComponentMap.containsKey(c.getId())) {
			logger.warn("Adding component which is already present. ID: "+c.getId()+ " parent: "+ getPath());
		}
		addChildComponent(c);
		c.setParent(this);

		TipiDataComponent cparent = c.getServiceRoot();
		if (cparent != null) {
			cparent.registerPropertyChild(c);
		}
		getStateMessage().addMessage(c.getStateMessage());
		if (getContainer() != null && c.isVisibleElement()) {
			addToContainer(c.getContainer(), td);
			addedToParent();
		} else {
		}
		c.addedToParentContainer(this, getActualComponent(), c.getContainer(),
				td);
		if (c.isPropertyComponent() && c instanceof PropertyComponent) {
			properties.add((PropertyComponent) c);
		} else {
		}
		if (c instanceof MessageComponent) {
			messages.add((MessageComponent) c);
		}

		getStateMessage().addMessage(c.getStateMessage());

		/**
		 * @todo Beware: I think this means that the onInstantiate event is
		 *       never called on a toplevel component
		 */

		// I placed this in a invokeAndWait clause, which is conceptually not
		// correct, especially when
		// if we are using a non-swing implementation. Need to fix.
		// placing it in this thread gives _occasional_ freezes on startup.
		// As far as I know, it only happens when the performTipiEvent is called
		// from the main thread
		try {
			c.performTipiEvent("onInstantiate", null, true);
		} catch (TipiException ex) {
			logger.error("Error: ",ex);
		} catch (TipiBreakException e) {
		}
	}

	public void addedToParentContainer(TipiComponent parentTipiComponent,
			Object parentContainer, Object container, Object constriants) {

	}

	public Navajo getNavajo() {
		return myNavajo;
	}

	public Navajo getNearestNavajo() {
		if (myNavajo != null) {
			return myNavajo;
		}
		if (myParent == null) {
			return null;
		}
		return myParent.getNearestNavajo();
	}

	public void setConstraints(Object constraints) {
		myConstraints = constraints;
	}

	public Object getConstraints() {
		return myConstraints;
	}

	public void addTipiEvent(TipiEvent te) {
		myEventList.add(te);
		helperRegisterEvent(te);
	}

	public void removeTipiEvent(TipiEvent e) {
		myEventList.remove(e);
		helperDeregisterEvent(e);
	}

	public void refreshParent() {
		if (getTipiParent() == null) {
			logger.warn("Can not refresh parent: No parent present!");
			return;
		}
		if (TipiDataComponent.class.isInstance(getTipiParent())) {
			((TipiDataComponent) getTipiParent()).refreshLayout();
		} else {
			System.err
					.println("Can not refresh parent: Parent is not a tipi. THIS is a bit weird, actually.");
		}
	}

	public boolean performTipiEvent(String type, Map<String, Object> event,
			boolean sync) throws TipiException, TipiBreakException {
		return performTipiEvent(type, event, sync, null);
	}

	public boolean performTipiEvent(String type, Map<String, Object> event,
			boolean sync, Runnable afterEvent) throws TipiBreakException {
		boolean hasEventType = false;
		for (int i = 0; i < myEventList.size(); i++) {
			TipiEvent te = myEventList.get(i);
			if (te.isTrigger(type)) {
				myContext.fireTipiContextEvent(this, type, event, sync);
				hasEventType = true;
				TipiExecutable parentEvent = null;

				if (sync) {
					try {
						te.performAction(this, parentEvent, event);
					} catch (TipiSuspendException e) {
						// ignore, so do fire all the other events
					} catch (TipiBreakException e) {
						// logger.error("Error: ",e);
						throw (e);
					} catch (NavajoException e) {
//						getContext().showInternalError(
//								"Error performing event: " + te.getEventName()
//										+ " for component: "
//										+ te.getComponent().getPath(), e);
//						logger.error("Error: ",e);
						te.dumpStack(e.getMessage());
						throw e;
					} catch(Exception e) {
						getContext().showInternalError(
								"Error performing event: " + te.getEventName()
										+ " for component: "
										+ te.getComponent().getPath(), e);
						logger.error("Error: ",e);
						te.dumpStack(e.getMessage());
					} finally {
						if (afterEvent != null) {
							afterEvent.run();
						}
					}
				} else {
					try {
						if (te.checkCondition(te)) {
							te.asyncPerformAction(this, parentEvent, event,
									afterEvent);
						}
					} catch (Throwable e) {
						getContext().showInternalError(
								"Error performing event: " + te.getEventName()
										+ " for component: "
										+ te.getComponent().getPath(), e);
						logger.error("Error: ",e);
					}
				}
			}
		}
		if (!hasEventType) {
			if (afterEvent != null) {
				afterEvent.run();
			}
		}
		return hasEventType;
	}

	public void eventStarted(TipiExecutable te, Object event) {
	}

	public void eventFinished(TipiExecutable te, Object event) {
	}

	protected Operand evaluate(String expr, TipiComponent source,
			TipiEvent event) {
		return myContext.evaluate(expr, source, event);
	}

	public String getName() {
		return myName;
	}

	public Object getContainer() {
		return myContainer;
	}

	public void replaceContainer(Object c) {
		myContainer = c;
	}

	public void setContainer(Object c) {
		if (getContainer() == null) {
			replaceContainer(c);
		}
	}

	public void clearContainer() {
		myContainer = null;
	}

	protected void setComponentValue(String name, Object object) {
		// myContext.animateProperty(getAttributeProperty(name), 1000, object);
		getAttributeProperty(name).setAnyValue(object);
		// valuesSet.add(name);
		// helperSetComponentValue(name, object);
	}

	protected Object getComponentValue(String name) {
		return helperGetComponentValue(name);
	}

	public void setCursor(int cursorid) {
	}

	public void resetComponentValidationStateByRule(String id) {
		if (myContainer != null) {
			Iterator<TipiComponent> it = tipiComponentList.iterator();
			while (it.hasNext()) {
				TipiComponent next = it.next();
				next.resetComponentValidationStateByRule(id);
			}
			// hadConditionErrors = true;
			if (PropertyValidatable.class.isInstance(myContainer)) {
				PropertyValidatable p = (PropertyValidatable) myContainer;
				p.resetComponentValidationStateByRule(id);
			}
		}
	}

	public boolean hasConditionErrors() {
		return hadConditionErrors;
	}

	public int getChildCount() {
		return tipiComponentList.size();
	}

	public boolean hasPath(String path, TipiEvent event) {
		if (path.equals("*")) {
			return true;
		}
		TipiComponent tc = (TipiComponent) myContext.getClassManager().parse(this, "component",
				path, event);
		return tc == this;
	}

	public String getPath() {
		if (this instanceof TipiDataComponent) {
			return getPath("tipi:/");
		} else {
			return getPath("component:/");
		}
	}

	public String getPath(String typedef) {
		if (getTipiParent() == null) {
			return typedef + "/" + getId();
		} else {
			return getTipiParent().getPath(typedef) + "/" + getId();
		}
	}

	public AttributeRef getAttributeRef(String name) {
		return new AttributeRef(this, name);
	}

	public List<TipiEvent> getEventList() {
		return myEventList;
	}

	public void componentInstantiated() {
	}

	public void addHelper(TipiHelper th) {
		myHelpers.add(th);
	}

	public void removeHelper(TipiHelper th) {
		myHelpers.add(th);
	}

	protected void helperSetComponentValue(String name, Object object) {
		for (int i = 0; i < myHelpers.size(); i++) {
			TipiHelper current = myHelpers.get(i);
			current.setComponentValue(name, object);
		}
	}

	protected Object helperGetComponentValue(String name) {
		for (int i = 0; i < myHelpers.size(); i++) {
			TipiHelper current = myHelpers.get(i);
			Object o = current.getComponentValue(name);
			if (o != null) {
				return o;
			}
		}
		return null;
	}

	protected void helperRegisterEvent(TipiEvent te) {
		for (int i = 0; i < myHelpers.size(); i++) {
			TipiHelper current = myHelpers.get(i);
			current.registerEvent(te);
		}
	}

	protected void helperDeregisterEvent(TipiEvent te) {
		for (int i = 0; i < myHelpers.size(); i++) {
			TipiHelper current = myHelpers.get(i);
			current.deregisterEvent(te);
		}
	}

	private final void helperDispose() {
		for (int i = 0; i < myEventList.size(); i++) {
			TipiEvent current = myEventList.get(i);
			helperDeregisterEvent(current);
		}
	}

	protected void addedToParent() {
	}

	public int getIndex(TipiComponent node) {
		return tipiComponentList.indexOf(node);
	}

	public TipiComponent addComponentInstance(TipiContext context,
			XMLElement inst, Object constraints) throws TipiException {
		// TODO add TipiEvent as parameter
		TipiComponent ti = (context
				.instantiateComponent(inst, null, null, this));
		if (ti.getId() == null) {
			ti.setId(myContext.generateComponentId(this, ti));
		}
		ti.setParent(this);
		ti.setConstraints(constraints);
		addComponent(ti, context, constraints);

		return ti;
	}

	// This method actually implements the TipiLink interface

	private TipiEvent lastEvent = null;
	private String componentClassName;

	public void setCurrentEvent(TipiEvent event) {
		lastEvent = event;
	}

	public Object evaluateExpression(String expression) throws Exception {
		return myContext.evaluateExpression(expression, this, lastEvent);
	}

	public void commitToUi() {
		for (int i = 0; i < tipiComponentList.size(); i++) {
			TipiComponent current = tipiComponentList.get(i);
			current.commitToUi();
		}
	}

	public Object getActualComponent() {
		return getContainer();
	}

	public String toString() {
		return "{" + getPath() + " / " + getClass() + " / " + hashCode() + "}";
	}

	public String childDump() {
		return "LIST: " + tipiComponentList + " MAP: " + tipiComponentMap;
	}

	/**
	 * If a component has a custom load method, override this method and
	 * enumerate all the possible tags, so it can identify its events.
	 * 
	 * @return
	 */
	public String[] getCustomChildTags() {
		return new String[] {};
	}

	public boolean isHomeComponent() {
		return isHomeComponent;
	}

	public void setHomeComponent(boolean isHomeComponent) {
		this.isHomeComponent = isHomeComponent;
	}

	public TipiComponent getHomeComponent() {
		if (isHomeComponent()) {
			return this;
		}
		if (getTipiParent() == null) {
			return null;
		}
		return getTipiParent().getHomeComponent();
	}

	public String getAlias(String expression) {
		return aliasMap.get(expression);
	}

	public void setAlias(String name, String value) {
		aliasMap.put(name, value);
	}

	public void runSyncInEventThread(Runnable r) {
		myContext.runSyncInEventThread(r);
	}

	public void runAsyncInEventThread(Runnable r) {
		myContext.runAsyncInEventThread(r);
	}

	public TipiComponent findTipiComponentById(String id) {

		logger.debug("Component: " + getId() + " - " + getClass()
				+ " looking for: " + id);
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent tc = getTipiComponent(i);
			if (id.equals(tc.getId())) {
				logger.debug("Target found!");
				return tc;
			}
		}
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent tc = getTipiComponent(i);
			TipiComponent tt = tc.findTipiComponentById(id);
			if (tt != null) {
				return tt;
			}
		}
		return null;
	}

	public BaseAdapter createAdapter(TipiAction action, TipiEvent event) {
		String nameCap = getComponentType().substring(0, 1).toUpperCase()
				+ getComponentType().substring(1, getComponentType().length());
		if (getExtension() == null) {
			logger.warn("Can not create adapter for component: "
					+ nameCap + ". Extension unknown");
		}
		String adapterName = getExtension().toLowerCase() + "." + nameCap;

		try {
			Class<?> c = Class.forName(adapterName);
			BaseAdapter b = (BaseAdapter) c.newInstance();
			b.setComponent(this);
			b.setEvent(event);
			b.setInvocation(action);
			return b;
		} catch (InstantiationException e) {
			logger.error("Error: ",e);
		} catch (IllegalAccessException e) {
			logger.error("Error: ",e);
		} catch (ClassNotFoundException e) {
			logger.error("Error: ",e);
		}
		return null;
	}
	
	public Object getParentContainer() {
		return myParentContainer;
	}
	
	public void setParentContainer(Object c) {
		myParentContainer = c;
	}

	public void setClassName(String defname) {
		this.componentClassName = defname;
	}

	public String getClassName() {
		return componentClassName;
	}
	
	public void addLocalMethod(XMLElement method) throws TipiException {
		String name = method.getStringAttribute("name");
		if (localMethodsMap.containsKey(name))
		{
			throw new TipiException("Duplicate name for localmethod definition " + method + " at component: " + this.myId);
		}
		localMethodsMap.put(name, method);
		
	}

	public XMLElement getLocalMethod(String name) {
		if(!isScopeLimit())
		{
			logger.warn("Local method is being retrieved from a non-ScopeLimit implementing component! " + this.myId);
		}
		return localMethodsMap.get(name);
	}

	public void setLocalValue(String expression, Object value){
		if(!isScopeLimit())
		{
			logger.warn("Local method is being stored in a non-ScopeLimit implementing component! " + this.myId);
		}
		localValuesMap.put(expression, value);
		
	}

	public Object getLocalValue(String expression) {
		if(!isScopeLimit())
		{
			logger.warn("Local value is being retrieved from a non-ScopeLimit implementing component! " + this.myId);
		}
		return localValuesMap.get(expression);
	}
	
	public boolean containsLocalValue(String expression){
		if(!isScopeLimit())
		{
			logger.warn("Local value is being stored in a non-ScopeLimit implementing component! " + this.myId);
		}
		return localValuesMap.containsKey(expression);
	}
	
	public boolean isScopeLimit()
	{
		return this instanceof ScopeLimit;
	}

	public TipiComponent getScopeHomeComponent() {
		if (isScopeLimit()) {
			return this;
		}
		if (getTipiParent() == null) {
			return null;
		}
		return getTipiParent().getScopeHomeComponent();
	}

}
