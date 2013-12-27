package com.dexels.navajo.tipi.components.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.TipiLayout;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
public abstract class TipiLayoutImpl extends TipiLayout {

	private static final long serialVersionUID = 4380200289873893303L;
	protected XMLElement myInstanceElement;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiLayoutImpl.class);
	
	
	/**
	 * @param text  
	 * @param index 
	 */
	@Override
	public Object parseConstraint(String text, int index) {
		return null;
	}

	@Override
	public void loadLayout(XMLElement def, TipiComponent t)
			throws com.dexels.navajo.tipi.TipiException {
		myInstanceElement = def;
		myComponent = t;
		List<XMLElement> v = myInstanceElement.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement child = v.get(i);
			String constraintString = child.getStringAttribute("constraint");
			if (!child.getName().equals("event")
					&& !child.getName().startsWith("on")) {
				Object constraint = parseConstraint(constraintString, i);

				t.addComponentInstance(myContext, child, constraint);
			} else {
				logger.warn("Event found within layout. Line: "
						+ def.getLineNr() + "\nNot right, but should work");
			}
		}
	}

	/**
	 * @param tc  
	 * @param index 
	 */
	public Object getDefaultConstraint(TipiComponent tc, int index) {
		return null;
	}
}
