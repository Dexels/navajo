package com.dexels.navajo.server.listener.wsdl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.util.Generate;

public class WsdlProducer extends HttpServlet {

	protected String configurationPath = "";
	public  static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
	public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";
	public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";

	private static final String WSDL = "http://schemas.xmlsoap.org/wsdl/";
	private static final String XMLSCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
	
	private static final String MYNAMESPACE = "http://www.dexels.nl/navajo/webservice.xsd";
	private static final String MYSCHEMAS = "navajo:webservice:types";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		configurationPath = config.getInitParameter("configuration");

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.err.println("in WsdlProducer doGet()");
		doPost(request, response);
	}

	private boolean existsTypeDefinition(Node schemaNode, String type) {
		NodeList nodes = schemaNode.getChildNodes();
		
		for (int i = 0; i < nodes.getLength(); i++) {
			if ( nodes.item(i) instanceof Element ) {
				Element e = (Element) nodes.item(i);
				if ( e.getAttribute("name").equals(type) ) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void addSpecialNavajoTypes(Document d, Node schemaNode) {
		
		// selectedoption
		Element n1 = d.createElement("xsd:simpleType");
		n1.setAttribute("name", "selectedoption");
		schemaNode.appendChild(n1);
		Element n2 =  d.createElement("xsd:restriction");
		n2.setAttribute("base", "xsd:string");
		n1.appendChild(n2);
		Element enum1 = d.createElement("xsd:enumeration");
		enum1.setAttribute("value", "0");
		Element enum2 = d.createElement("xsd:enumeration");
		enum2.setAttribute("value", "1");
		n2.appendChild(enum1);
		n2.appendChild(enum2);
		
		// selection
		Element n3 = d.createElement("xsd:complexType");
		n3.setAttribute("name", "selection");
		schemaNode.appendChild(n3);
		Element n4 = d.createElement("xsd:sequence");
		n3.appendChild(n4);
		Element n5 = d.createElement("xsd:element");
		n5.setAttribute("name", "option");
		n5.setAttribute("minOccurs", "0");
		n5.setAttribute("maxOccurs", "unbounded");
		n4.appendChild(n5);
		
		Element n6 = d.createElement("xsd:complexType");
		n5.appendChild(n6);
		
		Element nameAttr = d.createElement("xsd:attribute");
		nameAttr.setAttribute("name", "name");
		nameAttr.setAttribute("type", "xsd:string");
		Element valueAttr = d.createElement("xsd:attribute");
		valueAttr.setAttribute("name", "value");
		valueAttr.setAttribute("type", "xsd:string");
		Element selectedAttr = d.createElement("xsd:attribute");
		selectedAttr.setAttribute("name", "selected");
		selectedAttr.setAttribute("type", "tns:selectedoption");
		n6.appendChild(nameAttr);
		n6.appendChild(valueAttr);
		n6.appendChild(selectedAttr);
		
	}
	
	private Element findTypesNode(Document d) {
		Element types = (Element) XMLutils.findNode(d, "types");

		System.err.println("types = " + types);
		if (types == null) { // Create it.
			types = d.createElement("types");
			d.getDocumentElement().appendChild(types);
		} 

		Element schema = (Element) XMLutils.findNode(types, "xsd:schema");
		if ( schema == null) {
			schema = d.createElementNS(XMLSCHEMA, "xsd:schema");
			schema.setAttribute("targetNamespace", MYNAMESPACE);
			types.appendChild(schema);
			addSpecialNavajoTypes(d, schema);
		}
		return schema;
	}

	private ArrayList addMessageDef(Document d, Element root, Object n, boolean input) throws Exception {

		Element types = findTypesNode(d);

		ArrayList messages = null;
		if ( n instanceof Navajo ) {
			messages = ((Navajo) n).getAllMessages();
		} else if ( n instanceof Message ) {
			messages = new ArrayList();
			messages.add(n);
		}
		
		for (int i = 0; i < messages.size(); i++ ) {
			Message m = (Message) messages.get(i);

			if ( !existsTypeDefinition(types, m.getName()) ) {
				
				Element complexType = d.createElementNS(XMLSCHEMA, "xsd:complexType");
				complexType.setAttribute("name", m.getName());
				types.appendChild(complexType);

				Element sequence = d.createElementNS(XMLSCHEMA, "xsd:sequence");
				complexType.appendChild(sequence);

				ArrayList properties = m.getAllProperties();
				for (int j = 0; j < properties.size(); j++) {
					Property p = (Property) properties.get(j);
					Element part = d.createElementNS(XMLSCHEMA, "xsd:element");
					sequence.appendChild(part);
					part.setAttribute("name", p.getName());
					if ( p.getType().equals(Property.STRING_PROPERTY) ) {
						part.setAttributeNS(XMLSCHEMA, "type", "xsd:string");
					} else if ( p.getType().equals(Property.INTEGER_PROPERTY) ) {
						part.setAttributeNS(XMLSCHEMA, "type", "xsd:integer");
					} else if ( p.getType().equals(Property.FLOAT_PROPERTY) ) {
						part.setAttributeNS(XMLSCHEMA, "type", "xsd:float");
					}  else if ( p.getType().equals(Property.DATE_PROPERTY) ) {
						part.setAttributeNS(XMLSCHEMA, "type", "xsd:date");
					} else if ( p.getType().equals(Property.BOOLEAN_PROPERTY) ) {
						part.setAttributeNS(XMLSCHEMA, "type", "xsd:boolean");
					} else if ( p.getType().equals(Property.SELECTION_PROPERTY)) {
						part.setAttributeNS(MYSCHEMAS, "type", "tns:selection");
					} else {
						part.setAttributeNS(XMLSCHEMA, "type", "xsd:string");
					}
				}
				
				// Find child messages...
				ArrayList children = m.getAllMessages();
				for (int j = 0; j < children.size(); j++ ) {
					Message c = (Message) children.get(j);
					Element part = d.createElementNS(XMLSCHEMA, "xsd:element");
					part.setAttribute("name", c.getName());
					part.setAttribute("type", "tns:" + c.getName());
					sequence.appendChild(part);
					addMessageDef(d, root, c, input);
				}
				
			}
		}
		return messages;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		System.err.println(request.getQueryString());

		System.err.println("HasmoreElements");
		String webservice = request.getQueryString();
		System.err.println("webservice = " + webservice);

		System.err.println("in WsdlProducer doPost()");
		try {

			DispatcherInterface dis = DispatcherFactory.getInstance(configurationPath,null, 
					new com.dexels.navajo.server.FileInputStreamReader());

			InputStream is = dis.getNavajoConfig().getScript(webservice);

			Generate gen = new Generate();

			// Get input message.
			Navajo input = gen.getInputPart(null, is);
			Message g = input.getMessage("__globals__");
			if ( g != null ) {
				input.removeMessage("__globals__");
			}
			is.close();

			Document doc = XMLDocumentUtils.createDocument();
			Element definitions = doc.createElement("definitions");
			definitions.setAttribute("xmlns", WSDL);
			definitions.setAttribute("xmlns:soap", SOAP);
			definitions.setAttribute("xmlns:xsd", XMLSCHEMA);
			definitions.setAttribute("targetNamespace", MYNAMESPACE);
			definitions.setAttribute("xmlns:tns", MYNAMESPACE);
			definitions.setAttribute("xmlns:esns", MYSCHEMAS);
			
			doc.appendChild(definitions);
			// Get input message.
			ArrayList inputMessages = addMessageDef(doc, definitions, input, true);
			Element inputMessage = doc.createElement("message");
			inputMessage.setAttribute("name", "ProcessReq");
			definitions.appendChild(inputMessage);

			for (int i = 0 ; i < inputMessages.size(); i++ ) {
				Message m = (Message) inputMessages.get(i);
				Element inputBody = doc.createElement("part");
				inputBody.setAttribute("name", m.getName());
				inputBody.setAttribute("type", "tns:"+m.getName());
				inputMessage.appendChild(inputBody);
			}

			// Get output message.
			is = dis.getNavajoConfig().getScript(webservice);
			Navajo output = gen.getOutputPart(is);
			g = output.getMessage("__globals__");
			if ( g != null ) {
				output.removeMessage("__globals__");
			}
			ArrayList outputMessages = addMessageDef(doc, definitions, output, false);
			Element outputMessage = doc.createElement("message");
			outputMessage.setAttribute("name", "ProcessRes");
			definitions.appendChild(outputMessage);
			for (int i = 0 ; i < outputMessages.size(); i++ ) {
				Message m = (Message) outputMessages.get(i);
				Element outputBody = doc.createElement("part");
				outputBody.setAttribute("name", m.getName());
				outputBody.setAttribute("type", "tns:"+m.getName());
				outputMessage.appendChild(outputBody);
			}

			is.close();

			/**
			 * Add portType.
			 */
			Element portType = doc.createElement("portType");
			portType.setAttribute("name", webservice.replaceAll("/", "_") + "_PortType");
			definitions.appendChild(portType);
			Element operation = doc.createElement("operation");
			operation.setAttribute("name", "Process");
			portType.appendChild(operation);

			// Add input message.
			Element inputMsg = doc.createElement("input");
			operation.appendChild(inputMsg);
			inputMsg.setAttribute("message", "tns:ProcessReq");
			inputMsg.setAttribute("name", "ProcessReq");


			// Add output message.

			Element outputMsg = doc.createElement("output");
			operation.appendChild(outputMsg);
			outputMsg.setAttribute("message", "tns:ProcessRes");
			outputMsg.setAttribute("name", "ProcessRes");


			/**
			 * Add Binding.
			 */
			Element binding = doc.createElement("binding");
			binding.setAttribute("name", webservice.replaceAll("/", "_") + "_Binding");
			binding.setAttribute("type", "tns:" + webservice.replaceAll("/", "_") + "_PortType");
			definitions.appendChild(binding);
			Element soap = doc.createElementNS(SOAP, "soap:binding");
			soap.setAttribute("style", "rpc");
			soap.setAttribute("transport", "http://schemas.xmlsoap.org/soap/http");
			binding.appendChild(soap);
			Element operation_b = doc.createElement("operation");
			operation_b.setAttribute("name", "Process");
			binding.appendChild(operation_b);
			Element soap_o = doc.createElementNS(SOAP, "soap:operation");
			soap_o.setAttribute("soapAction", webservice);
			operation_b.appendChild(soap_o);
			// Input
			Element operation_input = doc.createElement("input");
			operation_input.setAttribute("name", "ProcessReq");
			operation_b.appendChild(operation_input);
			Element soap_input_body =  doc.createElementNS(SOAP, "soap:body");
			operation_input.appendChild(soap_input_body);
			//soap_input_body.setAttribute("encodingStyle", ENCODING);
			soap_input_body.setAttribute("use", "literal");
			soap_input_body.setAttribute("namespace", MYNAMESPACE);
			// Output
			Element operation_output = doc.createElement("output");
			operation_output.setAttribute("name", "ProcessRes");
			operation_b.appendChild(operation_output);
			Element soap_output_body =  doc.createElementNS(SOAP, "soap:body");
			operation_output.appendChild(soap_output_body);
			//soap_output_body.setAttribute("encodingStyle", ENCODING);
			soap_output_body.setAttribute("use", "literal");
			soap_output_body.setAttribute("namespace", MYNAMESPACE);

			/**
			 * Service part.
			 */
			Element service = doc.createElement("service");
			service.setAttribute("name", webservice.replaceAll("/", "_"));
			definitions.appendChild(service);
			Element documentation = doc.createElement("documentation");
			documentation.setTextContent("Dit is de documentatie");
			service.appendChild(documentation);
			Element port = doc.createElement("port");
			port.setAttribute("binding",  "tns:"+webservice.replaceAll("/", "_") + "_Binding");
			port.setAttribute("name",  webservice.replaceAll("/", "_") + "_Port");
			Element soap_address = doc.createElementNS(SOAP, "soap:address");
			port.appendChild(soap_address);
			
			System.err.println("path = " + request.getServletPath());
			System.err.println("name = " + getServletConfig().getServletName());
			System.err.println("contextpath = " + request.getContextPath());
			System.err.println("server  =" + request.getServerName());
			System.err.println("porr  =" + request.getServerPort());
			System.err.println("request uri = " + request.getRequestURI());
			
			soap_address.setAttribute("location", "http://" + request.getServerName() + ":" + request.getServerPort() + "/" +  request.getContextPath() + "/SOAPMan");
			service.appendChild(port);
			
			OutputStream out = (OutputStream) response.getOutputStream();
			XMLDocumentUtils.write(doc, new OutputStreamWriter(out), false);
			out.close();


		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

	}
}
