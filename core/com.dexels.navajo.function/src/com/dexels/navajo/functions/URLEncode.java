package com.dexels.navajo.functions;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


public class URLEncode extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		if (o == null) {
			return null;
		}
		boolean space_as_plus = false;
		if(getOperands().size() > 1){
			Object b = getOperand(1);
			if(b != null){
				space_as_plus = (Boolean)b;
			}
		}
		if (o instanceof String) {
			try{
				String source = (String) o;				
				String encoded = URLEncoder.encode(source, "UTF-8");
				if(!space_as_plus){
					encoded = encoded.replace("+", "%20");
				}
				return encoded;
			}catch(Exception e){
				throw new TMLExpressionException(this, "Can not parse URL: " + o.getClass().getName());
			}
		} else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
	}

	@Override
	public String remarks() {
		return "Returns URL encoded string";
	}

	@Override
	public String usage() {
		return "URLEncode(s, space_as_plus)";
	}

	public static void main(String [] args) throws Exception {
		URLEncode u = new URLEncode();
		u.reset();
		final String original = "BZ2kTR4xD1Yqrkr0PlHP+3VOpTuzQzF3vzikqTjBLFioMmoofvpE0ykd1UT2tYPtITou59LFX0wECDlTR7xMD+72GyA8cLC3uq3nu/Jwxg8=";
		u.insertOperand(original);
		//u.insertOperand(false);
//		BZ2kTR4xD1Yqrkr0PlHP%2B3VOpTuzQzF3vzikqTjBLFioMmoofvpE0ykd1UT2tYPtITou59LFX0wECDlTR7xMD%2B72GyA8cLC3uq3nu%2FJwxg8%3D
	
		final String out = (String) u.evaluate();
		System.err.println(original);
	String res = URLDecoder.decode(out,"UTF-8");
		System.err.println(res);
	}
}
