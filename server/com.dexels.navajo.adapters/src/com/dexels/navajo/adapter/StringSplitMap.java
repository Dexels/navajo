/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class StringSplitMap implements Mappable{

	public String delimiter;
	public String value;
    public Token[] tokens;
  
	public Token[] getTokens(){
		if (value != null)
		{
			List<Token> toks = new ArrayList<>();
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
