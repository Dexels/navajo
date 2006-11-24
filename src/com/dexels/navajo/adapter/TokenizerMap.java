package com.dexels.navajo.adapter;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class TokenizerMap implements Mappable{

	public String delimiter;
	public String value;
  public Token[] tokens;
  
	public Token[] getTokens(){
		StringTokenizer tok = new StringTokenizer(value, delimiter);
		ArrayList toks = new ArrayList();
		while(tok.hasMoreTokens()){
			Token t = new Token();
			t.value = tok.nextToken();
			toks.add(t);
		}
		tokens = new Token[toks.size()];
		tokens = (Token[])toks.toArray(tokens);
		return tokens;		
	}
	
	public void kill() {
		// TODO Auto-generated method stub
		
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}
	
	public void setDelimiter(String d){
		delimiter = d;
	}
	
	public void setValue(String v){
		value = v;
	}

}
