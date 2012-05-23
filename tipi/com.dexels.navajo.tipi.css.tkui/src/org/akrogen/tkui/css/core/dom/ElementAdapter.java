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
package org.akrogen.tkui.css.core.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSS2Properties;
import org.w3c.dom.css.CSSStyleDeclaration;

/**
 * {@link Element} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class ElementAdapter implements Element, CSSStylableElement {

	private static final String[] EMPTY_STRING = new String[0];

	private Object nativeWidget;

	protected CSSEngine engine;

	private Map /* <String, CSSStyleDeclaration> */defaultStyleDeclarationMap = new HashMap();

	private CSS2Properties style = null;

	private List staticPseudoInstances;

	public ElementAdapter(Object nativeWidget, CSSEngine engine) {
		this.nativeWidget = nativeWidget;
		this.engine = engine;
	}

	/**
	 * Add static pseudo instance
	 * 
	 * @param instance
	 */
	public void addStaticPseudoInstance(String pseudoE) {
		if (staticPseudoInstances == null)
			staticPseudoInstances = new ArrayList();
		staticPseudoInstances.add(pseudoE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.CSSStylableElement#isStaticPseudoInstance(java.lang.String)
	 */
	public boolean isStaticPseudoInstance(String s) {
		if (staticPseudoInstances == null)
			return false;
		return staticPseudoInstances.contains(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.CSSStylableElement#copyDefaultStyleDeclarations(org.akrogen.tkui.css.core.dom.CSSStylableElement)
	 */
	public void copyDefaultStyleDeclarations(CSSStylableElement stylableElement) {
		// Copy default style decalaration
		this.setDefaultStyleDeclaration(null, stylableElement
				.getDefaultStyleDeclaration(null));
		// Copy all static pseudo instances
		String[] staticPseudoInstances = stylableElement
				.getStaticPseudoInstances();
		if (staticPseudoInstances != null) {
			for (int i = 0; i < staticPseudoInstances.length; i++) {
				String pseudoE = staticPseudoInstances[i];
				CSSStyleDeclaration declaration = stylableElement
						.getDefaultStyleDeclaration(pseudoE);
				this.setDefaultStyleDeclaration(pseudoE, declaration);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getLocalName()
	 */
	public abstract String getLocalName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 */
	public abstract String getAttribute(String arg0);

	public String getAttributeNS(String namespace, String attr)
			throws DOMException {
		return getAttribute(attr);
	};

	public Attr getAttributeNode(String arg0) {
		return null;
	}

	public Attr getAttributeNodeNS(String arg0, String arg1)
			throws DOMException {
		return null;
	}

	public NodeList getElementsByTagName(String arg0) {
		return null;
	}

	public NodeList getElementsByTagNameNS(String arg0, String arg1)
			throws DOMException {
		return null;
	}

	public boolean hasAttribute(String arg0) {
		return false;
	}

	public boolean hasAttributeNS(String namespace, String attr)
			throws DOMException {
		return hasAttribute(attr);
	}

	public void removeAttribute(String arg0) throws DOMException {
	}

	public void removeAttributeNS(String arg0, String arg1) throws DOMException {
	}

	public Attr removeAttributeNode(Attr arg0) throws DOMException {
		return null;
	}

	public void setAttribute(String arg0, String arg1) throws DOMException {
	}

	public void setAttributeNS(String arg0, String arg1, String arg2)
			throws DOMException {
	}

	public Attr setAttributeNode(Attr arg0) throws DOMException {
		return null;
	}

	public Attr setAttributeNodeNS(Attr arg0) throws DOMException {
		return null;
	}

	public Node appendChild(Node newChild) throws DOMException {
		return insertBefore(newChild, null);
	}

	public Node cloneNode(boolean arg0) {
		return null;
	}

	public NamedNodeMap getAttributes() {
		return null;
	}

	public Node getFirstChild() {
		return null;
	}

	public Node getLastChild() {
		return null;
	}

	public String getTagName() {
		return getLocalName();
	}

	public Node getNextSibling() {
		return null;
	}

	public String getNodeName() {
		// By default Node name is the same thing like localName
		return getLocalName();
	}

	public short getNodeType() {
		return ELEMENT_NODE;
	}

	public String getNodeValue() throws DOMException {
		return null;
	}

	public Document getOwnerDocument() {
		return null;
	}

	public String getPrefix() {
		return null;
	}

	public Node getPreviousSibling() {
		return null;
	}

	public boolean hasAttributes() {
		return false;
	}

	public boolean hasChildNodes() {
		return false;
	}

	public Node insertBefore(Node newChild, Node refChild) throws DOMException {

		return null;
	}

	public boolean isSupported(String arg0, String arg1) {
		return false;
	}

	public void normalize() {
	}

	public Node removeChild(Node arg0) throws DOMException {
		return null;
	}

	public Node replaceChild(Node arg0, Node arg1) throws DOMException {
		return null;
	}

	public void setNodeValue(String arg0) throws DOMException {
	}

	public void setPrefix(String arg0) throws DOMException {
	}

	public void setIdAttribute(String name, boolean isId) throws DOMException {
	}

	public void setIdAttributeNS(String namespaceURI, String localName,
			boolean isId) throws DOMException {
	}

	public void setIdAttributeNode(Attr idAttr, boolean isId)
			throws DOMException {
	}

	public short compareDocumentPosition(Node other) throws DOMException {
		return 0;
	}

	public String getBaseURI() {
		return null;
	}

	public Object getFeature(String feature, String version) {
		return null;
	}

	public String getTextContent() throws DOMException {
		return null;
	}

	public Object getUserData(String key) {
		return null;
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		return false;
	}

	public boolean isEqualNode(Node arg) {
		return false;
	}

	public boolean isSameNode(Node other) {
		return false;
	}

	public String lookupNamespaceURI(String prefix) {
		return null;
	}

	public String lookupPrefix(String namespaceURI) {
		return null;
	}

	public void setTextContent(String textContent) throws DOMException {

	}

	public Object getNativeWidget() {
		return nativeWidget;
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return null;
	}

	public TypeInfo getSchemaTypeInfo() {
		return null;
	}

	public CSSStyleDeclaration getDefaultStyleDeclaration(String pseudoE) {
		return (CSSStyleDeclaration) defaultStyleDeclarationMap.get(pseudoE);
	}

	public void setDefaultStyleDeclaration(String pseudoE,
			CSSStyleDeclaration declaration) {
		this.defaultStyleDeclarationMap.put(pseudoE, declaration);
	}

	public void onStylesApplied(NodeList nodes) {
		// Do Nothing

	}

	protected Element getElement(Object widget) {
		return engine.getElement(widget);
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.akrogen.tkui.css.core.dom.CSSStylableElement#getStyle()
//	 */
//	public CSSExtendedProperties getStyle() {
//		if (style == null)
//			style = new CSSExtendedPropertiesImpl(nativeWidget, engine);
//		return style;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.CSSStylableElement#getStaticPseudoInstances()
	 */
	public String[] getStaticPseudoInstances() {
		if (staticPseudoInstances == null)
			return EMPTY_STRING;
		return (String[]) staticPseudoInstances.toArray(EMPTY_STRING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.CSSStylableElement#isPseudoInstanceOf(java.lang.String)
	 */
	public boolean isPseudoInstanceOf(String s) {
		if (staticPseudoInstances == null)
			return false;
		return staticPseudoInstances.contains(s);
	}
}
