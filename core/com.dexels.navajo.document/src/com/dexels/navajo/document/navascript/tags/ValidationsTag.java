/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.base.BaseValidationsTagImpl;

public class ValidationsTag extends BaseValidationsTagImpl implements NS3Compatible {

	private Navajo myNavajo;
	
	public ValidationsTag(Navajo n) {
		super(n);
		myNavajo = n;
	}
	
	public CheckTag addCheck(String code, String description, String condition) {
		CheckTag ct = new CheckTag(myNavajo, code, description, condition);
		super.addCheck(ct);
		return ct;
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		String r = NS3Utils.generateIndent(indent) + NS3Keywords.VALIDATIONS + " {\n";
		w.write(r.getBytes());
		for ( BaseNode c : getChildren() ) {
			if ( c instanceof CheckTag ) {
				((CheckTag) c).formatNS3(indent+1, w);
			}
		}
		w.write("}\n\n".getBytes());
	}

}
