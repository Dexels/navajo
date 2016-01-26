package com.dexels.navajo.document.stream;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;

public interface NavajoStreamHandler {
	public void messageDone(Message msg, String path);
	public void messageStarted(Message msg, String path);
	public void arrayStarted(Message msg, String path);
	public void arrayElementStarted(Message msg, String path);
	public void arrayElement(Message msg, String path);
	public void arrayDone(Message msg, String path);
	public void header(Header h);
	public void navajoStart();
	public void navajoDone();
}
