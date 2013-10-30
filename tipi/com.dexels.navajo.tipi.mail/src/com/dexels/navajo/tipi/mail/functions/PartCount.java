package com.dexels.navajo.tipi.mail.functions;

import java.io.InputStream;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.mail.impl.BinaryDataSource;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class PartCount extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(PartCount.class);
	public PartCount() {	
	}
	
	@Override
	public String remarks() {
		return "Returns the number of mime parts ";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		Binary b = (Binary) getOperand(0);
		DataSource ds = new BinaryDataSource(b);
		try {
			MimeMultipart mmp = new MimeMultipart(ds);
			return mmp.getCount();
		} catch (MessagingException e) {
			logger.error("Error: ",e);
		}
		return null;

	}
	
	public static void main(String [] args) throws Exception {
		InputStream is = PartCount.class.getResourceAsStream("testdata.xml");
		Navajo nn = NavajoFactory.getInstance().createNavajo(is);
		is.close();
		nn.write(System.err);
		Binary b = (Binary) nn.getProperty("MailBox/Mail@0/Content").getTypedValue();
		logger.info("Length: "+b.getLength()+" type: "+b.guessContentType());
		PartCount gp = new PartCount();
		gp.reset();
		gp.insertOperand(b);
		Integer result = (Integer) gp.evaluate();
		logger.info("Parts: "+result);

	}
	

}
