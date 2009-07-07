package com.dexels.navajo.jsp.output;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

public class NavajoOutputWriter implements OutputWriter {

	public void writeMessage(Message mm, Writer w) throws IOException {
		w.write("<div class='message'>");

		String name = mm.getName();
		if (mm.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) {
			name = name + " (" + mm.getIndex() + ")";
		}
		w.write("MESSAGE TYPE: "+mm.getType());
		if(mm.getType().equals(Message.MSG_TYPE_ARRAY)) {
			
			writeTableHeader(mm, w);
			w.write("<hr/>");
		}
		w.write("<h3>" + name + "</h3>");
		for (Message m : mm.getAllMessages()) {
			writeMessage(m, w);
		}
		for (Property m : mm.getAllProperties()) {
			writeProperty(m, w);
		}
		w.write("</div>");
	}

	public void writeTableRow(Message m, Writer w) throws IOException {
		List<Property> pp = m.getAllProperties();
		w.write("<tr>");
		for (Property property : pp) {
			w.write("<td>");
			if (property.getDirection().equals(Property.DIR_IN)) {
				writeInProperty(property,w);
			} else {
				writeOutProperty(property, w);
			}
			w.write("</td>");
		}
		w.write("</tr>");
	}

	public void writeMethod(String method, Writer w) throws IOException {
		w.write("<div class='method'>");
		w.write("<input type=\"submit\" name=\"command\" value=\"" + method
				+ "\" class=\"method\"> ");
		w.write("</div>");
	}

	public void writePropertyValue(Property p, Writer w) throws IOException {
		if (p.getType().equals(Property.SELECTION_PROPERTY)) {
		} else {
			w.write(p.getValue());
		}
	}
	
	public void writePropertyDescription(Property p, Writer w) throws IOException {
		//w.write("<div class='property_label'>");
		if (p.getDescription() != null && !p.getDescription().equals("")) {
			w.write(p.getDescription());
		} else {
			w.write(p.getName());
		}
		//w.write("</div>");
	}
	
	public void writeProperty(Property p, Writer w) throws IOException {
		writePropertyDescription(p, w);
		if (p.getDirection().equals(Property.DIR_IN)) {
			writeInProperty(p,w);
		} else {
			writeOutProperty(p, w);
		}
	}
	
	private void writeOutProperty(Property p, Writer w) throws IOException {
		if (p.getType().equals(Property.SELECTION_PROPERTY)) {
		} else {
			w.write("<input type='hidden' name='" + getFullPropertyName(p)+ "' value='" + p.getValue() + "'>");
			w.write("</input>");
			w.write(p.getValue());
		}
	}
	
	private void writeInProperty(Property p, Writer w) throws IOException {
		if (p.getType().equals(Property.DATE_PROPERTY)) {
		} else if (p.getType().equals(Property.BOOLEAN_PROPERTY)) {
			boolean b = (Boolean)p.getTypedValue();
			if (b) {
				w.write("<input type='checkbox' name='"+p.getName()+"' CHECKED/>");
			} else {
				w.write("<input type='checkbox' name='"+p.getName()+"'/>");
			}
		} else if (p.getType().equals(Property.SELECTION_PROPERTY)) {
			w.write("<select name='"+p.getName()+"'>");
			try {
				ArrayList<Selection> selections = p.getAllSelections();
				for(Iterator i = selections.iterator(); i.hasNext();) {
					Selection selection = (Selection) i.next();
					if (selection.isSelected()) {
						w.write("<option value='"+selection.getValue()+"' SELECTED>"+selection.getName()+"</option>");
					} else {
						w.write("<option value='"+selection.getValue()+"'>"+selection.getName()+"</option>");
					}
				}
			} catch (NavajoException e) {
				e.printStackTrace();
			}
			w.write("</select>");
		} else {
			String value = p.getValue();
			
			if(value==null) {
				value = "";
			}
			w.write("<input type='"+p.getType()+"' name='" + getFullPropertyName(p)+ "' value='" + value + "'>");
			w.write("</input>");
			System.err.println(w.toString());
		}
	}

	public void writeService(Navajo service, Writer w) throws IOException {
		w.write("<div class='wrap'>");
		w
				.write("<a href='./NavajoTester'><img src='resource/logo-dexels.gif' border='0' alt='Back to the Navajo Tester'/></a>");

		w.write("<h1>" + service.getHeader().getRPCName() + "</h1>");
		try {
			for (Message m : service.getAllMessages()) {
				writeMessage(m, w);
			}
			for (Method m : service.getAllMethods()) {
				writeMethod(m.getName(), w);
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		w.write("</div>");

	}

	public void writeTable(Message mm, Writer w) throws IOException {
		if (!mm.getType().equals(Message.MSG_TYPE_ARRAY)) {
			throw new IllegalArgumentException(
					"Can not call writeTable on a non-array message");
		}
		w.write("<table> ");
		writeTableHeader(mm,w);
		for (Message m : mm.getAllMessages()) {
			writeTableRow(m, w);
		}
		w.write("</table> ");

	}

	private void writeTableHeader(Message mm, Writer w) throws IOException {
		if(mm.getArraySize()==0) {
			w.write("Empty table");
			return;
		}
		Message m = mm.getMessage(0);
		List<Property> pp = m.getAllProperties();
		w.write("<tr>");
		for (Property property : pp) {
			w.write("<td>");
			writePropertyDescription(property, w);
			w.write("</td>");
		}
		w.write("</tr>");	}

	private String getFullPropertyName(Property p) {
		Navajo n = p.getRootDoc();
		try {
			return n.getHeader().getRPCName() + ":" + p.getFullPropertyName();
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		return null;
	}

}
