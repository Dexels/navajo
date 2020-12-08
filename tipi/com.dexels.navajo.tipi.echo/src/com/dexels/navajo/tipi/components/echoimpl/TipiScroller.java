/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Extent;
import echopointng.ContainerEx;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiScroller extends TipiPanel {
	private static final long serialVersionUID = -4520969021281014402L;

	public TipiScroller() {
	}

	public void setComponentValue(final String name, final Object object) {

		if ("w".equals(name)) {
				ContainerEx cont = (ContainerEx) getContainer();
			cont.setWidth(new Extent(((Integer) object).intValue(), Extent.PX));
		}
		if ("h".equals(name)) {
			ContainerEx cont = (ContainerEx) getContainer();
			cont
					.setHeight(new Extent(((Integer) object).intValue(),
							Extent.PX));
		}
		super.setComponentValue(name, object);
	}

}
