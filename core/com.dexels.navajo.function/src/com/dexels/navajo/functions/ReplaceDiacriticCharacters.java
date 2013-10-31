package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class ReplaceDiacriticCharacters extends FunctionInterface {

	@Override
	public final Object evaluate()
			throws com.dexels.navajo.parser.TMLExpressionException {

		final Object op = this.getOperands().get(0);

		if (op == null) {
			return ("");
		}

		if (!(op instanceof java.lang.String)) {
			throw new TMLExpressionException(this, "String argument expected");
		}

		String s = (String) op;

		s = s.replaceAll("[àáâãäåāăąæ]","a");
		s = s.replaceAll("[ÀÁÂÃÄÅĀĂĄÆ]","A");
		s = s.replaceAll("[ß]","b");
		s = s.replaceAll("[ẞ]","B");
		s = s.replaceAll("[çćĉċč]","c");
		s = s.replaceAll("[ÇĆĈĊČ]","C");
		s = s.replaceAll("[ďđ]","d");
		s = s.replaceAll("[ĎĐ]","D");
		s = s.replaceAll("[èéêëēĕėęě]","e");
		s = s.replaceAll("[ÈÉÊËĒĔĖĘĚ]","E");
		s = s.replaceAll("[ĝğġģ]","g");
		s = s.replaceAll("[ĜĞĠĢ]","G");
		s = s.replaceAll("[ĥħ]","h");
		s = s.replaceAll("[ĤĦ]","H");
		s = s.replaceAll("[ìíîïĩīĭıįĳ]","i");
		s = s.replaceAll("[ÌÍÎÏĨĪĬİĮĲ]","I");
		s = s.replaceAll("[ĵ]","j");
		s = s.replaceAll("[Ĵ]","J");
		s = s.replaceAll("[ķĸ]","k");
		s = s.replaceAll("[Ķ]","K");
		s = s.replaceAll("[ĺļľŀł]","l");
		s = s.replaceAll("[ĹĻĽĿŁ]","L");
		s = s.replaceAll("[ñńņňŋ]","n");
		s = s.replaceAll("[ÑŃŅŇŊ]","N");
		s = s.replaceAll("[òóôöõøōŏőœ]","o");
		s = s.replaceAll("[ÒÓÔÖÕØŌŎŐŒ]","O");
		s = s.replaceAll("[ŕŗř]","r");
		s = s.replaceAll("[ŔŖŘ]","R");
		s = s.replaceAll("[śŝşš]","s");
		s = s.replaceAll("[ŚŜŞŠ]","S");
		s = s.replaceAll("[ţťŧ]","t");
		s = s.replaceAll("[ŢŤŦ]","T");
		s = s.replaceAll("[ùúûüũůūŭűų]","u");
		s = s.replaceAll("[ÙÚÛÜŨŮŪŬŰŲ]","U");
		s = s.replaceAll("[ŵ]","w");
		s = s.replaceAll("[Ŵ]","W");
		s = s.replaceAll("[ýÿŷ]","y");
		s = s.replaceAll("[ÝŸŶ]","Y");
		s = s.replaceAll("[źżž]","z");
		s = s.replaceAll("[ŹŻŽ]","Z");
		
		return s;
	}
	
	public static void main(String[] args) throws TMLExpressionException {
		ReplaceDiacriticCharacters r = new ReplaceDiacriticCharacters();
		r.reset();
		r.insertOperand("àáõøōâãäĪĬåÄÅÎÏĨĪĬĀÝŸāăą");
		String res = (String) r.evaluate();
		System.err.println(">" + res + "<");
	}

	@Override
	public String remarks() {
		// TODO Auto-generated method stub
		return null;
	}
}
