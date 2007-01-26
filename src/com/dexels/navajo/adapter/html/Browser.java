package com.dexels.navajo.adapter.html;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.net.ssl.HttpsURLConnection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;

/**
 * This class implements a rudimentary non-visual web browser for use in automated web browsing applications (e.g. web robots).
 * 
 * @author Arjen Schoneveld
 *
 */
public class Browser {

	public String sessionid;
	public boolean debug = false;
	
	public String refererField;
	public String hostField;
	
	private String connectionField = "keep-alive";
	private String keepAliveField = "300";
	private String encoding;
	// raw content.
	private String currentContent;
	// tidied (XHTML) content.
	private String tidiedStringContent;
	// XML content
	private Document tidiedContent;
	// Map with all HTML input form parameters.
	private HashMap formParameters;
	
	public Browser(String refererField, String hostField) {
		this.refererField = refererField;
		this.hostField = hostField;
	}
	
	public Browser() {
		this.refererField = refererField;
		this.hostField = hostField;
	}
	
	public void setRefererField(String s) {
		this.refererField = s;
	}
	
	public void setHostField(String s) {
		this.hostField = s;
	}
	
	public String getFormAction() {
	
		NodeList l = tidiedContent.getElementsByTagName("form");
		if ( l.getLength() > 0 ) {
			Element e = (Element) l.item(0);
			return e.getAttribute("action");
		} else {
			return null;
		}
		
	}
	
	public HashMap getFormParameters() {
		return formParameters;
	}
	
	/**
	 * Transforms the form (if it exists) inside the HTML into Navajo XML.
	 * @return
	 */
	public Navajo getNavajo() throws NavajoException {
		Navajo out = NavajoFactory.getInstance().createNavajo();
		com.dexels.navajo.document.Message m = NavajoFactory.getInstance().createMessage(out, "Form");
		out.addMessage(m);
		
		NodeList l = tidiedContent.getElementsByTagName("input");
		for (int i = 0; i < l.getLength(); i++ ) {
			if ( l.item(i) instanceof Element ) {
				Element e = (Element) l.item(i);
				String parameter = e.getAttribute("name");
				String value = e.getAttribute("value");
				String type = e.getAttribute("type");
				String navajotype;
				if ( type.toLowerCase().equals("checkbox") ) {
					navajotype = "boolean";
				} else {
					navajotype = "string";
				}
				Property p = NavajoFactory.getInstance().createProperty(out, parameter, navajotype, value, 100, parameter, Property.DIR_IN);
				m.addProperty(p);
				formParameters.put(parameter, value);
			}
		}
		
		l = tidiedContent.getElementsByTagName("select");
		System.err.println("l = " + l.getLength());
		for (int i = 0; i < l.getLength(); i++ ) {
			if ( l.item(i) instanceof Element ) {
				Element e = (Element) l.item(i);
				String parameter = e.getAttribute("name");
				String value = e.getAttribute("value");
				String type = e.getAttribute("type");
				
				Property p = NavajoFactory.getInstance().createProperty(out, parameter, "1", parameter, Property.DIR_IN);
				NodeList options = e.getElementsByTagName("option");
				System.err.println("options: " + options.getLength());
				for (int j = 0; j < options.getLength(); j++) {
					Element e2 = (Element) options.item(j);
					Selection s = NavajoFactory.getInstance().createSelection(out, e2.getAttribute("name"), 
							e2.getFirstChild().getNodeValue(), false);
					p.addSelection(s);
				}
				
				m.addProperty(p);
				formParameters.put(parameter, value);
			}
		}
		
		return out;
		
	}
	private void setFormParameters() {
		
		formParameters = new HashMap(); 
		NodeList l = tidiedContent.getElementsByTagName("input");
		for (int i = 0; i < l.getLength(); i++ ) {
			if ( l.item(i) instanceof Element ) {
				Element e = (Element) l.item(i);
				String parameter = e.getAttribute("name");
				String value = e.getAttribute("value");
				formParameters.put(parameter, value);
			}
		}
	}
	
	public String getFormParameter(String param) {
	
		if ( formParameters.get(param) != null ) {
			return (String) formParameters.get(param);
		} else {
			return null;
		}
	}
	
	public String constructFormBody(HashMap hm) {

		Iterator i = hm.keySet().iterator();
		StringBuffer content = new StringBuffer();
		while ( i.hasNext() ) {
			String key = (String) i.next();
			String value = (String) hm.get(key);
			content.append(key + "=" + URLEncoder.encode(value) + "&");
		}

		return content.toString().substring(0, content.length() - 1);
	}

	public String getSessionId() {
		return sessionid;
	}
	 
	private void readCookies(URLConnection conn)
	{
		if ( debug ) {
			System.err.println("in readCookies()");
			System.err.println("Header fields are:");
		}
		String sessionCookie = null;
		try
		{
			int headerPosition = 0;
			String data = conn.getHeaderField(headerPosition);
			while(data!=null)
			{
				String key = conn.getHeaderFieldKey(headerPosition);
				if(key!=null)
				{
					if ( debug ) {
						System.err.println(key + "=" + data);
					}
					if(key.compareToIgnoreCase("Set-Cookie")==0)
					{
						String subCookie = null;
						subCookie = data.substring(0, data.indexOf(";"));
						subCookie = subCookie.trim();
						if ( sessionCookie == null ) {
							sessionCookie = subCookie; 
						} else {
							sessionCookie = subCookie + ";" +sessionCookie;
						}
					} 
				}
				headerPosition++;
				data = conn.getHeaderField(headerPosition);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		sessionid = sessionCookie;
		
	}
	
	public String openUrl(String urlstring, String content, boolean doOutput) throws Exception {
		URL url = new URL(urlstring);
		
		URLConnection urlcon = url.openConnection();
		//System.err.println("Opened connection: " + urlcon);
		urlcon.setDoOutput(doOutput);
		urlcon.setDoInput(true);

		urlcon.setRequestProperty("Referer", this.refererField);
		urlcon.setRequestProperty("Connection", this.connectionField);
		urlcon.setRequestProperty("KeepAlive", this.keepAliveField);
		urlcon.setRequestProperty("Host", this.hostField);
		if ( sessionid != null ) {
			urlcon.setRequestProperty("Cookie", sessionid);
		} else {
			// Read header fields:
			try {
				readCookies(urlcon);
			} catch (Throwable e) {}
		}

		if ( doOutput) {  // POST
			if ( urlcon instanceof HttpsURLConnection) {
				((HttpsURLConnection) urlcon).setRequestMethod("POST");
			} else {
				((HttpURLConnection) urlcon).setRequestMethod("POST");
			}
			urlcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlcon.setRequestProperty("Content-Length", content.length()+ "" );
			
			//System.err.println("about to send: " + content);
//			Send POST output.
			DataOutputStream printout = new DataOutputStream( urlcon.getOutputStream() );
			//System.err.println("Opened outputstream");
			printout.writeBytes (content);
			System.err.println("Wrote content");
			printout.flush ();
			printout.close ();
		}
		
		// Determine document encoding.
		encoding = "UTF-8";
		StringTokenizer t = new StringTokenizer(urlcon.getContentType(), ";");
		while ( t.hasMoreTokens() ) {
			String s = t.nextToken();
			if ( s.indexOf("charset") != -1) {
				encoding = s.substring(8);
			}
		}
		System.err.println("encoding = " + encoding);
		
		System.err.println("TRYING TO OPEN URL");
		BufferedReader r = null;
		try {
			r = new BufferedReader( new InputStreamReader( urlcon.getInputStream(), encoding ) );
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.err.println("unknown encoding: " + encoding);
			r = new BufferedReader( new InputStreamReader( urlcon.getInputStream(), "8859_1" ) );
		}
		System.err.println("DONE");
		String line;
		StringBuffer form = new StringBuffer();
	
		boolean inscript = false;
		
		while ( (line = r.readLine()) != null ) {
			
			// Remove javascript.
			if ( line.indexOf("<script") != -1 && !inscript) {
				inscript = true;
			}
		
			if ( line.indexOf("!DOCTYPE") == -1 && !inscript) {
				form.append(line+"\n");
			} else {
				//System.err.println("Ignoring line: " + line);
			}
			
			if ( line.indexOf("</script>") != -1 && inscript) {
				inscript = false;
			}
		}
		
		currentContent = form.toString();
		try {
			setXHTML();
			setFormParameters();
		} catch (Exception e) {
			//e.printStackTrace(System.err);
		}
		
		return form.toString();
	}
	
	private void setXHTML() throws Exception {
		Tidy t = new Tidy();
		t.setQuiet(true);
		t.setFixBackslash(true);
		t.setFixComments(true);
		t.setDropFontTags(true);
		t.setWrapScriptlets(true);
		t.setQuoteMarks(true);
		t.setQuoteAmpersand(true);
		t.setQuoteNbsp(true);
		t.setShowWarnings(false);
		t.setEncloseBlockText(true);
		t.setEncloseText(true);
		
		ByteArrayOutputStream  out= new ByteArrayOutputStream();
		t.setXmlOut(true);
		
		t.parse(new ByteArrayInputStream(currentContent.getBytes()), out);
		
		tidiedStringContent =  new String(out.toByteArray());
		
		tidiedStringContent = tidiedStringContent.replaceAll("&nbsp;", "");
		tidiedStringContent = tidiedStringContent.replaceAll("&copy;", "");
		tidiedStringContent = tidiedStringContent.replaceAll("&amp;", "");
		tidiedStringContent = tidiedStringContent.replaceAll("&quot;", "");
		tidiedStringContent = tidiedStringContent.replaceAll("&apos;", "");
		tidiedStringContent = tidiedStringContent.replaceAll("&[A-z]*;", "");
		//&#0;
		tidiedStringContent = tidiedStringContent.replaceAll("&#0;", "");
//		tidiedStringContent = tidiedStringContent.replaceAll("<b.*>", "");
//		tidiedStringContent = tidiedStringContent.replaceAll("</b>", "");
//		tidiedStringContent = tidiedStringContent.replaceAll("<p.*>", "");
//		tidiedStringContent = tidiedStringContent.replaceAll("</p>", "");
		
//		XMLElement xe = new CaseSensitiveXMLElement();
//		xe.parseString(tidiedStringContent);
	
		tidiedContent = XMLDocumentUtils.createDocument(new ByteArrayInputStream(tidiedStringContent.getBytes()), false);
		
	}
	
	public static void main(String [] args) throws Exception {
		Browser b = new Browser("http://babelfish.altavista.com/", "babelfish.altavista.com");
		b.debug = true;
		String result = b.openUrl("http://babelfish.altavista.com/", null, false);
		
//		System.err.println(b.currentContent);
		Navajo n = b.getNavajo();
//		
		n.write(System.err);
		System.err.println(b.getFormAction());
		
		
		HashMap parameters = b.getFormParameters();
		parameters.put("trtext", "Coffee");
		parameters.put("lp", "en_el");
		
		// next action, login and post to /ideal/firstLogonPreAction.do
		// Find password using username value:

		result = b.openUrl(b.getFormAction(), b.constructFormBody(b.formParameters), true);
		n = b.getNavajo();
		n.write(System.err);
		System.err.println("vertaling = " + n.getMessage("Form").getProperty("q").getValue());
		//System.err.println(b.currentContent);
//		HashMap clubdetails = b.getFormParameters();
//		Iterator iter= clubdetails.keySet().iterator();
//		while ( iter.hasNext() ) {
//			String parameter = (String) iter.next();
//			String value = (String) clubdetails.get(parameter);
//			System.err.println(parameter + "=" + value);
//		}
		
	}

}
