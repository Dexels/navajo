/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.core.dom.properties.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.akrogen.tkui.css.core.dom.CSSStylableElement;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.exceptions.UnsupportedClassCSSPropertyException;
import org.akrogen.tkui.css.core.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.css.CSSStyleDeclaration;

/**
 * CSS property handler with lazy strategy. {@link ICSSPropertyHandler} are
 * retrieved with name into packages registered with registerPackage method.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSPropertyHandlerLazyProviderImpl extends
		AbstractCSSPropertyHandlerProvider {
 
	private static Log logger = LogFactory
			.getLog(CSSPropertyHandlerLazyProviderImpl.class);

	// List of package names containing handlers class for properties
	private List packageNames = new ArrayList();

	// Map used as a cache for properties handlers found
	private Map propertyToHandlersMap = new HashMap();

	/**
	 * Return the list of PropertiesHandler corresponding to the property name
	 * given as argument
	 */
	public Collection getCSSPropertyHandlers(String property) throws Exception {
		// Test if ICSSPropertyHandler List was stored into cache
		// with key property
		Map propertyHandlers = getPropertyToHandlersMap();
		Object h = propertyHandlers.get(property);
		if (h != null) {
			// Test if there
			if (h instanceof List)
				return (List) h;
			return null;
		}

		List handlers = null;
		try {
			String handlerClassName = getHandlerClassName(property);
			for (Iterator iterator = packageNames.iterator(); iterator
					.hasNext();) {
				String packageName = (String) iterator.next();

				ICSSPropertyHandler handler = getCSSPropertyHandler(
						packageName, handlerClassName);
				if (handler != null) {
					if (logger.isDebugEnabled())
						logger.debug("Handle CSS Property=" + property
								+ ", with class=" + packageName + "."
								+ handlerClassName);
					if (handlers == null)
						handlers = new ArrayList();
					handlers.add(handler);
				}
			}
			if (logger.isDebugEnabled()) {
				if (handlers == null)
					logger.debug("Cannot find Handle Class CSS Property="
							+ property + ", for class=" + handlerClassName);
			}
		} finally {
			if (handlers != null)
				propertyHandlers.put(property, handlers);
			else
				propertyHandlers.put(property, "__HANDLER_NOT_FOUND__");
		}
		return handlers;
	}

	/**
	 * Register a package path "name.name1." where to search for PropertyHandler
	 * class
	 * 
	 * @param packageName
	 */
	public void registerPackage(String packageName) {
		packageNames.add(packageName);
		propertyToHandlersMap = null;
	}

	protected Map getPropertyToHandlersMap() {
		if (propertyToHandlersMap == null)
			propertyToHandlersMap = new HashMap();
		return propertyToHandlersMap;
	}

	/**
	 * Reflexive method that return a property handler class
	 * 
	 * @param packageName
	 * @param handlerClassName
	 * @return
	 * @throws Exception
	 */
	protected ICSSPropertyHandler getCSSPropertyHandler(String packageName,
			String handlerClassName) throws Exception {
		String handlerClass = packageName + "." + handlerClassName;
		try {
			Class clazz = this.getClass().getClassLoader().loadClass(
					handlerClass);
			Object instance = clazz.newInstance();
			if (!(instance instanceof ICSSPropertyHandler)) {
				throw new UnsupportedClassCSSPropertyException(clazz);
			}
			return (ICSSPropertyHandler) clazz.newInstance();
		} catch (ClassNotFoundException e) {

		}
		return null;
	}

	/**
	 * Return the handler class name corresponding to the property label given
	 * as argument A Property Handler Class Name is CSSPropertyXXXHandler (like
	 * CSSPropertyBorderTopColorHandler)
	 * 
	 * @param property
	 * @return
	 */
	protected String getHandlerClassName(String property) {
		String handlerClassName = "CSSProperty";
		String[] s = StringUtils.split(property, "-");
		for (int i = 0; i < s.length; i++) {
			String p = s[i];
			p = p.substring(0, 1).toUpperCase() + p.substring(1, p.length());
			handlerClassName += p;
		}
		handlerClassName += "Handler";
		return handlerClassName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.akrogen.tkui.css.core.dom.properties.providers.AbstractCSSPropertyHandlerProvider#getDefaultCSSStyleDeclaration(org.akrogen.tkui.css.core.engine.CSSEngine, org.akrogen.tkui.css.core.dom.CSSStylableElement, org.w3c.dom.css.CSSStyleDeclaration)
	 */
	protected CSSStyleDeclaration getDefaultCSSStyleDeclaration(
			CSSEngine engine, CSSStylableElement stylableElement,
			CSSStyleDeclaration newStyle, String pseudoE) throws Exception {
		if (stylableElement.getDefaultStyleDeclaration(pseudoE) != null)
			return stylableElement.getDefaultStyleDeclaration(pseudoE);
		if (newStyle != null) {
			StringBuffer style = null;
			int length = newStyle.getLength();
			for (int i = 0; i < length; i++) {
				String propertyName = newStyle.item(i);
				String[] compositePropertiesNames = engine
						.getCSSCompositePropertiesNames(propertyName);
				if (compositePropertiesNames != null) {
					for (int j = 0; j < compositePropertiesNames.length; j++) {
						propertyName = compositePropertiesNames[j];
						String s = getCSSPropertyStyle(engine, stylableElement,
								propertyName);
						if (s != null) {
							if (style == null)
								style = new StringBuffer();
							style.append(s);
						}
					}
				} else {
					String s = getCSSPropertyStyle(engine, stylableElement,
							propertyName);
					if (s != null) {
						if (style == null)
							style = new StringBuffer();
						style.append(s);
					}
				}
			}
			if (style != null) {
				CSSStyleDeclaration defaultStyleDeclaration = engine
						.parseStyleDeclaration(style.toString());
				stylableElement
						.setDefaultStyleDeclaration(pseudoE, defaultStyleDeclaration);
				return defaultStyleDeclaration;
			}
		}
		return stylableElement.getDefaultStyleDeclaration(pseudoE);
	}

}
