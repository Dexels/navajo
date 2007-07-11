package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiPackageReference {

	private String id = null;
	private final TipiContext context;

	public TipiPackageReference(TipiContext tc, XMLElement xx) {
		context = tc;
		load(xx);
	}

	public TipiPackageReference(TipiContext tc, String id) {
		context = tc;
		this.id = id;
	}

	public void load(XMLElement xe) {
		id = xe.getStringAttribute("id");
	}

	public void storeTo(XMLElement xe) {
		XMLElement x = new CaseSensitiveXMLElement();
		x.setName("tipi-package");
		x.setAttribute("id", id);
		xe.addChild(x);
	}

	public String getId() {
		return id;
	}

}
