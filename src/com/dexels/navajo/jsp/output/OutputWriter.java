package com.dexels.navajo.jsp.output;

import java.io.IOException;
import java.io.Writer;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public interface OutputWriter {
	public void writeProperty(Property p,Writer w) throws IOException;
	public void writePropertyValue(Property p, Writer w) throws IOException;
	public void writeMessage(Message m,Writer w) throws IOException;
	public void writeTable(Message m,Writer w) throws IOException;
	public void writeMethod(String method,Writer w) throws IOException;
	public void writeService(Navajo service,Writer w) throws IOException;
	public void writeTableRow(Message m, Writer w) throws IOException;
	public void writePropertyDescription(Property p, Writer w) throws IOException;
		

	}
