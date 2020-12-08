/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiEditorPane;


@Deprecated
public class TipiBrowser extends TipiSwingComponentImpl {
	private static final long serialVersionUID = -7908348920868634250L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBrowser.class);
	private TipiEditorPane myItem;

	@Override
	public Object createContainer() {
		myItem = new TipiEditorPane();
		myItem.setEditable(false);
		return myItem;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("binary")) {
			logger.debug("Setting to binary: " + object.toString());
			try {
				myItem.setBinary((Binary) object);
			} catch (IOException e) {
				logger.error("Error detected",e);
			}
		}
		if (name.equals("url")) {
			try {
				myItem.setPage((String) object);
			} catch (IOException e) {
				logger.error("Error detected",e);
			}
		}
		super.setComponentValue(name, object);

	}

}
