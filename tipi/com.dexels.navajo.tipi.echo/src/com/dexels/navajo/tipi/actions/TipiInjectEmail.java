package com.dexels.navajo.tipi.actions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiInjectEmail extends TipiAction {

	private static final long serialVersionUID = -4545529880859492751L;




	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		Navajo input = (Navajo) getEvaluatedParameter("navajo", event);
		Navajo inputCopy = input.copy();
		String urlTemplate = (String) getEvaluatedParameterValue("urlTemplate", event);
		String navajoName = (String) getEvaluatedParameterValue("navajoName", event);
		
		try {
			createNavajoUrl(navajoName, inputCopy, urlTemplate);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getContext().injectNavajo(navajoName, inputCopy);
	}

	
	
	
	private String createNavajoUrl(String navajoName, Navajo emailNavajo, String urlTemplate) throws IOException {
		String u;
		Message parts = emailNavajo.getMessage("Mail/Parts");
		if (parts == null) {
			u = parkSingle(navajoName,emailNavajo,urlTemplate);
		} else {
			String contentType = (String) emailNavajo.getProperty("Mail/ContentType").getTypedValue();
			if(contentType.startsWith("multipart/alternative")) {
				u = parkAlternative(navajoName,emailNavajo, urlTemplate);
				
			} else {
				u = parkMultipart(navajoName,emailNavajo, urlTemplate);
			}
		}
		return u;
	}
	
	private String parkAlternative(String navajoName, Navajo emailNavajo, String urlTemplate) throws IOException {
		Message parts = emailNavajo.getMessage("Mail/Parts");
		int lastPartIndex = parts.getAllMessages().size()-1;
		return "{navajo:/"+navajoName+":/Mail/Parts@"+lastPartIndex+"/Content}";
	}

	
	private String parkSingle(String navajoName, Navajo emailNavajo, String urlTemplate) throws IOException {
		return parkPart(navajoName,urlTemplate);
	}

	private String parkPart(String navajoName, String urlTemplate) throws IOException, MalformedURLException {
		return "{navajo:/"+navajoName+":/Mail/Content}";
	}

	private String parkMultipart(String navajoName, com.dexels.navajo.document.Navajo pp, String urlTemplate) throws IOException {
		Message parts = pp.getMessage("Mail/Parts");
		Property contentProperty = pp.getProperty("Mail/Parts@0/Content");
		Binary body = (Binary) parts.getAllMessages().get(0).getProperty("Content").getTypedValue();
		// loop other parts:
		String bodyText = new String(body.getData());
		String replaced = replaceAttributes("src", bodyText,urlTemplate);
		Binary b = new Binary(replaced.getBytes());
		contentProperty.setAnyValue(b);
		return "{navajo:/"+navajoName+":/Mail/Parts@0/Content}";
	}

	public String replaceAttributes(String attributeName, String htmlString, String templateUrl) {
		Pattern patt = Pattern.compile(attributeName + "=\"([^<]*)\"");
		Matcher m = patt.matcher(htmlString);
		StringBuffer sb = new StringBuffer(htmlString.length());
		while (m.find()) {
			String text = m.group(1);
			// ... possibly process 'text' ...
			m.appendReplacement(sb, Matcher.quoteReplacement(attributeName + "=\"" + createUrl(templateUrl,text) + "\""));
		}
		m.appendTail(sb);
		return sb.toString();
	}




	private String createUrl(String templateUrl, String text) {
		return templateUrl.replaceAll("$$", text);
	}
}
