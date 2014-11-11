package com.dexels.navajo.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.dexels.navajo.server.Access;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.UserException;

public class StringSplitMap implements Mappable{

	public String delimiter;
	public String value;
    public Token[] tokens;
  
	public Token[] getTokens(){
		if (value != null)
		{
			List<Token> toks = new ArrayList<Token>();
			String[] result = value.split(delimiter);
			for(int i = 0; i < result.length; i++){
				Token t = new Token();
				t.value = result[i];
				toks.add(t);
			}
			tokens = new Token[toks.size()];
			tokens = toks.toArray(tokens);
		}
		else
		{
			tokens = null;
		}
		return tokens;		
	}
	
	@Override
	public void kill() {
		
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		
	}

	@Override
	public void store() throws MappableException, UserException {
		
	}
	
	public void setDelimiter(String d){
		delimiter = d;
	}
	
	public void setValue(String v){
		value = v;
	}

}
