package com.dexels.navajo.testmap;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class OuterMap implements Mappable {
	private InnerMap[] inner;

	public InnerMap[] getInner() {
		return inner;
	}

	public void setInner(InnerMap[] inner) {
		this.inner = inner;
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		InnerMap i1 = new InnerMap();
		i1.setSomeName("Aap");
		InnerMap i2 = new InnerMap();
		i2.setSomeName("Noot");
		InnerMap i3 = new InnerMap();
		i3.setSomeName("Mies");

		inner = new InnerMap[] { i1, i2, i3 };
	}

	@Override
	public void store() throws MappableException, UserException {
	}

	@Override
	public void kill() {
	}
}
