package com.dexels.navajo.document.navascript.tags;

import java.io.OutputStream;

public interface NS3Compatible {
	
	public void formatNS3(int indent, OutputStream w) throws Exception;
	
	public void addComment(CommentBlock cb);

	public String getTagName();
	
			
}
