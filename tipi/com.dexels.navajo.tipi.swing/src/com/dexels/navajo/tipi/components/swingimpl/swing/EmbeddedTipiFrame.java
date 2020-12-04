/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JInternalFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedTipiFrame extends JInternalFrame {

	private static final long serialVersionUID = -3251472052408327306L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(EmbeddedTipiFrame.class);
	
	@Override
	public void setVisible(boolean arg0) {
		logger.debug("setVisible: " + arg0);
		super.setVisible(true);
	}

}
