package com.dexels.navajo.tipi.mail.functions;

import java.io.InputStream;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.mail.impl.BinaryDataSource;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class PartCount extends FunctionInterface {

	public PartCount() {	
//		super(new Class[][]{ {Float.class,Integer.class, null} });
//		setReturnType(new Class[]{String.class});
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Returns the number of mime parts ";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		Binary b = (Binary) getOperand(0);
		DataSource ds = new BinaryDataSource(b);
		try {
			MimeMultipart mmp = new MimeMultipart(ds);
			return mmp.getCount();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static void main(String [] args) throws Exception {
		InputStream is = PartCount.class.getResourceAsStream("testdata.xml");
		Navajo nn = NavajoFactory.getInstance().createNavajo(is);
		is.close();
		nn.write(System.err);
		Binary b = (Binary) nn.getProperty("MailBox/Mail@0/Content").getTypedValue();
		System.err.println("Length: "+b.getLength()+" type: "+b.guessContentType());
		PartCount gp = new PartCount();
		gp.reset();
		gp.insertOperand(b);
		Integer result = (Integer) gp.evaluate();
		System.err.println("Parts: "+result);

	}
	

}
