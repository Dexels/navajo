/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingx;

import org.jdesktop.swingx.*;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiJXTaskItem extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = -8283604943121523660L;

	@Override
	public Object createContainer() {
		JXTaskPane p = new JXTaskPane();
		p.setTitle("Unspecified title");
		return p;
	}
}
