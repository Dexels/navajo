package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseBreakTagImpl;

public class BreakTag extends BaseBreakTagImpl implements NS3Compatible {

	private static final long serialVersionUID = 3907539607911418826L;

	public BreakTag(Navajo n, String condition, String id, String description) {
		super(n, condition, id, description);
	}

	public BreakTag(Navajo n) {
		super(n);
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append(NS3Utils.formatConditional(getCondition()));
		
		AttributeAssignments aa = new AttributeAssignments();
		aa.add(NS3Keywords.BREAK_CONDITIONID, getConditionId())
		  .add(NS3Keywords.BREAK_CONDITIONDESCRIPTION, getConditionDescription())
		  .add(NS3Keywords.BREAK_ERROR, getError());
		
		sb.append(NS3Keywords.BREAK);
		sb.append(aa.format(true));
		
		sb.append(NS3Constants.EOL_DELIMITER + "\n");
		w.write(sb.toString().getBytes());
	}

	@Override
	public void addComment(CommentBlock cb) {
		
	}

}
