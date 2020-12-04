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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingGridBagConstraints;

public class TipiColumn extends TipiPanel {

	// <value direction="in" name="enabled" type="boolean" />
	// <value direction="in" name="visible" type="boolean" />
	// <value direction="inout" name="title" type="string" />
	// <value direction="in" name="background" type="color" />
	// <value direction="in" name="foreground" type="color" />
	// <value direction="in" name="border" type="border" />

	/**
	 * 
	 */
	private static final long serialVersionUID = 2978470466062729297L;

	@Override
	public Object createContainer() {
		Container container = (Container) super.createContainer();
		container.setLayout(new GridBagLayout());
		return container;
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		int currentCount = getChildCount();
		super.addToContainer(c, new TipiSwingGridBagConstraints(0,
				currentCount, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}

}
