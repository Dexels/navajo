package com.dexels.navajo.tipi.mail.functions;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.mail.impl.BinaryDataSource;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetPart extends FunctionInterface {

	public GetPart() {	
//		super(new Class[][]{ {Float.class,Integer.class, null} });
//		setReturnType(new Class[]{String.class});
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Returns absolute value of a number";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		Binary b = (Binary) getOperand(0);
		int index = (Integer) getOperand(1);
		DataSource ds = new BinaryDataSource(b);
		try {
			MimeMultipart mmp = new MimeMultipart(ds);
			System.err.println("# of bodyparts: "+mmp.getCount());
			BodyPart bp = mmp.getBodyPart(index);
			String type = bp.getContentType();
			Binary result = new Binary( bp.getInputStream());
			result.setMimeType(type);
			return result;
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public static void main(String [] args) throws Exception {
		InputStream is = GetPart.class.getResourceAsStream("testdata.xml");
		Navajo nn = NavajoFactory.getInstance().createNavajo(is);
		is.close();
		nn.write(System.err);
		Binary b = (Binary) nn.getProperty("MailBox/Mail@0/Content").getTypedValue();
		System.err.println("Length: "+b.getLength()+" type: "+b.guessContentType());
		GetPart gp = new GetPart();
		gp.reset();
		gp.insertOperand(b);
		gp.insertOperand(0);
		Binary result = (Binary) gp.evaluate();
		System.err.println("Length of part 0: "+result.getLength());
		
	}
	

}
