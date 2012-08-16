package com.dexels.navajo.tipi.vaadin.functions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;

public class InjectMailNavajo extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 3) {
			throw new TMLExpressionException("InjectMailNavajo needs three parameters");
		}
		Object oo = getOperand(0);
		if(!(oo instanceof VaadinTipiContext)) {
			throw new TMLExpressionException("InjectMailNavajo: param one should be an EchoTipiContext");
		}
		VaadinTipiContext ee = (VaadinTipiContext)getOperand(0);
//		String expression = (String)getOperand(1);

		
		Navajo input = (Navajo) getOperand(1) ;//("navajo", event);
		Navajo inputCopy = input.copy();
		
		
//		String urlTemplate = (String)getOperand(2); // getEvaluatedParameterValue("urlTemplate", event);
		String navajoName = (String) getOperand(2); //getEvaluatedParameterValue("navajoName", event);
		
		//String externalUrlTemplate = ee.createExpressionUrl(urlTemplate);
		try {
			String result = createNavajoUrl(ee,navajoName, inputCopy);
			ee.injectNavajo(navajoName, inputCopy);
			return result;
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String remarks() {
		return null;
	}

	@Override
	public String usage() {
		return(" CreateEchoMailUrl(context,navajo, object) returns a string pointing to the object.\nContext will usually be {context:/} ");
	}
	

	
	private String createNavajoUrl(VaadinTipiContext ee, String navajoName, Navajo emailNavajo)  {
		String u;
		Message parts = emailNavajo.getMessage("Mail/Parts");
		if (parts == null) {
			u = parkSingle(navajoName);
		} else {
			String contentType = (String) emailNavajo.getProperty("Mail/ContentType").getTypedValue();
			if(contentType.startsWith("multipart/alternative")) {
				u = parkAlternative(navajoName,emailNavajo);
				
			} else {
				u = parkMultipart(ee,navajoName,emailNavajo);
			}
		}
		return u;
	}
	
	private String parkAlternative(String navajoName, Navajo emailNavajo) {
		Message parts = emailNavajo.getMessage("Mail/Parts");
		int lastPartIndex = parts.getAllMessages().size()-1;
		return "{property:/"+navajoName+":/Mail/Parts@"+lastPartIndex+"/Content}";
	}

	
	private String parkSingle(String navajoName) {
		return parkPart(navajoName);
	}

	private String parkPart(String navajoName) {
		return "{property:/"+navajoName+":/Mail/Content}";
	}

	private String parkMultipart(VaadinTipiContext ee,String navajoName, com.dexels.navajo.document.Navajo pp)  {
		Message parts = pp.getMessage("Mail/Parts");
		Property contentProperty = pp.getProperty("Mail/Parts@0/Content");
		Binary body = (Binary) parts.getAllMessages().get(0).getProperty("Content").getTypedValue();
		// loop other parts:
		String bodyText = new String(body.getData());
		String replaced = replaceAttributes(ee,"src",navajoName, bodyText);
		Binary b = new Binary(replaced.getBytes());
		contentProperty.setAnyValue(b);
		return "{property:/"+navajoName+":/Mail/Parts@0/Content}";
	}

	public String replaceAttributes(VaadinTipiContext ee,String attributeName, String navajoName, String htmlString) {
		String expressionTemplate =  "{property:/"+navajoName+":/Mail/Parts@__REPLACE__/Content}";
		
		
		Pattern patt = Pattern.compile(attributeName + "=\"([^<]*)\"");
		Matcher m = patt.matcher(htmlString);

		
		StringBuffer sb = new StringBuffer(htmlString.length());
		while (m.find()) {
			String text = m.group(1);
 			// +1 because TML considers Part 0 to be the body
			int index = getPartIndexOfAttach(text)+1;
			String attach = expressionTemplate.replaceAll("__REPLACE__", ""+index);
			
			m.appendReplacement(sb, Matcher.quoteReplacement(attributeName + "=\"" + ee.createExpressionUrl(attach) + "\""));
		}
		m.appendTail(sb);
		return sb.toString();
	}



	
	private int getPartIndexOfAttach(String attach) {
		try {
			String[] sp = attach.split("-");
			int index = Integer.parseInt(sp[sp.length-1]);
			return index;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// guess 1
		return 1;
	}
	
	public static void main(String[] args) throws NavajoException {
		String attachExample = "cid:attach-nr-3";
		String[] sp = attachExample.split("-");
		int index = Integer.parseInt(sp[sp.length-1]);
		System.err.println("Index: "+index);
	}
}
