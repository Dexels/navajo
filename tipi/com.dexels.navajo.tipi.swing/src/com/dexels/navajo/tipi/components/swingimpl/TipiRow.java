/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Mar 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;

import javax.swing.BoxLayout;

public class TipiRow extends TipiPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5890186122934128136L;

	@Override
	public Object createContainer() {
		Container container = (Container) super.createContainer();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		return container;
	}

}
