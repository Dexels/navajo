package com.dexels.navajo.tipi.cobra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.cobra.impl.NavajoHtmlRendererContext;
import com.dexels.navajo.tipi.cobra.impl.TipiHtmlPanel;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;

public class TipiCobraBrowser extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = 2950228008172758098L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiCobraBrowser.class);
	private TipiHtmlPanel myItem;
	private SimpleUserAgentContext localContext;
	private NavajoHtmlRendererContext renderingContext;

	public Object createContainer() {
		java.util.logging.Logger.getLogger("org.lobobrowser").setLevel(Level.WARNING);

		myItem = new TipiHtmlPanel();
		localContext = new SimpleUserAgentContext();
		renderingContext = new NavajoHtmlRendererContext(myItem, localContext, this);
		// Note that document builder should receive both contexts.
		// DocumentBuilderImpl dbi = new DocumentBuilderImpl(localContext,
		// renderingContext);
		myItem.setRenderingContext(renderingContext);
		return myItem;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		// if(name.equals("binary")) {
		// logger.info("Setting to binary: "+object.toString());
		// try {
		// myItem.setBinary((Binary) object);
		// } catch (IOException e) {
		// logger.error("Error: ",e);
		// }
		// }
		if (name.equals("url")) {
			try {
				renderingContext.navigate((String) object);
			} catch (MalformedURLException e) {
				logger.error("Error: ",e);
			}
		}
		if (name.equals("binary")) {
			try {
				Binary b = (Binary) object;
				// Reader documentReader = new FileReader("mailexample.html");
				DocumentBuilderImpl dbi = new DocumentBuilderImpl(localContext, renderingContext);
				String documentURI = "file:///";
				// A documentURI should be provided to resolve relative URIs.
				Document document = dbi.parse(new InputSourceImpl(new InputStreamReader(b.getDataAsStream()), documentURI));
				// Now set document in panel. This is what causes the document to
				// render.
				myItem.setDocument(document, renderingContext);
			} catch (IOException e) {
				logger.error("Error: ",e);
			} catch (SAXException e) {
				logger.error("Error: ",e);
			}

		}
		if (name.equals("emailNavajo")) {
			logger.info("setting emailNavjao");
			Navajo emailNavajo = (Navajo) object;
			try {
				URL u = null;

				u = createNavajoUrl(emailNavajo);
				renderingContext.navigate(u.toString());

			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
		super.setComponentValue(name, object);

	}

	private URL createNavajoUrl(Navajo emailNavajo) throws IOException {
		URL u;
		Message parts = emailNavajo.getMessage("Mail/Parts");
		if (parts == null) {
			u = parkSingle(emailNavajo, true);
		} else {
			String contentType = (String) emailNavajo.getProperty("Mail/ContentType").getTypedValue();
			if(contentType.startsWith("multipart/alternative")) {
				u = parkAlternative(emailNavajo, true);
				
			} else {
				u = parkMultipart(emailNavajo, true);
			}
		}
		return u;
	}
	private URL parkAlternative(Navajo emailNavajo, boolean doDeleteOnExit) throws IOException {
		Binary body = null;
		Message parts = emailNavajo.getMessage("Mail/Parts");
		Message lastPart = parts.getAllMessages().get(parts.getAllMessages().size()-1);

		body = (Binary) lastPart.getProperty("Content").getTypedValue();
		return parkPart(body, doDeleteOnExit);
	}

	
	private URL parkSingle(Navajo emailNavajo, boolean doDeleteOnExit) throws IOException {
		Binary body = null;
		body = (Binary) emailNavajo.getProperty("Mail/Content").getTypedValue();
		return parkPart(body, doDeleteOnExit);
	}

	private URL parkPart(Binary body, boolean doDeleteOnExit) throws IOException, MalformedURLException {
		File mailFile = null;
		mailFile = File.createTempFile("index", ".html");
		String bodyText = new String(body.getData());
		PrintWriter fos = new PrintWriter(new FileWriter(mailFile));
		fos.print(bodyText);
		fos.flush();
		fos.close();
		if (doDeleteOnExit) {
			mailFile.deleteOnExit();
		}
		return mailFile.toURI().toURL();
	}

	private URL parkMultipart(com.dexels.navajo.document.Navajo pp, boolean doDeleteOnExit) throws IOException {
		Map<String, String> replacementMap = new HashMap<String, String>();
		File mailFile = File.createTempFile("index", ".html");
		File mailFileOutput = File.createTempFile("indexProcessed", ".html");
//		logger.info("Mailfile: "+mailFile);
		Message parts = pp.getMessage("Mail/Parts");
		Binary body = (Binary) parts.getAllMessages().get(0).getProperty("Content").getTypedValue();
		FileOutputStream foss = new FileOutputStream(mailFile);
		body.write(foss);
		foss.close();
		for (int i = 1; i < parts.getAllMessages().size(); i++) {
			Message currentPart = parts.getAllMessages().get(i);
			String fileName = currentPart.getProperty("FileName").getValue();
			String nextName = "cid:attach-nr-" + (i - 1);
			if(fileName==null) {
				fileName = nextName;
			}
			replacementMap.put(nextName, fileName);
			Binary attach = (Binary) currentPart.getProperty("Content").getTypedValue();
			File parentFile = mailFile.getParentFile();
			File currentFile = new File(parentFile, fileName);
			attach.write(new FileOutputStream(currentFile));
			if (doDeleteOnExit) {
				currentFile.deleteOnExit();
			}
		}
		String bodyText = new String(body.getData());
		String replaced = replaceAttributes("src", bodyText, replacementMap);

		PrintWriter fos = new PrintWriter(new FileWriter(mailFileOutput));
		fos.print(replaced);
		fos.flush();
		fos.close();
		if (doDeleteOnExit) {
			mailFile.deleteOnExit();
		}
		return mailFile.toURI().toURL();

	}

	public String replaceAttributes(String attributeName, String htmlString, Map<String, String> replacementMap) {
		Pattern patt = Pattern.compile(attributeName + "=\"([^<]*)\"");
		Matcher m = patt.matcher(htmlString);
		StringBuffer sb = new StringBuffer(htmlString.length());
		while (m.find()) {
			String text = m.group(1);
			// ... possibly process 'text' ...
			m.appendReplacement(sb, Matcher.quoteReplacement(attributeName + "=\"" + replacementMap.get(text) + "\""));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public boolean allowLinking() {
		return false;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		TipiCobraBrowser tc = new TipiCobraBrowser();
		final FileInputStream stream = new FileInputStream("tmlexample.xml");
		Navajo emailNavajo = NavajoFactory.getInstance().createNavajo(stream);
		stream.close();
		URL u = tc.createNavajoUrl(emailNavajo);
		logger.info("u: "+u);
		Thread.sleep(100000);
	}
}
