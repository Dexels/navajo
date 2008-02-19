package com.dexels.navajo.tipi.internal;

import java.util.*;

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
public abstract class TipiLayout {
	protected Map<String,TipiValue> componentValues = new HashMap<String,TipiValue>();
	protected String layoutName = null;
	protected Object myLayout;
	protected XMLElement myDefinition;
	protected XMLElement myClassDef = null;
	protected TipiContext myContext;
	protected TipiComponent myComponent;

	// protected abstract Object parseConstraint(String text);
	public TipiLayout() {
	}

	public void setContext(TipiContext tc) {
		myContext = tc;
	}

	public void setComponent(TipiComponent tc) {
		myComponent = tc;
	}

	// public abstract void instantiateLayout(TipiContext context, Tipi t,
	// XMLElement def);
	public abstract void createLayout() throws TipiException;

	protected abstract void loadLayout(XMLElement def, TipiComponent current) throws TipiException;


	public void loadLayout(TipiComponent current) throws TipiException {
		loadLayout(myDefinition, current);
	}

	public XMLElement store() {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.setName("layout");
		xe.setAttribute("type", getName());
		return xe;
	}

	public void setClassDef(XMLElement xe) {
		myClassDef = xe;
	}

	public void loadClassDef() {
	}

	public void initializeLayout(XMLElement def) throws TipiException {
		myDefinition = def;
	}

	public Object createDefaultConstraint(int index) {
		return null;
	}

	protected abstract void setValue(String name, TipiValue tv);

	public String getName() {
		return layoutName;
	}

	public void setName(String name) {
		layoutName = name;
	}

	public Object getLayout() {
		return myLayout;
	}

	public void setLayout(Object l) {
		myLayout = l;
	}

	private final void loadValues(XMLElement values) {
		List<XMLElement> children = values.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement xx = children.get(i);
			String valueName = xx.getStringAttribute("name");
			TipiValue tv = new TipiValue(myComponent);
			tv.load(xx);
			componentValues.put(valueName, tv);
			if (tv.getValue() != null && !"".equals(tv.getValue())) {
				setValue(tv.getName(), tv);
			}
		}
	}

	public void childAdded(Object c) {
	}

	public void commitLayout() {
		// Not implemented, called after creating layout, override if further
		// action is needed

	}
}
