package com.dexels.navajo.document.navascript.tags;

import java.io.OutputStream;

public class LogTag extends DebugTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2307477286291340051L;

	public LogTag(NavascriptTag n, String value) {
		super(n, value);
	}
	
	public LogTag(NavascriptTag n) {
		super(n);
	}
	
	@Override
	public void formatNS3(int indent, OutputStream w) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append(NS3Utils.formatConditional(getCondition()));
		sb.append(NS3Keywords.LOG);
		sb.append("(");
		sb.append(value);
		sb.append(")");
		sb.append(NS3Constants.EOL_DELIMITER + "\n\n");
		w.write(sb.toString().getBytes());
	}

	@Override
	public String getTagName() {
		return Tags.LOG;
	}
}
