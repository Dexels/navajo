/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseExpressionTagImpl;

public class ExpressionTag extends BaseExpressionTagImpl implements NS3Compatible {

	private ValueTag myValueTag;
	
	public ExpressionTag(Navajo n) {
		super(n);
	}

	public ExpressionTag(Navajo n, String condition, String value) {
		super(n, condition, value);
	}

	public void addValueTag(ValueTag vt) {
		myValueTag = vt;
	}
	
	public ValueTag getValueTag() {
		return myValueTag;
	}
	
	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		if ( getCondition() != null && !"".equals(getCondition()) ) {
			String condition = getCondition();
			if ( condition == null && getValueTag() != null && getValueTag().getCondition() != null ) {
				condition = getValueTag().getCondition();
			}
			sb.append(NS3Utils.formatConditional(condition));
		} 
		if ( getConstant() != null ) {
			sb.append(NS3Utils.formatStringLiteral(getConstant()));
		} else
		if ( getValueTag() != null ) {
			String value = getValueTag().getValue();
			value = value.replaceAll("&gt;", ">");
			value = value.replaceAll("&lt;", "<");
			sb.append(value);
		} else {
			String value = getValue();
			value = value.replaceAll("&gt;", ">");
			value = value.replaceAll("&lt;", "<");
			sb.append(value);
		}
		w.write((NS3Utils.generateIndent(indent) + sb.toString()).getBytes());
	}

	@Override
	public void addComment(CommentBlock cb) {
		
	}
}
