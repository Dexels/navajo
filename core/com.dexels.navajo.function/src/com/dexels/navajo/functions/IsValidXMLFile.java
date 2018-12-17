package com.dexels.navajo.functions;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class IsValidXMLFile extends FunctionInterface {
    private final static Logger logger = LoggerFactory.getLogger(IsValidXMLFile.class);

	@Override
	public Object evaluate() throws TMLExpressionException {

		if (getOperands().size() != 1) {
			throw new TMLExpressionException(this, "One operand expected. ");
		}

		Object o = getOperand(0);
		if (!( o instanceof Binary )) {
			return Boolean.FALSE;
		}
		
		Binary b = (Binary) getOperand(0);
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);

			DocumentBuilder builder = factory.newDocumentBuilder();

			// the "parse" method also validates XML, will throw an exception if misformatted
			@SuppressWarnings("unused")
			Document document = builder.parse(new InputSource( b.getDataAsStream() ));

			return Boolean.TRUE;
		} catch (Throwable e) {
		    logger.info("Invalid XML file", e);
			return Boolean.FALSE;
		}
	}

	@Override
	public String remarks() {
		return "IsValidXMLFile(binary), returns true or false";
	}

	public static void main(String [] args) throws Exception {
	
//		FileInputStream fs = new FileInputStream((new File("c:/users/erik/appdata/local/temp/_aaacamt.xml")));
		FileInputStream fs = new FileInputStream((new File("/home/chris/camt.xml")));
		Binary b = new Binary(fs);
		fs.close();
		
		IsValidXMLFile i = new IsValidXMLFile();
		i.reset();
		i.insertBinaryOperand(b);
		Object o = i.evaluate();
	
		System.err.println("result valid xml file = " + o);
		
		
	}

}
