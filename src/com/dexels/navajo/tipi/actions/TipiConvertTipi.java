package com.dexels.navajo.tipi.actions;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

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

public class TipiConvertTipi extends TipiAction {
	private int count = 0;

	public TipiConvertTipi() {
	}

	protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {

		File dir = new File("C:/projecten/TipiSvgDemo/tipi");
		// processFile(file);
		processDir(dir, ".xml", "");
	}

	public void processDir(File base, String extension, String relativePath) {
		System.err.println("Processing dir: " + base.getAbsolutePath());
		File[] ff = base.listFiles();
		for (int i = 0; i < ff.length; i++) {
			System.err.println("Current: " + ff[i].getName());
			if (ff[i].getName().endsWith(extension)) {
				if (ff[i].isFile()) {
					processFile(ff[i], relativePath);
				}

			}
			if (ff[i].isDirectory()) {
				processDir(ff[i], extension, "../" + relativePath);
			}
		}
	}

	private void processFile(File file, String relativePath) throws TransformerFactoryConfigurationError {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// DOMImplementation impl = builder.getDOMImplementation();
			Document d = builder.parse(file);

			int i = 0;
			while (true) {
				try {
					processElement(d, d.getDocumentElement(), relativePath);
					break;
				} catch (TipiBreakException e) {
					System.err.println("change #" + i++);
				}

			}

			try {
				// Create a transformer
				Transformer xformer = TransformerFactory.newInstance().newTransformer();
				// Set the public and system id
				// / xformer.setOutputProperty(OutputKeys.METHOD, "text");
				// Write the DOM document to a file
				Source source = new DOMSource(d);
				Result result = new StreamResult(file);
				xformer.transform(source, result);
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void processElement(Document d, Element element, String relativePath) throws TipiBreakException {
		checkElement(d, element, relativePath);
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			// System.err.println("Element # "+i);
			Node n = nl.item(i);
			if (n instanceof Element) {
				processElement(d, (Element) n, relativePath);
			}
		}

	}

	public Element cloneElementWithNewName(Document d, Element element, String newName) {
		Element newElement = d.createElement(newName);
		NamedNodeMap nnm = element.getAttributes();
		for (int i = 0; i < nnm.getLength(); i++) {
			Node n = nnm.item(i);
			Attr a = (Attr) n;
			// System.err.println("TYPE: "+n.getClass());
			newElement.setAttribute(a.getName(), a.getValue());
		}
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);

			if (n instanceof Element) {
				Element e = (Element) n;
				Node cc = e.cloneNode(true);
				newElement.appendChild(cc);
			}
			if (n instanceof Comment) {
				Comment c = (Comment) n;
				Node cc = c.cloneNode(false);
				newElement.appendChild(cc);
			}
			if (n instanceof Text) {
				Text c = (Text) n;
				Node cc = c.cloneNode(false);
				newElement.appendChild(cc);
			}

		}

		Node parentNode = element.getParentNode();
		Node nextSib = element.getNextSibling();
		parentNode.removeChild(element);
		if (nextSib == null) {
			parentNode.appendChild(newElement);
		} else {
			parentNode.insertBefore(newElement, nextSib);
		}

		return newElement;

	}

	private void checkElement(Document d, Element element, String relativePath) throws TipiBreakException {
		// DeferredElementImpl element = (DeferredElementImpl)e;
		// Element e2 = (Element) element.cloneNode(true);
		//		

		// System.err.println("element: "+element.getClass());
		// element.set
		// System.err.println("Element: "+element.getNodeName()+"" +count ++);
		;

		// xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		// xsi:noNamespaceSchemaLocation="tipi.xsd"

		if (element.getNodeName().equals("tid")) {
			element.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			element.setAttribute("xsi:noNamespaceSchemaLocation", relativePath + "tipi.xsd");
		}
		// || element.getNodeName().equals("component-instance")) {

		if (element.getNodeName().equals("tipi-instance") || element.getNodeName().equals("component-instance")) {
			String cls = element.getAttribute("class");
			if (cls == null || "".equals(cls)) {
				cls = element.getAttribute("type");
			}
			if (cls != null) {
				Element e = cloneElementWithNewName(d, element, "c." + cls);
				e.removeAttribute("class");
				e.removeAttribute("type");
				throw new TipiBreakException();
			}
		}
		if (element.getNodeName().equals("tipi") || element.getNodeName().equals("component") || element.getNodeName().equals("definition")) {
			String cls = element.getAttribute("class");
			if (cls == null || "".equals(cls)) {
				cls = element.getAttribute("type");
			}
			if (cls != null) {

				if (((Element) element.getParentNode()).getNodeName().equals("tid")) {
					Element e = cloneElementWithNewName(d, element, "d." + cls);
					e.removeAttribute("class");
					e.removeAttribute("type");
				} else {
					Element e = cloneElementWithNewName(d, element, "c." + cls);
					e.removeAttribute("class");
					e.removeAttribute("type");
				}
				throw new TipiBreakException();
			}
		}
		if (element.getNodeName().equals("event")) {
			if (element.getAttribute("type") != null) {
				if ("onActionPerformed".equals(element.getAttribute("type"))) {
					System.err.println(">>>>>>>>Elements: " + element.getAttribute("type") + " count: " + count++);
				}
				// System.err.println("EventElements:
				// "+element.getAttribute("type"));
				Element e = cloneElementWithNewName(d, element, element.getAttribute("type"));
				// e.setAttribute("apekool","hoera");
				e.removeAttribute("type");
				throw new TipiBreakException();
			}
		}
		if (element.getNodeName().equals("layout")) {
			if (element.getAttribute("type") != null) {
				Element e = cloneElementWithNewName(d, element, "l." + element.getAttribute("type"));
				e.removeAttribute("type");
				throw new TipiBreakException();
			}
		}
		if (element.getNodeName().equals("action")) {
			if (element.getAttribute("type") != null) {

				Element e = cloneElementWithNewName(d, element, element.getAttribute("type"));
				e.removeAttribute("type");
				NodeList nl = e.getChildNodes();
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n instanceof Element) {
						Element cc = (Element) n;
						if (cc.getNodeName().equals("param")) {
							e.setAttribute(cc.getAttribute("name"), cc.getAttribute("value"));
						}
					}
				}
				// backwards
				for (int i = nl.getLength() - 1; i >= 0; i--) {
					Node n = nl.item(i);
					if (n instanceof Element) {
						Element cc = (Element) n;
						if (cc.getNodeName().equals("param")) {
							e.removeChild(cc);
						}
					}
					if (n instanceof Text) {
						Text t = (Text) n;
						e.removeChild(t);
					}
				}

				throw new TipiBreakException();
			}
		}
	}

	public static void main(String[] args) throws TipiBreakException, TipiException {
		new TipiConvertTipi().execute(null);
	}

}
