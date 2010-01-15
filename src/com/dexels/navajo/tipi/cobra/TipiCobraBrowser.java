package com.dexels.navajo.tipi.cobra;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.cobra.impl.NavajoHtmlRendererContext;
import com.dexels.navajo.tipi.cobra.impl.TipiHtmlPanel;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;

public class TipiCobraBrowser extends TipiSwingDataComponentImpl {

	private TipiHtmlPanel myItem;
	private SimpleUserAgentContext localContext;
	private NavajoHtmlRendererContext renderingContext;
	public Object createContainer() {
		Logger.getLogger("org.lobobrowser").setLevel(Level.WARNING);
		
		myItem = new TipiHtmlPanel();
		localContext = new SimpleUserAgentContext();
		renderingContext = new NavajoHtmlRendererContext(myItem, localContext,this);
		// Note that document builder should receive both contexts.
//		DocumentBuilderImpl dbi = new DocumentBuilderImpl(localContext, renderingContext);
		myItem.setRenderingContext(renderingContext);
		return myItem;
	}
	@Override
	protected void setComponentValue(String name, Object object) {
//		if(name.equals("binary")) {
//			System.err.println("Setting to binary: "+object.toString());
//			try {
//				myItem.setBinary((Binary) object);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		if(name.equals("url")) {
				try {
					renderingContext.navigate((String) object);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if(name.equals("binary")) {
			try {
				Binary b = (Binary)object;
//				Reader documentReader = new FileReader("mailexample.html");
				DocumentBuilderImpl dbi = new DocumentBuilderImpl(localContext, renderingContext);
				String documentURI = "file:///";
				// A documentURI should be provided to resolve relative URIs.
				Document document = dbi.parse(new InputSourceImpl(new InputStreamReader(b.getDataAsStream()), documentURI));
				// Now set document in panel. This is what causes the document to render.
				myItem.setDocument(document, renderingContext);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
			
	
	}
		if(name.equals("emailNavajo")) {
			System.err.println("setting emailNavjao");
			Navajo emailNavajo = (Navajo)object;
			try {
				emailNavajo.write(System.err);
			} catch (NavajoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				URL u = parkMultipart(emailNavajo, true);
				System.err.println("URL: "+u.toString());
				renderingContext.navigate(u.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.setComponentValue(name, object);
		
	}
	
	private  URL parkMultipart(com.dexels.navajo.document.Navajo pp,boolean doDeleteOnExit) throws IOException {
		Map<String,String> replacementMap = new HashMap<String, String>();
		File mailFile = File.createTempFile("index",".html");
		Message parts = pp.getMessage("Mail/Parts");
		Binary body = (Binary) parts.getAllMessages().get(0).getProperty("Content").getTypedValue();
//		if(!destinationFolder.exists()) {
//			destinationFolder.mkdir();
//		}
		for (int i = 1; i < parts.getAllMessages().size(); i++) {
			Message currentPart = parts.getAllMessages().get(i);
			String fileName = currentPart.getProperty("FileName").getValue();
			String nextName = "cid:attach-nr-"+(i-1);
			replacementMap.put(nextName, fileName);
			Binary attach = (Binary) currentPart.getProperty("Content").getTypedValue();
			File currentFile = new File(mailFile.getParentFile(),fileName);
			attach.write(new FileOutputStream(currentFile));
			if(doDeleteOnExit) {
				currentFile.deleteOnExit();
			}
		}
		String bodyText = new String(body.getData());
		String replaced = replaceAttributes("src",bodyText,replacementMap);
		
		PrintWriter fos = new PrintWriter( new FileWriter(mailFile));
		fos.print(replaced);
		fos.flush();
		fos.close();
		if(doDeleteOnExit) {
			mailFile.deleteOnExit();
		}
		return mailFile.toURI().toURL();
		
	}

	public String replaceAttributes(String attributeName, String htmlString,Map<String,String> replacementMap) {
		  Pattern patt = Pattern.compile(attributeName+"=\"([^<]*)\"");
		  Matcher m = patt.matcher(htmlString);
		  StringBuffer sb = new StringBuffer(htmlString.length());
		  while (m.find()) {
		    String text = m.group(1);
		    // ... possibly process 'text' ...
		    m.appendReplacement(sb, Matcher.quoteReplacement(attributeName+"=\""+replacementMap.get(text)+"\""));
		  }
		  m.appendTail(sb);
		  return sb.toString();
		}
	
	public boolean allowLinking() {
		return false;
	}

	
}
