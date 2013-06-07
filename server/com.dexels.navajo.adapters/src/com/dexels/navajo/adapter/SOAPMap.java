package com.dexels.navajo.adapter;


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

import org.dexels.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.soapmap.SoapAttachment;
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
	// For Basic Authentication.
	public String username = null;
	public String password = null;
	
	public boolean doSend;
	public Binary responseBody;
	public XMLMap xmlReponse;
	public SOAPMessage soapReply;
	public ArrayList<String> namespaces = new ArrayList<String>();
	public String namespace;
	public ArrayList<SoapAttachment> requestAttachments = new ArrayList<SoapAttachment>();
	public ArrayList<SoapAttachment> responseAttachments = new ArrayList<SoapAttachment>();
	
	private final static Logger logger = LoggerFactory.getLogger(SOAPMap.class);
	
	public void kill() {
	}
	
	public void setNamespace(String ns){
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
		StringBuffer sb = new StringBuffer();
		sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" ");
		for(int i=0;i<namespaces.size();i++){
			sb.append(namespaces.get(i));
		}
		sb.append(">\n");
		// CHANGE: Prevent empty headers, it seems to mess up some soap servers
		if ( requestHeader != null ) {
			sb.append("<SOAP-ENV:Header>\n");
			sb.append(new String(requestHeader.getData()));
			sb.append("</SOAP-ENV:Header>\n");
		}
		
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
				
				logger.debug(new String(requestBody.getData()));
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
				
				
				if ( username != null && password != null) {
					MimeHeaders headers= msg.getMimeHeaders();
//					String authorization = new BASE64Encoder().encode( (username + ":" + password).getBytes());
					String authorization = Base64.encode( (username + ":" + password).getBytes());
					
					headers.addHeader("Authorization", "Basic " + authorization);
				}
				
				/* IMPORTANT! */
				msg.saveChanges();
				
				soapReply = connection.call(msg, endpoint);
				if (soapReply==null) {
					responseBody = new Binary();
				} else {
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
				
			}

			catch (Exception e) {
				throw new UserException(-1, e.getMessage(), e);
			} finally {
				if ( connection != null ) {
					try {
						connection.close();
					} catch (SOAPException e) {
						logger.error("Error: ", e);
					}
				}
			}
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
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
		sm.setUsername("aap");
		sm.setPassword("noot");
		//sm.setUrl("http://10.0.0.132:8080/corvus/httpd/ebms/sender");
		sm.setUrl("https://sportlink.rfxweb.nl/Test/Sportlink.asmx");
		//sm.setUrl("http://10.0.0.132:8080/corvus/httpd/ebms/receiver_list");
		sm.setSoapAction("https://sportlink.rfxweb.nl/GetClubMembers");
		String body = 
		"<GetClubMembers xmlns=\"https://sportlink.rfxweb.nl/\">" +
        "<Club_ID>V0013356</Club_ID>" +
        "</GetClubMembers>";
        
		String header =
			"<rfxws_header xmlns=\"https://sportlink.rfxweb.nl/\">" +
	      "<Bondskey>P8-raj3tas!ast</Bondskey>" +
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
		
		logger.debug("\n\nRESPONSE:\n");
		
//		FileOutputStream fw = new FileOutputStream(new File("/home/arjen/result.xml"));
//		sm.getResponseBody().write(fw);
//		fw.close();
		
		SoapAttachment [] sa = sm.getResponseAttachments();
		logger.debug("received " + sa.length + " attachments.");
		for ( int i = 0; i < sa.length; i++ ) {
			logger.debug("type: " + sa[i].getMimeType() + ", content:\n" +  new String(sa[i].getContent().getData()));
		}
		
		XMLMap response = sm.getXmlResponse();
		
		response.setChildName("/soap:Body/GetClubMembersResponse/GetClubMembersResult");
		
		logger.debug("Value: " + response.getChild().getChildren().length);

		
		//logger.debug(response.getChildText("SOAP-ENV:Body/geocodeResponse/.*/item/lat"));
		
		
	}

	public SoapAttachment [] getResponseAttachments() {
		SoapAttachment [] atts = new SoapAttachment[responseAttachments.size()];
		atts = responseAttachments.toArray(atts);
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
