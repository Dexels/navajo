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
package org.akrogen.tkui.css.tipi.helpers;

import java.lang.reflect.InvocationTargetException;

import org.akrogen.tkui.css.core.dom.CSSStylableElement;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.dom.TipiElement;
import org.akrogen.tkui.css.tipi.dom.html.TipiHTMLElement;
import org.w3c.dom.Element;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * Swing Helper to link w3c Element with Swing Component.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class TipiElementHelpers {

	public static final String SWING_ELEMENT_KEY = "org.akrogen.tkui.core.css.swing.dom.SwingElement.ELEMENT";
	public static final String SWING_NODELIST_KEY = "org.akrogen.tkui.core.css.swing.dom.SwingElement.NODELIST";

	private static final Class[] ELEMENT_CONSTRUCTOR_PARAM = { TipiComponent.class,
			CSSEngine.class };

	/**
	 * Return the w3c Element linked to the Swing Component.
	 * 
	 * @param component
	 * @return
	 */
	public static Element getElement(TipiComponent component, CSSEngine engine,
			Class classElement) throws NoSuchMethodException,
			InvocationTargetException, InstantiationException,
			IllegalAccessException {
		// if (component instanceof JComponent) {
		// Element element = null;//(Element) jComponent
		// //.getClientProperty(SWING_ELEMENT_KEY);
		// if (element == null || !element.getClass().equals((classElement))) {
		// Element newElement = getElementInstance(component, classElement);
		// if (element != null && element instanceof CSSStylableElement
		// && newElement instanceof CSSStylableElement) {
		// CSSStyleDeclaration defaultStyleDeclaration = ((CSSStylableElement)
		// element)
		// .getDefaultStyleDeclaration();
		// ((CSSStylableElement) newElement)
		// .setDefaultStyleDeclaration(defaultStyleDeclaration);
		// }
		// //jComponent.putClientProperty(SWING_ELEMENT_KEY, newElement);
		// return newElement;
		// }
		// return element;
		// }
		return getElementInstance(component, engine, classElement);
	}

	public static Element getElementInstance(TipiComponent component,
			CSSEngine engine, Class classElement) throws NoSuchMethodException,
			InvocationTargetException, InstantiationException,
			IllegalAccessException {
		return new TipiElement(component, engine);
//		Constructor constructor = classElement
//				.getConstructor(ELEMENT_CONSTRUCTOR_PARAM);
//		Object[] o = { component, engine };
//		return (Element) constructor.newInstance(o);

	}

	/**
	 * Return the w3c Element linked to the Swing Component.
	 * 
	 * @param component
	 * @return
	 */
	public static Element getElement(TipiComponent component, CSSEngine engine) {
		try {
			return getElement(component, engine, TipiElement.class);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Return the w3c Element linked to the Swing Component.
	 * 
	 * @param component
	 * @return
	 */
	public static Element getHTMLElement(TipiComponent component, CSSEngine engine) {
		try {
			return getElement(component, engine, TipiHTMLElement.class);
		} catch (Exception e) {
			return null;
		}
	}

	public static TipiComponent getComponent(Object element) {
		if (element instanceof TipiComponent) {
			return (TipiComponent) element;
		} else {
			if (element instanceof CSSStylableElement) {
				CSSStylableElement elt = (CSSStylableElement) element;
				Object widget = elt.getNativeWidget();
				if (widget instanceof TipiComponent)
					return (TipiComponent) widget;
			}
		}
		return null;
	}

	/**
	 * Return the w3c NodeList linked to the container. Each node are w3c
	 * Element which are linked to child components of the Swing container.
	 * 
	 * @param container
	 * @return
	 */
	// public static NodeList getNodeList(Container container) {
	// NodeList nodeList = null;
	// if (container instanceof JComponent)
	// nodeList = (NodeList) ((JComponent) container)
	// .getClientProperty(SWING_NODELIST_KEY);
	// if (nodeList == null) {
	// NodeListAdapter list = new NodeListAdapter();
	// Component[] components = container.getComponents();
	// for (int i = 0; i < components.length; i++) {
	// Component component = components[i];
	// Element node = getElement(component);
	// list.add(node);
	// }
	// if (container instanceof JComponent)
	// ((JComponent) container).putClientProperty(SWING_NODELIST_KEY,
	// list);
	// return list;
	// }
	// return nodeList;
	// }
}
