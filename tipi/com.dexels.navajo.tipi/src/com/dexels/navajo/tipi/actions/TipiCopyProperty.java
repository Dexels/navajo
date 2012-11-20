package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class TipiCopyProperty extends TipiAction {
	private static final long serialVersionUID = -5296764161841844878L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiCopyProperty.class);
	
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Property p = (Property)getEvaluatedParameterValue("property", event);
		Message m =  (Message)getEvaluatedParameterValue("message", event);
		String propertyName = (String)getEvaluatedParameterValue("propertyName", event);
		if (p == null) {
			throw new TipiException(
					"Error in copyProperty action: property parameter missing!");
		}
		if (m == null) {
			throw new TipiException(
					"Error in addProperty action: navajo parameter missing!");
		}
	
		try {
			Property copy = p.copy(m.getRootDoc());
			if(propertyName!=null) {
				copy.setName(propertyName);
			}
			m.addProperty(copy);
		} catch (NavajoException e) {
			throw new TipiException("Error creating property: " + p);
		}
	}

	public static void main(String[] args) {
		String path = "1234/5678/90ab";
		logger.info(path);
		String name = path.substring(path.lastIndexOf("/") + 1, path.length());
		String pp = path.substring(0, path.lastIndexOf("/"));
		logger.info(name);
		logger.info(pp);

	}
}
// <tipiaction name="addProperty" class="TipiAddProperty"
// package="com.dexels.navajo.tipi.actions">
// <param name="path" type="string" required="true"/>
// <param name="value" type="object" required="true"/>
// <param name="navajo" type="navajo required="true""/>
// </tipiaction>
//
// <tipiaction name="addPropertyToMessage" class="TipiAddPropertyToMessage"
// package="com.dexels.navajo.tipi.actions">
// <param name="path" type="string" required="true"/>
// <param name="value" type="object" required="true"/>
// <param name="message" type="message" required="true"/>
// </tipiaction>
//
// <tipiaction name="insertMessage" class="TipiInsertMessage"
// package="com.dexels.navajo.tipi.actions">
// <param name="path" type="string" required="true"/>
// <param name="value" type="object" required="true"/>
// <param name="message" type="message" required="true"/>
// </tipiaction>
