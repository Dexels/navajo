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
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import com.dexels.navajo.util.Generate;

public class WsdlProducerDotNet extends TmlHttpServlet {
	protected String configurationPath = "";
	public static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
	public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";
	public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
	private static final String WSDL = "http://schemas.xmlsoap.org/wsdl/";
	private static final String XMLSCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
	private static final String MIME = "http://schemas.xmlsoap.org/wsdl/mime";
	private static final String HTTP = "http://schemas.xmlsoap.org/soap/http/";
	private static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
	private static final String MYNAMESPACE = "http://www.dexels.nl/navajo/webservice.xsd";
	private static final String MYSCHEMAS = "navajo:webservice:types";
	private static final String SOAPENC = "http://schemas.xmlsoap.org/soap/encoding/";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.err.println("in WsdlProducer doGet()");
		doPost(request, response);
	}

	private boolean existsTypeDefinition(Node schemaNode, String type) {
		NodeList nodes = schemaNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i) instanceof Element) {
				Element e = (Element) nodes.item(i);
				if (e.getAttribute("name").equals(type)) {
					return true;
				}
			}
		}
		return false;
	}

	private Element findTypesNode(Document d) {
		Element types = (Element) XMLutils.findNode(d, "types");
		System.err.println("types = " + types);
		if (types == null) { // Create it.
			types = d.createElement("types");
			d.getFirstChild().appendChild(types);
		}
		// WARNING findnode, strips everything before ':' character
		Element schema = null;
		NodeList l = types.getElementsByTagName("schema");
		if (l.getLength() > 0) {
			schema = (Element) l.item(0);
		}
		System.err.println("Schema: " + schema);
		if (schema == null) {
			schema = d.createElementNS(XMLSCHEMA, "schema");
			schema.setAttribute("targetNamespace", MYNAMESPACE);
			schema.setAttribute("xmlns", XMLSCHEMA);
			schema.setAttribute("elementFormDefault", "qualified");
			types.appendChild(schema);
			System.err.println("Schema added!");
			addSpecialDotNetTypes(d, schema);
		}
		return schema;
	}

	private void addSpecialDotNetTypes(Document d, Node schemaNode) {
		// selectedoption
		// <schema xmlns="http://www.w3.org/2001/XMLSchema"
		// targetNamespace="prt:service:com.shell.gep.service.provisioning.ProvisioningQandE"
		// elementFormDefault="qualified">
		// - <complexType name="ArrayOf_xsd_string">
		// - <sequence>
		// <element maxOccurs="unbounded" minOccurs="0"
		// name="item_ArrayOf_xsd_string" type="xsd:string" />
		// </sequence>
		// </complexType>
		// </schema>
		Element typesNode = (Element) XMLutils.findNode(d, "types");
		Element schema = d.createElementNS(XMLSCHEMA, "schema");
		schema.setAttribute("targetNamespace", MYNAMESPACE);
		schema.setAttribute("xmlns", XMLSCHEMA);
		schema.setAttribute("elementFormDefault", "qualified");
		typesNode.appendChild(schema);
		Element complexType = d.createElementNS(XMLSCHEMA, "complexType");
		complexType.setAttribute("name", "ArrayOf_xsd_string");
		schema.appendChild(complexType);
		Element sequence = d.createElementNS(XMLSCHEMA, "sequence");
		complexType.appendChild(sequence);
		Element part = d.createElementNS(XMLSCHEMA, "element");
		part.setAttribute("maxOccurs", "unbounded");
		part.setAttribute("minOccurs", "0");
		part.setAttribute("name", "item_ArrayOf_xsd_string");
		part.setAttribute("type", "xsd:string");
		sequence.appendChild(part);
	}

	private ArrayList addMessageDef(Document d, Element root, Navajo n, boolean input) throws Exception {
		Element types = findTypesNode(d);
		ArrayList messages = n.getAllMessages();
		for (int i = 0; i < messages.size(); i++) {
			Message m = (Message) messages.get(i);
		
				
			if (!existsTypeDefinition(types, m.getName())) {
				if (input) {
					ArrayList allProp = m.getAllProperties();
					for (int j = 0; j < allProp.size(); j++) {
						Element part = d.createElementNS(XMLSCHEMA, "element");
						types.appendChild(part);
						part.setAttributeNS(XMLSCHEMA, "type", "xsd:string");
						Property p = (Property) allProp.get(j);
						part.setAttribute("name", m.getName() + "_" + p.getName());
					}
				} else {
					
					if(m.getType().equals(Message.MSG_TYPE_ARRAY)){
								
						Element part = d.createElementNS(XMLSCHEMA, "element");
						types.appendChild(part);
						part.setAttribute("name", m.getName() + "Array");
						
						Element complexType = d.createElementNS(XMLSCHEMA, "complexType");						
						part.appendChild(complexType);
						Element complexContent = d.createElementNS(XMLSCHEMA, "complexContent");
						complexType.appendChild(complexContent);
						Element restriction = d.createElementNS(XMLSCHEMA, "restriction");
						complexContent.appendChild(restriction);
						restriction.setAttribute("base", "soapenc:Array");
						Element attribute = d.createElementNS(XMLSCHEMA, "attribute");		
						restriction.appendChild(attribute);
						attribute.setAttribute("ref", "soapenc:arrayType");
						attribute.setAttribute("wsdl:arrayType", "tns:" + m.getName() + "[]");	
						
						Message arrayDef = m.getMessage(0);
							
						
						Element complexTypeDef = d.createElementNS(XMLSCHEMA, "complexType");
						complexTypeDef.setAttribute("name", arrayDef.getName());
						types.appendChild(complexTypeDef);
		
						Element all = d.createElementNS(XMLSCHEMA, "all");
						complexTypeDef.appendChild(all);
						
						ArrayList allProp = arrayDef.getAllProperties();
						for (int j = 0; j < allProp.size(); j++) {
							Property p = (Property) allProp.get(j);
							Element prop = d.createElementNS(XMLSCHEMA, "element");
							all.appendChild(prop);
							prop.setAttribute("name", p.getName());
							prop.setAttributeNS(XMLSCHEMA, "type", "xsd:string");
							prop.setAttribute("nillable", "true");
						}	
						
					}else{
						Element elmt = d.createElement("element");
						elmt.setAttribute("name", m.getName());
						types.appendChild(elmt);
						
						Element complexType = d.createElementNS(XMLSCHEMA, "complexType");
						
						elmt.appendChild(complexType);
						Element all = d.createElementNS(XMLSCHEMA, "all");
						complexType.appendChild(all);
						
						ArrayList allProp = m.getAllProperties();
						for (int j = 0; j < allProp.size(); j++) {
							Property p = (Property) allProp.get(j);
							Element part = d.createElementNS(XMLSCHEMA, "element");
							all.appendChild(part);
							part.setAttribute("name", p.getName());
							part.setAttributeNS(XMLSCHEMA, "type", "xsd:string");
							part.setAttribute("nillable", "true");
						}				
						
					}					
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
			Dispatcher dis =  initDispatcher(); // Dispatcher.getInstance(configurationPath, null, new com.dexels.navajo.server.FileInputStreamReader(), request.getServerName() + request.getRequestURI());
			InputStream is = dis.getNavajoConfig().getScript(webservice);
			Generate gen = new Generate();
			// Get input message.
			Navajo input = gen.getInputPart(null, is);
			Message g = input.getMessage("__globals__");
			if (g != null) {
				input.removeMessage("__globals__");
			}
			is.close();
			Document doc = XMLDocumentUtils.createDocument();
			Element definitions = doc.createElement("wsdl:definitions");
			definitions.setAttribute("name", webservice.replaceAll("/", "_"));
			definitions.setAttribute("targetNamespace", MYNAMESPACE);
			definitions.setAttribute("xmlns:tns", MYNAMESPACE);
			definitions.setAttribute("xmlns:xsd_doc", MYNAMESPACE);
			definitions.setAttribute("xmlns:mime", MIME);
			definitions.setAttribute("xmlns:http", HTTP);
			definitions.setAttribute("xmlns:xsi", XSI);
			definitions.setAttribute("xmlns:xsd", XMLSCHEMA);
			definitions.setAttribute("xmlns:soap", SOAP);
			definitions.setAttribute("xmlns:soapenc", SOAPENC);
			definitions.setAttribute("xmlns:wsdl", WSDL);
			definitions.setAttribute("xmlns", WSDL);
			doc.appendChild(definitions);
			
			input.write(System.err);
			
			// Get input message.
			ArrayList inputMessages = addMessageDef(doc, definitions, input, true);
			Element inputMessage = doc.createElement("wsdl:message");
			inputMessage.setAttribute("name", "ProcessReq");
			definitions.appendChild(inputMessage);
			for (int i = 0; i < inputMessages.size(); i++) {
				Message m = (Message) inputMessages.get(i);
				ArrayList allProp = m.getAllProperties();
				for (int j = 0; j < allProp.size(); j++) {
					Element inputBody = doc.createElement("wsdl:part");
					Property p = (Property) allProp.get(j);
					inputBody.setAttribute("element", "tns:" + m.getName() + "_" + p.getName());
					inputBody.setAttribute("name", m.getName() + "_" + p.getName());
					inputMessage.appendChild(inputBody);
				}
			}
			// Get output message.
			is = dis.getNavajoConfig().getScript(webservice);
			Navajo output = gen.getOutputPart(is);
			g = output.getMessage("__globals__");
			if (g != null) {
				output.removeMessage("__globals__");
			}
			output.write(System.err);
			ArrayList outputMessages = addMessageDef(doc, definitions, output, false);
			Element outputMessage = doc.createElement("wsdl:message");
			outputMessage.setAttribute("name", "ProcessRes");
			definitions.appendChild(outputMessage);
			for (int i = 0; i < outputMessages.size(); i++) {
				Message m = (Message) outputMessages.get(i);
				Element outputBody = doc.createElement("wsdl:part");
//				outputBody.setAttribute("name", m.getName() + "Response");
				outputBody.setAttribute("name", "ProcessRes");
//				outputBody.setAttribute("type", "tns:" + m.getName());
				if(m.getType().equals(Message.MSG_TYPE_ARRAY)){
					outputBody.setAttribute("element", "xsd_doc:" + m.getName() + "Array");
				}else{
					outputBody.setAttribute("element", "xsd_doc:" + m.getName());
				}
				
				outputMessage.appendChild(outputBody);
			}
			is.close();
			/**
			 * Add portType.
			 */
			Element portType = doc.createElement("wsdl:portType");
			portType.setAttribute("name", webservice.replaceAll("/", "_") + "_PortType");
			definitions.appendChild(portType);
			Element operation = doc.createElement("wsdl:operation");
			operation.setAttribute("name", "Process");
			portType.appendChild(operation);
			// Add input message.
			Element inputMsg = doc.createElement("wsdl:input");
			operation.appendChild(inputMsg);
			inputMsg.setAttribute("message", "tns:ProcessReq");
			inputMsg.setAttribute("name", "ProcessReq");
			// Add output message.
			Element outputMsg = doc.createElement("wsdl:output");
			operation.appendChild(outputMsg);
			outputMsg.setAttribute("message", "tns:ProcessRes");
			outputMsg.setAttribute("name", "ProcessRes");
			/**
			 * Add Binding.
			 */
			Element binding = doc.createElement("wsdl:binding");
			binding.setAttribute("name", webservice.replaceAll("/", "_") + "_Binding");
			binding.setAttribute("type", "tns:" + webservice.replaceAll("/", "_") + "_PortType");
			definitions.appendChild(binding);
			Element soap = doc.createElementNS(SOAP, "soap:binding");
			soap.setAttribute("style", "document");
			soap.setAttribute("transport", "http://schemas.xmlsoap.org/soap/http");
			binding.appendChild(soap);
			Element operation_b = doc.createElement("wsdl:operation");
			operation_b.setAttribute("name", "Process");
			binding.appendChild(operation_b);
			Element soap_o = doc.createElementNS(SOAP, "soap:operation");
			soap_o.setAttribute("soapAction", webservice);
			operation_b.appendChild(soap_o);
			// Input
			Element operation_input = doc.createElement("wsdl:input");
			operation_input.setAttribute("name", "ProcessReq");
			operation_b.appendChild(operation_input);
			Element soap_input_body = doc.createElementNS(SOAP, "soap:body");
			operation_input.appendChild(soap_input_body);
			// soap_input_body.setAttribute("encodingStyle", ENCODING);
			soap_input_body.setAttribute("use", "literal");
			// soap_input_body.setAttribute("namespace", MYNAMESPACE);
			// Output
			Element operation_output = doc.createElement("wsdl:output");
			operation_output.setAttribute("name", "ProcessRes");
			operation_b.appendChild(operation_output);
			Element soap_output_body = doc.createElementNS(SOAP, "soap:body");
			operation_output.appendChild(soap_output_body);
			// soap_output_body.setAttribute("encodingStyle", ENCODING);
			soap_output_body.setAttribute("use", "literal");
			// soap_output_body.setAttribute("namespace", MYNAMESPACE);
			/**
			 * Service part.
			 */
			Element service = doc.createElement("wsdl:service");
			service.setAttribute("name", webservice.replaceAll("/", "_"));
			definitions.appendChild(service);
			Element port = doc.createElement("wsdl:port");
			port.setAttribute("binding", "tns:" + webservice.replaceAll("/", "_") + "_Binding");
			port.setAttribute("name", webservice.replaceAll("/", "_") + "_Port");
			Element soap_address = doc.createElementNS(SOAP, "soap:address");
			port.appendChild(soap_address);
			System.err.println("path = " + request.getServletPath());
			System.err.println("name = " + getServletConfig().getServletName());
			System.err.println("contextpath = " + request.getContextPath());
			System.err.println("server  =" + request.getServerName());
			System.err.println("porr  =" + request.getServerPort());
			System.err.println("request uri = " + request.getRequestURI());
			soap_address.setAttribute("location", "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/SOAPMan");
			service.appendChild(port);
			OutputStream out = (OutputStream) response.getOutputStream();
			response.setContentType("text/xml");
			XMLDocumentUtils.write(doc, new OutputStreamWriter(out), false);
			out.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
