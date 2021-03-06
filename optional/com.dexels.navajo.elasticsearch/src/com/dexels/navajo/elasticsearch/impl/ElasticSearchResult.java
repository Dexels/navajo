/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.elasticsearch.impl;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ElasticSearchResult implements Mappable {
	
	public final String id;
	public final String fileName;
	public final String score;
	
	public ElasticSearchResult(String id, String score, String fileName) {
		this.id = id;
		this.fileName = fileName;
		this.score = score;
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
	
	public String getId() {
		return id;
	}
	
	public String getScore() {
		return score;
	}
	public String getFileName() {
		return fileName;
	}

}
