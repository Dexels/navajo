package com.dexels.navajo.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class TokenizerMap implements Mappable{

	public String delimiter;
	public String value;
  public Token[] tokens;
  
	public Token[] getTokens(){
		StringTokenizer tok = new StringTokenizer(value, delimiter);
		List<Token> toks = new ArrayList<Token>();
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
		
	}

	public void load(Access access) throws MappableException, UserException {
		
	}

	public void store() throws MappableException, UserException {
		
	}
	
	public void setDelimiter(String d){
		delimiter = d;
	}
	
	public void setValue(String v){
		value = v;
	}

}
