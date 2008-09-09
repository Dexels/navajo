package com.dexels.navajo.functions;

import java.io.*;

import org.w3c.tidy.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.*;

public class StripProlog extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 1) {
			throw new TMLExpressionException("StripProlog needs ONE parameter");
		}
		Object oo = getOperand(0);
		if (oo==null) {
			throw new TMLExpressionException("StripProlog problem: Parameter null");
		}
		if (oo != null && oo instanceof String) {
			String s = (String) oo;
			return stripProlog(s);
		}
		if (oo != null && oo instanceof Binary) {
			Binary b = (Binary)oo;
			String s = new String(b.getData());
			return stripProlog(s);
		}
		throw new TMLExpressionException("StripProlog needs ONE string parameter.");
	}

	public String stripProlog(String s) throws TMLExpressionException {

		int startIndex = s.indexOf("<html");
		if (startIndex == -1) {
			throw new TMLExpressionException("StripProlog can't find a <html> tag.");
	}
		s = s.substring(startIndex );
		int endIndex = s.indexOf("</html>");
		s = s.substring(0, endIndex+7);
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

	public static void main(String[] args) throws IOException, TMLExpressionException {
		StripProlog sp = new StripProlog();
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

		ss = sp.stripProlog(sb.toString());
		System.err.println("SS: "+ss);
		fr.close();
	}

	@Override
	public String remarks() {
		return "Strips the junk before the <html> tag and after the </html> tag";
	}

	@Override
	public String usage() {
		return "StripProlog('troeptrop<html>aap</html> troeptroep') = <html>aap</html>";
	}
	



}
