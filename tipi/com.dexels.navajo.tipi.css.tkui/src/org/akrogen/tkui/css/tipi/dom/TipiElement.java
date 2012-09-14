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
package org.akrogen.tkui.css.tipi.dom;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.akrogen.tkui.css.core.dom.ElementAdapter;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.utils.ClassUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * w3c Element which wrap Swing component.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class TipiElement extends ElementAdapter implements NodeList {

	private String defaultHTMLContent = null;

	private Document document;

	protected String localName;

	protected String namespaceURI;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiElement.class);
	
	public TipiElement(TipiComponent component, CSSEngine engine) {
		super(component, engine);
		this.localName = computeLocalName();
		this.namespaceURI = computeNamespaceURI();
		storeDefaultHTMLContentIfNeeded(component);
	}

	protected String computeLocalName() {
		// The localName is simple class name
		// of the Swing component. For instance
		// for the javax.swing.JLabel
		// localName is JLabel
		// CSS selector will use this localName
		// ex : JLabel {background-color:red;}
		TipiComponent component = getComponent();
		// TODO fix this
//		Class clazz = component.getClass();
		return component.getClassName();
	}

	protected String computeNamespaceURI() {
		// The NamespaceURI is package name
		// of the Swing component. For instance
		// for the javax.swing.JLabel
		// namespaceURI is javax.swing
		// CSS selector will use this localName
		// @namespace swing javax.swing
		// ex : swing|JLabel {background-color:red;}

		// TODO fix, this isn't correct, I think.
		TipiComponent component = getComponent();
		Class clazz = component.getClass();
		return ClassUtils.getPackageName(clazz);
	}

	public void setAttribute(String name, String value) throws DOMException {

		TipiComponent component = getComponent();
		component.setValue(name, value);
}
	
	public String getAttribute(String attr) {
		TipiComponent component = getComponent();
			Object o = component.getValue(attr);
			return o.toString();
//		try {
//			Object o = PropertyUtils.getProperty(component, attr);
//			if (o != null)
//				return o.toString();
//		} catch (Exception e) {
//			// logger.error("Error: ",e);
//		}
//		return "";
	}


	
	@Override
	public String getNodeName() {
		return getLocalName();
	}

	public String getLocalName() {
		TipiComponent component = getComponent();

		return component.getClassName();
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}

	public Node getParentNode() {
		TipiComponent component = getComponent();
		TipiComponent parent = component.getTipiParent();
	
		if (parent != null) {
			Element element = getElement(parent);
			return element;
		}
		return null;
	}

	public NodeList getChildNodes() {
		if (defaultHTMLContent != null) {
			// load the HTML Content into DOM
			try {
				// Load XML into DOM
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				this.document = builder.parse(new InputSource(new StringReader(
						defaultHTMLContent)));
				return document.getDocumentElement().getChildNodes();
			} catch (Exception e) {
				logger.error("Error: ",e);
			}
		}
		NodeList n = new NodeList(){

			@Override
			public int getLength() {
				return getComponent().getChildCount();
			}

			@Override
			public Node item(int index) {
				return new TipiElement(getComponent().getTipiComponent(index),engine);
			}};
		return this;
	}

	public int getLength() {
		TipiComponent component = getComponent();
		return component.getChildCount();
	}

	public Node item(int index) {
		TipiComponent component = getComponent();
		return getElement(component.getTipiComponent(index));
	}

	protected TipiComponent getComponent() {
		return (TipiComponent) getNativeWidget();
	}



	public String getCSSId() {
		TipiComponent component = getComponent();
		return component.getId();
	}

	/**
	 * A bit debatable
	 */
	public String getCSSClass() {
		TipiComponent component = getComponent();
		return (String) component.getValue("cssClass");
	
	}

	public String getCSSStyle() {
		TipiComponent component = getComponent();
		return (String) component.getValue("cssStyle");
	}

	public boolean isPseudoInstanceOf(String s) {
//		if ("disabled".equals(s)) {
//			Component component = getComponent();
//			return !component.isEnabled();
//		}
//		if ("focus".equals(s)) {
//			Component component = getComponent();
//			return component.hasFocus();
//		}
//		if ("hover".equals(s)) {
//			JComponent component = getJComponent();
//			if (component == null)
//				return false;
//			return (component.getClientProperty("mouseMoved") != null);
//		}
		return false;
	}

	public void onStylesApplied(NodeList nodes) {

		super.onStylesApplied(nodes);
	}

	protected void storeDefaultHTMLContentIfNeeded(TipiComponent component) {
//		if (!(component instanceof JLabel))
//			return;
//		JLabel label = (JLabel) component;
//		String text = label.getText();
//		if (text.startsWith("<html>") && text.endsWith("</html>")) {
//			this.defaultHTMLContent = text;
//		}
	}

	protected String getHTMLContent(Element element) {
		StringWriter writer = new StringWriter();
		OutputFormat outputFormat = new OutputFormat();
		outputFormat.setOmitXMLDeclaration(true);
		XMLSerializer serializer = new XMLSerializer(writer, outputFormat);
		try {
			serializer.serialize(element);
			return writer.toString();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
		return null;
	}

}
