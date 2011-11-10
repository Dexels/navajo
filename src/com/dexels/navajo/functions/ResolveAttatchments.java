package com.dexels.navajo.functions;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


public class ResolveAttatchments extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 1) {
			throw new TMLExpressionException("ResolveAttatchments needs (String, String) parameter");
		}
		Object oo = getOperand(0);
		Object pp = getOperand(1);
		String text = (String)oo;
		String expression = (String)pp;
		resolve(text,expression);
		throw new TMLExpressionException("StripProlog needs ONE string parameter.");
	}

	private void resolve(String text, String expression) {
		// TODO Auto-generated method stub
		
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




	
	
	public  String renderHTML(String result,String expression) {

		DocumentBuilderFactory d = DocumentBuilderFactory.newInstance();
		DocumentBuilder dd;
		Document doc = null;
		try {
			dd = d.newDocumentBuilder();
			doc = dd.parse(new ByteArrayInputStream(result.getBytes()), "aap ");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (doc == null) {
//			System.err.println("Oh dear!");
			return result;
		}
		
		fixImageTags(doc, new ArrayList<Element>(), expression);

		StringWriter sw = new StringWriter();
		try {
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(sw));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// String retidy2 = convertToXHTML(sw.toString());

		try {
			FileWriter fw = new FileWriter("/tmp/4_image_tags_fixed");
			fw.write(sw.toString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

	
	public  void fixImageTags(Node n, List<Element> imageTagList, String expression) {
		if (n instanceof Document) {
			NodeList v = n.getChildNodes();
			for (int i = 0; i < v.getLength(); i++) {
				Node element = v.item(i);
				fixImageTags(element, imageTagList, expression);
			}
		}
		if (n instanceof Element) {
			Element x = (Element) n;
			if (x.getTagName().equals("img")) {
				x.setAttribute("src", convertImgSrc(imageTagList.size(), expression));
				imageTagList.add(x);
			}
			NodeList v = x.getChildNodes();
			for (int i = 0; i < v.getLength(); i++) {
				Node element = v.item(i);
				fixImageTags(element, imageTagList,expression);
			}
		}

	}
	
	public  String convertImgSrc(int index, String expression) {
		return  "?index=" + (index + 1) + "&amp;expression=" + expression ;
	}


	@Override
	public String remarks() {
		return "Strips the junk before the <html> tag and after the </html> tag";
	}

	@Override
	public String usage() {
		return "StripProlog('troeptrop<html>aap</html> troeptroep') = <html>aap</html>";
	}
	

	public static void main(String[] args) throws IOException, TMLExpressionException {

	}

}
