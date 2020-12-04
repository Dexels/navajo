/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class Token implements Mappable {
  public String value;
	
	@Override
	public void kill() {

	}

	@Override
	public void load(Access access) throws MappableException, UserException {

	}

	@Override
	public void store() throws MappableException, UserException {

	}
	
	public String getValue(){
		return value;
	}

}
