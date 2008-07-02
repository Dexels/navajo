package com.dexels.navajo.server.listener.wsdl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;

public class SimpleWS extends HttpServlet {

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
		try{
			
			Document doc = XMLDocumentUtils.createDocument();
			Element definitions = doc.createElement("definitions");
			definitions.setAttribute("xmlns", WSDL);
			definitions.setAttribute("xmlns:soap", SOAP);
			definitions.setAttribute("xmlns:xsd", XMLSCHEMA);
			definitions.setAttribute("targetNamespace", MYNAMESPACE);
			definitions.setAttribute("xmlns:tns", MYNAMESPACE);
			definitions.setAttribute("xmlns:esns", MYSCHEMAS);
			
			doc.appendChild(definitions);
			Element schema = findTypesNode(doc);
			
//			<definitions targetNamespace="http://www.dexels.nl/navajo/webservice.xsd" xmlns="http://schemas.xmlsoap.org/wsdl/" xmlns:esns="navajo:webservice:types" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://www.dexels.nl/navajo/webservice.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><types><xsd:schema targetNamespace="http://www.dexels.nl/navajo/webservice.xsd"><xsd:complexType name="String"><xsd:all><xsd:element name="String" type="xsd:string"/></xsd:all>
//			</xsd:complexType>
//			</xsd:schema>
//			</types>
//			<message name="ProcessReq"/><message name="ProcessRes"><part name="String" type="xsd:string"/></message>
//			<portType name="shell_provisioning_getstring_PortType"><operation name="Process"><input message="tns:ProcessReq" name="ProcessReq"/><output message="tns:ProcessRes" name="ProcessRes"/></operation>
//			</portType>
//			<binding name="shell_provisioning_getstring_Binding" type="tns:shell_provisioning_getstring_PortType"><soap:binding style="rpc" transport="http://schemas.xmlsoap.org/soap/http"/><operation name="Process"><soap:operation soapAction="shell/provisioning/getstring"/><input name="ProcessReq"><soap:body namespace="http://www.dexels.nl/navajo/webservice.xsd" use="literal"/></input>
//			<output name="ProcessRes"><soap:body namespace="http://www.dexels.nl/navajo/webservice.xsd" use="literal"/></output>
//			</operation>
//			</binding>
//			<service name="shell_provisioning_getstring"><documentation>Dit is de documentatie</documentation>
//			<port binding="tns:shell_provisioning_getstring_Binding" name="shell_provisioning_getstring_Port"><soap:address location="http://amsdc1-s-1414.europe.shell.com:8180//navajo/SOAPMan"/></port>
//			</service>
//			</definitions>
			
//			Element complexType = doc.createElementNS(XMLSCHEMA, "xsd:complexType");
//			complexType.setAttribute("name", "String");
//			schema.appendChild(complexType);

//			Element sequence = doc.createElementNS(XMLSCHEMA, "xsd:all");
//			complexType.appendChild(sequence);
			
			Element elm = doc.createElementNS(XMLSCHEMA, "xsd:element");
			elm.setAttribute("type", "xsd:string");
			elm.setAttribute("name", "getString_response");
			schema.appendChild(elm);
			
			Element inputMessage = doc.createElement("message");
			inputMessage.setAttribute("name", "ProcessReq");
			definitions.appendChild(inputMessage);


			Element outputMessage = doc.createElement("message");
			outputMessage.setAttribute("name", "ProcessRes");
			definitions.appendChild(outputMessage);
			
			Element outputBody = doc.createElement("part");
			outputBody.setAttribute("name", "getString_response");
			outputBody.setAttribute("element", "tns:getString_response");
//			outputBody.setAttribute("type", "xsd:string");
			outputMessage.appendChild(outputBody);
			

			/**
			 * Add portType.
			 */
			Element portType = doc.createElement("portType");
			portType.setAttribute("name","shell_provisioning_getstring_PortType");
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
			binding.setAttribute("name", "shell_provisioning_getstring_Binding");
			binding.setAttribute("type", "tns:shell_provisioning_getstring_PortType");
			definitions.appendChild(binding);
			Element soap = doc.createElementNS(SOAP, "soap:binding");
			soap.setAttribute("style", "rpc");
			soap.setAttribute("transport", "http://schemas.xmlsoap.org/soap/http");
			binding.appendChild(soap);
			Element operation_b = doc.createElement("operation");
			operation_b.setAttribute("name", "Process");
			binding.appendChild(operation_b);
			Element soap_o = doc.createElementNS(SOAP, "soap:operation");
			soap_o.setAttribute("soapAction", "shell/provisioning/getstring" );
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
			service.setAttribute("name", "shell_provisioning_getstring");
			definitions.appendChild(service);
			Element documentation = doc.createElement("documentation");
			documentation.setTextContent("Dit is de documentatie");
			service.appendChild(documentation);
			Element port = doc.createElement("port");
			port.setAttribute("binding",  "tns:shell_provisioning_getstring_Binding");
			port.setAttribute("name",  "shell_provisioning_getstring_Port");
			Element soap_address = doc.createElementNS(SOAP, "soap:address");
			port.appendChild(soap_address);
			
			System.err.println("path = " + request.getServletPath());
			System.err.println("name = " + getServletConfig().getServletName());
			System.err.println("contextpath = " + request.getContextPath());
			System.err.println("server  =" + request.getServerName());
			System.err.println("porr  =" + request.getServerPort());
			System.err.println("request uri = " + request.getRequestURI());
			
			soap_address.setAttribute("location", "http://amsdc1-s-1414.europe.shell.com:8180/navajo/SimpleWS");
			service.appendChild(port);
			
			OutputStream out = (OutputStream) response.getOutputStream();
			response.setContentType("text/xml");
			XMLDocumentUtils.write(doc, new OutputStreamWriter(out), false);
			out.close();
					
		}catch(Exception e){
			
		}
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
		
		NodeList l = types.getElementsByTagName("xsd:schema");
		if(l.getLength() > 0){
			schema = (Element) l.item(0);
		}
		
		System.err.println("Schema: " + schema);
		if ( schema == null) {
			schema = d.createElementNS(XMLSCHEMA, "xsd:schema");
			schema.setAttribute("targetNamespace", MYNAMESPACE);
			types.appendChild(schema);
			System.err.println("Schema added!");		
			
		}
		return schema;
	}
	


	

	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try{
		
//			<?xml version="1.0" encoding="UTF-8"?>
//			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
//			    <soapenv:Body>
//			        <String>Does this work?</String>
//			    </soapenv:Body>
//			</soapenv:Envelope>
			
			Document doc = XMLDocumentUtils.createDocument();
			Element envelope = doc.createElement("soapenv:Envelope");
			envelope.setAttribute("xmlns:soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
			doc.appendChild(envelope);
			
			Element body = doc.createElement("soapenv:Body");
			Element string = doc.createElement("tns:getString_response");
			string.setAttribute("xmlns:tns", MYNAMESPACE);
//			string.setAttribute("type", "xsd:string");
			body.appendChild(string);
			envelope.appendChild(body);
			string.setTextContent("Does this work?");
			
						
			OutputStream out = (OutputStream) response.getOutputStream();
			response.setContentType("text/xml; charset=UTF-8");
			
			// NOTE: XMLDocumentUtils.write does not write the <?xml?> tag
			XMLDocumentUtils.write(doc, new OutputStreamWriter(out), false);
			
			// NOTE: XMLDocumentUtils.toXML does write the <?xml?> tag.
//			XMLDocumentUtils.toXML(doc, null, null, new StreamResult(System.err));
			
			out.close();


		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

	}
	
	public static void main(String[] args){
		Document doc = XMLDocumentUtils.createDocument();
		Element envelope = doc.createElement("soapenv:Envelope");
		envelope.setAttribute("xmlns:soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
		doc.appendChild(envelope);
		
		Element body = doc.createElement("soapenv:Body");
		Element string = doc.createElement("String");
		body.appendChild(string);
		envelope.appendChild(body);
		string.setTextContent("Does this work");
		
					
		try{
			// Does it work, this one does not include the <?xml version="1.0" encoding="UTF-8"?> tag.
			
			XMLDocumentUtils.write(doc, new OutputStreamWriter(System.err), false);
			System.err.println("====================== v2 ========================");
			XMLDocumentUtils.toXML(doc, null, null, new StreamResult(System.err));
		}catch(Exception e){
			
		}

	}
}
