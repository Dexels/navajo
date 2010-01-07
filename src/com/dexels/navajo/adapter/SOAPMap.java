package com.dexels.navajo.adapter;


import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.dexels.navajo.adapter.xmlmap.TagMap;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class SOAPMap implements Mappable {

	public String url;
	public String soapAction;
	public XMLMap xmlRequest;
	public Binary requestBody;
	public boolean doSend;
	public Binary responseBody;
	public XMLMap xmlReponse;
	public ArrayList<String> namespaces = new ArrayList<String>();
	public String namespace;
	
	public void kill() {
	}
	
	public void setNamespace(String ns){
		System.err.println("Adding namespace to list: " + ns);
		namespaces.add(ns);
	}

	public void load(Access access) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

	public void setUrl(String s) {
		this.url = s;
	}
	
	public void setSoapAction(String s) {
		this.soapAction = s;
	}
	
	public XMLMap getXmlResponse() throws UserException {
		XMLMap xml = new XMLMap();
		xml.setContent(responseBody);
		return xml;
	}
	
	public Binary getResponseBody() {
		return this.responseBody;
	}
	
	public void setRequestBody(Binary b) {
		System.err.println("Constructing envelope, I have " + namespaces.size() + " extra namespaces" );
		StringBuffer sb = new StringBuffer();
		sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" ");
		for(int i=0;i<namespaces.size();i++){
			System.err.println("Appending extra namespace: " + namespaces.get(i));
			sb.append(namespaces.get(i));
		}
		sb.append(">\n");
		sb.append("<SOAP-ENV:Header/>\n<SOAP-ENV:Body>\n");
		sb.append(new String( b.getData() ));
		sb.append("</SOAP-ENV:Body>\n");
		sb.append("</SOAP-ENV:Envelope>");
		this.requestBody = new Binary( sb.toString().getBytes() );		
	}
	
	public void setDoSend(boolean b) throws UserException {
		if ( b ) {
			URL endpoint;
			try {
				endpoint = new URL(url);

				//Endpoint ep = new Endpoint(SIMPLE_SAMPLE_URI);

				SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
				SOAPConnection connection = scf.createConnection();

				MessageFactory mf = MessageFactory.newInstance();

				// Create a message from the message factory.
				SOAPMessage msg = mf.createMessage();
				if ( soapAction != null ) {
					MimeHeaders hd = msg.getMimeHeaders();
					hd.addHeader("SOAPAction", soapAction);
				}

				SOAPPart soapPart= msg.getSOAPPart();
				
				StreamSource ss = new StreamSource( requestBody.getDataAsStream() );
				soapPart.setContent( ss );
				msg.setContentDescription("text/xml");
				msg.saveChanges();
				
				System.err.println("Sending message to URL: "+ endpoint);
				msg.writeTo(System.err);

				SOAPMessage reply = connection.call(msg, endpoint);
				
				TransformerFactory tFact=TransformerFactory.newInstance();
				Transformer transformer = tFact.newTransformer();
				Source src = reply.getSOAPPart().getContent();
				StringWriter sw = new StringWriter();
				StreamResult result=new StreamResult( sw);
				transformer.transform(src, result);
				responseBody = new Binary(sw.toString().getBytes());
				
			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
				throw new UserException(-1, e.getMessage(), e);
			}
		}
	}
	
	public static void main(String [] args) throws Exception {
		
		SOAPMap sm = new SOAPMap();
		sm.setUrl("http://localhost:8080//NavajoServer/SOAPMan");
		sm.setSoapAction("ProcessApproveAap");
		String aap = 
		 "<Process><Result>" +
         "<Status>OK</Status> " +
         "<Name>Anton</Name> " +
         "</Result></Process>";
  
		XMLMap xm = new XMLMap();
		xm.setContent(new Binary ( aap.getBytes() ));
//		xm.setStart("geocode");
//		xm.setChildName("location");
//		xm.setChildText("1600 Pennsylvania Av, Washington, DC");
		
		sm.setRequestBody(xm.getContent());
		sm.setDoSend(true);
		
		sm.getResponseBody().write(System.err);
		//System.err.println(response.getChildText("SOAP-ENV:Body/geocodeResponse/.*/item/lat"));
		
		
	}

	public void setXmlRequest(XMLMap xmlRequest) {
		
		setRequestBody(xmlRequest.getContent());
		
	}
}
