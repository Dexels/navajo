/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseCheckTagImpl;

public class CheckTag extends BaseCheckTagImpl implements NS3Compatible {

	private static final long serialVersionUID = 3708858001793910176L;

	public CheckTag(Navajo n, String code, String description, String condition) {
		super(n, code, description, null, condition);
	}
	
	public void setRule(String r) {
		super.setRule(r);
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		String r = NS3Utils.generateIndent(indent) + NS3Keywords.CHECK + " " + NS3Constants.PARAMETERS_START + NS3Keywords.CHECK_CODE + ":" + getCode();
		w.write(r.getBytes());
		if ( getDescription() != null ) {
			String c = "," + NS3Keywords.CHECK_DESCRIPTION + "=" + getDescription();
			w.write(c.getBytes());
		}
		if ( getCondition() != null ) {
			String c = "," + NS3Keywords.CHECK_CONDITION + "=" + getCondition();
			w.write(c.getBytes());
		}
		w.write((NS3Constants.PARAMETERS_END + " =\n").getBytes());
		w.write((NS3Utils.generateIndent(indent+1) + getRule() + NS3Constants.EOL_DELIMITER + "\n").getBytes());
	}

}
