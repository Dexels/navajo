package com.dexels.navajo.tipi.components.core;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
public class TipiHeadlessFrame extends TipiDataComponentImpl {

	private static final long serialVersionUID = 8708472273371762323L;

	public TipiHeadlessFrame() {
	}

	public Object createContainer() {
		return null;
	}

	@Override
	public void load(XMLElement definition, XMLElement instance,
			TipiContext context) throws TipiException {
		super.load(definition, instance, context);
	}

}
