package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.base.BaseNode;

public class CommentBlock extends BaseNode implements NS3Compatible {

	String comment;
	
	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		comment = comment.replaceAll("!-", "").replaceAll("--", "");
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		if ( comment != null && comment.indexOf("\n") == -1 ) {
			sb.append("//");
			sb.append(comment);
			sb.append("\n");
		} else if ( comment != null ) {
			sb.append("/*");
			comment = comment.replaceAll("\n", "\n" + NS3Utils.generateIndent(indent) + "   ");
			sb.append(comment);
			sb.append("*/\n");
		}
		w.write(sb.toString().getBytes());
	}

	@Override
	public Map<String, String> getAttributes() {
		return null;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	
	@Override
	public String getTagName() {
		return null;
	}
	
	@Override
	public void printElement(final Writer sw, int indent) throws IOException {
		// nop
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public void addComment(CommentBlock cb) {
		
	}

}
