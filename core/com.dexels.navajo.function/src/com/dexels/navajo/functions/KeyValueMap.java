package com.dexels.navajo.functions;

import java.util.StringTokenizer;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class KeyValueMap extends FunctionInterface {

	@Override
	public String remarks() {
		return "This function returns a value from a serialized key/value map, e.g. KeyValueMap('key1=value1,key2=value2', ',', 'key2') = 'value2'";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		if ( getOperands().size() != 3 ) {
			throw new TMLExpressionException("KeyValueMap(): invalid number of operands. Usage: " + usage());
		}
		
		Object a = this.getOperands().get(0);
		Object b = this.getOperands().get(1);
		Object c = this.getOperands().get(2);
		
		if ( ! ((a instanceof String) && (b instanceof String) && ( c instanceof String) ) ) {
			throw new TMLExpressionException("KeyValueMap(): invalid operand. Usage: " + usage());
		}
		
		String map = (String) a;
		String sep = (String) b;
		String key = (String) c;
		
		StringTokenizer tokens = new StringTokenizer(map, sep);
		
		while ( tokens.hasMoreElements() ) {
			
			String keyValue = tokens.nextToken();
			if ( keyValue.indexOf("=") != -1 ) {
				String k = keyValue.split("=")[0];
				if ( k.equals(key) ) {
					return keyValue.split("=")[1];
				}
			}
		}
		
		return null;
	}
	
	public static void main(String [] args) throws Exception {
		
		KeyValueMap kvm = new KeyValueMap();
		kvm.reset();
		kvm.insertOperand(new String("personid=CHGP12Y;statuscode=21;organizationid=BBFW06E"));
		kvm.insertOperand(new String(";"));
		kvm.insertOperand(new String("organizationid"));
		
		Object o = kvm.evaluate();
		
		System.err.println("o = " + o);
	}

}
