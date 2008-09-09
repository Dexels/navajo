package com.dexels.navajo.functions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.w3c.tidy.Tidy;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class TidyHtml extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 1) {
			throw new TMLExpressionException("TidyHtml needs ONE parameter");
		}
		Object oo = getOperand(0);
		if (oo != null && oo instanceof String) {
			String s = (String) oo;
			return tidyString(s);
		}
		if (oo != null && oo instanceof Binary) {
			Binary s = (Binary) oo;

			String source = new String(s.getData());
			String result = tidyString(source);
			System.err.println("TIDINY: "+source+"\n\n"+result);
			return result;
		}
		throw new TMLExpressionException("TidyHtml needs ONE string parameter.");

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
	
	@Override
	public String remarks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String usage() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
