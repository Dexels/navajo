/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseIncludeTagImpl;

public class IncludeTag extends BaseIncludeTagImpl implements NS3Compatible {

	NS3Compatible parent;

	public NS3Compatible getParentTag() {
		return parent;
	}

	public void addParent(NS3Compatible p) {
		parent = p;
	}
	
	public IncludeTag(Navajo n,String s) {
		super(n, s);
	}
	
	public IncludeTag(Navajo n) {
		super(n);
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append(NS3Utils.formatConditional(getCondition()));
		sb.append(NS3Keywords.INCLUDE + " \"" + getScript() + "\"" + NS3Constants.EOL_DELIMITER + "\n");
		w.write(sb.toString().getBytes());
	}
	
	@Override
	public void addComment(CommentBlock cb) {
		cb.addParent(this);
	}

}
