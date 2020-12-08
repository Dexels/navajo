/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.functions.mail.impl.BinaryDataSource;

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
		Binary b = (Binary) getBinaryOperand(0);
		DataSource ds = new BinaryDataSource(b);
		try {
			MimeMultipart mmp = new MimeMultipart(ds);
			return mmp.getCount();
		} catch (MessagingException e) {
			logger.error("Can not parse as multipart, assuming single part. ");
			return 1;
		}

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
		gp.insertBinaryOperand(b);
		Integer result = (Integer) gp.evaluate();
		logger.info("Parts: "+result);

	}
	

}
