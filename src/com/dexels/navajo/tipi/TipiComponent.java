package com.dexels.navajo.tipi;

import java.util.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
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
public interface TipiComponent extends ConditionErrorHandler, TipiEventListener, TipiLink {
	public void removeFromContainer(Object c);

	public void addToContainer(Object c, Object constraints);



	public TipiContext getContext();

	public void initContainer();

	public void deregisterEvent(TipiEvent e);

	public void setName(String name);

	public void setContext(TipiContext tc);

	public boolean isPropertyComponent();

	public void setPropertyComponent(boolean b);

	public void setValue(String name, Object value);

	public Object getValue(String name);

//	public String getStringValue(String name);

//	public TipiValue getTipiValue(String name);

	/**
	 * Loads an event definition from the component definition
	 */
	public void loadEventsDefinition(TipiContext context, XMLElement definition, XMLElement classDef) throws TipiException;

	public void loadMethodDefinitions(TipiContext context, XMLElement definition, XMLElement classDef) throws TipiException;

	public void load(XMLElement def, XMLElement instance, TipiContext context) throws TipiException;

	public void setId(String id);

	public boolean isVisibleElement();

	public void instantiateComponent(XMLElement instance, XMLElement classdef) throws TipiException;

	public void loadStartValues(XMLElement element, TipiEvent event);

	public boolean isReusable();

	public boolean isTopLevel();

	public void reUse();

	public String getId();

	public void performMethod(String methodName, TipiAction invocation, TipiEvent event) throws TipiBreakException;

	public TipiComponentMethod getTipiComponentMethod(String methodName);

	public TipiComponent getTipiComponentByPath(String path);

	public void setLayout(TipiLayout tl);

	
	/**
	 * This is a hook to prepare the component for 'receiving children'
	 * You can for example prepare your container, based on the attributes.
	 * Some containers need to do this before children are added.
	 * You can also do it in the createContainer method, but then you don't
	 * have any access to the attributes.
	 * @param instance
	 * @param classdef
	 * @param definition
	 */
	public void initBeforeBuildingChildren(XMLElement instance, XMLElement classdef, XMLElement definition);

	public TipiLayout getLayout();

	public TipiComponent getTipiComponent(String s);

	public TipiComponent getTipiComponent(int i);

	/**
	 * Called when this component gets disposed. Do not call directly.
	 * You can override it if you want, but make sure to call the super.
	 */

	public void disposeComponent();

	
	/**
	 * removes all child components.
	 * You can override it if you want, but make sure to call the super.
	 */

	public void removeAllChildren();

	public void removeChild(TipiComponent child);

	public void setParent(TipiComponent tc);

	public TipiComponent getTipiParent();

	public void addComponent(TipiComponent c, TipiContext context, Object td);

	public void addComponent(TipiComponent c, int index, TipiContext context, Object td);

	public Navajo getNavajo();

	public Navajo getNearestNavajo();

	public void setConstraints(Object constraints);

	public Object getConstraints();

	public void addTipiEvent(TipiEvent te);

	public void removeTipiEvent(TipiEvent e);

	public void refreshParent();

	public boolean performTipiEvent(String type, Map<String, Object> event, boolean sync) throws TipiException, TipiBreakException;

	public String getName();

	public Object getContainer();

	public void setContainer(Object c);

	public void setCursor(int cursorid);


	public void checkValidation(Message msg);

	public void resetComponentValidationStateByRule(String id);

	public boolean hasConditionErrors();

	public int getIndex(TipiComponent node);

	
	public boolean hasPath(String path, TipiEvent event);

	public String getPath();

	public String getPath(String typedef);

	public AttributeRef getAttributeRef(String name);

	public List<TipiEvent> getEventList();

	public void componentInstantiated();


	public void addHelper(TipiHelper th);

	public void removeHelper(TipiHelper th);

	public int getChildCount();

	public TipiComponent addComponentInstance(TipiContext context, XMLElement inst, Object constraints) throws TipiException;

	public Object getContainerLayout();

	public void setContainerLayout(Object o);

	public boolean isDisposed();

	public void setCurrentEvent(TipiEvent event);

	public void updateId(TipiComponent tc, String oldId, String id);

	/**
	 * This returns the 'real' ui-component of this Tipi component.
	 * In most of the cases, it will return the same as getContainer();
	 * (If you don't override it, that is what you'll get)
	 * getContainer will return the ui-component that will be placed into the
	 * parent ui-component.
	 * For example, if you have a swing tipitable, the getContainer will return
	 * either a scrollbar or a panel, depending on the autoscroll version.
	 * getActualComponent will return the 'real' component, a MessageTable in 
	 * this case.
	 * @return
	 */
	public Object getActualComponent();

	/**
	 * A 'home component' is a toplevel component in a definition.
	 * As such, it can be addressed using the '~' from within that definition.
	 * 
	 * @return
	 */
	public boolean isHomeComponent();

	public void setHomeComponent(boolean isHomeComponent);

	public TipiComponent getHomeComponent();

	/*
	 * gets all the property components under this property, and also under its
	 * (recursive) children
	 */
	public List<TipiComponent> getRecursiveProperties();

	/**
	 * 
	 */
	public void commitToUi();

	public String getAlias(String expression);

	public void setAlias(String name, String value);

	public Message getStateMessage();

	public Property getAttributeProperty(String value);

	public void runSyncInEventThread(Runnable r);

	public void runAsyncInEventThread(Runnable r);
	public void addedToParentContainer(Object parentContainer, Object container, Object constriants);


}
