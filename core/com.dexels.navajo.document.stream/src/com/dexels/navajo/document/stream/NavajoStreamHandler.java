package com.dexels.navajo.document.stream;

import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.stream.api.Method;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;

public interface NavajoStreamHandler {
	public void messageDone(Map<String,String> attributes, List<Prop> properties);
	public void messageStarted(Map<String,String> attributes);
	public void messageDefinitionStarted(Map<String,String> attributes);
	public void messageDefinition(Map<String,String> attributes, List<Prop> properties);
	public void arrayStarted(Map<String,String> attributes);
	public void arrayElementStarted();
	public void arrayElement(List<Prop> properties);
	public void arrayDone(String name);
	public void navajoStart(NavajoHead head);
	public void navajoDone(List<Method> methods);
	public void binaryStarted(String name);
	public void binaryContent(String name);
	public void binaryDone();
}
