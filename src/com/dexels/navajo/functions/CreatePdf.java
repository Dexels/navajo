package com.dexels.navajo.functions;

import java.io.*;

import javax.swing.text.html.HTMLDocument.*;

import org.xml.sax.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.parser.*;
import com.lowagie.text.*;
import com.lowagie.text.html.*;
import com.lowagie.text.pdf.*;

public class CreatePdf extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 1) {
			throw new TMLExpressionException("CreatePdf needs ONE parameter");
		}
		Object oo = getOperand(0);
		if (oo != null && oo instanceof String) {
			String s = (String) oo;
			try {
				return convertHtml(s);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new TMLExpressionException("Problem evaluating CreatePdf function: ",e);
			} catch (DocumentException e) {
				throw new TMLExpressionException("Problem evaluating CreatePdf function: ",e);
			} catch (IOException e) {
				throw new TMLExpressionException("Problem evaluating CreatePdf function: ",e);
			}
		}
		if (oo != null && oo instanceof Binary) {
			Binary b = (Binary)oo;
			String res = new String(b.getData());
			System.err.println("Converting: "+res);
			try {
				return convertHtml(res);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new TMLExpressionException("Problem evaluating CreatePdf function: ",e);
			} catch (DocumentException e) {
				throw new TMLExpressionException("Problem evaluating CreatePdf function: ",e);
			} catch (IOException e) {
				throw new TMLExpressionException("Problem evaluating CreatePdf function: ",e);
			}
		}
		throw new TMLExpressionException("CreatePdf needs ONE string|binary parameter.");
	}

	@Override
	public String remarks() {
		return "Converts a html coded string to a pdf file.";
	}

	@Override
	public String usage() {
		return "CreatePdf(String, html encoded) -> Binary";
	}

	/**
	 * @param args
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	public static void oldmain(String[] args) throws DocumentException, IOException {
		FileInputStream fis = new FileInputStream("test.txt");
		
		FileReader fr = new FileReader("test.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		StringBuffer sb = new StringBuffer();
		do {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		} while (line != null);

		
		String inputString = sb.toString();
		StripBody sp = new StripBody();
//		inputString = sp.stripBody(inputString);
		
//		inputString = sp.tidyString(inputString);
		
		FileWriter fw = new FileWriter("copytest.xml");
		fw.write(inputString);
		fw.flush();
		fw.close();
//		inputString = sp.stripBody(inputString);
		System.err.println(inputString);
//		convertHtml(inputString);
	}
	public static void main(String[] args) throws DocumentException, IOException {
		FileOutputStream fos = new FileOutputStream("test.pdf");
		Document dd = new Document();
		PdfWriter.getInstance(dd, fos);
		dd.open();
		HtmlParser.parse(dd, new InputSource(new StringReader("<html><head><title>W</title></head><body><table></table><p>H</p></body></html>")));
		fos.flush();
		fos.close();
	}
	
	private Binary convertHtml(String input) throws FileNotFoundException, DocumentException, IOException {
		Binary b = new Binary();
		Document dd = new Document();
		OutputStream os = b.getOutputStream();
		PdfWriter.getInstance(dd, os);
		dd.open();
		HtmlParser.parse(dd, new InputSource(new StringReader(input)));
		os.flush();
		os.close();
		
		return b;
	}

}
