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
		sb.append(NS3Keywords.BREAK + NS3Constants.PARAMETERS_START);
		int index = 0;
		if ( getCondition() != null ) {
			sb.append(NS3Keywords.BREAK_CONDITION+"="+getCondition());
			index++;
		}
		if ( getConditionId() != null ) {
			if ( index > 0 ) sb.append(",");
			sb.append(NS3Keywords.BREAK_CONDITIONID+"="+getConditionId());
			index++;
		}
		if ( getConditionDescription() != null ) {
			if ( index > 0 ) sb.append(",");
			sb.append(NS3Keywords.BREAK_CONDITIONDESCRIPTION+"="+getConditionDescription());
			index++;
		}
		sb.append(NS3Constants.PARAMETERS_END);
		sb.append(NS3Constants.EOL_DELIMITER + "\n");
		w.write(sb.toString().getBytes());
	}

}
