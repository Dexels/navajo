package com.dexels.navajo.document.stream;

import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;

public interface NavajoStreamHandler {
	public void messageDone(Map<String,String> attributes, List<Prop> properties);
	public void messageStarted(Map<String,String> attributes);
	public void messageDefinitionStarted(Map<String,String> attributes);
	public void messageDefinition(Map<String,String> attributes, List<Prop> properties);
	public void arrayStarted(Map<String,String> attributes);
	public void arrayElementStarted(Map<String,String> attributes);
	public void arrayElement(Map<String,String> attributes, List<Prop> properties);
	public void arrayDone(String name);
	public void navajoStart(NavajoHead head);
	public void navajoDone();
}
