package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseValueImpl;

public class ValueTag extends BaseValueImpl implements NS3Compatible {

	public ValueTag(Navajo n) {
		super(n);
	}
	
	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		
	}

	@Override
	public void addComment(CommentBlock cb) {
		
	}
}
