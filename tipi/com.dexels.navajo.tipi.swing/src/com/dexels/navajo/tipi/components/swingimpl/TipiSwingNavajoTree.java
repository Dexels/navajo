/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.JTree;

public class TipiSwingNavajoTree extends JTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7879989320974319712L;
	private String selectedPath = null;

	public String getSelectedPath() {
		return selectedPath;
	}

	public void setSelectedPath(String selectedPath) {
		this.selectedPath = selectedPath;
	}

}
