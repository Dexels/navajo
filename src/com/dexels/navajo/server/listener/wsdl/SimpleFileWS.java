package com.dexels.navajo.server.listener.wsdl;

import java.io.File;
import java.io.FileInputStream;
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


public class SimpleFileWS extends HttpServlet {

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
					
			OutputStream out = (OutputStream) response.getOutputStream();
			response.setContentType("text/xml");
			
			File f = new File("c:/wsdl-out.xml");
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			int read = -1;
			while((read = fis.read(buffer)) > -1){
				out.write(buffer, 0, read);
			}			
			out.close();
					
		}catch(Exception e){
			
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try{
		
			OutputStream out = (OutputStream) response.getOutputStream();
			response.setContentType("text/xml");
			
			File f = new File("c:/repsonse.xml");
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			int read = -1;
			while((read = fis.read(buffer)) > -1){
				out.write(buffer, 0, read);
			}			
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
