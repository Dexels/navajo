package com.dexels.navajo.document.stream.api;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.types.Binary;

public class Prop {
	private final String name;
	private final Object value;
	private final String type;
	private final List<Select> selections = new ArrayList<>();
	private final int length;
	private final String description;
	private final Direction direction;
	private final String subtype;
	private final String cardinality;
	
	
	private final static Logger logger = LoggerFactory.getLogger(Prop.class);

	
	public enum Direction {
		IN,OUT
	}
	
	Prop(String name, Object value, String type, List<Select> selections, String cardinality) {
		this(name,value,type,selections,Direction.OUT,"",-1,"",cardinality);
	}
	
	public Prop copy() {
		return new Prop(name, value, type,selections,direction,description,length,subtype,cardinality);
	}

	public Prop(String name, Object value, String type) {
		this(name, value, type, Collections.emptyList(),"1");
	}

	public Prop(String name, Object value, String type, List<Select> selections, Prop.Direction direction,
			String description, int length, String subtype,String cardinality) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.selections.addAll(selections);
		this.direction = direction;
		this.description = description;
		this.length = length;
		this.subtype = subtype;
		this.cardinality = cardinality;
	}

	public static Prop create(String name) {
		return new Prop(name,null,null);
	}

	
	public static Prop create(Map<String,String> attributes, List<Select> selections) {
		String lngth = attributes.get("length");
		String cardinality = attributes.get("cardinality");
		int len = lngth==null || "".equals(lngth)?-1:Integer.parseInt(lngth);
		return create(attributes.get("name"),attributes.get("value"),attributes.get("type"),selections,attributes.get("direction").equals("in")?Direction.IN:Direction.OUT,attributes.get("description"),len,attributes.get("subtype"),cardinality);
	}

	public static Prop create(Map<String, String> attributes, Binary currentBinary) {
		int len =  (int) currentBinary.getLength(); // lngth==null || "".equals(lngth)?-1:Integer.parseInt(lngth);
		return create(attributes.get("name"),currentBinary,attributes.get("type"),Collections.emptyList(),parseDirection(attributes.get("direction")),attributes.get("description"),len,attributes.get("subtype"),null);
	}
	
	private static Direction parseDirection(String direction) {
		if(direction==null) {
			return Direction.OUT;
		}
		return direction.equals("in")?Direction.IN:Direction.OUT;
	}

	public static Prop create(String name, Object value) {
		return new Prop(name,value,null);
	}
	
	public static Prop create(String name, Object value, String type) {
		return new Prop(name,value,type);
	}
	
	public static Prop create(String name, Object value, String type,List<Select> selections, String cardinality) {
		return new Prop(name,value,type,selections,Prop.Direction.OUT,"",-1,"",cardinality);
	}
	
	public static Prop create(String name, Object value, String type,List<Select> selections, Prop.Direction direction, String description, int length, String subtype, String cardinality ) {
		return new Prop(name,value,type,selections,direction,description,length,subtype,cardinality);
	}
	

	
	public Prop withSelections(List<Select> currentSelections) {
		return new Prop(name, value, type,currentSelections,cardinality);
	}

	public Prop withValue(Object val) {
		return new Prop(name, val, type,selections,direction,description,length,subtype,cardinality);
	}
	
	public Prop withBinaryFromFile(String path) {
		File fpath = new File(path);
		if(fpath.exists()) {
			Binary b;
			try {
				b = new Binary(fpath);
			} catch (IOException e) {
				logger.error("Error loading binary: ", e);
				b = new Binary();
			}
			return new Prop(name, b, Property.BINARY_PROPERTY,selections,direction,description,length,subtype,cardinality);
		}
		return new Prop(name, new Binary(), type,selections,direction,description,length,subtype,cardinality);
	}

	public Prop withName(String newName) {
		return new Prop(newName, value, type,selections,direction,description,length,subtype,cardinality);
	}
	
	public Prop emptyWithType(String type) {
		return new Prop(name,null,type);
	}

	public String name() {
		return name;
	}

	public String type() {
		return type;
	}

	public int length() {
		return this.length;
	}
	
	public String toString() {
		return name+":"+value;
	}

	public String description() {
		return this.description;
	}

	public String direction() {
		switch(this.direction) {
		case IN:
			return "in";
		default:
			return "out";
		}

	}

	public Object value() {
		return value;
	}
	
	public String valueAsString() {
		if(value==null) {
			return null;
		}
		if(value instanceof String) {
			return (String)value;
		}
		if(value instanceof Date) {
			Date d = (Date)value;
			String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS")
					.format(d);
			return formatted;
		}
		return value.toString();
	}

	public void write(Writer sw, int indent) throws IOException {
		 for (int a = 0; a < indent; a++) {
			 sw.write(" ");
		 }
		 sw.write("<property");
		 if(name!=null) {
			 sw.write(" name=\""+ StringEscapeUtils.escapeXml(name)+"\"");
		 }
		 if(type!=null) {
			 sw.write(" type=\""+type+"\"");
		 }
		 if(value!=null && !isBinary()) {
			 String value = valueAsString();
			 String escapedValue = StringEscapeUtils.escapeXml(value);
			sw.write(" value=\""+escapedValue+"\"");
		 }
		 if(direction!=null) {
			 sw.write(" direction=\""+direction()+"\"");
		 }
		 if(description!=null && !"".equals(description)) {
			 sw.write(" description=\""+description+"\"");
		 }
		 if(cardinality!=null && !"".equals(cardinality)) {
			 sw.write(" cardinality=\""+cardinality+"\"");
		 }		 
		 if(length>0) {
			 sw.write(" length=\""+length+"\"");
		 }
		 if(!isBinary() && (selections==null || selections.isEmpty())) {
				sw.write("/>\n");
		 } else {
			 sw.write(">\n");
			 if (isBinary()) {
				Binary b = (Binary)value;
				b.writeBase64(sw);
			} else {
				for (Select select : selections) {
					writeSelection(sw, select,indent+NavajoStreamSerializer.INDENT);
				}
			}
			 for (int a = 0; a < indent; a++) {
				 sw.write(" ");
			 }
			sw.write("</property>\n");

		 }
	}

	private void writeSelection(Writer sw, Select select,int indent) throws IOException {
		 for (int a = 0; a < indent; a++) {
			 sw.write(" ");
		 }
		sw.write("<option name=\""+ StringEscapeUtils.escapeXml(select.name())+"\" value=\""+ StringEscapeUtils.escapeXml(select.value())+"\" selected=\""+(select.selected()?"1":"0")+"\"/>\n");
	}
	
	private boolean isBinary() {
		if(value!=null && value instanceof Binary) {
			return true;
		}
		return false;
	}
	public void printStartTag(final Writer sw, int indent,boolean forceDualTags,String tag,  String[] attributes) throws IOException {
		 for (int a = 0; a < indent; a++) {
			 sw.write(" ");
		 }
		 sw.write("<");
		 sw.write(tag);
		 sw.write(" ");
		 for (String attribute : attributes) {
			sw.write(attribute);
		}
		sw.write(">");
	}

	public String subtype() {

		return this.subtype;
	}

	public void subtype(String sub) {
		// TODO, don't want to make it mutable
	}

	public String subtypes(String subTypeKey) {
		Map<String,String> subtypes = Collections.emptyMap();
		return subtypes.get(subTypeKey);
	}

	public void addSelect(Select s) {
		selections.add(s);
		
	}

	public String cardinality() {
		return cardinality;
	}

	public List<Select> selections() {
		return selections;
	}




}
