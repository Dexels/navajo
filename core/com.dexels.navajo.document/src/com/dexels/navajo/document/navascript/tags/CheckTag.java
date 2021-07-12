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
	NS3Compatible parent;

	public NS3Compatible getParentTag() {
		return parent;
	}

	public void addParent(NS3Compatible p) {
		parent = p;

	}
	
	public CheckTag(Navajo n, String code, String description, String condition) {
		super(n, code, description, null, condition);
	}

	public CheckTag(Navajo n) {
		super(n);
	}

	public void setRule(String r) {
		super.setRule(r);
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append(NS3Utils.formatConditional(getCondition()));
		sb.append(NS3Keywords.CHECK + " " + NS3Constants.PARAMETERS_START + NS3Keywords.CHECK_CODE + "=\"" + getCode() + "\"");
		if ( getDescription() != null ) {
			String c = "," + NS3Keywords.CHECK_DESCRIPTION + "=" + getDescription();
			sb.append(c);
		}
		sb.append((NS3Constants.PARAMETERS_END + " =\n"));
		String rule = getRule();
		rule = rule.replaceAll("&gt;", ">");
		rule = rule.replaceAll("&lt;", "<");
		sb.append((NS3Utils.generateIndent(indent+1) + rule + NS3Constants.EOL_DELIMITER + "\n"));
		w.write(sb.toString().getBytes());
	}

	@Override
	public void addComment(CommentBlock cb) {
		cb.addParent(this);
	}

}
