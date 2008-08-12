package com.dexels.navajo.functions;

import java.io.*;

import org.w3c.tidy.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;

public class StripBody extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 1) {
			throw new TMLExpressionException("StripBody needs ONE parameter");
		}
		Object oo = getOperand(0);
		if (oo != null && oo instanceof String) {
			String s = (String) oo;
			return stripBody(s);
		}
		throw new TMLExpressionException("StripBody needs ONE string parameter.");
	}

	public String stripBody(String s) {


		System.err.println("Result: "+s);

		
		s = bodyStrip(s);
		s = tidyString(s);
		s = bodyStrip(s);
		
		System.err.println("final Result: " + s);
		return s;
	}

	public String bodyStrip(String s) {
		int startIndex = s.indexOf("<body");
		if (startIndex != -1) {
			startIndex = s.indexOf(">", startIndex + ("<body".length()));
		}
		s = s.substring(startIndex + 1);
		int endIndex = s.indexOf("</body>");
		s = s.substring(0, endIndex);
		return s;
	}

	public String tidyString(String s) {
		ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
		Tidy t = new Tidy();
		t.setXmlOut(true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		t.parse(bais, baos);
		
		try {
			s = new String(baos.toByteArray(),"UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public static void main(String[] args) throws IOException {
		StripBody sp = new StripBody();
		String ss = "";
		FileReader fr = new FileReader("copytest.xml");
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		StringBuffer sb = new StringBuffer();
		do {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		} while (line != null);

		ss = sp.stripBody(sb.toString());
		ss = sp.tidyString(ss);
		ss = sp.stripBody(ss);
		System.err.println("SS: "+ss);
		fr.close();
	}

	@Override
	public String remarks() {
		return "Strips the part withing <body> tags in a html string. will return everything if no body tags are found";
	}

	@Override
	public String usage() {
		return "StripBody('<body>aap</body>') = aap";
	}

}
