package com.dexels.navajo.adapter;


import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.dexels.navajo.adapter.soapmap.SoapAttachment;
import com.dexels.navajo.adapter.xmlmap.TagMap;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.AuditLog;

public class SOAPMap implements Mappable {

	public String url;
	public String soapAction;
	public XMLMap xmlRequest;
	public Binary requestBody = null;
	public Binary requestHeader = null;

	public boolean doSend;
	public Binary responseBody;
	public XMLMap xmlReponse;
	public SOAPMessage soapReply;
	public ArrayList<String> namespaces = new ArrayList<String>();
	public String namespace;
	public ArrayList<SoapAttachment> requestAttachments = new ArrayList<SoapAttachment>();
	public ArrayList<SoapAttachment> responseAttachments = new ArrayList<SoapAttachment>();
	
	public void kill() {
	}
	
	public void setNamespace(String ns){
		//System.err.println("Adding namespace to list: " + ns);
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
	
	public void setRequestHeader(Binary requestHeader) {
		this.requestHeader = requestHeader;
	}

	public void setRequestBody(Binary b) {
		setRequestBody(b, false);
	}
	
	public void setRequestBody(Binary b, boolean containsSoapBody) {
		//System.err.println("Constructing envelope, I have " + namespaces.size() + " extra namespaces" );
		StringBuffer sb = new StringBuffer();
		sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" ");
		for(int i=0;i<namespaces.size();i++){
			//System.err.println("Appending extra namespace: " + namespaces.get(i));
			sb.append(namespaces.get(i));
		}
		sb.append(">\n");
		sb.append("<SOAP-ENV:Header>\n");
		if ( requestHeader != null ) {
			sb.append(new String(requestHeader.getData()));
		}
		sb.append("</SOAP-ENV:Header>\n");
		
		if ( !containsSoapBody ) {
			sb.append("<SOAP-ENV:Body>\n");
		}
		sb.append(new String( b.getData() ));
		if ( !containsSoapBody ) {
			sb.append("</SOAP-ENV:Body>\n");
		}
		sb.append("</SOAP-ENV:Envelope>");
		this.requestBody = new Binary( sb.toString().getBytes() );		
	}
	
	public void setDoSend(boolean b) throws UserException {
		
		SOAPConnection connection = null;
		
		if ( b ) {
			URL endpoint;
			try {
				endpoint = new URL(url);

				SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
				connection = scf.createConnection();

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
				
				System.err.println(new String(requestBody.getData()));
				// attachments
				if ( requestAttachments.size() > 0 ) {
					for ( int i = 0; i < requestAttachments.size(); i++ ) {
						if ( requestAttachments.get(i) != null && requestAttachments.get(i).getContent() != null ) {
							AttachmentPart attachment = msg.createAttachmentPart();
							attachment.setRawContent(requestAttachments.get(i).getContent().getDataAsStream(), requestAttachments.get(i).getMimeType() );
							msg.addAttachmentPart(attachment);
						} else {
							AuditLog.log("SOAPMAP", "Could not add empty attachment.");
						}
					}
				}
				
				/* IMPORTANT! */
				msg.saveChanges();
				
				soapReply = connection.call(msg, endpoint);
				
				TransformerFactory tFact=TransformerFactory.newInstance();
				Transformer transformer = tFact.newTransformer();
				Source src = soapReply.getSOAPPart().getContent();
				// Fetch attachments
				Iterator<AttachmentPart> iter = soapReply.getAttachments();
				while ( iter.hasNext() ) {
					AttachmentPart part = iter.next();
					responseAttachments.add(new SoapAttachment(new Binary(part.getRawContent()), part.getContentType()));
				}
				
				StringWriter sw = new StringWriter();
				StreamResult result=new StreamResult( sw);
				transformer.transform(src, result);
				responseBody = new Binary(sw.toString().getBytes());
				
			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
				throw new UserException(-1, e.getMessage(), e);
			} finally {
				if ( connection != null ) {
					try {
						connection.close();
					} catch (SOAPException e) {
						//e.printStackTrace();
					}
				}
			}
		}
	}
	
	public SOAPMessage getSoapReply() {
		return soapReply;
	}

	/*
	 * POST /Test/Sportlink.asmx HTTP/1.1
Host: sportlink.rfxweb.nl
Content-Type: text/xml; charset=utf-8
Content-Length: length
SOAPAction: "https://sportlink.rfxweb.nl/GetClub"

<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Header>
    <rfxws_header xmlns="https://sportlink.rfxweb.nl/">
      <Bondskey>string</Bondskey>
      <UserId>string</UserId>
      <UserPassword>string</UserPassword>
    </rfxws_header>
  </soap:Header>
  <soap:Body>
    <GetClub xmlns="https://sportlink.rfxweb.nl/">
      <ClubId>string</ClubId>
    </GetClub>
  </soap:Body>
</soap:Envelope>
	 */
	public static void main(String [] args) throws Exception {
		
		SOAPMap sm = new SOAPMap();
		//sm.setUrl("http://10.0.0.132:8080/corvus/httpd/ebms/sender");
		sm.setUrl("https://sportlink.rfxweb.nl/Test/Sportlink.asmx");
		//sm.setUrl("http://10.0.0.132:8080/corvus/httpd/ebms/receiver_list");
		sm.setSoapAction("https://sportlink.rfxweb.nl/GetClub");
		String body = 
		"<GetClub xmlns=\"https://sportlink.rfxweb.nl/\">" +
        "<ClubId>V0001101</ClubId>" +
        "</GetClub>";
        
		String header =
			"<rfxws_header xmlns=\"https://sportlink.rfxweb.nl/\">" +
	      "<Bondskey>P8-raj3t&amp;as!ast</Bondskey>" +
	      "<UserId>SLC</UserId>" +
	      "<UserPassword>#9eza5UHa#at5U?</UserPassword>" +
	    "</rfxws_header>";
  
		
//		xm.setStart("geocode");
//		xm.setChildName("location");
//		xm.setChildText("1600 Pennsylvania Av, Washington, DC");
		
		sm.setRequestHeader(new Binary(header.getBytes()));
		sm.setRequestBody(new Binary(body.getBytes()));
		//sm.setRequestBody(new Binary(aap.getBytes()));
  	  //  sm.setRequestAttachment(new Binary(new FileInputStream(new File("/home/arjen/ebms.txt"))));
//		sm.setAddAttachment(new Binary(new FileInputStream(new File("/home/arjen/wedstrijden.csv"))));
		sm.setDoSend(true);
		
		System.err.println("\n\nRESPONSE:\n");
		sm.getResponseBody().write(System.err);
		
		SoapAttachment [] sa = sm.getResponseAttachments();
		System.err.println("received " + sa.length + " attachments.");
		for ( int i = 0; i < sa.length; i++ ) {
			System.err.println("type: " + sa[i].getMimeType() + ", content:\n" +  new String(sa[i].getContent().getData()));
		}
		//System.err.println(response.getChildText("SOAP-ENV:Body/geocodeResponse/.*/item/lat"));
		
		
	}

	public SoapAttachment [] getResponseAttachments() {
		SoapAttachment [] atts = new SoapAttachment[responseAttachments.size()];
		atts = (SoapAttachment []) responseAttachments.toArray(atts);
		return atts;
	}
	
	public void setRequestAttachment(Binary b) {
		requestAttachments.add(new SoapAttachment(b, b.getMimeType()));
	}
	
	public void setXmlRequest(XMLMap xmlRequest) {
		
		boolean containsSoapBody = ( xmlRequest.getName().equals("SOAP-ENV:Body"));
		
		setRequestBody(xmlRequest.getContent(), containsSoapBody);
		
	}
}
