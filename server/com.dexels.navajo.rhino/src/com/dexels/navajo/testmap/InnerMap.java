package com.dexels.navajo.testmap;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class InnerMap implements Mappable {
	public String someName;

	public String getSomeName() {
		return someName;
	}

	public void setSomeName(String name) {
		this.someName = name;
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

}
